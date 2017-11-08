package com.aopk.myweather.javabeanXml;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by asus on 2017/10/25.
 */
@Root(name = "weather",strict = false)
public class Weather {
    public Weather() {
    }
    @Element(name = "date")
    private String date;
    @Element(name = "high")
    private String high;
    @Element(name = "low")
    private String low;
    @Element(name = "day")
    private Day day;
    @Element(name = "night")
    private Night night;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public Night getNight() {
        return night;
    }

    public void setNight(Night night) {
        this.night = night;
    }
}
