package org.example.ridesharingproject.repository;

import org.example.ridesharingproject.entity.EmergencyContact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmergencyContactRepository extends JpaRepository<EmergencyContact,Long> {

}
