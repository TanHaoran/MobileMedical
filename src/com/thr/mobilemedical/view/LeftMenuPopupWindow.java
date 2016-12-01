package com.thr.mobilemedical.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.thr.mobilemedical.R;
import com.thr.mobilemedical.activity.DropexecuteActivity;
import com.thr.mobilemedical.activity.LoginActivity;
import com.thr.mobilemedical.activity.MainActivity;
import com.thr.mobilemedical.activity.MyApplication;
import com.thr.mobilemedical.activity.NursinglistActivity;
import com.thr.mobilemedical.activity.NursingpatrolActivity;
import com.thr.mobilemedical.activity.NursingrecordActivity;
import com.thr.mobilemedical.activity.PatientinfoActivity;
import com.thr.mobilemedical.activity.ReminderSettingActivity;
import com.thr.mobilemedical.activity.SettingAcitivity;
import com.thr.mobilemedical.constant.ActivityName;
import com.thr.mobilemedical.constant.LoginInfo;
import com.thr.mobilemedical.constant.SettingInfo;
import com.thr.mobilemedical.utils.ActivityUtil;
import com.thr.mobilemedical.utils.ScreenUtils;
import com.thr.mobilemedical.view.MyDialog.SureClickListener;

/**
 * @author Jerry Tan
 * @version 1.0
 * @description 左侧菜单弹出框
 * @date 2015-9-24 13:45:29
 * @Company Buzzlysoft
 * @email thrforever@126.com
 * @remark
 */
public class LeftMenuPopupWindow extends PopupWindow implements OnClickListener {

    private Context mContext;

    /**
     * 主View
     */
    private View mMenu;
    /**
     * 姓名TextView
     */
    private TextView mName;
    /**
     * 科室TextView
     */
    private TextView mOffice;

    private LinearLayout mMenuHomepage; // 主页菜单
    private LinearLayout mMenuPatientinfo; // 病患信息菜单
    private LinearLayout mMenuNursingrecord; // 护理录入菜单
    private LinearLayout mMenuOrderexecute; // 医嘱执行菜单
    private LinearLayout mMenuOrderlist; // 护理清单菜单
    private LinearLayout mMenuNursingPatrol; // 护理巡视菜单
    private LinearLayout mMenuReminderSetting; // 用药提醒菜单


    private LinearLayout mMenuSetting; // 设置菜单
    private LinearLayout mMenuQuit; // 退出菜单

    private ImageView mImgHomepage;
    private ImageView mImgPatientinfo;
    private ImageView mImgNursingrecord;
    private ImageView mImgOrderexecute;
    private ImageView mImgOrderlist;
    private ImageView mImgNursingpatrol;
    private ImageView mImgReminderSetting;

    private TextView mTextHomepage;
    private TextView mTextPatientinfo;
    private TextView mTextNursingrecord;
    private TextView mTextOrderexecute;
    private TextView mTextOrderList;
    private TextView mTextNursingpatrol;
    private TextView mTextReminderSetting;

    private int mStatusHeight; // 状态栏高度
    private int mScreenHeight; // 屏幕的高度

