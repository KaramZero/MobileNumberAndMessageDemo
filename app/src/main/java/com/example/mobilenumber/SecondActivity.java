package com.example.mobilenumber;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SecondActivity extends AppCompatActivity {

    final String KEY ="key";
    TextView number;
    TextView message;
    String msg;
    String num;
    int counter=-1;
    Map<String,String> map = new HashMap<String, String>();
    SharedPreferences data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        Intent intent = getIntent();
        msg = intent.getStringExtra(MainActivity.msgKey);
        num = intent.getStringExtra(MainActivity.numKey);

        number = findViewById(R.id.mobileNumber);
        message = findViewById(R.id.message);

        number.setText(num);
        message.setText(msg);


        data=getPreferences(MODE_PRIVATE);

        map= (Map<String, String>) data.getAll();


        if (savedInstanceState!=null){
            counter=savedInstanceState.getInt("counter");
            number.setText(map.get(KEY+"num"+counter));
            message.setText(map.get(KEY+"msg"+counter));
        }
        else {
            counter =map.size()/2;
            map.put(KEY+"num"+counter,num);
            map.put(KEY+"msg"+counter, msg);
        }


    }

    void saveData(){


        SharedPreferences.Editor editor = data.edit();
        editor.clear().commit();

        for(int i=0;i<map.size()/2;i++){
            editor.putString(KEY+"num"+i,map.get(KEY+"num"+i));
            editor.putString(KEY+"msg"+i, map.get(KEY+"msg"+i));
        }
        editor.commit();

    }



    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("counter",counter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveData();
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
    }
}