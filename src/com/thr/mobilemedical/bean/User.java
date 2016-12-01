package com.thr.mobilemedical.bean;

import java.io.Serializable;

/**
 * @description 操作员实体
 * @date 2015-9-22 13:57:09
 * @version 1.0
 * @Company Buzzlysoft
 * @author Jerry Tan
 * @email thrforever@126.com
 * @remark
 */
public class User  implements Serializable{
	/**
	 * 人员id
	 */
	private String USERID;
	/**
	 * 操作员id
	 */
	private String OPERATORID;
	/**
	 * 用户名
	 */
	private String LOGINNAME;
	/**
	 * 密码
	 */
	private String PASSWORD;
	/**
	 * 姓名
	 */
	private String NAME;
	/**
	 * 科室id
	 */
	private String OFFICEID;
	/**
	 * 科室名称
	 */
	private String OFFICENAME;
	/**
	 * 职务
	 */
	private String JOB;
	/**
	 * 人员类型
	 */
	private String STAFFTYPE;
	/**
	 * 人员角色
	 */
	private String ROLE;

	public User() {
	}

	public String getUSERID() {
		return USERID;
	}

	public void setUSERID(String uSERID) {
		USERID = uSERID;
	}

	public String getOPERATORID() {
		return OPERATORID;
	}

	public void setOPERATORID(String oPERATORID) {
		OPERATORID = oPERATORID;
	}

	public String getLOGINNAME() {
		return LOGINNAME;
	}

	public void setLOGINNAME(String lOGINNAME) {
		LOGINNAME = lOGINNAME;
	}

	public String getPASSWORD() {
		return PASSWORD;
	}

	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}

	public String getNAME() {
		return NAME;
	}

	public void setNAME(String nAME) {
		NAME = nAME;
	}

	public String getOFFICEID() {
		return OFFICEID;
	}

	public void setOFFICEID(String oFFICEID) {
		OFFICEID = oFFICEID;
	}

	public String getOFFICENAME() {
		return OFFICENAME;
	}

	public void setOFFICENAME(String oFFICENAME) {
		OFFICENAME = oFFICENAME;
	}

	public String getJOB() {
		return JOB;
	}

	public void setJOB(String jOB) {
		JOB = jOB;
	}

	public String getSTAFFTYPE() {
		return STAFFTYPE;
	}

	public void setSTAFFTYPE(String sTAFFTYPE) {
		STAFFTYPE = sTAFFTYPE;
	}

	public String getROLE() {
		return ROLE;
	}

	public void setROLE(String rOLE) {
		ROLE = rOLE;
	}

	@Override
	public String toString() {
		return "UserInfo [USERID=" + USERID + ", OPERATORID=" + OPERATORID
				+ ", LOGINNAME=" + LOGINNAME + ", PASSWORD=" + PASSWORD
				+ ", NAME=" + NAME + ", OFFICEID=" + OFFICEID + ", OFFICENAME="
				+ OFFICENAME + ", JOB=" + JOB + ", STAFFTYPE=" + STAFFTYPE
				+ ", ROLE=" + ROLE + "]";
	}

}
