package com.blazeey.sentimentanalysis.Model;

/**
 * Created by venki on 30/3/18.
 */

public class XAxisLineChartFormatter {
    private String time;
    private String Date;

    public XAxisLineChartFormatter(String time, String date) {
        this.time = time;
        Date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
