package org.example.ridesharingproject.dao;

import lombok.RequiredArgsConstructor;
import org.example.ridesharingproject.dto.request.RideRequestDto;
import org.example.ridesharingproject.dto.response.RideResponseDto;
import org.example.ridesharingproject.entity.Driver;
import org.example.ridesharingproject.entity.Passenger;
import org.example.ridesharingproject.entity.Ride;
import org.example.ridesharingproject.entity.Users;
import org.example.ridesharingproject.enums.DriverVerificationStatus;
import org.example.ridesharingproject.enums.RideStatus;
import org.example.ridesharingproject.exception.BadRequestException;
import org.example.ridesharingproject.exception.ResourceNotFoundException;
import org.example.ridesharingproject.mapper.RideMapper;
import org.example.ridesharingproject.repository.DriverRepository;
import org.example.ridesharingproject.repository.PassengerRepository;
import org.example.ridesharingproject.repository.RideRepository;
import org.example.ridesharingproject.repository.UsersRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RideDaoImpl implements RideDao {

    private final RideRepository rideRepository;
    private final PassengerRepository passengerRepository;
    private final UsersRepository usersRepository;
    private final DriverRepository driverRepository;
    private final RideMapper rideMapper;

    @Override
    @Transactional
    public RideResponseDto requestRide(RideRequestDto dto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        Users user = usersRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Passenger passenger = passengerRepository.findByUserId(user.getId()).orElseThrow(() -> new ResourceNotFoundException("Passenger profile not found"));

        validateCoordinates(dto);
        validateFare(dto.requestedFare());

        Ride ride = rideMapper.toEntity(dto);

        ride.setPassenger(passenger);
        ride.setDriver(null);
        ride.setStatus(RideStatus.REQUESTED);
        ride.setAgreedFare(null);

        Ride savedRide = rideRepository.save(ride);

        return rideMapper.toResponse(savedRide);
    }

    @Override
    public List<RideResponseDto> getRequestedRides() {
        return rideRepository.findByStatus(RideStatus.REQUESTED)
                .stream()
                .map(rideMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public RideResponseDto acceptRide(Long rideId) {

        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        Users user = usersRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

        Driver driver = driverRepository.findByUserId(user.getId()).orElseThrow(() -> new RuntimeException("Driver profile not found"));

        if (!driver.getActive() || !driver.getOnline() || driver.getVerificationStatus() != DriverVerificationStatus.APPROVED) {
            throw new RuntimeException("Only approved, active, online drivers can accept rides");
        }

        Ride ride = rideRepository.findById(rideId).orElseThrow(() -> new RuntimeException("Ride not found"));

        if (ride.getStatus() != RideStatus.REQUESTED) {
            throw new RuntimeException("This ride is no longer available");
        }

        ride.setDriver(driver);
        ride.setStatus(RideStatus.ACCEPTED);

        Ride savedRide = rideRepository.save(ride);

        return rideMapper.toResponse(savedRide);
    }

    private void validateCoordinates(RideRequestDto dto) {

        if (dto.pickupLatitude() < -90 || dto.pickupLatitude() > 90) {
            throw new BadRequestException("Pickup latitude must be between -90 and 90");
        }

        if (dto.pickupLongitude() < -180 || dto.pickupLongitude() > 180) {
            throw new BadRequestException("Pickup longitude must be between -180 and 180");
        }

        if (dto.destinationLatitude() < -90 || dto.destinationLatitude() > 90) {
            throw new BadRequestException("Destination latitude must be between -90 and 90");
        }

        if (dto.destinationLongitude() < -180 || dto.destinationLongitude() > 180) {
            throw new BadRequestException("Destination longitude must be between -180 and 180");
        }
    }

    private void validateFare(BigDecimal requestedFare) {
        if (requestedFare == null || requestedFare.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Requested fare must be greater than zero");
        }
    }
}