/*
Homework 1
Group#5_HW01
Christopher Stump && Houa Vue
 */

package com.example.chris.timeconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.chris.timeconverter.R.id.easternRadio;
import static com.example.chris.timeconverter.R.id.resultSpace;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etHours, etMins;
    private TextView tvResult;
    private RadioGroup rg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etHours = (EditText) findViewById(R.id.hoursInput1);
        etMins = (EditText) findViewById(R.id.minuteInpute1);
        tvResult = (TextView) findViewById(R.id.resultSpace);
        rg = (RadioGroup) findViewById(R.id.radioGroup);


        findViewById(R.id.easternButton).setOnClickListener(this);
        findViewById(R.id.centralButton).setOnClickListener(this);
        findViewById(R.id.mountainButton).setOnClickListener(this);
        findViewById(R.id.pacificButton).setOnClickListener(this);
        findViewById(R.id.clearButton).setOnClickListener(this);
        findViewById(R.id.convertButton2).setOnClickListener(this);

        final LinearLayout button_layout = (LinearLayout) findViewById(R.id.buttonLayout);
        final LinearLayout radio_layout = (LinearLayout) findViewById(R.id.radioLayout);
        final Switch sw = (Switch) this.findViewById(R.id.buttonSwitch1);

        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked()){
                    radio_layout.setVisibility(View.GONE);
                    button_layout.setVisibility(View.VISIBLE);
                } else{
                    radio_layout.setVisibility(View.VISIBLE);
                    button_layout.setVisibility(View.GONE);
                }
            }
        });


        findViewById(R.id.convertButton2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!etHours.getText().toString().equals("") && !etMins.getText().toString().equals("")) {
                    int hours = Integer.parseInt(etHours.getText().toString());
                    int minutes = Integer.parseInt(etMins.getText().toString());
                    int newHour = 0;

                    String minuteFormat = Integer.toString(minutes);
                    if (minuteFormat.length() == 1) {
                        minuteFormat = "0" + minuteFormat;
                    }

                    boolean hoursCheck = false, minutesCheck = false;
                    if (hours >= 0 && hours <= 23) {
                        hoursCheck = true;
                    }
                    if (minutes >= 0 && minutes <= 59) {
                        minutesCheck = true;
                    }
                    if(hoursCheck && minutesCheck) {
                        if(rg.getCheckedRadioButtonId() == R.id.easternRadio){
                            newHour = hours - 5;
                            if(newHour < 0){
                                newHour = 24 + newHour;
                                tvResult.setText("EST: " + newHour + ":" + minuteFormat + " (PREVIOUS DAY)");
                            } else{
                                tvResult.setText("EST: " + newHour + ":" + minuteFormat);
                            }
                        } else if(rg.getCheckedRadioButtonId() == R.id.centralRadio){
                            newHour = hours - 6;
                            if(newHour < 0){
                                newHour = 24 + newHour;
                                tvResult.setText("CST: " + newHour + ":" + minuteFormat + " (PREVIOUS DAY)");
                            } else{
                                tvResult.setText("CST: " + newHour + ":" + minuteFormat);
                            }
                        } else if(rg.getCheckedRadioButtonId() == R.id.mountainRadio){
                            newHour = hours - 7;
                            if(newHour < 0){
                                newHour = 24 + newHour;
                                tvResult.setText("MST: " + newHour + ":" + minuteFormat + " (PREVIOUS DAY)");
                            } else{
                                tvResult.setText("MST: " + newHour + ":" + minuteFormat);
                            }
                        } else if(rg.getCheckedRadioButtonId() == R.id.pacificRadio){
                            newHour = hours - 8;
                            if(newHour < 0){
                                newHour = 24 + newHour;
                                tvResult.setText("PST: " + newHour + ":" + minuteFormat + " (PREVIOUS DAY)");
                            } else{
                                tvResult.setText("PST: " + newHour + ":" + minuteFormat);
                            }
                        } else{
                            etHours.setText("");
                            etHours.setHint("Hours");
                            etMins.setText("");
                            etMins.setHint("Minutes");
                            tvResult.setText("Result: ");
                            rg.check(easternRadio);
                        }

                    } else{
                        Toast toast = Toast.makeText(getBaseContext(), "Hours or minutes does not have a valid input.", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }else{
                    Toast toast = Toast.makeText(getBaseContext(), "Hours or minutes does not have a valid input.", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        if(!etHours.getText().toString().equals("") && !etMins.getText().toString().equals("")) {
            int hours = Integer.parseInt(etHours.getText().toString());
            int minutes = Integer.parseInt(etMins.getText().toString());
            int newHour = 0;

            String minuteFormat = Integer.toString(minutes);
            if(minuteFormat.length() == 1){
                minuteFormat = "0" + minuteFormat;
            }

            boolean hoursCheck = false, minutesCheck = false;
            if(hours >= 0 && hours <= 23){
                hoursCheck = true;
            }
            if(minutes >= 0 && minutes <= 59){
                minutesCheck = true;
            }

            if(hoursCheck && minutesCheck){
                if(view.getId() == R.id.easternButton){
                    newHour = hours - 5;
                    if(newHour < 0){
                        newHour = 24 + newHour;
                        tvResult.setText("EST: " + newHour + ":" + minuteFormat + " (PREVIOUS DAY)");
                    } else{
                        tvResult.setText("EST: " + newHour + ":" + minuteFormat);
                    }
                } else if(view.getId() == R.id.centralButton){
                    newHour = hours - 6;
                    if(newHour < 0){
                        newHour = 24 + newHour;
                        tvResult.setText("CST: " + newHour + ":" + minuteFormat + " (PREVIOUS DAY)");
                    } else{
                        tvResult.setText("CST: " + newHour + ":" + minuteFormat);
                    }

                } else if(view.getId() == R.id.mountainButton){
                    newHour = hours - 7;
                    if(newHour < 0){
                        newHour = 24 + newHour;
                        tvResult.setText("MST: " + newHour + ":" + minuteFormat + " (PREVIOUS DAY)");
                    } else{
                        tvResult.setText("MST: " + newHour + ":" + minuteFormat);
                    }

                } else if(view.getId() == R.id.pacificButton) {
                    newHour = hours - 8;
                    if(newHour < 0){
                        newHour = 24 + newHour;
                        tvResult.setText("PST: " + newHour + ":" + minuteFormat + " (PREVIOUS DAY)");
                    } else{
                        tvResult.setText("PST: " + newHour + ":" + minuteFormat);
                    }

                } else{
                    etHours.setText("");
                    etHours.setHint("Hours");
                    etMins.setText("");
                    etMins.setHint("Minutes");
                    tvResult.setText("Result: ");
                }

            }else{
                Toast toast = Toast.makeText(this, "Hours or minutes does not have a valid input.", Toast.LENGTH_SHORT);
                toast.show();
            }
        } else{
            Toast toast = Toast.makeText(this, "Hours or minutes does not have a valid input.", Toast.LENGTH_SHORT);
            toast.show();
        }

    }
}
