package com.thr.mobilemedical.constant;

import com.thr.mobilemedical.bean.Nursingrecord;
import com.thr.mobilemedical.bean.Patient;

import java.util.List;

/**
 * @author Jerry Tan
 * @version 1.0
 * @description 保存登陆信息的类
 * @date 2015年9月18日 下午2:06:43
 * @Company Buzzlysoft
 * @email thrforever@126.com
 * @remark
 */
public class LoginInfo {
    /**
     * 科室ID
     */
    public static String OFFICE_ID = "";
    /**
     * 科室名称
     */
    public static String OFFICE_NAME = "";

    /**
     * 用户名
     */
    public static String USERNAME = "";

    /**
     * 用户ID
     */
    public static String USER_ID = "";

    /**
     * 登陆密码
     */
    public static String PASSWORD = "";


    /**
     * 病患列表
     */
    public static List<Patient> PATIENT_ALL;
    /**
     * 今日入院病患
     */
    public static List<Patient> PATIENT_IN;
    /**
     * 今日出院病患
     */
    public static List<Patient> PATIENT_OUT;
    /**
     * 明日出院病患
     */
    public static List<Patient> PATIENT_TOMORROW;
    /**
     * 今日手术病患
     */
    public static List<Patient> PATIENT_OPERATION;
    /**
     * 特护病患
     */
    public static List<Patient> PATIENT_LEVELSPECIAL;
    /**
     * 一级护理病患
     */
    public static List<Patient> PATIENT_LEVELONE;
    /**
     * 二级护理病患
     */
    public static List<Patient> PATIENT_LEVELTWO;
    /**
     * 三级护理病患
     */
    public static List<Patient> PATIENT_LEVELTHREE;

    /**
     * 当前病患
     */
    public static Patient patient;
    /**
     * 病患序号
     */
    public static int patientIndex;
    /**
     * 护理记录单
     */
    public static List<Nursingrecord> nursingrecordList;
    /**
     * 时间点集合
     */
    public static List<String> timePoints;
    /**
     * 时间点序号
     */
    public static int timeIndex;

    /**
     * 默认提交护理记录单id
     */
    public static String defaultNursingid;
}
