package com.openclassroomsProject.Mediscreenpatient.utils;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import com.openclassroomsProject.Mediscreenpatient.controller.PatientController;
import com.openclassroomsProject.Mediscreenpatient.model.Patient;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

/**
 * Implementation of Spring HATEOAS’s interface
 *
 * @author jonathan GOUVEIA
 * @version 1.0
 */
@Component
public class PatientModelAssembler implements RepresentationModelAssembler<Patient, EntityModel<Patient>> {

    /**
     * Convert Patient objects to EntityModel<Patient> objects.
     *
     * @param patient Patient object.
     * @return EntityModel<Patient> object.
     */
    @Override
    public EntityModel<Patient> toModel(Patient patient) {
        return EntityModel.of(patient,
                linkTo(methodOn(PatientController.class).getPatientById(patient.getId())).withSelfRel(),
                linkTo(methodOn(PatientController.class).getAllPatient()).withRel("patients"));
    }
}