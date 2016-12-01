package com.thr.mobilemedical.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.device.ScanManager;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnTouch;
import com.thr.mobilemedical.R;
import com.thr.mobilemedical.bean.Office;
import com.thr.mobilemedical.bean.User;
import com.thr.mobilemedical.constant.LoginInfo;
import com.thr.mobilemedical.constant.Method;
import com.thr.mobilemedical.constant.SettingInfo;
import com.thr.mobilemedical.utils.GsonUtil;
import com.thr.mobilemedical.utils.HttpGetUtil;
import com.thr.mobilemedical.utils.KeyBoardUtils;
import com.thr.mobilemedical.utils.L;
import com.thr.mobilemedical.view.ConfigDialog;
import com.thr.mobilemedical.view.MyAlertDialog;
import com.thr.mobilemedical.view.MyProgressDialog;
import com.thr.mobilemedical.view.OfficeDialog;
import com.thr.mobilemedical.view.WheelView;

import java.util.ArrayList;
import java.util.List;

/**
 * 登陆界面
 *
 * @author Jerry Tan
 * @version 1.0
 * @description LoginActivity.java
 * @date 2015年9月9日 下午1:45:37
 * @Company Buzzlysoft
 * @email thrforever@126.com
 * @remark
 */
@ContentView(R.layout.activity_login)
public class LoginActivity extends Activity {

    @ViewInject(R.id.et_username)
    private EditText mEditUsername;
    @ViewInject(R.id.et_password)
    private EditText mEditPassword;

    private MyProgressDialog mDialog;

    // 科室集合
    private List<Office> mOffices;
    // 人员列表集合
    private List<User> mUsers;
    // 用户名集合
    private List<String> mUsernames;

    // 人员选择弹出框
    private PopupWindow mPopWindow;

    private BroadcastReceiver scanReceiver;
    private SoundPool soundpool;
    private int soundid;

