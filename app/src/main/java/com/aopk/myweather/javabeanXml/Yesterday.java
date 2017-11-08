package com.aopk.myweather.javabeanXml;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by asus on 2017/10/25.
 */
@Root(name = "yesterday",strict = false)
public class Yesterday {
    public Yesterday() {
    }
    @Element(name = "date_1")
    private String date_1;
    @Element(name = "high_1")
    private String high_1;
    @Element(name = "low_1")
    private String low_1;
    @Element(name = "day_1")
    private Day_1 day_1;
    @Element(name = "night_1")
    private Night_1 night_1;

    public String getDate_1() {
        return date_1;
    }

    public void setDate_1(String date_1) {
        this.date_1 = date_1;
    }

    public String getHigh_1() {
        return high_1;
    }

    public void setHigh_1(String high_1) {
        this.high_1 = high_1;
    }

    public String getLow_1() {
        return low_1;
    }

    public void setLow_1(String low_1) {
        this.low_1 = low_1;
    }

    public Day_1 getDay_1() {
        return day_1;
    }

    public void setDay_1(Day_1 day_1) {
        this.day_1 = day_1;
    }

    public Night_1 getNight_1() {
        return night_1;
    }

    public void setNight_1(Night_1 night_1) {
        this.night_1 = night_1;
    }
}
