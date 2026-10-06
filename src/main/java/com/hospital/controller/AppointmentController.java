package com.hospital.controller;

import com.hospital.model.Appointment;
import com.hospital.service.AppointmentService;
import com.hospital.service.DoctorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final DoctorService doctorService;

    public AppointmentController(AppointmentService appointmentService,
                                  DoctorService doctorService) {
        this.appointmentService = appointmentService;
        this.doctorService = doctorService;
    }

    @GetMapping("/appointments")
    public String showAppointments(Model model) {
        model.addAttribute("appointments",
                appointmentService.getAllAppointments());

        return "appointments";
    }

    @GetMapping("/appointments/add")
    public String showAddAppointmentForm(Model model) {
        model.addAttribute("appointment", new Appointment());

        // Add Doctor se saved doctors appointment form mein bhejega
        model.addAttribute("doctors", doctorService.getAllDoctors());

        return "add-appointment";
    }

    @PostMapping("/appointments/add")
    public String saveAppointment(@ModelAttribute Appointment appointment) {
        appointmentService.bookAppointment(appointment);

        return "redirect:/appointments";
    }

    @GetMapping("/appointments/cancel/{id}")
    public String cancelAppointment(@PathVariable Long id) {
        appointmentService.cancelAppointment(id);

        return "redirect:/appointments";
    }
}