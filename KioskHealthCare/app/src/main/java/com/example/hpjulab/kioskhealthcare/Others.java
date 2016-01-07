package com.example.hpjulab.kioskhealthcare;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by devil on 10/21/15.
 */
public class Others extends Activity {

    public static final int PROBLEM_SHOW=39;
    public static EditText others;
    public Button reset,submit;
    public String localValues,seperator=" --> ",where;
    public static String finalString;

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);

        setContentView(R.layout.others_complain);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        others=(EditText)findViewById(R.id.others_complain_otherId);
        reset=(Button)findViewById(R.id.others_complain_resetId);
        submit=(Button)findViewById(R.id.others_complain_submitId);


        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                others.setText(null);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alt = new AlertDialog.Builder(Others.this);
                alt.setTitle("Want to finish!");
                alt.setMessage("Are you sure?");
                alt.setIcon(R.drawable.alert6);
                alt.setCancelable(false);

                alt.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        localValues = "";
                        finalString = "";
                        where = "Where";
                        loadValues();
                        PatientForm.healthProblemsVar = finalString;

                        Intent in = new Intent(Others.this, ShowProblems.class);
                        startActivityForResult(in, PROBLEM_SHOW);
//                        startActivity(in);
                    }
                });

                alt.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                alt.setNeutralButton("Exit without saving", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        finish();
                    }
                });

                alt.show();

            }
        });

        others.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View view, MotionEvent event) {
                // TODO Auto-generated method stub
                if (view.getId() == R.id.others_complain_otherId) {
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

    @Override
    public void onBackPressed()
    {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==PROBLEM_SHOW && resultCode==RESULT_OK)
        {


            String s=PatientForm.healthproblem.getText().toString();
            if(!s.equals(""))
            {
                s += "\n" + PatientForm.healthProblemsVar;
                PatientForm.healthProblemsVar=s;
            }


            PatientForm.healthproblem.setText(PatientForm.healthProblemsVar);

            finish();

        }
        else if(requestCode==PROBLEM_SHOW && resultCode==RESULT_CANCELED)
        {
           // showDialogues("Operation cancelled");
        }
    }

    public void showDialogues(String s)
    {
        Toast.makeText(Others.this,s,Toast.LENGTH_SHORT).show();
    }

    public void loadValues()
    {
        loadPain();
        loadFever();
        loadEyeEar();
        loadOthers();
    }

    public void loadPain()
    {
        localValues="";


        if(Pain.head.isChecked())
        {
            //showDialogues("enterd");
            if(Pain.headWhole.isChecked())
                localValues+=where+seperator+"Head-Whole\n";
            else if(Pain.headFront.isChecked())
                localValues+=where+seperator+"Head-Back\n";
            else if(Pain.headBack.isChecked())
                localValues+=where+seperator+"Head-Front\n";
        }

        if(Pain.teeth.isChecked())
            localValues+=where+seperator+"Teeth\n";

        if(Pain.neck.isChecked())
            localValues+=where+seperator+"Neck\n";

        if(Pain.back.isChecked())
        {
            if(Pain.backUpper.isChecked())
                localValues+=where+seperator+"Back-Upper\n";
            else if(Pain.backLower.isChecked())
                localValues+=where+seperator+"Back-Lower\n";
            else if(Pain.backBoth.isChecked())
                localValues+=where+seperator+"Back-Both\n";
        }

        if(Pain.arm.isChecked())
        {
            if(Pain.arm_arm.isChecked())
                localValues+=where+seperator+"Arm-Arm\n";
            if(Pain.arm_fingers.isChecked())
                localValues+=where+seperator+"Arm-Forearm\n";
            if(Pain.arm_forearm.isChecked())
                localValues+=where+seperator+"Arm-Fingers\n";
        }

        if(Pain.chest.isChecked())
        {
            if(Pain.chestRight.isChecked())
                localValues+=where+seperator+"Chest-Right\n";
            else if(Pain.chestLeft.isChecked())
                localValues+=where+seperator+"Chest-Left\n";
            else if(Pain.chestBoth.isChecked())
                localValues+=where+seperator+"Chest-Both\n";
        }

        if(Pain.breast.isChecked())
        {
            if(Pain.breastRight.isChecked())
                localValues+=where+seperator+"Breast-Right\n";
            else if(Pain.breastLeft.isChecked())
                localValues+=where+seperator+"Breast-Left\n";
            else if(Pain.breastBoth.isChecked())
                localValues+=where+seperator+"Breast-Both\n";
        }

        if(Pain.urineArea.isChecked())
            localValues+=where+seperator+"Urine-Area\n";

        if(Pain.breast.isChecked())
        {
            if(Pain.breastRight.isChecked())
                localValues+=where+seperator+"Breast-Right\n";
            else if(Pain.breastLeft.isChecked())
                localValues+=where+seperator+"Breast-Left\n";
            else if(Pain.breastBoth.isChecked())
                localValues+=where+seperator+"Breast-Both\n";
        }

        if(Pain.ear.isChecked())
        {
            if(Pain.earRight.isChecked())
                localValues+=where+seperator+"Ear-Right\n";
            else if(Pain.earLeft.isChecked())
                localValues+=where+seperator+"Ear-Left\n";
            else if(Pain.earBoth.isChecked())
                localValues+=where+seperator+"Ear-Both\n";
        }

        if(Pain.jaw.isChecked())
        {
            if(Pain.jawRight.isChecked())
                localValues+=where+seperator+"Jaw-Right\n";
            else if(Pain.jawRight.isChecked())
                localValues+=where+seperator+"Jaw-Left\n";
            else if(Pain.jawBoth.isChecked())
                localValues+=where+seperator+"Jaw-Both\n";
        }

        if(Pain.throat.isChecked())
            localValues+=where+seperator+"Throat\n";

        if(Pain.joints.isChecked())
        {
            if(Pain.joints_shoulder.isChecked())
                localValues+=where+seperator+"Joints-Shoulder\n";
            if(Pain.joints_wrist.isChecked())
                localValues+=where+seperator+"Joints-Wrist\n";
            if(Pain.joints_fingers.isChecked())
                localValues+=where+seperator+"Joints-Finger\n";
        }

        if(Pain.legs.isChecked())
        {
            if(Pain.legs_thigh.isChecked())
                localValues+=where+seperator+"Legs-Thigh\n";
            if(Pain.legs_leg.isChecked())
                localValues+=where+seperator+"Legs-Leg\n";
            if(Pain.legs_foot.isChecked())
                localValues+=where+seperator+"Legs-Foot\n";
        }

        if(Pain.abdomen.isChecked())
        {
            if(Pain.abdomenUpper.isChecked())
                localValues+=where+seperator+"Abdomen-Upper\n";
            else if(Pain.abdomenMiddle.isChecked())
                localValues+=where+seperator+"Abdomen-Middle\n";
            else if(Pain.abdomenLower.isChecked())
                localValues+=where+seperator+"Abdomen-Lower\n";
        }

        if(Pain.scortum.isChecked())
        {
            if(Pain.scortumRight.isChecked())
                localValues+=where+seperator+"Scortum-Right\n";
            else if(Pain.scortumLeft.isChecked())
                localValues+=where+seperator+"Scortum-Left\n";
            else if(Pain.scortumBoth.isChecked())
                localValues+=where+seperator+"Scortum-Both\n";
        }

        if(Pain.analArea.isChecked())
            localValues+=where+seperator+"Anal Area\n";

        if(Pain.always.isChecked())
        {
            localValues+="Type"+seperator+"Always\n";
        }
        else if(Pain.sometime.isChecked())
        {
            localValues+="Type"+seperator+"Sometimes\n";
            localValues+="Brought On By"+seperator+Pain.broughtOn.getSelectedItem().toString()+"\n";
            localValues+="Relieved By"+seperator+Pain.relievedBy.getSelectedItem().toString()+"\n";
        }

        String s=Pain.howlong.getText().toString();
        if(!isEmpty(s))
        {
            localValues+="Duration"+seperator+s+"\n";
        }


        if(!isEmpty(localValues))
        {
            finalString+="Pain\n";
            finalString+=localValues;
        }

    }

    public void loadFever()
    {
        localValues="";

        String s=Fever.duration.getText().toString();
        if(!isEmpty(s))
            localValues+="Duration"+seperator+s+"\n";

        if(Fever.wholeYes.isChecked())
            localValues+="Whole Day"+seperator+"Yes\n";
        else if(Fever.wholeNo.isChecked())
            localValues+="Whole Day"+seperator+"No\n";

        if(Fever.shiversYes.isChecked())
            localValues+="Shivers"+seperator+"Yes\n";
        else if(Fever.shiversNo.isChecked())
            localValues+="Shivers"+seperator+"No\n";

        s=Fever.tempMin.getText().toString();

        if(!isEmpty(s))
            localValues+="Temperature Minimum"+seperator+s+"\n";

        s=Fever.tempAv.getText().toString();

        if(!isEmpty(s))
            localValues+="Temperature Average"+seperator+s+"\n";

        s=Fever.tempMax.getText().toString();

        if(!isEmpty(s))
            localValues+="Temperature Maximum"+seperator+s+"\n";

        if(Fever.typeEvery.isChecked())
            localValues+="Type"+seperator+"Every Day\n";
        else if(Fever.typeSome.isChecked())
            localValues+="Type"+seperator+"Some Days\n";

        if(Fever.morning.isChecked())
            localValues+="Timing"+seperator+"Morning\n";

        if(Fever.night.isChecked())
            localValues+="Timing"+seperator+"Night\n";

        s=Fever.other.getText().toString();

        if(!isEmpty(s))
            localValues+="Others"+seperator+s+"\n";

        if(!isEmpty(localValues))
        {
            finalString+="Fever\n";
            finalString+=localValues;
        }


    }

    public void loadEyeEar()
    {
        //Ear
        localValues="";

        if(Ear_Eye.ear_painLeft.isChecked())
            localValues+="Pain"+seperator+"Left\n";
        else if(Ear_Eye.ear_painRight.isChecked())
            localValues+="Pain"+seperator+"Right\n";
        else if(Ear_Eye.ear_painBoth.isChecked())
            localValues+="Pain"+seperator+"Both\n";


        if(Ear_Eye.ear_dischargeLeft.isChecked())
            localValues += "Discharge" + seperator + "Left\n";

        else if(Ear_Eye.ear_dischargeRight.isChecked())
            localValues+="Discharge"+seperator+"Right\n";
        else if(Ear_Eye.ear_dischargeBoth.isChecked())
            localValues+="Discharge"+seperator+"Both\n";


        String s=Ear_Eye.ear_hear.getText().toString();

        if(!isEmpty(s))
            localValues+="Cannot Hear"+seperator+s+"\n";


        if(!isEmpty(localValues)) {
            finalString+="Ear\n";
            finalString += localValues;
        }

        //Eye
        localValues="";

        s=Ear_Eye.eye_difficult.getText().toString();

        if(!isEmpty(s))
            localValues+="Cannot see"+seperator+s+"\n";

        s=Ear_Eye.eye_when.getText().toString();

        if(!isEmpty(s))
            localValues+="Injury When"+seperator+s+"\n";

        s=Ear_Eye.eye_how.getText().toString();

        if(!isEmpty(s))
            localValues+="Injury How"+seperator+s+"\n";

        if(Ear_Eye.eye_left.isChecked())
            localValues+="Injury Location"+seperator+"Left\n";

        else if(Ear_Eye.eye_right.isChecked())
            localValues+="Injury Location"+seperator+"Right\n";

        else if(Ear_Eye.eye_both.isChecked())
            localValues+="Injury Location"+seperator+"Both\n";

        s=Ear_Eye.eye_Other.getText().toString();

        if(!isEmpty(s))
            localValues+="Other"+seperator+s+"\n";

        if(!isEmpty(localValues))
        {
            finalString+="Eye\n";
            finalString+=localValues;
        }

    }

    public void loadOthers()
    {
        localValues="";
        String s=others.getText().toString();

        if(!isEmpty(s))
        {
            finalString+="Other\n";
            finalString+="Description"+seperator+s+"\n";

        }
    }

    boolean isEmpty(String s)
    {
        if(s.equals(null))
            return true;

        if(s.equals(""))
            return true;
        return false;
    }

}
