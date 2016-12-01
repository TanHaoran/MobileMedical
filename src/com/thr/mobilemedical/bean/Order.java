package com.thr.mobilemedical.bean;

/**
 * @description 医嘱实体
 * @date 2015年9月11日 下午4:20:45
 * @version 1.0
 * @Company Buzzlysoft
 * @author Jerry Tan
 * @email thrforever@126.com
 * @remark
 */
public class Order {
	/**
	 * 住院号
	 */
	private String PATIENTHOSID;
	/**
	 * 住院次数
	 */
	private String PATIENTINTIMES;
	/**
	 * 父医嘱
	 */
	private String PARENTORDER;
	/**
	 * 子医嘱
	 */
	private String CHILDORDER;
	/**
	 * 医嘱类型
	 */
	private String ORDERCLASSIFY;
	/**
	 * 用药类型
	 */
	private String ORDERTYPE;
	/**
	 * 开始时间
	 */
	private String STARTTIME;
	/**
	 * 医嘱内容
	 */
	private String ORDERCONTENT;
	/**
	 * 执行频率
	 */
	private String FREQUENCY;
	/**
	 * 用药途径
	 */
	private String MEDICINEWAY;
	/**
	 * 停止时间
	 */
	private String STOPTIME;
	/**
	 * 医生
	 */
	private String DOCTOR;
	/**
	 * 容量
	 */
	private String PERTIME;
	/**
	 * 用药剂量
	 */
	private String DOSETYPE;
	/**
	 * 提交时间
	 */
	private String SUBTIME;
	/**
	 * 备注
	 */
	private String NOTE;
	/**
	 * 计划执行时间
	 */
	private String PLANEXECTIME;
	/**
	 * 执行次数
	 */
	private String EXECTIMES;
	/**
	 * 执行时间
	 */
	private String EXECTIME;
	/**
	 * 计划执行id
	 */
	private String EXECPERSONID;
	/**
	 * 执行类型
	 */
	private String EXECTYPE;
	/**
	 * 计划执行日期
	 */
	private String PLANEXECDAY;
	/**
	 * 执行人
	 */
	private String execUser;

	public Order() {
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

	public String getPARENTORDER() {
		return PARENTORDER;
	}

	public void setPARENTORDER(String pARENTORDER) {
		PARENTORDER = pARENTORDER;
	}

	public String getCHILDORDER() {
		return CHILDORDER;
	}

	public void setCHILDORDER(String cHILDORDER) {
		CHILDORDER = cHILDORDER;
	}

	public String getORDERCLASSIFY() {
		return ORDERCLASSIFY;
	}

	public void setORDERCLASSIFY(String oRDERCLASSIFY) {
		ORDERCLASSIFY = oRDERCLASSIFY;
	}

	public String getORDERTYPE() {
		return ORDERTYPE;
	}

	public void setORDERTYPE(String oRDERTYPE) {
		ORDERTYPE = oRDERTYPE;
	}

	public String getSTARTTIME() {
		return STARTTIME;
	}

	public void setSTARTTIME(String sTARTTIME) {
		STARTTIME = sTARTTIME;
	}

	public String getORDERCONTENT() {
		return ORDERCONTENT;
	}

	public void setORDERCONTENT(String oRDERCONTENT) {
		ORDERCONTENT = oRDERCONTENT;
	}

	public String getFREQUENCY() {
		return FREQUENCY;
	}

	public void setFREQUENCY(String fREQUENCY) {
		FREQUENCY = fREQUENCY;
	}

	public String getMEDICINEWAY() {
		return MEDICINEWAY;
	}

	public void setMEDICINEWAY(String mEDICINEWAY) {
		MEDICINEWAY = mEDICINEWAY;
	}

	public String getSTOPTIME() {
		return STOPTIME;
	}

	public void setSTOPTIME(String sTOPTIME) {
		STOPTIME = sTOPTIME;
	}

	public String getDOCTOR() {
		return DOCTOR;
	}

	public void setDOCTOR(String dOCTOR) {
		DOCTOR = dOCTOR;
	}

	public String getDOSETYPE() {
		return DOSETYPE;
	}

	public void setDOSETYPE(String dOSETYPE) {
		DOSETYPE = dOSETYPE;
	}

	public String getSUBTIME() {
		return SUBTIME;
	}

	public void setSUBTIME(String sUBTIME) {
		SUBTIME = sUBTIME;
	}

	public String getNOTE() {
		return NOTE;
	}

	public void setNOTE(String nOTE) {
		NOTE = nOTE;
	}

	public String getPLANEXECTIME() {
		return PLANEXECTIME;
	}

	public void setPLANEXECTIME(String pLANEXECTIME) {
		PLANEXECTIME = pLANEXECTIME;
	}

	public String getEXECTIMES() {
		return EXECTIMES;
	}

	public void setEXECTIMES(String eXECTIMES) {
		EXECTIMES = eXECTIMES;
	}

	public String getEXECTIME() {
		return EXECTIME;
	}

	public void setEXECTIME(String eXECTIME) {
		EXECTIME = eXECTIME;
	}

	public String getPERTIME() {
		return PERTIME;
	}

	public void setPERTIME(String pERTIME) {
		PERTIME = pERTIME;
	}

	public String getEXECPERSONID() {
		return EXECPERSONID;
	}

	public void setEXECPERSONID(String eXECPERSONID) {
		EXECPERSONID = eXECPERSONID;
	}

	public String getEXECTYPE() {
		return EXECTYPE;
	}

	public void setEXECTYPE(String eXECTYPE) {
		EXECTYPE = eXECTYPE;
	}

	public String getPLANEXECDAY() {
		return PLANEXECDAY;
	}

	public void setPLANEXECDAY(String pLANEXECDAY) {
		PLANEXECDAY = pLANEXECDAY;
	}

	public String getExecUser() {
		return execUser;
	}

	public void setExecUser(String execUser) {
		this.execUser = execUser;
	}

	@Override
	public String toString() {
		return "Order [PATIENTHOSID=" + PATIENTHOSID + ", PATIENTINTIMES="
				+ PATIENTINTIMES + ", PARENTORDER=" + PARENTORDER
				+ ", CHILDORDER=" + CHILDORDER + ", ORDERCLASSIFY="
				+ ORDERCLASSIFY + ", ORDERTYPE=" + ORDERTYPE + ", STARTTIME="
				+ STARTTIME + ", ORDERCONTENT=" + ORDERCONTENT + ", FREQUENCY="
				+ FREQUENCY + ", MEDICINEWAY=" + MEDICINEWAY + ", STOPTIME="
				+ STOPTIME + ", DOCTOR=" + DOCTOR + ", PERTIME=" + PERTIME
				+ ", DOSETYPE=" + DOSETYPE + ", SUBTIME=" + SUBTIME + ", NOTE="
				+ NOTE + ", PLANEXECTIME=" + PLANEXECTIME + ", EXECTIMES="
				+ EXECTIMES + ", EXECTIME=" + EXECTIME + ", EXECPERSONID="
				+ EXECPERSONID + ", EXECTYPE=" + EXECTYPE + ", PLANEXECDAY="
				+ PLANEXECDAY + ", execUser=" + execUser + "]";
	}

}
