package org.example.ridesharingproject.dao;

import org.example.ridesharingproject.dto.request.DriverRequestDto;
import org.example.ridesharingproject.dto.response.DriverResponseDto;

import java.util.List;

public interface DriverDao {

    DriverResponseDto applyAsDriver(DriverRequestDto dto);
    DriverResponseDto approveDriver(Long driverId);
    DriverResponseDto rejectDriver(Long driverId);
    DriverResponseDto resubmitDriver(DriverRequestDto dto);
    List<DriverResponseDto> getPendingDrivers();
    DriverResponseDto goOnline();
    DriverResponseDto goOffline();
    DriverResponseDto getApprovedDriverById(Long driverId);
    List<DriverResponseDto> getApprovedDrivers();
    DriverResponseDto removeDriver(Long driverId);


}