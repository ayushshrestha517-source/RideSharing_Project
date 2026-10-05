package org.example.ridesharingproject.controller;

import lombok.RequiredArgsConstructor;
import org.example.ridesharingproject.dao.UsersDao;
import org.example.ridesharingproject.dto.request.SignupRequestDto;
import org.example.ridesharingproject.dto.response.ApiResponse;
import org.example.ridesharingproject.dto.response.SignupResponseDto;
import org.springframework.http.HttpStatus;
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
}