package com.example.tjng.dolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.EditText;

import java.util.ArrayList;

public class MySQLiteHelper extends SQLiteOpenHelper {
    //attributes in the first table Contacts
    public static final String TABLE_NAME = "Contacts";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_UNAME = "username";
    public static final String COLUMN_PASS = "password";
    public static final String COLUMN_NAME = "name";

    //attributes in the 2nd table Tasks
    public static final String DB_TABLE= "Tasks";
    public static final String DB_COLUMN= "TaskName";

    private static final String DATABASE_NAME = "Contacts.db";
    private static final int DATABASE_VERSION = 5;
    SQLiteDatabase db;


    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table Contacts (id integer primary key not null," +
            "name text not null , username text not null , password text not null)";

    private static final String TASKTABLE_CREATE="create table Tasks (id integer primary key not null, "+
            "TaskName text not null)";
    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
        db.execSQL(TASKTABLE_CREATE);
        this.db = db;
    }

    public void insertContact(Contact c) {

        //save contacts into the database

        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String query = "select * from Contacts";
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();
        values.put(COLUMN_ID, count);
        values.put(COLUMN_NAME, c.getName());
        values.put(COLUMN_UNAME, c.getUsername());
        values.put(COLUMN_PASS, c.getPassword());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public String searchPass(String username) {

        //search for password uses by the username

        db = this.getReadableDatabase();
        String query = "select username, password from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        String a, b;
        b = "not found";
        if (cursor.moveToFirst()) {
            do {
                a = cursor.getString(0);
                if (a.equals(username)) {
                    b = cursor.getString(1);
                    break;
                }
            } while (cursor.moveToNext());
        }

        return b;

    }
    public String searchuser(String username) {
        db = this.getReadableDatabase();
        String query = "select username from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        String a, b;
        b = "not found";
        if (cursor.moveToFirst()) {
            do {
                a = cursor.getString(0);
                if (a.equals(username)) {
                    b = cursor.getString(0);
                    break;
                }
            } while (cursor.moveToNext());
        }

        return b;

    }
    public void insertNewTask(String task){
        //to insert the task into the database

        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DB_COLUMN,task);

        db.insertWithOnConflict(DB_TABLE,null,values,SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public void deleteTask(String task){
        //to remove the list from the database

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DB_TABLE,DB_COLUMN + " = ?",new String[]{task});
        db.close();
    }

    public ArrayList<String> getTaskList(){
        //to create list

        ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DB_TABLE,new String[]{DB_COLUMN},null,null,null,null,null);
        while(cursor.moveToNext()){
            int index = cursor.getColumnIndex(DB_COLUMN);
            taskList.add(cursor.getString(index));
        }
        cursor.close();
        db.close();
        return taskList;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //If this is not the first time, check the version number in the database. If current version number in is higher, call onUpgrade(SQLiteDatabase, int, int)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ DB_TABLE);
        onCreate(db);
    }
}

