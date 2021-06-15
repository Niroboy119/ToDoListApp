package com.example.todolistapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private RecyclerView taskRecyclerView;
    private FloatingActionButton fab;
    private DatabaseHandler dbHandler;
    private ArrayList<ToDoTask> toDoList;
    private ArrayList<ToDoTask> overDueTasksList;
    private ArrayList<ToDoTask> completedList;
    private ToDoAdapter toDoAdapter;
    private SimpleDateFormat sdf;
    //ToDoTask overDueTask;
    Date taskDueDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_recycler_layout);

        taskRecyclerView = findViewById(R.id.recyclerView);
        fab = findViewById(R.id.addTaskBtn);
        dbHandler = new DatabaseHandler(this);

        //populateDB();
        populateToDoList();

        // sort tasks in ascending order according to due dates (earliest due tasks first)
        // order the tasks by date in ascending order
        Collections.sort(toDoList, (toDo1, toDo2) -> toDo1.getTaskSetDate().compareTo(toDo2.getTaskSetDate()));
        Collections.reverse(toDoList);

        Date currentDate = Calendar.getInstance().getTime();
        sdf = new SimpleDateFormat("dd/MM/yyyy");
        overDueTasksList = new ArrayList<>();

        for (int i=0; i < toDoList.size(); i++){
            try {
                taskDueDate = sdf.parse(toDoList.get(i).getTaskSetDate());
                if (currentDate.compareTo(taskDueDate) > 0){
                    overDueTasksList.add(toDoList.get(i));
                    toDoList.remove(toDoList.get(i));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        //Collections.sort(overDueTasksList, (toDo1, toDo2) -> toDo1.getTaskSetDate().compareTo(toDo2.getTaskSetDate()));
        toDoList.addAll(overDueTasksList);
        overDueTasksList.clear();

        toDoAdapter = new ToDoAdapter(MainActivity.this, toDoList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        taskRecyclerView.setLayoutManager(linearLayoutManager);
        taskRecyclerView.setAdapter(toDoAdapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open new activity to add task
                Intent intent = new Intent(MainActivity.this, AddTask.class);
                startActivity(intent);
                finish();
            }
        });
    }


    private void populateToDoList() {
        if (toDoList != null)
            toDoList.clear();
        toDoList = dbHandler.GetAllTasks();
    }

//    private void populateDB() {
//        dbHandler.AddTask(new ToDoTask("Do Homework", "Complete it", "0", "20-05-2021", "Pending"));
//        dbHandler.AddTask(new ToDoTask("Do Laundry", "Do it", "0", "22-05-2021", "Pending"));
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i("ON-OPTIONS-MENU", "Menu will now be inflated");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.overflow_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.completedTasksOpt){
            OpenCompletedTasksList();
        }
        return super.onOptionsItemSelected(item);
    }

    private void OpenCompletedTasksList() {
        completedList = ToDoAdapter.getTasksCompletedList();
        Intent intent = new Intent(MainActivity.this, CompletedTasks.class);
        intent.putParcelableArrayListExtra("completed list", completedList);
        startActivity(intent);
    }
}