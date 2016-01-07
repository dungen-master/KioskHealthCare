package com.example.hpjulab.kioskhealthcare;


import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "Report")
public class Report
{
	@Element(required = false)
	PatientComplaint patientComplaint=new PatientComplaint();
	@Element(required = false)
	DoctorPrescription doctorPrescription=new DoctorPrescription();


	PatientComplaint getpatientComplaint()
	{
		return patientComplaint;
	}


	void setpatientComplaint(PatientComplaint patientComplaint)
	{
		this.patientComplaint=patientComplaint;
	}

	DoctorPrescription getdoctorPrescription()
	{
		return doctorPrescription;
	}


	void setdoctorPrescription(DoctorPrescription doctorPrescription)
	{
		this.doctorPrescription=doctorPrescription;
	}
}

@Root
class DoctorPrescription
{
	@Element(required = false)
	String doctorName, provisionalDiagnosis,finalDiagnosis,advice, medication, diagnosis, prescription_date,referral,signature,registration_number;

	DoctorPrescription()
	{
		// DateFormat d=new SimpleDateFormat("yyyy-MM-dd");
		// Prescription_Date=d.format(new Date().toString());
	}

	String getdoctorName()
	{
		return doctorName;
	}


	void setdoctorName(String doctorName)
	{
		this.doctorName=doctorName;
	}

	String getProvisionalDiagnosis()
	{
		return provisionalDiagnosis;
	}

	void setProvisionalDiagnosis(String ProvisionalDiagnosis)
	{
		this.provisionalDiagnosis=ProvisionalDiagnosis;
	}

	String getFinalDiagnosis()
	{
		return finalDiagnosis;
	}

	void setFinalDiagnosis(String FinalDiagnosis)
	{
		this.finalDiagnosis=FinalDiagnosis;
	}

	String getAdvice()
	{
		return advice;
	}


	void setAdvice(String Advice)
	{
		this.advice=Advice;
	}

	String getMedication()
	{
		return medication;
	}


	void setMedication(String Medication)
	{
		this.medication=Medication;
	}

	String getDiagnosis()
	{
		return diagnosis;
	}


	void setDiagnosis(String Diagnosis)
	{
		this.diagnosis=Diagnosis;
	}

	String getPrescription_date()
	{
		return prescription_date;
	}


	void setPrescription_date(String Prescription_date)
	{
		this.prescription_date=Prescription_date;
	}

	String getReferral()
	{
		return referral;
	}

	void setReferral(String Referral)
	{
		this.referral=Referral;
	}

	String getSignature()
	{
		return signature;
	}

	void setSignature(String Signature)
	{
		this.signature=Signature;
	}

	String getRegistration_number()
	{
		return registration_number;
	}

	void setRegistration_number(String Registration_number)
	{
		this.registration_number=Registration_number;
	}
}

