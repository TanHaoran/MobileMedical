package com.thr.mobilemedical.bean;

/**
 * @description NewOrder.java
 * @date 2015年10月21日 上午10:24:46
 * @version 1.0
 * @Company Buzzlysoft
 * @author Jerry Tan
 * @email thrforever@126.com
 * @remark
 */
public class NewOrder {

	/**
	 * 住院号
	 */
	private String INHOSID;
	/**
	 * 住院次数
	 */
	private String INHOSTIMES;
	/**
	 * 姓名
	 */
	private String PATIENTNAME;
	/**
	 * 床号
	 */
	private String BEDNO;
	/**
	 * 开嘱时间
	 */
	private String START_TIME;
	/**
	 * 总数
	 */
	private String COUNT;
	/**
	 * 医嘱状态（1：正常）
	 */
	private String STATE;

	public String getINHOSID() {
		return INHOSID;
	}

	public void setINHOSID(String iNHOSID) {
		INHOSID = iNHOSID;
	}

	public String getINHOSTIMES() {
		return INHOSTIMES;
	}

	public void setINHOSTIMES(String iNHOSTIMES) {
		INHOSTIMES = iNHOSTIMES;
	}

	public String getPATIENTNAME() {
		return PATIENTNAME;
	}

	public void setPATIENTNAME(String pATIENTNAME) {
		PATIENTNAME = pATIENTNAME;
	}

	public String getBEDNO() {
		return BEDNO;
	}

	public void setBEDNO(String bEDNO) {
		BEDNO = bEDNO;
	}

	public String getSTART_TIME() {
		return START_TIME;
	}

	public void setSTART_TIME(String sTART_TIME) {
		START_TIME = sTART_TIME;
	}

	public String getCOUNT() {
		return COUNT;
	}

	public void setCOUNT(String cOUNT) {
		COUNT = cOUNT;
	}

	public String getSTATE() {
		return STATE;
	}

	public void setSTATE(String sTATE) {
		STATE = sTATE;
	}

}
