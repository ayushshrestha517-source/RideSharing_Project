package org.example.ridesharingproject.dao;

import org.example.ridesharingproject.dto.request.LoginRequestDto;
import org.example.ridesharingproject.dto.request.ResetPasswordRequestDto;
import org.example.ridesharingproject.dto.request.SelectRoleRequestDto;
import org.example.ridesharingproject.dto.request.SignupRequestDto;
import org.example.ridesharingproject.dto.response.LoginResponseDto;
import org.example.ridesharingproject.dto.response.SelectRoleResponseDto;
import org.example.ridesharingproject.dto.response.SignupResponseDto;

public interface UsersDao {
    SignupResponseDto register(SignupRequestDto dto);
    SelectRoleResponseDto selectRole(SelectRoleRequestDto dto);
    LoginResponseDto login(LoginRequestDto loginRequestDTO);
    void resetPassword(ResetPasswordRequestDto dto);
}
