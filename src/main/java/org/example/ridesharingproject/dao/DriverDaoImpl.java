package org.example.ridesharingproject.dao;

import lombok.RequiredArgsConstructor;
import org.example.ridesharingproject.dto.request.DriverRequestDto;
import org.example.ridesharingproject.dto.response.DriverResponseDto;
import org.example.ridesharingproject.entity.Driver;
import org.example.ridesharingproject.entity.Users;
import org.example.ridesharingproject.exception.AlreadyExistsException;
import org.example.ridesharingproject.exception.ResourceNotFoundException;
import org.example.ridesharingproject.mapper.DriverMapper;
import org.example.ridesharingproject.repository.DriverRepository;
import org.example.ridesharingproject.repository.UsersRepository;
import org.example.ridesharingproject.util.DriverVerificationStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DriverDaoImpl implements DriverDao {

    private final DriverRepository driverRepository;
    private final UsersRepository usersRepository;
    private final DriverMapper driverMapper;

    @Override
    public DriverResponseDto applyAsDriver(DriverRequestDto dto) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        Users user = usersRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        if (driverRepository.existsByUserId(user.getId())) {
            throw new AlreadyExistsException(
                    "You have already submitted a driver application");
        }

        if (driverRepository.existsByLicenseNumber(dto.licenseNumber())) {
            throw new AlreadyExistsException(
                    "License number already exists");
        }

        Driver driver = driverMapper.toEntity(dto);

        driver.setUser(user);
        driver.setVerificationStatus(DriverVerificationStatus.PENDING);
        driver.setActive(false);
        driver.setOnline(false);

        Driver savedDriver = driverRepository.save(driver);

        return driverMapper.toResponse(savedDriver);
    }
}