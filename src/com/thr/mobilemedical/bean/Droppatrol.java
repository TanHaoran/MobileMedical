package com.thr.mobilemedical.bean;

/**
 * @description 静滴巡视实体
 * @date 2015年9月30日 上午10:30:19
 * @version 1.0
 * @Company Buzzlysoft
 * @author Jerry Tan
 * @email thrforever@126.com
 * @remark
 */
public class Droppatrol {
	/**
	 * 开始时间
	 */
	private String time;
	/**
	 * 医嘱内容
	 */
	private String content;
	/**
	 * 滴速
	 */
	private String speed;
	/**
	 * 巡视时间
	 */
	private String patrolTime;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public String getPatrolTime() {
		return patrolTime;
	}

	public void setPatrolTime(String patrolTime) {
		this.patrolTime = patrolTime;
	}

}
