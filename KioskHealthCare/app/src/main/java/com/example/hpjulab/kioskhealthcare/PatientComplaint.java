package com.example.hpjulab.kioskhealthcare;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.text.SimpleDateFormat;
import java.util.Date;


@Root
public class PatientComplaint
{
	@Element(required = false)
	String complaint, complaint_date,weight,bmi,bp,pulse,temperature,spo2,otherResults,fileNames,kioskCoordinatorName,prevDiagnosis;

	PatientComplaint()
	{
		SimpleDateFormat d=new SimpleDateFormat("yyyy-MM-dd");
		complaint_date=d.format(new Date());
	}

	String getcomplaint()
	{
		return complaint;
	}


	void setcomplaint(String complaint)
	{
		this.complaint=complaint;
	}

	String getcomplaint_date()
	{

		return complaint_date;
	}

	void setcomplaint_date(String complaint_date)
	{
		this.complaint_date=complaint_date;
	}

	String getWeight()
	{
		return weight;
	}

	void setWeight(String Weight)
	{
		this.weight=Weight;
	}

	String getBmi()
	{
		return bmi;
	}

	void setBmi(String Bmi)
	{
		this.bmi=Bmi;
	}

	String getBp()
	{
		return bp;
	}

	void setBp(String Bp)
	{
		this.bp=Bp;
	}

	String getPulse()
	{
		return pulse;
	}

	void setPulse(String Pulse)
	{
		this.pulse=Pulse;
	}

	String getTemperature()
	{
		return temperature;
	}

	void setTemperature(String Temperature)
	{
		this.temperature=Temperature;
	}

	String getSpo2()
	{
		return spo2;
	}

	void setSpo2(String Spo2)
	{
		this.spo2=Spo2;
	}

	String getOtherResults()
	{
		return otherResults;
	}

	void setOtherResults(String OtherResults)
	{
		this.otherResults=OtherResults;
	}

	String getFileNames()
	{
		return fileNames;
	}

	void setFileNames(String FileNames)
	{
		this.fileNames=FileNames;
	}

	String getKioskCoordinatorName()
	{
		return kioskCoordinatorName;
	}

	void setKioskCoordinatorName(String KioskCoordinatorName)
	{
		this.kioskCoordinatorName=KioskCoordinatorName;
	}

	String getPrevDiagnosis()
	{
		return prevDiagnosis;
	}

	void setPrevDiagnosis(String PrevDiagnosis)
	{
		this.prevDiagnosis=PrevDiagnosis;
	}

}