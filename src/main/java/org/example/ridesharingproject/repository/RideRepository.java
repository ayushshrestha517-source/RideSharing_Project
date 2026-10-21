package org.example.ridesharingproject.repository;

import org.example.ridesharingproject.entity.Ride;
import org.example.ridesharingproject.enums.RideStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface RideRepository extends JpaRepository<Ride, Long> {

    List<Ride> findByPassengerId(Long passengerId);

    List<Ride> findByDriverId(Long driverId);

    List<Ride> findByStatus(RideStatus status);

    List<Ride> findByPassengerIdAndStatus(Long passengerId, RideStatus status);

    List<Ride> findByDriverIdAndStatus(Long driverId, RideStatus status);

    boolean existsByDriverIdAndStatus(Long driverId, RideStatus status);

    boolean existsByPassengerIdAndStatusIn(Long passengerId, Collection<RideStatus> statuses);

    boolean existsByDriverIdAndStatusIn(Long driverId, Collection<RideStatus> statuses);
}