package com.openclassroomsProject.Mediscreenpatient.service.impl;

import com.openclassroomsProject.Mediscreenpatient.model.Patient;
import com.openclassroomsProject.Mediscreenpatient.repository.PatientRepository;
import com.openclassroomsProject.Mediscreenpatient.service.IPatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * @author jonathan GOUVEIA
 * @version 1.0
 */
@Service
public class PatientServiceImpl implements IPatientService {
    private final Logger LOGGER = LoggerFactory.getLogger(PatientServiceImpl.class);

    @Autowired
    PatientRepository patientRepository;

    /**
     * Get Patient by id
     *
     * @param id Identifier of the wanted patient.
     * @return The wanted Patient object.
     */
    @Override
    public Optional<Patient> getPatientById(int id) {
        LOGGER.info("[SERVICE] Call method: getPatientById");
        return patientRepository.findById(id);
    }

    /**
     * Get all Patient
     *
     * @return A list of all patients found in the database.
     */
    @Override
    public List<Patient> getAllPatients() {
        LOGGER.info("[SERVICE] Call method: getAllPatients");
        return patientRepository.findAll();
    }

    /**
     * Modify an existing Patient.
     *
     * @param actualPatient The actual Patient object.
     * @param newPatient    The Patient to modify.
     * @return The Patient object modified.
     */
    @Override
    public Patient updatePatient(Patient actualPatient, Patient newPatient) {
        LOGGER.info("[SERVICE] Call method: updatePatient");
        actualPatient.setFamily(newPatient.getFamily());
        actualPatient.setGiven(newPatient.getGiven());
        actualPatient.setDob(newPatient.getDob());
        actualPatient.setSex(newPatient.getSex());
        if (newPatient.getAddress() != null) {
            actualPatient.setAddress(newPatient.getAddress());
        }
        if (newPatient.getPhone() != null) {
            actualPatient.setPhone(newPatient.getPhone());
        }
        return patientRepository.save(actualPatient);
    }

    /**
     * Create a new a Patient
     *
     * @param patient The patient to create.
     * @return The Patient object created.
     */
    @Override
    public Patient addPatient(Patient patient) {
        LOGGER.info("[SERVICE] Call method: addPatient");
        return patientRepository.save(patient);
    }

    /**
     * @param id Patient Id to delete.
     */
    @Override
    public void deletePatientById(int id) {
        LOGGER.info("[SERVICE] Call method: deletePatientById");
        patientRepository.deleteById(id);
    }
}