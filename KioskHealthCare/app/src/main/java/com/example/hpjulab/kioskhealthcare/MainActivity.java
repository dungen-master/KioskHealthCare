package com.example.hpjulab.kioskhealthcare;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileReader;

public class MainActivity extends AppCompatActivity {


    public ImageButton kioskId;

    public static String ip;
    public static int port;
    public static String DirLocal,SERVER_DIR,KIOSK_NUMBER,SERVER_TEMP_DIR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initiate();

        kioskId.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                kioskId.playSoundEffect(android.view.SoundEffectConstants.CLICK);
                return false;
            }
        });

        kioskId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int i=getIpFile();
                if(i==1) {

                    SERVER_DIR = "final" + "/Kiosk_" + KIOSK_NUMBER;
                    SERVER_TEMP_DIR = "temp";
                    startActivity(new Intent(MainActivity.this,KioskLogin.class));
                    finish();
                }
                else
                {
                    showDialogue("IpPort File Problem");
                }
            }
        });

    }

    public void initiate()
    {


        DirLocal= Environment.getExternalStorageDirectory().getAbsolutePath().toString();
        kioskId=(ImageButton)findViewById(R.id.activity_main_kioskId);


    }

    public int getIpFile()
    {
        try{

            BufferedReader bin=new BufferedReader(new FileReader(DirLocal+"/IpFile.txt"));
            String ipTemp=bin.readLine();
            String portTemp=bin.readLine();
            String Kiosk=bin.readLine();
            bin.close();

            if(ipTemp!=null && portTemp!=null && Kiosk!=null)
            {
                ip=ipTemp;
                port=Integer.parseInt(portTemp);
                KIOSK_NUMBER=Kiosk;
                return 1;
            }
            else return -1;

        }
        catch (Exception e)
        {
            return -1;
        }
    }


    public void showDialogue(String s)
    {
        Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
