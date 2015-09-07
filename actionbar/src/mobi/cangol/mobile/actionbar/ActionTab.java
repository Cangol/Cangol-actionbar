package mobi.cangol.mobile.actionbar;

import java.util.ArrayList;

import mobi.cangol.mobile.actionbar.view.ActionTabView.OnTabSelectedListener;

public interface ActionTab {
	
	ArrayList<ActionTabItem> getTabs();

	void addTabs(ArrayList<ActionTabItem> tabs);
	
	void removeAllTabs();

	ActionTabItem newTab(int id,String title,int selected);
	
	void setTabSelected(int id);

	void setOnTabSelectedListener(OnTabSelectedListener onTabSelectedListener);
}
