package adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ldc.easycar.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import bean.Car;
import views.SectionedBaseAdapter;

/**
 * TODO 4，需要继承SectionedBaseAdapter
 * @author alex
 *
 */
public class SectionedGroupableAdapter extends SectionedBaseAdapter {

	private Context mContext;
	private List<Car> mList;
	private HashMap<Integer, List<Car>> mData = new HashMap<Integer, List<Car>>();

	public SectionedGroupableAdapter(Context mContext, List<Car> list) {
		this.mContext = mContext;
		if (list == null) {
			list = new ArrayList<Car>();
		}
		mList = list;
		generateData();
	}

	// 拼组数据 将所有的list按字母分组 并放入map中
	private void generateData() {
		HashSet<String> list = new HashSet<String>();
		int j = 0;
		List<Car> listCash = null;
		for (int i = 0, size = mList.size(); i < size; i++) {
			if (list.add(mList.get(i).getGroupName())) {
				if (listCash != null && listCash.size() != 0) {
					mData.put(j, listCash);
					j++;
				}
				listCash = new ArrayList<Car>();
				listCash.add(mList.get(i));
			} else {
				listCash.add(mList.get(i));
			}
		}
		mData.put(j, listCash);
	}

	// adapter 设置数据
	public void setList(List<Car> list) {
		if (list == null) {
			list = new ArrayList<Car>();
		}
		mList = list;
		generateData();
		notifyDataSetChanged();
	}

	// 根据右侧字母的index 获得listView中行对应的字母所在位置
	public int getPositionSecction(int index) {
		int position = 0;
		for (int i = 0, size = index; i < size; i++) {
			position += mData.get(i).size() + 1;
		}
		Log.i("sortListView", "position:" + position);
		return position;
	}

	@Override
	public Object getItem(int section, int position) {
		
		return mData.get(section).get(position);
	}

	@Override
	public long getItemId(int section, int position) {
		return position;
	}

	@Override
	public int getSectionCount() {
		return mData.size();
	}

	@Override
	public int getCountForSection(int section) {
		return mData.get(section) == null ? 0 : mData.get(section).size();
	}

	// item View
	@Override
	public View getItemView(int section, int position, View convertView,
			ViewGroup parent) {
		Holder holder = null;
		if (convertView == null) {
			holder = new Holder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.xc_item_layout, null, false);
			holder.tv = (TextView) convertView.findViewById(R.id.tv_xc_item);
			holder.iv= (ImageView) convertView.findViewById(R.id.iv_xc_item);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		Car item = (Car) getItem(section, position);
		if (item != null) {
			holder.tv.setText(item.name);
			Picasso.with(mContext).load(item.logoUrl).into(holder.iv);

		}
		return convertView;
	}

	// header View
	@Override
	public View getSectionHeaderView(int section, View convertView,
			ViewGroup parent) {
		TitleHolder titleHolder = null;

		if (null == convertView) {
			titleHolder = new TitleHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.xc_item_header_layout, null, false);
			titleHolder.title = (TextView) convertView.findViewById(R.id.title);
			convertView.setTag(titleHolder);
		} else {
			titleHolder = (TitleHolder) convertView.getTag();
		}
		
		if (mData != null && mData.size() != 0) {
			if (mData.get(section) != null && mData.get(section).size() != 0) {
				titleHolder.title.setText(mData.get(section).get(0)
						.getGroupName());
			}
		}

		return convertView;
	}

	static class Holder {
		public TextView tv;
		public ImageView iv;
	}

	static class TitleHolder {
		public TextView title;
	}

}
