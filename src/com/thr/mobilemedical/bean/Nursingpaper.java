package com.thr.mobilemedical.bean;

/**
 * @description 护理清单实体
 * @date 2015年10月13日 下午4:16:37
 * @version 1.0
 * @Company Buzzlysoft
 * @author Jerry Tan
 * @email thrforever@126.com
 * @remark
 */
public class Nursingpaper {

	/**
	 * 床号
	 */
	private String BEDNO;
	/**
	 * 患者姓名
	 */
	private String NAME;
	/**
	 * 住院号
	 */
	private String PATIENTHOSID;
	/**
	 * 总次数
	 */
	private String ZCOUNT;
	/**
	 * 执行次数
	 */
	private String YCOUNT;
	/**
	 * 未行次数
	 */
	private String WCOUNT;
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
	public String getPATIENTHOSID() {
		return PATIENTHOSID;
	}
	public void setPATIENTHOSID(String pATIENTHOSID) {
		PATIENTHOSID = pATIENTHOSID;
	}
	public String getZCOUNT() {
		return ZCOUNT;
	}
	public void setZCOUNT(String zCOUNT) {
		ZCOUNT = zCOUNT;
	}
	public String getYCOUNT() {
		return YCOUNT;
	}
	public void setYCOUNT(String yCOUNT) {
		YCOUNT = yCOUNT;
	}
	public String getWCOUNT() {
		return WCOUNT;
	}
	public void setWCOUNT(String wCOUNT) {
		WCOUNT = wCOUNT;
	}

	
	

}
