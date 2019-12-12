package bnrg.app.criminalintent2frag.Activities;

import java.sql.Time;
import java.util.Date;
import java.util.UUID;

public class Crime {

    private String mTitle;
    private UUID mId;
    private Date mDate;
    private Date mTime;
    private boolean mSolved;
    private boolean mRequiresPolice;


    public Crime(){
        mDate = new Date();
        mId = UUID.randomUUID();
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    public boolean isRequiresPolice() {
        return mRequiresPolice;
    }

    public void setRequiresPolice(boolean requiresPolice) {
        mRequiresPolice = requiresPolice;
    }

    public Date getTime() {
        return mTime;
    }

    public void setTime(Date time) {
        mTime = time;
    }
}
