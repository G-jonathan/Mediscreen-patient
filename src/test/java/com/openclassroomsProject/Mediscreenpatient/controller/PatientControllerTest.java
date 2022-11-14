package com.openclassroomsProject.Mediscreenpatient.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import com.openclassroomsProject.Mediscreenpatient.model.Patient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.sql.Date;
import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PatientControllerTest {
    private static final String LOCALHOST = "http://localhost:";

    @LocalServerPort
    private int serverPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getPatientById() {
        ResponseEntity<Patient> result = restTemplate.getForEntity(LOCALHOST + serverPort + "/patients/1", Patient.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        /*
        assertEquals(Objects.requireNonNull(result.getBody()).getId(),1);
        assertEquals(Objects.requireNonNull(result.getBody()).getFamily(),"TestNone" );
        assertEquals(Objects.requireNonNull(result.getBody()).getGiven(),"Test" );
        assertEquals(Objects.requireNonNull(result.getBody()).getDob().toString(), "1966-12-31");
        assertEquals(Objects.requireNonNull(result.getBody()).getSex(),"F" );
        assertEquals(Objects.requireNonNull(result.getBody()).getAddress(),"1 Brookside St" );
        assertEquals(Objects.requireNonNull(result.getBody()).getPhone(),"100-222-3333" );
         */
    }

    @Test
    void getAllPatient() {

    }

    @Test
    void addPatient() {
        Patient patient = new Patient();
        patient.setFamily("test");
        patient.setGiven("test");
        patient.setDob(new Date(1111-11-11));
        patient.setSex("T");
        ResponseEntity<Patient> response = this.restTemplate.postForEntity(LOCALHOST + serverPort + "/patients", patient, Patient.class);
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    void updatePatient() {
    }

    @Test
    void deletePatient() {
    }
}