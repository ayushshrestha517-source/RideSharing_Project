package org.example.ridesharingproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ridesharingproject.dao.RatingDao;
import org.example.ridesharingproject.dto.request.RatingRequestDto;
import org.example.ridesharingproject.dto.response.DriverRatingSummaryResponseDto;
import org.example.ridesharingproject.dto.response.RatingResponseDto;
import org.example.ridesharingproject.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ratings")
@RequiredArgsConstructor
public class RatingController {

    private final RatingDao ratingDao;

    @PreAuthorize("hasRole('PASSENGER')")
    @PostMapping("/{rideId}")
    public ResponseEntity<ApiResponse<RatingResponseDto>> submitRating(@PathVariable Long rideId, @Valid @RequestBody RatingRequestDto dto) {
        return ResponseEntity.ok(ApiResponse.success(ratingDao.submitRating(rideId, dto),"Rating submitted successfully."));
    }

    @PreAuthorize("hasRole('DRIVER')")
    @GetMapping("/driver/me")
    public ResponseEntity<ApiResponse<List<RatingResponseDto>>> getDriverRatings() {
        return ResponseEntity.ok(ApiResponse.success(ratingDao.getDriverRatings(),"Driver ratings retrieved successfully."));
    }

    @PreAuthorize("hasRole('DRIVER')")
    @GetMapping("/driver/me/average")
    public ResponseEntity<ApiResponse<Double>> getMyAverageRating() {
        return ResponseEntity.ok(ApiResponse.success(ratingDao.getMyAverageRating(),"Driver average rating retrieved successfully."));
    }

    @PreAuthorize("hasRole('DRIVER')")
    @GetMapping("/driver/me/summary")
    public ResponseEntity<ApiResponse<DriverRatingSummaryResponseDto>> getMyRatingSummary() {
        return ResponseEntity.ok(ApiResponse.success(ratingDao.getMyRatingSummary(),"Driver rating summary retrieved successfully."));
    }

    @PreAuthorize("hasRole('PASSENGER')")
    @GetMapping("/driver/summary/{driverId}")
    public ResponseEntity<ApiResponse<DriverRatingSummaryResponseDto>> getDriverRatingSummary(@PathVariable Long driverId) {
        return ResponseEntity.ok(ApiResponse.success(ratingDao.getDriverRatingSummary(driverId),"Driver rating summary retrieved successfully."));
    }

    @PreAuthorize("hasRole('PASSENGER')")
    @GetMapping("/driver/{driverId}")
    public ResponseEntity<ApiResponse<List<RatingResponseDto>>> getDriverRatings(@PathVariable Long driverId) {
        return ResponseEntity.ok(ApiResponse.success(ratingDao.getDriverRatings(driverId),"Driver ratings retrieved successfully."));
    }

    @PreAuthorize("hasRole('PASSENGER')")
    @GetMapping("/my-submitted")
    public ResponseEntity<ApiResponse<List<RatingResponseDto>>> getMySubmittedRatings() {
        return ResponseEntity.ok(ApiResponse.success(ratingDao.getMySubmittedRatings(),"Your submitted ratings retrieved successfully."));
    }
}