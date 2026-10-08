package org.example.ridesharingproject.dao;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.ridesharingproject.dto.request.DriverRequestDto;
import org.example.ridesharingproject.dto.response.DriverResponseDto;
import org.example.ridesharingproject.entity.Driver;
import org.example.ridesharingproject.entity.Role;
import org.example.ridesharingproject.entity.Users;
import org.example.ridesharingproject.exception.AlreadyExistsException;
import org.example.ridesharingproject.exception.BadRequestException;
import org.example.ridesharingproject.exception.ResourceNotFoundException;
import org.example.ridesharingproject.mapper.DriverMapper;
import org.example.ridesharingproject.repository.DriverRepository;
import org.example.ridesharingproject.repository.RoleRepository;
import org.example.ridesharingproject.repository.UsersRepository;
import org.example.ridesharingproject.util.DriverVerificationStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverDaoImpl implements DriverDao {

    private final DriverRepository driverRepository;
    private final UsersRepository usersRepository;
    private final DriverMapper driverMapper;
    private final RoleRepository roleRepository;

    @Override
    public DriverResponseDto applyAsDriver(DriverRequestDto dto) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        Users user = usersRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        // Check if this user has already applied
        if (driverRepository.existsByUserId(user.getId())) {
            throw new AlreadyExistsException(
                    "You have already submitted a driver application");
        }

        // Check duplicate license number
        if (driverRepository.existsByLicenseNumber(dto.licenseNumber())) {
            throw new AlreadyExistsException(
                    "License number already exists");
        }

        // Check duplicate vehicle number
        if (driverRepository.existsByVehicleNumber(dto.vehicleNumber())) {
            throw new AlreadyExistsException(
                    "Vehicle number already exists");
        }

        Driver driver = driverMapper.toEntity(dto);

        // Set values controlled by the system
        driver.setUser(user);
        driver.setVerificationStatus(DriverVerificationStatus.PENDING);
        driver.setActive(false);
        driver.setOnline(false);

        Driver savedDriver = driverRepository.save(driver);

        return driverMapper.toResponse(savedDriver);
    }

    @Transactional
    @Override
    public DriverResponseDto approveDriver(Long driverId) {

        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Driver not found"));

        if (driver.getVerificationStatus() != DriverVerificationStatus.PENDING) {
            throw new BadRequestException(
                    "Only pending driver applications can be approved");
        }

        Role driverRole = roleRepository.findByName("ROLE_DRIVER")
                .orElseThrow(() ->
                        new ResourceNotFoundException("Driver role not found"));

        Users user = driver.getUser();

        if (user.getRoles() == null) {
            user.setRoles(new HashSet<>());
        }

        user.getRoles().add(driverRole);

        usersRepository.save(user);

        driver.setVerificationStatus(DriverVerificationStatus.APPROVED);
        driver.setActive(true);
        driver.setOnline(false);

        Driver savedDriver = driverRepository.save(driver);

        return driverMapper.toResponse(savedDriver);
    }

    @Override
    public DriverResponseDto rejectDriver(Long driverId) {

        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Driver not found"));

        if (driver.getVerificationStatus() != DriverVerificationStatus.PENDING) {
            throw new BadRequestException(
                    "Only pending driver applications can be rejected");
        }

        driver.setVerificationStatus(DriverVerificationStatus.REJECTED);
        driver.setActive(false);
        driver.setOnline(false);

        Driver savedDriver = driverRepository.save(driver);

        return driverMapper.toResponse(savedDriver);
    }

    @Override
    public DriverResponseDto resubmitDriver(DriverRequestDto dto) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        Users user = usersRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Driver driver = driverRepository.findByUserId(user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Driver application not found"));

        if (driver.getVerificationStatus() != DriverVerificationStatus.REJECTED) {
            throw new BadRequestException(
                    "Only rejected driver applications can be resubmitted");
        }

        // Check whether the new license number belongs to another driver
        if (!driver.getLicenseNumber().equals(dto.licenseNumber())
                && driverRepository.existsByLicenseNumber(dto.licenseNumber())) {

            throw new AlreadyExistsException(
                    "License number already exists");
        }

        // Check whether the new vehicle number belongs to another driver
        if (!driver.getVehicleNumber().equals(dto.vehicleNumber())
                && driverRepository.existsByVehicleNumber(dto.vehicleNumber())) {

            throw new AlreadyExistsException(
                    "Vehicle number already exists");
        }

        driver.setLicenseNumber(dto.licenseNumber());
        driver.setLicenseExpiryDate(dto.licenseExpiryDate());
        driver.setLicenseImage(dto.licenseImage());

        driver.setVehicleType(dto.vehicleType());
        driver.setVehicleNumber(dto.vehicleNumber());
        driver.setVehicleModel(dto.vehicleModel());
        driver.setVehicleColour(dto.vehicleColour());
        driver.setBlueBook(dto.blueBook());

        driver.setVerificationStatus(DriverVerificationStatus.PENDING);
        driver.setActive(false);
        driver.setOnline(false);

        Driver savedDriver = driverRepository.save(driver);

        return driverMapper.toResponse(savedDriver);
    }

    @Override
    public List<DriverResponseDto> getPendingDrivers() {

        List<Driver> drivers =
                driverRepository.findByVerificationStatus(
                        DriverVerificationStatus.PENDING
                );

        return drivers.stream()
                .map(driverMapper::toResponse)
                .toList();
    }

    @Override
    public DriverResponseDto goOnline() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        Users user = usersRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Driver driver = driverRepository.findByUserId(user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Driver application not found"));

        if (driver.getVerificationStatus()
                != DriverVerificationStatus.APPROVED) {

            throw new BadRequestException(
                    "Only approved drivers can go online");
        }

        if (!driver.getActive()) {
            throw new BadRequestException(
                    "Driver account is not active");
        }

        if (driver.getLicenseExpiryDate().isBefore(LocalDate.now())) {
            throw new BadRequestException(
                    "Your driving license has expired");
        }

        driver.setOnline(true);

        Driver savedDriver = driverRepository.save(driver);

        return driverMapper.toResponse(savedDriver);
    }

    @Override
    public DriverResponseDto goOffline() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        Users user = usersRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Driver driver = driverRepository.findByUserId(user.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Driver application not found"));

        driver.setOnline(false);

        Driver savedDriver = driverRepository.save(driver);

        return driverMapper.toResponse(savedDriver);
    }

    @Override
    public DriverResponseDto getApprovedDriverById(Long driverId) {

        Driver driver = driverRepository
                .findByIdAndVerificationStatus(
                        driverId,
                        DriverVerificationStatus.APPROVED
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Approved driver not found"));

        return driverMapper.toResponse(driver);
    }

    @Override
    public List<DriverResponseDto> getApprovedDrivers() {

        List<Driver> drivers =
                driverRepository.findByVerificationStatus(
                        DriverVerificationStatus.APPROVED
                );

        return drivers.stream()
                .map(driverMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public DriverResponseDto removeDriver(Long driverId) {

        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Driver not found"));

        if (driver.getVerificationStatus()
                != DriverVerificationStatus.APPROVED) {

            throw new BadRequestException(
                    "Only approved drivers can be removed");
        }

        driver.setActive(false);
        driver.setOnline(false);

        Driver savedDriver = driverRepository.save(driver);

        return driverMapper.toResponse(savedDriver);
    }


}