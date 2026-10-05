package org.example.ridesharingproject.controller;

import lombok.RequiredArgsConstructor;
import org.example.ridesharingproject.dao.UsersDao;
import org.example.ridesharingproject.dto.request.SignupRequestDto;
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
    public ResponseEntity<SignupResponseDto> register(
            @RequestBody SignupRequestDto dto) {

        SignupResponseDto response = usersDao.register(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}