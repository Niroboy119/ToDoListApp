package com.example.todolistapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "taskDB";
    private static final String TABLE_NAME = "taskTable";
    private static final String KEY_ID = "id";
    private static final String KEY_TASK_NAME = "taskName";
    private static final String KEY_TASK_DATE = "taskSetDate";
    private static final String KEY_TASK_DETAILS = "taskDetails";
    private static final String KEY_TASK_PRIORITY = "taskPriority";
    private static final String KEY_TASK_STATUS = "taskStatus";
    private SQLiteDatabase db;

    public DatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_Table = "CREATE TABLE " + TABLE_NAME + "(" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_TASK_NAME +
                " TEXT, " + KEY_TASK_DATE + " TEXT, " + KEY_TASK_DETAILS + " TEXT, " +
                KEY_TASK_PRIORITY + " TEXT, " + KEY_TASK_STATUS + " TEXT)" ;
        db.execSQL(create_Table);
        System.out.println("Todo table has been created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        System.out.println("Todo Table has been dropped");
    }

    public void AddTask(ToDoTask task){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TASK_NAME, task.getTaskName());
        values.put(KEY_TASK_DATE, task.getTaskSetDate());
        values.put(KEY_TASK_DETAILS, task.getTaskDetails());
        values.put(KEY_TASK_PRIORITY, task.getTaskPriority());
        values.put(KEY_TASK_STATUS, task.getTaskStatus());
        db.insert(TABLE_NAME, null, values);
    }

    public void EditTaskName(int id, String taskName){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TASK_NAME, taskName);
        db.update(TABLE_NAME, values, "ID=?" , new String[]{String.valueOf(id)});
    }

    public void EditTaskDate(int id, String taskDate){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TASK_DATE, taskDate);
        db.update(TABLE_NAME, values, "ID=?" , new String[]{String.valueOf(id)});
    }

    public void EditTaskDetails(int id, String taskDetails){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TASK_DETAILS, taskDetails);
        db.update(TABLE_NAME, values, "ID=?" , new String[]{String.valueOf(id)});
    }

    public void EditTaskPriority(int id, String taskPriority){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TASK_PRIORITY, taskPriority);
        db.update(TABLE_NAME, values, "ID=?" , new String[]{String.valueOf(id)});
    }

    public void EditTaskStatus(int id, String taskStatus){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TASK_STATUS, taskStatus);
        db.update(TABLE_NAME, values, "ID=?" , new String[]{String.valueOf(id)});
    }

    public void DeleteTask(int id){
        db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "ID=?" , new String[]{String.valueOf(id)});
    }

    public ArrayList<ToDoTask> GetAllTasks(){

        db = this.getWritableDatabase();
        Cursor cursor = null;
        ArrayList<ToDoTask> taskList = new ArrayList<>();

        db.beginTransaction();
        try {
            cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
            if (cursor != null){
                if (cursor.moveToFirst()){
                    do {
                        ToDoTask toDoTask = new ToDoTask();
                        toDoTask.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                        toDoTask.setTaskName(cursor.getString(cursor.getColumnIndex(KEY_TASK_NAME)));
                        toDoTask.setTaskSetDate(cursor.getString(cursor.getColumnIndex(KEY_TASK_DATE)));
                        toDoTask.setTaskDetails(cursor.getString(cursor.getColumnIndex(KEY_TASK_DETAILS)));
                        toDoTask.setTaskPriority(cursor.getString(cursor.getColumnIndex(KEY_TASK_PRIORITY)));
                        toDoTask.setTaskStatus(cursor.getString(cursor.getColumnIndex(KEY_TASK_STATUS)));
                        taskList.add(toDoTask);

                    }while(cursor.moveToNext());
                }
            }
        }finally {
            db.endTransaction();
            cursor.close();
        }

        return taskList;
    }


}
