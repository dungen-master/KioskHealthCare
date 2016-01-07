package com.example.hpjulab.kioskhealthcare;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by devil on 9/19/15.
 */
public class PatientForm extends Activity {

    public int REPORT_ACTIVITY=19,PROBLEM_ACTIVITY=20;

    public TextView regvalue,status,date;
    public EditText height,weight,temp,bmi,bp1,bp2,spo2,pulse,prescription;
    public static EditText healthproblem;
    public CheckBox anaemia,edemia,jaundice,emergency;
    public Button problemBtn,uploadBtn,snapBtn,finalSubmitBtn,complainBtn;
    public Spinner blood;

    public static String patientId,statusVar,dateVar,heightVar,weightVar,tempVar,bmiVar,bpVar,spo2Var,pulseVar,
            prescriptionVar,healthVar,imageString,effected_imageString,kioskCordinatorName,fileNames;

    public String imagePath,localDir,serverDir;
    public static int reportCount;
    public boolean newComplain,previousComplainShow,savePressed;
    public Patient_Report new_report;
    public ArrayList<Report> reports;
    public ArrayList<String> files;

    public static Report lastReport;
    public static boolean effectedImageCanbeTaken,showLastDiagnosis,isEmergency;
    public static String healthProblemsVar;
    public ProgressDialog progressDialog;
    public boolean isLockFile;
    public int lockId;
    public static Patient_Report patient_report;
    public String tempError="";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from new_activity.xml

        setContentView(R.layout.patient_health_info);
        initiate();

        //add scrolling

