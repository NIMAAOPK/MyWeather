package com.aopk.myweather.javabeanXml;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

/**
 * Created by asus on 2017/10/25.
 */
@Root(name = "resp", strict = false)
public class BaseEntity {

    public BaseEntity() {
    }

    @Element(name = "city")
    private String city;
    @Element(name = "updatetime")
    private String updatetime;
    @Element(name = "wendu")
    private String wendu;
    @Element(name = "fengli")
    private String fengli;
    @Element(name = "shidu")
    private String shidu;
    @Element(name = "fengxiang")
    private String fengxiang;
    @Element(name = "sunrise_1")
    private String sunrise_1;
    @Element(name = "sunset_1")
    private String sunset_1;
    @Element(name = "yesterday")
    private Yesterday yesterday;
    @ElementList(name = "forecast",entry = "weather")
    private ArrayList<Weather> forecast;
    @ElementList(name = "zhishus",entry = "zhishu")
    private ArrayList<Zhishu> zhishus;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getWendu() {
        return wendu;
    }

    public void setWendu(String wendu) {
        this.wendu = wendu;
    }

    public String getFengli() {
        return fengli;
    }

    public void setFengli(String fengli) {
        this.fengli = fengli;
    }

    public String getShidu() {
        return shidu;
    }

    public void setShidu(String shidu) {
        this.shidu = shidu;
    }

    public String getFengxiang() {
        return fengxiang;
    }

    public void setFengxiang(String fengxiang) {
        this.fengxiang = fengxiang;
    }

    public String getSunrise_1() {
        return sunrise_1;
    }

    public void setSunrise_1(String sunrise_1) {
        this.sunrise_1 = sunrise_1;
    }

    public String getSunset_1() {
        return sunset_1;
    }

    public void setSunset_1(String sunset_1) {
        this.sunset_1 = sunset_1;
    }

    public Yesterday getYesterday() {
        return yesterday;
    }

    public void setYesterday(Yesterday yesterday) {
        this.yesterday = yesterday;
    }

    public ArrayList<Weather> getForecast() {
        return forecast;
    }

    public void setForecast(ArrayList<Weather> forecast) {
        this.forecast = forecast;
    }

    public ArrayList<Zhishu> getZhishus() {
        return zhishus;
    }

    public void setZhishus(ArrayList<Zhishu> zhishus) {
        this.zhishus = zhishus;
    }

    @Override
    public String toString() {
        return "city: "+city+"time: "+updatetime;
    }
}
