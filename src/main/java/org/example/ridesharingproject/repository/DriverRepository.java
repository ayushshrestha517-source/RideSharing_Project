package org.example.ridesharingproject.repository;

import org.example.ridesharingproject.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<Driver,Long> {
}
