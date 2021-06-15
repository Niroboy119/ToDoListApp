package com.example.todolistapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class AddTask extends AppCompatActivity {
    EditText addTaskName;
    EditText addTaskDate;
    EditText addTaskDetails;
    CheckBox taskPriorityCheck;
    DatePickerDialog datePicker;
    ToDoTask newTask;
    MenuItem addTaskMenuItem;
    DatabaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        addTaskName = findViewById(R.id.addTaskName);
        addTaskDate = findViewById(R.id.addTaskDate);
        addTaskDetails = findViewById(R.id.addTaskDetails);
        addTaskMenuItem = findViewById(R.id.addTaskTick);
        taskPriorityCheck = findViewById(R.id.taskPriorityCheckBox);

        dbHandler = new DatabaseHandler(getApplicationContext());
        newTask = new ToDoTask();

        addTaskDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar mCalendar = Calendar.getInstance();
                int day = mCalendar.get(Calendar.DAY_OF_MONTH);
                int month = mCalendar.get(Calendar.MONTH);
                int year = mCalendar.get(Calendar.YEAR);

                datePicker = new DatePickerDialog(AddTask.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                addTaskDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year); }
                        }, year, month, day);
                datePicker.show();
            }
        });

        taskPriorityCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    newTask.setTaskPriority("1");
                }else{
                    newTask.setTaskPriority("0");
                }
            }
        });


        //newTask.setTaskPriority("0");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i("ON-OPTIONS-MENU", "Menu will now be inflated");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.overflow_menu2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.addTaskTick){
            AddTaskToList();
        }
        return super.onOptionsItemSelected(item);
    }

    private void AddTaskToList() {
        newTask.setTaskName(addTaskName.getText().toString());
        newTask.setTaskSetDate(addTaskDate.getText().toString());
        newTask.setTaskDetails(addTaskDetails.getText().toString());
        newTask.setTaskStatus("Pending");
        dbHandler.AddTask(newTask);

        Intent intent = new Intent(AddTask.this, MainActivity.class);
        startActivity(intent);
        finish();


    }

}