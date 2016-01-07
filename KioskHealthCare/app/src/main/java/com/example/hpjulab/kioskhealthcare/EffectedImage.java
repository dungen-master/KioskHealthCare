package com.example.hpjulab.kioskhealthcare;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by devil on 9/28/15.
 */
public class EffectedImage extends Activity {
    public int CAMERA_REQUEST_CODE=13;

    public Button snapBtn,loadBtn;
    public TextView regvalue,status,date;
    public ImageView effectedImg;
    public File tempImgFile,imageFile;

    public String localDir,patientId,statusVar,dateVar,imageName;
    public static String imagePath;
    public static boolean effected_imageTaken=false;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.effected_image_patient);

        initiate();

        snapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(PatientForm.effectedImageCanbeTaken==false)
                {
                    AlertDialog.Builder alt=new AlertDialog.Builder(EffectedImage.this);
                    alt.setIcon(R.drawable.alert5);
                    alt.setTitle("Doctor Not Available");
                    alt.setMessage("Previous complaint not seen.");
                    alt.setCancelable(false);

                    alt.setPositiveButton("OK",new DialogInterface.OnClickListener(){

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alt.show();
                }
                else
                {
                    takePicture();
                }


            }
        });

        loadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndLoad();
            }
        });
    }

    public void initiate()
    {
        snapBtn=(Button)findViewById(R.id.effectd_image_patient_snapId);
        loadBtn=(Button)findViewById(R.id.effectd_image_patient_loadId);

        effectedImg=(ImageView)findViewById(R.id.effectd_image_patient_imageId);
        effectedImg.setScaleType(ImageView.ScaleType.FIT_XY);

        regvalue=(TextView)findViewById(R.id.effectd_image_patient_regId);
        status=(TextView)findViewById(R.id.effectd_image_patient_stId);
        date=(TextView)findViewById(R.id.effectd_image_patient_dateId);

        patientId=Patient_login.patient_report.PatientBasicData.getId();
        statusVar=Patient_login.patient_report.PatientBasicData.getStatus();

        Date d = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy");
        this.dateVar = ft.format(d);

        regvalue.setText(patientId);
        status.setText(statusVar);
        date.setText(dateVar);


        localDir= MainActivity.DirLocal;
        imagePath=localDir+"/"+patientId+"_effected_image.jpg";
        imageName=patientId+"_effected_image.jpg";


    }

    public void checkAndLoad()
    {
        LoadImage li=new LoadImage();
        li.execute();
    }

    private void takePicture() {

        try{

            Intent camera=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File f=new File(localDir+"/temp_effected_pic.jpg");
            camera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
            startActivityForResult(camera,CAMERA_REQUEST_CODE);
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

        if(requestCode==CAMERA_REQUEST_CODE && resultCode==RESULT_OK)
        {
            try{

                File tempImage=new File(localDir+"/temp_effected_pic.jpg");

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

                    effectedImg.setImageURI(null);
                    effectedImg.setImageURI(Uri.fromFile(new File(imagePath)));

                    tempImage.delete();
                    effected_imageTaken=true;
                }

            }catch (Exception e)
            {
                e.printStackTrace();
                showDialogue("Image processing failed");
            }

        }
    }

    public void showDialogue(String s)
    {
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }

    public class LoadImage extends AsyncTask<Void,Void,Void>{

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

                if(KioskLogin.conFinal.receiveFromServer(imageName,imagePath)>=0)
                {
                    isAlright=true;
                }

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
                File f=new File(imagePath);
                effectedImg.setImageURI(null);
                effectedImg.setImageURI(Uri.fromFile(f));
            }
            else
            {
                AlertDialog.Builder alt=new AlertDialog.Builder(EffectedImage.this);
                alt.setIcon(R.drawable.alert5);
                alt.setTitle("Image Not Found");
                alt.setMessage("Take image if necessary");
                alt.setCancelable(false);

                alt.setPositiveButton("OK",new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alt.show();
            }
        }
    }

    @Override
    public void onBackPressed()
    {

    }

}
