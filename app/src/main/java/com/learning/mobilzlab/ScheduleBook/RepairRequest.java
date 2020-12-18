package com.learning.mobilzlab.ScheduleBook;

import java.io.Serializable;
import java.util.Date;
import java.util.Random;

public class RepairRequest implements Serializable {

    private int mScheduleID;

    private int mUserID;

    private Date sendDate;

    private String mUserName;
    private String mUserNumber;
    private String mUserAddress;
    private String mUserMessage;
    private String mUserMobile;

    public RepairRequest() {

    }

    public RepairRequest(int mUserID, String mUserName, String mUserNumber, String mUserMobile, String mUserMessage) {

        this.mUserID = mUserID;
        this.mUserName = mUserName;
        this.mUserNumber = mUserNumber;
        this.mUserMobile = mUserMobile;
        this.mUserMessage = mUserMessage;

        this.mScheduleID = 100000 + new Random().nextInt(899999);
        this.sendDate = new Date();

    }

    public int getmScheduleID() {
        return mScheduleID;
    }

    public void setmScheduleID(int mScheduleID) {
        this.mScheduleID = mScheduleID;
    }

    public int getmUserID() {
        return mUserID;
    }

    public void setmUserID(int mUserID) {
        this.mUserID = mUserID;
    }

    public String getmUserName() {
        return mUserName;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getmUserNumber() {
        return mUserNumber;
    }

    public void setmUserNumber(String mUserNumber) {
        this.mUserNumber = mUserNumber;
    }

    public String getmUserAddress() {
        return mUserAddress;
    }

    public void setmUserAddress(String mUserAddress) {
        this.mUserAddress = mUserAddress;
    }

    public String getmUserMessage() {
        return mUserMessage;
    }

    public void setmUserMessage(String mUserMessage) {
        this.mUserMessage = mUserMessage;
    }

    public String getmUserMobile() {
        return mUserMobile;
    }

    public void setmUserMobile(String mUserMobile) {
        this.mUserMobile = mUserMobile;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }


}
