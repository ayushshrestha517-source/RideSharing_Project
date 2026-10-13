package org.example.ridesharingproject.dao;

import org.example.ridesharingproject.dto.request.ProposeFareRequestDto;
import org.example.ridesharingproject.dto.request.RideRequestDto;
import org.example.ridesharingproject.dto.response.RideResponseDto;

import java.util.List;

public interface RideDao {
    RideResponseDto requestRide(RideRequestDto dto);
    List<RideResponseDto> getRequestedRides();
    RideResponseDto acceptRide(Long rideId);
    RideResponseDto cancelRide(Long rideId);
    List<RideResponseDto> getMyRides();
    RideResponseDto startRide(Long rideId);
    RideResponseDto completeRide(Long rideId);
    RideResponseDto proposeFare(Long rideId, ProposeFareRequestDto dto);
}
