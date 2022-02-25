package com.example.mobilenumber;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    public static final String msgKey = "message";
    public static final String numKey = "number";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

    }
    public void next(View view){

        EditText number = findViewById(R.id.mobileNumber);
        EditText message = findViewById(R.id.message);

        String num =  number.getText().toString();

        if(num.length()>0){
            Pattern pattern = Pattern.compile("^\\d+$", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(num.trim());

            if(matcher.find()){
                Intent intent = new Intent(this,SecondActivity.class);
                intent.putExtra(numKey,num);
                intent.putExtra(msgKey,message.getText().toString());
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

    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {

            });

    public void close(View view){
        finish();
    }
}