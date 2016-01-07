package com.example.hpjulab.kioskhealthcare;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;

/**
 * Created by devil on 10/21/15.
 */
public class Fever extends Activity {

    public static EditText duration,tempMin,tempAv,tempMax,other;
    public static RadioButton wholeYes,wholeNo,shiversYes,shiversNo,typeEvery,typeSome;
    public static CheckBox morning,night;
    public Button reset;
    @Override
    public void onCreate(Bundle savedInstance)
    {
        super.onCreate(savedInstance);

        setContentView(R.layout.fever_complain);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        initiate();

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
        duration=(EditText)findViewById(R.id.fever_complain_durationId);
        tempMin=(EditText)findViewById(R.id.fever_complain_tempMinId);
        tempAv=(EditText)findViewById(R.id.fever_complain_tempAvgId);
        tempMax=(EditText)findViewById(R.id.fever_complain_tempMaxId);
        other=(EditText)findViewById(R.id.fever_complain_otherId);

        wholeYes=(RadioButton)findViewById(R.id.fever_complain_wholedayYesId);
        wholeNo=(RadioButton)findViewById(R.id.fever_complain_wholedayNoId);
        shiversYes=(RadioButton)findViewById(R.id.fever_complain_shiversYesId);
        shiversNo=(RadioButton)findViewById(R.id.fever_complain_shiversNoId);
        typeEvery=(RadioButton)findViewById(R.id.fever_complain_typeEveryId);
        typeSome=(RadioButton)findViewById(R.id.fever_complain_typeSomeId);

        morning=(CheckBox)findViewById(R.id.fever_complain_morningId);
        night=(CheckBox)findViewById(R.id.fever_complain_nightId);

        reset=(Button)findViewById(R.id.fever_complain_resetId);

    }

    public void resetFields()
    {
        duration.setText(null);
        tempMin.setText(null);
        tempAv.setText(null);
        tempMax.setText(null);
        other.setText(null);

        wholeYes.setChecked(false);
        wholeNo.setChecked(false);
        shiversYes.setChecked(false);
        shiversNo.setChecked(false);
        typeSome.setChecked(false);
        typeEvery.setChecked(false);

        morning.setChecked(false);
        night.setChecked(false);
    }
}