    public LeftMenuPopupWindow(Context context) {
        super();
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenu = inflater.inflate(R.layout.view_menu, null);
        getScreenSize();
        // 设置SelectPicPopupWindow的View
        setContentView(mMenu);
        // 设置SelectPicPopupWindow弹出窗体的宽
        setWidth(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        setHeight(mScreenHeight - mStatusHeight);
        // 设置SelectPicPopupWindow弹出窗体可点击
        setFocusable(true);
        // 设置出入动画
        setAnimationStyle(R.style.leftmenu_style);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        setBackgroundDrawable(dw);
        initView();
    }

    /**
     * 取得屏幕中所需的各项高度
     */
    private void getScreenSize() {
        mStatusHeight = ScreenUtils.getStatusHeight(mContext);
        mScreenHeight = ScreenUtils.getScreenHeight(mContext);
    }

    /**
     * 初始化显示和监听器
     */
    private void initView() {

        mName = (TextView) mMenu.findViewById(R.id.tv_name);
        mOffice = (TextView) mMenu.findViewById(R.id.tv_office);

        mMenuHomepage = (LinearLayout) mMenu.findViewById(R.id.ll_homepage);
        mMenuPatientinfo = (LinearLayout) mMenu
                .findViewById(R.id.ll_patientinfo);
        mMenuNursingrecord = (LinearLayout) mMenu
                .findViewById(R.id.ll_nursingrecord);
        mMenuOrderexecute = (LinearLayout) mMenu
                .findViewById(R.id.ll_orderexecute);
        mMenuOrderlist = (LinearLayout) mMenu
                .findViewById(R.id.ll_order_list);
        mMenuNursingPatrol = (LinearLayout) mMenu
                .findViewById(R.id.ll_nursing_patrol);
        mMenuReminderSetting = (LinearLayout) mMenu
                .findViewById(R.id.ll_reminder_setting);
        mMenuSetting = (LinearLayout) mMenu.findViewById(R.id.ll_setting);
        mMenuQuit = (LinearLayout) mMenu.findViewById(R.id.ll_quit);

        mImgHomepage = (ImageView) mMenu.findViewById(R.id.iv_homepage);
        mImgPatientinfo = (ImageView) mMenu.findViewById(R.id.iv_patientinfo);
        mImgNursingrecord = (ImageView) mMenu
                .findViewById(R.id.iv_nursingrecord);
        mImgOrderexecute = (ImageView) mMenu.findViewById(R.id.iv_orderexecute);
        mImgOrderlist = (ImageView) mMenu.findViewById(R.id.iv_order_list);
        mImgNursingpatrol = (ImageView) mMenu.findViewById(R.id.iv_nursing_patrol);
        mImgReminderSetting = (ImageView) mMenu.findViewById(R.id.iv_reminder_setting);

        mTextHomepage = (TextView) mMenu.findViewById(R.id.tv_homepage);
        mTextPatientinfo = (TextView) mMenu.findViewById(R.id.tv_patientinfo);
        mTextNursingrecord = (TextView) mMenu.findViewById(R.id.tv_nursingrecord);
        mTextOrderList = (TextView) mMenu.findViewById(R.id.tv_order_list);
        mTextOrderexecute = (TextView) mMenu.findViewById(R.id.tv_orderexecute);
        mTextNursingpatrol = (TextView) mMenu.findViewById(R.id.tv_nursing_patrol);
        mTextReminderSetting = (TextView) mMenu.findViewById(R.id.tv_reminder_setting);

        if (SettingInfo.IS_DEMO) {
            mName.setText("陈冰");
            mOffice.setText("心理科");
        } else {
            mName.setText(LoginInfo.USERNAME);
            mOffice.setText(LoginInfo.OFFICE_NAME);
        }


        String activityName = ActivityUtil.getCurrentActivity(mContext);
        // 主页
        if (activityName.endsWith(ActivityName.MAIN_ACTIVITY)) {
            mImgHomepage.setImageResource(R.drawable.left_menu_sy_n);
            makeTextHighlight(mTextHomepage);
        }
        // 病患详细
        else if (activityName.endsWith(ActivityName.PATIENTINFO_ACTIVITY)) {
            mImgPatientinfo.setImageResource(R.drawable.left_menu_bhxx_n);
            makeTextHighlight(mTextPatientinfo);
        }
        // 护理录入
        else if (activityName.endsWith(ActivityName.NURSINGRECORD_ACTIVITY)) {
            mImgNursingrecord.setImageResource(R.drawable.left_menu_hllr_n);
            makeTextHighlight(mTextNursingrecord);
        }
        // 静滴执行
        else if (activityName.endsWith(ActivityName.ORDEREXECUTE_ACTIVITY)) {
            mImgOrderexecute.setImageResource(R.drawable.icon_jdzx_b);
            makeTextHighlight(mTextOrderexecute);
        }
        // 医嘱清单
        else if (activityName.endsWith(ActivityName.NURSINGLIST_ACTIVITY)) {
            mImgOrderlist.setImageResource(R.drawable.left_menu_yzzx_n);
            makeTextHighlight(mTextOrderList);
        }
        // 护理巡视
        else if (activityName.endsWith(ActivityName.NURSINGPATROL_ACTIVITY)) {
            mImgNursingpatrol.setImageResource(R.drawable.icon_hlxs_b);
            makeTextHighlight(mTextNursingpatrol);
        }
        // 提醒设置
        else if (activityName.endsWith(ActivityName.REMINDER_SETTING_ACTIVITY)) {
            mImgReminderSetting.setImageResource(R.drawable.icon_txsz_b);
            makeTextHighlight(mTextReminderSetting);
        }


        mMenuHomepage.setOnClickListener(this);
        mMenuPatientinfo.setOnClickListener(this);
        mMenuNursingrecord.setOnClickListener(this);
        mMenuOrderexecute.setOnClickListener(this);
        mMenuOrderlist.setOnClickListener(this);
        mMenuNursingPatrol.setOnClickListener(this);
        mMenuReminderSetting.setOnClickListener(this);
        mMenuSetting.setOnClickListener(this);
        mMenuQuit.setOnClickListener(this);

        mMenu.setOnTouchListener(new OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int width = mMenu.findViewById(R.id.rl_main).getRight();
                int x = (int) event.getX();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (x > width) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }

    /**
     * 使当前标签高亮
     *
     * @param textView
     */
    private void makeTextHighlight(TextView textView) {
        textView.setTextColor(mContext.getResources().getColor(
                R.color.slidingmenu_text_select));
    }

    @Override
    public void onClick(View v) {
        // 首先获取当前Activity类名，如果一致，只需关闭菜单即可
        String activityName = ActivityUtil.getCurrentActivity(mContext);
        Intent intent = new Intent();
        switch (v.getId()) {
            // 主页
            case R.id.ll_homepage:
                if (activityName.endsWith(ActivityName.MAIN_ACTIVITY)) {
                    dismiss();
                    return;
                } else {
                    intent.setClass(mContext, MainActivity.class);
                }
                break;
            // 病患信息
            case R.id.ll_patientinfo:
                if (activityName.endsWith(ActivityName.PATIENTINFO_ACTIVITY)) {
                    dismiss();
                    return;
                } else {
                    intent.setClass(mContext, PatientinfoActivity.class);
                }
                break;
            // 护理录入
            case R.id.ll_nursingrecord:
                if (activityName.endsWith(ActivityName.NURSINGRECORD_ACTIVITY)) {
                    dismiss();
                    return;
                } else {
                    intent.setClass(mContext, NursingrecordActivity.class);
                }
                break;
            // 医嘱执行
            case R.id.ll_orderexecute:
                if (activityName.endsWith(ActivityName.ORDEREXECUTE_ACTIVITY)) {
                    dismiss();
                    return;
                } else {
                    intent.setClass(mContext, DropexecuteActivity.class);
                }
                break;
            // 医嘱清单
            case R.id.ll_order_list:
                if (activityName.endsWith(ActivityName.NURSINGLIST_ACTIVITY)) {
                    dismiss();
                    return;
                } else {
                    intent.setClass(mContext, NursinglistActivity.class);
                }
                break;
            // 护理巡视
            case R.id.ll_nursing_patrol:
                if (activityName.endsWith(ActivityName.NURSINGPATROL_ACTIVITY)) {
                    dismiss();
                    return;
                } else {
                    intent.setClass(mContext, NursingpatrolActivity.class);
                }
                break;
            // 提醒设置
            case R.id.ll_reminder_setting:
                if (activityName.endsWith(ActivityName.REMINDER_SETTING_ACTIVITY)) {
                    dismiss();
                    return;
                } else {
                    intent.setClass(mContext, ReminderSettingActivity.class);
                }
                break;
            // 设置
            case R.id.ll_setting:
                dismiss();
                intent.setClass(mContext, SettingAcitivity.class);
                break;
            // 退出
            case R.id.ll_quit:
                showExitDialog();
                return;
        }
        mContext.startActivity(intent);
    }

    /**
     * 显示退出提示框
     */
    private void showExitDialog() {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.view_logout_text, null);

        final MyDialog dialog = new MyDialog(mContext, view,
                new SureClickListener() {

                    @Override
                    public void onSureClick() {
                        MyApplication.getInstance().finishAllActivity();
                        // 退出后跳转到登陆页面
                        Intent intent = new Intent(mContext,
                                LoginActivity.class);
                        mContext.startActivity(intent);
                    }
                });
        dialog.show();
    }
}
