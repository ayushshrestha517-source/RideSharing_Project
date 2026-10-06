package org.example.ridesharingproject.dao;

import org.example.ridesharingproject.dto.request.DriverRequestDto;
import org.example.ridesharingproject.dto.response.DriverResponseDto;

public interface DriverDao {

    DriverResponseDto applyAsDriver(DriverRequestDto dto);

}