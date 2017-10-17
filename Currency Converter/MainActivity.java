package com.example.houavue.inclass2a;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText et;
    private TextView tv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et = (EditText) findViewById(R.id.inputLine);
        tv = (TextView) findViewById(R.id.outputLine);


            findViewById(R.id.button_euro).setOnClickListener(this);
            findViewById(R.id.button_cad).setOnClickListener(this);
            findViewById(R.id.button_gbp).setOnClickListener(this);
            findViewById(R.id.button_jap).setOnClickListener(this);
            findViewById(R.id.button_clear).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        double setAmount;
        double result;

        String str = et.getText().toString();
        int index = str.indexOf('.');
        int size = str.length();

        if(size - index - 1 > 2) {
            Toast toast = Toast.makeText(getBaseContext(), "More than two decimal places.", Toast.LENGTH_SHORT);
            toast.show();
        } else if (view.getId() == R.id.button_euro) {
            Log.d("demo", "Euro button clicked");
            setAmount = Double.parseDouble(et.getText().toString());
            result = setAmount * 0.849282;
            result = round(result, 2);
            tv.setText(setAmount + " USD = " + result + " EUR");
        } else if(view.getId() == R.id.button_cad ) {
            Log.d("demo", "Canadian button clicked");
            setAmount = Double.parseDouble(et.getText().toString());
            result = setAmount * 1.19;
            result = round(result, 2);
            tv.setText(setAmount + " USD = " + result + " CAD");
        } else if(view.getId() == R.id.button_gbp) {
            Log.d("demo", "British button clicked.");
            setAmount = Double.parseDouble(et.getText().toString());
            result = setAmount * 0.65;
            result = round(result, 2);
            tv.setText(setAmount + " USD = " + result + " GDP");
        } else if(view.getId() == R.id.button_jap) {
            Log.d("demo", "Japanese button clicked.");
            setAmount = Double.parseDouble(et.getText().toString());
            result = setAmount * 117.62;
            result = round(result, 2);
            tv.setText(setAmount + " USD = " + result + " JPY");
        } else if(view.getId() == R.id.button_clear) {
            Log.d("demo", "Clear button clicked.");
            et.setText(R.string.defaultInput);
            tv.setText(R.string.defaultOutput);

        }
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}

