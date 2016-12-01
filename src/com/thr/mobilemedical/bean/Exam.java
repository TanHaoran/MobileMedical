package com.thr.mobilemedical.bean;

/**
 * @description 检验报告实体
 * @date 2015年9月25日 下午1:56:52
 * @version 1.0
 * @Company Buzzlysoft
 * @author Jerry Tan
 * @email thrforever@126.com
 * @remark
 */
public class Exam {

	/**
	 * 检验号
	 */
	private String ID;
	/**
	 * 检验名称
	 */
	private String NAME;
	/**
	 * 检验时间
	 */
	private String TIME;

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getNAME() {
		return NAME;
	}

	public void setNAME(String nAME) {
		NAME = nAME;
	}

	public String getTIME() {
		return TIME;
	}

	public void setTIME(String tIME) {
		TIME = tIME;
	}
}
