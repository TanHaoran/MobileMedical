package com.thr.mobilemedical.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.thr.mobilemedical.R;
import com.thr.mobilemedical.adapter.BaseOrderAdapter;
import com.thr.mobilemedical.bean.Order;
import com.thr.mobilemedical.constant.LoginInfo;
import com.thr.mobilemedical.constant.Method;
import com.thr.mobilemedical.constant.SettingInfo;
import com.thr.mobilemedical.utils.DateUtil;
import com.thr.mobilemedical.utils.GsonUtil;
import com.thr.mobilemedical.utils.HttpGetUtil;
import com.thr.mobilemedical.utils.L;
import com.thr.mobilemedical.utils.SelectbarUtil;
import com.thr.mobilemedical.view.MyProgressDialog;
import com.thr.mobilemedical.view.SelectorView;

/**
 * @author Jerry Tan
 * @version 1.0
 * @description 病患信息-医嘱界面
 * @date 2015年9月11日14:12:45
 * @Company Buzzlysoft
 * @email thrforever@126.com
 * @remark
 */
public class OrderFragment extends Fragment {

    private View v;

    private SelectorView mSelectorView;


    private TextView mTotal;
    private TextView mExecute;
    private TextView mNotExe;

    private ListView mListView;
    private List<Order> mLongList;
    private List<Order> mTempList;
    private BaseOrderAdapter mAdapter;

