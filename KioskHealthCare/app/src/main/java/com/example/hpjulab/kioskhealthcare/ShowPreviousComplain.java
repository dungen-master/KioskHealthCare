package com.example.hpjulab.kioskhealthcare;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * Created by devil on 10/24/15.
 */
public class ShowPreviousComplain extends Activity {

    public TextView regvalue,status,date;
    public EditText height,weight,temp,bmi,bp,spo2,pulse,prescription,blood;
    public EditText healthproblem;
    public CheckBox anaemia,edemia,jaundice;
    public Button submitBtn;

    @Override
    public void onCreate(Bundle savedInstance)
    {
        super.onCreate(savedInstance);
        setContentView(R.layout.previous_complain);
        initiate();
        enableFields(false);
        showComplain();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void initiate()
    {

        regvalue=(TextView)findViewById(R.id.previous_complain_regId);
        status=(TextView)findViewById(R.id.previous_complain_stId);
        date=(TextView)findViewById(R.id.previous_complain_dateId);

        height=(EditText)findViewById(R.id.previous_complain_heightId);
        height.setEnabled(false);

        weight=(EditText)findViewById(R.id.previous_complain_weightId);
        temp=(EditText)findViewById(R.id.previous_complain_tempId);
        bmi=(EditText)findViewById(R.id.previous_complain_bmiId);
        bp=(EditText)findViewById(R.id.previous_complain_bpId);
        spo2=(EditText)findViewById(R.id.previous_complain_spo2Id);
        pulse=(EditText)findViewById(R.id.previous_complain_pulseId);
        prescription=(EditText)findViewById(R.id.previous_complain_prescriptionId);
        healthproblem=(EditText)findViewById(R.id.previous_complain_problemsId);

        anaemia=(CheckBox)findViewById(R.id.previous_complain_anemiaId);
        edemia=(CheckBox)findViewById(R.id.previous_complain_edemiaId);
        jaundice=(CheckBox)findViewById(R.id.previous_complain_jaundiceId);

        blood=(EditText)findViewById(R.id.previous_complain_bgId);

        submitBtn=(Button)findViewById(R.id.previous_complain_okId);

        //add scrolling

        prescription.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View view, MotionEvent event) {
                // TODO Auto-generated method stub
                if (view.getId() == R.id.previous_complain_prescriptionId) {
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

        healthproblem.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View view, MotionEvent event) {
                // TODO Auto-generated method stub
                if (view.getId() == R.id.previous_complain_problemsId) {
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


    }

    public void enableFields(boolean flag)
    {

        weight.setEnabled(flag);
        temp.setEnabled(flag);
        bmi.setEnabled(flag);
        bp.setEnabled(flag);
        spo2.setEnabled(flag);
        pulse.setEnabled(flag);
        blood.setEnabled(flag);

        anaemia.setClickable(flag);
        edemia.setClickable(flag);
        jaundice.setClickable(flag);

        healthproblem.setKeyListener(null);
        prescription.setKeyListener(null);

    }

    @Override
    public void onBackPressed()
    {

    }

    public void showComplain()
    {

        Report report = PatientForm.patient_report.Reports.get(PatientForm.reportCount);

        status.setText("Review");
        regvalue.setText(PatientForm.patientId);
        date.setText(report.patientComplaint.getcomplaint_date());
        height.setText(PatientForm.patient_report.PatientBasicData.getHeight());
        weight.setText(report.patientComplaint.getWeight());
        temp.setText(report.patientComplaint.getTemperature());
        bp.setText(report.patientComplaint.getBp());
        bmi.setText(report.patientComplaint.getBmi());
        spo2.setText(report.patientComplaint.getSpo2());
        pulse.setText(report.patientComplaint.getPulse());
        prescription.setText(report.patientComplaint.getFileNames());
        healthproblem.setText(report.patientComplaint.getcomplaint());
        blood.setText(ExistPatientBasic.bloodGroupVar);

        String otherProblems=report.patientComplaint.getOtherResults();
        if( otherProblems!=null && !otherProblems.equals("") ) {

            String[] st = otherProblems.split("\n");

            String temp;

            int i = 0;
            while (i < st.length) {
                temp = st[i];
                if (temp.equals("anemia"))
                    anaemia.setChecked(true);
                else if (temp.equals("edema"))
                    edemia.setChecked(true);
                else if (temp.equals("jaundice"))
                    jaundice.setChecked(true);

                i++;
            }
        }

    }

    public void showDialogues(String s)
    {
        Toast.makeText(ShowPreviousComplain.this, s, Toast.LENGTH_SHORT).show();
    }

}
