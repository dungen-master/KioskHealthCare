package com.example.hpjulab.kioskhealthcare;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.net.Socket;

/**
 * Created by devil on 9/19/15.
 */
public class NewExistingPatient extends Activity{

    private Button newPatient,existPatient;
    private ImageButton back;
    public ProgressDialog progressDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from new_activity.xml

        setContentView(R.layout.existing_new_patient);
        initiate();


        newPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LockUnlock lc=new LockUnlock();
                lc.execute();

            }
        });

        existPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(NewExistingPatient.this, Patient_login.class));
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    KioskLogin.conFinal.disconnect();
                    finish();
                }
                catch (Exception e)
                {
                    finish();
                }


            }
        });
    }

    @Override
    public void onBackPressed()
    {

    }

    public void initiate()
    {
        newPatient=(Button)findViewById(R.id.existing_new_patient_newId);
        existPatient=(Button)findViewById(R.id.existing_new_patient_existId);
        back=(ImageButton)findViewById(R.id.existing_new_patient_backId);

    }

    public void showDialogue(String s) {
        Toast.makeText(NewExistingPatient.this, s, Toast.LENGTH_SHORT).show();
    }


    public class LockUnlock extends AsyncTask<Void,Void,Void> {

        public boolean isAlright;
        public int response;

        @Override
        protected void onPreExecute()
        {
            isAlright=false;
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            try{

                response=KioskLogin.conFinal.lockFile("Patient_"+MainActivity.KIOSK_NUMBER+"_IdCount.txt");

                isAlright=true;
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
            if(isAlright==false)
            {
                showDialogue("Connection Error");
            }
            else
            {
                if(response<0)
                {
                    showDialogue("File Locked.Try again later");
                }
                else
                {

                    startActivity(new Intent(NewExistingPatient.this, RegisterNewPatient.class));
                    finish();
                }
            }

        }
    }

}
