package mobi.cangol.mobile.actionbar;

import java.util.ArrayList;

import mobi.cangol.mobile.actionbar.view.ActionTabView.OnTabCheckedListener;

public interface ActionTab {
	ArrayList<ActionTabItem> getTabs();

	void removeAllTabs();

	void addTabItem(ActionTabItem tab);

	void addTabs(ArrayList<ActionTabItem> tabs);

	void setOnTabCheckedListener(OnTabCheckedListener onTabCheckedListener);
}
