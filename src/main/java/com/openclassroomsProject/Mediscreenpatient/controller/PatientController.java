package com.openclassroomsProject.Mediscreenpatient.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import com.openclassroomsProject.Mediscreenpatient.exceptions.PatientNotFoundException;
import com.openclassroomsProject.Mediscreenpatient.model.Patient;
import com.openclassroomsProject.Mediscreenpatient.service.IPatientService;
import com.openclassroomsProject.Mediscreenpatient.utils.PatientModelAssembler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * REST Controller for Patient resource
 *
 * @author jonathan GOUVEIA
 * @version 1.0
 */
@RestController
public class PatientController {
    private final Logger LOGGER = LoggerFactory.getLogger(PatientController.class);

    @Autowired
    IPatientService patientService;

    @Autowired
    PatientModelAssembler patientModelAssembler;

    /**
     * GET Request, get Patient by id.
     *
     * @param id Identifier of the wanted patient.
     * @return HTTP Response with status code 200 and the requested resource.
     */
    @GetMapping("/patients/{id}")
    public ResponseEntity<EntityModel<Patient>> getPatientById(@PathVariable int id) {
        LOGGER.info("[CONTROLLER] Request URL: GET /patients/{id}");
        Patient patient = patientService.getPatientById(id)
                .orElseThrow(() -> new PatientNotFoundException(id));
        return ResponseEntity.ok(patientModelAssembler.toModel(patient));
    }

    /**
     * GET Request, get all Patient.
     *
     * @return HTTP Response with status code 200 and a collection of all patients found in the database.
     */
    @GetMapping("/patients")
    public ResponseEntity<CollectionModel<EntityModel<Patient>>> getAllPatient() {
        LOGGER.info("[CONTROLLER] Request URL: GET /patients");
        List<EntityModel<Patient>> patientsList = patientService.getAllPatients().stream()
                .map(patientModelAssembler::toModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(patientsList,
                linkTo(methodOn(PatientController.class).getAllPatient()).withSelfRel()));
    }

    /**
     * POST Request, create a new a Patient.
     *
     * @param newPatient The patient to create.
     * @return HTTP Response wit status code 201 and the created resource.
     */
    @PostMapping("/patients")
    public ResponseEntity<EntityModel<Patient>> addPatient(@Valid @RequestBody Patient newPatient) {
        LOGGER.info("[CONTROLLER] Request URL: POST /patients");
        EntityModel<Patient> patientEntityModel = patientModelAssembler
                .toModel(patientService.addPatient(newPatient));
        return ResponseEntity
                .created(patientEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(patientEntityModel);
    }

    /**
     * PUT Request Modify an existing Patient.
     *
     * @param newPatient The Patient to modify.
     * @param id         Identifier of the patient to modify.
     * @return HTTP response with status code 201 and the modified resource.
     */
    @PutMapping("/patients/{id}")
    public ResponseEntity<EntityModel<Patient>> updatePatient(@Valid @RequestBody Patient newPatient, @PathVariable int id) {
        LOGGER.info("[CONTROLLER] Request URL: PUT /patients/{id}");
        Patient actualPatient = patientService.getPatientById(id)
                .orElseThrow(() -> new PatientNotFoundException(id));
        Patient updatedPatient = patientService.updatePatient(actualPatient, newPatient);
        EntityModel<Patient> patientEntityModel = patientModelAssembler.toModel(updatedPatient);
        return ResponseEntity
                .created(patientEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(patientEntityModel);
    }

    /**
     * DELETE Request, delete a Patient by id.
     *
     * @param id Identifier of the patient to delete.
     * @return HTTP response with status code 204.
     */
    @DeleteMapping("/patients/{id}")
    public ResponseEntity<?> deletePatient(@PathVariable int id) {
        LOGGER.info("[CONTROLLER] Request URL: DELETE /patients/{id}");
        patientService.getPatientById(id)
                .orElseThrow(() -> new PatientNotFoundException(id));
        patientService.deletePatientById(id);
        return ResponseEntity.noContent().build();
    }
}