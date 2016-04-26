package mobi.cangol.mobile.actionbar.internal;

import java.util.ArrayList;

import mobi.cangol.mobile.actionbar.ActionTab;
import mobi.cangol.mobile.actionbar.ActionTabItem;
import mobi.cangol.mobile.actionbar.view.ActionTabView;
import mobi.cangol.mobile.actionbar.view.ActionTabView.OnTabSelectedListener;
/**
 * @author Cangol
 */
public final class ActionTabImpl extends ActionTab{
	private ActionTabView mActionTabView;
	private ArrayList<ActionTabItem> mTabs=new ArrayList<ActionTabItem>();
	public ActionTabImpl(ActionTabView view){
		this.mActionTabView=view;
		mActionTabView.setActionTab(this);
	}

	@Override
	public void setTabs(ArrayList<ActionTabItem> tabs) {
        if(mTabs==null)
            mTabs=tabs;
        else
		    mTabs.addAll(tabs);
		mActionTabView.addTabs(tabs);
	}

	@Override
	public void removeAllTabs() {
		mTabs.clear();
		mActionTabView.removeAllTabs();
	}
	
	@Override
	public ArrayList<ActionTabItem> getTabs() {
		return mTabs;
	}
	
	@Override
	public void setOnTabSelectedListener(OnTabSelectedListener onTabSelectedListener) {
		mActionTabView.setOnTabSelectedListener(onTabSelectedListener);
	}

	@Override
	public ActionTabItem newTab(int id,String title,int selected) {
		ActionTabItem item=new ActionTabItem(id,title,selected);
		mTabs.add(item);
		mActionTabView.addTabItem(item);
		return item;
	}

	@Override
	public void setTabSelected(int id) {
		mActionTabView.setTabSelected(id);
	}

	

}
