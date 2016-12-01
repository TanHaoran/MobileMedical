package com.thr.mobilemedical.bean;

import java.io.Serializable;

/**
 * @description 病患实体
 * @date 2015-9-15 11:38:29
 * @version 1.0
 * @Company Buzzlysoft
 * @author Jerry Tan
 * @email thrforever@126.com
 * @remark
 */
public class Patient implements Serializable {
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 住院号
	 */
	private String PATIENTHOSID;
	/**
	 * 住院次数
	 * 
	 */
	private String PATIENTINTIMES;
	/**
	 * 床号
	 */
	private String BEDNO;
	/**
	 * 科室id
	 */
	private String OFFICEID;
	/**
	 * 姓名
	 */
	private String NAME;
	/**
	 * 性别
	 */
	private String SEX;
	/**
	 * 生日
	 */
	private String BIRTHDAY;
	/**
	 * 国籍
	 */
	private String NATION;
	/**
	 * 护理等级
	 */
	private String NURSELEVEL;
	/**
	 * 诊断
	 */
	private String DIAGNOSIS;
	/**
	 * 入院日期
	 */
	private String PATIENTINHOSDATE;
	/**
	 * 主管医生
	 */
	private String DOCTOR;
	/**
	 * 描述
	 */
	private String DESCRIPTION;
	/**
	 * 手术时间
	 */
	private String OPERATETIME;
	/**
	 * 手术状态
	 */
	private String OPERATIONSTATE;
	/**
	 * 护士
	 */
	private String NURSE;
	/**
	 * 离院状态
	 */
	private String LEAVEHOSPITALSTATE;
	/**
	 * 生病状态
	 */
	private String ILLNESSSTATE;
	/**
	 * 预交款
	 */
	private String PRECOST;
	/**
	 * 总费用
	 */
	private String TOTALCOST;
	/**
	 * 结余
	 */
	private String BALANCE;
	/**
	 * 医保类型
	 */
	private String MEDICARETYPE;
	/**
	 * 联系电话
	 */
	private String CONTACT;
	/**
	 * 科室名称
	 */
	private String OFFICENAME;

	public Patient() {
	}

	public String getPATIENTHOSID() {
		return PATIENTHOSID;
	}

	public void setPATIENTHOSID(String pATIENTHOSID) {
		PATIENTHOSID = pATIENTHOSID;
	}

	public String getPATIENTINTIMES() {
		return PATIENTINTIMES;
	}

	public void setPATIENTINTIMES(String pATIENTINTIMES) {
		PATIENTINTIMES = pATIENTINTIMES;
	}

	public String getBEDNO() {
		return BEDNO;
	}

	public void setBEDNO(String bEDNO) {
		BEDNO = bEDNO;
	}

	public String getOFFICEID() {
		return OFFICEID;
	}

	public void setOFFICEID(String oFFICEID) {
		OFFICEID = oFFICEID;
	}

	public String getNAME() {
		return NAME;
	}

	public void setNAME(String nAME) {
		NAME = nAME;
	}

	public String getSEX() {
		return SEX;
	}

	public void setSEX(String sEX) {
		SEX = sEX;
	}

	public String getBIRTHDAY() {
		return BIRTHDAY;
	}

	public void setBIRTHDAY(String bIRTHDAY) {
		BIRTHDAY = bIRTHDAY;
	}

	public String getNATION() {
		return NATION;
	}

	public void setNATION(String nATION) {
		NATION = nATION;
	}

	public String getNURSELEVEL() {
		return NURSELEVEL;
	}

	public void setNURSELEVEL(String nURSELEVEL) {
		NURSELEVEL = nURSELEVEL;
	}

	public String getDIAGNOSIS() {
		return DIAGNOSIS;
	}

	public void setDIAGNOSIS(String dIAGNOSIS) {
		DIAGNOSIS = dIAGNOSIS;
	}

	public String getPATIENTINHOSDATE() {
		return PATIENTINHOSDATE;
	}