    private MyProgressDialog mDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_order, container, false);
        initView();

        if (!SettingInfo.IS_DEMO) {

            loadLongOrder(LoginInfo.patient.getPATIENTHOSID(),
                    LoginInfo.patient.getPATIENTINTIMES(), "1");
            loadTempOrder(LoginInfo.patient.getPATIENTHOSID(),
                    LoginInfo.patient.getPATIENTINTIMES(), "0");
        } else {
            mLongList = new ArrayList<Order>();
            mTempList = new ArrayList<Order>();
            for (int i = 0; i < 5; i++) {
                Order order = new Order();
                order.setDOCTOR("李医生");
                order.setDOSETYPE("3次");
                order.setEXECTIME("4");
                order.setEXECTIMES("5");
                order.setFREQUENCY("每小时一次");
                order.setMEDICINEWAY("口服");
                order.setExecUser("张莉");
                order.setNOTE("备注");
                order.setSTARTTIME("2016-5-17 15:43:23");
                order.setSTOPTIME("2016-5-18 15:43:40");
                mLongList.add(order);
                mTempList.add(order);
            }
            mAdapter = new BaseOrderAdapter(getActivity(), mLongList,
                    R.layout.item_baseorder);
            mListView.setAdapter(mAdapter);
            if (mLongList != null && mLongList.size() > 0) {
                mSelectorView.setLeftText("长期医嘱" + "(" + mLongList.size() + ")");
            } else {
                mSelectorView.setLeftText("长期医嘱");
            }
            if (mTempList != null && mTempList.size() > 0) {
                mSelectorView.setRightText("临时医嘱" + "(" + mTempList.size() + ")");
            } else {
                mSelectorView.setRightText("临时医嘱");
            }
        }
        return v;
    }

    private void initView() {
        mDialog = new MyProgressDialog(getActivity());

        mSelectorView = (SelectorView) v.findViewById(R.id.select_view);

        mTotal = (TextView) v.findViewById(R.id.tv_total);
        mExecute = (TextView) v.findViewById(R.id.tv_execute);
        mNotExe = (TextView) v.findViewById(R.id.tv_notexe);

        mListView = (ListView) v.findViewById(R.id.lv_order);

        mSelectorView.setOnSelectorClickListener(new SelectorView.OnSelectorClickListener() {
            @Override
            public void onLeftClick() {
                if (!SettingInfo.IS_DEMO) {
                    changeToLong();
                }
            }

            @Override
            public void onRightClick() {
                if (!SettingInfo.IS_DEMO) {
                    chagneToTemp();
                }
            }
        });


    }

    /**
     * 切换到长期医嘱
     */
    public void changeToLong() {
        initOrderList(mLongList);
    }

    /**
     * 切换到临时医嘱
     */
    private void chagneToTemp() {
        initOrderList(mTempList);
    }

    /**
     * 初始化医嘱数据
     *
     * @param orderList
     */
    private void initOrderList(List<Order> orderList) {
        mAdapter = new BaseOrderAdapter(getActivity(), orderList,
                R.layout.item_baseorder);
        if (orderList != null && orderList.size() > 0
                && !orderList.get(0).getPATIENTHOSID().trim().equals("")) {
            mListView.setVisibility(View.VISIBLE);
            mListView.setAdapter(mAdapter);
            mTotal.setText(String.valueOf(orderList.size()));
            int execute = 0;
            for (Order o : orderList) {
                if (o.getEXECTIME() != null && !"".equals(o.getEXECTIME())) {
                    execute++;
                }
            }
            mExecute.setText(String.valueOf(execute));
            mNotExe.setText(String.valueOf(orderList.size() - execute));
        } else {
            mListView.setVisibility(View.INVISIBLE);
            mTotal.setText("0");
            mExecute.setText("0");
            mNotExe.setText("0");
        }
    }

    /**
     * 读取病患长期医嘱信息
     *
     * @param patientHosId
     * @param patientInTimes
     * @param orderType      1：长期医嘱；0：临时医嘱
     */
    public void loadLongOrder(String patientHosId, String patientInTimes,
                              String orderType) {
        String url = SettingInfo.SERVICE + Method.DOCTOR_ORDER_BY_PATIENTHOSID
                + "?PatientHosId=" + patientHosId + "&PatientInTimes="
                + patientInTimes + "&OrderType=" + orderType;
        HttpGetUtil httpGet = new HttpGetUtil(getActivity()) {

            @Override
            public void success(String json) {
                L.i("长期医嘱------" + json);
                mLongList = GsonUtil.getOrderList(json);
                mLongList = filterLongOrder(mLongList);
                initOrderList(mLongList);
                if (mLongList != null
                        && mLongList.size() > 0
                        && !mLongList.get(0).getPATIENTHOSID().trim()
                        .equals("")) {
                    mSelectorView.setLeftText("长期医嘱" + "(" + mLongList.size() + ")");
                } else {
                    mSelectorView.setLeftText("长期医嘱");
                }
            }
        };
        httpGet.doGet(url, mDialog, getActivity(), "长期医嘱");
    }

    /**
     * 读取病患长期医嘱信息
     */
    public void loadTempOrder(String patientHosId, String patientInTimes,
                              String orderType) {
        String url = SettingInfo.SERVICE + Method.DOCTOR_ORDER_BY_PATIENTHOSID
                + "?PatientHosId=" + patientHosId + "&PatientInTimes="
                + patientInTimes + "&OrderType=" + orderType;
        HttpGetUtil httpGet = new HttpGetUtil(getActivity()) {

            @Override
            public void success(String json) {
                L.i("临时医嘱------" + json);
                mTempList = GsonUtil.getOrderList(json);
                mTempList = filterTodayOrder(mTempList);
                if (mTempList != null
                        && mTempList.size() > 0
                        && !mTempList.get(0).getPATIENTHOSID().trim()
                        .equals("")) {
                    mSelectorView.setRightText("临时医嘱" + "(" + mTempList.size() + ")");
                } else {
                    mSelectorView.setRightText("临时医嘱");
                }
            }
        };
        httpGet.doGet(url, mDialog, getActivity(), "临时医嘱");
    }

    /**
     * 对只有是今天的医嘱进行过滤
     *
     * @param orderList
     * @return
     */
    protected List<Order> filterTodayOrder(List<Order> orderList) {
        List<Order> orders = new ArrayList<Order>();
        if (orderList != null && orderList.size() > 0) {
            for (Order o : orderList) {
                if (o.getSUBTIME().split(" ").length == 2) {
                    String date = DateUtil.formatDateOrder(o.getSUBTIME()
                            .split(" ")[0].replace("-", "/"));
                    String now = DateUtil.getYMD();
                    now = now.replace("-", "/");
                    if (date.equals(now)) {
                        orders.add(o);
                    }
                }
            }
            orders = filterTempOrder(orders);
        }
        return orders;
    }

    /**
     * 对临时医嘱进行过滤，父医嘱号相同的归为一类
     *
     * @param orderList
     * @return
     */
    protected List<Order> filterLongOrder(List<Order> orderList) {
        List<Order> orders = new ArrayList<Order>();
        for (int i = 0; i < orderList.size(); i++) {
            Order o = orderList.get(i);
            if (i == 0) {
                orders.add(o);
            } else {
                // 如果两个父医嘱号是一致并且计划执行时间一致的就显示为一条医嘱
                if (orderList.get(i - 1).getPARENTORDER()
                        .equals(orderList.get(i).getPARENTORDER())) {
                    String content = orders.get(orders.size() - 1)
                            .getORDERCONTENT()
                            + " "
                            + orders.get(orders.size() - 1).getPERTIME();
                    orders.get(orders.size() - 1).setORDERCONTENT(
                            content + "\n" + o.getORDERCONTENT()
                                    + o.getPERTIME());
                    continue;
                }
                orders.add(o);
            }
        }
        return orders;
    }

    protected List<Order> filterTempOrder(List<Order> orderList) {
        List<Order> orders = new ArrayList<Order>();
        for (int i = 0; i < orderList.size(); i++) {
            Order o = orderList.get(i);
            if (i == 0) {
                orders.add(o);
            } else {
                // 如果两个父医嘱号是一致并且计划执行时间一致的就显示为一条医嘱
                if (orderList.get(i - 1).getPARENTORDER()
                        .equals(orderList.get(i).getPARENTORDER())
                        && orderList.get(i - 1).getPLANEXECTIME()
                        .equals(orderList.get(i).getPLANEXECTIME())) {
                    String content = orders.get(orders.size() - 1)
                            .getORDERCONTENT()
                            + " "
                            + orders.get(orders.size() - 1).getPERTIME();
                    orders.get(orders.size() - 1).setORDERCONTENT(
                            content + "\n\n" + o.getORDERCONTENT()
                                    + o.getPERTIME());
                    continue;
                }
                orders.add(o);
            }
        }
        return orders;
    }
}
