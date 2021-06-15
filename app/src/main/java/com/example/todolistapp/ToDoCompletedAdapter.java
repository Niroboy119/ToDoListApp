package com.example.todolistapp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ToDoCompletedAdapter extends RecyclerView.Adapter<ToDoCompletedAdapter.ToDoCompletedAdapterViewHolder> {
    private ArrayList<ToDoTask> cList;
    private Context context;

    public ToDoCompletedAdapter(ArrayList<ToDoTask> cList, Context context) {
        this.cList = cList;
        this.context = context;
    }

    @NonNull
    @Override
    public ToDoCompletedAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout, parent, false);
        return new ToDoCompletedAdapter.ToDoCompletedAdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoCompletedAdapterViewHolder holder, int position) {
        ToDoTask task = cList.get(position);

        holder.taskNameView.setText(task.getTaskName());
        holder.taskDateView.setText(task.getTaskSetDate());

        if (task.getTaskStatus().equals("Completed")){
            holder.taskStatusToggle.setEnabled(false);
            holder.taskStatusToggle.setText(task.getTaskStatus());
        }

//        if (task.getTaskPriority() != null && task.getTaskPriority().equals("1")){
//            holder.taskCardView.setCardBackgroundColor(Color.YELLOW);
//        }

    }

    @Override
    public int getItemCount() {
        return cList.size();
    }

    public static class ToDoCompletedAdapterViewHolder extends RecyclerView.ViewHolder{
        private TextView taskNameView;
        private TextView taskDateView;
        private Switch taskStatusToggle;
        private CardView taskCardView;


        public ToDoCompletedAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            taskNameView = itemView.findViewById(R.id.taskName);
            taskDateView = itemView.findViewById(R.id.taskDate);
            taskStatusToggle = itemView.findViewById(R.id.taskStatus);
            taskCardView = itemView.findViewById(R.id.taskCardView);
        }
    }
}
