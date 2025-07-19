// MultipleFiles/PatientAdmissionService.java
package com.patientadmission.service;

import com.patientadmission.model.PatientAdmission;
import com.patientadmission.repository.PatientAdmissionRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@Transactional
public class PatientAdmissionService {

  private final PatientAdmissionRepository patientAdmissionRepository;

  public PatientAdmissionService(PatientAdmissionRepository patientAdmissionRepository) {
    this.patientAdmissionRepository = patientAdmissionRepository;
  }

  public PatientAdmission savePatientAdmission(PatientAdmission patientAdmission) {
    return patientAdmissionRepository.save(patientAdmission);
  }

  public Optional<PatientAdmission> findPatientBySearchParameters(String aadhar, String pan, String phone, Long id) {
    if (id != null) {
      return patientAdmissionRepository.findById(id);
    }
    if (aadhar != null && !aadhar.isEmpty() && phone != null && !phone.isEmpty()) {
      // If both Aadhar and Phone are provided, search by both
      return patientAdmissionRepository.findByAadharOrPhone(aadhar, phone);
    }
    if (aadhar != null && !aadhar.isEmpty()) {
      return patientAdmissionRepository.findByAadhar(aadhar);
    }
    if (pan != null && !pan.isEmpty()) {
      return patientAdmissionRepository.findByPan(pan);
    }
    if (phone != null && !phone.isEmpty()) {
      return patientAdmissionRepository.findByPhone(phone);
    }
    return Optional.empty();
  }

  // New method to find by ID for file download and admin edit
  public Optional<PatientAdmission> findById(Long id) {
    return patientAdmissionRepository.findById(id);
  }

  // New method for admin to view all patients with pagination
  public Page<PatientAdmission> findAllPatients(Pageable pageable) {
    return patientAdmissionRepository.findAll(pageable);
  }
}
