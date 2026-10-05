package org.example.ridesharingproject.repository;

import org.example.ridesharingproject.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengerRepository extends JpaRepository<Passenger,Long> {
}
