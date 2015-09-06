package mobi.cangol.mobile.actionbar.internal;

import java.util.ArrayList;

import mobi.cangol.mobile.actionbar.ActionTab;
import mobi.cangol.mobile.actionbar.ActionTabItem;
import mobi.cangol.mobile.actionbar.view.ActionTabView;
import mobi.cangol.mobile.actionbar.view.ActionTabView.OnTabCheckedListener;

public class ActionTabImpl implements ActionTab{
	private ActionTabView mActionTabView;
	private ArrayList<ActionTabItem> mTabs=new ArrayList<ActionTabItem>();
	public ActionTabImpl(ActionTabView view){
		this.mActionTabView=view;
		mActionTabView.setActionTab(this);
	}
	
	@Override
	public void addTabItem(ActionTabItem tab) {
		mTabs.add(tab);
		mActionTabView.addTabItem(tab);
	}

	@Override
	public void addTabs(ArrayList<ActionTabItem> tabs) {
		mTabs.addAll(tabs);
		mActionTabView.addTabs(tabs);
	}

	@Override
	public void removeAllTabs() {
		mTabs.clear();
		mActionTabView.removeAllTabs();
	}

	@Override
	public void setOnTabCheckedListener(OnTabCheckedListener onTabCheckedListener) {
		mActionTabView.setOnTabCheckedListener(onTabCheckedListener);
	}

	@Override
	public ArrayList<ActionTabItem> getTabs() {
		return mTabs;
	}

}