        healthproblem.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View view, MotionEvent event) {
                // TODO Auto-generated method stub
                if (view.getId() == R.id.patient_health_info_problemsId) {
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

        prescription.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View view, MotionEvent event) {
                // TODO Auto-generated method stub
                if (view.getId() == R.id.patient_health_info_prescriptionId) {
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



        problemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(PatientForm.this, "Not added", Toast.LENGTH_SHORT).show();
                healthProblemsVar="";
                startActivity(new Intent(PatientForm.this,HealthProblemsTab.class));

            }
        });

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(PatientForm.this,UploadReports.class);
                startActivityForResult(in,REPORT_ACTIVITY);

            }
        });

        snapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((TabActivity) getParent()).getTabHost().setCurrentTab(1);
            }
        });

        finalSubmitBtn.setOnClickListener(new View.OnClickListener() {//exit and cancel
            @Override
            public void onClick(View v) {
                String btn = finalSubmitBtn.getText().toString();

                if (btn.equals("CANCEL")) {

                    lockId=3;
                    isLockFile=false;
                    LockUnlock lc=new LockUnlock();
                    lc.execute();

                } else if (btn.equals("EXIT")) {

                    AlertDialog.Builder alt1=new AlertDialog.Builder(PatientForm.this);
                    alt1.setTitle("Finish");
                    alt1.setMessage("Are you sure!");
                    alt1.setIcon(R.drawable.alert5);
                    alt1.setCancelable(false);

                    alt1.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            endConnection();
                        }
                    });

                    alt1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {


                        }
                    });

                    alt1.show();


                }
            }
        });

        complainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String btn = complainBtn.getText().toString();


                if (btn.equals("COMPLAIN")) {

                    AlertDialog.Builder alt1 = new AlertDialog.Builder(PatientForm.this);
                    alt1.setTitle("Check Availability");
                    alt1.setMessage("Choose from below options");
                    alt1.setIcon(R.drawable.alert6);

                    alt1.setPositiveButton("New Complain", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            isLockFile = true;
                            lockId = 1;
                            LockUnlock lc = new LockUnlock();
                            lc.execute();

                        }
                    });

                    alt1.setNegativeButton("Previous Complain", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            if (previousComplainShow == true) {
                                showPreviousComplaint();
                            } else {
                                AlertDialog.Builder alt3 = new AlertDialog.Builder(PatientForm.this);
                                alt3.setCancelable(false);
                                alt3.setIcon(R.drawable.alert5);
                                alt3.setTitle("New Patient");
                                alt3.setMessage("No previous complaints");

                                alt3.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });

                                alt3.show();

                            }
                        }
                    });

                    alt1.show();


                } else if (btn.equals("FINAL SUBMIT")) {

                    generatePrescription();

                }
            }
        });


    }

    @Override
    public void onBackPressed()
    {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REPORT_ACTIVITY && resultCode==RESULT_OK)
        {

            if(!UploadReports.presPath.equals(""))
            {
                files.add(UploadReports.presPath);
                prescriptionVar+=UploadReports.presPath+"\n";
            }

            if(!UploadReports.xrayPath.equals(""))
            {
                files.add(UploadReports.xrayPath);
                prescriptionVar+=UploadReports.xrayPath+"\n";
            }

            if(!UploadReports.bloodPath.equals(""))
            {
                files.add(UploadReports.bloodPath);
                prescriptionVar+=UploadReports.bloodPath+"\n";
            }

            if(!UploadReports.ecgPath.equals(""))
            {
                files.add(UploadReports.ecgPath);
                prescriptionVar+=UploadReports.ecgPath+"\n";
            }

            if(!UploadReports.otherPath.equals(""))
            {
                files.add(UploadReports.otherPath);
                prescriptionVar+=UploadReports.otherPath+"\n";
            }

            prescription.setText(prescriptionVar);

        }

    }

    public void enableFields(boolean flag)
    {

        weight.setEnabled(flag);
        temp.setEnabled(flag);
        bmi.setEnabled(flag);
        bp1.setEnabled(flag);
        bp2.setEnabled(flag);
        spo2.setEnabled(flag);
        pulse.setEnabled(flag);

        anaemia.setClickable(flag);
        edemia.setClickable(flag);
        jaundice.setClickable(flag);
        emergency.setClickable(flag);

        if(flag==false)
            healthproblem.setKeyListener(null);
        else healthproblem.setEnabled(true);


        problemBtn.setEnabled(flag);
        uploadBtn.setEnabled(flag);

    }

    public void resetField()
    {

        //weight.setText(null);
        //temp.setText(null);
        //bmi.setText(null);
        //bp.setText(null);
        //spo2.setText(null);
        //pulse.setText(null);
        prescription.setText(null);
        healthproblem.setText(null);

        anaemia.setChecked(false);
        jaundice.setChecked(false);
        edemia.setChecked(false);

        emergency.setChecked(false);

    }

    public void initiate()
    {
        localDir=MainActivity.DirLocal;

        regvalue=(TextView)findViewById(R.id.patient_health_info_regId);
        status=(TextView)findViewById(R.id.patient_health_info_stId);
        date=(TextView)findViewById(R.id.patient_health_info_dateId);

        height=(EditText)findViewById(R.id.patient_health_info_heightId);
        height.setEnabled(false);

        weight=(EditText)findViewById(R.id.patient_health_info_weightId);
        temp=(EditText)findViewById(R.id.patient_health_info_tempId);
        bmi=(EditText)findViewById(R.id.patient_health_info_bmiId);
        bp1=(EditText)findViewById(R.id.patient_health_info_bpId1);
        bp2=(EditText)findViewById(R.id.patient_health_info_bpId2);
        spo2=(EditText)findViewById(R.id.patient_health_info_spo2Id);
        pulse=(EditText)findViewById(R.id.patient_health_info_pulseId);
        prescription=(EditText)findViewById(R.id.patient_health_info_prescriptionId);
        prescription.setKeyListener(null);

        healthproblem=(EditText)findViewById(R.id.patient_health_info_problemsId);

        anaemia=(CheckBox)findViewById(R.id.patient_health_info_anemiaId);
        edemia=(CheckBox)findViewById(R.id.patient_health_info_edemiaId);
        jaundice=(CheckBox)findViewById(R.id.patient_health_info_jaundiceId);
        emergency=(CheckBox)findViewById(R.id.patient_health_info_emergencyId);

        problemBtn=(Button)findViewById(R.id.patient_health_info_prblmbtnId);
        uploadBtn=(Button)findViewById(R.id.patient_health_info_uploadId);
        snapBtn=(Button)findViewById(R.id.patient_health_info_snapId);
        finalSubmitBtn=(Button)findViewById(R.id.patient_health_info_finalSubmitId);
        complainBtn=(Button)findViewById(R.id.patient_health_info_complainId);

        blood=(Spinner)findViewById(R.id.patient_health_info_bgId);
        blood.setEnabled(false);


        int index=0;

        savePressed=false;

        if(ExistPatientBasic.bloodGroupVar!=null && !ExistPatientBasic.bloodGroupVar.equals(""))
        {
            for(int i=0;i<blood.getCount();i++)
            {
                if(blood.getItemAtPosition(i).equals(ExistPatientBasic.bloodGroupVar))
                    index=i;
            }

            blood.setSelection(index);
        }

        statusVar=Patient_login.patient_report.PatientBasicData.getStatus();
        patientId=Patient_login.patient_report.PatientBasicData.getId();
        imageString=Patient_login.patient_report.PatientBasicData.getImage();

        prescriptionVar="";
        fileNames="";


        Date d = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
        this.dateVar = ft.format(d);

        status.setText(statusVar);
        regvalue.setText(patientId);
        date.setText(dateVar);
        height.setText(Patient_login.patient_report.PatientBasicData.getHeight());



        kioskCordinatorName=KioskLogin.KioskName;


        serverDir=MainActivity.SERVER_DIR;

        imagePath=localDir+"/"+patientId+"_effected_image.jpg";
        effected_imageString=patientId+"_effected_image.jpg";


        enableFields(false);



        reportCount=Patient_login.patient_report.Reports.size()-1;
        previousComplainShow=false;

        reports=new ArrayList<Report>();
        files=new ArrayList<String>();



//        showDialogues(""+reportCount);

        effectedImageCanbeTaken=false;
        showLastDiagnosis=false;



        if(reportCount<0) {

            newComplain = true;
            effectedImageCanbeTaken=true;

            showLastDiagnosis=false;
            previousComplainShow=false;
        }
        else{

            try{

                patient_report=Patient_login.patient_report;

                reports=Patient_login.patient_report.getReports();
                lastReport=reports.get(reportCount);

                showHealthInformation();

                if(lastReport.doctorPrescription.getdoctorName().equals(null))
                {
                    newComplain=false;
                    effectedImageCanbeTaken=false;
                    showLastDiagnosis=false;
                }
                else{
                    newComplain=true;
                    effectedImageCanbeTaken=true;
                    showLastDiagnosis=true;
                }


            }catch (Exception e)
            {

                newComplain=false;
                effectedImageCanbeTaken=false;
                showLastDiagnosis=false;
            }
            previousComplainShow=true;
        }



    }

    public void showHealthInformation()
    {
        try{

            weight.setText(lastReport.patientComplaint.getWeight());
            bmi.setText(lastReport.patientComplaint.getBmi());
            String s1=lastReport.patientComplaint.getBp();

            if(!s1.equals("")) {

                String[] bp = s1.split("/");
                bp1.setText(bp[0]);
                bp2.setText(bp[1]);

            }
//            bp.setText(lastReport.patientComplaint.getBp());
            temp.setText(lastReport.patientComplaint.getTemperature());
            pulse.setText(lastReport.patientComplaint.getPulse());
            spo2.setText(lastReport.patientComplaint.getSpo2());


        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void checkNewFile()
    {
        reportCount=patient_report.Reports.size()-1;
        previousComplainShow=false;

        reports=new ArrayList<Report>();
        files=new ArrayList<String>();


        effectedImageCanbeTaken=false;
        showLastDiagnosis=false;



        if(reportCount<0) {

            newComplain = true;
            effectedImageCanbeTaken=true;

            showLastDiagnosis=false;
            previousComplainShow=false;
        }
        else{

            try{

                reports=patient_report.getReports();

                lastReport =reports.get(reportCount);
                showHealthInformation();

                if(lastReport.doctorPrescription.getdoctorName().equals(null))
                {
                    newComplain=false;
                    effectedImageCanbeTaken=false;
                    showLastDiagnosis=false;
                }
                else{
                    newComplain=true;
                    effectedImageCanbeTaken=true;
                    showLastDiagnosis=true;
                }


            }catch (Exception e)
            {

                newComplain=false;
                effectedImageCanbeTaken=false;
                showLastDiagnosis=false;
            }
            previousComplainShow=true;
        }
    }


    public void showPreviousComplaint()
    {
        if(previousComplainShow==true) {

            startActivity(new Intent(PatientForm.this,ShowPreviousComplain.class));

        }

    }



    public void changeComplainBtn()// new complain and FINAl submit
    {
        if(complainBtn.getText().toString().equals("COMPLAIN"))
            complainBtn.setText("FINAL SUBMIT");
        else complainBtn.setText("COMPLAIN");
    }

    public void changeFinalSubmitBtn()//final submit and cancel
    {
        if(finalSubmitBtn.getText().toString().equals("EXIT"))
            finalSubmitBtn.setText("CANCEL");
        else finalSubmitBtn.setText("EXIT");
    }

    public void showDialogues(String s)
    {
        Toast.makeText(PatientForm.this,s,Toast.LENGTH_SHORT).show();
    }

    public void deleteFiles()
    {
        File f=new File(localDir+"/"+imageString);
        if(f.exists())
            f.delete();

        f=new File(localDir+"/tempPatient.xml");
        if(f.exists())
            f.delete();

        f=new File(localDir+"/"+patientId+"_image.jpg");
        if(f.exists())
            f.delete();

        f=new File(localDir+"/"+effected_imageString);
        if(f.exists())
            f.delete();

        f=new File(localDir+"/patientLog.xml");
        if(f.exists())
            f.delete();

    }


    public void endConnection()
    {

        deleteFiles();

        startActivity(new Intent(PatientForm.this,NewExistingPatient.class));
        finish();
    }


    public void generatePrescription()//to be checked for new complain false
    {

        if(newComplain==true)
        {

            AlertDialog.Builder alt1=new AlertDialog.Builder(PatientForm.this);
            alt1.setTitle("Confirmation");
            alt1.setMessage("Are you sure!");
            alt1.setIcon(R.drawable.alert5);
            alt1.setCancelable(false);

            alt1.setPositiveButton("Submit", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    progressDialog=new ProgressDialog(PatientForm.this);
                    progressDialog.setMessage("Sending Information");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.setIndeterminate(true);

                    new android.os.Handler().postDelayed(
                            new Runnable() {
                                @Override
                                public void run() {

                                    takeCare();
                                }
                            }, 1000);

                    progressDialog.show();

//                takeCare();//form submission

                }
            });

            alt1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {


                }
            });


            alt1.show();



        }
        else
        {
            AlertDialog.Builder alt = new AlertDialog.Builder(PatientForm.this);
            alt.setTitle("Back to registration");
            alt.setIcon(R.drawable.alert6);
            alt.setCancelable(false);

            alt.setPositiveButton("Finish", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    endConnection();
                }
            });

            alt.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            alt.show();
        }


    }

    public void getValues()
    {
        weightVar=weight.getText().toString();
        tempVar=temp.getText().toString();
        bpVar="";
        if(bp1.getText()!=null) {

            String s1 = bp1.getText().toString();
            if (s1.equals(""))
                bpVar = "";
            else {
                String s2 = bp2.getText().toString();
                bpVar = s1 + "/" + s2;
            }
        }
//        bpVar=bp.getText().toString();
        bmiVar=bmi.getText().toString();
        spo2Var=spo2.getText().toString();
        pulseVar=pulse.getText().toString();
        healthVar=healthproblem.getText().toString();
        prescriptionVar=prescription.getText().toString();

    }

    public void takeValuesBasic()
    {
        new_report=new Patient_Report();
        new_report.PatientBasicData=patient_report.PatientBasicData;
        new_report.PatientBasicData.setStatus("Review");
    }

    public boolean checkValues()
    {
        boolean weightcheck =(weight.getText()==null) || ((weight.getText()!=null) && ((weight.getText().toString().equals("")) ||
                (!weight.getText().toString().matches(".*[a-zA-Z]+.*") && Float.parseFloat(weight.getText().toString()) >= 3 && Float.parseFloat(weight.getText().toString()) <= 200)));

        boolean bmicheck = (bmi.getText()==null) || ((bmi.getText()!=null) && ((bmi.getText().toString().equals("")) ||
                (!bmi.getText().toString().matches(".*[a-zA-Z]+.*"))));

        //String bpParts[] = bp.getText().toString().split("/");
//        try
//        {
//            if(!bp_field.getText().equals(""))
//                bpcheck = bpParts.length ==2 && !bp_field.getText().matches(".*[a-zA-Z]+.*") && Float.parseFloat(bpParts[0]) >= 40 && Float.parseFloat(bpParts[0]) <= 200 && Float.parseFloat(bpParts[1]) >= 40 && Float.parseFloat(bpParts[1]) <= 200;
//        }
//        catch(Exception e){}

//        boolean bpcheck = (bp.getText()==null) || ((bp.getText()!=null) && ((bp.getText().toString().equals("")) ||
//                (!bp.getText().toString().matches(".*[a-zA-Z]+.*") && Float.parseFloat((bp.getText().toString().split("/"))[0]) >= 40 && Float.parseFloat((bp.getText().toString().split("/"))[0]) <= 200)));

        boolean bpcheck=false;

        if(bp1.getText()==null && bp2.getText()==null)
            bpcheck=true;
        else if(bp1.getText()==null || bp2.getText()==null)
            bpcheck=false;
        else if(bp1.getText().toString().equals("") && bp2.getText().toString().equals(""))
            bpcheck=true;
        else if(bp1.getText().toString().equals("") || bp2.getText().toString().equals(""))
            bpcheck=false;
        else
        {
            String s1=bp1.getText().toString();
            String s2=bp2.getText().toString();

            if(Float.parseFloat(s1)>=40 && Float.parseFloat(s1)<=200 && Float.parseFloat(s2)>=40 && Float.parseFloat(s2)<=200)
                bpcheck=true;
            else bpcheck=false;
        }

//        boolean bpcheck = (bp.getText()==null) || ((bp.getText()!=null) && ((bp.getText().toString().equals("")) ||
//                (bpParts.length ==2 && !bp.getText().toString().matches(".*[a-zA-Z]+.*") && Float.parseFloat(bpParts[0]) >= 40 && Float.parseFloat(bpParts[0]) <= 200 && Float.parseFloat(bpParts[1]) >= 40 && Float.parseFloat(bpParts[1]) <= 200)));

        boolean pulsecheck =(pulse.getText()==null) || ((pulse.getText()!=null) &&  ((pulse.getText().toString().equals("")) ||
                (!pulse.getText().toString().matches(".*[a-zA-Z]+.*") && Float.parseFloat(pulse.getText().toString()) >= 30 && Float.parseFloat(pulse.getText().toString()) <= 240)));

        boolean temperaturecheck =(temp.getText()==null) || ((temp.getText()!=null) && ((temp.getText().toString().equals("")) ||
                (!temp.getText().toString().matches(".*[a-zA-Z]+.*") && Float.parseFloat(temp.getText().toString()) >= 13 && Float.parseFloat(temp.getText().toString()) <= 45)));

        boolean spO2check =(spo2.getText()==null) || ((spo2.getText()!=null) && ((spo2.getText().toString().equals("")) ||
                (!spo2.getText().toString().matches(".*[a-zA-Z]+.*") && Float.parseFloat(spo2.getText().toString()) >= 90 && Float.parseFloat(spo2.getText().toString()) <= 100)));

        tempError="Check:";

        if(!weightcheck)
            tempError+=" --Weight";

        if(!bmicheck)
            tempError+=" --Bmi";

        if(!bpcheck)
            tempError+=" --Bp.(Format of BP : A/B)";

        if(!pulsecheck)
            tempError+=" --Pulse";

        if(!temperaturecheck)
            tempError+=" --Temperature";

        if(!spO2check)
            tempError+=" --Spo2";


        return (weightcheck & bmicheck & bpcheck & pulsecheck & temperaturecheck & spO2check);


    }

    public void takeHealthValues()
    {

        if(newComplain==true)
        {
            Report rp=new Report();

            rp.patientComplaint.setWeight(weightVar);
            rp.patientComplaint.setTemperature(tempVar);
            rp.patientComplaint.setBp(bpVar);
            rp.patientComplaint.setBmi(bmiVar);
            rp.patientComplaint.setSpo2(spo2Var);
            rp.patientComplaint.setPulse(pulseVar);
            rp.patientComplaint.setcomplaint(healthVar);
            rp.patientComplaint.setFileNames(fileNames);
            rp.patientComplaint.setcomplaint_date(dateVar);
            rp.patientComplaint.setKioskCoordinatorName(kioskCordinatorName);

            String other="";
            if(anaemia.isChecked())
                other+="anemia";
            if(edemia.isChecked())
                other+="\n"+"edema";
            if(jaundice.isChecked())
                other+="\n"+"jaundice";

            rp.patientComplaint.setOtherResults(other);
            rp.patientComplaint.setPrevDiagnosis("");


            reports.add(rp);
        }

        new_report.setReports(reports);

    }

    public void takeCare()
    {


        if(checkValues())
        {
            getValues();

            takeValuesBasic();
            if(emergency.isChecked()==true)
                isEmergency=true;
            else isEmergency=false;

            CreatePrescription crpr=new CreatePrescription();
            crpr.execute();

        }
        else {
            progressDialog.dismiss();
            showDialogues(tempError);
        }


    }

    public String createName(String s,int i)
    {
        SimpleDateFormat fileDate = new SimpleDateFormat("yy_MM_dd_HH_mm_ss");

        String[]  str=s.split("/",-1);
        String temp=str[str.length-1];

        String[] s2=temp.split("\\.",-1);

        String fileExtension=s2[s2.length-1];
        String fileName = patientId + "_" + fileDate.format(new Date()) + "_" + i + "." + fileExtension;

        return fileName;
    }


    public class CreatePrescription extends AsyncTask<Void,Void,Void>{

        public boolean isAlright,fileSent;

        @Override
        protected void onPreExecute()
        {
            isAlright=false;
            fileSent=false;
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            try{
                int count=0;

                File f=new File(localDir+"/"+effected_imageString);
                if(f.exists() && effectedImageCanbeTaken==true)
                {
                    if(KioskLogin.conFinal.sendToServer(localDir+"/"+effected_imageString,serverDir+"/"+patientId+"_effected_image.jpg")>=0)
                    {
                        count++;
                        fileNames+=effected_imageString+"\n";
                    }
                }
                else count++;

                for(int i=0;i<files.size();i++)
                {

                    try{

                        Thread.sleep(2000);

                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    String s=files.get(i);
                    s=createName(s,i);

                    if(KioskLogin.conFinal.sendToServer(files.get(i),serverDir+"/"+s)>=0)
                    {
                        fileNames+=s+"\n";
                    }

                }


                takeHealthValues();
                File finalPres=new File(localDir+"/tempPatient.xml");
                Serializer sr=new Persister();
                sr.write(new_report,finalPres);

                if(KioskLogin.conFinal.sendToServer(localDir+"/tempPatient.xml",serverDir+"/"+patientId+".xml")>=0)
                {
                    count++;
                }

                int lockResponse=KioskLogin.conFinal.unlockFile(patientId+".xml");

                if(lockResponse>=0)
                    count++;

                lockResponse=KioskLogin.conFinal.lockFile("Patient_"+MainActivity.KIOSK_NUMBER+"_Log.xml");

                if(KioskLogin.conFinal.receiveFromServer("Patient_"+MainActivity.KIOSK_NUMBER+"_Log.xml",localDir+"/patientLog.xml")>=0)
                {
                    finalPres=new File(localDir+"/patientLog.xml");
                    Serializer sr2=new Persister();
                    PatientLog pl;
                    pl=sr2.read(PatientLog.class,finalPres);

                    if(isEmergency==false)
                    {
                        String temp=pl.getnormal();
                        if(temp==null)
                            temp=patientId;
                        else
                            temp+=" "+patientId;

                        pl.setnormal(temp);
                    }
                    else
                    {
                        String temp=pl.getemergency();
                        if(temp==null)
                            temp=patientId;
                        else
                            temp+=" "+patientId;

                        pl.setemergency(temp);
                    }


                    sr2.write(pl,finalPres);

                    if(lockResponse>=0 && KioskLogin.conFinal.sendToServer(localDir+"/patientLog.xml",serverDir+"/Patient_"+MainActivity.KIOSK_NUMBER+"_Log.xml")>=0)
                        count++;
                }

                if(count==4)
                    isAlright=true;
                lockResponse=KioskLogin.conFinal.unlockFile("Patient_"+MainActivity.KIOSK_NUMBER+"_Log.xml");

            }
            catch (Exception e)
            {
                e.printStackTrace();
                isAlright=false;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            if(isAlright==true)
            {
                progressDialog.dismiss();
                AlertDialog.Builder alt = new AlertDialog.Builder(PatientForm.this);
                alt.setTitle("Form submitted");
                alt.setMessage("Back to registration");
                alt.setIcon(R.drawable.alert6);
                alt.setCancelable(false);

                alt.setPositiveButton("Finish", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        endConnection();
                    }
                });

                alt.show();
            }
            else
            {
                progressDialog.dismiss();
                AlertDialog.Builder alt=new AlertDialog.Builder(PatientForm.this);
                alt.setTitle("CONNECTION ERROR");
                alt.setMessage("Back to login page!");
                alt.setIcon(R.drawable.alert5);
                alt.setCancelable(false);

                alt.setNeutralButton("Finish", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        endConnection();
                    }
                });

                alt.show();

            }
        }
    }

    public class LockUnlock extends AsyncTask<Void,Void,Void>{

        public boolean isAlright;
        public int response,unLockResponse;


        @Override
        protected void onPreExecute()
        {
            isAlright=false;

        }

        @Override
        protected Void doInBackground(Void... params)
        {
            try{
                    if(isLockFile==true) {

                        response = KioskLogin.conFinal.lockFile(patientId + ".xml");

                        if (response >= 0 && KioskLogin.conFinal.receiveFromServer(patientId + ".xml", localDir + "/tempPatient.xml") >= 0) {

                            File f1 = new File(localDir + "/tempPatient.xml");
                            Serializer sp = new Persister();
                            patient_report = sp.read(Patient_Report.class, f1);

                            reports = new ArrayList<Report>();
                            reports = patient_report.getReports();
                            isAlright = true;
                        }
                    }
                else
                    {
                        unLockResponse=KioskLogin.conFinal.unlockFile(patientId+".xml");
                    }
            }
            catch (Exception e)
            {
                isAlright=false;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            if(lockId==1)
            {
                if(isAlright==false)
                {
                    if(response==-12 || response==-13)
                    {
                        showDialogues("File is Locked,Wait or exit");
                    }
                    else {

                        showDialogues("Connection Error");
                        endConnection();

                    }
                }
                else
                {
                    checkNewFile();

                    if (newComplain == true) {

                        enableFields(true);

                        changeFinalSubmitBtn();
                        changeComplainBtn();


                    } else if (newComplain == false) {


                        AlertDialog.Builder alt = new AlertDialog.Builder(PatientForm.this);
                        alt.setTitle("WARNING! DOCTOR UNAVAILABLE");
                        alt.setMessage("Previous complain not seen");
                        alt.setIcon(R.drawable.alert5);
                        alt.setCancelable(false);

                        alt.setNegativeButton("BACK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                isLockFile=false;
                                lockId=4;
                                LockUnlock lc=new LockUnlock();
                                lc.execute();
                            }
                        });

                        alt.show();
                    }
                }

            }
            else if(lockId==2)
            {
                endConnection();
            }
            else if(lockId==3)
            {

                enableFields(false);
                resetField();
                changeFinalSubmitBtn();
                changeComplainBtn();

            }
            else if(lockId==4)
            {

            }

        }
    }

    public void PrintPrescription()
    {
        //printPrescription
        endConnection();
    }
}
