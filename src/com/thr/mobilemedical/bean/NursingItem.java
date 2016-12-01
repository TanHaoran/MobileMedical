package com.thr.mobilemedical.bean;

/**
 * Created by Jerry on 2016/5/17.
 */
public class NursingItem {
    private String id;
    private String name;
    private String desc;
    private int times;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }
}
