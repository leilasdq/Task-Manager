package com.example.homeworrrrrk9.Model;

import com.example.homeworrrrrk9.State;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class TaskManager implements Serializable {
    private long taskId;
    private UUID mUUID;
    private String mTitle;
    private String mDetail;
    private Date mDate;
    private State mState;
    private long userId;

    public TaskManager() {
        this(UUID.randomUUID());
        mDate = Calendar.getInstance().getTime();
    }

    public TaskManager(UUID UUID) {
        mUUID = UUID;
    }

    public UUID getUUID() {
        return mUUID;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDetail() {
        return mDetail;
    }

    public void setDetail(String detail) {
        mDetail = detail;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public State getState() {
        return mState;
    }

    public void setState(State state) {
        mState = state;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }
}
