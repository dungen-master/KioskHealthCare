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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * Created by devil on 9/20/15.
 */
public class ExistPatientBasic extends Activity{

    public static TextView regvalue,status,date,downloadImage;
    public Button edit,next,cancel;
    public static EditText nameEdit,relnameEdit,ageEdit,phoneEdit,heightEdit,occuEdit,addressEdit,famhisEdit,medhisEdit;
    public static ImageView image;
    public RadioGroup gen,relgen;
    public Spinner blood;
    public static RadioButton male,female,wife,daughter,son;
    public static String nameVar,phoneVar,addressVar,patientId,genderVar,imageString,
            referenceVar,ageVar,occupationVar,statusVar,heightVar,
            familyhistoryVar,medicalhistoryVar,dateVar,referenceName,referenceGender,bloodGroupVar;

    public String localDir,imagePath,imageName,serverDir;

    public static boolean isImageTaken;
    public boolean imageDownloaded,isSaveClicked;
    public  int editClicked;
    public ProgressDialog progressDialog;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from new_activity.xml

        setContentView(R.layout.patient_exist_basic);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        initiate();
        getvalues();
        setValues();
        enableField(false);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(editClicked==0) {

                    LockUnlockFile luf = new LockUnlockFile();
                    luf.execute();
                }
                else
                    enableField(true);

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (next.getText().toString().equals("SAVE"))
                {
                    if(checkField() && checkRelation()) {

                        isSaveClicked = true;
                        enableField(false);
                    }

                }
                else if (next.getText().toString().equals("NEXT") ) {

                    if(isSaveClicked==true)
                    {
                        AlertDialog.Builder alt=new AlertDialog.Builder(ExistPatientBasic.this);
                        alt.setIcon(R.drawable.alert6);
                        alt.setTitle("Update");
                        alt.setCancelable(false);
                        alt.setMessage("Update if neccessary");
                        alt.setCancelable(false);

                        alt.setPositiveButton("Update", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                progressDialog=new ProgressDialog(ExistPatientBasic.this);
                                progressDialog.setIndeterminate(true);
                                progressDialog.setCancelable(false);
                                progressDialog.setMessage("Updating");

                                new android.os.Handler().postDelayed(
                                        new Runnable() {
                                            @Override
                                            public void run() {

                                                takeValues();
                                                sendFiles();

                                            }
                                        }, 1000);
                                progressDialog.show();
                            }
                        });

                        alt.setNegativeButton("Don't Update", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                deleteImage();
                                deleteFiles();

                                unlockFile();
                            }
                        });

                        alt.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        alt.show();
                    }
                    else
                    {
                        deleteImage();
                        deleteFiles();
                        unlockFile();
                    }
