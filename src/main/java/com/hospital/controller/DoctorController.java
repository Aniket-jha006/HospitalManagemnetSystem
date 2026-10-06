package com.hospital.controller;

import com.hospital.model.Doctor;
import com.hospital.service.DoctorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping("/doctors")
    public String showDoctors(Model model) {
        model.addAttribute("doctors", doctorService.getAllDoctors());
        return "doctors";
    }

    @GetMapping("/doctors/add")
    public String showAddDoctorForm(Model model) {
        model.addAttribute("doctor", new Doctor());
        return "add-doctor";
    }

    @PostMapping("/doctors/add")
    public String saveDoctor(@ModelAttribute Doctor doctor) {

        String name = doctor.getName();

        if (name != null && !name.trim().isEmpty()) {

            name = name.trim();

            // Automatically add Dr. if not already present
            if (!name.toLowerCase().startsWith("dr.")) {
                name = "Dr. " + name;
            }

            doctor.setName(name);
        }

        doctorService.addDoctor(doctor);

        return "redirect:/doctors";
    }

    @GetMapping("/doctors/delete/{id}")
    public String deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return "redirect:/doctors";
    }
}