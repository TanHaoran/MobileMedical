package com.thr.mobilemedical.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 护理记录单字段实体
 * @date 2015-9-15 11:52:54
 * @version 1.0
 * @Company Buzzlysoft
 * @author Jerry Tan
 * @email thrforever@126.com
 * @remark
 */
public class NursingrecordFiled {

	/**
	 * 字段id
	 */
	private String ITEMID;
	/**
	 * 字段类型
	 */
	private String COLTYPE;
	/**
	 * 字段名称
	 */
	private String COLNAME;
	/**
	 * 字段英文名
	 */
	private String COLNAMES;
	/**
	 * 字段pc编号
	 */
	private String PCNO;
	/**
	 * 字段pda编号
	 */
	private String PADNO;
	/**
	 * 护理记录单id
	 */
	private String NURSINGID;
	/**
	 * 字段是否显示：0不显示，1显示
	 */
	private String VISIBLE;
	/**
	 * 是否可编辑
	 */
	private String EDITABLE;
	private String ISTOTAL;
	private String ISCALCCOL;
	private String CALCEXPRESSION;
	private String GROUPID;
	private String COLNUMBER;
	/**
	 * 字段pda显示方式：文本、日期、时间、下拉、数字
	 */
	private String COLPROPERTY;
	private String COLLENGTH;
	private String ISCANNULL;
	/**
	 * 单位
	 */
	private String UNIT;
	private String COLOR;
	private String FONT;
	/**
	 * 取值范围
	 */
	private String COLRANGE;
	private String WIDTH;
	/**
	 * 字段内容
	 */
	private String NURSINGCONTENT;
	/**
	 * 默认显示值
	 */
	private String DEFAULTVALUE;
	/**
	 * 实际内容
	 */
	private String content;
	/**
	 * 实际内容2（供血压使用）
	 */
	private String content2;
	/**
	 * 取值最大值
	 */
	private float max;
	/**
	 * 取值最小值
	 */
	private float min;
	/**
	 * 第二取值最大之
	 */
	private float max2;
	/**
	 * 第二取值最小值
	 */
	private float min2;
	/**
	 * 多选选项
	 */
	private List<String> selections;
	/**
	 * 多选序号
	 */
	private int index;

	public List<String> getSelections() {
		return selections;
	}

