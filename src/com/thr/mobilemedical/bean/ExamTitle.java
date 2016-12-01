package com.thr.mobilemedical.bean;

/**
 * @description 检验报告标题实体
 * @date 2015年10月19日 上午10:38:17
 * @version 1.0
 * @Company Buzzlysoft
 * @author Jerry Tan
 * @email thrforever@126.com
 * @remark
 */
public class ExamTitle {

	/**
	 * 检验时间
	 */
	private String time;
	/**
	 * 报告数量
	 */
	private int num;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

}
