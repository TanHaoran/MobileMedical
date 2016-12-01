package com.thr.mobilemedical.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.thr.mobilemedical.bean.Exam;
import com.thr.mobilemedical.bean.ExamTitle;
import com.thr.mobilemedical.R;

/**
 * @description 检验的扩展列表适配器
 * @date 2015年9月25日 上午10:56:40
 * @version 1.0
 * @Company Buzzlysoft
 * @author Jerry Tan
 * @email thrforever@126.com
 * @remark
 */
public class ExamAdapter extends BaseExpandableListAdapter {

	@SuppressWarnings("unused")
	private Context mContext;
	private List<ExamTitle> mtitles;
	private List<List<Exam>> mExams;
	private LayoutInflater mInflater;

	public ExamAdapter(Context context, List<ExamTitle> titles,
			List<List<Exam>> exams) {
		mContext = context;
		mInflater = LayoutInflater.from(context);
		mtitles = titles;
		mExams = exams;
	}

	@Override
	public int getGroupCount() {
		return mtitles.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return mExams.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return mtitles.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return mExams.get(groupPosition).get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		GroupHolder groupHolder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_exam_group, null);
			groupHolder = new GroupHolder();
			groupHolder.tDate = (TextView) convertView
					.findViewById(R.id.tv_date);
			groupHolder.tNumber = (TextView) convertView
					.findViewById(R.id.tv_number);
			groupHolder.iArrow = (ImageView) convertView
					.findViewById(R.id.iv_arrow);
			convertView.setTag(groupHolder);
		} else {
			groupHolder = (GroupHolder) convertView.getTag();
		}
		// 判断isExpanded就可以控制是按下还是关闭，同时更换图片
		if (isExpanded) {
			groupHolder.iArrow.setBackgroundResource(R.drawable.arrow_down);
		} else {
			groupHolder.iArrow.setBackgroundResource(R.drawable.arrow_up);
		}
		groupHolder.tDate.setText(mtitles.get(groupPosition).getTime());
		groupHolder.tNumber.setText(String.valueOf(mtitles.get(groupPosition)
				.getNum()));
		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		ChildHolder childHolder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_exam_child, null);
			childHolder = new ChildHolder();
			childHolder.tContent = (TextView) convertView
					.findViewById(R.id.tv_content);
			childHolder.tDesc = (TextView) convertView
					.findViewById(R.id.tv_desc);
			convertView.setTag(childHolder);
		} else {
			childHolder = (ChildHolder) convertView.getTag();
		}
		childHolder.tContent.setText(mExams.get(groupPosition)
				.get(childPosition).getNAME());
		childHolder.tDesc.setVisibility(View.INVISIBLE);
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	class GroupHolder {
		TextView tDate;
		TextView tNumber;
		ImageView iArrow;
	}

	class ChildHolder {
		TextView tContent;
		TextView tDesc;
	}
}
