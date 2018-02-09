package mobi.cangol.mobile.actionbar.internal;

import android.view.View;
import android.view.View.OnClickListener;

import java.util.ArrayList;

import mobi.cangol.mobile.actionbar.ActionBar;
import mobi.cangol.mobile.actionbar.ActionMenu;
import mobi.cangol.mobile.actionbar.ActionMenuItem;
import mobi.cangol.mobile.actionbar.ActionMode;
import mobi.cangol.mobile.actionbar.ActionMode.Callback;
import mobi.cangol.mobile.actionbar.ActionTab;
import mobi.cangol.mobile.actionbar.ActionTabItem;
import mobi.cangol.mobile.actionbar.OnNavigationListener;
import mobi.cangol.mobile.actionbar.view.ActionBarView;

/**
 * @author Cangol
 */
public final class ActionBarImpl extends ActionBar {
    private ActionBarView mActionBarView;

    public ActionBarImpl(ActionBarView view) {
        this.mActionBarView = view;
    }

    @Override
    public void setCustomHomeAsUpIndicator(int homeId, int upId) {
        mActionBarView.setCustomHomeAsUpIndicator(homeId, upId);
    }

    @Override
    public void setDisplayShowHomeEnabled(boolean show) {
        mActionBarView.setDisplayShowHomeEnabled(show);
    }

    @Override
    public void displayIndicator(float slideOffset) {
        mActionBarView.displayIndicator(slideOffset);
    }

    @Override
    public void displayHomeIndicator() {
        mActionBarView.displayHomeIndicator();
    }

    @Override
    public void displayUpIndicator() {
        mActionBarView.displayUpIndicator();
    }

    @Override
    public void setIndicatorColor(int color) {
        mActionBarView.setIndicatorColor(color);
    }

    @Override
    public void setLeftMenu(int id, int text, int icon, OnClickListener listener) {
        mActionBarView.setLeftMenu(id,text,icon,listener);
    }

    @Override
    public void clearLeftMenu() {
        mActionBarView.clearLeftMenu();
    }

    @Override
    public boolean onBackPressed() {
        return mActionBarView.onBackPressed();
    }

    public void setBackgroundColor(int color) {
        mActionBarView.setBackgroundColor(color);
    }

    public void setBackgroundResource(int resId) {
        mActionBarView.setBackgroundResource(resId);
    }

    @Override
    public boolean isShow() {
        return mActionBarView.getVisibility() == View.VISIBLE;
    }

    @Override
    public void setShow(boolean show) {
        mActionBarView.setVisibility(show ? View.VISIBLE : View.GONE);

    }

    @Override
    public CharSequence getTitle() {
        return mActionBarView.getTitle();
    }

    @Override
    public void setTitle(int resId) {
        mActionBarView.setTitle(resId);
    }

    @Override
    public void setTitle(CharSequence title) {
        mActionBarView.setTitle(title);
    }

    @Override
    public void setTitleGravity(int gravity) {
        mActionBarView.setTitleGravity(gravity);
    }

    @Override
    public int getTitleGravity() {
        return mActionBarView.getTitleGravity();
    }

    @Override
    public void setTitleVisibility(int visibility) {
        mActionBarView.setTitleVisibility(visibility);
    }

    @Override
    public int getTitleVisibility() {
        return mActionBarView.getTitleVisibility();
    }

    @Override
    public void setOnTitleClickListener(OnClickListener listener) {
        mActionBarView.setOnTitleClickListener(listener);
    }

    @Override
    public ActionMode startActionMode(Callback callback) {
        return mActionBarView.startActionMode(callback);
    }


    @Override
    public void stopActionMode() {
        mActionBarView.stopActionMode();

    }

    @Override
    public void enableRefresh(boolean enable) {
        mActionBarView.enableRefresh(enable);
    }

    @Override
    public void enableRefresh(boolean enable, int gravity) {
        mActionBarView.enableRefresh(enable,gravity);
    }

    @Override
    public void refreshing(boolean refresh) {
        mActionBarView.refreshing(refresh);
    }

    @Override
    public void setOnRefreshClickListener(OnClickListener listener) {
        mActionBarView.setOnRefreshClickListener(listener);
    }

    @Override
    public void setOnNavigationListener(OnNavigationListener onNavigationListener) {
        mActionBarView.setOnNavigationListener(onNavigationListener);
    }

    @Override
    public String[] getListNavigation() {
        return mActionBarView.getListNavigation();
    }

    @Override
    public void setListNavigation(String[] navs) {
        mActionBarView.setListNavigation(navs);
    }

    @Override
    public void clearListNavigation() {
        mActionBarView.clearListNavigation();
    }


    @Override
    public ActionMenu getActionMenu() {
        return mActionBarView.getActionMenu();
    }

    @Override
    public ArrayList<ActionMenuItem> getMenus() {
        return mActionBarView.getActions();
    }

    @Override
    public void setMenus(ArrayList<ActionMenuItem> actions) {
        mActionBarView.setActions(actions);
    }

    @Override
    public void clearActionMenus() {
        mActionBarView.clearActions();
    }

    @Override
    public ActionTab getActionTab() {
        return mActionBarView.getActionTab();
    }

    @Override
    public ArrayList<ActionTabItem> getTabs() {
        return mActionBarView.getTabs();
    }

    @Override
    public void setTabs(ArrayList<ActionTabItem> tabs) {
        mActionBarView.setTabs(tabs);
    }

    @Override
    public void clearActionTabs() {
        mActionBarView.setTitleVisibility(View.VISIBLE);
        mActionBarView.clearActionTabs();
    }

    @Override
    public void setCustomView(View view) {
        mActionBarView.setCustomView(view);
    }

    @Override
    public void removeCustomView() {
        mActionBarView.removeCustomView();
    }

    @Override
    public View findViewById(int id) {
        return mActionBarView.findViewById(id);
    }


}