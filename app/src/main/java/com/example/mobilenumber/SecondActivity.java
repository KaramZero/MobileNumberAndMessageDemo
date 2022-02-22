package com.example.mobilenumber;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent intent = getIntent();
        String mes = intent.getStringExtra("message");
        String num = intent.getStringExtra("number");

        TextView number = findViewById(R.id.mobileNumber);
        TextView message = findViewById(R.id.message);

        number.setText(num);
        message.setText(mes);

    }

    public void close(View view){
        finishAffinity();
        System.exit(0);
    }
}