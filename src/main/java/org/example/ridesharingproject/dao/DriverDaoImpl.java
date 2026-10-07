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

import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class DriverDaoImpl implements DriverDao {

    private final DriverRepository driverRepository;
    private final UsersRepository usersRepository;
    private final DriverMapper driverMapper;
    private final RoleRepository roleRepository;

    @Override
    public DriverResponseDto applyAsDriver(DriverRequestDto dto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        Users user = usersRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (driverRepository.existsByUserId(user.getId())) {
            throw new AlreadyExistsException("You have already submitted a driver application");
        }

        if (driverRepository.existsByLicenseNumber(dto.licenseNumber())) {
            throw new AlreadyExistsException("License number already exists");
        }

        Driver driver = driverMapper.toEntity(dto);

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

        Driver driver = driverRepository.findById(driverId).orElseThrow(() -> new ResourceNotFoundException("Driver not found"));

        if (driver.getVerificationStatus() != DriverVerificationStatus.PENDING) {
            throw new BadRequestException("Only pending driver applications can be approved");
        }

        Role driverRole = roleRepository.findByName("ROLE_DRIVER").orElseThrow(() -> new ResourceNotFoundException("Driver role not found"));

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
}