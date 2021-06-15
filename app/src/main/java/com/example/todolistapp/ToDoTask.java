package com.example.todolistapp;

import android.os.Parcel;
import android.os.Parcelable;

public class ToDoTask implements Parcelable {
    private int id;
    private String taskName;
    private String taskDetails;
    private String taskPriority;
    private String taskSetDate;
    private String taskStatus;

    public ToDoTask() {
        // empty constructor
    }

    public ToDoTask(String taskName, String taskDetails, String taskPriority, String taskSetDate, String taskStatus) {
        this.taskName = taskName;
        this.taskDetails = taskDetails;
        this.taskPriority = taskPriority;
        this.taskSetDate = taskSetDate;
        this.taskStatus = taskStatus;
    }

    public ToDoTask(int id, String taskName, String taskDetails, String taskPriority, String taskSetDate, String taskStatus) {
        this.id = id;
        this.taskName = taskName;
        this.taskDetails = taskDetails;
        this.taskPriority = taskPriority;
        this.taskSetDate = taskSetDate;
        this.taskStatus = taskStatus;
    }

    protected ToDoTask(Parcel in) {
        id = in.readInt();
        taskName = in.readString();
        taskDetails = in.readString();
        taskPriority = in.readString();
        taskSetDate = in.readString();
        taskStatus = in.readString();
    }

    public static final Creator<ToDoTask> CREATOR = new Creator<ToDoTask>() {
        @Override
        public ToDoTask createFromParcel(Parcel in) {
            return new ToDoTask(in);
        }

        @Override
        public ToDoTask[] newArray(int size) {
            return new ToDoTask[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskSetDate() {
        return taskSetDate;
    }

    public void setTaskSetDate(String taskSetDate) {
        this.taskSetDate = taskSetDate;
    }

    public String getTaskDetails() {
        return taskDetails;
    }

    public void setTaskDetails(String taskDetails) {
        this.taskDetails = taskDetails;
    }

    public String getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(String taskPriority) {
        this.taskPriority = taskPriority;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(taskName);
        dest.writeString(taskDetails);
        dest.writeString(taskPriority);
        dest.writeString(taskSetDate);
        dest.writeString(taskStatus);
    }
}
