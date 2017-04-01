package com.example.tstv.remindmeabout.model;

import com.example.tstv.remindmeabout.R;

import java.util.Date;

/**
 * Created by tstv on 30.03.2017.
 */

public class ModelTask implements Item {

    public static final int PRIORITY_LOW = 0;
    public static final int PRIORITY_NORMAL = 1;
    public static final int PRIORITY_HIGH = 2;

    public static final String[] PRIORITY_LEVELS = {"Low Priority", "Normal Priority", "High Priority"};

    public static final int STATUS_OVERDUE = 0;
    public static final int STATUS_CURRENT = 1;
    public static final int STATUS_DONE = 2;

    private String mTitle;
    private long mDate;
    private int priority;
    private int status;
    private long timeStamp;

    public ModelTask(){
        this.status = -1;
        this.timeStamp = new Date().getTime();
    }

    public ModelTask(String title, long data,int priority,int status,long timeStamp){
        this.mTitle = title;
        this.mDate = data;
        this.priority = priority;
        this.status = status;
        this.timeStamp = timeStamp;

    }

    public int getPriorityColor(){
        switch (getPriority()){
            case PRIORITY_HIGH:
                if (getStatus() == STATUS_CURRENT || getStatus() == STATUS_OVERDUE){
                    return R.color.priority_high;
                }else {
                    return R.color.priority_high_selected;
                }
            case PRIORITY_NORMAL:
                if (getStatus() == STATUS_CURRENT || getStatus() == STATUS_OVERDUE){
                    return R.color.priority_normal;
                }else {
                    return R.color.priority_normal_selected;
                }
            case PRIORITY_LOW:
                if (getDate() == STATUS_CURRENT || getStatus() == STATUS_OVERDUE){
                    return  R.color.priority_low;
                }else {
                    return R.color.priority_low_selected;
                }


            default:return 0;
        }
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public long getDate() {
        return mDate;
    }

    public void setDate(long date) {
        mDate = date;
    }




    @Override
    public boolean isTask() {
        return true;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
