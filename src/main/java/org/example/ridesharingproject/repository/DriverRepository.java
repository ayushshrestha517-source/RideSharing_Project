package org.example.ridesharingproject.repository;

import org.example.ridesharingproject.entity.Driver;
import org.example.ridesharingproject.enums.DriverVerificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DriverRepository extends JpaRepository<Driver, Long> {

    Optional<Driver> findByUserId(Long userId);

    boolean existsByUserId(Long userId);

    boolean existsByLicenseNumber(String licenseNumber);

    boolean existsByVehicleNumber(String vehicleNumber);

    List<Driver> findByVerificationStatus(DriverVerificationStatus verificationStatus);

    Optional<Driver> findByIdAndVerificationStatus(Long id, DriverVerificationStatus verificationStatus);

}