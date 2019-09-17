package com.example.homeworrrrrk9.Model;

import com.example.homeworrrrrk9.State;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class TaskManager {
    private UUID mUUID;
    private String mTitle;
    private String mDetail;
    private Date mDate;
    private State mState;

    public TaskManager() {
        mUUID = UUID.randomUUID();
        mDate = Calendar.getInstance().getTime();
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
}
