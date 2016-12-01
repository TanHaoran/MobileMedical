package com.thr.mobilemedical.bean;

/**
 * @description 护理记录单实体
 * @date 2015-9-15 11:52:54
 * @version 1.0
 * @Company Buzzlysoft
 * @author Jerry Tan
 * @email thrforever@126.com
 * @remark
 */
public class Nursingrecord {

	/**
	 * 护理记录单id
	 */
	private String NURSINGID;
	/**
	 * 护理记录单名称
	 */
	private String NURSINGNAME;
	/**
	 * 护理记录单文档名称
	 */
	private String NURSINGFILENAME;
	/**
	 * 是否可见
	 */
	private String VISIBLE;

	public Nursingrecord() {
	}

	public String getVISIBLE() {
		return VISIBLE;
	}

	public void setVISIBLE(String vISIBLE) {
		VISIBLE = vISIBLE;
	}

	public String getNURSINGID() {
		return NURSINGID;
	}

	public void setNURSINGID(String nURSINGID) {
		NURSINGID = nURSINGID;
	}

	public String getNURSINGNAME() {
		return NURSINGNAME;
	}

	public void setNURSINGNAME(String nURSINGNAME) {
		NURSINGNAME = nURSINGNAME;
	}

	public String getNURSINGFILENAME() {
		return NURSINGFILENAME;
	}

	public void setNURSINGFILENAME(String nURSINGFILENAME) {
		NURSINGFILENAME = nURSINGFILENAME;
	}

	@Override
	public String toString() {
		return "NursingRecordInfo [NURSINGID=" + NURSINGID + ", NURSINGNAME="
				+ NURSINGNAME + ", NURSINGFILENAME=" + NURSINGFILENAME + "]";
	}

}
