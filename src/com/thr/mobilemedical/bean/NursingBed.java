package com.thr.mobilemedical.bean;

import java.util.List;

/**
 * Created by Jerry on 2016/5/17.
 */
public class NursingBed {

    private String id;
    private String name;
    private String level;
    private int age;
    private String sex;
    private List<NursingItem> nursingList;

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

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public List<NursingItem> getNursingList() {
        return nursingList;
    }

    public void setNursingList(List<NursingItem> nursingList) {
        this.nursingList = nursingList;
    }
}
