package com.example.hpjulab.kioskhealthcare;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

/**
 * Created by devil on 10/18/15.
 */
public class HealthProblemsTab extends TabActivity {
    public TabHost tabHost;

    @Override
    public void onCreate(Bundle savedInstance)
    {
        super.onCreate(savedInstance);

        setContentView(R.layout.health_problem_tabs);

        tabHost=getTabHost();

        Intent in1=new Intent().setClass(this, Pain.class);//PatientForm.class);

        TabHost.TabSpec t1 = tabHost
                .newTabSpec("Pain")
                .setIndicator("Pain")
                .setContent(in1);

        Intent in2=new Intent().setClass(this, Fever.class);

        TabHost.TabSpec t2 = tabHost
                .newTabSpec("Fever")
                .setIndicator("Fever")
                .setContent(in2);


        Intent in3=new Intent().setClass(this, Ear_Eye.class);

        TabHost.TabSpec t3 = tabHost
                .newTabSpec("EarEye")
                .setIndicator("Eye & Ear")
                .setContent(in3);

        Intent in4=new Intent().setClass(this,Others.class);

        TabHost.TabSpec t4 = tabHost
                .newTabSpec("Other")
                .setIndicator("Final")
                .setContent(in4);

        tabHost.addTab(t1);
        tabHost.addTab(t2);
        tabHost.addTab(t3);
        tabHost.addTab(t4);

        for(int i=0;i<4;i++)
            tabHost.setCurrentTab(i);

        tabHost.setCurrentTab(0);


    }
}
