package com.example.pushupcounter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.pushupcounter.Adapters.Entry;
import com.example.pushupcounter.Adapters.LogAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class Logs extends AppCompatActivity {

    RecyclerView logsview;
    private Gson gson;
    private String json;
    private Type type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logs);

        loadElapsedTime();

//
        FileInputStream fis = null;

        try {
//
            fis = openFileInput("FILENAME");
//
            InputStreamReader isr = new InputStreamReader(fis);
//            FileReader fr = new FileReader("names.txt");
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;
            while ((text = br.readLine()) != null) {
                sb.append(text);
            }

            json = sb.toString();


            gson = new Gson();
            type = new TypeToken<ArrayList<Entry>>(){}.getType();

            ArrayList<Entry> newList = gson.fromJson(json, type);
            logsview = findViewById(R.id.logview);
            logsview.setHasFixedSize(true);

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            logsview.setLayoutManager(layoutManager);

            RecyclerView.Adapter mAdapter = new LogAdapter(this, newList);
            logsview.setAdapter(mAdapter);

            Toast.makeText(this, "Bundle Is Null", Toast.LENGTH_SHORT).show();


        } catch (FileNotFoundException ex) {
            Toast.makeText(this, "file not found", Toast.LENGTH_SHORT).show();
            ex.printStackTrace();
        } catch (IOException ex) {
            Toast.makeText(this, "Io Exception", Toast.LENGTH_SHORT).show();
            ex.printStackTrace();
        }
    }

//        finally {
//            if(fis != null){
//                try {
//                    fis.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }






//        if(data == null){
//            data = new ArrayList<>() ;
//        }





    public void loadElapsedTime() {
//


//        SharedPreferences sharedPreferences =  getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
////        gson = new Gson();
////        String json = sharedPreferences.getString("LogList", null);
////        Type type = new TypeToken<ArrayList<Entry>>(){}.getType() ;
////        ArrayList<Entry> data = gson.fromJson(json, type);
//
//        String entry = sharedPreferences.getString("Entry", null) ;
//        Toast.makeText(Logs.this, entry , Toast.LENGTH_LONG).show();

//
//        int size = data.size() ;
//        int i ;
//        for (i = 0 ; i < size ; i++) {
//
//            Entry dataentry = data.get(i);
//            String serialnumber = dataentry.getSlno();
//            Float chronometercount = dataentry.getChronometercount();
//            String count = dataentry.getPushcount();
//            Integer pushups = dataentry.getCounter();
//
//            secontime = "You did " + pushups + " pushups in " + chronometercount + " mins/secs" ; //pushups plus timetaken
//            Toast.makeText(this, secontime + " on " + serialnumber + " at " + count, Toast.LENGTH_LONG).show();
////        }
//
//        String date ,localtime ;
//        float timer ;
//        int pushups ;
//
//        date = sharedPreferences.getString("DATE", null) ;
//        localtime = sharedPreferences.getString("LOCALTIME", null);
//        timer = sharedPreferences.getFloat("TIMER", 0f);
//        pushups = sharedPreferences.getInt("PUSHUPS", 0) ;
//
//        secontime = "You did " + pushups + " pushups in " + timer + " mins/secs" ; //pushups plus timetaken
//        Toast.makeText(this, secontime + " on " + date + " at " + localtime, Toast.LENGTH_LONG).show();

    }
}
