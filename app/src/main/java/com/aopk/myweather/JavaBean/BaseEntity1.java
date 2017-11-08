package com.aopk.myweather.JavaBean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/25.
 */

public class BaseEntity1 implements Serializable {
    private Data data;
    private String status;
    private String desc;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return data.toString()+"status:"+status+"desc:"+desc;
    }
}
