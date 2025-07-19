// MultipleFiles/AdminController.java
package com.patientadmission.controller;

import com.patientadmission.model.PatientAdmission;
import com.patientadmission.service.PatientAdmissionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final PatientAdmissionService patientAdmissionService;

    // Define possible statuses
    private static final String[] STATUS_OPTIONS = {
            "Approved with Room/Bed",
            "Declined",
            "Approved, meet FrontDesk",
            "Approved, sent to Insurance Company",
            "Approved, waiting for Room Allocation"
    };

    public AdminController(PatientAdmissionService patientAdmissionService) {
        this.patientAdmissionService = patientAdmissionService;
    }

    @GetMapping("/login")
    public String adminLogin() {
        return "admin_login";
    }

    @GetMapping("/dashboard")
    public String viewAllPatients(Model model,
                                  @RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PatientAdmission> patientPage = patientAdmissionService.findAllPatients(pageable);
        model.addAttribute("patientPage", patientPage);
        return "admin_dashboard";
    }

    @GetMapping("/edit/{id}")
    public String editPatientForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<PatientAdmission> patientOptional = patientAdmissionService.findById(id);
        if (patientOptional.isPresent()) {
            model.addAttribute("patient", patientOptional.get());
            model.addAttribute("statusOptions", STATUS_OPTIONS);
            return "admin_edit_patient";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Patient not found.");
            return "redirect:/admin/dashboard";
        }
    }

    @PostMapping("/save")
    public String savePatient(@ModelAttribute PatientAdmission patient, RedirectAttributes redirectAttributes) {
        try {
            // Retrieve existing patient to update specific fields
            Optional<PatientAdmission> existingPatientOptional = patientAdmissionService.findById(patient.getId());
            if (existingPatientOptional.isEmpty()) {
                redirectAttributes.addFlashAttribute("errorMessage", "Patient not found for update.");
                return "redirect:/admin/dashboard";
            }

            PatientAdmission existingPatient = existingPatientOptional.get();

            // Update only the fields that admin is allowed to edit
            existingPatient.setPatientName(patient.getPatientName());
            existingPatient.setSurname(patient.getSurname());
            existingPatient.setFirstName(patient.getFirstName());
            existingPatient.setHusbandFatherName(patient.getHusbandFatherName());
            existingPatient.setPatientAddress(patient.getPatientAddress());
            existingPatient.setAge(patient.getAge());
            existingPatient.setDateOfBirth(patient.getDateOfBirth());
            existingPatient.setPhone(patient.getPhone());
            existingPatient.setOccupation(patient.getOccupation());
            existingPatient.setNameOfOrganization(patient.getNameOfOrganization());
            existingPatient.setDesignation(patient.getDesignation());
            existingPatient.setPatientCategory(patient.getPatientCategory());
            existingPatient.setNameOfDoctor(patient.getNameOfDoctor());
            existingPatient.setGender(patient.getGender());
            existingPatient.setRoomPreference(patient.getRoomPreference());
            existingPatient.setOpdRegistrationNo(patient.getOpdRegistrationNo());
            existingPatient.setRelativesNameAddress(patient.getRelativesNameAddress());
            existingPatient.setRelativesPhone(patient.getRelativesPhone());
            existingPatient.setPan(patient.getPan());
            existingPatient.setAadhar(patient.getAadhar());
            existingPatient.setDiagnosis(patient.getDiagnosis());
            existingPatient.setMedicalInsurance(patient.getMedicalInsurance());
            existingPatient.setInsuranceCompany(patient.getInsuranceCompany());
            existingPatient.setPolicyNo(patient.getPolicyNo());
            existingPatient.setMrMrs(patient.getMrMrs());
            existingPatient.setRelativeOfPatient(patient.getRelativeOfPatient());
            existingPatient.setBedNo(patient.getBedNo());
            existingPatient.setUndertakingAmount(patient.getUndertakingAmount());
            existingPatient.setPaymentDueDate(patient.getPaymentDueDate());
            existingPatient.setNameUndertaking(patient.getNameUndertaking());
            existingPatient.setSignatureUndertaking(patient.getSignatureUndertaking());
            existingPatient.setDateUndertaking(patient.getDateUndertaking());
            existingPatient.setRelationUndertaking(patient.getRelationUndertaking());
            existingPatient.setResponsibleName1(patient.getResponsibleName1());
            existingPatient.setResponsibleSign1(patient.getResponsibleSign1());
            existingPatient.setResponsibleMobile1(patient.getResponsibleMobile1());
            existingPatient.setResponsibleEmail1(patient.getResponsibleEmail1());
            existingPatient.setResponsibleName2(patient.getResponsibleName2());
            existingPatient.setResponsibleSign2(patient.getResponsibleSign2());
            existingPatient.setResponsibleMobile2(patient.getResponsibleMobile2());
            existingPatient.setResponsibleEmail2(patient.getResponsibleEmail2());
            existingPatient.setTotalAmountPaid(patient.getTotalAmountPaid());
            existingPatient.setNoDepositReason(patient.getNoDepositReason());
            existingPatient.setNameOfCounsellor(patient.getNameOfCounsellor());
            existingPatient.setSignCounsellor(patient.getSignCounsellor());

            // Admin specific updates
            existingPatient.setAdminComments(patient.getAdminComments()); // Update comments
            existingPatient.setStatus(patient.getStatus()); // Update status

            // In AdminController.java, savePatient method
            existingPatient.setAdminComments(patient.getAdminComments()); // Update comments
            existingPatient.setStatus(patient.getStatus()); // Update status
            existingPatient.setInsuranceCompanyComment(patient.getInsuranceCompanyComment()); // Add this line


            patientAdmissionService.savePatientAdmission(existingPatient);
            redirectAttributes.addFlashAttribute("successMessage", "Patient record updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating patient record: " + e.getMessage());
        }
        return "redirect:/admin/dashboard";
    }
}
