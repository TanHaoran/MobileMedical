package com.thr.mobilemedical.bean;

/**
 * @description 体温单实体
 * @date 2015年9月24日 上午11:10:55
 * @version 1.0
 * @Company Buzzlysoft
 * @author Jerry Tan
 * @email thrforever@126.com
 * @remark
 */
public class Temperature {

	/**
	 * 床号
	 */
	private String BEDNO;
	/**
	 * 姓名
	 */
	private String NAME;
	/**
	 * 体温1
	 */
	private String HEAT1;
	/**
	 * 体温2
	 */
	private String HEAT2;
	/**
	 * 体温3
	 */
	private String HEAT3;
	/**
	 * 体温4
	 */
	private String HEAT4;
	/**
	 * 体温5
	 */
	private String HEAT5;
	/**
	 * 体温6
	 */
	private String HEAT6;

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

	public String getHEAT1() {
		return HEAT1;
	}

	public void setHEAT1(String hEAT1) {
		HEAT1 = hEAT1;
	}

	public String getHEAT2() {
		return HEAT2;
	}

	public void setHEAT2(String hEAT2) {
		HEAT2 = hEAT2;
	}

	public String getHEAT3() {
		return HEAT3;
	}

	public void setHEAT3(String hEAT3) {
		HEAT3 = hEAT3;
	}

	public String getHEAT4() {
		return HEAT4;
	}

	public void setHEAT4(String hEAT4) {
		HEAT4 = hEAT4;
	}

	public String getHEAT5() {
		return HEAT5;
	}

	public void setHEAT5(String hEAT5) {
		HEAT5 = hEAT5;
	}

	public String getHEAT6() {
		return HEAT6;
	}

	public void setHEAT6(String hEAT6) {
		HEAT6 = hEAT6;
	}

	@Override
	public String toString() {
		return "Temperature [NAME=" + NAME + ", HEAT1=" + HEAT1 + ", HEAT2="
				+ HEAT2 + ", HEAT3=" + HEAT3 + ", HEAT4=" + HEAT4 + ", HEAT5="
				+ HEAT5 + ", HEAT6=" + HEAT6 + "]";
	}

}
