package com.example.chris.inclass03;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {

    private EditText name, email;
    private RadioGroup rg;
    private SeekBar sb;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        final Student student = (Student) getIntent().getExtras().getSerializable(DisplayActivity.STUDENT_KEY);
        final Integer choice = getIntent().getExtras().getInt(DisplayActivity.CHOICE_KEY);

        rg = (RadioGroup) findViewById(R.id.radioGroup2);
        name = (EditText) findViewById(R.id.newName);
        email = (EditText) findViewById(R.id.newEmail);
        sb = (SeekBar) findViewById(R.id.saveSeekBar);

        findViewById(R.id.buttonSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(choice == 1){
                    String chosenName = name.getText().toString();
                    student.setName(chosenName);
                } else if(choice == 2){
                    String chosenEmail = email.getText().toString();
                    student.setEmail(chosenEmail);
                } else if(choice == 3){
                    String chosenDept = null;
                    if(rg.getCheckedRadioButtonId() == R.id.radioButton9){
                        chosenDept = "SIS";
                    } else if (rg.getCheckedRadioButtonId() == R.id.radioButton10){
                        chosenDept = "CS";
                    } else if (rg.getCheckedRadioButtonId() == R.id.radioButton12){
                        chosenDept = "BIO";
                    } else if (rg.getCheckedRadioButtonId() == R.id.radioButton11){
                        chosenDept = "Others";
                    }
                    student.setDepartment(chosenDept);
                } else{
                    int moodAmount = sb.getProgress();
                    student.setMood(moodAmount);
                }
                if(student.getName().equals("")) {
                    Toast toast = Toast.makeText(EditActivity.this, "Please enter in a name.", Toast.LENGTH_SHORT);
                    toast.show();
                } else if(student.getEmail().equals("")){
                    Toast toast = Toast.makeText(EditActivity.this, "Please enter in an email.", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    Intent intent = new Intent(EditActivity.this, DisplayActivity.class);
                    intent.putExtra(DisplayActivity.STUDENT_KEY, student);
                    startActivity(intent);
                    finish();
                }
            }
        });

        if(choice == 1) {
            findViewById(R.id.newName).setVisibility(View.VISIBLE);
            String str = student.getName();
            TextView tv = (TextView) findViewById(R.id.newName);
            tv.setText(str);
        } else if(choice == 2){
            findViewById(R.id.newEmail).setVisibility(View.VISIBLE);
            String str = student.getEmail();
            TextView tv = (TextView) findViewById(R.id.newEmail);
            tv.setText(str);
        } else if(choice == 3){
            findViewById(R.id.textView7).setVisibility(View.VISIBLE);
            findViewById(R.id.radioGroup2).setVisibility(View.VISIBLE);
            String str = student.getDepartment();
            if (str.equals("SIS")){
                rg.check(R.id.radioButton9);
            } else if(str.equals("CS")){
                rg.check(R.id.radioButton10);
            } else if(str.equals("BIO")){
                rg.check(R.id.radioButton12);
            } else {
                rg.check(R.id.radioButton11);
            }

        } else {
            findViewById(R.id.textView8).setVisibility(View.VISIBLE);
            findViewById(R.id.saveSeekBar).setVisibility(View.VISIBLE);
            Integer someInt = student.getMood();
            sb.setProgress(someInt);
        }
    }
}
