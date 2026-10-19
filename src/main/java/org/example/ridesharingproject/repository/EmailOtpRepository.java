package org.example.ridesharingproject.repository;

import org.example.ridesharingproject.entity.EmailOtp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailOtpRepository extends JpaRepository<EmailOtp,Long> {
    Optional<EmailOtp> findTopByEmailOrderByCreatedAtDesc(String email);
}
