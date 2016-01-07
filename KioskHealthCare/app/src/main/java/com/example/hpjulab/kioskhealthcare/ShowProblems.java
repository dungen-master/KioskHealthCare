package com.example.hpjulab.kioskhealthcare;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by devil on 10/23/15.
 */
public class ShowProblems extends Activity {

    public Button cancel,submit;
    public EditText problem;

    @Override
    public void onCreate(Bundle savedInstance)
    {
        super.onCreate(savedInstance);

        setContentView(R.layout.show_problems);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        initiate();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alt=new AlertDialog.Builder(ShowProblems.this);
                alt.setIcon(R.drawable.alert6);
                alt.setTitle("Are you sure?");
                alt.setMessage("Check before submit");
                alt.setCancelable(false);

                alt.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        setResult(Activity.RESULT_OK);
                        finish();
                    }
                });

                alt.setNegativeButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                alt.show();
            }
        });
    }

    @Override
    public void onBackPressed()
    {

    }

    public void initiate()
    {
        cancel=(Button)findViewById(R.id.show_problems_cancelId);
        submit=(Button)findViewById(R.id.show_problems_submitId);

        problem=(EditText)findViewById(R.id.show_problems_problemId);

        problem.setText(Others.finalString);
        problem.setKeyListener(null);

        //add scrolling


        problem.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View view, MotionEvent event) {
                // TODO Auto-generated method stub
                if (view.getId() == R.id.show_problems_problemId) {
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

    public void showDialogues(String s)
    {
        Toast.makeText(ShowProblems.this, s, Toast.LENGTH_SHORT).show();
    }

}
