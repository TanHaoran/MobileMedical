package com.thr.mobilemedical.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

import com.thr.mobilemedical.activity.ExamDetailActivity;
import com.thr.mobilemedical.R;
import com.thr.mobilemedical.adapter.ExamAdapter;
import com.thr.mobilemedical.bean.Exam;
import com.thr.mobilemedical.bean.ExamTitle;
import com.thr.mobilemedical.constant.LoginInfo;
import com.thr.mobilemedical.constant.Method;
import com.thr.mobilemedical.constant.SettingInfo;
import com.thr.mobilemedical.utils.GsonUtil;
import com.thr.mobilemedical.utils.HttpGetUtil;
import com.thr.mobilemedical.utils.L;
import com.thr.mobilemedical.utils.SelectbarUtil;
import com.thr.mobilemedical.view.MyProgressDialog;

/**
 * @author Jerry Tan
 * @version 1.0
 * @description 病患信息-检验界面
 * @date 2015年9月11日14:12:34
 * @Company Buzzlysoft
 * @email thrforever@126.com
 * @remark
 */
public class ExamFragment extends Fragment {

    private View v;

    private MyProgressDialog mDialog;

    private ExpandableListView mListView;

    private ExamAdapter mAdapter;

    private Map<String, Integer> mTimeMap;
    private List<ExamTitle> mTimeList;
    private List<Exam> mExamList;
    private List<List<Exam>> mExams;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_exam, container, false);
        initView();
        if (!SettingInfo.IS_DEMO) {
            loadExamList(LoginInfo.patient.getPATIENTHOSID());
        } else {
            mExamList = new ArrayList<Exam>();
            for (int i = 0; i < 10; i++) {
                Exam e = new Exam();
                e.setNAME("心率检测报告");
                e.setTIME("2016-5-18 16:44:26");
                mExamList.add(e);
            }
            setData();
        }
        return v;
    }

    private void initView() {

        mDialog = new MyProgressDialog(getActivity());

        mListView = (ExpandableListView) v.findViewById(R.id.elv_exam);
        mListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                if (SettingInfo.IS_DEMO) {
                    return false;
                }
                Intent intent = new Intent();
                intent.putExtra("title",
                        mExams.get(groupPosition).get(childPosition).getNAME());
                intent.putExtra("examid",
                        mExams.get(groupPosition).get(childPosition).getID());
                intent.setClass(getActivity(), ExamDetailActivity.class);
                startActivity(intent);
                return false;
            }
        });
    }

    /**
     * 读取检验报告列表
     *
     * @param patientHosId
     */
    public void loadExamList(String patientHosId) {
        String url = SettingInfo.SERVICE + Method.GET_LIS + "?PatientHosId="
                + patientHosId;
        HttpGetUtil httpGet = new HttpGetUtil(getActivity()) {

            @Override
            public void success(String json) {
                L.i("读取检验报告列表------" + json);
                mExamList = GsonUtil.getExamList(json);
                setData();
            }
        };
        httpGet.doGet(url, mDialog, getActivity(), "检验报告列表");
    }

    /**
     * 将数据设置到扩展列表上
     */
    protected void setData() {
        mTimeMap = new HashMap<String, Integer>();
        mExams = new ArrayList<List<Exam>>();
        List<Exam> list = new ArrayList<Exam>();
        mTimeList = new ArrayList<ExamTitle>();

        ExamTitle title = new ExamTitle();

        if (mExamList != null && mExamList.size() > 0
                && !"".equals(mExamList.get(0).getID())) {
            for (Exam e : mExamList) {
                // 读取时间字串，如果存在就加一，不存在就新建key
                String time = e.getTIME().split(" ")[0];
                // 当出现新的日期走if，重复日期的走else
                if (!mTimeMap.containsKey(time)) {
                    // 如果有0条以上的记录就添加到List集合中
                    if (list.size() > 0) {
                        mExams.add(list);
                        mTimeList.add(title);
                        // 第一次就要设置好日期和数量1
                        title = new ExamTitle();
                        title.setTime(time);
                        title.setNum(1);
                    }
                    // list.clear();
                    list = new ArrayList<Exam>();
                    list.add(e);
                    mTimeMap.put(time, 1);
                } else {
                    // 出现重复项目就要将数量加一
                    Integer num = mTimeMap.get(time);
                    mTimeMap.put(time, num + 1);
                    title.setTime(time);
                    title.setNum(mTimeMap.get(time));
                    list.add(e);
                }
            }
            // 不要忘记将最后一批加入到集合中
            mTimeList.add(title);
            mExams.add(list);

            mAdapter = new ExamAdapter(getActivity(), mTimeList, mExams);
            mListView.setAdapter(mAdapter);
            mListView.setVisibility(View.VISIBLE);
            // 如果第一个有父集合就展开第一项
            if (mListView.getChildAt(0) != null) {
                mListView.expandGroup(0);
            }
        } else {
            mListView.setVisibility(View.INVISIBLE);
        }

    }
}
