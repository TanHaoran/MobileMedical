package com.thr.mobilemedical.bean;

/**
 * @description 消息中心----异常体温
 * @date 2015年10月13日 上午10:35:58
 * @version 1.0
 * @Company Buzzlysoft
 * @author Jerry Tan
 * @email thrforever@126.com
 * @remark
 */
public class UnusualTemper {
	/**
	 * 住院号
	 */
	private String PATIENTHOSID;
	/**
	 * 床号
	 */
	private String BEDNO;
	/**
	 * 姓名
	 */
	private String NAME;
	/**
	 * 体温
	 */
	private String HEAT;

	public String getPATIENTHOSID() {
		return PATIENTHOSID;
	}

	public void setPATIENTHOSID(String pATIENTHOSID) {
		PATIENTHOSID = pATIENTHOSID;
	}

	public String getBEDNO() {
		return BEDNO;
	}

	public void setBEDNO(String bEDNO) {
		BEDNO = bEDNO;
	}

	public String getNAME() {
		return NAME;
	}

	public void setNAME(String nAME) {
		NAME = nAME;
	}

	public String getHEAT() {
		return HEAT;
	}

	public void setHEAT(String hEAT) {
		HEAT = hEAT;
	}

}
