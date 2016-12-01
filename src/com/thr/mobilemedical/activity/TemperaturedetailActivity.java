package com.thr.mobilemedical.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.thr.mobilemedical.R;
import com.thr.mobilemedical.adapter.PatientAdapter;
import com.thr.mobilemedical.bean.Nursingrecord;
import com.thr.mobilemedical.bean.NursingrecordFiled;
import com.thr.mobilemedical.constant.LoginInfo;
import com.thr.mobilemedical.constant.Method;
import com.thr.mobilemedical.constant.SettingInfo;
import com.thr.mobilemedical.utils.ClientUtil;
import com.thr.mobilemedical.utils.ClientUtil.CallBack;
import com.thr.mobilemedical.utils.DateUtil;
import com.thr.mobilemedical.utils.GsonUtil;
import com.thr.mobilemedical.utils.HttpGetUtil;
import com.thr.mobilemedical.utils.HttpUtils;
import com.thr.mobilemedical.utils.L;
import com.thr.mobilemedical.utils.SelectbarUtil;
import com.thr.mobilemedical.view.MyAlertDialog;
import com.thr.mobilemedical.view.MyDialog;
import com.thr.mobilemedical.view.MyDialog.SureClickListener;
import com.thr.mobilemedical.view.MyProgressDialog;
import com.thr.mobilemedical.view.NumberPopupWindow;
import com.thr.mobilemedical.view.NumberPopupWindow.LastClickListener;
import com.thr.mobilemedical.view.NumberPopupWindow.NextClickListener;
import com.thr.mobilemedical.view.NumberPopupWindow.SaveClickListener;
import com.thr.mobilemedical.view.PatientPopupWindow;
import com.thr.mobilemedical.view.SelectPopupWindow;
import com.thr.mobilemedical.view.SelectPopupWindow.OnEnsureClickListener;
import com.thr.mobilemedical.view.TimePopupWindow;
import com.thr.mobilemedical.view.TitleBar;
import com.thr.mobilemedical.view.TitleBar.OnLeftClickListener;
import com.thr.mobilemedical.view.TitleBar.OnRightClickListener;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @author Jerry Tan
 * @version 1.0
 * @description 护理记录单详细界面
 * @date 2015年9月30日 下午1:51:28
 * @Company Buzzlysoft
 * @email thrforever@126.com
 * @remark
 */

@SuppressLint({"HandlerLeak", "CutPasteId"})
@ContentView(R.layout.activity_temperaturedetail)
public class TemperaturedetailActivity extends Activity {

    @ViewInject(R.id.titlebar)
    private TitleBar mTitlebar;
    @ViewInject(R.id.tv_date)
    private TextView mToday;

    @ViewInject(R.id.ll_temperaturetab)
    private LinearLayout mTabTemperature;
    @ViewInject(R.id.ll_inandouttab)
    private LinearLayout mTabInandout;

    @ViewInject(R.id.tv_temperature)
    private TextView mTextTemperature;
    @ViewInject(R.id.tv_inandout)
    private TextView mTextInandout;

    @ViewInject(R.id.v_temperature)
    private View mTemperatureLine;
    @ViewInject(R.id.v_inandout)
    private View mInandoutLine;
    @ViewInject(R.id.tv_time)
    private TextView mTextTime;

    @ViewInject(R.id.ll_temperature)
    private LinearLayout mTemperatureLayout;
    // 最主要的内容区域
    @ViewInject(R.id.ll_nursingrecordfiled)
    private LinearLayout mFiledLayout;
    @ViewInject(R.id.ll_inandout)
    private LinearLayout mInandoutLayout;
    @ViewInject(R.id.tv_saveto)
    private TextView mTextSaveTo;

    @ViewInject(R.id.rb_axillary)
    private RadioButton mRbAxillary;

    @ViewInject(R.id.btn_save)
    private Button mSaveBtn;

    private SelectPopupWindow mSelectTimeWindow;
    private SelectPopupWindow mSelectWindow;
    private NumberPopupWindow mNumberWindow;
    private TimePopupWindow mTimeWindow;

    private MyProgressDialog mDialog; // 等待框
    // 护理记录单字段结构
    private List<NursingrecordFiled> mFileds;
    // 单条记录Map集合
    private Map<String, String> mDatas;
    // 出入量Map集合
    private Map<String, String> mMainMap;
    // 护理记录单id
    private String nursingrecordId = "";
    // 出入量项目
    private String item = "";
    // 病患列表
    private PatientPopupWindow mPatientWindow;

    private ListView mListView; // 病患列表
    private PatientAdapter mAdapter;

    private static final int RESULT = 0;
    private static final int UPDATE_NURSINGRECORD = 1;
    private static final int UPDATE_INANDOUT = 2;

    protected int saveToIndex;

    // 保存体温单界面TextView的序号
    private int mTextTemperIndex = 0;
    private List<TextView> mTextTemperList;
    // 保存出入量界面TextView的序号
    private int mTextIaoIndex = 0;
    private List<TextView> mTextIaoList;

