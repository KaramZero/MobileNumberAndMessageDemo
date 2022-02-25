package com.example.mobilenumber;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

public class MyPreferences implements MyDataStorage {

    private SharedPreferences data;
    private Context context;
    private static final String KEY = "key";


    MyPreferences(Context x) {
        context = x;
    }


    public Map<String, String> getData() {
        data = context.getSharedPreferences("Data", context.MODE_PRIVATE);
        return (Map<String, String>) data.getAll();
    }

    public void saveData(String n, String m, int pos) {


        data = context.getSharedPreferences("Data", context.MODE_APPEND);

        SharedPreferences.Editor editor = data.edit();

        editor.putString(KEY + "num" + pos, n);
        editor.putString(KEY + "msg" + pos, m);

        editor.commit();

    }

    public void clear() {
        data.edit().clear().commit();
    }
}
