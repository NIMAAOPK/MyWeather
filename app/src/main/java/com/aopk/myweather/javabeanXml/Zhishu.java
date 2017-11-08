package com.aopk.myweather.javabeanXml;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by asus on 2017/10/25.
 */
@Root(name = "zhishu",strict = false)
public class Zhishu {
    public Zhishu() {
    }
    @Element(name = "name")
    private String name;
    @Element(name = "value")
    private String value;
    @Element(name = "detail")
    private String detail;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