	public void setPATIENTINHOSDATE(String pATIENTINHOSDATE) {
		PATIENTINHOSDATE = pATIENTINHOSDATE;
	}

	public String getDOCTOR() {
		return DOCTOR;
	}

	public void setDOCTOR(String dOCTOR) {
		DOCTOR = dOCTOR;
	}

	public String getDESCRIPTION() {
		return DESCRIPTION;
	}

	public void setDESCRIPTION(String dESCRIPTION) {
		DESCRIPTION = dESCRIPTION;
	}

	public String getOPERATETIME() {
		return OPERATETIME;
	}

	public void setOPERATETIME(String oPERATETIME) {
		OPERATETIME = oPERATETIME;
	}

	public String getNURSE() {
		return NURSE;
	}

	public void setNURSE(String nURSE) {
		NURSE = nURSE;
	}

	public String getLEAVEHOSPITALSTATE() {
		return LEAVEHOSPITALSTATE;
	}

	public void setLEAVEHOSPITALSTATE(String lEAVEHOSPITALSTATE) {
		LEAVEHOSPITALSTATE = lEAVEHOSPITALSTATE;
	}

	public String getILLNESSSTATE() {
		return ILLNESSSTATE;
	}

	public void setILLNESSSTATE(String iLLNESSSTATE) {
		ILLNESSSTATE = iLLNESSSTATE;
	}

	public String getPRECOST() {
		return PRECOST;
	}

	public void setPRECOST(String pRECOST) {
		PRECOST = pRECOST;
	}

	public String getTOTALCOST() {
		return TOTALCOST;
	}

	public void setTOTALCOST(String tOTALCOST) {
		TOTALCOST = tOTALCOST;
	}

	public String getBALANCE() {
		return BALANCE;
	}

	public void setBALANCE(String bALANCE) {
		BALANCE = bALANCE;
	}

	public String getMEDICARETYPE() {
		return MEDICARETYPE;
	}

	public void setMEDICARETYPE(String mEDICARETYPE) {
		MEDICARETYPE = mEDICARETYPE;
	}

	public String getCONTACT() {
		return CONTACT;
	}

	public void setCONTACT(String cONTACT) {
		CONTACT = cONTACT;
	}

	public String getOFFICENAME() {
		return OFFICENAME;
	}

	public void setOFFICENAME(String oFFICENAME) {
		OFFICENAME = oFFICENAME;
	}

	public String getOPERATIONSTATE() {
		return OPERATIONSTATE;
	}

	public void setOPERATIONSTATE(String oPERATIONSTATE) {
		OPERATIONSTATE = oPERATIONSTATE;
	}

	@Override
	public String toString() {
		return "PatientInfo [PATIENTHOSID=" + PATIENTHOSID
				+ ", PATIENTINTIMES=" + PATIENTINTIMES + ", BEDNO=" + BEDNO
				+ ", OFFICEID=" + OFFICEID + ", NAME=" + NAME + ", SEX=" + SEX
				+ ", BIRTHDAY=" + BIRTHDAY + ", NATION=" + NATION
				+ ", NURSELEVEL=" + NURSELEVEL + ", DIAGNOSIS=" + DIAGNOSIS
				+ ", PATIENTINHOSDATE=" + PATIENTINHOSDATE + ", DOCTOR="
				+ DOCTOR + ", DESCRIPTION=" + DESCRIPTION + ", OPERATETIME="
				+ OPERATETIME + ", OPERATIONSTATE=" + OPERATIONSTATE
				+ ", NURSE=" + NURSE + ", LEAVEHOSPITALSTATE="
				+ LEAVEHOSPITALSTATE + ", ILLNESSSTATE=" + ILLNESSSTATE
				+ ", PRECOST=" + PRECOST + ", TOTALCOST=" + TOTALCOST
				+ ", BALANCE=" + BALANCE + ", MEDICARETYPE=" + MEDICARETYPE
				+ ", CONTACT=" + CONTACT + ", OFFICENAME=" + OFFICENAME + "]";
	}

}
