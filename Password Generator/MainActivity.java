/*
InClass04
Christopher Stump
800870867_InClass04
 */
package com.example.chris.passwordgenerator;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.preference.DialogPreference;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MainActivity extends AppCompatActivity {

    final int STATUS_START = 0x00;
    final int STATUS_STEP = 0x01;
    final int STATUS_DONE = 0x02;

    ExecutorService threadPool;
    private SeekBar sbCount, sbLength;
    private TextView tvCount, tvLength, result;
    Handler handler;
    ProgressDialog progressDialog;
    ArrayList<String> passwords;

    int progress;

    public class GeneratePass implements Runnable{
        int length;
        String password;

        public GeneratePass(int length){
            this.length = length;
        }

        @Override
        public void run() {
            password = Util.getPassword(length, true, true, true, true);

            Message msg = new Message();
            msg.what = STATUS_STEP;
            msg.obj = password;
            handler.sendMessage(msg);

            Log.d("demo", "Created password: " + password);
        }
    }

    public class GeneratePass2 extends AsyncTask<Integer,Integer,String>{
        int length, amount;
        String password;

        @Override
        protected String doInBackground(Integer... params) {
            length=params[0];
            amount = params[1];
            password = Util.getPassword(length, true, true, true, true);
            passwords.add(password);
            publishProgress(1);

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(progress == amount){
                progressDialog.dismiss();
                final String [] passwordList = passwords.toArray(new String[passwords.size()]);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Passwords").setItems(passwordList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("demo", "Selected: " + passwordList[which]);
                        result.setText("Password: " + passwordList[which]);
                    }
                });
                builder.show();
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progress++;
            progressDialog.setProgress(progress);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sbCount = (SeekBar) findViewById(R.id.countSeek);
        sbLength = (SeekBar) findViewById(R.id.lengthSeek);
        tvCount = (TextView) findViewById(R.id.passCountDisplay);
        tvLength = (TextView) findViewById(R.id.passLengthDisplay);
        result = (TextView) findViewById(R.id.generatedDisplay);

        threadPool = Executors.newFixedThreadPool(2);

        passwords = new ArrayList<>();

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch(msg.what){
                    case STATUS_START:
                        progressDialog.show();
                        Log.d("demo", "Thread Started");
                        break;
                    case STATUS_STEP:
                        passwords.add((String) msg.obj);
                        progressDialog.setProgress(passwords.size());
                        Log.d("demo", "Step " + passwords.size() + " Completed.");
                        if(passwords.size() == sbCount.getProgress()){
                            progressDialog.dismiss();
                            final String [] passwordList = passwords.toArray(new String[passwords.size()]);
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setTitle("Passwords").setItems(passwordList, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.d("demo", "Selected: " + passwordList[which]);
                                    result.setText("Password: " + passwordList[which]);
                                }
                            });
                            builder.show();
                        }
                        break;
                }
                return false;
            }
        });



        findViewById(R.id.threadButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Work for Thread Button
                int amount = sbCount.getProgress();
                int passLength = sbLength.getProgress() + 8;

                progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setMessage("Generating Passwords...");
                progressDialog.setMax(amount);
                progressDialog.setCancelable(false);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

                Message msg = new Message();
                msg.what = STATUS_START;
                handler.sendMessage(msg);

                passwords.clear();

                for(int i = 0; i < amount; i++) {
                    threadPool.execute(new GeneratePass(passLength));
                }
            }
        });

        findViewById(R.id.asyncButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Work for Async Button
                int amount = sbCount.getProgress();
                int passLength = sbLength.getProgress() + 8;

                passwords.clear();
                progress = 0;

                progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setMessage("Generating Passwords...");
                progressDialog.setMax(amount);
                progressDialog.setCancelable(false);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.show();
                for(int i = 0; i < amount; i++) {
                    new GeneratePass2().execute(passLength, amount);
                }

            }
        });

        sbCount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvCount.setText("Select Passwords count : " + sbCount.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        sbLength.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvLength.setText("Select Passwords length : " + (sbLength.getProgress() + 8));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}
