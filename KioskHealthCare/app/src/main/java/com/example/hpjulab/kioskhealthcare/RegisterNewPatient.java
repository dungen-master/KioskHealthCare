package com.example.hpjulab.kioskhealthcare;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * Created by devil on 9/19/15.
 */
public class RegisterNewPatient extends Activity {

    private static final int Camera_request = 100;

    public TextView regvalue, status, date;
    public Button reset, submit, back;
    public EditText nameEdit, relnameEdit, ageEdit, phoneEdit, heightEdit, occuEdit, addressEdit, famhisEdit, medhisEdit;
    public ImageView image;
    public RadioGroup genderGroup, relgenderGroup;
    public RadioButton male, female, wife, daughter, son;
    public Spinner blood;
    public String imagePath,imageName, localDir, serverDir, dateVar, KioskNumber,patientId,serverTempDir;
    public String nameVar,relNameVar,bloodGroupVar,ageVar,phoneVar,heightVar,occuVar,addressVar,famhisVar,medhisVar,genVar,relgenVar,statusVar,imageString;
    public int countId,asyncId;
    public boolean isImageTaken;
    public ProgressDialog progressDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from new_activity.xml

        setContentView(R.layout.register_new_patient);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        statusVar="New";
        initiate();


        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetField();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValues();
                if(checkField() && checkRelation())
                {
                    takeCare();
                }



            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                LockUnlock lc=new LockUnlock();
                lc.execute();
            }
        });


    }

    @Override
    public void onBackPressed()
    {

    }

    public void initiate() {
        regvalue = (TextView) findViewById(R.id.register_new_patient_regId);
        status = (TextView) findViewById(R.id.register_new_patient_stvalueId);
        date = (TextView) findViewById(R.id.register_new_patient_dateId);
        status.setText(statusVar);

        Date d = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
        this.dateVar = ft.format(d);

        date.setText(this.dateVar);

        reset = (Button) findViewById(R.id.register_new_patient_resetId);
        submit = (Button) findViewById(R.id.register_new_patient_submitId);
        back = (Button) findViewById(R.id.register_new_patient_backId);

        nameEdit = (EditText) findViewById(R.id.register_new_patient_namepatientId);
        relnameEdit = (EditText) findViewById(R.id.register_new_patient_relnameId);
        ageEdit = (EditText) findViewById(R.id.register_new_patient_ageId);
        phoneEdit = (EditText) findViewById(R.id.register_new_patient_phoneId);
        heightEdit = (EditText) findViewById(R.id.register_new_patient_heightId);
        occuEdit = (EditText) findViewById(R.id.register_new_patient_occuId);
        addressEdit = (EditText) findViewById(R.id.register_new_patient_addressId);
        famhisEdit = (EditText) findViewById(R.id.register_new_patient_familyhisId);
        medhisEdit = (EditText) findViewById(R.id.register_new_patient_medicalhisId);

        image = (ImageView) findViewById(R.id.register_new_patient_imageId);
        image.setClickable(true);

        genderGroup = (RadioGroup) findViewById(R.id.register_new_patient_patientgenId);
        relgenderGroup = (RadioGroup) findViewById(R.id.register_new_patient_relgenId);

        male = (RadioButton) findViewById(R.id.register_new_patient_MaleId);
        female = (RadioButton) findViewById(R.id.register_new_patient_FemaleId);
        son = (RadioButton) findViewById(R.id.register_new_patient_sonId);
        daughter = (RadioButton) findViewById(R.id.register_new_patient_daughterId);
        wife = (RadioButton) findViewById(R.id.register_new_patient_wifeId);

        blood=(Spinner)findViewById(R.id.register_new_patient_bgId);

        //add scrolling

        addressEdit.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View view, MotionEvent event) {
                // TODO Auto-generated method stub
                if (view.getId() == R.id.register_new_patient_addressId) {
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

        famhisEdit.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View view, MotionEvent event) {
                // TODO Auto-generated method stub
                if (view.getId() == R.id.register_new_patient_familyhisId) {
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

        medhisEdit.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View view, MotionEvent event) {
                // TODO Auto-generated method stub
                if (view.getId() == R.id.register_new_patient_medicalhisId) {
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


//        localDir = getFilesDir().toString();
        localDir = MainActivity.DirLocal;
        KioskNumber = MainActivity.KIOSK_NUMBER;
        serverTempDir=MainActivity.SERVER_TEMP_DIR;
        serverDir=MainActivity.SERVER_DIR;
        isImageTaken=false;
        createId();

    }



    public void showDialogue(String s) {
        Toast.makeText(RegisterNewPatient.this, s, Toast.LENGTH_SHORT).show();
    }

    public void resetField() {
        nameEdit.setText("");
        relnameEdit.setText("");
        ageEdit.setText("");
        phoneEdit.setText("");
        heightEdit.setText("");
        occuEdit.setText("");
        addressEdit.setText("");
        famhisEdit.setText("");
        medhisEdit.setText("");

        male.setChecked(false);
        female.setChecked(false);

        son.setChecked(false);
        daughter.setChecked(false);
        wife.setChecked(false);
    }

    private void getValues()
    {
        nameVar=relNameVar=ageVar=phoneVar=heightVar=occuVar=addressVar=famhisVar=medhisVar=bloodGroupVar="";

        nameVar=nameEdit.getText().toString();
        relNameVar=relnameEdit.getText().toString();
        ageVar=ageEdit.getText().toString();
        phoneVar=phoneEdit.getText().toString();
        heightVar=heightEdit.getText().toString();
        occuVar=occuEdit.getText().toString();
        addressVar=addressEdit.getText().toString();
        famhisVar=famhisEdit.getText().toString();
        medhisVar=medhisEdit.getText().toString();
        genVar="";
        relgenVar="";

       if(male.isChecked())
           genVar="Male";
        else if(female.isChecked())
           genVar="Female";

        if(son.isChecked())
            relgenVar="Son";
        else if(daughter.isChecked())
            relgenVar="Daughter";
        else if(wife.isChecked())
            relgenVar="Wife";

        bloodGroupVar=blood.getSelectedItem().toString();

    }

    private boolean checkField()
    {
        boolean check=(nameVar.trim().isEmpty() || ageVar.trim().isEmpty() || relNameVar.trim().isEmpty() || phoneVar.trim().isEmpty() ||
                        occuVar.trim().isEmpty() || heightVar.trim().isEmpty() || addressVar.trim().isEmpty() || genVar.trim().isEmpty()
                        || relgenVar.trim().isEmpty());

        if(check==true)
        {
            showDialogue("Enter necessary fields");
            return false;
        }
        else
        {
            boolean nameCheck=nameVar.matches(".*[0-9]+.*");
            boolean sdwCheck=relnameEdit.getText().toString().matches(".*[0-9]+.*");
            boolean occupationCheck=occuVar.matches(".*[0-9]+.*");
            boolean phoneCheck=phoneEdit.getText().toString().matches(".*[a-zA-Z]+.*");
            boolean ageCheck=ageVar.matches(".*[a-zA-Z]+.*") || (Float.parseFloat(ageVar) <0 || Float.parseFloat(ageVar) >120);
            boolean heightCheck=heightVar.matches(".*[a-zA-Z]+.*") || (Float.parseFloat(heightVar) < 30 || Float.parseFloat(heightVar) > 240);

            if(nameCheck)
            {
                showDialogue("Name should not contain any numbers");
                nameEdit.setText(null);
                return false;
            }

            if(sdwCheck)
            {
                showDialogue("Relation Name should not contain any numbers");
                relnameEdit.setText(null);
                return false;
            }
             if(occupationCheck)
             {
                 showDialogue("Occupation should not contain any numbers");
                 occuEdit.setText(null);
                 return false;
             }

            if(phoneCheck)
            {
                showDialogue("Phone should not contain any character");
                phoneEdit.setText(null);
                return false;
            }

            if(ageCheck)
            {
                showDialogue("Check Age field");
                ageEdit.setText(null);
                return false;
            }

            if(heightCheck)
            {
                showDialogue("Check height field");
                heightEdit.setText(null);
                return  false;
            }
            return true;

        }
    }

    private boolean checkRelation()
    {
        if(male.isChecked() && (wife.isChecked() || daughter.isChecked()))
        {
            if(wife.isChecked())
                showDialogue("CHECK GENDER....(Wife cannot be male)");
            else if(daughter.isChecked())
                showDialogue("CHECK GENDER....(Daughter cannot be male)");

            return false;
        }
        else if(female.isChecked() && son.isChecked())
        {
            showDialogue("CHECK GENDER....(Son cannot be female)");
            return false;
        }

        return true;


    }

    private void takeCare()
    {
        this.incrementPatientIdCount();
    }

    private void incrementPatientIdCount()
    {

        progressDialog=new ProgressDialog(RegisterNewPatient.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Registering Patient...");


        new android.os.Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {

                        asyncId=2;
                        CreateIdSocket incre=new CreateIdSocket();
                        incre.execute();

                    }
                }, 1000);
        progressDialog.show();

    }


    private void deleteImage() {
        File f = new File(imagePath);
        if(f.exists())
            f.delete();

        f=new File(localDir+"/temp_patient_pic.jpg");

        if(f.exists())
            f.delete();

    }


    private void takePicture() {

        try{

            Intent camera=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File f=new File(localDir+"/temp_patient_pic.jpg");
            camera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
            startActivityForResult(camera,12);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            showDialogue("Can't take image");
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==12 && resultCode==RESULT_OK)
        {
            try{

                File tempImage=new File(localDir+"/temp_patient_pic.jpg");

                if(tempImage.exists()) {

                    BitmapFactory.Options bmp = new BitmapFactory.Options();
                    Bitmap photo = BitmapFactory.decodeFile(tempImage.getAbsolutePath(), bmp);

                    photo = Bitmap.createScaledBitmap(photo, 300, 300, true);
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.JPEG, 100, bytes);


                    File f2 = new File(imagePath);
                    f2.createNewFile();

                    FileOutputStream fo = new FileOutputStream(f2);
                    fo.write(bytes.toByteArray());
                    fo.close();

                    image.setImageURI(null);
                    image.setImageURI(Uri.fromFile(new File(imagePath)));
                    isImageTaken=true;
                }

            }catch (Exception e)
            {
                e.printStackTrace();
                showDialogue("Image processing failed");
            }

        }
    }



    private void createId() {
        asyncId = 1;
        CreateIdSocket cr = new CreateIdSocket();
        cr.execute();
    }

    private void endConnection() {
        deleteImage();
        File f=new File(localDir+"/PatientIdCount.txt");

        if(f.exists())
            f.delete();

        f=new File(localDir+"/tempPatient.xml");

        if(f.exists())
            f.delete();

        finish();
    }


    private  void encodeToString() {

      imageString=patientId+"_image.jpg";
    }


    public void createpatientBasicData()
    {
        Patient_Report patient_Report=new Patient_Report();
        patient_Report.PatientBasicData=new PatientBasicData();


        patient_Report.PatientBasicData.setId(patientId);
        patient_Report.PatientBasicData.setName(nameVar);
        patient_Report.PatientBasicData.setDate(dateVar);
        patient_Report.PatientBasicData.setAddress(addressVar);
        patient_Report.PatientBasicData.setPhone(phoneVar);
        patient_Report.PatientBasicData.setGender(genVar);
        patient_Report.PatientBasicData.setReference(relgenVar + " of " + relNameVar);
        patient_Report.PatientBasicData.setAge(ageVar);
        patient_Report.PatientBasicData.setOccupation(occuVar);
        patient_Report.PatientBasicData.setBloodGroup(bloodGroupVar);
        patient_Report.PatientBasicData.setStatus(statusVar);
        patient_Report.PatientBasicData.setHeight(heightVar);
        patient_Report.PatientBasicData.setImage(imageString);
        patient_Report.PatientBasicData.setFamilyhistory(famhisVar);
        patient_Report.PatientBasicData.setMedicalhistory(medhisVar);



        try {
            Serializer sr = new Persister();
            File f=new File(localDir+"/tempPatient.xml");
            sr.write(patient_Report,f);

        }catch (Exception e)
        {
            progressDialog.dismiss();

            e.printStackTrace();
            showDialogue("Error writing file");
            endConnection();
        }

        RegisterPatient rp= new RegisterPatient();
        rp.execute();
    }



    //Async task for network

    private class CreateIdSocket extends AsyncTask<Void, Void, Void> {
        private boolean isAlright1,isAlright2;

        @Override
        protected void onPreExecute() {
            if(asyncId==1)
                isAlright1 = false;
            else if(asyncId==2)
                isAlright2=false;
        }

        @Override
        protected Void doInBackground(Void... params) {

            if(asyncId==1) //for create ID
            {
                try {



                    if (KioskLogin.conFinal.receiveFromServer("Patient_" + KioskNumber + "_IdCount.txt", localDir + "/PatientIdCount.txt")>=0) {
                        BufferedReader bin = new BufferedReader(new FileReader(localDir + "/PatientIdCount.txt"));
                        countId = Integer.parseInt(bin.readLine());
                        bin.close();
                        String temp = "Patient_" + KioskNumber + "_" + String.format("%03d", (countId++));
                        patientId = temp;

                        if ((new File(localDir + "/PatientIdCount.txt")).isFile())
                            (new File(localDir + "/PatientIdCount.txt")).delete();

                        isAlright1 = true;
                    } else {
                        isAlright1 = false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    isAlright1 = false;
                }
                return null;
            }

            else if(asyncId==2)// for increment patient id count
            {
                try
                {
                    File file=new File(localDir+"/PatientIdCount.txt");
                    file.createNewFile();
                    BufferedWriter bout=new BufferedWriter(new FileWriter(file));
                    bout.write(String.valueOf(countId));
                    bout.close();

                    if(KioskLogin.conFinal.sendToServer(localDir+"/PatientIdCount.txt",serverDir+"/Patient_"+KioskNumber+"_IdCount.txt")<0)
                    {
                        isAlright2=false;
                        file.delete();

                    }
                    else
                    {
                        file.delete();
                        isAlright2=true;
                    }
                }
                catch(Exception e)
                {
                    isAlright2=false;
                    e.printStackTrace();
                }
                return null;
            }
            return null;

        }


        @Override
        protected void onPostExecute(Void result) {
            if(asyncId==1)
            {
                if (isAlright1 == false) {
                    showDialogue("Error in Connection");
                    endConnection();
                }
                else {

                    regvalue.setText(patientId);
                    imagePath = localDir+"/"+patientId+"_"+"image.jpg";
                    imageName=patientId+"_"+"image.jpg";
                }
            }
            else if(asyncId==2)
            {
                if(isAlright2==false)
                {
                    progressDialog.dismiss();

                    showDialogue("Error in connection");
                    endConnection();
                }
                else
                {
                    encodeToString();
                    createpatientBasicData();
                }
            }

        }
    }

    //Async task for register patient


    private class RegisterPatient extends AsyncTask<Void, Void, Void> {
        private boolean isAlright;

        @Override
        protected void onPreExecute() {
            isAlright=false;
        }

        @Override
        protected Void doInBackground(Void... params) {

            try
            {
                if(KioskLogin.conFinal.sendToServer(localDir + "/tempPatient.xml", serverTempDir + "/" + patientId + ".xml")>=0)
                {
                    isAlright=true;
                }
                (new File(localDir+"/tempPatient.xml")).delete();


                if(isImageTaken==true) {

                    if (KioskLogin.conFinal.sendToServer(imagePath, serverTempDir + "/" + imageName) >= 0) {
                        isAlright = true;
                    } else isAlright = false;
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
                isAlright=false;
            }
            return null;

        }


        @Override
        protected void onPostExecute(Void result) {


            if(isAlright==false)
            {
                progressDialog.dismiss();

                showDialogue("Error Connection");
                endConnection();
            }
            else if(isAlright==true)
            {
                progressDialog.dismiss();

                AlertDialog.Builder alt=new AlertDialog.Builder(RegisterNewPatient.this);
                alt.setTitle("Register Successfully");
                alt.setIcon(R.drawable.alert6);
                alt.setCancelable(false);

                alt.setPositiveButton("FINISH", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        LockUnlock lc=new LockUnlock();
                        lc.execute();
                    }
                });

                alt.show();


            }

        }
    }

    public class LockUnlock extends AsyncTask<Void,Void,Void>{

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

                response=KioskLogin.conFinal.unlockFile("Patient_" + KioskNumber + "_IdCount.txt");
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

            startActivity(new Intent(RegisterNewPatient.this, NewExistingPatient.class));
            endConnection();

        }
    }

}



