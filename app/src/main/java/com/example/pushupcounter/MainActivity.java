package com.example.pushupcounter;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pushupcounter.Adapters.Entry;
import com.google.firebase.database.DatabaseReference;
import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //declaration of Fields from layout
    private TextView motimsg;
    private TextView stop;
    private TextView showlog;
    private TextView timer;

    private int totalcount; //number of pushups
    private Chronometer stopwatch;
    private boolean stopwatchrunning;
    private long pauseOffset;
    protected long time;
    private ArrayList<Entry> mLogList;
    private Boolean disabled;//buttons setDisabled
    private String json;
    Intent intentLog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLogList = new ArrayList<>();
        long savedValue = 0l;
        SharedPreferences prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE);

        if (prefs.contains("hello")) {
            savedValue = prefs.getLong("myTime", 0l);
            stopwatch.setBase(savedValue);
        }

//Assignment of local layout fields
        motimsg = findViewById(R.id.motimsg);
        final TextView start = findViewById(R.id.start);
        start.setText(R.string.start);
        timer = findViewById(R.id.timer);
        stop = findViewById(R.id.stop);
        showlog = findViewById(R.id.showlog);
        stopwatch = findViewById(R.id.stopwatch);
        final TextView plus = findViewById(R.id.plus);
        final TextView minus = findViewById(R.id.minus);


        totalcount = 0;
        timer.setText(String.valueOf(totalcount));

//on clicking the start button
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//if start button is clicked
                if ((start.getText().toString()).equals("Start")) {
                    disabled = false;
                    if (!stopwatchrunning) {
                        stopwatch.start();
                        stopwatch.setBase(SystemClock.elapsedRealtime() - pauseOffset);
                        stopwatchrunning = true;
                        start.setText(R.string.pause);

                    }
//if pause button is clicked
                } else if ((start.getText().toString()).equals("Pause")) {
                    disabled = true;
                    if (stopwatchrunning) {
                        stopwatch.stop();
                        pauseOffset = SystemClock.elapsedRealtime() - stopwatch.getBase();
                        stopwatchrunning = false;
                        start.setText(R.string.start);

                    }
                }


