package org.example.ridesharingproject.dao;

import org.example.ridesharingproject.dto.request.RatingRequestDto;
import org.example.ridesharingproject.dto.response.DriverRatingSummaryResponseDto;
import org.example.ridesharingproject.dto.response.RatingResponseDto;

import java.util.List;

public interface RatingDao {

    RatingResponseDto submitRating(Long rideId, RatingRequestDto dto);

    List<RatingResponseDto> getDriverRatings();

    Double getMyAverageRating();

    DriverRatingSummaryResponseDto getMyRatingSummary();

    DriverRatingSummaryResponseDto getDriverRatingSummary(Long driverId);
}