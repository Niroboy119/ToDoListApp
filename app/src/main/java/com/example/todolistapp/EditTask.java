package com.example.todolistapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditTask extends AppCompatActivity {
    EditText editTaskName;
    EditText editTaskDate;
    EditText editTaskDetails;
    CheckBox editTaskPriority;
    DatePickerDialog datePicker;
    ToDoTask task;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        editTaskName = findViewById(R.id.addTaskName);
        editTaskDate = findViewById(R.id.addTaskDate);
        editTaskDetails = findViewById(R.id.addTaskDetails);
        editTaskPriority = findViewById(R.id.taskPriorityCheckBox);

        Bundle bundle = getIntent().getExtras();
        task = new ToDoTask();
        task = bundle.getParcelable("task");
        db = new DatabaseHandler(getApplicationContext());

        editTaskName.setText(task.getTaskName());
        editTaskDate.setText(task.getTaskSetDate());
        editTaskDetails.setText(task.getTaskDetails());

        editTaskPriority.setChecked(task.getTaskPriority() != null && task.getTaskPriority().equals("1"));

        editTaskDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar mCalendar = Calendar.getInstance();
                int day = mCalendar.get(Calendar.DAY_OF_MONTH);
                int month = mCalendar.get(Calendar.MONTH);
                int year = mCalendar.get(Calendar.YEAR);

                datePicker = new DatePickerDialog(EditTask.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                editTaskDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year); }
                        }, year, month, day);
                datePicker.show();
            }
        });

        editTaskPriority.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    task.setTaskPriority("1");
                }else{
                    task.setTaskPriority("0");
                }
            }
        });

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
            EditToDoTask();
        }
        return super.onOptionsItemSelected(item);
    }

    private void EditToDoTask() {
        task.setTaskName(editTaskName.getText().toString());
        task.setTaskSetDate(editTaskDate.getText().toString());
        task.setTaskDetails(editTaskDetails.getText().toString());
        task.setTaskStatus("Pending");
        db.EditTaskName(task.getId(),task.getTaskName());
        db.EditTaskDate(task.getId(),task.getTaskSetDate());
        db.EditTaskDetails(task.getId(),task.getTaskDetails());
        db.EditTaskPriority(task.getId(),task.getTaskPriority());

        Intent intent = new Intent(EditTask.this, MainActivity.class);
        startActivity(intent);
        finish();

    }

}