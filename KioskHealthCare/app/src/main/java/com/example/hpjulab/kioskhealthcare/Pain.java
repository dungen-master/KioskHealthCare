package com.example.hpjulab.kioskhealthcare;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by devil on 10/20/15.
 */
public class Pain extends Activity {

    public static CheckBox head,teeth,neck,back,arm,chest,breast,urineArea,ear,jaw,throat,
                  joints,legs,abdomen,scortum,analArea;

    public static RadioButton always,sometime;

    public static RadioButton headFront,headBack,headWhole,backUpper,backLower,backBoth,chestRight,chestLeft,chestBoth,breastLeft,breastRight,breastBoth,
                              earRight,earLeft,earBoth,jawLeft,jawRight,jawBoth,abdomenUpper,abdomenLower,abdomenMiddle,
                              scortumRight,scortumLeft,scortumBoth;

    public static CheckBox arm_arm,arm_forearm,arm_fingers,joints_shoulder,joints_wrist,joints_fingers,legs_thigh,legs_leg,legs_foot;

    public static Spinner broughtOn,relievedBy;

    public static EditText howlong;

    public Button reset;


    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);

        setContentView(R.layout.pain_complain);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        initiate();
        loadActions();

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                resetFields();
            }
        });

    }

    @Override
    public void onBackPressed()
    {

    }

    public void initiate()
    {

        head=(CheckBox)findViewById(R.id.pain_complain_head);
        teeth=(CheckBox)findViewById(R.id.pain_complain_teeth);
        neck=(CheckBox)findViewById(R.id.pain_complain_neck);
        back=(CheckBox)findViewById(R.id.pain_complain_back);
        arm=(CheckBox)findViewById(R.id.pain_complain_arm);
        chest=(CheckBox)findViewById(R.id.pain_complain_chest);
        breast=(CheckBox)findViewById(R.id.pain_complain_breast);
        urineArea=(CheckBox)findViewById(R.id.pain_complain_urine);
        ear=(CheckBox)findViewById(R.id.pain_complain_ear);
        jaw=(CheckBox)findViewById(R.id.pain_complain_jaw);
        throat=(CheckBox)findViewById(R.id.pain_complain_throat);
        joints=(CheckBox)findViewById(R.id.pain_complain_joints);
        legs=(CheckBox)findViewById(R.id.pain_complain_legs);
        abdomen=(CheckBox)findViewById(R.id.pain_complain_abdomen);
        scortum=(CheckBox)findViewById(R.id.pain_complain_scortum);
        analArea=(CheckBox)findViewById(R.id.pain_complain_analarea);

        always=(RadioButton)findViewById(R.id.pain_complain_typeAlways);
        sometime=(RadioButton)findViewById(R.id.pain_complain_typeSometimes);

        headFront=(RadioButton)findViewById(R.id.pain_complain_headFront);
        headBack=(RadioButton)findViewById(R.id.pain_complain_headBack);
        headWhole=(RadioButton)findViewById(R.id.pain_complain_headWhole);

        backLower=(RadioButton)findViewById(R.id.pain_complain_backLower);
        backUpper=(RadioButton)findViewById(R.id.pain_complain_backUpper);
        backBoth=(RadioButton)findViewById(R.id.pain_complain_backBoth);

        chestLeft=(RadioButton)findViewById(R.id.pain_complain_chestLeft);
        chestRight=(RadioButton)findViewById(R.id.pain_complain_chestRight);
        chestBoth=(RadioButton)findViewById(R.id.pain_complain_chestBoth);

        breastLeft=(RadioButton)findViewById(R.id.pain_complain_breastLeft);
        breastRight=(RadioButton)findViewById(R.id.pain_complain_breastRight);
        breastBoth=(RadioButton)findViewById(R.id.pain_complain_breastBoth);

        earLeft=(RadioButton)findViewById(R.id.pain_complain_earLeft);
        earRight=(RadioButton)findViewById(R.id.pain_complain_earRight);
        earBoth=(RadioButton)findViewById(R.id.pain_complain_earBoth);

        jawLeft=(RadioButton)findViewById(R.id.pain_complain_jawLeft);
        jawRight=(RadioButton)findViewById(R.id.pain_complain_jawRight);
        jawBoth=(RadioButton)findViewById(R.id.pain_complain_jawBoth);

        abdomenUpper=(RadioButton)findViewById(R.id.pain_complain_abdomenUpper);
        abdomenMiddle=(RadioButton)findViewById(R.id.pain_complain_abdomenMiddle);
        abdomenLower=(RadioButton)findViewById(R.id.pain_complain_abdomenLower);

        scortumLeft=(RadioButton)findViewById(R.id.pain_complain_scortumLeft);
        scortumRight=(RadioButton)findViewById(R.id.pain_complain_scortumRight);
        scortumBoth=(RadioButton)findViewById(R.id.pain_complain_scortumBoth);

        arm_arm=(CheckBox)findViewById(R.id.pain_complain_armArm);
        arm_forearm=(CheckBox)findViewById(R.id.pain_complain_armForearm);
         arm_fingers=(CheckBox)findViewById(R.id.pain_complain_armFingers);

        joints_shoulder=(CheckBox)findViewById(R.id.pain_complain_jointsShoulder);
        joints_wrist=(CheckBox)findViewById(R.id.pain_complain_jointsWrist);
        joints_fingers=(CheckBox)findViewById(R.id.pain_complain_jointsFinger);

        legs_thigh=(CheckBox)findViewById(R.id.pain_complain_legsThigh);
        legs_leg=(CheckBox)findViewById(R.id.pain_complain_legsLeg);
        legs_foot=(CheckBox)findViewById(R.id.pain_complain_legsFoot);

        howlong=(EditText)findViewById(R.id.pain_complain_duration);

        broughtOn=(Spinner)findViewById(R.id.pain_complain_broughtOnId);
        relievedBy=(Spinner)findViewById(R.id.pain_complain_relievedId);

        reset=(Button)findViewById(R.id.pain_complain_resetId);

        headCheck(false);
        backCheck(false);
        armCheck(false);
        chestCheck(false);
        breastCheck(false);
        earCheck(false);
        jawCheck(false);
        jointsCheck(false);
        legsCheck(false);
        abdomenCheck(false);
        scortumCheck(false);
        typeCheck(false);


    }

    public void loadActions()
    {

        head.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(head.isChecked()==true)
                {
                    headCheck(true);
                }
                else
                {

                    headCheck(false);
                }
            }
        });

        back.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(back.isChecked()==true)
                {
                    backCheck(true);
                }
                else
                {

                    backCheck(false);
                }

            }
        });

        arm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (arm.isChecked() == true) {
                    armCheck(true);
                } else {
                    armCheck(false);
                }

            }
        });

        chest.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (chest.isChecked() == true) {
                    chestCheck(true);
                } else {
                    chestCheck(false);
                }

            }
        });

        breast.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (breast.isChecked() == true) {
                    breastCheck(true);
                } else {
                    breastCheck(false);
                }

            }
        });

        ear.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (ear.isChecked() == true) {
                    earCheck(true);
                } else {
                    earCheck(false);
                }

            }
        });

        jaw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (jaw.isChecked() == true) {
                    jawCheck(true);
                } else {
                    jawCheck(false);
                }

            }
        });


        joints.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (joints.isChecked() == true) {
                    jointsCheck(true);
                } else {
                    jointsCheck(false);
                }

            }
        });

        legs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (legs.isChecked() == true) {
                    legsCheck(true);
                } else {
                    legsCheck(false);
                }

            }
        });

        abdomen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (abdomen.isChecked() == true) {
                    abdomenCheck(true);
                } else {
                    abdomenCheck(false);
                }

            }
        });

        scortum.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (scortum.isChecked() == true) {
                    scortumCheck(true);
                } else {
                    scortumCheck(false);
                }

            }
        });

        always.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeCheck(false);
            }
        });

        sometime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeCheck(true);
            }
        });

    }

    public void resetFields()
    {

        head.setChecked(false);
        teeth.setChecked(false);
        neck.setChecked(false);
        back.setChecked(false);
        arm.setChecked(false);
        chest.setChecked(false);
        breast.setChecked(false);
        urineArea.setChecked(false);
        ear.setChecked(false);
        jaw.setChecked(false);
        throat.setChecked(false);
        joints.setChecked(false);
        legs.setChecked(false);
        abdomen.setChecked(false);
        scortum.setChecked(false);
        analArea.setChecked(false);

        always.setChecked(false);
        sometime.setChecked(false);

        headFront.setChecked(false);
        headBack.setChecked(false);
        headWhole.setChecked(false);

        backLower.setChecked(false);
        backUpper.setChecked(false);
        backBoth.setChecked(false);

        chestLeft.setChecked(false);
        chestRight.setChecked(false);
        chestBoth.setChecked(false);

        breastLeft.setChecked(false);
        breastRight.setChecked(false);
        breastBoth.setChecked(false);

        earLeft.setChecked(false);
        earRight.setChecked(false);
        earBoth.setChecked(false);

        jawLeft.setChecked(false);
        jawRight.setChecked(false);
        jawBoth.setChecked(false);

        abdomenUpper.setChecked(false);
        abdomenMiddle.setChecked(false);
        abdomenLower.setChecked(false);

        scortumLeft.setChecked(false);
        scortumRight.setChecked(false);
        scortumBoth.setChecked(false);

        arm_arm.setChecked(false);
        arm_forearm.setChecked(false);
        arm_fingers.setChecked(false);

        joints_shoulder.setChecked(false);
        joints_wrist.setChecked(false);
        joints_fingers.setChecked(false);

        legs_thigh.setChecked(false);
        legs_leg.setChecked(false);
        legs_foot.setChecked(false);

        howlong.setText(null);


    }

    public void headCheck(boolean flag)
    {
        headFront.setEnabled(flag);
        headFront.setChecked(false);

        headBack.setEnabled(flag);
        headBack.setChecked(false);

        headWhole.setEnabled(flag);
        headWhole.setChecked(false);

    }

    public void backCheck(boolean flag)
    {
        backLower.setEnabled(flag);
        backLower.setChecked(false);

        backUpper.setEnabled(flag);
        backUpper.setChecked(false);

        backBoth.setEnabled(flag);
        backBoth.setChecked(false);
    }

    public void armCheck(boolean flag)
    {
        arm_forearm.setEnabled(flag);
        arm_forearm.setChecked(false);

        arm_arm.setEnabled(flag);
        arm_arm.setChecked(false);

        arm_fingers.setEnabled(flag);
        arm_fingers.setChecked(false);
    }

    public void chestCheck(boolean flag)
    {
        chestRight.setEnabled(flag);
        chestRight.setChecked(false);

        chestLeft.setEnabled(flag);
        chestLeft.setChecked(false);

        chestBoth.setEnabled(flag);
        chestBoth.setChecked(false);
    }

    public void breastCheck(boolean flag)
    {
        breastLeft.setEnabled(flag);
        breastLeft.setChecked(false);

        breastRight.setEnabled(flag);
        breastRight.setChecked(false);

        breastBoth.setEnabled(flag);
        breastBoth.setChecked(false);
    }

    public void earCheck(boolean flag)
    {
        earLeft.setEnabled(flag);
        earLeft.setChecked(false);

        earRight.setEnabled(flag);
        earRight.setChecked(false);

        earBoth.setEnabled(flag);
        earBoth.setChecked(false);
    }

    public void jawCheck(boolean flag)
    {
        jawLeft.setEnabled(flag);
        jawLeft.setChecked(false);

        jawRight.setEnabled(flag);
        jawRight.setChecked(false);

        jawBoth.setEnabled(flag);
        jawBoth.setChecked(false);
    }

    public void jointsCheck(boolean flag)
    {
        joints_fingers.setEnabled(flag);
        joints_fingers.setChecked(false);

        joints_wrist.setEnabled(flag);
        joints_wrist.setChecked(false);

        joints_shoulder.setEnabled(flag);
        joints_shoulder.setChecked(false);
    }

    public void legsCheck(boolean flag)
    {
        legs_foot.setEnabled(flag);
        legs_foot.setChecked(false);

        legs_thigh.setEnabled(flag);
        legs_thigh.setChecked(false);

        legs_leg.setEnabled(flag);
        legs_leg.setChecked(false);
    }

    public void abdomenCheck(boolean flag)
    {
        abdomenLower.setEnabled(flag);
        abdomenLower.setChecked(false);

        abdomenMiddle.setEnabled(flag);
        abdomenMiddle.setChecked(false);

        abdomenUpper.setEnabled(flag);
        abdomenUpper.setChecked(false);
    }

    public void scortumCheck(boolean flag)
    {
        scortumLeft.setEnabled(flag);
        scortumLeft.setChecked(false);

        scortumRight.setEnabled(flag);
        scortumRight.setChecked(false);

        scortumBoth.setEnabled(flag);
        scortumBoth.setChecked(false);
    }

    public void typeCheck(boolean flag)
    {
        relievedBy.setEnabled(flag);
        broughtOn.setEnabled(flag);
    }

    public void showDialogue(String s)
    {
        Toast.makeText(Pain.this,s,Toast.LENGTH_SHORT).show();
    }

}
