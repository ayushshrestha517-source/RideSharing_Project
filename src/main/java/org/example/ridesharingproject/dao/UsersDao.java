package org.example.ridesharingproject.dao;

import org.example.ridesharingproject.dto.request.SignupRequestDto;
import org.example.ridesharingproject.dto.response.SignupResponseDto;

public interface UsersDao {
    public SignupResponseDto register(SignupRequestDto dto);
}
