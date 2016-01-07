package com.example.hpjulab.kioskhealthcare;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;

import javax.xml.transform.Result;

/**
 * Created by devil on 9/19/15.
 */
public class Patient_login extends Activity {

    public EditText regId,phoneId;
    public Button loginBtn,backBtn;
    public String localDir,serverDir,registrationId,phone;
    public static Patient_Report patient_report;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from new_activity.xml

        setContentView(R.layout.login_patient);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        initiate();


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                registrationId=regId.getText().toString();
                phone=phoneId.getText().toString();

                if(regId.getText()!=null && !registrationId.equals("")) {

                    CheckExists ce = new CheckExists();
                    ce.execute();
                }
                else
                {
                    showDialogue("Enter Registration Id");
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    File f = new File(localDir + "/tempPatient.xml");
                    if (f.exists())
                        f.delete();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                startActivity(new Intent(Patient_login.this, NewExistingPatient.class));
                finish();
            }
        });

    }

    @Override
    public void onBackPressed()
    {

    }

    public void initiate()
    {
        regId=(EditText)findViewById(R.id.login_patient_regId);
        phoneId=(EditText)findViewById(R.id.login_patient_phoneId);

        loginBtn=(Button)findViewById(R.id.login_patient_loginId);
        backBtn=(Button)findViewById(R.id.login_patient_backId);

        localDir=MainActivity.DirLocal;
        serverDir=MainActivity.SERVER_DIR;


    }


    //show dialogues
    public void showDialogue(String s)
    {
        Toast.makeText(Patient_login.this, s, Toast.LENGTH_SHORT).show();
    }

    public void endConnection()
    {
        File f=new File(localDir+"/tempPatient.xml");

        if(f.exists())
            f.delete();

        finish();
    }

    public boolean getPatientBasicData(File f)
    {
        try{

            Serializer sr=new Persister();
            patient_report=new Patient_Report();

            patient_report=sr.read(Patient_Report.class, f);
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }


    }


    private class CheckExists extends AsyncTask<Void,Void,Void>{

        private boolean isAlright,loginId;
        public int response;

        @Override
        protected void onPreExecute()
        {
            isAlright=false;
            loginId=false;
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            try{

                if((response=(KioskLogin.conFinal.receiveFromServer(registrationId+".xml",localDir+"/tempPatient.xml")))>=0)
                {
                    File file=new File(localDir+"/tempPatient.xml");
                    boolean i=getPatientBasicData(file);

                    if(file.exists())
                        file.delete();

                    if(i==true)
                        isAlright=true;

                    loginId=true;

                }
                else
                {
                    isAlright=true;
                    loginId=false;
                }
            }
            catch (Exception e)
            {
                isAlright=false;
                e.printStackTrace();

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {

            if(isAlright==false)
            {
                showDialogue("Connection Error");
                endConnection();
            }
            else if(response<0 && response!=-2)
            {
                showDialogue("Connection Error/"+RHErrors.getErrorDescription(response));
            }
            else
            {
                if(loginId==true)
                {
                    AlertDialog.Builder alt=new AlertDialog.Builder(Patient_login.this);
                    alt.setCancelable(false);
                    alt.setIcon(R.drawable.alert6);
                    alt.setTitle("Patient Information");
                    alt.setMessage(patient_report.PatientBasicData.getName());

                    alt.setPositiveButton("Login", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            startActivity(new Intent(Patient_login.this, ExistPatientBasic.class));
                            endConnection();

                        }
                    });

                    alt.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            File f=new File(localDir+"/tempPatient.xml");

                            if(f.exists())
                                f.delete();
                        }
                    });
                    alt.show();
                }
                else
                {
                    AlertDialog.Builder alt=new AlertDialog.Builder(Patient_login.this);
                    alt.setCancelable(false);
                    alt.setIcon(R.drawable.alert6);
                    alt.setTitle("Patient not found");
                    alt.setMessage("Wrong Registration Id.");

                    alt.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                        }
                    });

                    alt.show();
                }


            }
        }

    }

}
