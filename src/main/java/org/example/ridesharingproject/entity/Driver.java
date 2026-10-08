package org.example.ridesharingproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.ridesharingproject.enums.DriverVerificationStatus;
import org.example.ridesharingproject.enums.VehicleType;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "drivers")
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private Users user;

    @Column(nullable = false, unique = true)
    private String licenseNumber;

    @Column(nullable = false)
    private LocalDate licenseExpiryDate;

    @Column(nullable = false)
    private String licenseImage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleType vehicleType;

    @Column(nullable = false, unique = true)
    private String vehicleNumber;

    @Column(nullable = false)
    private String vehicleModel;

    @Column(nullable = false)
    private String vehicleColour;

    @Column(nullable = false)
    private String blueBook;

    // Driver verification details

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DriverVerificationStatus verificationStatus =
            DriverVerificationStatus.PENDING;

    @Column(nullable = false)
    private Boolean active = false;

    @Column(nullable = false)
    private Boolean online = false;
}