package com.openclassroomsProject.Mediscreenpatient.service;

import com.openclassroomsProject.Mediscreenpatient.model.Patient;
import java.util.List;
import java.util.Optional;

/**
 * @author jonathan GOUVEIA
 * @version 1.0
 */
public interface IPatientService {

    Optional<Patient> getPatientById(int id);

    List<Patient>getAllPatients();

    Patient updatePatient(Patient actualPatient, Patient newPatient);

    Patient addPatient(Patient patient);

    void deletePatientById(int id);
}