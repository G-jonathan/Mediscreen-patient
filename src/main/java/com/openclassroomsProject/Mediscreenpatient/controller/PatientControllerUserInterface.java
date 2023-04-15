package com.openclassroomsProject.Mediscreenpatient.controller;

import com.openclassroomsProject.Mediscreenpatient.constants.ExceptionConstants;
import com.openclassroomsProject.Mediscreenpatient.exceptions.PatientNotFoundException;
import com.openclassroomsProject.Mediscreenpatient.model.Patient;
import com.openclassroomsProject.Mediscreenpatient.service.IPatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

/**
 * MVC Controller for user interface
 *
 * @author jonathan GOUVEIA
 * @version 1.0
 */
@Controller
public class PatientControllerUserInterface {
    private final Logger LOGGER = LoggerFactory.getLogger(PatientControllerUserInterface.class);
    private static final String LOGGER_PATIENT_NOT_FOUND_ERROR = "PatientNotFoundException was thrown in PatientControllerUserInterface.";

    @Autowired
    IPatientService patientService;

    /**
     * Displays the home page for the application.
     *
     * @param model The view template for the homePage.html page.
     * @return the name of the view to display (homePage.html).
     */
    @GetMapping(value = {"/homePage", "/"})
    public String showHomePage(Model model) {
        LOGGER.info("[CONTROLLER-UI] Request URL: GET / or GET /homePage");
        return "homePage";
    }

    /**
     * GET all patients and displays them in the patients.html page
     *
     * @param model The view template for the patients.html page.
     * @return the name of the view to display (patients.html).
     */
    @GetMapping("/patients")
    public String getAllPatient(Model model) {
        LOGGER.info("[CONTROLLER-UI] Request URL: GET /patients");
        List<Patient> patientList = patientService.getAllPatients();
        model.addAttribute("patients", patientList);
        return "patients";
    }

    /**
     * GET a patient by his ID and displays it in the patient.html page.
     *
     * @param model The view template for the patient.html page.
     * @param id    Identifier of the wanted patient.
     * @return the name of the view to display (patient.html).
     * @throws PatientNotFoundException if no patient is found with the ID
     */
    @GetMapping("/patients/{id}")
    public String getPatientById(@PathVariable int id, Model model) {
        LOGGER.info("[CONTROLLER-UI] Request URL: GET /patients/{id}");
        try {
            Patient patient = patientService.getPatientById(id)
                    .orElseThrow(() -> new PatientNotFoundException(id));
            model.addAttribute("patient", patient);
            return "patient";
        } catch (PatientNotFoundException exception) {
            LOGGER.error(LOGGER_PATIENT_NOT_FOUND_ERROR + "getPatientById");
            model.addAttribute("errorMessage", exception.getMessage());
            return "error";
        }
    }

    /**
     * This method displays the add new patient page.
     *
     * @param model the view template for the addPatient.html page
     * @return the name of the view to display (addPatient.html).
     */
    @GetMapping("/addPatient")
    public String showAddPatientPage(Model model) {
        LOGGER.info("[CONTROLLER-UI] Request URL: GET /addPatient");
        model.addAttribute("patient", new Patient());
        return "addPatient";
    }

    /**
     * Adds a new patient to the database
     *
     * @param newPatient    the patient object to add
     * @param bindingResult the binding result object to handle validation errors
     * @param model         the model object to pass data to the view
     * @return the view name to display after adding the patient or the error page
     */
    @PostMapping("/patients")
    public String addPatient(@ModelAttribute("addPatient") @Valid Patient newPatient, BindingResult bindingResult, Model model) {
        LOGGER.info("[CONTROLLER-UI] Request URL: POST /patients");
        if (bindingResult.hasErrors()) {
            return "addPatient";
        }
        try {
            Patient patientSave = patientService.addPatient(newPatient);
            return "redirect:/patients/" + patientSave.getId();
        } catch (DataIntegrityViolationException exception) {
            LOGGER.error("DataIntegrityViolationException thrown in PatientControllerUserInterface.addPatient");
            String message = ExceptionConstants.PATIENT_ALREADY_EXIST;
            model.addAttribute("errorMessage", message);
            return "error";
        }
    }

    /**
     * This method handles a POST request to delete a patient with the specified ID.
     * The method checks that the HTTP request method is POST and that the parameter "_method" is set to "DELETE".
     *
     * @param id    the id of the patient to delete
     * @param model the model used to store data for the view
     * @return the name of the view to display (patient.html or error.html).
     */
    @PostMapping(value = "/patients/{id}", params = "_method=DELETE")
    public String deletePatient(@PathVariable int id, Model model) {
        LOGGER.info("[CONTROLLER-UI] Request URL: POST /patients/{id} with _method=DELETE");
        try {
            patientService.deletePatientById(id);
            return "redirect:/patients/";
        } catch (PatientNotFoundException exception) {
            LOGGER.error(LOGGER_PATIENT_NOT_FOUND_ERROR + "deletePatient");
            String message = exception.getMessage();
            model.addAttribute("errorMessage", message);
            return "error";
        }
    }

    /**
     * Displays the form to update a patient with the given ID
     *
     * @param id    the ID of the patient to update
     * @param model the model to be used by the view, contain the patient object to update
     * @return the view name to display
     */
    @GetMapping("/updatePatient/{id}")
    public String showEditPatientForm(@PathVariable int id, Model model) {
        LOGGER.info("[CONTROLLER-UI] Request URL: GET /updatePatient");
        try {
            Patient patient = patientService.getPatientById(id)
                    .orElseThrow(() -> new PatientNotFoundException(id));
            model.addAttribute("patient", patient);
            return "updatePatient";
        } catch (PatientNotFoundException exception) {
            LOGGER.error(LOGGER_PATIENT_NOT_FOUND_ERROR + "showEditPatientForm");
            model.addAttribute("errorMessage", exception.getMessage());
            return "error";
        }
    }

    @PostMapping(value = "/patients/{id}", params = "_method=PUT")
    public String updatePatient(@PathVariable("id") int id, @Valid Patient updatedPatient,
                                BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        LOGGER.info("[CONTROLLER-UI] Request URL: POST /patients/{id} with _method=PUT");
        if (bindingResult.hasErrors()) {
            LOGGER.error("bindingResult error in PatientControllerUserInterface.updatePatient");
            model.addAttribute("bindingResult", bindingResult);
            model.addAttribute("patient", updatedPatient);
            return "updatePatient";
        }
        try {
            Patient actualPatient = patientService.getPatientById(id)
                    .orElseThrow(() -> new PatientNotFoundException(id));
            patientService.updatePatient(actualPatient, updatedPatient);
            return "redirect:/patients/" + id;
        } catch (PatientNotFoundException exception) {
            LOGGER.error(LOGGER_PATIENT_NOT_FOUND_ERROR + "updatePatient");
            model.addAttribute("errorMessage", exception.getMessage());
            return "error";
        }
    }
}