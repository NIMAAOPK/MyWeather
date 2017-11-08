package com.aopk.myweather.javabeanXml;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by asus on 2017/10/25.
 */
@Root(name = "day",strict = false)
public class Day {
    public Day() {
    }
    @Element(name = "type")
    private String type;
    @Element(name = "fengxiang")
    private String fengxiang;
    @Element(name = "fengli")
    private String fengli;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFengxiang() {
        return fengxiang;
    }

    public void setFengxiang(String fengxiang) {
        this.fengxiang = fengxiang;
    }

    public String getFengli() {
        return fengli;
    }

    public void setFengli(String fengli) {
        this.fengli = fengli;
    }
}
