package mobi.cangol.mobile.actionbar.internal;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mobi.cangol.mobile.actionbar.ActionTab;
import mobi.cangol.mobile.actionbar.ActionTabItem;
import mobi.cangol.mobile.actionbar.view.ActionTabView;
import mobi.cangol.mobile.actionbar.view.ActionTabView.OnTabSelectedListener;

/**
 * @author Cangol
 */
public final class ActionTabImpl extends ActionTab {
    private ActionTabView mActionTabView;
    private List<ActionTabItem> mTabs = new ArrayList<>();
    public ActionTabImpl(ActionTabView view) {
        this.mActionTabView = view;
        mActionTabView.setActionTab(this);
    }

    @Override
    public void removeAllTabs() {
        mTabs.clear();
        mActionTabView.setOnTabSelectedListener(null);
        mActionTabView.removeAllTabs();
    }

    @Override
    public List<ActionTabItem> getTabs() {
        return mTabs;
    }

    @Override
    public void setTabs(List<ActionTabItem> tabs) {
        if(null!=tabs&&!tabs.isEmpty()){
            mTabs=tabs;
            mActionTabView.addTabs(tabs);
        }
    }

    @Override
    public void setOnTabSelectedListener(OnTabSelectedListener onTabSelectedListener) {
        mActionTabView.setOnTabSelectedListener(onTabSelectedListener);
    }

    @Override
    public TextView getTabView(int id) {
        return mActionTabView.findViewById(id);
    }

    @Override
    public ActionTabItem newTab(int id, String title, int selected) {
        ActionTabItem item = new ActionTabItem(id, title, selected);
        mTabs.add(item);
        mActionTabView.addTabItem(item);
        return item;
    }

    @Override
    public void setTabSelected(int id) {
        mActionTabView.setTabSelected(id);
    }

    @Override
    public int getTabSelected() {
        return mActionTabView.getTabSelected();
    }


}
