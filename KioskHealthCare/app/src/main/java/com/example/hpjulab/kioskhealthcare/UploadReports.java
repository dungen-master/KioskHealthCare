package com.example.hpjulab.kioskhealthcare;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;

/**
 * Created by hpjulab on 9/21/2015.
 */
public class UploadReports extends Activity{

    public Button prevpresBtn,xrayBtn,ecgBtn,bloodBtn,otherBtn,saveBtn,cancelBtn;
    public TextView prevpres,xray,ecg,blood,other;
    public File selectedFile;
    public static String presPath,xrayPath,ecgPath,bloodPath,otherPath;

    public int report_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reports_upload);
        initiate();

        UploadAction();
    }

    public void setClickable(boolean flag)
    {
        prevpresBtn.setClickable(flag);
        xrayBtn.setClickable(flag);
        ecgBtn.setClickable(flag);
        bloodBtn.setClickable(flag);
        otherBtn.setClickable(flag);
        saveBtn.setClickable(flag);
    }

    public void initiate()
    {
        prevpres=(TextView)findViewById(R.id.reports_upload_textpresId);
        prevpres.setText("");

        ecg=(TextView)findViewById(R.id.reports_upload_textecgId);
        ecg.setText("");

        xray=(TextView)findViewById(R.id.reports_upload_textxrayId);
        xray.setText("");

        blood=(TextView)findViewById(R.id.reports_upload_textbloodId);
        blood.setText("");

        other=(TextView)findViewById(R.id.reports_upload_textotherpresId);
        other.setText("");

        prevpresBtn=(Button)findViewById(R.id.reports_upload_presId);
        xrayBtn=(Button)findViewById(R.id.reports_upload_xrayId);
        ecgBtn=(Button)findViewById(R.id.reports_upload_ecgId);
        bloodBtn=(Button)findViewById(R.id.reports_upload_bloodId);
        otherBtn=(Button)findViewById(R.id.reports_upload_otherpresId);
        saveBtn=(Button)findViewById(R.id.reports_upload_saveId);
        cancelBtn=(Button)findViewById(R.id.reports_upload_cancelId);

        presPath=xrayPath=ecgPath=bloodPath=otherPath="";
    }


    public void UploadAction()
    {
        final Intent intent=new Intent(UploadReports.this,FilePicker.class);

        prevpresBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(intent,1);
            }
        });

        xrayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(intent,2);
            }
        });

        ecgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(intent,3);
            }
        });

        bloodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(intent,4);
            }
        });

        otherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(intent,5);
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                presPath=prevpres.getText().toString();
                xrayPath=xray.getText().toString();
                ecgPath=ecg.getText().toString();
                bloodPath=blood.getText().toString();
                otherPath=other.getText().toString();


                setResult(Activity.RESULT_OK);
                finish();


//                ((TabActivity)getParent()).getTabHost().setCurrentTab(0);
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
                finish();
//                ((TabActivity)getParent()).getTabHost().setCurrentTab(0);
            }
        });


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {

            switch (requestCode) {

                case 1:

                    if (data.hasExtra(FilePicker.EXTRA_FILE_PATH)) {

                        selectedFile = new File
                                (data.getStringExtra(FilePicker.EXTRA_FILE_PATH));


                        prevpres.setText(selectedFile.getPath());
                    }
                    break;

                case 2:
                    if (data.hasExtra(FilePicker.EXTRA_FILE_PATH)) {

                        selectedFile = new File
                                (data.getStringExtra(FilePicker.EXTRA_FILE_PATH));


                        xray.setText(selectedFile.getPath());
                    }
                    break;

                case 3:
                    if (data.hasExtra(FilePicker.EXTRA_FILE_PATH)) {

                        selectedFile = new File
                                (data.getStringExtra(FilePicker.EXTRA_FILE_PATH));


                        ecg.setText(selectedFile.getPath());
                    }
                    break;

                case 4:

                    if (data.hasExtra(FilePicker.EXTRA_FILE_PATH)) {

                        selectedFile = new File
                                (data.getStringExtra(FilePicker.EXTRA_FILE_PATH));


                        blood.setText(selectedFile.getPath());
                    }
                    break;

                case 5:

                    if (data.hasExtra(FilePicker.EXTRA_FILE_PATH)) {

                        selectedFile = new File
                                (data.getStringExtra(FilePicker.EXTRA_FILE_PATH));


                        other.setText(selectedFile.getPath());
                    }
                    break;
            }
        }
    }

    public void reset()
    {
        presPath=xrayPath=ecgPath=bloodPath=otherPath="";
    }
}
