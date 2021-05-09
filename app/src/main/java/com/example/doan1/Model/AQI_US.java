package com.example.doan1.Model;


import com.example.doan1.R;

public class AQI_US {
    //Phương thức chứa icon, tại mỗi thời điểm AQI khác nhau thì sẽ đưa ra một icon khác nhau
    public static int getAqiIcon(double AQI) {
        if (AQI >= 301) {
            return R.drawable.ic_aqi_hazardoous;
        } else if (AQI >= 201) {
            return R.drawable.ic_aqi_very_unhealthy;
        } else if (AQI >= 151) {
            return R.drawable.ic_aqi_unhealthy;
        } else if (AQI >= 101) {
            return R.drawable.ic_aqi_sensitive;
        } else if (AQI >= 51) {
            return R.drawable.ic_aqi_moderate;
        } else {
            return R.drawable.ic_aqi_good;
        }
    }

    //Phương thức chứa màu của marker. Khi AQI thay đổi thì màu của marker sẽ thay đổi
    public static int getStyleColorMarker(double AQI) {
        if (AQI >= 301) {
            return R.drawable.marker_small_hazardous;
        } else if (AQI >= 201) {
            return R.drawable.marker_small_very_unhealthy;
        } else if (AQI >= 151) {
            return R.drawable.marker_small_unhealthy;
        } else if (AQI >= 101) {
            return R.drawable.marker_small_unhealthy_sensitive;
        } else if (AQI >= 51) {
            return R.drawable.marker_small_moderate;
        } else {
            return R.drawable.marker_small_green;
        }
    }

    //Phương thức chứa trạng thái cảnh báo tại mỗi thời điểm của AQI
    public static int getMessage(double AQI) {
        if (AQI >= 301) {
            return R.string.warning_aqi_hazardous;
        } else if (AQI >= 201) {
            return R.string.warning_aqi_very_unhealthy;
        } else if (AQI >= 151) {
            return R.string.warning_aqi_unhealthy;
        } else if (AQI >= 101) {
            return R.string.warning_aqi_unhealthy_for_sensitive;
        } else if (AQI >= 51) {
            return R.string.warning_aqi_moderate;
        } else {
            return R.string.warning_aqi_good;
        }
    }

    public static double AQI_PM2_5(int Cp) {
        double Ip = 0;
        double BP_Hi = 0;
        double BP_Lo = 0;
        double I_Hi = 0;
        double I_Lo = 0;

        if (Cp > 350.5) {
            BP_Lo = 350.5;
            BP_Hi = 500.4;
            I_Lo = 401;
            I_Hi = 500;
        } else if (Cp > 250.5) {
            BP_Lo = 250.5;
            BP_Hi = 350.4;
            I_Lo = 301;
            I_Hi = 400;
        } else if (Cp > 150.5) {
            BP_Lo = 150.5;
            BP_Hi = 250.4;
            I_Lo = 201;
            I_Hi = 300;
        } else if (Cp > 65.5) {
            BP_Lo = 65.5;
            BP_Hi = 150.4;
            I_Lo = 151;
            I_Hi = 200;
        } else if (Cp > 40.5) {
            BP_Lo = 40.5;
            BP_Hi = 65.4;
            I_Lo = 101;
            I_Hi = 150;
        } else if (Cp > 15.5) {
            BP_Lo = 15.5;
            BP_Hi = 40.4;
            I_Lo = 51;
            I_Hi = 100;
        } else {
            BP_Lo = 0;
            BP_Hi = 15.4;
            I_Lo = 0;
            I_Hi = 50;
        }

        //Công thức tính chỉ số AQI
        Ip = ((I_Hi - I_Lo) * (Cp - BP_Lo)) / (BP_Hi - BP_Lo) + I_Lo;

        return Ip;
    }

    //Phương thức chứa màu của từng cột trong đồ thị
    public static int listColorForChart(int AQI){
        if (AQI >= 301) {
            return R.color.Maroon_Hazardous;
        } else if (AQI >= 201) {
            return R.color.Purple_VeryUnhealthy;
        } else if (AQI >= 151) {
            return R.color.Red_Unhealthy;
        } else if (AQI >= 101) {
            return R.color.Orange_UnhealthyForSensitiveGroup;
        } else if (AQI >= 51) {
            return R.color.Yellow_Moderate;
        } else {
            return R.color.Green_Good;
        }
    }
}
