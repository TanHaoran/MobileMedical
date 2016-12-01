package com.thr.mobilemedical.bean;

import com.lidroid.xutils.db.annotation.Id;

import java.io.Serializable;

/**
 * Created by Jerry on 2016/5/23.
 */
public class Reminder implements Serializable{

    @Id
    private int id;
    private String bed;
    private String name;
    private String level;
    private int age;
    private String sex;
    private String item;
    private String startTime;
    private String endTime;
    private int mode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBed() {
        return bed;
    }

    public void setBed(String bed) {
        this.bed = bed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    @Override
    public String toString() {
        return "Reminder{" +
                "id=" + id +
                ", bed='" + bed + '\'' +
                ", name='" + name + '\'' +
                ", level='" + level + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", item='" + item + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", mode='" + mode + '\'' +
                '}';
    }
}
