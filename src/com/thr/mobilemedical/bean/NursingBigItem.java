package com.thr.mobilemedical.bean;

import java.util.List;

/**
 * Created by Jerry on 2016/5/18.
 */
public class NursingBigItem {
    private String name;
    private List<NursingBed> bedList;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<NursingBed> getBedList() {
        return bedList;
    }

    public void setBedList(List<NursingBed> bedList) {
        this.bedList = bedList;
    }
}
