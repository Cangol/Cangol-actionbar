package mobi.cangol.mobile.actionbar.view;

import java.util.ArrayList;
import java.util.List;

import mobi.cangol.mobile.R;
import mobi.cangol.mobile.actionbar.ActionTab;
import mobi.cangol.mobile.actionbar.ActionTabItem;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class ActionTabView extends RadioGroup implements OnCheckedChangeListener {
	private LayoutInflater mInflater;
	private ActionTab mActionTab;
	private OnTabCheckedListener mOnTabCheckedListener;

	public ActionTabView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setOrientation(HORIZONTAL);
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.setOnCheckedChangeListener(this);
	}

	public void setActionTab(ActionTab actionTab) {
		this.mActionTab = actionTab;
	}

	public void addTabs(List<ActionTabItem> tabs) {
		for (int i = 0; i < tabs.size(); i++) {
			addTabItem(tabs.get(i));
		}
	}

	public void addTabItem(ActionTabItem tabItem) {
		this.addView(inflateTabItem(tabItem));
	}

	public void removeAllTabs() {
		this.removeAllViews();
	}

	private View inflateTabItem(ActionTabItem tabItem) {
		View view = mInflater.inflate(R.layout.actionbar_tab_item, this, false);

		RadioButton labelView = (RadioButton) view.findViewById(R.id.actionbar_tab_item);
		labelView.setId(tabItem.getId());
		labelView.setTag(tabItem);
		labelView.setText(tabItem.getTitle());
		labelView.setChecked(1 == tabItem.getChecked());
		return view;
	}

	public void setOnTabCheckedListener(OnTabCheckedListener onTabCheckedListener) {
		this.mOnTabCheckedListener = onTabCheckedListener;
	}

	public interface OnTabCheckedListener {
		boolean onTabChecked(ActionTabItem tab);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		View view = this.findViewById(checkedId);
		if (view == null)
			return;
		Log.d(VIEW_LOG_TAG, "first view=null");
		if (view.getTag() instanceof ActionTabItem) {
			final ActionTabItem tab = (ActionTabItem) view.getTag();
			if (mOnTabCheckedListener != null)
				mOnTabCheckedListener.onTabChecked(tab);
		}
	}

	public ArrayList<ActionTabItem> getTabs() {
		return mActionTab.getTabs();
	}

}
