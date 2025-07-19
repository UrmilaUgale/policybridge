// MultipleFiles/PatientAdmission.java
package com.patientadmission.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "patient_admission")
public class PatientAdmission {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "referred_by_dr")
  private String referredByDr;

  @Column(name = "patient_type")
  private String patientType;

  @Size(max = 255)
  @Column(name = "patient_name")
  private String patientName;

  @Size(max = 255)
  private String surname;

  @Size(max = 255)
  @Column(name = "first_name")
  private String firstName;

  @Size(max = 255)
  @Column(name = "husband_father_name")
  private String husbandFatherName;

  @Size(max = 500)
  @Column(name = "patient_address")
  private String patientAddress;

  @Min(0)
  @Max(150)
  private Integer age;

  @Column(name = "date_of_birth")
  private LocalDate dateOfBirth;

  @Size(max = 20)
  private String phone;

  @Size(max = 255)
  private String occupation;

  @Size(max = 255)
  @Column(name = "name_of_organization")
  private String nameOfOrganization;

  @Size(max = 255)
  private String designation;

  @Column(name = "patient_category")
  private String patientCategory;

  @Size(max = 255)
  @Column(name = "name_of_doctor")
  private String nameOfDoctor;

  private String gender;

  @Column(name = "room_preference")
  private String roomPreference;

  @Size(max = 255)
  @Column(name = "opd_registration_no")
  private String opdRegistrationNo;

  @Size(max = 500)
  @Column(name = "relatives_name_address")
  private String relativesNameAddress;

  @Size(max = 20)
  @Column(name = "relatives_phone")
  private String relativesPhone;

  @Pattern(regexp = "[A-Z]{5}[0-9]{4}[A-Z]{1}")
  @Size(min = 10, max = 10)
  private String pan;

  @Pattern(regexp = "\\d{12}")
  @Size(min = 12, max = 12)
  private String aadhar;

  @Size(max = 255)
  private String diagnosis;

  @Column(name = "medical_insurance")
  private String medicalInsurance;

  @Size(max = 255)
  @Column(name = "insurance_company")
  private String insuranceCompany;

  @Size(max = 255)
  @Column(name = "policy_no")
  private String policyNo;

  @Size(max = 255)
  @Column(name = "mr_mrs")
  private String mrMrs;

  @Size(max = 255)
  @Column(name = "relative_of_patient")
  private String relativeOfPatient;

  @Size(max = 50)
  @Column(name = "bed_no")
  private String bedNo;

  @Column(name = "undertaking_amount", precision = 16, scale = 2)
  private BigDecimal undertakingAmount;

  @Column(name = "payment_due_date")
  private LocalDate paymentDueDate;

  @Size(max = 255)
  @Column(name = "name_undertaking")
  private String nameUndertaking;

  @Size(max = 255)
  @Column(name = "signature_undertaking")
  private String signatureUndertaking;

  @Column(name = "date_undertaking")
  private LocalDate dateUndertaking;

  @Size(max = 255)
  @Column(name = "relation_undertaking")
  private String relationUndertaking;

  @Size(max = 255)
  @Column(name = "responsible_name_1")
  private String responsibleName1;

  @Size(max = 255)
  @Column(name = "responsible_sign_1")
  private String responsibleSign1;

  @Size(max = 20)
  @Column(name = "responsible_mobile_1")
  private String responsibleMobile1;

  @Email
  @Size(max = 255)
  @Column(name = "responsible_email_1")
  private String responsibleEmail1;

  @Size(max = 255)
  @Column(name = "responsible_name_2")
  private String responsibleName2;

  @Size(max = 255)
  @Column(name = "responsible_sign_2")
  private String responsibleSign2;

  @Size(max = 20)
  @Column(name = "responsible_mobile_2")
  private String responsibleMobile2;

  @Email
  @Size(max = 255)
  @Column(name = "responsible_email_2")
  private String responsibleEmail2;

  @Column(name = "total_amount_paid", precision = 16, scale = 2)
  private BigDecimal totalAmountPaid;

  @Size(max = 500)
  @Column(name = "no_deposit_reason")
  private String noDepositReason;

  @Size(max = 255)
  @Column(name = "name_of_counsellor")
  private String nameOfCounsellor;

  @Size(max = 255)
  @Column(name = "sign_counsellor")
  private String signCounsellor;

  @Column(name = "status")
  private String status;

  @Column(name = "admin_comments", length = 1000)
  private String adminComments;

  // --- New field for Insurance Company Comments ---
  @Column(name = "insurance_company_comment", length = 1000) // Adjust length as needed
  private String insuranceCompanyComment;

  // --- New fields for file uploads ---
  @Lob // Large Object for binary data
  @Column(name = "aadhar_card_data", columnDefinition="LONGBLOB") // Use LONGBLOB for potentially large files
  private byte[] aadharCardData;

  @Column(name = "aadhar_card_filename")
  private String aadharCardFilename;

  @Lob
  @Column(name = "doctor_prescription_data", columnDefinition="LONGBLOB")
  private byte[] doctorPrescriptionData;

  @Column(name = "doctor_prescription_filename")
  private String doctorPrescriptionFilename;

  @Lob
  @Column(name = "insurance_policy_data", columnDefinition="LONGBLOB")
  private byte[] insurancePolicyData;

  @Column(name = "insurance_policy_filename")
  private String insurancePolicyFilename;

  public PatientAdmission() {
  }

  // --- Getters and Setters for all fields ---

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getReferredByDr() {
    return referredByDr;
  }

  public void setReferredByDr(String referredByDr) {
    this.referredByDr = referredByDr;
  }

  public String getPatientType() {
    return patientType;
  }

  public void setPatientType(String patientType) {
    this.patientType = patientType;
  }

  public String getPatientName() {
    return patientName;
  }

  public void setPatientName(String patientName) {
    this.patientName = patientName;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getHusbandFatherName() {
    return husbandFatherName;
  }

  public void setHusbandFatherName(String husbandFatherName) {
    this.husbandFatherName = husbandFatherName;
  }

  public String getPatientAddress() {
    return patientAddress;
  }

  public void setPatientAddress(String patientAddress) {
    this.patientAddress = patientAddress;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public LocalDate getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(LocalDate dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getOccupation() {
    return occupation;
  }

  public void setOccupation(String occupation) {
    this.occupation = occupation;
  }

  public String getNameOfOrganization() {
    return nameOfOrganization;
  }

  public void setNameOfOrganization(String nameOfOrganization) {
    this.nameOfOrganization = nameOfOrganization;
  }

  public String getDesignation() {
    return designation;
  }

  public void setDesignation(String designation) {
    this.designation = designation;
  }

  public String getPatientCategory() {
    return patientCategory;
  }

  public void setPatientCategory(String patientCategory) {
    this.patientCategory = patientCategory;
  }

  public String getNameOfDoctor() {
    return nameOfDoctor;
  }

  public void setNameOfDoctor(String nameOfDoctor) {
    this.nameOfDoctor = nameOfDoctor;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getRoomPreference() {
    return roomPreference;
  }

  public void setRoomPreference(String roomPreference) {
    this.roomPreference = roomPreference;
  }

  public String getOpdRegistrationNo() {
    return opdRegistrationNo;
  }

  public void setOpdRegistrationNo(String opdRegistrationNo) {
    this.opdRegistrationNo = opdRegistrationNo;
  }

  public String getRelativesNameAddress() {
    return relativesNameAddress;
  }

  public void setRelativesNameAddress(String relativesNameAddress) {
    this.relativesNameAddress = relativesNameAddress;
  }

  public String getRelativesPhone() {
    return relativesPhone;
  }

  public void setRelativesPhone(String relativesPhone) {
    this.relativesPhone = relativesPhone;
  }

  public String getPan() {
    return pan;
  }

  public void setPan(String pan) {
    this.pan = pan;
  }

  public String getAadhar() {
    return aadhar;
  }

  public void setAadhar(String aadhar) {
    this.aadhar = aadhar;
  }

  public String getDiagnosis() {
    return diagnosis;
  }

  public void setDiagnosis(String diagnosis) {
    this.diagnosis = diagnosis;
  }

  public String getMedicalInsurance() {
    return medicalInsurance;
  }

  public void setMedicalInsurance(String medicalInsurance) {
    this.medicalInsurance = medicalInsurance;
  }

  public String getInsuranceCompany() {
    return insuranceCompany;
  }

  public void setInsuranceCompany(String insuranceCompany) {
    this.insuranceCompany = insuranceCompany;
  }

  public String getPolicyNo() {
    return policyNo;
  }

  public void setPolicyNo(String policyNo) {
    this.policyNo = policyNo;
  }

  public String getMrMrs() {
    return mrMrs;
  }

  public void setMrMrs(String mrMrs) {
    this.mrMrs = mrMrs;
  }

  public String getRelativeOfPatient() {
    return relativeOfPatient;
  }

  public void setRelativeOfPatient(String relativeOfPatient) {
    this.relativeOfPatient = relativeOfPatient;
  }

  public String getBedNo() {
    return bedNo;
  }

  public void setBedNo(String bedNo) {
    this.bedNo = bedNo;
  }

  public BigDecimal getUndertakingAmount() {
    return undertakingAmount;
  }

  public void setUndertakingAmount(BigDecimal undertakingAmount) {
    this.undertakingAmount = undertakingAmount;
  }

  public LocalDate getPaymentDueDate() {
    return paymentDueDate;
  }

  public void setPaymentDueDate(LocalDate paymentDueDate) {
    this.paymentDueDate = paymentDueDate;
  }

  public String getNameUndertaking() {
    return nameUndertaking;
  }

  public void setNameUndertaking(String nameUndertaking) {
    this.nameUndertaking = nameUndertaking;
  }

  public String getSignatureUndertaking() {
    return signatureUndertaking;
  }

  public void setSignatureUndertaking(String signatureUndertaking) {
    this.signatureUndertaking = signatureUndertaking;
  }

  public LocalDate getDateUndertaking() {
    return dateUndertaking;
  }

  public void setDateUndertaking(LocalDate dateUndertaking) {
    this.dateUndertaking = dateUndertaking;
  }

  public String getRelationUndertaking() {
    return relationUndertaking;
  }

  public void setRelationUndertaking(String relationUndertaking) {
    this.relationUndertaking = relationUndertaking;
  }

  public String getResponsibleName1() {
    return responsibleName1;
  }

  public void setResponsibleName1(String responsibleName1) {
    this.responsibleName1 = responsibleName1;
  }

  public String getResponsibleSign1() {
    return responsibleSign1;
  }

  public void setResponsibleSign1(String responsibleSign1) {
    this.responsibleSign1 = responsibleSign1;
  }

  public String getResponsibleMobile1() {
    return responsibleMobile1;
  }

  public void setResponsibleMobile1(String responsibleMobile1) {
    this.responsibleMobile1 = responsibleMobile1;
  }

  public String getResponsibleEmail1() {
    return responsibleEmail1;
  }

  public void setResponsibleEmail1(String responsibleEmail1) {
    this.responsibleEmail1 = responsibleEmail1;
  }

  public String getResponsibleName2() {
    return responsibleName2;
  }

  public void setResponsibleName2(String responsibleName2) {
    this.responsibleName2 = responsibleName2;
  }

  public String getResponsibleSign2() {
    return responsibleSign2;
  }

  public void setResponsibleSign2(String responsibleSign2) {
    this.responsibleSign2 = responsibleSign2;
  }

  public String getResponsibleMobile2() {
    return responsibleMobile2;
  }

  public void setResponsibleMobile2(String responsibleMobile2) {
    this.responsibleMobile2 = responsibleMobile2;
  }

  public String getResponsibleEmail2() {
    return responsibleEmail2;
  }

  public void setResponsibleEmail2(String responsibleEmail2) {
    this.responsibleEmail2 = responsibleEmail2;
  }

  public BigDecimal getTotalAmountPaid() {
    return totalAmountPaid;
  }

  public void setTotalAmountPaid(BigDecimal totalAmountPaid) {
    this.totalAmountPaid = totalAmountPaid;
  }

  public String getNoDepositReason() {
    return noDepositReason;
  }

  public void setNoDepositReason(String noDepositReason) {
    this.noDepositReason = noDepositReason;
  }

  public String getNameOfCounsellor() {
    return nameOfCounsellor;
  }

  public void setNameOfCounsellor(String nameOfCounsellor) {
    this.nameOfCounsellor = nameOfCounsellor;
  }

  public String getSignCounsellor() {
    return signCounsellor;
  }

  public void setSignCounsellor(String signCounsellor) {
    this.signCounsellor = signCounsellor;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getAdminComments() {
    return adminComments;
  }

  public void setAdminComments(String adminComments) {
    this.adminComments = adminComments;
  }

  // --- Getters and Setters for new field ---
  public String getInsuranceCompanyComment() {
    return insuranceCompanyComment;
  }

  public void setInsuranceCompanyComment(String insuranceCompanyComment) {
    this.insuranceCompanyComment = insuranceCompanyComment;
  }

  // --- Getters and Setters for new file fields ---
  public byte[] getAadharCardData() {
    return aadharCardData;
  }

  public void setAadharCardData(byte[] aadharCardData) {
    this.aadharCardData = aadharCardData;
  }

  public String getAadharCardFilename() {
    return aadharCardFilename;
  }

  public void setAadharCardFilename(String aadharCardFilename) {
    this.aadharCardFilename = aadharCardFilename;
  }

  public byte[] getDoctorPrescriptionData() {
    return doctorPrescriptionData;
  }

  public void setDoctorPrescriptionData(byte[] doctorPrescriptionData) {
    this.doctorPrescriptionData = doctorPrescriptionData;
  }

  public String getDoctorPrescriptionFilename() {
    return doctorPrescriptionFilename;
  }

  public void setDoctorPrescriptionFilename(String doctorPrescriptionFilename) {
    this.doctorPrescriptionFilename = doctorPrescriptionFilename;
  }

  public byte[] getInsurancePolicyData() {
    return insurancePolicyData;
  }

  public void setInsurancePolicyData(byte[] insurancePolicyData) {
    this.insurancePolicyData = insurancePolicyData;
  }

  public String getInsurancePolicyFilename() {
    return insurancePolicyFilename;
  }

  public void setInsurancePolicyFilename(String insurancePolicyFilename) {
    this.insurancePolicyFilename = insurancePolicyFilename;
  }
}
