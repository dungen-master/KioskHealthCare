package com.example.hpjulab.kioskhealthcare;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Created by devil on 9/28/15.
 */
public class Diagnosis extends Activity {


    public ImageButton next,back;
    public EditText provDiag,finalDiag,medication,advice,diagTest,referal;

    public String provDiagVar,finalDiagVar,medicationVar,adviceVar,diagTestVar,referalVar;
    public Report reportSeen;
    public int report_counter;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.diagnosis_health);

        initiate();
        //setValues();

        /*updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setValues();
            }
        });*/
    }

    public void initiate()
    {


        provDiag=(EditText)findViewById(R.id.diagnosis_health_provId);
        finalDiag=(EditText)findViewById(R.id.diagnosis_health_fdiagnosisId);
        medication=(EditText)findViewById(R.id.diagnosis_health_medicationId);
        advice=(EditText)findViewById(R.id.diagnosis_health_adviceId);
        diagTest=(EditText)findViewById(R.id.diagnosis_health_diagtestId);
        referal=(EditText)findViewById(R.id.diagnosis_health_referalId);

        back=(ImageButton)findViewById(R.id.diagnosis_health_backId);
        next=(ImageButton)findViewById(R.id.diagnosis_health_nextId);

        provDiag.setKeyListener(null);
        finalDiag.setKeyListener(null);
        medication.setKeyListener(null);
        advice.setKeyListener(null);
        diagTest.setKeyListener(null);
        referal.setKeyListener(null);

        provDiagVar=finalDiagVar=medicationVar=adviceVar=diagTestVar=referalVar=null;

        if(PatientForm.reportCount>=0)
        {
            reportSeen=PatientForm.lastReport;

        }

        provDiag.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View view, MotionEvent event) {
                // TODO Auto-generated method stub
                if (view.getId() == R.id.diagnosis_health_provId) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_UP:
                            view.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                }
                return false;
            }
        });

        finalDiag.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View view, MotionEvent event) {
                // TODO Auto-generated method stub
                if (view.getId() == R.id.diagnosis_health_fdiagnosisId) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_UP:
                            view.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                }
                return false;
            }
        });

        medication.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View view, MotionEvent event) {
                // TODO Auto-generated method stub
                if (view.getId() == R.id.diagnosis_health_medicationId) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_UP:
                            view.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                }
                return false;
            }
        });

        advice.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View view, MotionEvent event) {
                // TODO Auto-generated method stub
                if (view.getId() == R.id.diagnosis_health_adviceId) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_UP:
                            view.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                }
                return false;
            }
        });

        diagTest.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View view, MotionEvent event) {
                // TODO Auto-generated method stub
                if (view.getId() == R.id.diagnosis_health_diagtestId) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_UP:
                            view.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                }
                return false;
            }
        });

        referal.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View view, MotionEvent event) {
                // TODO Auto-generated method stub
                if (view.getId() == R.id.diagnosis_health_referalId) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_UP:
                            view.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }
                }
                return false;
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next.setVisibility(View.VISIBLE);
                report_counter--;
                if (report_counter < 0) {
                    back.setVisibility(View.INVISIBLE);
                } else {
                    reportSeen = PatientForm.patient_report.getReports().get(report_counter);
                    showPrescription();
                }

                if(report_counter==0)
                {
                    back.setVisibility(View.INVISIBLE);
                }

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                report_counter++;
                back.setVisibility(View.VISIBLE);
                if(report_counter>PatientForm.reportCount)
                {
                    next.setVisibility(View.INVISIBLE);
                }
                else
                {
                    reportSeen=PatientForm.patient_report.getReports().get(report_counter);
                    showPrescription();
                }

                if(PatientForm.showLastDiagnosis==false)
                {
                    if(report_counter==(PatientForm.reportCount-1))
                        next.setVisibility(View.INVISIBLE);
                }

                if(report_counter==PatientForm.reportCount)
                {
                    next.setVisibility(View.INVISIBLE);
                }
            }
        });




    }

    @Override
    public void onResume()
    {
        super.onResume();
        //initiate();
        resetFields();
        setValues();

    }

    @Override
    public void onBackPressed()
    {

    }

    public void resetFields()
    {
        provDiag.setText(null);
        finalDiag.setText(null);
        medication.setText(null);
        advice.setText(null);
        diagTest.setText(null);
        referal.setText(null);
    }

    public void setValues()
    {

        report_counter=PatientForm.reportCount;

        if(PatientForm.reportCount>=0)
        {
            reportSeen=PatientForm.lastReport;
            next.setVisibility(View.INVISIBLE);

        }

        if(report_counter<=0)
        {
            back.setVisibility(View.INVISIBLE);
        }
        else back.setVisibility(View.VISIBLE);


        if(PatientForm.showLastDiagnosis==true) {

            showPrescription();
        }
        else
        {
            report_counter--;

            AlertDialog.Builder alt=new AlertDialog.Builder(Diagnosis.this);
            alt.setIcon(R.drawable.alert5);
            if(report_counter<0)
            {
                alt.setTitle("New Patient");
                alt.setMessage("Complaint not added");
            }
            else {
                alt.setTitle("Doctor Not Available");
                alt.setMessage("Previous complaint not seen");
            }

            alt.setCancelable(false);

            alt.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if (report_counter >= 0) {
                        AlertDialog.Builder alt1 = new AlertDialog.Builder(Diagnosis.this);
                        alt1.setCancelable(false);
                        alt1.setTitle("Previous prescriptions");
                        alt1.setMessage("Show previous prescriptions");
                        alt1.setIcon(R.drawable.alert6);

                        alt1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                reportSeen = PatientForm.patient_report.getReports().get(report_counter);
                                if (report_counter > 0) {
                                    back.setVisibility(View.VISIBLE);

                                } else {
                                    back.setVisibility(View.INVISIBLE);
                                }
                                showPrescription();
                            }
                        });

                        alt1.setNegativeButton("Back", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ((TabActivity) getParent()).getTabHost().setCurrentTab(0);
                            }
                        });


                        alt1.show();

                    } else {

                        ((TabActivity) getParent()).getTabHost().setCurrentTab(0);
                    }
                }
            });

            alt.show();
        }
    }

    public void showPrescription()
    {
        provDiag.setText(reportSeen.doctorPrescription.getProvisionalDiagnosis());
        finalDiag.setText(reportSeen.doctorPrescription.getFinalDiagnosis());
        medication.setText(reportSeen.doctorPrescription.getMedication());
        advice.setText(reportSeen.doctorPrescription.getAdvice());
        diagTest.setText(reportSeen.doctorPrescription.getDiagnosis());
        referal.setText(reportSeen.doctorPrescription.getReferral());
    }

    public void showDialogue(String s)
    {
        Toast.makeText(Diagnosis.this, s, Toast.LENGTH_SHORT).show();
    }
}
