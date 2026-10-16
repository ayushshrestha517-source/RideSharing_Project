package org.example.ridesharingproject.repository;

import org.example.ridesharingproject.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    boolean existsByRideId(Long rideId);

    List<Rating> findByDriverId(Long driverId);

    List<Rating> findByPassengerId(Long passengerId);

    @Query("""
    SELECT AVG(r.score)
    FROM Rating r
    WHERE r.driver.id = :driverId
    """)
    Double findAverageRatingByDriverId(@Param("driverId") Long driverId);

    long countByDriverId(Long driverId);
}
