package com.example.hpjulab.kioskhealthcare;




import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;


@Root(name="PatientReport")
public class Patient_Report
{
	@Element(required = false)
	PatientBasicData PatientBasicData;
	@ElementList(required = false)
	ArrayList<Report> Reports;

	Patient_Report()
	{
		Reports=new ArrayList<Report>();
	}

	PatientBasicData getpatientBasicData()
	{
		return PatientBasicData;
	}

	void setpatientBasicData(PatientBasicData patientBasicData)
	{
		this.PatientBasicData=patientBasicData;
	}


	ArrayList<Report> getReports()
	{
		return Reports;
	}

	void setReports(ArrayList<Report> Reports)
	{
		this.Reports=Reports;
	}
}
