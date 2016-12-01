package com.thr.mobilemedical.bean;

/**
 * @description 护理巡视实体
 * @date 2015年9月30日 上午10:30:19
 * @version 1.0
 * @Company Buzzlysoft
 * @author Jerry Tan
 * @email thrforever@126.com
 * @remark
 */
public class Nursingpatrol {

	/**
	 * 巡视时间
	 */
	private String time;
	/**
	 * 巡视人
	 */
	private String name;

	public Nursingpatrol() {
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
