package com.example.hpjulab.kioskhealthcare;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;
import android.widget.TextView;


/**
 * Created by devil on 9/27/15.
 */

public class TabInformation extends TabActivity {
    TabHost tabHost;


    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.information_tab);

        tabHost=getTabHost();



        //First Tab
        Intent in1=new Intent().setClass(this, PatientForm.class);//PatientForm.class);

        TabHost.TabSpec t1 = tabHost
                .newTabSpec("Health")
                .setIndicator("Health Info")
                .setContent(in1);

        Intent in2=new Intent().setClass(this, EffectedImage.class);

        TabHost.TabSpec t2 = tabHost
                .newTabSpec("Infection")
                .setIndicator("Image")
                .setContent(in2);


        Intent in3=new Intent().setClass(this,Diagnosis.class);

        TabHost.TabSpec t3 = tabHost
                .newTabSpec("Report")
                .setIndicator("Diagnosis")
                .setContent(in3);




        tabHost.addTab(t1);
        tabHost.addTab(t2);
        tabHost.addTab(t3);

        tabHost.setCurrentTab(0);

        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
            tabHost.getTabWidget().getChildAt(i).getLayoutParams().width=500;
            tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#BCC6A3")); // unselected
            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title); //Unselected Tabs
            tv.setTextColor(Color.parseColor("#ffffff"));
            tv.setTextSize(20);

        }
        tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundColor(Color.parseColor("#7F856D"));


        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

            @Override
            public void onTabChanged(String tabId) {

                for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {//940505
                    tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#BCC6A3")); // unselected
                    TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title); //Unselected Tabs
                    tv.setTextColor(Color.parseColor("#ffffff"));



                }

                tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundColor(Color.parseColor("#7F856D")); // selected
                TextView tv = (TextView) tabHost.getCurrentTabView().findViewById(android.R.id.title); //for Selected Tab
                tv.setTextColor(Color.parseColor("#ffffff"));


            }
        });
    }

}