//if plus btn is clicked
                plus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //checking if btn is disabled
                        if (disabled) {
                            motimsg.setText("Nice Try, now start the timer and do it");
                        } else {
                            totalcount += 20;
                            timer.setText(Integer.toString(totalcount));
                            ArrayList<String> lista = new ArrayList<String>();
                            lista.add("You Can Do It");
                            lista.add("Go 30 More, Push Harder");
                            lista.add("Spare the rod or spoil the child");
                            lista.add(" “Don’t Let Yesterday Take Up Too Much Of Today.” – Will Rogers");
                            lista.add(" “The Pessimist Sees Difficulty In Every Opportunity. The Optimist Sees Opportunity In Every Difficulty.– Winston Churchill");
                            lista.add("“The Way Get Started Is To Quit Talking And Begin Doing.” – Walt Disney");
                            lista.add(" “It’s Not Whether You Get Knocked Down, It’s Whether You Get Up.” – Inspirational Quote By Vince Lombardi");
                            lista.add("“If You Are Working On Something That You Really Care About, You Don’t Have To Be Pushed. The Vision Pulls You.” – Steve Jobs");
                            lista.add("“People Who Are Crazy Enough To Think They Can Change The World, Are The Ones Who Do.” – Rob Siltanen");
                            lista.add("“Failure Will Never Overtake Me If My Determination To Succeed Is Strong Enough.” – Og Mandino");
                            lista.add("“We May Encounter Many Defeats But We Must Not Be Defeated.” – Maya Angelou");
                            lista.add("“Knowing Is Not Enough; We Must Apply. Wishing Is Not Enough; We Must Do.” – Johann Wolfgang Von Goethe");
                            lista.add("“Imagine Your Life Is Perfect In Every Respect; What Would It Look Like?” – Brian Tracy");

                            int randomIndex = (int) (Math.random() * lista.size());
                            motimsg.setText(lista.get(randomIndex));
                        }
                    }
                });


                stop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (disabled) {

                            motimsg.setText("Nice Try, now start the timer and do it");
                        } else {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                showElapsedTime(totalcount);
                            }
                            time = stopwatch.getBase() - SystemClock.elapsedRealtime();

                            stopwatch.setBase(SystemClock.elapsedRealtime());
                            pauseOffset = 0;


                            totalcount = 0;
                            timer.setText(Integer.toString(totalcount));

                        }
                    }
                });


                minus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (disabled) {
                            motimsg.setText("Nice Try, now start the timer and do it");
                        } else {

                            int timervalue = Integer.valueOf((String) timer.getText());


                            if (!Integer.valueOf(timervalue).equals(0)) {
                                totalcount -= 20;
                                timer.setText(Integer.toString(totalcount));
                            } else {
                                timer.setText(Integer.toString(totalcount));
                            }
                        }


                    }
                });


                showlog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        intentLog = new Intent(MainActivity.this, Logs.class);
                        startActivity(intentLog);

                    }
                });


            }
        });


    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void  showElapsedTime(int totalcount) {


        float elapsedSecs = (SystemClock.elapsedRealtime() - stopwatch.getBase()) / 1000;
        if (elapsedSecs > 60) {
            float elapsedMins = elapsedSecs / 60;

            LocalDate todate = LocalDate.now();
            String formattedDate = todate.format(DateTimeFormatter.ofPattern("dd"));

            LocalTime today = LocalTime.now();
            String timeString = today.format(DateTimeFormatter.ofPattern("HH.mm"));

            Entry listEntry = new Entry(formattedDate, timeString, elapsedMins, totalcount);
            mLogList.add(listEntry);

            Gson gson = new Gson();
            json = gson.toJson(mLogList);


            if (elapsedMins > 1) {

                Toast.makeText(MainActivity.this, "You did " + totalcount + " pushups in " + elapsedMins + " mins at " + timeString + " on " + formattedDate,
                        Toast.LENGTH_SHORT).show();
            } else {

                Toast.makeText(MainActivity.this, "You did " + totalcount + " pushups in " + elapsedMins + " min at " + timeString + " on " + formattedDate,
                        Toast.LENGTH_SHORT).show();
            }

//
//            FileWriter writer = null;
//            try {
//                writer = new FileWriter("names.txt");
//                BufferedWriter bwr = new BufferedWriter(writer);
//                bwr.write(json);
//                Toast.makeText(this, "saved to" + getFilesDir() + "/" + "names.txt", Toast.LENGTH_SHORT).show();
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

            FileOutputStream fos = null ;

            try {
                fos = openFileOutput("FILENAME", MODE_PRIVATE);
                fos.write(json.getBytes());
                Toast.makeText(this, "saved to" + getFilesDir() + "/" + "FILENAME", Toast.LENGTH_SHORT).show();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(fos != null){
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }



        } else {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH.mm");

            LocalDate todate = LocalDate.now();
            String formattedDate = todate.format(DateTimeFormatter.ofPattern("dd"));


            LocalTime today = LocalTime.now();

            String timeString = today.format(formatter);
            mLogList.add(new Entry(formattedDate, timeString, elapsedSecs, totalcount));

            Gson gson = new Gson();
            String json = gson.toJson(mLogList);


            if (elapsedSecs > 1) {

                Toast.makeText(MainActivity.this, "You did " + totalcount + " pushups in " + elapsedSecs + " secs at " + timeString + " on " + formattedDate,
                        Toast.LENGTH_SHORT).show();
            } else {

                Toast.makeText(MainActivity.this, "You did " + totalcount + " pushups in " + elapsedSecs + " sec at " + timeString + " on " + formattedDate,
                        Toast.LENGTH_SHORT).show();
            }

//
//            FileWriter writer = null;
//            try {
//                writer = new FileWriter("names.txt");
//                BufferedWriter bwr = new BufferedWriter(writer);
//                bwr.write(json);
//                Toast.makeText(this, "saved to" + getFilesDir() + "/" + "names.txt", Toast.LENGTH_SHORT).show();
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

            FileOutputStream fos = null ;

            try {
                fos = openFileOutput("FILENAME", MODE_PRIVATE);
                fos.write(json.getBytes());
                Toast.makeText(this, "saved to" + getFilesDir() + "/" + "FILENAME", Toast.LENGTH_SHORT).show();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(fos != null){
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }


        }

    }

}

//    protected void onDestroy() {
//        super.onDestroy();
//       long Destroytime  = stopwatch.getBase()-SystemClock.elapsedRealtime() ;
//
//        SharedPreferences pref = getApplicationContext().getSharedPreferences("prefs", Context.MODE_PRIVATE) ;
//        SharedPreferences.Editor editor = pref.edit();
//        editor.putLong("myTime", Destroytime) ;
//        editor.apply();
//    }




//            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE) ;
//            SharedPreferences.Editor editor = sharedPreferences.edit() ;
//            editor.putString("DATE", formattedDate) ;
//            editor.putString("LOCALTIME", timeString) ;
//            editor.putFloat("TIMER", elapsedMins) ;
//            editor.putInt("PUSHUPS", totalcount);
//            Gson gson= new Gson();
//            String json = gson.toJson(mLogList) ;
//            editor.putString("LogList", json) ;

//            editor.putString("DATE", formattedDate) ;
//            editor.putString("LOCALTIME", timeString) ;
//            editor.putFloat("TIMER", elapsedSecs) ;
//            editor.putInt("PUSHUPS", totalcount);
//            String entry = "You did " + totalcount + " pushups in "  + elapsedSecs + " secs at " + timeString + " on " + formattedDate ;
//
//
//            editor.putString("Entry",entry);
//
//    SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE) ;
//    SharedPreferences.Editor editor = sharedPreferences.edit() ;
//
//
//            editor.putString("LogList", json) ;