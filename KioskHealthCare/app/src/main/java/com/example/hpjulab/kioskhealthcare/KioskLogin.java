package com.example.hpjulab.kioskhealthcare;

/**
 * Created by hpjulab on 9/15/2015.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.os.Environment;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.*;

import org.simpleframework.xml.*;
import org.simpleframework.xml.core.Persister;


public class KioskLogin extends Activity{
    public static  String server;
    public static  int port;
    public String localDirectory,serverDirectory;
    public String username,password;
    public Button signIn,signUp;
    public EditText passwordId,usernameId;
    public Socket clientSocket;
    public static Connection conFinal;
    public static String KioskName;
    public static int conn=0;
    public String LocalFile;
    public  ProgressDialog progressDialog;
    public LoginClient lc;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from new_activity.xml

        setContentView(R.layout.login_kiosk);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        initiate();

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValues();
                if (username.equals("") || password.equals("")) {
                    showDialogue("Provide Username/Password");
                    resetField();
                } else
                    check();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alt=new AlertDialog.Builder(KioskLogin.this);
                alt.setIcon(R.drawable.alert5);
                alt.setTitle("Service Unavailable");
                alt.setMessage("Contact with service provider");
                alt.setCancelable(false);

                alt.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });


                alt.show();
                /*
                SignupClient sc=new SignupClient();
                sc.execute();*/

            }
        });

    }

    public void initiate(){
        username="";
        password="";
        signIn=(Button)findViewById(R.id.login_kiosk_signInId);
        signUp= (Button)findViewById(R.id.login_kiosk_signUpId);
        usernameId=(EditText)findViewById(R.id.login_kiosk_usernameId);
        passwordId=(EditText)findViewById(R.id.login_kiosk_passwordId);
//        localDirectory=getFilesDir().toString();
        localDirectory= MainActivity.DirLocal;
        serverDirectory=MainActivity.SERVER_DIR;
        server=MainActivity.ip;
        port=MainActivity.port;

    }

    public void getValues()
    {
        username=usernameId.getText().toString();
        password=passwordId.getText().toString();
    }


    //show dialogues
    public void showDialogue(String s)
    {
        Toast.makeText(KioskLogin.this,s,Toast.LENGTH_SHORT).show();
    }

    public void resetField()
    {
        usernameId.setText(null);
        passwordId.setText(null);
    }

    public void check()
    {

        try{

            progressDialog=new ProgressDialog(KioskLogin.this);
            progressDialog.setMessage("Signing in....");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setIndeterminate(true);


            new android.os.Handler().postDelayed(
                    new Runnable() {
                        @Override
                        public void run() {

                            lc=new LoginClient();
                            lc.execute();
                        }
                    }, 1000);
            progressDialog.show();

            new android.os.Handler().postDelayed(
                    new Runnable() {
                        @Override
                        public void run() {

                           if(lc.getStatus()==AsyncTask.Status.RUNNING)
                           {
                               lc.cancel(true);
                               progressDialog.dismiss();
                               showDialogue("Connection Error");
                           }
                        }
                    }, 15000);



        }
        catch(Exception e)
        {
            progressDialog.dismiss();
            showDialogue("....Server Syncronisation Problem....");

        }

    }




    public class LoginClient extends AsyncTask<Void, Void, Void>{
        private boolean hsConnection;
        int filePresent;


        @Override
        protected void onPreExecute(){
            hsConnection=false;
        }

        @Override
        protected Void doInBackground(Void... params) {

            try{

                clientSocket = new Socket(InetAddress.getByName(server), port);
                conFinal=new Connection(clientSocket);
                conFinal.login("sam","sas");


                String ServerFile=username+".xml";
                LocalFile=localDirectory+"/tempEmployee.xml";
                filePresent = conFinal.receiveFromServer(ServerFile,LocalFile);

                hsConnection = true;

            }
            catch (Exception e)
            {

                e.printStackTrace();
                hsConnection=false;

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            if(hsConnection==true)
            {

                if(filePresent>=0) {

                    File f = new File(LocalFile);
                    Employee emp=new Employee();

                    try {

                        Serializer sr = new Persister();

                        emp = sr.read(Employee.class, f);
                        KioskName = emp.getName();
                    }catch (Exception e)
                    {
                        progressDialog.dismiss();
                        showDialogue("perser exception");
                    }

                    try {

                        if (f.exists())
                            f.delete();

                        if(emp.getPassword().equals(password))
                        {
                            progressDialog.dismiss();

                            AlertDialog.Builder alt=new AlertDialog.Builder(KioskLogin.this);
                            alt.setIcon(R.drawable.alert6);
                            alt.setTitle("KIOSK CORDINATOR");
                            alt.setMessage(emp.getName() + " ! " + "Welcome to Hospital");
                            alt.setCancelable(false);

                            alt.setPositiveButton("Enter", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent(KioskLogin.this, NewExistingPatient.class));
                                }
                            });

                            alt.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    conFinal.disconnect();
                                }
                            });

                            alt.show();

                        }
                        else
                        {
                            progressDialog.dismiss();

                            AlertDialog.Builder alt=new AlertDialog.Builder(KioskLogin.this);
                            alt.setIcon(R.drawable.alert5);
                            alt.setTitle("Incorrect Password");
                            alt.setMessage("Check password.");
                            alt.setCancelable(false);

                            alt.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    passwordId.setText(null);
                                    conFinal.disconnect();
                                }
                            });

                            alt.show();

                        }


                    } catch (Exception e) {
                        progressDialog.dismiss();

                        e.printStackTrace();
                        showDialogue("Error");
                    }
                }
                else
                {
                    progressDialog.dismiss();

                    AlertDialog.Builder alt=new AlertDialog.Builder(KioskLogin.this);
                    alt.setIcon(R.drawable.alert5);
                    alt.setTitle("WRONG INPUT");
                    alt.setMessage("Username doesn't exists");
                    alt.setCancelable(false);

                    alt.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            conFinal.disconnect();
                        }
                    });

                    alt.show();

                }

            }
            else
            {
                progressDialog.dismiss();
                showDialogue("Connection Error");
            }

        }
    }

    public class SignupClient extends AsyncTask<Void, Void, Void>{

        private boolean hsConnection,filePresent;


        @Override
        protected void onPreExecute(){

            hsConnection=false;
        }

        @Override
        protected Void doInBackground(Void... params) {

            try{

                clientSocket = new Socket(InetAddress.getByName(server), port);
                conFinal=new Connection(clientSocket);
                conFinal.login("sam","sas");
                hsConnection=true;


            }
            catch (Exception e)
            {
                e.printStackTrace();
                hsConnection=false;

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            if(hsConnection==true)
            {

                startActivity(new Intent(KioskLogin.this,RegisterNewEmployee.class));
            }
            else
            {
                showDialogue("Connection Error");
            }

        }
    }

}

