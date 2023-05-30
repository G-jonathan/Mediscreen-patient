package com.openclassroomsProject.Mediscreenpatient.service.impl;

import com.openclassroomsProject.Mediscreenpatient.model.Patient;
import com.openclassroomsProject.Mediscreenpatient.repository.PatientRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Tests for the {@link PatientServiceImpl} class.
 *
 * @author Jonathan GOUVEIA
 * @version 1.0
 */
class PatientServiceImplTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientServiceImpl patientService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetPatientById_whenPatientExist_thenReturnCorrectPatient() {
        int patientId = 1;
        Patient expectedPatient = new Patient();
        Mockito.when(patientRepository.findById(patientId)).thenReturn(Optional.of(expectedPatient));
        Optional<Patient> actualPatient = patientService.getPatientById(patientId);
        Assertions.assertEquals(Optional.of(expectedPatient), actualPatient);
        Mockito.verify(patientRepository, Mockito.times(1)).findById(patientId);
    }

    @Test
    public void testGetPatientById_whenPatientDoesNotExist_thenReturnEmptyOptional() {
        int patientId = 1;
        Mockito.when(patientRepository.findById(patientId)).thenReturn(Optional.empty());
        Optional<Patient> actualPatient = patientService.getPatientById(patientId);
        Assertions.assertEquals(Optional.empty(), actualPatient);
        Mockito.verify(patientRepository, Mockito.times(1)).findById(patientId);
    }

    @Test
    public void testGetAllPatients_shouldReturnCorrectList() {
        Patient patient = new Patient();
        patient.setFamily("family_test");
        patient.setGiven("given_test");
        patient.setDob(new Date(1000));
        patient.setSex("M");
        Patient patient2 = new Patient();
        patient2.setFamily("family_test2");
        patient2.setGiven("given_test2");
        patient2.setDob(new Date(2000));
        patient2.setSex("F");
        List<Patient> expectedPatientList = new ArrayList<>();
        expectedPatientList.add(patient);
        expectedPatientList.add(patient2);
        Mockito.when(patientRepository.findAll()).thenReturn(expectedPatientList);
        List<Patient> actualPatientsList = patientService.getAllPatients();
        Assertions.assertEquals(expectedPatientList, actualPatientsList);
        Mockito.verify(patientRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void testUpdatePatient_shouldReturnCorrectPatient() {
        Patient actualPatient = new Patient();
        actualPatient.setFamily("family_test");
        actualPatient.setGiven("given_test");
        actualPatient.setDob(new Date(5000));
        actualPatient.setSex("M");
        Patient newPatient = new Patient();
        newPatient.setFamily("family_test_updated");
        newPatient.setGiven("given_test_updated");
        newPatient.setDob(new Date(5000));
        newPatient.setSex("F");
        Mockito.when(patientRepository.save(actualPatient)).thenReturn(actualPatient);
        Patient updatedPatient = patientService.updatePatient(actualPatient, newPatient);
        Assertions.assertEquals(actualPatient, updatedPatient);
        Mockito.verify(patientRepository, Mockito.times(1)).save(actualPatient);
    }

    @Test
    public void testAddPatient() {
        Patient patientToSave = new Patient();
        patientToSave.setFamily("family_test");
        patientToSave.setGiven("given_test");
        patientToSave.setDob(new Date(2500));
        patientToSave.setSex("M");
        Mockito.when(patientRepository.save(patientToSave)).thenReturn(patientToSave);
        Patient addedPatient = patientService.addPatient(patientToSave);
        Assertions.assertEquals(patientToSave, addedPatient);
        Mockito.verify(patientRepository, Mockito.times(1)).save(patientToSave);
    }

    @Test
    public void testDeletePatientById() {
        int patientId = 1;
        patientService.deletePatientById(patientId);
        Mockito.verify(patientRepository, Mockito.times(1)).deleteById(patientId);
    }
}