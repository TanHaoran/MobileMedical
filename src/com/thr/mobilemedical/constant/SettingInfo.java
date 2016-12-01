package com.thr.mobilemedical.constant;

/**
 * @description 保存设置信息的类
 * @date 2015年9月18日 下午2:06:43
 * @version 1.0
 * @Company Buzzlysoft
 * @author Jerry Tan
 * @email thrforever@126.com
 * @remark
 */
public class SettingInfo {
	/**
	 * 服务地址
	 */
	public static String SERVICE = "";
	/**
	 * NSIS服务
	 */
	public static String NSIS = "";

	/**
	 * 是否是demo演示版
	 */
	public static boolean IS_DEMO = false;

	/**
	 * T8过滤器
	 */
	public static final String T8_FILTER = "com.android.scanner.result";
	/**
	 * 联想（黄色）过滤器
	 */
	public static final String LENOVO_FILTER = "action.barcode.reader.value";
	/**
	 * 摩托罗拉过滤器
	 */
	public static final String MOTOROLA_FILTER = "com.motorolasolutions.emdk.sample.dwdemosample.RECVR";
	/**
	 * 优博讯过滤器
	 */
	public static final String UROVO_FILTER = "urovo.rcv.message";
	/**
	 * EMH过滤器
	 */
	public static final String EMH_FILTER = "com.ge.action.barscan";
	/**
	 * 过滤器
	 */
	public static String SCAN_FILTER = "";

	/**
	 * T8接受字串
	 */
	public static final String T8_STRING = "result";
	/**
	 * 联想（黄色）接受字串
	 */
	public static final String LENOVO_STRING = "borcode_value";
	/**
	 * 摩托罗拉接受字串
	 */
	public static final String MOTOROLA_STRING = "DataWedgeAPI_KEY_ENUMERATEDSCANNERLIST";
	/**
	 * 优博讯接受字串
	 */
	public static final String UROVO_STRING = "";
	/**
	 * EMH接受字串
	 */
	public static final String EMH_STRING = "value";
	/**
	 * 接受字串
	 */
	public static String RECEIVE_STRING = "";

}
