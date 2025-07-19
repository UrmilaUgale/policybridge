// MultipleFiles/PatientAdmissionController.java
package com.patientadmission.controller;

import com.patientadmission.model.PatientAdmission;
import com.patientadmission.service.PatientAdmissionService;
// import com.patientadmission.util.EncryptionUtil; // REMOVE THIS IMPORT
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequestMapping
public class PatientAdmissionController {

  private static final Logger logger = LoggerFactory.getLogger(PatientAdmissionController.class);

  private final PatientAdmissionService patientAdmissionService;

  public PatientAdmissionController(PatientAdmissionService patientAdmissionService) {
    this.patientAdmissionService = patientAdmissionService;
  }

  @PostMapping(value = "/admitPatient", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<String> admitPatient(
          @Valid PatientAdmission patientAdmission,
          BindingResult bindingResult,
          @RequestParam(value = "aadharCardFile", required = false) MultipartFile aadharCardFile,
          @RequestParam(value = "doctorPrescriptionFile", required = false) MultipartFile doctorPrescriptionFile,
          @RequestParam(value = "insurancePolicyFile", required = false) MultipartFile insurancePolicyFile) { // Add this line

    if (bindingResult.hasErrors()) {
      logger.warn("Validation failed for patient admission: {}", bindingResult.getAllErrors());
      return ResponseEntity.badRequest().body("Invalid data submitted. Please correct the form.");
    }
    try {
      // Set the status to 'Pending Review' before saving
      patientAdmission.setStatus("Pending Review");

      // Handle Aadhar Card File Upload (NO ENCRYPTION)
      if (aadharCardFile != null && !aadharCardFile.isEmpty()) {
        try {
          patientAdmission.setAadharCardData(aadharCardFile.getBytes()); // Store raw bytes
          patientAdmission.setAadharCardFilename(aadharCardFile.getOriginalFilename());
        } catch (Exception e) {
          logger.error("Error setting Aadhar card file: {}", e.getMessage(), e);
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing Aadhar card file.");
        }
      }

      // Handle Doctor Prescription File Upload (NO ENCRYPTION)
      if (doctorPrescriptionFile != null && !doctorPrescriptionFile.isEmpty()) {
        try {
          patientAdmission.setDoctorPrescriptionData(doctorPrescriptionFile.getBytes()); // Store raw bytes
          patientAdmission.setDoctorPrescriptionFilename(doctorPrescriptionFile.getOriginalFilename());
        } catch (Exception e) {
          logger.error("Error setting doctor prescription file: {}", e.getMessage(), e);
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing doctor prescription file.");
        }
      }

      // Handle Insurance Policy File Upload (NO ENCRYPTION) - Add this block
      if (insurancePolicyFile != null && !insurancePolicyFile.isEmpty()) {
        try {
          patientAdmission.setInsurancePolicyData(insurancePolicyFile.getBytes()); // Store raw bytes
          patientAdmission.setInsurancePolicyFilename(insurancePolicyFile.getOriginalFilename());
        } catch (Exception e) {
          logger.error("Error setting insurance policy file: {}", e.getMessage(), e);
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing insurance policy file.");
        }
      }

      PatientAdmission savedPatientAdmission = patientAdmissionService.savePatientAdmission(patientAdmission);
      logger.info("Patient admission saved successfully for: {}", savedPatientAdmission.getPatientName());
      // Return a success message with the ID and a link to the search page
      String successMessage = "Patient admission saved successfully. Your reference ID is: " + savedPatientAdmission.getId() +
              "<br><a href='/search.html'>Click here to search your record</a>";
      return ResponseEntity.ok(successMessage);
    } catch (Exception e) {
      logger.error("Error saving patient admission: {}", e.getMessage(), e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request.");
    }
  }

  @GetMapping("/searchPatient")
  public ResponseEntity<PatientAdmission> searchPatient(
          @RequestParam(required = false) String aadhar,
          @RequestParam(required = false) String pan,
          @RequestParam(required = false) String phone,
          @RequestParam(required = false) Long id) {

    if ((aadhar == null || aadhar.isEmpty()) &&
            (pan == null || pan.isEmpty()) &&
            (phone == null || phone.isEmpty()) &&
            (id == null)) {
      return ResponseEntity.badRequest().build(); // No search parameters provided
    }

    Optional<PatientAdmission> patientAdmission = patientAdmissionService.findPatientBySearchParameters(aadhar, pan, phone, id);

    return patientAdmission.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
  }

  // New endpoint to download files (NO DECRYPTION)
  @GetMapping("/downloadFile/{patientId}/{fileType}")
  public ResponseEntity<byte[]> downloadFile(@PathVariable Long patientId, @PathVariable String fileType) {
    Optional<PatientAdmission> patientOptional = patientAdmissionService.findById(patientId);
    if (patientOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    PatientAdmission patient = patientOptional.get();
    byte[] fileData = null;
    String fileName = null;
    String contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE; // Default content type

    try {
      if ("aadhar".equalsIgnoreCase(fileType) && patient.getAadharCardData() != null) {
        fileData = patient.getAadharCardData(); // Retrieve raw bytes
        fileName = patient.getAadharCardFilename();
        // Try to determine content type based on filename extension
        if (fileName != null && fileName.contains(".")) {
          String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
          switch (extension) {
            case "pdf": contentType = MediaType.APPLICATION_PDF_VALUE; break;
            case "jpg": case "jpeg": contentType = MediaType.IMAGE_JPEG_VALUE; break;
            case "png": contentType = MediaType.IMAGE_PNG_VALUE; break;
            // Add more types as needed
          }
        }
      } else if ("prescription".equalsIgnoreCase(fileType) && patient.getDoctorPrescriptionData() != null) {
        fileData = patient.getDoctorPrescriptionData(); // Retrieve raw bytes
        fileName = patient.getDoctorPrescriptionFilename();
        if (fileName != null && fileName.contains(".")) {
          String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
          switch (extension) {
            case "pdf": contentType = MediaType.APPLICATION_PDF_VALUE; break;
            case "jpg": case "jpeg": contentType = MediaType.IMAGE_JPEG_VALUE; break;
            case "png": contentType = MediaType.IMAGE_PNG_VALUE; break;
            // Add more types as needed
          }
        }
      } else if ("insurance".equalsIgnoreCase(fileType) && patient.getInsurancePolicyData() != null) { // Add this block
        fileData = patient.getInsurancePolicyData();
        fileName = patient.getInsurancePolicyFilename();
        if (fileName != null && fileName.contains(".")) {
          String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
          switch (extension) {
            case "pdf": contentType = MediaType.APPLICATION_PDF_VALUE; break;
            case "jpg": case "jpeg": contentType = MediaType.IMAGE_JPEG_VALUE; break;
            case "png": contentType = MediaType.IMAGE_PNG_VALUE; break;
            // Add more types as needed
          }
        }
      }
      else {
        return ResponseEntity.badRequest().body(null); // Invalid file type or no data
      }
    } catch (Exception e) { // Catch any potential IOException from getBytes() if not already handled
      logger.error("Error retrieving file data: {}", e.getMessage(), e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }


    if (fileData == null || fileData.length == 0) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
            .body(fileData);
  }

  // New DTO for incoming JSON data
  static class InsuranceCommentRequest {
    private String aadhar;
    private String phone;
    private String comment;

    // Getters and Setters
    public String getAadhar() { return aadhar; }
    public void setAadhar(String aadhar) { this.aadhar = aadhar; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
  }

  @PostMapping("/updateInsuranceComment")
  public ResponseEntity<String> updateInsuranceComment(@RequestBody InsuranceCommentRequest request) {
    if ((request.getAadhar() == null || request.getAadhar().isEmpty()) &&
            (request.getPhone() == null || request.getPhone().isEmpty())) {
      return ResponseEntity.badRequest().body("Aadhar number or Phone number is required.");
    }
    if (request.getComment() == null || request.getComment().isEmpty()) {
      return ResponseEntity.badRequest().body("Comment cannot be empty.");
    }

    Optional<PatientAdmission> patientOptional = patientAdmissionService.findPatientBySearchParameters(
            request.getAadhar(), null, request.getPhone(), null); // Search by Aadhar or Phone

    if (patientOptional.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient not found with provided Aadhar or Phone number.");
    }

    try {
      PatientAdmission patient = patientOptional.get();
      patient.setInsuranceCompanyComment(request.getComment());
      patientAdmissionService.savePatientAdmission(patient);
      logger.info("Insurance comment updated for patient ID: {}", patient.getId());
      return ResponseEntity.ok("Insurance comment updated successfully for patient " + patient.getPatientName() + ".");
    } catch (Exception e) {
      logger.error("Error updating insurance comment: {}", e.getMessage(), e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating insurance comment: " + e.getMessage());
    }
  }
}
