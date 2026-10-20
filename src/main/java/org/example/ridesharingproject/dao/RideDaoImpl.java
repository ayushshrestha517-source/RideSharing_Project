package org.example.ridesharingproject.dao;

import lombok.RequiredArgsConstructor;
import org.example.ridesharingproject.dto.request.FareResponseRequestDto;
import org.example.ridesharingproject.dto.request.ProposeFareRequestDto;
import org.example.ridesharingproject.dto.request.RideRequestDto;
import org.example.ridesharingproject.dto.response.DriverEarningsResponseDto;
import org.example.ridesharingproject.dto.response.RideResponseDto;
import org.example.ridesharingproject.entity.Driver;
import org.example.ridesharingproject.entity.Passenger;
import org.example.ridesharingproject.entity.Ride;
import org.example.ridesharingproject.entity.Users;
import org.example.ridesharingproject.enums.DriverVerificationStatus;
import org.example.ridesharingproject.enums.RideStatus;
import org.example.ridesharingproject.exception.BadRequestException;
import org.example.ridesharingproject.exception.ForbiddenException;
import org.example.ridesharingproject.exception.ResourceNotFoundException;
import org.example.ridesharingproject.exception.RunTimeException;
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

        if (rideRepository.existsByPassengerIdAndStatusIn(passenger.getId(), List.of(RideStatus.REQUESTED, RideStatus.FARE_PROPOSED, RideStatus.ACCEPTED, RideStatus.IN_PROGRESS))) {
            throw new BadRequestException("You already have an active ride");
        }

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
    public RideResponseDto cancelRide(Long rideId) {

        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        Users user = usersRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Passenger passenger = passengerRepository.findByUserId(user.getId()).orElseThrow(() -> new ResourceNotFoundException("Passenger profile not found"));

        Ride ride = rideRepository.findById(rideId).orElseThrow(() -> new ResourceNotFoundException("Ride not found"));

        if (!ride.getPassenger().getId().equals(passenger.getId())) {
            throw new ForbiddenException("You can only cancel your own rides");
        }

        if (ride.getStatus() != RideStatus.REQUESTED && ride.getStatus() != RideStatus.FARE_PROPOSED) {
            throw new BadRequestException("Only requested rides or rides with pending fare offers can be cancelled");
        }

        ride.setStatus(RideStatus.CANCELLED);
        ride.setProposedFare(null);
        ride.setDriver(null);

        Ride savedRide = rideRepository.save(ride);
        return rideMapper.toResponse(savedRide);
    }

    @Override
    public List<RideResponseDto> getMyRides() {

        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        Users user = usersRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Passenger passenger = passengerRepository.findByUserId(user.getId()).orElseThrow(() -> new ResourceNotFoundException("Passenger profile not found"));

        return rideRepository.findByPassengerId(passenger.getId())
                .stream()
                .map(rideMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public RideResponseDto startRide(Long rideId) {

        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        Users user = usersRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Driver driver = driverRepository.findByUserId(user.getId()).orElseThrow(() -> new ResourceNotFoundException("Driver profile not found"));

        if (!Boolean.TRUE.equals(driver.getActive()) || !Boolean.TRUE.equals(driver.getOnline()) || driver.getVerificationStatus() != DriverVerificationStatus.APPROVED) {
            throw new RunTimeException("Only approved, active, online drivers can start rides");
        }

        Ride ride = rideRepository.findById(rideId).orElseThrow(() -> new ResourceNotFoundException("Ride not found"));

        if (ride.getDriver() == null || !ride.getDriver().getId().equals(driver.getId())) {
            throw new ForbiddenException("You can only start rides assigned to you");
        }

        if (ride.getStatus() != RideStatus.ACCEPTED) {
            throw new BadRequestException("Only accepted rides can be started");
        }

        if (ride.getAgreedFare() == null || ride.getAgreedFare().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RunTimeException("Cannot start a ride without a valid agreed fare");
        }

        if (rideRepository.existsByDriverIdAndStatus(driver.getId(), RideStatus.IN_PROGRESS)) {
            throw new BadRequestException("You already have a ride in progress");
        }

        ride.setStatus(RideStatus.IN_PROGRESS);
        ride.setStartedAt(java.time.LocalDateTime.now());

        Ride savedRide = rideRepository.save(ride);

        return rideMapper.toResponse(savedRide);
    }

    @Override
    @Transactional
    public RideResponseDto completeRide(Long rideId) {

        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        Users user = usersRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Driver driver = driverRepository.findByUserId(user.getId()).orElseThrow(() -> new ResourceNotFoundException("Driver profile not found"));

        if (!Boolean.TRUE.equals(driver.getActive()) || driver.getVerificationStatus() != DriverVerificationStatus.APPROVED) {
            throw new BadRequestException("Only approved, active drivers can complete rides");
        }

        Ride ride = rideRepository.findById(rideId).orElseThrow(() -> new ResourceNotFoundException("Ride not found"));

        if (ride.getDriver() == null || !ride.getDriver().getId().equals(driver.getId())) {
            throw new ForbiddenException("You can only complete rides assigned to you");
        }

        if (ride.getStatus() != RideStatus.IN_PROGRESS) {
            throw new BadRequestException("Only rides in progress can be completed");
        }

        ride.setStatus(RideStatus.COMPLETED);
        ride.setCompletedAt(java.time.LocalDateTime.now());

        Ride savedRide = rideRepository.save(ride);

        return rideMapper.toResponse(savedRide);
    }

    @Override
    @Transactional
    public RideResponseDto proposeFare(Long rideId, ProposeFareRequestDto dto) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Users user = usersRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Driver driver = driverRepository.findByUserId(user.getId()).orElseThrow(() -> new ResourceNotFoundException("Driver profile not found"));

        if (!driver.getActive() || !driver.getOnline() || driver.getVerificationStatus() != DriverVerificationStatus.APPROVED) {
            throw new RunTimeException("Only approved, active, online drivers can propose fares");
        }

        if (dto.proposedFare() == null || dto.proposedFare().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Proposed fare must be greater than zero");
        }

        Ride ride = rideRepository.findById(rideId).orElseThrow(() -> new ResourceNotFoundException("Ride not found"));

        if (ride.getStatus() != RideStatus.REQUESTED || ride.getDriver() != null) {
            throw new RunTimeException("This ride is no longer available for a fare proposal");
        }

        if (rideRepository.existsByDriverIdAndStatus(driver.getId(), RideStatus.IN_PROGRESS)) {
            throw new BadRequestException("You cannot propose a fare while a ride is in progress");
        }

        ride.setProposedFare(dto.proposedFare());
        ride.setStatus(RideStatus.FARE_PROPOSED);
        ride.setDriver(driver);

        Ride savedRide = rideRepository.save(ride);

        return rideMapper.toResponse(savedRide);
    }

    @Override
    @Transactional
    public RideResponseDto respondToFare(Long rideId, FareResponseRequestDto dto) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Users user = usersRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Passenger passenger = passengerRepository.findByUserId(user.getId()).orElseThrow(() -> new ResourceNotFoundException("Passenger profile not found"));

        Ride ride = rideRepository.findById(rideId).orElseThrow(() -> new ResourceNotFoundException("Ride not found"));

        // Ensure the ride belongs to this passenger
        if (!ride.getPassenger().getId().equals(passenger.getId())) {
            throw new ForbiddenException("You can only respond to fares for your own rides");
        }

        // Ensure a driver has proposed a fare
        if (ride.getStatus() != RideStatus.FARE_PROPOSED) {
            throw new RunTimeException("This ride does not have a pending fare proposal");
        }

        // Ensure the proposed fare exists
        if (ride.getProposedFare() == null) {
            throw new RunTimeException("No proposed fare found");
        }

        if (ride.getDriver() == null) {
            throw new RunTimeException("No driver is assigned to this fare proposal");
        }
        if (dto.accepted()) {
            // Passenger accepts the driver's offer
            ride.setAgreedFare(ride.getProposedFare());
            ride.setProposedFare(null);
            ride.setStatus(RideStatus.ACCEPTED);
        } else {
            // Passenger rejects the offer
            ride.setProposedFare(null);
            ride.setStatus(RideStatus.REQUESTED);
            ride.setDriver(null);
        }

        Ride savedRide = rideRepository.save(ride);

        return rideMapper.toResponse(savedRide);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RideResponseDto> getMyDriverRides() {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Users user = usersRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Driver driver = driverRepository.findByUserId(user.getId()).orElseThrow(() -> new ResourceNotFoundException("Driver profile not found"));

        return rideRepository.findByDriverId(driver.getId())
                .stream()
                .map(rideMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<RideResponseDto> getMyRidesByStatus(RideStatus status) {

        String username = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        Users user = usersRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Passenger passenger = passengerRepository.findByUserId(user.getId()).orElseThrow(() -> new ResourceNotFoundException("Passenger profile not found"));

        return rideRepository.findByPassengerIdAndStatus(passenger.getId(), status)
                .stream()
                .map(rideMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<RideResponseDto> getMyDriverRidesByStatus(RideStatus status) {

        String username = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        Users user = usersRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Driver driver = driverRepository.findByUserId(user.getId()).orElseThrow(() -> new ResourceNotFoundException("Driver profile not found"));

        return rideRepository.findByDriverIdAndStatus(driver.getId(), status)
                .stream()
                .map(rideMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DriverEarningsResponseDto getMyEarnings() {

        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        Users user = usersRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Driver driver = driverRepository.findByUserId(user.getId()).orElseThrow(() -> new ResourceNotFoundException("Driver profile not found"));

        List<Ride> completedRides = rideRepository.findByDriverIdAndStatus(driver.getId(),RideStatus.COMPLETED);

        BigDecimal totalEarnings = completedRides.stream()
                .map(Ride::getAgreedFare)
                .filter(java.util.Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        long completedRideCount = completedRides.size();

        BigDecimal averageEarnings = completedRideCount == 0
                ? BigDecimal.ZERO
                : totalEarnings.divide(
                BigDecimal.valueOf(completedRideCount),
                2,
                java.math.RoundingMode.HALF_UP
        );

        return new DriverEarningsResponseDto(totalEarnings, completedRideCount, averageEarnings);
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
