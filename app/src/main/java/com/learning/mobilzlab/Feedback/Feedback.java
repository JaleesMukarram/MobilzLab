package com.learning.mobilzlab.Feedback;

import java.util.Date;
import java.util.Random;

public class Feedback {

    private int mID;
    private String mName;
    private Date mDate;
    private String feedback;

    public Feedback() {
    }

    public Feedback(String mName, String feedback) {
        this.mName = mName;
        this.feedback = feedback;

        this.mID = 100000 + new Random().nextInt(899999);
        this.mDate = new Date();
    }

    public Feedback(int mID, String mName, Date date, String feedback) {
        this.mID = mID;
        this.mName = mName;
        this.mDate = date;
        this.feedback = feedback;
    }

    public int getmID() {
        return mID;
    }

    public void setmID(int mID) {
        this.mID = mID;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public Date getmDate() {
        return mDate;
    }

    public void setmDate(Date mDate) {
        this.mDate = mDate;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