    // 大便次数的选择
    private List<String> stoolTimes = new ArrayList<String>();

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case RESULT:
                    String result = (String) msg.obj;
                    showResultDialog(result);
                    break;
                case UPDATE_NURSINGRECORD:
                    setNursingrecordData();
                    break;
                case UPDATE_INANDOUT:
                    setInandoutData();
                    break;
            }
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getInstance().addActivity(this);
        initView();
        if (!SettingInfo.IS_DEMO) {

            loadAllData();
        }
    }

    /**
     * 读取所有的数据
     */
    private void loadAllData() {
        mTextTemperIndex = 0;
        mTextIaoIndex = 0;
        // 读取护理记录单结构
        loadNursingRecordFiled(LoginInfo.nursingrecordList.get(0)
                .getNURSINGID());
        // 读取出入量结构
        loadInandoutFiled(LoginInfo.patient.getPATIENTHOSID(),
                LoginInfo.patient.getPATIENTINTIMES(), DateUtil.getYMD());
    }

    private void initView() {

        initSelectbar();
        mDialog = new MyProgressDialog(this);
        mPatientWindow = new PatientPopupWindow(this);

        mTitlebar.setLeftImage(R.drawable.top_fh);
        mTitlebar.setRightImage(R.drawable.top_bh);
        mTitlebar.setOnLeftClickListener(new OnLeftClickListener() {

            @Override
            public void onLeftClick(View v) {
                finish();
            }
        });
        mTitlebar.setOnRightClickListener(new OnRightClickListener() {

            @Override
            public void onRightClick(View v) {
                mPatientWindow.showAtLocation(findViewById(R.id.ll_main),
                        Gravity.BOTTOM, 0, 0);
            }
        });

        mSelectTimeWindow = new SelectPopupWindow(this);
        mSelectWindow = new SelectPopupWindow(this);
        mNumberWindow = new NumberPopupWindow(this);
        mTimeWindow = new TimePopupWindow(this);
        mToday.setText(DateUtil.getYMD());
        // 读取进来时设定的护理时间
        mTextTime.setText(LoginInfo.timePoints.get(LoginInfo.timeIndex));
        mRbAxillary.setChecked(true);
        // 初始化保存到的文本
        mTextSaveTo.setText("空");
        for (Nursingrecord n : LoginInfo.nursingrecordList) {
            if (n.getNURSINGID().equals(LoginInfo.defaultNursingid)) {
                mTextSaveTo.setText(n.getNURSINGNAME());
            }
        }
        nursingrecordId = LoginInfo.defaultNursingid;

        // 初始化大便次数
        stoolTimes.add("0");
        stoolTimes.add("1");
        stoolTimes.add("2");
        stoolTimes.add("3");
        setPatientListView();
    }

    /**
     * 初始化选择条内容
     */
    public void initSelectbar() {
        SelectbarUtil selectbarUtil = new SelectbarUtil() {

            @Override
            public void data() {
                loadAllData();
            }
        };
        selectbarUtil.initView(this, this);
    }

    /**
     * 点击修改时间
     *
     * @param v
     */
    @OnClick(R.id.tv_time)
    public void onTime(View v) {
        mSelectTimeWindow.setTitle(R.string.select_time);
        mSelectTimeWindow.setOnEnsureClickListener(new OnEnsureClickListener() {

            @Override
            public void onEnsureClick() {
                mSelectTimeWindow.dismiss();
                mTextTime.setText(mSelectTimeWindow.getWheelString());
                LoginInfo.timeIndex = mSelectTimeWindow.getSelectIndex();
                loadAllData();
            }
        });
        List<String> ts = new ArrayList<String>();
        ts.addAll(LoginInfo.timePoints);
        mSelectTimeWindow.setItems(ts);
        mSelectTimeWindow.showAtLocation(this.findViewById(R.id.ll_main),
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        mSelectTimeWindow.setSelection(LoginInfo.timeIndex);
    }

    /**
     * 切换到体温标签
     */
    protected void changeToTemperature() {
        mTemperatureLine.setVisibility(View.VISIBLE);
        mInandoutLine.setVisibility(View.INVISIBLE);
        mTextTemperature.setTextColor(getResources().getColor(
                R.color.login_btn_bg_normal));
        mTextInandout.setTextColor(getResources().getColor(
                R.color.bottom_tab_text));
        mTemperatureLayout.setVisibility(View.VISIBLE);
        mInandoutLayout.setVisibility(View.GONE);
    }

    /**
     * 切换到出入量标签
     */
    protected void changeToInandout() {
        mInandoutLine.setVisibility(View.VISIBLE);
        mTemperatureLine.setVisibility(View.INVISIBLE);
        mTextInandout.setTextColor(getResources().getColor(
                R.color.login_btn_bg_normal));
        mTextTemperature.setTextColor(getResources().getColor(
                R.color.bottom_tab_text));
        mTemperatureLayout.setVisibility(View.GONE);
        mInandoutLayout.setVisibility(View.VISIBLE);
    }

    /**
     * 读取护理记录单结构
     */
    private void loadNursingRecordFiled(String nursignId) {
        String url = SettingInfo.SERVICE + Method.GET_NURSING_FILED
                + "?NursingId=" + nursignId;
        HttpGetUtil httpGet = new HttpGetUtil(this) {

            @Override
            public void success(String json) {
                mFileds = GsonUtil.getNursingStructureList(json);
                initFiledData();
                // 读取单条护理记录单记录
                if (mFileds != null && mFileds.size() > 0) {
                    loadNursingrecordData(LoginInfo.patient.getPATIENTHOSID(),
                            LoginInfo.patient.getPATIENTINTIMES(),
                            DateUtil.getYMD(),
                            LoginInfo.timePoints.get(LoginInfo.timeIndex));
                }
            }
        };
        httpGet.doGet(url, mDialog, this, "护理记录单");
    }

    /**
     * 读取出入量结构
     */
    private void loadInandoutFiled(String patientHosId, String patientInTimes,
                                   String tendDate) {
        String url = SettingInfo.SERVICE + Method.GET_NURSING_MAIN
                + "?PatientHosId=" + patientHosId + "&PatientInTimes="
                + patientInTimes + "&TENDDATE=" + tendDate;

        HttpUtils.doGetAsyn(this, url, new HttpUtils.CallBack() {

            @Override
            public void onRequestComplete(String json) {
                mMainMap = GsonUtil.getInandoutToMap(json);
                Message msg = new Message();
                msg.what = UPDATE_INANDOUT;
                mHandler.sendMessage(msg);
            }
        }, "出入量");

    }

    /**
     * 设置出入量的数据内容
     */
    protected void setInandoutData() {
        LayoutInflater inflater = LayoutInflater.from(this);
        mTextIaoList = new ArrayList<TextView>();
        mTextIaoList.clear();
        Set<String> set = mMainMap.keySet();
        mInandoutLayout.removeAllViews();
        Integer min = 0;
        Integer max = 9999;

        for (final String key : set) {
            boolean check = false;
            if ("STOOL".equals(key)) {
                item = "大便(次)";
                max = 3;
                check = true;
            } else if ("WEIGHTS".equals(key)) {
                item = "体重(kg)";
                max = 200;
                check = true;
            } else if ("URINE".equals(key)) {
                item = "尿量(ml)";
                max = 9999;
                check = true;
            } else if ("INTAKEONE".equals(key)) {
                item = "输入液量(ml)";
                max = 9999;
                check = true;
            } else if ("OUTTAKEONE".equals(key)) {
                item = "排出液量(ml)";
                max = 9999;
                check = true;
            } else if ("HEIGHT".equals(key)) {
                item = "身高(cm)";
                max = 250;
                check = true;
            } else if ("OTHER".equals(key)) {
                item = "血糖(g/ml)";
                max = 9999;
                check = true;
            } else if ("URINENUMBER".equals(key)) {
                item = "灌肠(次)";
                max = 10;
                check = true;
            } else if ("ABDOMINAL".equals(key)) {
                item = "灌肠后大便(次)";
                max = 10;
                check = true;
            }
            if (check) {
                View child = inflater.inflate(R.layout.item_nursingrecord,
                        mInandoutLayout, false);
                TextView tItem = (TextView) child.findViewById(R.id.tv_item);
                final TextView tContent = (TextView) child
                        .findViewById(R.id.tv_content);
                mTextIaoList.add(tContent);
                // 保存至集合
                tContent.setTag(R.id.tv_content, mTextIaoIndex);
                mTextIaoIndex++;

                tItem.setText(item);
                String value = mMainMap.get(key);
                tContent.setText(value);
                tContent.setTag(tItem);
                tContent.setTag(R.id.tv_low, min);
                tContent.setTag(R.id.tv_high, max);
                tContent.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        mNumberWindow.setTitle(((TextView) tContent.getTag())
                                .getText().toString());
                        // 将之前保存好的值显示在显示框中
                        mNumberWindow.showAtLocation(
                                TemperaturedetailActivity.this
                                        .findViewById(R.id.ll_main),
                                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0,
                                0);
                        // 如果之前保存值了，就显示出来，否则显示0
                        String content = tContent.getText().toString();
                        if (content != null && !"".equals(content.trim())) {
                            mNumberWindow.setScreen(content);
                        } else {
                            mNumberWindow.setScreen("0");
                        }
                        mNumberWindow
                                .setSaveClickListener(new SaveClickListener() {

                                    @Override
                                    public void onSaveClick() {
                                        if (isCorrectValue(Float
                                                        .parseFloat(mNumberWindow
                                                                .getContent()),
                                                (Integer) tContent
                                                        .getTag(R.id.tv_low),
                                                (Integer) tContent
                                                        .getTag(R.id.tv_high))) {
                                            tContent.setText(mNumberWindow
                                                    .getContent());
                                            mMainMap.put(key,
                                                    mNumberWindow.getContent());
                                            mNumberWindow.dismiss();
                                        }
                                    }
                                });
                        mNumberWindow
                                .setLastClickListener(new LastClickListener() {

                                    @Override
                                    public void onLastClick() {
                                        Integer dex = (Integer) tContent
                                                .getTag(R.id.tv_content);
                                        dex--;
                                        if (dex >= 0) {
                                            if (isCorrectValue(
                                                    Float.parseFloat(mNumberWindow
                                                            .getContent()),
                                                    (Integer) tContent
                                                            .getTag(R.id.tv_low),
                                                    (Integer) tContent
                                                            .getTag(R.id.tv_high))) {
                                                mNumberWindow.getSaveButton()
                                                        .performClick();
                                                mNumberWindow.dismiss();
                                                mTextIaoList.get(dex)
                                                        .performClick();
                                            }
                                        } else {
                                            dex = 0;
                                        }
                                    }
                                });
                        mNumberWindow
                                .setNextClickListener(new NextClickListener() {

                                    @Override
                                    public void onNextClick() {
                                        Integer dex = (Integer) tContent
                                                .getTag(R.id.tv_content);
                                        dex++;
                                        if (dex <= mTextIaoList.size() - 1) {
                                            if (isCorrectValue(
                                                    Float.parseFloat(mNumberWindow
                                                            .getContent()),
                                                    (Integer) tContent
                                                            .getTag(R.id.tv_low),
                                                    (Integer) tContent
                                                            .getTag(R.id.tv_high))) {
                                                mNumberWindow.getSaveButton()
                                                        .performClick();
                                                mNumberWindow.dismiss();
                                                mTextIaoList.get(dex)
                                                        .performClick();
                                            }
                                        } else {
                                            dex = mTextTemperList.size() - 1;
                                        }
                                    }
                                });
                    }
                });
                mInandoutLayout.addView(child);
            }
        }

    }

    /**
     * 读取单条记录
     */
    private void loadNursingrecordData(String patientHosId,
                                       String patientInTimes, String tendDay, String tendTime) {

        String url = SettingInfo.SERVICE + Method.GET_NURSING_LIST
                + "?PatientHosId=" + patientHosId + "&PatientInTimes="
                + patientInTimes + "&TENDDAY=" + tendDay + "&TENDTIME="
                + tendTime;
        HttpUtils.doGetAsyn(this, url, new HttpUtils.CallBack() {

            @Override
            public void onRequestComplete(String json) {
                mDatas = GsonUtil.getNursingrecordToMap(json);
                Message msg = new Message();
                msg.what = UPDATE_NURSINGRECORD;
                mHandler.sendMessage(msg);
            }
        }, "护理记录单单条记录");
    }

    /**
     * 设置好单条记录内容
     */
    protected void setNursingrecordData() {
        Iterator<Entry<String, String>> it = mDatas.entrySet().iterator();
        while (it.hasNext()) {
            Entry<String, String> entry = it.next();
            String key = entry.getKey();
            String value = entry.getValue();
            for (int i = 0; i < mFiledLayout.getChildCount(); i++) {
                View child = mFiledLayout.getChildAt(i);
                NursingrecordFiled filed = (NursingrecordFiled) child.getTag();
                String colName = filed.getCOLNAMES();
                // 对每一个控件获取tag和我们需要绑定的数据进行对比，一致的就进行数据绑定
                if (colName.equals(key)) {
                    // 因为血压的输入控件有2个，所以要对血压一栏要做特殊处理
                    if (!"BLOODPRESSURE".equals(key)) {
                        TextView tContent = (TextView) child
                                .findViewById(R.id.tv_content);
                        if ("TENDDATE".equals(key) && "".equals(value.trim())) {
                            tContent.setText(DateUtil.getHM());
                        } else if ("TENDDATE".equals(key)
                                && !"".equals(value.trim())) {
                            if (value.split(" ").length == 2) {
                                String s = value.split(" ")[1];
                                if (s != null) {
                                    s = s.substring(0, s.length() - 3);
                                    tContent.setText(s);
                                }
                            }
                        } else if ("单选".equals(filed.getCOLPROPERTY())) {
                            CheckBox box = (CheckBox) child
                                    .findViewById(R.id.cb_yes);
                            if ("是".equals(value)) {
                                box.setChecked(true);
                            } else {
                                box.setChecked(false);
                            }
                        } else {
                            if (!"".equals(value)) {
                                tContent.setText(value);
                            }
                        }
                    }
                    // 血压
                    else {
                        TextView tLow = (TextView) child
                                .findViewById(R.id.tv_low);
                        TextView tHigh = (TextView) child
                                .findViewById(R.id.tv_high);
                        String[] bloodPressure = value.split("/");
                        // 如果产生2个值，说明有一个低压，一个高压，要不然只有一个低压或者没有值。
                        if (bloodPressure.length == 1) {
                            tLow.setText(bloodPressure[0]);
                        } else if (bloodPressure.length == 2) {
                            tLow.setText(bloodPressure[0]);
                            tHigh.setText(bloodPressure[1]);
                        }
                    }
                }
            }
        }
    }

    /**
     * 将读取到的字段信息显示在界面上
     */
    private void initFiledData() {
        mFiledLayout.removeAllViews();
        mTextTemperList = new ArrayList<TextView>();
        mTextTemperList.clear();
        if (mFileds != null && mFileds.size() > 0) {
            for (int i = 0; i < mFileds.size(); i++) {
                NursingrecordFiled filed = mFileds.get(i);
                // 当两种条件都满足的时候，就显示在界面上
                if ("1".equals(filed.getVISIBLE())
                        && "1".equals(filed.getEDITABLE())
                        && !"护理时间".equals(filed.getCOLNAME())) {
                    // 开始分类添加界面
                    LayoutInflater inflater = LayoutInflater.from(this);
                    View itemView = null;
                    // 类型1：血压分为低压和高压
                    if ("血压".equals(filed.getCOLNAME())) {
                        itemView = inflater.inflate(
                                R.layout.item_nursingrecord_pressure,
                                mFiledLayout, false);
                    }
                    // 类型2：数字输入框
                    else if ("数字".equals(filed.getCOLPROPERTY())) {
                        itemView = inflater.inflate(
                                R.layout.item_nursingrecord, mFiledLayout,
                                false);
                    }
                    // 类型3：文本输入框
                    else if ("文本".equals(filed.getCOLPROPERTY())) {
                        itemView = inflater.inflate(
                                R.layout.item_nursingrecord_edit, mFiledLayout,
                                false);
                    }
                    // 类型4：既可以下拉选择也可以输入文本
                    else if ("下拉文本".equals(filed.getCOLPROPERTY())) {
                        itemView = inflater.inflate(
                                R.layout.item_nursingrecord_selectandedit,
                                mFiledLayout, false);
                    }
                    // 类型5：单选框
                    else if ("单选".equals(filed.getCOLPROPERTY())) {
                        itemView = inflater.inflate(
                                R.layout.item_nursingrecord_check,
                                mFiledLayout, false);
                    }
                    // 类型5：选择输入框
                    else {
                        itemView = inflater.inflate(
                                R.layout.item_nursingrecord_select,
                                mFiledLayout, false);
                    }
                    fillFiledData(itemView, filed);
                    mFiledLayout.addView(itemView);
                }
            }
        }
    }

    /**
     * 填充好数据
     *
     * @param filed
     */
    private void fillFiledData(final View view, final NursingrecordFiled filed) {
        if (("数字".equals(filed.getCOLPROPERTY()) || "文本".equals(filed
                .getCOLPROPERTY())) && !"血压".equals(filed.getCOLNAME())) {
            TextView tv = (TextView) view.findViewById(R.id.tv_content);
            // 保存至集合
            tv.setTag(R.id.tv_content, mTextTemperIndex);
            mTextTemperIndex++;
            mTextTemperList.add(tv);
        }

        TextView tItem = (TextView) view.findViewById(R.id.tv_item);
        // 设置好左侧的内容
        tItem.setText(filed.getCOLNAME() + filed.getUNIT());
        // 通过initRange方法确定好最大值和最小值
        filed.initRange();
        // 如果有多选的选项，初始化好多选选项
        filed.initSelections();
        // 用当前的字段保存到Tag上，以便下回方便取得对应的值
        view.setTag(filed);
        // 血压字段需要特殊处理
        if ("血压".equals(filed.getCOLNAME())) {
            initBloodpressureFiled(view, filed);
        } else if ("下拉文本".equals(filed.getCOLPROPERTY())) {
            initSelectEditFiled(view, filed);
        } else if ("文本".equals(filed.getCOLPROPERTY())) {
            initEditFiled(view, filed);
        } else if ("单选".equals(filed.getCOLPROPERTY())) {
            initCheckFiled(view, filed);
        } else {
            initOtherFiled(view, filed);
        }
    }

    /**
     * 初始化单选框
     *
     * @param view
     * @param filed
     */
    private void initCheckFiled(View view, NursingrecordFiled filed) {
        final CheckBox cYes = (CheckBox) view.findViewById(R.id.cb_yes);
    }

    /**
     * 初始化文本框
     *
     * @param view
     * @param filed
     */
    private void initEditFiled(final View view, final NursingrecordFiled filed) {
        final EditText tContent = (EditText) view.findViewById(R.id.tv_content);
        // 设置护理项目的默认值
        tContent.setText(filed.getDEFAULTVALUE());
    }

    /**
     * 初始化选择文本框
     *
     * @param view
     * @param filed
     */
    private void initSelectEditFiled(final View view,
                                     final NursingrecordFiled filed) {
        final EditText tContent = (EditText) view.findViewById(R.id.tv_content);
        final LinearLayout lSelect = (LinearLayout) view
                .findViewById(R.id.iv_select);
        // 设置护理项目的默认值
        tContent.setText(filed.getDEFAULTVALUE());
        lSelect.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                mSelectWindow.setTitle(filed.getCOLNAME());
                // 将之前保存好的值显示在显示框中
                mSelectWindow.showAtLocation(TemperaturedetailActivity.this
                        .findViewById(R.id.ll_main), Gravity.BOTTOM
                        | Gravity.CENTER_HORIZONTAL, 0, 0);
                mSelectWindow.setItems(filed.getSelections());
                // 如果之前保存值了，就显示出来，否则显示0
                String save = tContent.getText().toString();
                if (!"".equals(save)) {
                    int index = mSelectWindow.getItems().indexOf(
                            tContent.getText().toString());
                    mSelectWindow.setSelection(index);
                } else {
                    mSelectWindow.setSelection(0);
                }
                mSelectWindow
                        .setOnEnsureClickListener(new OnEnsureClickListener() {
                            @Override
                            public void onEnsureClick() {
                                tContent.setText(mSelectWindow.getWheelString());
                                NursingrecordFiled tag = (NursingrecordFiled) view
                                        .getTag();
                                tag.setIndex(mSelectWindow.getSelectIndex());
                                tag.setContent(mSelectWindow.getWheelString());
                                view.setTag(tag);
                            }
                        });

            }
        });
    }

    /**
     * 初始化数字和选择框界面
     *
     * @param view
     * @param filed
     */
    private void initOtherFiled(final View view, final NursingrecordFiled filed) {
        final TextView tContent = (TextView) view.findViewById(R.id.tv_content);
        // 设置护理项目的默认值
        tContent.setText(filed.getDEFAULTVALUE());
        tContent.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // 先检查是否保存tag了
                NursingrecordFiled tagFiled = (NursingrecordFiled) view
                        .getTag();
                // 时间选择框
                if ("时间".equals(tagFiled.getCOLNAME())) {
                    mTimeWindow.showAtLocation(TemperaturedetailActivity.this
                            .findViewById(R.id.ll_main), Gravity.BOTTOM
                            | Gravity.CENTER_HORIZONTAL, 0, 0);
                    if (tagFiled != null
                            && tContent.getText().toString() != null
                            && !"".equals(tContent.getText().toString().trim())) {
                        String time = tContent.getText().toString();
                        setTimeToTimeWindow(time);
                    } else {
                        int h = DateUtil.getCurrentHour();
                        int m = DateUtil.getCurrentMinute();

                        mTimeWindow.getHourWheel().setSeletion(h);
                        mTimeWindow.getMinuteWheel().setSeletion(m);
                    }

                    mTimeWindow
                            .setSureClickListener(new TimePopupWindow.SureClickListener() {

                                @Override
                                public void onSureClick() {
                                    String s = mTimeWindow.getContent();
                                    if (s.split(" ").length == 2) {
                                        s = s.split(" ")[1];
                                        s = s.substring(0, s.length() - 3);
                                        tContent.setText(s);
                                    }
                                    NursingrecordFiled tag = (NursingrecordFiled) view
                                            .getTag();
                                    tag.setContent(mTimeWindow.getContent());
                                    view.setTag(tag);
                                }
                            });
                } else if ("数字".equals(tagFiled.getCOLPROPERTY())
                        || "文本".equals(tagFiled.getCOLPROPERTY())) {
                    // 设置好左侧的名字和最大最小值
                    mNumberWindow.setTitle(tagFiled.getCOLNAME());
                    mNumberWindow.setMin(tagFiled.getMin());
                    mNumberWindow.setMax(tagFiled.getMax());
                    // 将之前保存好的值显示在显示框中
                    mNumberWindow.showAtLocation(TemperaturedetailActivity.this
                            .findViewById(R.id.ll_main), Gravity.BOTTOM
                            | Gravity.CENTER_HORIZONTAL, 0, 0);
                    // 如果之前保存值了，就显示出来，否则显示0
                    String save = tContent.getText().toString();
                    if (!"".equals(save)) {
                        mNumberWindow.setScreen(save);
                    } else {
                        mNumberWindow.setScreen("0");
                    }
                    mNumberWindow.setSaveClickListener(new SaveClickListener() {

                        @Override
                        public void onSaveClick() {
                            if (isCorrectValue(Float.parseFloat(mNumberWindow
                                            .getContent()), mNumberWindow.getMin(),
                                    mNumberWindow.getMax())) {
                                tContent.setText(mNumberWindow.getContent());
                                NursingrecordFiled tag = (NursingrecordFiled) view
                                        .getTag();
                                tag.setContent(mNumberWindow.getContent());
                                view.setTag(tag);
                                mNumberWindow.dismiss();
                            }
                        }
                    });
                    mNumberWindow.setLastClickListener(new LastClickListener() {

                        @Override
                        public void onLastClick() {
                            Integer dex = (Integer) tContent
                                    .getTag(R.id.tv_content);
                            dex--;
                            if (dex >= 0) {
                                if (isCorrectValue(
                                        Float.parseFloat(mNumberWindow
                                                .getContent()), mNumberWindow
                                                .getMin(), mNumberWindow
                                                .getMax())) {
                                    mNumberWindow.getSaveButton()
                                            .performClick();
                                    mNumberWindow.dismiss();
                                    mTextTemperList.get(dex).performClick();
                                }
                            } else {
                                dex = 0;
                            }
                        }
                    });
                    mNumberWindow.setNextClickListener(new NextClickListener() {

                        @Override
                        public void onNextClick() {
                            Integer dex = (Integer) tContent
                                    .getTag(R.id.tv_content);
                            dex++;
                            if (dex <= mTextTemperList.size() - 1) {
                                if (isCorrectValue(
                                        Float.parseFloat(mNumberWindow
                                                .getContent()), mNumberWindow
                                                .getMin(), mNumberWindow
                                                .getMax())) {
                                    mNumberWindow.getSaveButton()
                                            .performClick();
                                    mNumberWindow.dismiss();
                                    mTextTemperList.get(dex).performClick();
                                }
                            } else {
                                dex = mTextTemperList.size() - 1;
                            }
                        }
                    });
                } else if ("下拉".equals(tagFiled.getCOLPROPERTY())) {
                    mSelectWindow.setTitle(tagFiled.getCOLNAME());
                    // 将之前保存好的值显示在显示框中
                    mSelectWindow.showAtLocation(TemperaturedetailActivity.this
                            .findViewById(R.id.ll_main), Gravity.BOTTOM
                            | Gravity.CENTER_HORIZONTAL, 0, 0);
                    mSelectWindow.setItems(tagFiled.getSelections());
                    // 如果之前保存值了，就显示出来，否则显示0
                    String save = tContent.getText().toString();
                    if (!"".equals(save)) {
                        int index = mSelectWindow.getItems().indexOf(
                                tContent.getText().toString());
                        mSelectWindow.setSelection(index);
                    } else {
                        mSelectWindow.setSelection(0);
                    }
                    mSelectWindow
                            .setOnEnsureClickListener(new OnEnsureClickListener() {
                                @Override
                                public void onEnsureClick() {
                                    tContent.setText(mSelectWindow
                                            .getWheelString());
                                    NursingrecordFiled tag = (NursingrecordFiled) view
                                            .getTag();
                                    tag.setIndex(mSelectWindow.getSelectIndex());
                                    tag.setContent(mSelectWindow
                                            .getWheelString());
                                    view.setTag(tag);
                                }
                            });
                } else if ("时间".equals(tagFiled.getCOLPROPERTY())
                        || "日期".equals(tagFiled.getCOLPROPERTY())) {
                } else {
                    view.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     * 初始化高压、低压两个界面
     *
     * @param view
     * @param filed
     */
    private void initBloodpressureFiled(final View view,
                                        final NursingrecordFiled filed) {
        final TextView tHigh = (TextView) view.findViewById(R.id.tv_high);
        final TextView tLow = (TextView) view.findViewById(R.id.tv_low);
        // 设置好左侧的显示项目，和高压低压的两个判断临界值
        mNumberWindow.setMin(filed.getMin());
        mNumberWindow.setMin2(filed.getMin());
        mNumberWindow.setMax(filed.getMax());
        mNumberWindow.setMax2(filed.getMax2());
        tHigh.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // 先检查是否保存tag了
                NursingrecordFiled tagFiled = (NursingrecordFiled) view
                        .getTag();
                mNumberWindow.setTitle(filed.getCOLNAME() + "高");
                // 将之前保存好的值显示在显示框中
                mNumberWindow.showAtLocation(TemperaturedetailActivity.this
                        .findViewById(R.id.ll_main), Gravity.BOTTOM
                        | Gravity.CENTER_HORIZONTAL, 0, 0);
                // 如果之前保存值了，就显示出来，否则显示0
                if (tagFiled != null && tagFiled.getContent() != null
                        && !"".equals(tagFiled.getContent().trim())) {
                    mNumberWindow.setScreen(tagFiled.getContent());
                } else {
                    mNumberWindow.setScreen("0");
                }
                mNumberWindow.setSaveClickListener(new SaveClickListener() {

                    // 高血压的保存事件
                    @Override
                    public void onSaveClick() {
                        if (isCorrectValue(
                                Float.parseFloat(mNumberWindow.getContent()),
                                mNumberWindow.getMin2(),
                                mNumberWindow.getMax2())) {
                            tHigh.setText(mNumberWindow.getContent());
                            NursingrecordFiled tag = (NursingrecordFiled) view
                                    .getTag();
                            tag.setContent(mNumberWindow.getContent());
                            view.setTag(tag);
                            mNumberWindow.dismiss();
                        }
                    }
                });
            }
        });
        tLow.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // 先检查是否保存tag了
                NursingrecordFiled tagFiled = (NursingrecordFiled) view
                        .getTag();
                mNumberWindow.setTitle(filed.getCOLNAME() + "低");
                // 将之前保存好的值显示在显示框中
                mNumberWindow.showAtLocation(TemperaturedetailActivity.this
                        .findViewById(R.id.ll_main), Gravity.BOTTOM
                        | Gravity.CENTER_HORIZONTAL, 0, 0);
                // 如果之前保存值了，就显示出来，否则显示0
                if (tagFiled != null && tagFiled.getContent2() != null) {
                    mNumberWindow.setScreen(tagFiled.getContent2());
                } else {
                    mNumberWindow.setScreen("0");
                }
                mNumberWindow.setSaveClickListener(new SaveClickListener() {

                    // 低血压的保存事件
                    @Override
                    public void onSaveClick() {
                        if (isCorrectValue(
                                Float.parseFloat(mNumberWindow.getContent()),
                                mNumberWindow.getMin(), mNumberWindow.getMax())) {
                            tLow.setText(mNumberWindow.getContent());
                            NursingrecordFiled tag = (NursingrecordFiled) view
                                    .getTag();
                            tag.setContent2(mNumberWindow.getContent());
                            view.setTag(tag);
                            mNumberWindow.dismiss();
                        }
                    }
                });
            }
        });
    }

    /**
     * 从服务端读取到的时间显示在将要弹出来的时间选择框上
     *
     * @param content
     */
    protected void setTimeToTimeWindow(String content) {
        // 先取得后面的时分秒
        String hour = content.split(":")[0];
        String minute = content.split(":")[1];
        mTimeWindow.getHourWheel().setSeletion(Integer.parseInt(hour));
        mTimeWindow.getMinuteWheel().setSeletion(Integer.parseInt(minute));
    }

    @OnClick(R.id.btn_save)
    public void onSave(View v) {
        // 必须填写好保存到护理记录单才可以提交内容
        String saveTo = mTextSaveTo.getText().toString();
        if (saveTo == null || "".equals(saveTo)) {
            MyAlertDialog alert = new MyAlertDialog(this, "请选择保存至一个护理记录单");
            alert.show();
        } else {
            if (checkHasData()) {
                LayoutInflater inflater = LayoutInflater.from(this);
                View view = inflater.inflate(R.layout.view_nursingrecord_text,
                        null);
                ScrollView scroll = new ScrollView(this);
                scroll.setLayoutParams(new LayoutParams(
                        LayoutParams.MATCH_PARENT, 500));
                TextView tContent = (TextView) view
                        .findViewById(R.id.tv_content);
                StringBuilder sb = new StringBuilder();
                MyDialog dialog = null;
                if (mTemperatureLayout.getVisibility() == View.VISIBLE) {
                    for (int i = 0; i < mFiledLayout.getChildCount(); i++) {
                        View child = mFiledLayout.getChildAt(i);
                        NursingrecordFiled filed = (NursingrecordFiled) child
                                .getTag();
                        String colName = filed.getCOLNAME();
                        if ("单选".equals(filed.getCOLPROPERTY())) {
                            CheckBox c = (CheckBox) child
                                    .findViewById(R.id.cb_yes);
                            String content = null;
                            if (c.isChecked()) {
                                content = "是";
                            } else {
                                content = "否";
                            }
                            sb.append(colName + "：" + content + "\n");
                        } else if (!"血压".equals(colName)) {
                            TextView t = (TextView) child
                                    .findViewById(R.id.tv_content);
                            String content = t.getText().toString();
                            sb.append(colName + "：" + content + "\n");
                        } else {
                            TextView tL = (TextView) child
                                    .findViewById(R.id.tv_low);
                            TextView tH = (TextView) child
                                    .findViewById(R.id.tv_high);
                            String content = tL.getText().toString() + "/"
                                    + tH.getText().toString();
                            sb.append(colName + "：" + content + "\n");
                        }
                    }
                    sb.append("保存到：" + mTextSaveTo.getText().toString());
                    tContent.setText(sb.toString());
                    scroll.addView(tContent);
                    // 护理记录单提交
                    dialog = new MyDialog(this, scroll,
                            new SureClickListener() {

                                @Override
                                public void onSureClick() {
                                    postNursingrecord();
                                }
                            });
                    dialog.show();
                    dialog.setTitle("体温单");
                } else {
                    for (int i = 0; i < mInandoutLayout.getChildCount(); i++) {
                        View child = mInandoutLayout.getChildAt(i);
                        TextView mItem = (TextView) child
                                .findViewById(R.id.tv_item);
                        TextView mContent = (TextView) child
                                .findViewById(R.id.tv_content);
                        sb.append(mItem.getText().toString() + "："
                                + mContent.getText().toString() + "\n");
                    }
                    sb.append("保存到：" + mTextSaveTo.getText().toString());
                    tContent.setText(sb.toString());
                    scroll.addView(tContent);

                    // 出入量提交
                    dialog = new MyDialog(this, scroll,
                            new SureClickListener() {

                                @Override
                                public void onSureClick() {
                                    postInandout();
                                }
                            });
                    dialog.show();
                    dialog.setTitle("出入量单");
                }
            } else {
                new MyAlertDialog(this, "没有记录任何数据！");
            }
        }
    }

    private boolean checkHasData() {
        boolean hasData = false;
        // 依次遍历控件中每一个内容
        for (int i = 0; i < mFiledLayout.getChildCount(); i++) {
            View child = mFiledLayout.getChildAt(i);
            TextView tContent = (TextView) child.findViewById(R.id.tv_content);
            TextView tLow = (TextView) child.findViewById(R.id.tv_low);
            TextView tHigh = (TextView) child.findViewById(R.id.tv_high);
            if (tContent != null) {
                if (!"".equals(tContent.getText().toString())) {
                    hasData = true;
                }
            }
            if (tLow != null) {
                if (!"".equals(tLow.getText().toString())) {
                    hasData = true;
                }
            }
            if (tHigh != null) {
                if (!"".equals(tHigh.getText().toString())) {
                    hasData = true;
                }
            }
        }
        return hasData;
    }

    /**
     * 提交出入量数据
     */
    protected void postInandout() {
        try {
            StringBuilder sb = new StringBuilder();
            Iterator<Entry<String, String>> it = mMainMap.entrySet().iterator();
            sb.append("{\"ds\":["); // TENDTIME
            while (it.hasNext()) {

                Map.Entry<String, String> entry = (Map.Entry<String, String>) it
                        .next();
                String key = entry.getKey();
                String value = entry.getValue();
                // 护理时间
                if ("TENDDATE".equals(key)) {
                    value = DateUtil.getYMD() + " 00:00:00";
                }
                // 患者住院id
                else if ("PATIENTHOSID".equals(key)) {
                    value = LoginInfo.patient.getPATIENTHOSID();
                }
                // 患者住院次数
                else if ("PATIENTINTIMES".equals(key)) {
                    value = LoginInfo.patient.getPATIENTINTIMES();
                }
                // 用户id
                else if ("OPERATORID".equals(key)) {
                    value = LoginInfo.USER_ID;
                }
                // 操作时间
                else if ("OPERATEDATE".equals(key)) {
                    value = DateUtil.getYMDHMS();
                }
                sb.append("{\"KeyProperty\":\"" + key
                        + "\",\"ValueProperty\":\""
                        + URLEncoder.encode(value, "utf-8") + "\"},");
            }
            // 拼接地址，发送请求
            final String json = sb.substring(0, sb.length() - 1) + "]}";

            L.i("处理数据" + json);

            String url = SettingInfo.SERVICE + Method.SET_NURSING_MAIN;

            L.i("url地址：" + url);
            ClientUtil.doPostAsyn(url, json, new CallBack() {

                @Override
                public void onRequestComplete(String result) {
                    // 返回1成功，0失败
                    L.i("返回值是：" + result);
                    Message msg = new Message();
                    msg.obj = result;
                    msg.what = RESULT;
                    mHandler.sendMessage(msg);
                }
            });
        } catch (Exception e) {

        }
    }

    /**
     * 回传护理记录单内容
     */
    protected void postNursingrecord() {
        final StringBuilder sb = new StringBuilder();
        sb.append("{\"ds\":[");
        try {
            // TENDTIME 护理时间
            sb.append("{\"KeyProperty\":\"TENDTIME\",\"ValueProperty\":\""
                    + URLEncoder
                    .encode(mTextTime.getText().toString(), "utf-8")
                    + "\"},");
            // NURSINGID 护理记录单id
            sb.append("{\"KeyProperty\":\"NURSINGID\",\"ValueProperty\":\""
                    + URLEncoder.encode(nursingrecordId, "utf-8") + "\"},");
            // PATIENTHOSID 病患住院号
            sb.append("{\"KeyProperty\":\"PATIENTHOSID\",\"ValueProperty\":\""
                    + URLEncoder.encode(LoginInfo.patient.getPATIENTHOSID(),
                    "utf-8") + "\"},");
            // PATIENTINTIMES 病患住院次数
            sb.append("{\"KeyProperty\":\"PATIENTINTIMES\",\"ValueProperty\":\""
                    + URLEncoder.encode(LoginInfo.patient.getPATIENTINTIMES(),
                    "utf-8") + "\"},");
            // NURSINGLISTID 用来表示记录是否编辑的id
            sb.append("{\"KeyProperty\":\"NURSINGLISTID\",\"ValueProperty\":\""
                    + URLEncoder.encode(mDatas.get("NURSINGLISTID"), "utf-8")
                    + "\"},");
            // TENDDAY 护理日期
            sb.append("{\"KeyProperty\":\"TENDDAY\",\"ValueProperty\":\""
                    + URLEncoder.encode(DateUtil.getYMD(), "utf-8") + "\"},");
            // OPERATORTIME 操作时间
            sb.append("{\"KeyProperty\":\"OPERATORTIME\",\"ValueProperty\":\""
                    + URLEncoder.encode(DateUtil.getYMDHMS(), "utf-8") + "\"},");
            // OPERATORID 操作人员id
            sb.append("{\"KeyProperty\":\"OPERATORID\",\"ValueProperty\":\""
                    + URLEncoder.encode(LoginInfo.USER_ID, "utf-8")
                    + "\"},");
            // 依次遍历控件中每一个内容
            for (int i = 0; i < mFiledLayout.getChildCount(); i++) {
                View child = mFiledLayout.getChildAt(i);
                TextView tContent = (TextView) child
                        .findViewById(R.id.tv_content);
                TextView tLow = (TextView) child.findViewById(R.id.tv_low);
                TextView tHigh = (TextView) child.findViewById(R.id.tv_high);
                CheckBox cBox = (CheckBox) child.findViewById(R.id.cb_yes);
                NursingrecordFiled filed = (NursingrecordFiled) child.getTag();
                String key = filed.getCOLNAMES();
                String value = "";
                // 因为血压有两个值所以需要拼凑一下数据
                if ("TENDDATE".equals(filed.getCOLNAMES())) {
                    value = DateUtil.getYMD() + " "
                            + tContent.getText().toString() + ":00";
                } else if ("血压".equals(filed.getCOLNAME())) {
                    value = tLow.getText().toString() + "/"
                            + tHigh.getText().toString();
                } else if ("单选".equals(filed.getCOLPROPERTY())) {
                    if (cBox.isChecked()) {
                        value = "是";
                    } else {
                        value = "否";
                    }
                } else {
                    value = tContent.getText().toString();
                }
                // 加入json数据中
                sb.append("{\"KeyProperty\":\"" + key
                        + "\",\"ValueProperty\":\""
                        + URLEncoder.encode(value, "utf-8") + "\"},");
            }
            // 拼接地址，发送请求
            final String json = sb.substring(0, sb.length() - 1) + "]}";

            L.i("处理数据" + json);

            String url = SettingInfo.SERVICE + Method.SET_NURSING_LIST;

            L.i("url地址：" + url);
            ClientUtil.doPostAsyn(url, json, new CallBack() {

                @Override
                public void onRequestComplete(String result) {
                    // 返回1成功，0失败
                    L.i("返回值是：" + result);
                    Message msg = new Message();
                    msg.obj = result;
                    msg.what = RESULT;
                    mHandler.sendMessage(msg);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 对提交结果进行提醒
     */
    private void showResultDialog(String result) {
        String content = null;
        if ("\"1\"".equals(result)) {
            content = "提交成功";
        } else {
            content = "提交失败";
        }
        MyAlertDialog alertDialog = new MyAlertDialog(this, content);
        alertDialog.show();
    }

    @OnClick(R.id.ll_temperaturetab)
    public void onTemperature(View v) {
        changeToTemperature();
    }

    @OnClick(R.id.ll_inandouttab)
    public void onInandout(View v) {
        changeToInandout();
    }

    @OnClick(R.id.tv_saveto)
    public void onSaveTo(View v) {
        final SelectPopupWindow saveToWindow = new SelectPopupWindow(this);
        List<String> nursingrecordList = new ArrayList<String>();
        nursingrecordList.add("空");
        for (Nursingrecord n : LoginInfo.nursingrecordList) {
            nursingrecordList.add(n.getNURSINGNAME());
        }
        saveToWindow.setItems(nursingrecordList);
        saveToWindow.showAtLocation(findViewById(R.id.ll_main), Gravity.BOTTOM
                | Gravity.CENTER_HORIZONTAL, 0, 0);
        saveToWindow.setSelection(saveToIndex);
        saveToWindow.setOnEnsureClickListener(new OnEnsureClickListener() {

            @Override
            public void onEnsureClick() {
                mTextSaveTo.setText(saveToWindow.getWheelString());
                saveToIndex = saveToWindow.getSelectIndex();
                if (0 == saveToIndex) {
                    nursingrecordId = LoginInfo.defaultNursingid;
                } else {
                    nursingrecordId = LoginInfo.nursingrecordList.get(
                            saveToIndex - 1).getNURSINGID();
                }
            }
        });
    }

    /**
     * 判断所填值是否符合
     *
     * @param value
     * @param min
     * @param max
     * @return
     */
    private boolean isCorrectValue(float value, float min, float max) {
        boolean isCorrect = false;
        if (value < min || value > max) {
            new MyAlertDialog(this, "请输入在" + min + "和" + max + "之间的值").show();
        } else {
            isCorrect = true;
        }
        return isCorrect;
    }

    /**
     * 初始化病人列表信息
     */
    protected void setPatientListView() {
        mListView = mPatientWindow.getListView();
        mAdapter = new PatientAdapter(this, LoginInfo.PATIENT_ALL,
                R.layout.item_patient);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                LoginInfo.patientIndex = position;
                LoginInfo.patient = LoginInfo.PATIENT_ALL.get(position);
                mPatientWindow.dismiss();
                loadAllData();
                initSelectbar();
            }
        });
    }

}
