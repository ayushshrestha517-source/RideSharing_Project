package org.example.ridesharingproject.repository;

import org.example.ridesharingproject.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    boolean existsByRideId(Long rideId);

    List<Rating> findByDriverId(Long driverId);

    List<Rating> findByPassengerId(Long passengerId);
}
