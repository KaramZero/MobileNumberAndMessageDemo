package com.example.mobilenumber;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseAdapter {

    private final Context context;
    static DataBaseHelper dbHelper;

    public DataBaseAdapter(Context context) {
        dbHelper = new DataBaseHelper(context);
        this.context = context;
    }

    public void insert(MyDTO myDTO) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(DataBaseHelper.NUMBER, myDTO.getNumber());
        contentValues.put(DataBaseHelper.MESSAGE, myDTO.getMessage());
        db.insert(DataBaseHelper.TABLE_NAME, null, contentValues);

        db.close();
    }

    public MyDTO getByNumber(String number) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        MyDTO myDTO = null;
        Cursor c;
        c = db.query(DataBaseHelper.TABLE_NAME, new String[]{DataBaseHelper.MESSAGE}, DataBaseHelper.NUMBER + " = ?", new String[]{number}, null, null, null);

        if (c.moveToNext()) {
            myDTO = new MyDTO(number, c.getString(0));
        }
        c.close();
        db.close();


        return myDTO;
    }

    public void clearAllData(){
        String DELETE = "DELETE FROM " + DataBaseHelper.TABLE_NAME + ";";
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL(DELETE);

    }

    private static class DataBaseHelper extends SQLiteOpenHelper {

        private static final String TABLE_NAME = "Numbers";
        private static final String NUMBER = "Num";
        private static final String MESSAGE = "Message";

        private static final String CREATE_NUMBERS_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + NUMBER + " INTEGER PRIMARY KEY, " +
                MESSAGE + " TEXT);";

        public DataBaseHelper(Context context) {
            super(context, "MyDBs", null, 1);

        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_NUMBERS_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
