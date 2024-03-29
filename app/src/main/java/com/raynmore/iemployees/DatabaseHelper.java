package com.raynmore.iemployees;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String EMPLOYEE_TABLE = "EMPLOYEE_TABLE";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PHONE_NUMBER = "phone_number";
    public static final String COLUMN_IMAGE_URL = "image_url";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "employees.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + EMPLOYEE_TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME + " String, " + COLUMN_PHONE_NUMBER + " String, " + COLUMN_IMAGE_URL + " String)";

        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addEmployee(Employee employee) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, employee.getName());
        cv.put(COLUMN_PHONE_NUMBER, employee.getPhoneNumber());
        cv.put(COLUMN_IMAGE_URL, employee.getImageId());

        long insert = db.insert(EMPLOYEE_TABLE, null, cv);

        return insert != -1;
    }
}
