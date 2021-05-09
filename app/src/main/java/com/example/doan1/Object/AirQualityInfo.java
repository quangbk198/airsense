package com.example.doan1.Object;

import android.os.Parcel;
import android.os.Parcelable;

public class AirQualityInfo implements Parcelable {
    private String ID = "";
    private int PM1;
    private int PM10;
    private int PM2_5;
    private double AQI;
    private int mTimeStamp;
    private int humidity;
    private int temprature;

    //Hàm tạo này sẽ được gọi khi tại receiving activity - nơi mà chúng ta muốn nhận giá trị
    protected AirQualityInfo(Parcel in) {
        ID = in.readString();
        PM1 = in.readInt();
        PM10 = in.readInt();
        PM2_5 = in.readInt();
        AQI = in.readDouble();
        mTimeStamp = in.readInt();
        humidity = in.readInt();
        temprature = in.readInt();
    }

    //Đối tượng CREATOR được dùng để "tạo lại" đối tượng khi cần
    public static final Creator<AirQualityInfo> CREATOR = new Creator<AirQualityInfo>() {
        @Override
        public AirQualityInfo createFromParcel(Parcel in) {
            return new AirQualityInfo(in);
        }

        @Override
        public AirQualityInfo[] newArray(int size) {
            return new AirQualityInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public int getPM1() {
        return PM1;
    }

    public void setPM1(int PM1) {
        this.PM1 = PM1;
    }

    public int getPM10() {
        return PM10;
    }

    public void setPM10(int PM10) {
        this.PM10 = PM10;
    }

    public int getPM2_5() {
        return PM2_5;
    }

    public void setPM2_5(int PM2_5) {
        this.PM2_5 = PM2_5;
    }

    public double getAQI() {
        return AQI;
    }

    public void setAQI(double AQI) {
        this.AQI = AQI;
    }

    public int getmTimeStamp() {
        return mTimeStamp;
    }

    public void setmTimeStamp(int mTimeStamp) {
        this.mTimeStamp = mTimeStamp;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getTemprature() {
        return temprature;
    }

    public void setTemprature(int temprature) {
        this.temprature = temprature;
    }

    public static Creator<AirQualityInfo> getCREATOR() {
        return CREATOR;
    }

    public AirQualityInfo(String ID, int PM1, int PM10, int PM2_5, double AQI, int mTimeStamp, int humidity, int temprature) {
        this.ID = ID;
        this.PM1 = PM1;
        this.PM10 = PM10;
        this.PM2_5 = PM2_5;
        this.AQI = AQI;
        this.mTimeStamp = mTimeStamp;
        this.humidity = humidity;
        this.temprature = temprature;
    }

    // Phương thức này dùng để thêm tất cả các thuộc tính vào parcel cần chuyển :))
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ID);
        dest.writeInt(PM1);
        dest.writeInt(PM10);
        dest.writeInt(PM2_5);
        dest.writeDouble(AQI);
        dest.writeInt(mTimeStamp);
        dest.writeInt(humidity);
        dest.writeInt(temprature);
    }

    @Override
    public String toString() {
        return "Record{" +
                "mId='" + ID + '\'' +
                ", mPM1=" + PM1 +
                ", mPM10=" + PM10 +
                ", mPM2_5=" + PM2_5 +
                ", mTimeStamp=" + mTimeStamp +
                ", humidity=" + humidity +
                ", temprature=" + temprature +
                ", AQI=" + AQI +
                '}';
    }
}