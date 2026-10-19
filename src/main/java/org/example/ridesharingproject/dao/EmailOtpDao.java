package org.example.ridesharingproject.dao;

import org.example.ridesharingproject.dto.request.EmailOtpRequestDto;
import org.example.ridesharingproject.dto.request.VerifyOtpRequestDto;
import org.example.ridesharingproject.dto.response.EmailOtpResponseDto;

public interface EmailOtpDao {
    EmailOtpResponseDto sendOtp(EmailOtpRequestDto dto);
    EmailOtpResponseDto verifyOtp(VerifyOtpRequestDto dto);
}
