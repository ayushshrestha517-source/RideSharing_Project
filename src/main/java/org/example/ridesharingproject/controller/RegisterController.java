package org.example.ridesharingproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ridesharingproject.dao.UsersDao;
import org.example.ridesharingproject.dto.request.LoginRequestDto;
import org.example.ridesharingproject.dto.request.ResetPasswordRequestDto;
import org.example.ridesharingproject.dto.request.SelectRoleRequestDto;
import org.example.ridesharingproject.dto.request.SignupRequestDto;
import org.example.ridesharingproject.dto.response.ApiResponse;
import org.example.ridesharingproject.dto.response.LoginResponseDto;
import org.example.ridesharingproject.dto.response.SelectRoleResponseDto;
import org.example.ridesharingproject.dto.response.SignupResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class RegisterController {

    private final UsersDao usersDao;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<SignupResponseDto>> register(@RequestBody SignupRequestDto dto) {
       return ResponseEntity.ok(ApiResponse.success(usersDao.register(dto),"User registered successfully."));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDto>> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(ApiResponse.success(usersDao.login(loginRequestDto),"User logged in successfully"));
    }

    @PutMapping("/reset-password")
    public ResponseEntity<ApiResponse<Void>> resetPassword(@Valid @RequestBody ResetPasswordRequestDto dto) {
        usersDao.resetPassword(dto);
        return ResponseEntity.ok(ApiResponse.success(null, "Password reset successfully."));
    }

    @PutMapping("/select-role")
    public ResponseEntity<ApiResponse<SelectRoleResponseDto>> selectRole(@RequestBody SelectRoleRequestDto dto) {
        return ResponseEntity.ok(ApiResponse.success(usersDao.selectRole(dto), "Role selection done successfully."));
    }
}