package com.thr.mobilemedical.bean;

/**
 * @description 血糖、血氧、血压等实体类
 * @date 2015年10月13日 下午4:16:37
 * @version 1.0
 * @Company Buzzlysoft
 * @author Jerry Tan
 * @email thrforever@126.com
 * @remark
 */
public class Blood {

	/**
	 * 床号
	 */
	private String BEDNO;
	/**
	 * 患者姓名
	 */
	private String PATIENTNAME;
	/**
	 * 住院号
	 */
	private String INHOSID;
	/**
	 * 总次数
	 */
	private String PERFROMTIMES;
	/**
	 * 执行次数
	 */
	private String PERFORMEDTIMES;

	public String getBEDNO() {
		return BEDNO;
	}

	public void setBEDNO(String bEDNO) {
		BEDNO = bEDNO;
	}

	public String getPATIENTNAME() {
		return PATIENTNAME;
	}

	public void setPATIENTNAME(String pATIENTNAME) {
		PATIENTNAME = pATIENTNAME;
	}

	public String getINHOSID() {
		return INHOSID;
	}

	public void setINHOSID(String iNHOSID) {
		INHOSID = iNHOSID;
	}

	public String getPERFROMTIMES() {
		return PERFROMTIMES;
	}

	public void setPERFROMTIMES(String pERFROMTIMES) {
		PERFROMTIMES = pERFROMTIMES;
	}

	public String getPERFORMEDTIMES() {
		return PERFORMEDTIMES;
	}

	public void setPERFORMEDTIMES(String pERFORMEDTIMES) {
		PERFORMEDTIMES = pERFORMEDTIMES;
	}

}
