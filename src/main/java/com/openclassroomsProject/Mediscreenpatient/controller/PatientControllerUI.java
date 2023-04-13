package com.openclassroomsProject.Mediscreenpatient.controller;

import com.openclassroomsProject.Mediscreenpatient.exceptions.PatientNotFoundException;
import com.openclassroomsProject.Mediscreenpatient.model.Patient;
import com.openclassroomsProject.Mediscreenpatient.service.IPatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
@Controller
public class PatientControllerUI {

    private final Logger LOGGER = LoggerFactory.getLogger(PatientControllerUI.class);

    @Autowired
    IPatientService patientService;

    @GetMapping("/patients/{id}")
    public String getPatientById(@PathVariable int id, Model model) {
        Patient patient = patientService.getPatientById(id)
                .orElseThrow(() -> new PatientNotFoundException(id));
        model.addAttribute("patient", patient);
        return "patient";
    }
}