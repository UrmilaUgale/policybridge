// MultipleFiles/PatientAdmissionRepository.java
package com.patientadmission.repository;

import com.patientadmission.model.PatientAdmission;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PatientAdmissionRepository extends JpaRepository<PatientAdmission, Long> {
    Optional<PatientAdmission> findByAadhar(String aadhar);
    Optional<PatientAdmission> findByPan(String pan);
    Optional<PatientAdmission> findByPhone(String phone);
    Optional<PatientAdmission> findById(Long id); // Already exists, but good to explicitly list

    // New method to find by Aadhar OR Phone
    Optional<PatientAdmission> findByAadharOrPhone(String aadhar, String phone);
}
