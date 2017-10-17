package com.example.chris.inclass03;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class DisplayActivity extends AppCompatActivity {

    final static String STUDENT_KEY = "STUDENT";
    final static String CHOICE_KEY = "CHOICE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        final Student student = (Student) getIntent().getExtras().getSerializable(MainActivity.STUDENT_KEY);

        String str = student.getName();
        TextView tv = (TextView) findViewById(R.id.nameDisplay);
        tv.setText("Name: " + str);

        str = student.getEmail();
        tv = (TextView) findViewById(R.id.emailDisplay);
        tv.setText("Email: " + str);

        str = student.getDepartment();
        tv = (TextView) findViewById(R.id.deptDisplay);
        tv.setText("Department: " + str);

        Integer someInt = student.getMood();
        tv = (TextView) findViewById(R.id.moodDisplay);
        tv.setText("Mood: " + someInt);

        findViewById(R.id.nameEdit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayActivity.this, EditActivity.class);
                intent.putExtra(STUDENT_KEY, student);
                Integer choice = 1;
                intent.putExtra(CHOICE_KEY, choice);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.emailEdit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayActivity.this, EditActivity.class);
                intent.putExtra(STUDENT_KEY, student);
                Integer choice = 2;
                intent.putExtra(CHOICE_KEY, choice);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.deptEdit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayActivity.this, EditActivity.class);
                intent.putExtra(STUDENT_KEY, student);
                Integer choice = 3;
                intent.putExtra(CHOICE_KEY, choice);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.moodEdit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DisplayActivity.this, EditActivity.class);
                intent.putExtra(STUDENT_KEY, student);
                Integer choice = 4;
                intent.putExtra(CHOICE_KEY, choice);
                startActivity(intent);
                finish();
            }
        });

    }
}
