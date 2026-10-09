package org.example.ridesharingproject.dao;

import org.example.ridesharingproject.dto.request.RideRequestDto;
import org.example.ridesharingproject.dto.response.RideResponseDto;

public interface RideDao {
    RideResponseDto requestRide(RideRequestDto dto);
}
