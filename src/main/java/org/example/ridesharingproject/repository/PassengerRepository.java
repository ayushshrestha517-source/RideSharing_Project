package org.example.ridesharingproject.repository;

import org.example.ridesharingproject.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PassengerRepository extends JpaRepository<Passenger,Long> {
    boolean existsByUserId(Long userId);
    Optional<Passenger> findByUserUsername(String username);
    List<Passenger> findByActiveTrue();
}
