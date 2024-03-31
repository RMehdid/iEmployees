package com.raynmore.iemployees;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String EMPLOYEE_TABLE = "EMPLOYEE_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PHONE_NUMBER = "phone_number";
    public static final String COLUMN_IMAGE_URL = "image_url";
    public static final String DB_NAME = "employees.db";
    public static final String COLUMN_EMAIL = "email";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + EMPLOYEE_TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME + " String, " + COLUMN_PHONE_NUMBER + " String, " + COLUMN_EMAIL + " String, " + COLUMN_IMAGE_URL + " int)";

        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addEmployee(@NonNull Employee employee) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, employee.getName());
        cv.put(COLUMN_PHONE_NUMBER, employee.getPhoneNumber());
        cv.put(COLUMN_EMAIL, employee.getEmail());
        cv.put(COLUMN_IMAGE_URL, employee.getImageId());

        db.insert(EMPLOYEE_TABLE, null, cv);
    }

    public void updateEmployee(@NonNull Employee employee) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, employee.getName());
        cv.put(COLUMN_PHONE_NUMBER, employee.getPhoneNumber());
        cv.put(COLUMN_EMAIL, employee.getEmail());
        cv.put(COLUMN_IMAGE_URL, employee.getImageId());

        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(employee.getId())};

        Log.d("ID", String.valueOf(employee.getId()));

        int rowsAffected = db.update(EMPLOYEE_TABLE, cv, selection, selectionArgs);

        // Check if the update was successful
        if (rowsAffected > 0) {
            Log.d("Database", "Employee updated successfully");
        } else {
            Log.d("Database", "Failed to update employee");
        }
    }

    public void deleteEmployee(@NonNull int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(id)};

        int rowsAffected = db.delete(EMPLOYEE_TABLE, selection, selectionArgs);

        // Check if the deletion was successful
        if (rowsAffected > 0) {
            Log.d("Database", "Employee deleted successfully");
        } else {
            Log.d("Database", "Failed to delete employee");
        }
    }

    public ArrayList<Employee> getEmployees() {

        ArrayList<Employee> employees = new ArrayList<>();

        String queryString = "SELECT * FROM " + EMPLOYEE_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String phone = cursor.getString(2);
                String email = cursor.getString(3);
                int image = cursor.getInt(4);

                employees.add(new Employee(id, name, phone, email, image));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return employees;
    }
}
