package org.example.ridesharingproject.dao;

import lombok.RequiredArgsConstructor;
import org.example.ridesharingproject.dto.request.RatingRequestDto;
import org.example.ridesharingproject.dto.response.DriverRatingSummaryResponseDto;
import org.example.ridesharingproject.dto.response.RatingResponseDto;
import org.example.ridesharingproject.entity.*;
import org.example.ridesharingproject.enums.RideStatus;
import org.example.ridesharingproject.exception.ForbiddenException;
import org.example.ridesharingproject.exception.ResourceNotFoundException;
import org.example.ridesharingproject.exception.RunTimeException;
import org.example.ridesharingproject.mapper.RatingMapper;
import org.example.ridesharingproject.repository.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingDaoImpl implements RatingDao {

    private final RatingRepository ratingRepository;
    private final RideRepository rideRepository;
    private final UsersRepository usersRepository;
    private final PassengerRepository passengerRepository;
    private final DriverRepository driverRepository;
    private final RatingMapper ratingMapper;

    @Override
    @Transactional
    public RatingResponseDto submitRating(Long rideId, RatingRequestDto dto) {

        String username = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        Users user = usersRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Passenger passenger = passengerRepository.findByUserId(user.getId()).orElseThrow(() -> new ResourceNotFoundException("Passenger profile not found"));

        Ride ride = rideRepository.findById(rideId).orElseThrow(() -> new ResourceNotFoundException("Ride not found"));

        if (!ride.getPassenger().getId().equals(passenger.getId())) {
            throw new ForbiddenException("You can only rate rides that belong to you");
        }

        if (ride.getStatus() != RideStatus.COMPLETED) {
            throw new RunTimeException("You can only rate a completed ride");
        }

        if (ride.getDriver() == null) {
            throw new RunTimeException("Cannot rate a ride without an assigned driver");
        }

        if (ratingRepository.existsByRideId(rideId)) {
            throw new RunTimeException("This ride has already been rated");
        }

        Rating rating = ratingMapper.toEntity(dto);

        rating.setRide(ride);
        rating.setPassenger(passenger);
        rating.setDriver(ride.getDriver());

        Rating savedRating = ratingRepository.save(rating);

        return ratingMapper.toResponse(savedRating);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RatingResponseDto> getDriverRatings() {

        String username = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        Users user = usersRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Driver driver = driverRepository.findByUserId(user.getId()).orElseThrow(() -> new ResourceNotFoundException("Driver profile not found"));

        return ratingRepository.findByDriverId(driver.getId())
                .stream()
                .map(ratingMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Double getMyAverageRating() {

        String username = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        Users user = usersRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Driver driver = driverRepository.findByUserId(user.getId()).orElseThrow(() -> new ResourceNotFoundException("Driver profile not found"));

        Double averageRating = ratingRepository.findAverageRatingByDriverId(driver.getId());

        return averageRating != null ? Math.round(averageRating * 100.0) / 100.0 : 0.0;
    }

    @Override
    public DriverRatingSummaryResponseDto getMyRatingSummary() {

        String username = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        Users user = usersRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Driver driver = driverRepository.findByUserId(user.getId()).orElseThrow(() -> new ResourceNotFoundException("Driver profile not found"));

        Double averageRating = ratingRepository.findAverageRatingByDriverId(driver.getId());

        long totalRatings = ratingRepository.countByDriverId(driver.getId());

        double average = averageRating != null ? Math.round(averageRating * 100.0) / 100.0 : 0.0;

        return new DriverRatingSummaryResponseDto(average, totalRatings);
    }
}
