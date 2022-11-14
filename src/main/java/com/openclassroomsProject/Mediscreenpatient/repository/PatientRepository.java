package com.openclassroomsProject.Mediscreenpatient.repository;

import com.openclassroomsProject.Mediscreenpatient.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Patient repository extend JPA to perform database queries on the Patient resource.
 *
 * @author jonathan GOUVEIA
 * @version 1.0
 */
@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {
}