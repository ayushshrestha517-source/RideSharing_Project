package org.example.ridesharingproject.service;

import lombok.RequiredArgsConstructor;
import org.example.ridesharingproject.dao.EmailOtpDao;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EmailOtpCleanupService {

    private final EmailOtpDao emailOtpDao;

    @Scheduled(fixedRate = 300000)
    public void cleanupExpiredOtps() {
        emailOtpDao.cleanupExpiredOtps(LocalDateTime.now());
    }
}