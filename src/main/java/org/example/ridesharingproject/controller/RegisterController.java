package org.example.ridesharingproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ridesharingproject.dao.UsersDao;
import org.example.ridesharingproject.dto.request.*;
import org.example.ridesharingproject.dto.response.ApiResponse;
import org.example.ridesharingproject.dto.response.LoginResponseDto;
import org.example.ridesharingproject.dto.response.SelectRoleResponseDto;
import org.example.ridesharingproject.dto.response.SignupResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PutMapping("/change-password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<Void>> changePassword(@Valid @RequestBody ChangePasswordRequestDto dto) {
        usersDao.changePassword(dto);
        return ResponseEntity.ok(ApiResponse.success(null,"Password changed successfully"));
    }

    @PutMapping("/select-role")
    public ResponseEntity<ApiResponse<SelectRoleResponseDto>> selectRole(@RequestBody SelectRoleRequestDto dto) {
        return ResponseEntity.ok(ApiResponse.success(usersDao.selectRole(dto), "Role selection done successfully."));
    }
}