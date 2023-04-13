package com.openclassroomsProject.Mediscreenpatient.controller;

import com.openclassroomsProject.Mediscreenpatient.exceptions.PatientNotFoundException;
import com.openclassroomsProject.Mediscreenpatient.model.Patient;
import com.openclassroomsProject.Mediscreenpatient.service.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import javax.validation.Valid;

/**
 * MVC Controller for user interface
 *
 * @author jonathan GOUVEIA
 * @version 1.0
 */
@Controller
public class PatientControllerUserInterface {

    @Autowired
    public IPatientService patientService;

    @GetMapping("/index")
    public String showPatientList(Model model) {
        model.addAttribute("patients", patientService.getAllPatients());
        return "index";
    }

    @GetMapping("/patient/{id}")
    public String getPatientById(@PathVariable("id") int id, Model model) {
        Patient patient = patientService.getPatientById(id)
                .orElseThrow(() -> new PatientNotFoundException(id));
        model.addAttribute("patient", patient);
        return "patient";
    }

    @GetMapping("/add")
    public String showAddUserPage(Patient patient) {
        return "add-patient";
    }

    @PostMapping("/validate")
    public String addPatient(@Valid Patient newPatient, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "add-patient";
        }
        Patient patientSave = patientService.addPatient(newPatient);
        return "redirect:/patient/" + patientSave.getId();
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        Patient patient = patientService.getPatientById(id)
                .orElseThrow(() -> new PatientNotFoundException(id));
        model.addAttribute("patient", patient);
        return "update-patient";
    }

    @PostMapping("/update/{id}")
    public String updatePatient(@PathVariable("id") int id, @Valid Patient patient, Model model, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            patient.setId(id);
            return "update-patient";
        }
        Patient actualPatient = patientService.getPatientById(id)
                .orElseThrow(() -> new PatientNotFoundException(id));
        patientService.updatePatient(actualPatient, patient);
        return "redirect:/patient/" + id;
    }

    @GetMapping("/delete/{id}")
    public String deletePatient(@PathVariable int id, Model model) {
        Patient patient = patientService.getPatientById(id)
                .orElseThrow(() -> new PatientNotFoundException(id));
        patientService.deletePatientById(id);
        return "redirect:/index";
    }
}