	public void setSelections(List<String> selections) {
		this.selections = selections;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getContent2() {
		return content2;
	}

	public void setContent2(String content2) {
		this.content2 = content2;
	}

	public float getMax() {
		return max;
	}

	public void setMax(float max) {
		this.max = max;
	}

	public float getMin() {
		return min;
	}

	public void setMin(float min) {
		this.min = min;
	}

	public float getMax2() {
		return max2;
	}

	public void setMax2(float max2) {
		this.max2 = max2;
	}

	public float getMin2() {
		return min2;
	}

	public void setMin2(float min2) {
		this.min2 = min2;
	}

	public String getContent() {
		if (content == null) {
			return "";
		} else {
			return content;
		}
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDEFAULTVALUE() {
		return DEFAULTVALUE;
	}

	public void setDEFAULTVALUE(String dEFAULTVALUE) {
		DEFAULTVALUE = dEFAULTVALUE;
	}

	public String getNURSINGCONTENT() {
		return NURSINGCONTENT;
	}

	public void setNURSINGCONTENT(String nURSINGCONTENT) {
		NURSINGCONTENT = nURSINGCONTENT;
	}

	public NursingrecordFiled() {
	}

	public String getITEMID() {
		return ITEMID;
	}

	public void setITEMID(String iTEMID) {
		ITEMID = iTEMID;
	}

	public String getCOLTYPE() {
		return COLTYPE;
	}

	public void setCOLTYPE(String cOLTYPE) {
		COLTYPE = cOLTYPE;
	}

	public String getCOLNAME() {
		return COLNAME;
	}

	public void setCOLNAME(String cOLNAME) {
		COLNAME = cOLNAME;
	}

	public String getCOLNAMES() {
		return COLNAMES;
	}

	public void setCOLNAMES(String cOLNAMES) {
		COLNAMES = cOLNAMES;
	}

	public String getPCNO() {
		return PCNO;
	}

	public void setPCNO(String pCNO) {
		PCNO = pCNO;
	}

	public String getPADNO() {
		return PADNO;
	}

	public void setPADNO(String pADNO) {
		PADNO = pADNO;
	}

	public String getNURSINGID() {
		return NURSINGID;
	}

	public void setNURSINGID(String nURSINGID) {
		NURSINGID = nURSINGID;
	}

	public String getVISIBLE() {
		return VISIBLE;
	}

	public void setVISIBLE(String vISIBLE) {
		VISIBLE = vISIBLE;
	}

	public String getISTOTAL() {
		return ISTOTAL;
	}

	public void setISTOTAL(String iSTOTAL) {
		ISTOTAL = iSTOTAL;
	}

	public String getISCALCCOL() {
		return ISCALCCOL;
	}

	public void setISCALCCOL(String iSCALCCOL) {
		ISCALCCOL = iSCALCCOL;
	}

	public String getEDITABLE() {
		return EDITABLE;
	}

	public void setEDITABLE(String eDITABLE) {
		EDITABLE = eDITABLE;
	}

	public String getCALCEXPRESSION() {
		return CALCEXPRESSION;
	}

	public void setCALCEXPRESSION(String cALCEXPRESSION) {
		CALCEXPRESSION = cALCEXPRESSION;
	}

	public String getGROUPID() {
		return GROUPID;
	}

	public void setGROUPID(String gROUPID) {
		GROUPID = gROUPID;
	}

	public String getCOLNUMBER() {
		return COLNUMBER;
	}

	public void setCOLNUMBER(String cOLNUMBER) {
		COLNUMBER = cOLNUMBER;
	}

	public String getCOLPROPERTY() {
		return COLPROPERTY;
	}

	public void setCOLPROPERTY(String cOLPROPERTY) {
		COLPROPERTY = cOLPROPERTY;
	}

	public String getCOLLENGTH() {
		return COLLENGTH;
	}

	public void setCOLLENGTH(String cOLLENGTH) {
		COLLENGTH = cOLLENGTH;
	}

	public String getISCANNULL() {
		return ISCANNULL;
	}

	public void setISCANNULL(String iSCANNULL) {
		ISCANNULL = iSCANNULL;
	}

	public String getUNIT() {
		return UNIT;
	}

	public void setUNIT(String uNIT) {
		UNIT = uNIT;
	}

	public String getCOLOR() {
		return COLOR;
	}

	public void setCOLOR(String cOLOR) {
		COLOR = cOLOR;
	}

	public String getFONT() {
		return FONT;
	}

	public void setFONT(String fONT) {
		FONT = fONT;
	}

	public String getCOLRANGE() {
		return COLRANGE;
	}

	public void setCOLRANGE(String cOLRANGE) {
		COLRANGE = cOLRANGE;
	}

	/**
	 * 初始化范围设置
	 */
	public void initRange() {
		String[] ranges = COLRANGE.split("\\|");
		// 针对两种不同的情况，血压有4个取值范围
		if (ranges.length == 2) {
			min = Integer.parseInt(ranges[0]);
			max = Integer.parseInt(ranges[1]);
		} else if (ranges.length == 4) {
			min = Integer.parseInt(ranges[0]);
			max = Integer.parseInt(ranges[1]);
			min2 = Integer.parseInt(ranges[2]);
			max2 = Integer.parseInt(ranges[3]);
		} else if (ranges.length == 1) {
			min = 0;
			max = 99999;
		}
	}

	/**
	 * 初始化多选选择框
	 */
	public void initSelections() {
		String[] slcts = NURSINGCONTENT.split("\\|");
		selections = new ArrayList<String>();
		for (String slct : slcts) {
			selections.add(slct);
		}
	}

	public String getWIDTH() {
		return WIDTH;
	}

	public void setWIDTH(String wIDTH) {
		WIDTH = wIDTH;
	}

	@Override
	public String toString() {
		return "NursingRecordItem [ITEMID=" + ITEMID + ", COLTYPE=" + COLTYPE
				+ ", COLNAME=" + COLNAME + ", COLNAMES=" + COLNAMES + ", PCNO="
				+ PCNO + ", PADNO=" + PADNO + ", NURSINGID=" + NURSINGID
				+ ", VISIBLE=" + VISIBLE + ", EDITABLE=" + EDITABLE
				+ ", ISTOTAL=" + ISTOTAL + ", ISCALCCOL=" + ISCALCCOL
				+ ", CALCEXPRESSION=" + CALCEXPRESSION + ", GROUPID=" + GROUPID
				+ ", COLNUMBER=" + COLNUMBER + ", COLPROPERTY=" + COLPROPERTY
				+ ", COLLENGTH=" + COLLENGTH + ", ISCANNULL=" + ISCANNULL
				+ ", UNIT=" + UNIT + ", COLOR=" + COLOR + ", FONT=" + FONT
				+ ", COLRANGE=" + COLRANGE + ", WIDTH=" + WIDTH
				+ ", NURSINGCONTENT=" + NURSINGCONTENT + "]";
	}

}