    // 选择人员序号
    int mIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getInstance().finishAllActivity();
        MyApplication.getInstance().addActivity(this);
        // 初始化界面
        initView();
        // 读取服务地址信息
        loadConfig();
        // 读取设备信息
        loadDeviceInfo();
    }


    /**
     * 初始化界面
     */
    private void initView() {
        // 隐藏软键盘的弹出
        mEditUsername.setInputType(InputType.TYPE_NULL);
        mPopWindow = initPopupWindow();
        mDialog = new MyProgressDialog(this);
    }


    /**
     * 初始化底布弹出框
     */
    @SuppressWarnings("deprecation")
    private PopupWindow initPopupWindow() {
        // 初始化弹出窗口
        LayoutInflater mLayoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View contentView = mLayoutInflater
                .inflate(R.layout.pop_selection, null);
        mPopWindow = new PopupWindow(contentView, LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        mPopWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopWindow.setFocusable(true);
        // 设置动画效果
        mPopWindow.setAnimationStyle(R.style.pop_changehead);

        return mPopWindow;
    }

    /**
     * 读取服务配置信息，读取上次用户登陆信息，并显示在界面上
     */
    private void loadConfig() {
        SharedPreferences sp = getSharedPreferences(ConfigDialog.CONFIG,
                Activity.MODE_PRIVATE);
        // 读取服务地址配置信息
        String service = sp.getString("service",
                "http://192.168.252.21:4011/MMIPService/");
        String nsis = sp.getString("nsis",
                "http://192.168.252.21:4010/NSIS_WebAPI/");

        SettingInfo.SERVICE = service;
        SettingInfo.NSIS = nsis;

        // 读取上次登陆科室相关信息
        String officeId = sp.getString("office_id", "");
        String officeName = sp.getString("office_name", "");

        LoginInfo.OFFICE_ID = officeId;
        LoginInfo.OFFICE_NAME = officeName;

        // 读取上次登陆用户相关信息
        String userId = sp.getString("user_id", "");
        String username = sp.getString("username", "");
        String password = sp.getString("password", "");

        LoginInfo.USER_ID = userId;
        LoginInfo.USERNAME = username;
        LoginInfo.PASSWORD = password;


        // 将读取到的用户名和密码显示在界面上
        mEditUsername.setText(username);
        mEditPassword.setText(password);
    }


    /**
     * 点击移动护理，修改服务地址配置
     *
     * @param v
     */
    @OnClick(R.id.tv_logo)
    public void onConfig(View v) {
        ConfigDialog dialog = new ConfigDialog(this);
        dialog.show();
    }


    /**
     * 点击logo图标，读取科室信息
     *
     * @param v
     */
    @OnClick(R.id.iv_logo)
    public void onOffice(View v) {
        loadOffice();
    }


    /**
     * 读取可用科室
     */
    private void loadOffice() {
        String url = SettingInfo.SERVICE + Method.GET_REGIST_OFFICE;
        HttpGetUtil httpGet = new HttpGetUtil(this) {

            @Override
            public void success(String json) {
                L.i("返回值------" + json);
                mOffices = GsonUtil.getOfficeList(json);
                if (GsonUtil.hasData(mOffices)) {
                    showOfficeList();
                } else {
                    new MyAlertDialog(LoginActivity.this, "科室列表为空").show();
                }
            }
        };
        httpGet.doGet(url, mDialog, this, "科室列表");
    }


    /**
     * 显示科室列表
     */
    protected void showOfficeList() {
        OfficeDialog dialog = new OfficeDialog(this);
        dialog.show();
        dialog.setItems(mOffices);
    }


    @OnTouch(R.id.et_username)
    public boolean onUsernameTouch(View v, MotionEvent event) {
        mEditUsername.setBackgroundResource(R.drawable.edit_login_bg_focuse);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (LoginInfo.OFFICE_ID == null) {
                MyAlertDialog alertDialog = new MyAlertDialog(
                        LoginActivity.this, "请先选择科室");
                alertDialog.show();
            } else {
                KeyBoardUtils.closeKeybord(mEditPassword,
                        LoginActivity.this);
                loadAndShowUser(LoginInfo.OFFICE_ID);
            }
            return true;
        }
        return false;
    }


    /**
     * 读取科室内的人员
     */
    private void loadAndShowUser(String officeId) {
        String url = SettingInfo.SERVICE + Method.GET_OPERATOR_BY_OFFICEID
                + "?OfficeId=" + officeId;
        HttpGetUtil httpGet = new HttpGetUtil(this) {

            @Override
            public void success(String json) {
                L.i("人员列表------" + json);
                mUsers = GsonUtil.getUsers(json);
                if (GsonUtil.hasData(mUsers)) {
                    // 显示用户列表窗口
                    showUserPopWindow();
                } else {
                    new MyAlertDialog(LoginActivity.this, "人员列表为空").show();
                }
            }

        };
        httpGet.doGet(url, mDialog, this, "人员列表");
    }

    /**
     * 显示用户列表窗口
     */
    private void showUserPopWindow() {
        initPopupData();
        // 设置layout在PopupWindow中显示的位置
        mPopWindow.showAtLocation(
                LoginActivity.this.findViewById(R.id.rl_main), Gravity.BOTTOM
                        | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    /**
     * 设置弹出框的内容数据、监听等
     */
    private void initPopupData() {
        // 设置监听事件
        TextView ensure = (TextView) mPopWindow.getContentView().findViewById(
                R.id.tv_ensure);
        TextView cancle = (TextView) mPopWindow.getContentView().findViewById(
                R.id.tv_cancle);

        final WheelView wheelView = (WheelView) mPopWindow.getContentView()
                .findViewById(R.id.wv_selection);
        wheelView.setOffset(2);
        // 如果之前选择好的，弹出式就显示之前选择的框
        if (mIndex == -1) {
            wheelView.setSeletion(0);
        } else {
            wheelView.setSeletion(mIndex);
        }
        if (GsonUtil.hasData(mUsers)) {
            mUsernames = new ArrayList<String>();
            for (User user : mUsers) {
                mUsernames.add(user.getNAME());
            }
            wheelView.setItems(mUsernames);
        }
        wheelView.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                L.i("selectedIndex: " + selectedIndex + ", item: " + item);
            }
        });
        // 确认事件
        ensure.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // 获取选中的用户并显示到界面上
                mIndex = wheelView.getSeletedIndex();
                if (mUsers != null) {
                    User select = mUsers.get(mIndex);
                    saveLoginInfo(select);
                    mEditUsername.setText(select.getNAME());
                    // 选好操作员后，焦点跳转到密码框，弹出输入法
                    mEditPassword.setFocusable(true);
                    mEditPassword.requestFocus();
                    KeyBoardUtils
                            .openKeybord(mEditPassword, LoginActivity.this);
                }
                mEditUsername.setBackgroundResource(R.drawable.edit_login_bg_normal);
                mPopWindow.dismiss();
            }
        });
        // 取消事件
        cancle.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mEditUsername.setBackgroundResource(R.drawable.edit_login_bg_normal);
                mPopWindow.dismiss();
            }
        });
    }


    /**
     * 保存登录信息
     *
     * @param user
     */
    protected void saveLoginInfo(User user) {
        LoginInfo.USER_ID = user.getUSERID();
        LoginInfo.USERNAME = user.getNAME();

        SharedPreferences sp = getSharedPreferences(ConfigDialog.CONFIG,
                Activity.MODE_PRIVATE);
        sp.edit().putString("username", user.getNAME()).commit();
        sp.edit().putString("user_id", user.getLOGINNAME()).commit();
    }

    /**
     * 保存登录信息
     */
    protected void saveLoginInfo() {
        String username = mEditUsername.getText().toString();
        String password = mEditPassword.getText().toString();

        SharedPreferences sp = getSharedPreferences(ConfigDialog.CONFIG,
                Activity.MODE_PRIVATE);
        sp.edit().putString("username", username).commit();
        sp.edit().putString("password", password).commit();
    }

    /**
     * 点击登录按钮
     *
     * @param view
     */
    @OnClick(R.id.btn_login)
    public void loginBtn(View view) {
        String username = mEditUsername.getText().toString();
        String password = mEditPassword.getText().toString();
        if (TextUtils.isEmpty(username)) {
            MyAlertDialog alertDialog = new MyAlertDialog(this, "请选择登陆用户");
            alertDialog.show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            MyAlertDialog alertDialog = new MyAlertDialog(this, "请输入密码");
            alertDialog.show();
            return;
        }

        SharedPreferences sp = getSharedPreferences(ConfigDialog.CONFIG,
                Activity.MODE_PRIVATE);
        String name = sp.getString("username", "");
        String id = sp.getString("user_id", "");
        if (username.equals(name)) {
            if (isUserInOffice(id)) {
                // 登陆逻辑
                login(id, password);
            } else {
                new MyAlertDialog(this, "不是该科室用户").show();
            }
        } else {
            new MyAlertDialog(this, "用户名错误").show();
        }
    }

    protected boolean isUserInOffice(String username) {
        if (!GsonUtil.hasData(mUsers)) {
            return true;
        }
        for (User u : mUsers) {
            if (username.equals(u.getLOGINNAME())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据用户名和密码判断登陆
     *
     * @param username
     * @param password
     */
    private void login(final String username, String password) {

        String url = SettingInfo.SERVICE + Method.LOGIN_NURSE + "?LoginName="
                + username + "&Password=" + password;
        HttpGetUtil httpGet = new HttpGetUtil(this) {

            @Override
            public void success(String json) {
                L.i("登陆返回值------" + json);
                // 对登陆返回值做判断，验证是否登陆成功
                verifyLoginInfo(json);
            }
        };
        httpGet.doGet(url, mDialog, this, "登陆");
    }

    /**
     * 对登陆返回值做判断，验证是否登陆成功
     *
     * @param json
     */
    private void verifyLoginInfo(String json) {
        String content = null;
        if ("\"-1\"".equals(json)) {
            content = "用户名不存在";
        } else if ("\"-2\"".equals(json)) {
            content = "密码错误";
        } else if (json != null && (json.trim().length() > 3)) {
            saveLoginInfo();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            return;
        }
        MyAlertDialog alertDialog = new MyAlertDialog(this, content);
        alertDialog.show();
    }


    @Override
    protected void onResume() {
        registerScanReceiver();
        // 在使用黑色的设备的时候需要这个来进行初始化
        // initScan();
        super.onResume();
    }

    private ScanManager mScanManager;

    private void initScan() {
        // TODO Auto-generated method stub
        mScanManager = new ScanManager();
        mScanManager.openScanner();

        mScanManager.switchOutputMode(0);
        soundpool = new SoundPool(1, AudioManager.STREAM_NOTIFICATION, 100); // MODE_RINGTONE
        soundid = soundpool.load("/etc/Scan_new.ogg", 1);
    }

    @Override
    protected void onStop() {
        if (scanReceiver != null) {
            unregisterReceiver(scanReceiver);
        }
        super.onStop();
    }

    private void registerScanReceiver() {

        scanReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                int barocodelen = intent.getIntExtra("length", -1);
                String result = new String();
                if (barocodelen == -1) {
                    result = intent.getStringExtra(SettingInfo.RECEIVE_STRING)
                            .toString().replace("#", "");
                } else {
                    soundpool.play(soundid, 1, 1, 0, 0, 1);
                    byte[] barcode = intent.getByteArrayExtra("barcode");
                    byte temp = intent.getByteExtra("barcodeType", (byte) 0);
                    android.util.Log.i("debug", "----codetype--" + temp);
                    result = new String(barcode, 0, barocodelen).replace("#",
                            "");
                }
                L.i(result);
                String[] resultArray = result.split("@");
                String username = resultArray[0];
                String password = resultArray[1];
                String officeId = resultArray[2];
                if (LoginInfo.OFFICE_ID.equals(officeId)) {
                    login(username, password);
                } else {
                    new MyAlertDialog(LoginActivity.this, "科室不相符，请重新选择科室！")
                            .show();
                }
            }
        };

        IntentFilter filter = new IntentFilter(SettingInfo.SCAN_FILTER);
        filter.addAction("urovo.rcv.message");
        registerReceiver(scanReceiver, filter);

    }

    /**
     * 读取设备的各种信息，用来区别不同的接收器和广播
     */
    private void loadDeviceInfo() {
        String manufacturer = Build.MANUFACTURER;
        L.i("生产厂商------" + manufacturer);
        // 联想
        if ("lenovo".equals(manufacturer)) {
            SettingInfo.SCAN_FILTER = SettingInfo.LENOVO_FILTER;
            SettingInfo.RECEIVE_STRING = SettingInfo.LENOVO_STRING;
        } else if ("Motorola Solutions".equals(manufacturer)) {
            SettingInfo.SCAN_FILTER = SettingInfo.MOTOROLA_FILTER;
            SettingInfo.RECEIVE_STRING = SettingInfo.MOTOROLA_STRING;
        } else if ("rockchip".equals(manufacturer)) {
            SettingInfo.SCAN_FILTER = "com.ge.action.barscan";
            SettingInfo.RECEIVE_STRING = "value";
        } else {
            SettingInfo.SCAN_FILTER = "urovo.rcv.message";
            SettingInfo.RECEIVE_STRING = "";
            // SettingInfo.SCAN_FILTER = SettingInfo.EMH_FILTER;
            // SettingInfo.RECEIVE_STRING = SettingInfo.EMH_STRING;
            // SettingInfo.SCAN_FILTER = SettingInfo.T8_FILTER;
            // SettingInfo.RECEIVE_STRING = SettingInfo.T8_STRING;
        }
        // String info = DeviceUtil.getCPUSerial();
        // L.i(info);
        // if (info.trim().startsWith("ARM")) {
        // SettingInfo.SCAN_FILTER = SettingInfo.T8_SCANFILTER;
        // SettingInfo.RECEIVE_STRING = SettingInfo.T8_RECEIVE_STRING;
        // } else {
        // SettingInfo.SCAN_FILTER = SettingInfo.NEW_SCAN_FILTER;
        // SettingInfo.RECEIVE_STRING = SettingInfo.NEW_RECEIVE_STRING;
        // }

    }
}
