package com.example.forexample;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int version = 1;
    private static final String name = "Tasks";
    private static final String Table_name = "Task";
    private static final String ID = "id";
    private static final String Task = "task";

    public DatabaseHandler(@Nullable Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + Table_name + " ( " + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Task + " TEXT " + " ) ";
        sqLiteDatabase.execSQL(query);
    }

    public void addTask(String task) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Task, task);
        db.insert(Table_name, null, values);
        db.close();
    }

    public void deleteTask(String task) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(Table_name, Task + " = ?", new String[]{task});
        db.close();
    }

    @SuppressLint("Range")
    public List<String> getAllTasks() {
        List<String> tasks = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Table_name, null);
        if (cursor.moveToFirst()) {
            do {
                tasks.add(cursor.getString(cursor.getColumnIndex(Task)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return tasks;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Table_name);
        onCreate(sqLiteDatabase);
    }
}
