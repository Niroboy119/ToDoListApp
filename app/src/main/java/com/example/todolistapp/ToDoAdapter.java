package com.example.todolistapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.switchmaterial.SwitchMaterial;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.TodoViewHolder> {
    private Context context;
    private static ArrayList<ToDoTask> tList;
    private static ArrayList<ToDoTask> tasksCompletedList = new ArrayList<>();
    private static DatabaseHandler db;
    SimpleDateFormat sdf;
    int actualPosition = 0;

    public ToDoAdapter(Context context, ArrayList<ToDoTask> toDoTaskList) {
        this.context = context;
        tList = toDoTaskList;
        db = new DatabaseHandler(context);
    }

//    public ToDoAdapter() {
//        // empty adapter constructor
//    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout, parent, false);
        return new TodoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoAdapter.TodoViewHolder holder, int position) {
        ToDoTask task = tList.get(position);
        holder.taskNameView.setText(task.getTaskName());
        holder.taskDateView.setText(task.getTaskSetDate());

        //holder.taskStatusToggle.setText(task.getTaskStatus());
        holder.taskStatusToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    task.setTaskStatus("Completed");
                    actualPosition = holder.getAdapterPosition();
                    tasksCompletedList.add(task);
                    db.DeleteTask(task.getId());
                    tList.remove(actualPosition);
                    notifyItemRemoved(actualPosition);
                    notifyItemRangeChanged(actualPosition, tList.size());
                }else{
                    task.setTaskStatus("Pending");
                }
                holder.taskStatusToggle.setText(task.getTaskStatus());

            }
        });

        if (task.getTaskPriority() != null && task.getTaskPriority().equals("1")){
            holder.taskCardView.setCardBackgroundColor(Color.YELLOW);
        }

        Date currentDate = Calendar.getInstance().getTime();

        sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date taskDueDate = sdf.parse(task.getTaskSetDate());
            if (currentDate.compareTo(taskDueDate) > 0){
                holder.taskCardView.setCardBackgroundColor(Color.RED);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return tList.size();
    }

    public class TodoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView taskNameView;
        private TextView taskDateView;
        private Switch taskStatusToggle;
        private CardView taskCardView;


        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);

            taskNameView = itemView.findViewById(R.id.taskName);
            taskDateView = itemView.findViewById(R.id.taskDate);
            taskStatusToggle = itemView.findViewById(R.id.taskStatus);
            taskCardView = itemView.findViewById(R.id.taskCardView);

            itemView.setOnClickListener(this);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());

                    builder.setTitle("Confirm");
                    builder.setMessage("Are you sure?");

                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing but close the dialog
                            ToDoTask toDoTask;
                            toDoTask = tList.get(getAdapterPosition());
                            db.DeleteTask(toDoTask.getId());
                            tList.remove(toDoTask);
                            notifyItemRemoved(actualPosition);
                            notifyItemRangeChanged(actualPosition, tList.size());
                            dialog.dismiss();
                        }
                    });

                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();

                    return true;
                }
            });
        }


        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, EditTask.class);
            Bundle todoTaskData = new Bundle();
            todoTaskData.putParcelable("task", tList.get(getAdapterPosition()));
            intent.putExtras(todoTaskData);
            context.startActivity(intent);
        }
    }

    public static ArrayList<ToDoTask> getTasksCompletedList(){
        return tasksCompletedList;
    }
}