//
//                    takeValues();
//
//                    if(isImageTaken==false) {
//
//                        deleteImage();
//                        startActivity(new Intent(ExistPatientBasic.this, TabInformation.class));
//                        finish();
//                    }
//                    else
//                    {
//                        sendFiles();
//                    }
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteImage();
                getvalues();
                setValues();
                enableField(false);
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alt=new AlertDialog.Builder(ExistPatientBasic.this);
                alt.setTitle("Ready to take Image???");
                alt.setMessage("If any,Previous image will be deleted");
                alt.setIcon(R.drawable.alert5);
                alt.setCancelable(false);

                alt.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        takePicture();
                    }
                });


                alt.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alt.show();

            }
        });

        downloadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               download();
            }
        });

    }

    @Override
    public void onBackPressed()
    {

    }

    public void initiate()
    {
        regvalue=(TextView)findViewById(R.id.patient_exist_basic_regId);
        status=(TextView)findViewById(R.id.patient_exist_basic_stvalueId);
        date=(TextView)findViewById(R.id.patient_exist_basic_dateId);
        downloadImage=(TextView)findViewById(R.id.patient_exist_basic_downloadId);
        downloadImage.setClickable(true);


        edit=(Button)findViewById(R.id.patient_exist_basic_editId);
        next=(Button)findViewById(R.id.patient_exist_basic_nextId);
        cancel=(Button)findViewById(R.id.patient_exist_basic_cancelId);

        nameEdit=(EditText)findViewById(R.id.patient_exist_basic_namepatientId);
        relnameEdit=(EditText)findViewById(R.id.patient_exist_basic_relnameId);
        ageEdit=(EditText)findViewById(R.id.patient_exist_basic_ageId);
        phoneEdit=(EditText)findViewById(R.id.patient_exist_basic_phoneId);
        heightEdit=(EditText)findViewById(R.id.patient_exist_basic_heightId);
        occuEdit=(EditText)findViewById(R.id.patient_exist_basic_occuId);
        addressEdit=(EditText)findViewById(R.id.patient_exist_basic_addressId);
        famhisEdit=(EditText)findViewById(R.id.patient_exist_basic_familyhisId);
        medhisEdit=(EditText)findViewById(R.id.patient_exist_basic_medicalhisId);

        image=(ImageView)findViewById(R.id.patient_exist_basic_imageId);

        blood=(Spinner)findViewById(R.id.patient_exist_basic_bgId);

        gen=(RadioGroup)findViewById(R.id.patient_exist_basic_patientgenId);
        relgen=(RadioGroup)findViewById(R.id.patient_exist_basic_relgenId);

        male=(RadioButton)findViewById(R.id.patient_exist_basic_MaleId);
        female=(RadioButton)findViewById(R.id.patient_exist_basic_FemaleId);
        son=(RadioButton)findViewById(R.id.patient_exist_basic_sonId);
        daughter=(RadioButton)findViewById(R.id.patient_exist_basic_daughterId);
        wife=(RadioButton)findViewById(R.id.patient_exist_basic_wifeId);

        localDir= MainActivity.DirLocal;
        serverDir=MainActivity.SERVER_DIR;

        isImageTaken=false;

        imageDownloaded=false;
        isSaveClicked=false;
        editClicked=0;

        //add scrolling to edit texts

        addressEdit.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View view, MotionEvent event) {
                // TODO Auto-generated method stub
                if (view.getId() == R.id.patient_exist_basic_addressId) {
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
                if (view.getId() == R.id.patient_exist_basic_familyhisId) {
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
                if (view.getId() == R.id.patient_exist_basic_medicalhisId) {
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

    public void setValues()
    {
        regvalue.setText(patientId);
        status.setText(statusVar);
        date.setText(dateVar);

        nameEdit.setText(nameVar);
        relnameEdit.setText(referenceName);
        ageEdit.setText(ageVar);
        phoneEdit.setText(phoneVar);
        heightEdit.setText(heightVar);
        occuEdit.setText(occupationVar);
        addressEdit.setText(addressVar);
        famhisEdit.setText(familyhistoryVar);
        medhisEdit.setText(medicalhistoryVar);

        int index=0;

//        showDialogue(bloodGroupVar);

        if(bloodGroupVar!=null && (!bloodGroupVar.equals("")))
        {

            for(int i=0;i<blood.getCount();i++)
            {
              if(blood.getItemAtPosition(i).equals(bloodGroupVar))
                  index=i;
            }

            blood.setSelection(index);
        }

        if(genderVar.equals("Male"))
            male.setChecked(true);
        else if(genderVar.equals("Female"))
            female.setChecked(true);

        if(referenceGender.equals("Son"))
            son.setChecked(true);
        else if(referenceGender.equals("Daughter"))
            daughter.setChecked(true);
        else if(referenceGender.equals("Wife"))
            wife.setChecked(true);



    }

    private boolean checkField()
    {
        String nameVar=nameEdit.getText().toString();
        String ageVar=ageEdit.getText().toString();
        String relNameVar=relnameEdit.getText().toString();
        String phoneVar=phoneEdit.getText().toString();
        String occuVar=occuEdit.getText().toString();
        String heightVar=heightEdit.getText().toString();
        String addressVar=addressEdit.getText().toString();
        String genVar="";
        String relgenVar="";

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


    public void getvalues()
    {
        nameVar=Patient_login.patient_report.PatientBasicData.getName();
        ageVar=Patient_login.patient_report.PatientBasicData.getAge();
        heightVar=Patient_login.patient_report.PatientBasicData.getHeight();
        phoneVar=Patient_login.patient_report.PatientBasicData.getPhone();
        addressVar=Patient_login.patient_report.PatientBasicData.getAddress();
        patientId=Patient_login.patient_report.PatientBasicData.getId();
        genderVar=Patient_login.patient_report.PatientBasicData.getGender();
        imageString=Patient_login.patient_report.PatientBasicData.getImage();
        referenceVar=Patient_login.patient_report.PatientBasicData.getReference();
        occupationVar=Patient_login.patient_report.PatientBasicData.getOccupation();
        statusVar=Patient_login.patient_report.PatientBasicData.getStatus();
        familyhistoryVar=Patient_login.patient_report.PatientBasicData.getFamilyhistory();
        medicalhistoryVar=Patient_login.patient_report.PatientBasicData.getMedicalhistory();
        dateVar=Patient_login.patient_report.PatientBasicData.getDate();
        bloodGroupVar=Patient_login.patient_report.PatientBasicData.getBloodGroup();

        imageName=Patient_login.patient_report.PatientBasicData.getImage();
        imagePath=localDir+"/"+imageName;

        Date d = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
        this.dateVar = ft.format(d);

        getReferenceNameGen();

    }

    public void endConnection()
    {
        deleteFiles();
        deleteImage();
        finish();
    }

    public void getReferenceNameGen()
    {
        StringTokenizer st=new StringTokenizer(referenceVar);

        if(referenceVar.charAt(0)=='D') {
            referenceGender = "Daughter";

        }

        else if(referenceVar.charAt(0)=='W') {

            referenceGender = "Wife";
        }
        else
        {
            referenceGender="Son";
        }

        while (st.hasMoreTokens())
        {
            referenceName=st.nextToken();
            if(referenceName.equals("of"))
                break;
        }

        referenceName="";
        while(st.hasMoreTokens())
            referenceName+=st.nextToken()+" ";

    }

    public void enableField(boolean flag)
    {
        nameEdit.setEnabled(flag);
        relnameEdit.setEnabled(flag);
        ageEdit.setEnabled(flag);
        phoneEdit.setEnabled(flag);
        heightEdit.setEnabled(flag);
        occuEdit.setEnabled(flag);
        addressEdit.setEnabled(flag);
        famhisEdit.setEnabled(flag);
        medhisEdit.setEnabled(flag);

        image.setEnabled(flag);

        blood.setEnabled(flag);

        male.setEnabled(flag);
        female.setEnabled(flag);

        son.setEnabled(flag);
        daughter.setEnabled(flag);
        wife.setEnabled(flag);


        if(flag==false) {

            edit.setVisibility(View.VISIBLE);
            edit.setClickable(true);

            cancel.setClickable(false);
            cancel.setVisibility(View.INVISIBLE);

            next.setText("NEXT");
        }
        else
        {
            cancel.setClickable(true);
            cancel.setVisibility(View.VISIBLE);

            edit.setClickable(false);
            edit.setVisibility(View.INVISIBLE);

            next.setText("SAVE");
        }


    }


    public void download()
    {
        if(imageDownloaded==false) {

            DownloadClient dcl = new DownloadClient();
            dcl.execute();
        }
    }


    public void takeValues()
    {
        nameVar=nameEdit.getText().toString();
        phoneVar=phoneEdit.getText().toString();
        addressVar=addressEdit.getText().toString();
        patientId=regvalue.getText().toString();

        if(male.isChecked())
            genderVar="Male";
        else if(female.isChecked())
            genderVar="Female";


        ageVar=ageEdit.getText().toString();
        occupationVar=occuEdit.getText().toString();
        statusVar=status.getText().toString();
        bloodGroupVar=blood.getSelectedItem().toString();
        heightVar=heightEdit.getText().toString();
        familyhistoryVar=famhisEdit.getText().toString();
        medicalhistoryVar=medhisEdit.getText().toString();
        dateVar=date.getText().toString();
        referenceName=relnameEdit.getText().toString();

        if(son.isChecked())
            referenceGender="Son";
        else if(daughter.isChecked())
            referenceGender="Daughter";
        else referenceGender="Wife";

        referenceVar=referenceGender+" of "+referenceName;

    }

    public void createData(Patient_Report pr)
    {
        pr.PatientBasicData.setName(nameVar);
        pr.PatientBasicData.setStatus(statusVar);
        pr.PatientBasicData.setId(patientId);
        pr.PatientBasicData.setDate(dateVar);

        pr.PatientBasicData.setName(ExistPatientBasic.nameVar);
        pr.PatientBasicData.setAge(ExistPatientBasic.ageVar);
        pr.PatientBasicData.setPhone(ExistPatientBasic.phoneVar);
        pr.PatientBasicData.setGender(ExistPatientBasic.genderVar);
        pr.PatientBasicData.setBloodGroup(ExistPatientBasic.bloodGroupVar);
        pr.PatientBasicData.setReference(ExistPatientBasic.referenceVar);
        pr.PatientBasicData.setAddress(ExistPatientBasic.addressVar);
        pr.PatientBasicData.setOccupation(ExistPatientBasic.occupationVar);
        pr.PatientBasicData.setFamilyhistory(ExistPatientBasic.familyhistoryVar);
        pr.PatientBasicData.setMedicalhistory(ExistPatientBasic.medicalhistoryVar);
        pr.PatientBasicData.setImage(ExistPatientBasic.imageString);
        pr.PatientBasicData.setHeight(ExistPatientBasic.heightVar);

    }


    public void showDialogue(String s)
    {
        Toast.makeText(ExistPatientBasic.this,s,Toast.LENGTH_SHORT).show();
    }

    private void deleteImage() {
        File f = new File(imagePath);
        if(f.exists())
            f.delete();


        f=new File(localDir+"/temp_patient_pic.jpg");

        if(f.exists())
            f.delete();


    }

    public void deleteFiles()
    {
        File f=new File(localDir+"/tempPatient.xml");
        if(f.exists())
            f.delete();
    }


    public void takePicture() {

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
                File tempImgFile=new File(localDir+"/temp_patient_pic.jpg");

                if(tempImgFile.exists()) {

                    isImageTaken = true;


                    BitmapFactory.Options bmp = new BitmapFactory.Options();
                    Bitmap photo = BitmapFactory.decodeFile(tempImgFile.getAbsolutePath(), bmp);

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

                    tempImgFile.delete();
                }


            }catch (Exception e)
            {
                e.printStackTrace();
                showDialogue("Image processing failed");
            }
        }
    }


    public void showDownloadImage()
    {
        try{

            image.setImageURI(null);
            image.setImageURI(Uri.fromFile(new File(imagePath)));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            showDialogue("Image processing failed");
        }
    }

    public class DownloadClient extends AsyncTask<Void,Void,Void>{

        public boolean isAlright;
        public int filePresent;

        @Override
        protected void onPreExecute()
        {
            isAlright=false;
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            try{

                filePresent=KioskLogin.conFinal.receiveFromServer(imageName,localDir+"/"+imageName);
                if(filePresent>=0)
                {
                    isAlright=true;
                }

            }catch (Exception e)
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
                imageDownloaded=true;
                showDownloadImage();
            }
            else
            {
                showDialogue("No image Exists");
            }
        }
    }

    public void sendFiles()
    {
        sendUpdate snd=new sendUpdate();
        snd.execute();
    }

    public class sendUpdate extends AsyncTask<Void,Void,Void>{

        public boolean isAlright;
        Patient_Report pr;

        @Override
        protected void onPreExecute()
        {
            isAlright=false;
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            try{

                if(KioskLogin.conFinal.receiveFromServer(patientId+".xml",localDir+"/tempPatient.xml")>=0)
                {
                    File f=new File(localDir+"/tempPatient.xml");

                    if(f.exists()) {


                        Serializer sr = new Persister();
                        pr=sr.read(Patient_Report.class, f);

                        createData(pr);
                        isAlright=true;

                    }
                    else
                        isAlright=false;
                }
                else
                    isAlright=false;

                if(isAlright==true)
                {
                    File newData=new File(localDir+"/tempPatient.xml");
                    Serializer sp=new Persister();
                    sp.write(pr,newData);

                    if(newData.exists())
                    {
                        if(KioskLogin.conFinal.sendToServer(localDir+"/tempPatient.xml",serverDir+"/"+patientId+".xml")>=0)
                        {
                            isAlright=true;
                        }
                        else
                            isAlright=false;
                    }
                    else
                        isAlright=false;
                }

                if(isImageTaken==true) {

                    if (KioskLogin.conFinal.sendToServer(imagePath, serverDir + "/" + imageName) >= 0) {
                        isAlright = true;
                    }
                    else
                        isAlright = false;

                }

                int lockResponse2=KioskLogin.conFinal.unlockFile(patientId+".xml");

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
                deleteImage();
                deleteFiles();
                progressDialog.dismiss();
                startActivity(new Intent(ExistPatientBasic.this, TabInformation.class));
                finish();
            }
            else
            {
                progressDialog.dismiss();
                showDialogue("Connection Error");
                endConnection();
            }
        }
    }


    public class LockUnlockFile extends AsyncTask<Void,Void,Void>{

        public int lockResponse;
        public boolean isAlright;

        @Override
        protected void onPreExecute()
        {
            isAlright=false;
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            try{

                lockResponse=KioskLogin.conFinal.lockFile(patientId+".xml");
                isAlright=true;

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            if(isAlright==true)
            {
                if(lockResponse>=0) {
                    editClicked++;
                    enableField(true);
                }
                else
                {
                    showDialogue("Patient File in use.Try again...");
                }
            }
            else
            {
                showDialogue("Connection Error");
                endConnection();
            }
        }
    }

    public void unlockFile()
    {
        UnlockFile un=new UnlockFile();
        un.execute();
    }

    public class UnlockFile extends AsyncTask<Void,Void,Void>{

        public int lockResponse;
        public boolean isAlright;

        @Override
        protected void onPreExecute()
        {
            isAlright=false;
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            try{

                lockResponse=KioskLogin.conFinal.unlockFile(patientId + ".xml");
                isAlright=true;

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            if(isAlright==true)
            {
                deleteFiles();
                deleteImage();
                startActivity(new Intent(ExistPatientBasic.this,TabInformation.class));
                finish();
            }
            else
            {
                showDialogue("Connection Error");
                endConnection();
            }
        }
    }



}
