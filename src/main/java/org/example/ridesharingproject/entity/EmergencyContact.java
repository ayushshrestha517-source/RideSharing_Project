package org.example.ridesharingproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "emergency_contacts")
public class EmergencyContact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "passenger_id", nullable = false)
    private Passenger passenger;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 20)
    private String phone;

    @Column(length = 50)
    private String relationship;

    @Column(nullable = false)
    private Boolean active = true;
}


//POST /emergency-contacts
//        ↓
//Check active contacts
//        ↓
//count >= 3
//        ↓
//Reject
//"Maximum of 3 active emergency contacts allowed"

//long activeContacts =
//        emergencyContactRepository.countByPassengerIdAndActiveTrue(
//                passenger.getId()
//        );
//
//if (activeContacts >= 3) {
//        throw new BadRequestException(
//            "Maximum of 3 active emergency contacts allowed"
//);
//}