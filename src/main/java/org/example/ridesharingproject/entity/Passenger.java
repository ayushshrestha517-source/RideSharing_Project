package org.example.ridesharingproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "passengers")
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            unique = true
    )
    private Users user;

    @Column(nullable = false)
    private Boolean active = true;

    @OneToMany(
            mappedBy = "passenger",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<EmergencyContact> emergencyContacts = new HashSet<>();
}

