package com.example.hpjulab.kioskhealthcare;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

/**
 * Created by devil on 10/21/15.
 */
public class Ear_Eye extends Activity {

    public static EditText eye_difficult,eye_when,eye_how,eye_Other,ear_hear;
    public static RadioButton eye_left,eye_right,eye_both;
    public static RadioButton ear_painLeft,ear_painRight,ear_painBoth,ear_dischargeLeft,ear_dischargeRight,ear_dischargeBoth;
    public Button eye_reset,ear_reset;

    @Override
    public void onCreate(Bundle savedInstance)
    {
        super.onCreate(savedInstance);

        setContentView(R.layout.eye_ear_complain);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        initiate();

        eye_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetEyeFields();
            }
        });

        ear_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetEarFields();
            }
        });

    }

    @Override
    public void onBackPressed()
    {

    }

    public void initiate()
    {
        eye_difficult=(EditText)findViewById(R.id.eye_ear_difficultyId);
        eye_when=(EditText)findViewById(R.id.eye_ear_whenId);
        eye_how=(EditText)findViewById(R.id.eye_ear_howId);
        eye_Other=(EditText)findViewById(R.id.eye_ear_otherId);
        ear_hear=(EditText)findViewById(R.id.eye_ear_canthearId);

        eye_left=(RadioButton)findViewById(R.id.eye_ear_locationleftId);
        eye_right=(RadioButton)findViewById(R.id.eye_ear_locationrightId);
        eye_both=(RadioButton)findViewById(R.id.eye_ear_locationbothId);

        ear_painLeft=(RadioButton)findViewById(R.id.eye_ear_painleftId);
        ear_painRight=(RadioButton)findViewById(R.id.eye_ear_painrightId);
        ear_painBoth=(RadioButton)findViewById(R.id.eye_ear_painbothId);

        ear_dischargeLeft=(RadioButton)findViewById(R.id.eye_ear_dischargeleftId);
        ear_dischargeRight=(RadioButton)findViewById(R.id.eye_ear_dischargerightId);
        ear_dischargeBoth=(RadioButton)findViewById(R.id.eye_ear_dischargebothId);

        eye_reset=(Button)findViewById(R.id.eye_ear_eyeResetId);
        ear_reset=(Button)findViewById(R.id.eye_ear_earResetId);

    }

    public void resetEyeFields()
    {
        eye_difficult.setText(null);
        eye_when.setText(null);
        eye_how.setText(null);
        eye_Other.setText(null);

        eye_left.setChecked(false);
        eye_right.setChecked(false);
        eye_both.setChecked(false);
    }

    public void resetEarFields()
    {
        ear_hear.setText(null);

        ear_painBoth.setChecked(false);
        ear_painLeft.setChecked(false);
        ear_painRight.setChecked(false);

        ear_dischargeBoth.setChecked(false);
        ear_dischargeLeft.setChecked(false);
        ear_dischargeRight.setChecked(false);
    }


}
