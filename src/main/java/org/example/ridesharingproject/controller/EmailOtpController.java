package org.example.ridesharingproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ridesharingproject.dao.EmailOtpDao;
import org.example.ridesharingproject.dto.request.EmailOtpRequestDto;
import org.example.ridesharingproject.dto.request.VerifyOtpRequestDto;
import org.example.ridesharingproject.dto.response.ApiResponse;
import org.example.ridesharingproject.dto.response.EmailOtpResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmailOtpController {
    private final EmailOtpDao emailOtpDAO;

    @PostMapping("/sendotp")
    public ResponseEntity<ApiResponse<EmailOtpResponseDto>> sendOtp(@Valid @RequestBody EmailOtpRequestDto dto){
        return ResponseEntity.ok(ApiResponse.success(emailOtpDAO.sendOtp(dto),"OTP sent successfully."));
    }

    @PostMapping("/verifyotp")
    public ResponseEntity<ApiResponse<EmailOtpResponseDto>> verifyOtp(@Valid @RequestBody VerifyOtpRequestDto dto){
        return ResponseEntity.ok(ApiResponse.success(emailOtpDAO.verifyOtp(dto),"OTP verified successfully."));
    }
}
