/*
Assignment number: 3
Group5_InClass03
Christopher Stump & Houa Vue
 */
package com.example.chris.inclass03;

import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Toast;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {
    final static String STUDENT_KEY = "STUDENT";

    private EditText name, email;
    private RadioGroup rg;
    private SeekBar sb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        rg = (RadioGroup) findViewById(R.id.radioGroup);
        name = (EditText) findViewById(R.id.editText2);
        email = (EditText) findViewById(R.id.editText3);
        sb = (SeekBar) findViewById(R.id.seekBar);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId){
                RadioButton rb = (RadioButton)findViewById(checkedId);
                Log.d("day2", "Checked the " + rb.getText().toString());
            }
        });

        findViewById(R.id.buttonSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String chosenName = name.getText().toString();
                String chosenEmail = email.getText().toString();
                int moodAmount = sb.getProgress();

                String chosenDept = null;
                if(rg.getCheckedRadioButtonId() == R.id.radioButton4){
                    chosenDept = "SIS";
                } else if (rg.getCheckedRadioButtonId() == R.id.radioButton3){
                    chosenDept = "CS";
                } else if (rg.getCheckedRadioButtonId() == R.id.radioButton2){
                    chosenDept = "BIO";
                } else if (rg.getCheckedRadioButtonId() == R.id.radioButton){
                    chosenDept = "Others";
                }

                Log.d("day2", "Department is: " + chosenDept);
                if(chosenName.equals("")) {
                    Toast toast = Toast.makeText(MainActivity.this, "Please enter in a name.", Toast.LENGTH_SHORT);
                    toast.show();
                } else if(chosenEmail.equals("")){
                    Toast toast = Toast.makeText(MainActivity.this, "Please enter in an email.", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    Student student = new Student(chosenName, chosenEmail, chosenDept, moodAmount);

                    Intent intent = new Intent(MainActivity.this, DisplayActivity.class);

                    intent.putExtra(STUDENT_KEY, student);

                    startActivity(intent);
                }
            }
        });
    }
}
