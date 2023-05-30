package com.openclassroomsProject.Mediscreenpatient.controller;

import com.openclassroomsProject.Mediscreenpatient.exceptions.PatientNotFoundException;
import com.openclassroomsProject.Mediscreenpatient.model.Patient;
import com.openclassroomsProject.Mediscreenpatient.service.IPatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

/**
 * REST Controller for Patient resource
 *
 * @author jonathan GOUVEIA
 * @version 1.0
 */
@RestController
public class PatientControllerApi {
    private final Logger LOGGER = LoggerFactory.getLogger(PatientControllerApi.class);

    @Autowired
    IPatientService patientService;

    /**
     * GET Request, get Patient by id.
     *
     * @param id Identifier of the wanted patient.
     * @return HTTP Response with status code 200 and the requested resource.
     */
    @GetMapping("/api/patients/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable int id) {
        LOGGER.info("[CONTROLLER] Request URL: GET /api/patients/{id}");
        Patient patient = patientService.getPatientById(id)
                .orElseThrow(() -> new PatientNotFoundException(id));
        return ResponseEntity.ok(patient);
    }

    /**
     * GET Request, get all Patient.
     *
     * @return HTTP Response with status code 200 and a collection of all patients found in the database.
     */
    @GetMapping("/api/patients")
    public ResponseEntity<List<Patient>> getAllPatient() {
        LOGGER.info("[CONTROLLER] Request URL: GET /api/patients");
        List<Patient> patientsList = patientService.getAllPatients();
        return ResponseEntity.ok(patientsList);
    }

    /**
     * POST Request, create a new a Patient.
     *
     * @param newPatient The patient to create.
     * @return HTTP Response with status code 201 and the created resource.
     */
    @PostMapping("/api/patients")
    public ResponseEntity<Patient> addPatient(@Valid @RequestBody Patient newPatient) {
        LOGGER.info("[CONTROLLER] Request URL: POST /api/patients [PARAM]-> newPatient : " + newPatient);
        Patient patientCreated = patientService.addPatient(newPatient);
        return ResponseEntity.status(HttpStatus.CREATED).body(patientCreated);
    }

    /**
     * PUT Request Modify an existing Patient.
     *
     * @param newPatient The Patient to modify.
     * @param id         Identifier of the patient to modify.
     * @return HTTP response with status code 201 and the modified resource.
     */
    @PutMapping("/api/patients/{id}")
    public ResponseEntity<Patient> updatePatient(@Valid @RequestBody Patient newPatient, @PathVariable int id) {
        LOGGER.info("[CONTROLLER] Request URL: PUT /api/patients/{id}");
        Patient actualPatient = patientService.getPatientById(id)
                .orElseThrow(() -> new PatientNotFoundException(id));
        Patient updatedPatient = patientService.updatePatient(actualPatient, newPatient);
        return ResponseEntity.status(HttpStatus.OK).body(updatedPatient);
    }

    /**
     * DELETE Request, delete a Patient by id.
     *
     * @param id Identifier of the patient to delete.
     * @return HTTP response with status code 204.
     */
    @DeleteMapping("/api/patients/{id}")
    public ResponseEntity<?> deletePatient(@PathVariable int id) {
        LOGGER.info("[CONTROLLER] Request URL: DELETE /api/patients/{id}");
        patientService.getPatientById(id)
                .orElseThrow(() -> new PatientNotFoundException(id));
        patientService.deletePatientById(id);
        return ResponseEntity.noContent().build();
    }
}