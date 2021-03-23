package com.yoshione.fingen.model;

import android.content.ContentValues;
import android.os.Parcel;

import com.yoshione.fingen.dao.PushSendersDAO;
import com.yoshione.fingen.dao.SendersDAO;
import com.yoshione.fingen.interfaces.IAbstractModel;

public class PushSender extends BaseModel implements IAbstractModel {

    private String mName;
    private Boolean mIsActive;
    private String mPackageName;

    public PushSender(long id, String name, Boolean isActive, String packageName) {
        super();
        setID(id);
        mName = name;
        mIsActive = isActive;
        mPackageName = packageName;
    }

    public PushSender(long id) {
        super(id);
    }

    public PushSender() {
        super();
        mName = "";
        mIsActive = true;
        mPackageName = "";
    }

    @Override
    public String toString() {
            return mName;
    }

    @Override
    public String getSearchString() {
        return mName;
    }

    @Override
    public long getID() {
        return super.getID();
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Boolean getActive() {
        return mIsActive;
    }

    public void setActive(Boolean active) {
        mIsActive = active;
    }

    public String getPackageName() {
        return mPackageName;
    }

    public void setPackageName(String packageName) {
        mPackageName = packageName;
    }

    @Override
    public ContentValues getCV() {
        ContentValues values = super.getCV();
        values.put(PushSendersDAO.COL_NAME, getName());
        values.put(PushSendersDAO.COL_PACKAGE_NAME, getPackageName());
        values.put(PushSendersDAO.COL_IS_ACTIVE, getActive() ? 1 : 0);
        return values;
    }

    @Override
    public String getLogTransactionsField() {
        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.mName);
        dest.writeValue(this.mIsActive);
        dest.writeString(this.mPackageName);
    }

    protected PushSender(Parcel in) {
        super(in);
        this.mName = in.readString();
        this.mIsActive = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.mPackageName = in.readString();
    }

    public static final Creator<PushSender> CREATOR = new Creator<PushSender>() {
        @Override
        public PushSender createFromParcel(Parcel source) {
            return new PushSender(source);
        }

        @Override
        public PushSender[] newArray(int size) {
            return new PushSender[size];
        }
    };
}
