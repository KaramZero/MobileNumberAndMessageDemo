package com.example.mobilenumber;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Formattable;
import java.util.HashMap;
import java.util.Map;

public class SecondActivity extends AppCompatActivity {

    final String KEY ="key";
    TextView number;
    TextView message;
    String msg;
    String num;
    int counter=-1;
    Map<String,String> map = new HashMap<String, String>();

    MyDataStorage storage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        Intent intent = getIntent();
        msg = intent.getStringExtra(MainActivity.msgKey);
        num = intent.getStringExtra(MainActivity.numKey);

       storage = new InternalStorage(this);
        //storage = new MyPreferences(this);

        number = findViewById(R.id.mobileNumber);
        message = findViewById(R.id.message);

        number.setText(num);
        message.setText(msg);

        map= storage.getData();

        if (savedInstanceState!=null){
            counter=savedInstanceState.getInt("counter");
            number.setText(map.get(KEY+"num"+counter));
            message.setText(map.get(KEY+"msg"+counter));
        }
        else {
            counter =map.size()/2;
            map.put(KEY+"num"+counter,num);
            map.put(KEY+"msg"+counter, msg);

            storage.saveData(num,msg,counter);

        }

    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("counter",counter);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void close(View view){
        finish();
    }
    public void nextMSG(View view){

        if(counter<map.size()/2-1){
            counter++;
            number.setText(map.get(KEY+"num"+counter));
            message.setText(map.get(KEY+"msg"+counter));
        }

    }
    public void prevMSG(View view){
        if(counter>0){
            counter--;
            number.setText(map.get(KEY+"num"+counter));
            message.setText(map.get(KEY+"msg"+counter));
        }
    }
    public void clearAll(View view){
        map.clear();
        counter = 0;
        number.setText("All are cleared ");
        message.setText("Back to enter new data ");

        storage.clear();
    }
}