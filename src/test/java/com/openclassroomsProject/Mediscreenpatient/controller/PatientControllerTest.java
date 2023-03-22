package com.openclassroomsProject.Mediscreenpatient.controller;

import static org.junit.jupiter.api.Assertions.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassroomsProject.Mediscreenpatient.model.Patient;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import java.sql.Date;
import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PatientControllerTest {
    private static final String LOCALHOST = "http://localhost:";
    private static final String URI = "/patients/";
    private static final long MILLIS = System.currentTimeMillis();
    private static final Date ACTUALDATE = new Date(MILLIS);

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
    @Order(1)
    @DisplayName("Patient can be found")
    void getPatientById_whenExistingResource_thenResponseIsOk() {
        int patientToGetId = 1;
        ResponseEntity<Patient> getPatientDetailEntity = restTemplate.getForEntity(LOCALHOST + serverPort + URI + patientToGetId, Patient.class);
        Patient bodyContent = getPatientDetailEntity.getBody();
        assertEquals(HttpStatus.OK, getPatientDetailEntity.getStatusCode());
        assertEquals(Objects.requireNonNull(bodyContent).getId(), 1);
        assertEquals(Objects.requireNonNull(bodyContent).getFamily(), "TestNone");
        assertEquals(Objects.requireNonNull(bodyContent).getGiven(), "Test");
        assertEquals(Objects.requireNonNull(bodyContent).getDob().toString(), "1966-12-31");
        assertEquals(Objects.requireNonNull(bodyContent).getSex(), "F");
        assertEquals(Objects.requireNonNull(bodyContent).getAddress(), "1 Brookside St");
        assertEquals(Objects.requireNonNull(bodyContent).getPhone(), "100-222-3333");
    }

    @Test
    @Order(2)
    @DisplayName("Patient can't be found")
    void getPatientById_whenUnknownResource_thenResponseIsNotFound() throws JsonProcessingException {
        int patientToGetId = 9999;
        ResponseEntity<String> getPatientResponse = restTemplate.getForEntity(LOCALHOST + serverPort + URI + patientToGetId, String.class);
        String responseBody = getPatientResponse.getBody();
        JsonNode createPatientJsonResponse = new ObjectMapper().readTree(responseBody);
        assertEquals(HttpStatus.NOT_FOUND, getPatientResponse.getStatusCode());
        assertEquals("Could not find Patient with id: 9999", createPatientJsonResponse.path("message").asText());
        assertFalse(createPatientJsonResponse.path("timestamp").asText().trim().isEmpty());
    }

    @Test
    @Order(3)
    @DisplayName("Patient list is returned")
    void getAllPatient_shouldReturnPatientList() throws JsonProcessingException {
        ResponseEntity<String> getAllPatientResponse = restTemplate.getForEntity(LOCALHOST + serverPort + URI, String.class);
        String responseBody = getAllPatientResponse.getBody();
        JsonNode getAllPatientJsonResponse = new ObjectMapper().readTree(responseBody);
        assertEquals(HttpStatus.OK, getAllPatientResponse.getStatusCode());
        assertEquals(12, getAllPatientJsonResponse.path("_embedded").path("patientList").size());
    }

    @Test
    @Order(4)
    @DisplayName("Patient can be created")
    void addPatient_whenObjectIsValid_thenReturnTheCreatedObject() {
        Patient patientToCreate = new Patient();
        patientToCreate.setFamily("family_04_test");
        patientToCreate.setGiven("given_04_test");
        patientToCreate.setDob(ACTUALDATE);
        patientToCreate.setSex("T");
        ResponseEntity<Patient> createdPatientResponse = this.restTemplate.postForEntity(
                LOCALHOST + serverPort + URI,
                patientToCreate,
                Patient.class);
        Patient createdPatient = createdPatientResponse.getBody();
        assertEquals(HttpStatus.CREATED, createdPatientResponse.getStatusCode());
        assertEquals(patientToCreate.getFamily(), Objects.requireNonNull(createdPatient).getFamily());
        assertEquals(patientToCreate.getGiven(), Objects.requireNonNull(createdPatient).getGiven());
        assertEquals(patientToCreate.getDob().toString(), Objects.requireNonNull(createdPatient).getDob().toString());
        assertEquals(patientToCreate.getSex(), Objects.requireNonNull(createdPatient).getSex());
    }

    @Test
    @Order(5)
    @DisplayName("Patient can't be created")
    void addPatient_whenObjectContainFieldError_thenReturnBadRequest() throws JsonProcessingException {
        Patient patientToCreate = new Patient();
        patientToCreate.setFamily(null);
        patientToCreate.setGiven(null);
        patientToCreate.setDob(null);
        patientToCreate.setSex(null);
        ResponseEntity<String> createdPatientResponse = this.restTemplate.postForEntity(
                LOCALHOST + serverPort + URI,
                patientToCreate,
                String.class);
        String responseBody = createdPatientResponse.getBody();
        JsonNode createPatientJsonResponse = new ObjectMapper().readTree(responseBody);
        assertEquals(HttpStatus.BAD_REQUEST, createdPatientResponse.getStatusCode());
        assertEquals(4, createPatientJsonResponse.path("fieldErrors").size());
        assertEquals("Validation error", createPatientJsonResponse.path("message").asText());
    }

    @Test
    @Order(6)
    @DisplayName("Patient can be update")
    void updatePatient_whenRequestIsValid_shouldReturnTheModifiedPatient() {
        final int patientToUpdateId = 13;
        Patient patientToUpdate = new Patient();
        patientToUpdate.setFamily("family_06_test");
        patientToUpdate.setGiven("given_06_test");
        patientToUpdate.setDob(ACTUALDATE);
        patientToUpdate.setSex("T");
        HttpEntity<Patient> requestUpdate = new HttpEntity<>(patientToUpdate);
        ResponseEntity<Patient> updatedPatientResponse = this.restTemplate.exchange(
                LOCALHOST + serverPort + URI + patientToUpdateId,
                HttpMethod.PUT,
                requestUpdate,
                Patient.class);
        Patient updatedPatient = updatedPatientResponse.getBody();
        assertEquals(HttpStatus.CREATED, updatedPatientResponse.getStatusCode());
        assertEquals(patientToUpdate.getFamily(), Objects.requireNonNull(updatedPatient).getFamily());
        assertEquals(patientToUpdate.getGiven(), Objects.requireNonNull(updatedPatient).getGiven());
        assertEquals(patientToUpdate.getDob().toString(), Objects.requireNonNull(updatedPatient).getDob().toString());
        assertEquals(patientToUpdate.getSex(), Objects.requireNonNull(updatedPatient).getSex());
    }

    @Test
    @Order(7)
    @DisplayName("Patient can't be update")
    void updatePatient_whenRequestContainFieldErrors_thenShouldReturnedBadRequest() throws JsonProcessingException {
        final int patientToUpdateId = 13;
        Patient patientToUpdate = new Patient();
        patientToUpdate.setFamily(null);
        patientToUpdate.setGiven(null);
        patientToUpdate.setDob(null);
        patientToUpdate.setSex(null);
        HttpEntity<Patient> requestUpdate = new HttpEntity<>(patientToUpdate);
        ResponseEntity<String> updatedPatientResponse = this.restTemplate.exchange(
                LOCALHOST + serverPort + URI + patientToUpdateId,
                HttpMethod.PUT,
                requestUpdate,
                String.class);
        String responseBody = updatedPatientResponse.getBody();
        JsonNode createPatientJsonResponse = new ObjectMapper().readTree(responseBody);
        assertEquals(HttpStatus.BAD_REQUEST, updatedPatientResponse.getStatusCode());
        assertEquals(4, createPatientJsonResponse.path("fieldErrors").size());
        assertEquals("Validation error", createPatientJsonResponse.path("message").asText());
    }

    @Test
    @Order(8)
    @DisplayName("Patient to update doesn't exist")
    void updatePatient_whenPatientDoesNotExist_thenShouldReturnedNotFound() throws JsonProcessingException {
        final int patientToUpdateId = 9999;
        Patient patientToUpdate = new Patient();
        patientToUpdate.setFamily("family_08_test");
        patientToUpdate.setGiven("given_08_test");
        patientToUpdate.setDob(ACTUALDATE);
        patientToUpdate.setSex("T");
        HttpEntity<Patient> requestUpdate = new HttpEntity<>(patientToUpdate);
        ResponseEntity<String> updatedPatientResponse = this.restTemplate.exchange(
                LOCALHOST + serverPort + URI + patientToUpdateId,
                HttpMethod.PUT,
                requestUpdate,
                String.class);
        String responseBody = updatedPatientResponse.getBody();
        JsonNode createPatientJsonResponse = new ObjectMapper().readTree(responseBody);
        assertEquals(HttpStatus.NOT_FOUND, updatedPatientResponse.getStatusCode());
        assertEquals("Could not find Patient with id: 9999", createPatientJsonResponse.path("message").asText());
        assertFalse(createPatientJsonResponse.path("timestamp").asText().trim().isEmpty());
    }

    @Test
    @Order(9)
    @DisplayName("Patient can be delete")
    void deletePatient_whenPatientExist_shouldReturnNoContent() {
        final int patientToDeleteId = 20;
        ResponseEntity<String> deletePatientResponse = restTemplate.exchange(
                LOCALHOST + serverPort + URI + patientToDeleteId,
                HttpMethod.DELETE,
                new HttpEntity<>(null),
                String.class);
        assertEquals(HttpStatus.NO_CONTENT, deletePatientResponse.getStatusCode());
    }

    @Test
    @Order(10)
    @DisplayName("Patient to delete doesn't exist")
    void deletePatient_whenPatientDoesNotExist_shouldReturnNotFound() throws JsonProcessingException {
        final int patientToDeleteId = 9999;
        ResponseEntity<String> deletePatientResponse = restTemplate.exchange(
                LOCALHOST + serverPort + URI + patientToDeleteId,
                HttpMethod.DELETE,
                new HttpEntity<>(null),
                String.class);
        String responseBody = deletePatientResponse.getBody();
        JsonNode deletePatientJsonResponse = new ObjectMapper().readTree(responseBody);
        assertEquals(HttpStatus.NOT_FOUND, deletePatientResponse.getStatusCode());
        assertEquals("Could not find Patient with id: 9999", deletePatientJsonResponse.path("message").asText());
        assertFalse(deletePatientJsonResponse.path("timestamp").asText().trim().isEmpty());
    }
}