package com.example.todolistapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class CompletedTasks extends AppCompatActivity {
    private ArrayList<ToDoTask> completedTasksList;
    private RecyclerView completedListRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.completed_tasks_recycler_layout);

        ArrayList<ToDoTask> list = getIntent().getParcelableArrayListExtra("completed list");
        completedTasksList = new ArrayList<>();
        completedTasksList.addAll(list);

        completedListRecyclerView = findViewById(R.id.completedRecyclerView);

        final ToDoCompletedAdapter toDoAdapter = new ToDoCompletedAdapter(completedTasksList, CompletedTasks.this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        completedListRecyclerView.setLayoutManager(linearLayoutManager);
        completedListRecyclerView.setAdapter(toDoAdapter);

    }
}