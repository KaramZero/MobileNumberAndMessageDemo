package com.example.mobilenumber;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class SecondActivity extends AppCompatActivity {

    final String KEY = "key";

    String receivedMessage;
    String receivedNumber;

    TextView sharedNumberTextView;
    TextView sharedMessageTextView;
    TextView internalNumberTextView;
    TextView internalMessageTextView;
    TextView sqlNumberTextView;
    TextView sqlMessageTextView;

    int internalCounter = -1;
    int sharedCounter = -1;

    Map<String, String> internalMap = new HashMap<>();
    Map<String, String> sharedMap = new HashMap<>();

    MyDataStorage internalStorage;
    MyDataStorage sharedStorage;
    DataBaseAdapter dataBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        Intent intent = getIntent();
        receivedMessage = intent.getStringExtra(MainActivity.msgKey);
        receivedNumber = intent.getStringExtra(MainActivity.numKey);

        getViews();
        setViewsText();


        internalStorage = new InternalStorage(this);
        sharedStorage = new MyPreferences(this);
        dataBaseAdapter = new DataBaseAdapter(this);

        internalMap = internalStorage.getData();
        sharedMap = sharedStorage.getData();


        if (savedInstanceState != null) {
            internalCounter = savedInstanceState.getInt("InternalCounter");
            sharedCounter = savedInstanceState.getInt("SharedCounter");

            internalNumberTextView.setText(internalMap.get(KEY + "num" + internalCounter));
            internalMessageTextView.setText(internalMap.get(KEY + "msg" + internalCounter));

            sharedNumberTextView.setText(sharedMap.get(KEY + "num" + sharedCounter));
            sharedMessageTextView.setText(sharedMap.get(KEY + "msg" + sharedCounter));

            MyDTO temp = dataBaseAdapter.getByNumber(savedInstanceState.getString("DataBaseKey"));
            if (temp != null) {
                sqlNumberTextView.setText(temp.getNumber());
                sqlMessageTextView.setText(temp.getMessage());
            } else {
                sqlNumberTextView.setText(R.string.DataNotFound);
                sqlMessageTextView.setText("");
            }


        } else {
            internalCounter = internalMap.size() / 2;
            internalMap.put(KEY + "num" + internalCounter, receivedNumber);
            internalMap.put(KEY + "msg" + internalCounter, receivedMessage);


            sharedCounter = sharedMap.size() / 2;
            sharedMap.put(KEY + "num" + sharedCounter, receivedNumber);
            sharedMap.put(KEY + "msg" + sharedCounter, receivedMessage);

            internalStorage.saveData(receivedNumber, receivedMessage, internalCounter);
            sharedStorage.saveData(receivedNumber, receivedMessage, sharedCounter);
            dataBaseAdapter.insert(new MyDTO(receivedNumber, receivedMessage));

        }

    }

    void getViews() {
        sharedNumberTextView = findViewById(R.id.SPmobileNumber);
        sharedMessageTextView = findViewById(R.id.SPmessage);

        internalNumberTextView = findViewById(R.id.INmobileNumber);
        internalMessageTextView = findViewById(R.id.INmessage);

        sqlNumberTextView = findViewById(R.id.SQmobileNumber);
        sqlMessageTextView = findViewById(R.id.SQmessage);

    }

    void setViewsText() {
        sharedNumberTextView.setText(receivedNumber);
        sharedMessageTextView.setText(receivedMessage);

        internalNumberTextView.setText(receivedNumber);
        internalMessageTextView.setText(receivedMessage);

        sqlNumberTextView.setText(receivedNumber);
        sqlMessageTextView.setText(receivedMessage);


    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("InternalCounter", internalCounter);
        outState.putInt("SharedCounter", sharedCounter);
        outState.putString("DataBaseKey", sqlNumberTextView.getText().toString());

    }

    public void close(View view) {
        finish();
    }


    public void nextShared(View view) {
        if (sharedCounter < sharedMap.size() / 2 - 1) {
            sharedCounter++;
            sharedNumberTextView.setText(sharedMap.get(KEY + "num" + sharedCounter));
            sharedMessageTextView.setText(sharedMap.get(KEY + "msg" + sharedCounter));
        }
    }

    public void prevShared(View view) {
        if (sharedCounter > 0) {
            sharedCounter--;
            sharedNumberTextView.setText(sharedMap.get(KEY + "num" + sharedCounter));
            sharedMessageTextView.setText(sharedMap.get(KEY + "msg" + sharedCounter));
        }
    }

    public void clearAllShared(View view) {
        sharedMap.clear();
        sharedCounter = 0;
        sharedNumberTextView.setText(R.string.AllCleard);
        sharedMessageTextView.setText(R.string.reTypeAgain);

        sharedStorage.clear();
    }

    public void nextInternal(View view) {
        if (internalCounter < internalMap.size() / 2 - 1) {
            internalCounter++;
            internalNumberTextView.setText(internalMap.get(KEY + "num" + internalCounter));
            internalMessageTextView.setText(internalMap.get(KEY + "msg" + internalCounter));
        }
    }

    public void prevInternal(View view) {
        if (internalCounter > 0) {
            internalCounter--;
            internalNumberTextView.setText(internalMap.get(KEY + "num" + internalCounter));
            internalMessageTextView.setText(internalMap.get(KEY + "msg" + internalCounter));
        }
    }

    public void clearAllInternal(View view) {
        internalMap.clear();
        internalCounter = 0;
        internalNumberTextView.setText(R.string.AllCleard);
        internalMessageTextView.setText(R.string.reTypeAgain);

        internalStorage.clear();
    }

    public void getSQShared(View view) {
        MyDTO temp = dataBaseAdapter.getByNumber(sharedNumberTextView.getText().toString());
        if (temp != null) {
            sqlNumberTextView.setText(temp.getNumber());
            sqlMessageTextView.setText(temp.getMessage());
        } else {
            sqlNumberTextView.setText(R.string.DataNotFound);
            sqlMessageTextView.setText("");
        }
    }

    public void getSQInternal(View view) {
        MyDTO temp = dataBaseAdapter.getByNumber(internalNumberTextView.getText().toString());
        if (temp != null) {
            sqlNumberTextView.setText(temp.getNumber());
            sqlMessageTextView.setText(temp.getMessage());
        } else {
            sqlNumberTextView.setText(R.string.DataNotFound);
            sqlMessageTextView.setText("");
        }
    }

    public void clearAllSQ(View view) {
        dataBaseAdapter.clearAllData();
    }


}