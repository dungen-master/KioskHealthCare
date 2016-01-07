package com.example.hpjulab.kioskhealthcare;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.simpleframework.xml.*;
import org.simpleframework.xml.core.Persister;

/**
 * Created by hpjulab on 9/15/2015.
 */
public class RegisterNewEmployee extends Activity {
    public String server;
    public int port;
    public EditText nameId,countryId,stateId,addressId,phoneId,passId,conpassId;
    public Button submit,reset,back;
    public RadioButton male,female;
    public RadioGroup genRadio;
    public TextView employeeId;
    public Socket clientSocket=null;
    public String localDir,serverDir,dateVar,KioskNumber,EmployeeId,name,gen,add,state,country,phone,pass,conpass;;
    public boolean hsConnection;
    public int countId;
    public Connection myCon;
    public ProgressDialog progressDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from new_activity.xml

        setContentView(R.layout.register_new_employee);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        initiate();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                boolean check = checkAllFields();


                if (check == true) {

                    AlertDialog.Builder alt = new AlertDialog.Builder(RegisterNewEmployee.this);
                    alt.setTitle("Confirmation Page");
                    alt.setMessage("Register Employee");
                    alt.setIcon(R.drawable.alert);
                    alt.setCancelable(false);


                    alt.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            progressDialog=new ProgressDialog(RegisterNewEmployee.this);
                            progressDialog.setIndeterminate(true);
                            progressDialog.setCanceledOnTouchOutside(false);
                            progressDialog.setMessage("Registering Employee...");

                            new android.os.Handler().postDelayed(
                                    new Runnable() {
                                        @Override
                                        public void run() {

                                            Register reg = new Register();
                                            reg.execute();
                                        }
                                    }, 1000);
                            progressDialog.show();


                        }
                    });

                    alt.setNegativeButton("Cancel",new DialogInterface.OnClickListener(){

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alt.show();

                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetField();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                File f=new File(localDir+"/EmployeeIdCount.txt");
                if(f.exists())
                    f.delete();


                f=new File(localDir+"/tempEmployee.xml");
                if(f.exists())
                    f.delete();

                endConnection();
            }
        });
    }

    public void resetField()
    {
        nameId.setText(null);
        countryId.setText(null);
        stateId.setText(null);
        addressId.setText(null);
        phoneId.setText(null);
        passId.setText(null);
        conpassId.setText(null);

    }

    public void showDialogue(String s)
    {
        Toast.makeText(RegisterNewEmployee.this, s, Toast.LENGTH_SHORT).show();
    }


    public void initiate()
    {
        nameId=(EditText)findViewById(R.id.register_new_employee_nameId);
        countryId=(EditText)findViewById(R.id.register_new_employee_countryId);
        stateId=(EditText)findViewById(R.id.register_new_employee_stateId);
        addressId=(EditText)findViewById(R.id.register_new_employee_addressId);
        phoneId=(EditText)findViewById(R.id.register_new_employee_phnId);
        passId=(EditText)findViewById(R.id.register_new_employee_passId);
        conpassId=(EditText)findViewById(R.id.register_new_employee_cnfmpassId);

        submit=(Button)findViewById(R.id.register_new_employee_submitId);
        reset=(Button)findViewById(R.id.register_new_employee_clearId);
        back=(Button)findViewById(R.id.register_new_employee_backId);


        genRadio=(RadioGroup)findViewById(R.id.register_new_employee_genderId);
        male=(RadioButton)findViewById(R.id.register_new_employee_maleId);
        female=(RadioButton)findViewById(R.id.register_new_employee_femaleId);

        //add scrolling

        addressId.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View view, MotionEvent event) {
                // TODO Auto-generated method stub
                if (view.getId() == R.id.register_new_employee_addressId) {
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

        employeeId=(TextView)findViewById(R.id.register_new_employee_empId);

        localDir= MainActivity.DirLocal;
//        localDir=getFilesDir().toString();
        serverDir=MainActivity.SERVER_DIR;

        myCon=KioskLogin.conFinal;


        createSocket();


    }

    public void createSocket()
    {
        MyClient mc=new MyClient();
        mc.execute();

    }

    public void endConnection()
    {
        File f=new File(localDir+"/EmployeeIdCount.txt");
        if(f.exists())
            f.delete();

        f=new File(localDir+"/tempEmployee.xml");
        if(f.exists())
            f.delete();

        myCon.disconnect();
        this.finish();
    }



    public boolean IncrementEmployeeIdCount()
    {
        try
        {

            FileWriter fileWriter=new FileWriter(localDir+"/EmployeeIdCount.txt");
            BufferedWriter bout=new BufferedWriter(fileWriter);
            bout.write(String.valueOf(countId));
            bout.close();
            fileWriter.close();



            int filePresent=myCon.sendToServer(localDir+"/EmployeeIdCount.txt",serverDir+"/Employee_"+KioskNumber+"_IdCount.txt");


            if(filePresent<0)
            {
                (new File("tempFolder/EmployeeIdCount.txt")).delete();
                return false;
            }
            (new File(localDir+"/EmployeeIdCount.txt")).delete();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            (new File(localDir+"/EmployeeIdCount.txt")).delete();
            return false;
        }
        return true;
    }

    public boolean checkAllFields()
    {

        name=gen=add=state=country=phone=pass=conpass="";

        name=nameId.getText().toString();
        add=addressId.getText().toString();
        state=stateId.getText().toString();
        country=countryId.getText().toString();
        phone=phoneId.getText().toString();
        pass=passId.getText().toString();
        conpass=conpassId.getText().toString();

        if(male.isChecked())
            gen="Male";
        else if(female.isChecked())
            gen="Female";


        if(name.equals("") || add.equals("") || state.equals("") || country.equals("") || phone.equals("") || pass.equals("") ||
                conpass.equals("") || gen.equals("") )
        {
            showDialogue("All fields are mandatory");
            return false;
        }
        else if(!pass.equals(conpass))
        {
            showDialogue("Password Mismatch");
            passId.setText(null);
            conpassId.setText(null);
            return false;
        }
        else if(!(phone.matches("[0-9]+") && phone.length()>2))
        {
            showDialogue("Invalid phone number");
            phoneId.setText(null);
            return false;
        }
        return true;

    }



    public boolean register_file_to_server()
    {
            try {

                Employee emp = new Employee();
                emp.setName(name);
                emp.setEmployeeId(EmployeeId);
                emp.setGender(gen);
                emp.setAddress(add);
                emp.setCountry(country);
                emp.setState(state);
                emp.setPh_no(phone);
                emp.setPassword(pass);

                try {
                    File file = new File(localDir + "/tempEmployee.xml");
                    file.getParentFile().mkdirs();
                    file.createNewFile();

                    Serializer sr = new Persister();
                    sr.write(emp, file);


                } catch (Exception exc) {
                    exc.printStackTrace();
                    return false;
                }


                int filePresent = myCon.sendToServer(localDir + "/tempEmployee.xml", serverDir + "/" + EmployeeId + ".xml");
                boolean check = false;

                if (filePresent >= 0) {


                    check = IncrementEmployeeIdCount();

                    if (check == false)
                        return false;
                } else {


                    return false;
                }

                File file = new File(localDir + "/tempEmployee.xml");

                if (file.isFile())
                    file.delete();

                return true;
            }catch (Exception e)
            {
                e.printStackTrace();
                return false;
            }
    }



    class MyClient extends AsyncTask<Void, Void, Void> {

        public boolean isAlright;

        @Override
        protected void onPreExecute()
        {
            isAlright=false;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try
            {

                KioskNumber = MainActivity.KIOSK_NUMBER;


                int filePresent = myCon.receiveFromServer("Employee_"+KioskNumber + "_IdCount.txt", localDir + "/EmployeeIdCount.txt");

                if (filePresent>=0) {
                    BufferedReader bin = new BufferedReader(new FileReader(localDir + "/EmployeeIdCount.txt"));
                    countId = Integer.parseInt(bin.readLine());
                    bin.close();
                    String temp = "Employee_" + KioskNumber + "_" + String.format("%02d",(countId++));
                    EmployeeId = temp;
                    countId++;
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
        protected void onPostExecute(Void result) {
            if(isAlright==false)
            {
                showDialogue("Error connection");
                endConnection();
            }
            else
            {
                employeeId.setText(EmployeeId);
                cancel(true);
            }

        }
    }


    //to register employee

    class Register extends AsyncTask<Void, Void, Void> {

        public boolean isAlright;

        @Override
        protected void onPreExecute()
        {
            isAlright=false;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {


                isAlright=register_file_to_server();


            } catch (Exception e) {
                isAlright=false;
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            if(isAlright==true)
            {
                //showDialogue("Registered Successfully");
                progressDialog.dismiss();

                AlertDialog alt = new AlertDialog.Builder(RegisterNewEmployee.this).create();
                alt.setTitle("Registered Successfully");
                alt.setMessage("Back to Login");
                alt.setIcon(R.drawable.alert6);
                alt.setCanceledOnTouchOutside(false);

                alt.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(RegisterNewEmployee.this, KioskLogin.class));
                        endConnection();
                    }
                });

                alt.show();

            }
            else
            {
                progressDialog.dismiss();

                showDialogue("Error Connection");
                endConnection();
            }

        }
    }



}
