package com.thr.mobilemedical.constant;

/**
 * @description 保存方法名的类
 * @date 2015年9月14日 下午3:00:24
 * @version 1.0
 * @Company Buzzlysoft
 * @author Jerry Tan
 * @email thrforever@126.com
 * @remark
 */
public class Method {
	/**
	 * 读取科室列表
	 */
	public static final String GET_REGIST_OFFICE = "GetRegistOffice";
	/**
	 * 读取科室内人员列表
	 */
	public static final String GET_OPERATOR_BY_OFFICEID = "GetOperatorByOfficeID";
	/**
	 * 根据用户名密码登陆
	 */
	public static final String LOGIN_NURSE = "LoginNurse";
	/**
	 * 读取分类病患数目
	 */
	public static final String PATIENT_INFO_ARCHIVES_GROUP_COUNT = "PatientInfoArchivesGroupCount";
	/**
	 * 读取病患信息
	 */
	public static final String PATIENT_INFO_BY_OFFICEID = "PatientInfoByOfficeID";
	/**
	 * 读取病患的医嘱
	 */
	public static final String DOCTOR_ORDER_BY_PATIENTHOSID = "DoctorOrderByPatientHosId";
	/**
	 * 读取护理记录个数
	 */
	public static final String GET_NURSING_RECORD = "GetNursingRecord";
	/**
	 * 获取所有病患体温
	 */
	public static final String GET_PATIENT_TEMPERATURE = "GetTENDDETAILByOfficeID";
	/**
	 * 读取护理记录单结构
	 */
	public static final String GET_NURSING_FILED = "GetNursingFiled";
	/**
	 * 护理记录单信息
	 */
	public static final String GET_NURSING_LIST = "GetNursingList";
	/**
	 * 传一条记录
	 */
	public static final String SET_NURSING_LIST = "SetNursingList";
	/**
	 * 读取出入量信息
	 */
	public static final String GET_NURSING_MAIN = "GetNursingMain";
	/**
	 * 回传一条出入量记录
	 */
	public static final String SET_NURSING_MAIN = "SetNursingMain";
	/**
	 * 读取体温异常的病患
	 */
	public static final String GET_NURSING_LIST_YCTW_BY_OFFICEID = "GetNursingListYCTWByOfficeID";
	/**
	 * 读取血氧数据
	 */
	public static final String QUERY_NURSE_ITEM_MOBILE_BY_SATURATION = "QueryNurseItemMobileBySaturation";
	/**
	 * 读取血糖数据
	 */
	public static final String QUERY_NURSE_ITEM_MOBILE_BY_BLOODSUGAR = "QueryNurseItemMobileByBloodsugar";
	/**
	 * 读取血压数据
	 */
	public static final String QUERY_NURSE_ITEM_MOBILE_BY_BLOODPRESSURE = "QueryNurseItemMobileByBloodPressure";
	/**
	 * 读取护理清单
	 */
	public static final String QUERY_NURSE_ITEM_MOBILE_BY_STATISTICAL = "QueryNurseItemMobileByStatistical";
	/**
	 * 读取检验报告列表
	 */
	public static final String GET_LIS = "GetLis";
	/**
	 * 读取检验报告单条详细
	 */
	public static final String GET_LIS_DETAILED = "GetLisDetailed";
	/**
	 * 获取详细医嘱
	 */
	public static final String DOCTOR_ORDER_DETAILS_BY_PATIENTHOSID = "DoctorOrderDetailsByPatientHosId";
	/**
	 * 通过父医嘱号获取医嘱
	 */
	public static final String DOCTOR_ORDER_DETAILS_BY_PATIENTHOSID_PARENTORDER = "DoctorOrderDetailsByPatientHosIdParentOrder";
	/**
	 * 执行医嘱
	 */
	public static final String SET_DOCTOR_ORDER_DETAILS = "SetDoctorOrderDetails";
	/**
	 * 查询一定时间内新开的医嘱
	 */
	public static final String QUERY_MEDORDER_MOBILE_BY_OFFICE = "QueryMedorderMobileByOffice";

	/**
	 * 查询病患经静滴医嘱（包括已执行和未执行）
	 */
	public static final String GET_PATIENT_INFO_DOCTOR_ORDER_BY_PATIENTHOSID = "GetPatientInfoDoctorOrderByPatientHosId";

	/**
	 * 查询所有病患静滴执行（包括已执行和未执行）
	 *
	 */
	public static final String GET_HLQD_BY_OFFICEID = "GetHLQDByOfficeID";

	/**
	 * 根据住院号、住院次数、医嘱类型、父医嘱号查询医嘱
	 */
	public static final String DOCTOR_ORDER_BY_PATIENTHOSID_PARENTORDER = "DoctorOrderByPatientHosIdParentOrder";
	/**
	 * 长期医嘱
	 */
	public static String ORDER_LONG = "1";
	/**
	 * 临时医嘱
	 */
	public static String ORDER_TEMP = "0";
	/**
	 * 静滴
	 */
	public static String DROP = "1";
	/**
	 * 口服
	 */
	public static String ORALLY = "2";
	/**
	 * 注射
	 */
	public static String INJECTION = "3";
	/**
	 * 处置
	 */
	public static String HANDLE = "4";
	/**
	 * 皮肤
	 */
	public static String SKIN = "5";

	public static final String NURSING_SPECIAL_CH = "特级护理";
	public static final String NURSING_ONE_CH = "一级护理";
	public static final String NURSING_TWO_CH = "二级护理";
	public static final String NURSING_THREE_CH = "三级护理";

	public static final String NURSING_SPECIAL_EN = "特级护理";
	public static final String NURSING_ONE_EN = "Ⅰ级护理";
	public static final String NURSING_TWO_EN = "Ⅱ级护理";
	public static final String NURSING_THREE_EN = "Ⅲ级护理";

	/**
	 * 判断是否是一级护理
	 * 
	 * @param level
	 * @return
	 */
	public static boolean isNursingOne(String level) {
		if (NURSING_ONE_CH.equals(level) || NURSING_ONE_EN.equals(level)
				|| "Ⅰ级护理".equals(level)) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否是二级护理
	 * 
	 * @param level
	 * @return
	 */
	public static boolean isNursingTwo(String level) {
		if (NURSING_TWO_CH.equals(level) || NURSING_TWO_EN.equals(level)) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否是三级护理
	 * 
	 * @param level
	 * @return
	 */
	public static boolean isNursingThree(String level) {
		if (NURSING_THREE_CH.equals(level) || NURSING_THREE_EN.equals(level)) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否是特级护理
	 * 
	 * @param level
	 * @return
	 */
	public static boolean isNursingSpecial(String level) {
		if (NURSING_SPECIAL_CH.equals(level)
				|| NURSING_SPECIAL_EN.equals(level)) {
			return true;
		}
		return false;
	}
}
