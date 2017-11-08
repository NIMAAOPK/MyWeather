package com.aopk.myweather.javabeanXml;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by asus on 2017/10/25.
 */
@Root(name = "night_1",strict = false)
public class Night_1 {
    public Night_1() {
    }
    @Element(name = "type_1")
    private String type_1;
    @Element(name = "fx_1")
    private String fx_1;
    @Element(name = "fl_1")
    private String fl_1;

    public String getType_1() {
        return type_1;
    }

    public void setType_1(String type_1) {
        this.type_1 = type_1;
    }

    public String getFx_1() {
        return fx_1;
    }

    public void setFx_1(String fx_1) {
        this.fx_1 = fx_1;
    }

    public String getFl_1() {
        return fl_1;
    }

    public void setFl_1(String fl_1) {
        this.fl_1 = fl_1;
    }
}
