package com.example.mobilenumber;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class InternalStorage implements MyDataStorage {
    private static final String KEY = "key";
    private Context context;

    InternalStorage(Context x) {
        context = x;
    }


    public Map<String, String> getData() {


        return byFileInputStream();
        //return byFileReader();
        //return byDataInputStream();


    }


    Map<String, String> byFileInputStream() {

        Map<String, String> myMap = new HashMap<String, String>();

        try {
            context.openFileOutput("Data", context.MODE_APPEND).close();


            FileInputStream input = context.openFileInput("Data");

            StringBuilder s = new StringBuilder();
            int i = input.read();

            while (i != -1) {
                s.append((char) i);
                i = input.read();
            }
            input.close();

            String[] data = s.toString().split("#");

            int c = 0;

            for (i = 0; i < data.length - 1; i += 2) {
                Log.i("String Loaded ", data[i] + "  " + data[i + 1]);
                myMap.put(KEY + "num" + c, data[i]);
                myMap.put(KEY + "msg" + c, data[i + 1]);
                c++;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return myMap;
    }

    Map<String, String> byFileReader() {

        return new HashMap<>();
    }

    Map<String, String> byDataInputStream() {

        return new HashMap<>();
    }

    public void saveData(String n, String m, int pos) {
        try {
            String d = "#";

            FileOutputStream oFile = context.openFileOutput("Data", context.MODE_APPEND);
            oFile.write(n.getBytes());
            oFile.write(d.getBytes());
            oFile.write(m.getBytes());
            oFile.write(d.getBytes());
            oFile.close();

            Log.i("Data Saved", n + " " + m);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void clear() {
        Log.i("Delete res ", " " + context.deleteFile("Data"));
    }

}
