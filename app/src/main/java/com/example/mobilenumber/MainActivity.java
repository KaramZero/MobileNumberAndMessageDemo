package com.example.mobilenumber;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void next(View view){

        EditText number = findViewById(R.id.mobileNumber);
        EditText message = findViewById(R.id.message);

        String num =  number.getText().toString();

        if(num.length()>0){
            Pattern pattern = Pattern.compile("[0-9]+", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(num.trim());

            if(matcher.find()){
                Intent intent = new Intent(this,SecondActivity.class);
                intent.putExtra("number",num);
                intent.putExtra("message",message.getText().toString());
                startActivity(intent);
            }else{
                Toast toast = Toast.makeText(getApplicationContext(),"Enter valid phone number",Toast.LENGTH_SHORT);
                toast.show();
            }
        }else{
            Toast toast = Toast.makeText(getApplicationContext(),"phone number can't be empty",Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void close(View view){
        finishAffinity();
        System.exit(0);
    }
}