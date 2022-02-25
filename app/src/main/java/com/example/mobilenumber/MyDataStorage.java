package com.example.mobilenumber;

import java.util.Map;

public interface MyDataStorage {

    Map<String,String> getData();
    void saveData(String n ,String m,int pos);
    void clear();
}
