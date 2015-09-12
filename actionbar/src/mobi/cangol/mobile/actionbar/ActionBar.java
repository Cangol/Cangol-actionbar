package mobi.cangol.mobile.actionbar;

import android.view.View.OnClickListener;

import java.util.ArrayList;

import mobi.cangol.mobile.actionbar.ActionMenu;
import mobi.cangol.mobile.actionbar.ActionMenuItem;
import mobi.cangol.mobile.actionbar.ActionMode;
import mobi.cangol.mobile.actionbar.ActionTab;
import mobi.cangol.mobile.actionbar.ActionTabItem;
import mobi.cangol.mobile.actionbar.OnNavigationListener;
import mobi.cangol.mobile.actionbar.view.SearchView;

 public abstract class ActionBar {

	abstract public void setListNavigationCallbacks(String[] navs,
			OnNavigationListener onNavigationListener);

	abstract public void clearListNavigation();
	
	abstract public void setCustomHomeAsUpIndicator(int homeId,int upId);

	abstract public void setDisplayHomeAsUpEnabled(boolean show);

	abstract public void displayHomeIndicator();

	abstract public void displayUpIndicator();

	abstract public void displayIndicator(float slideOffset);
	
	abstract public void setIndicatorColor(int color);

	abstract public String getTitle();

	abstract public void setTitle(CharSequence title);

	abstract public void setTitle(int resid);

	abstract public void setTitleGravity(int gravity);

	abstract public void setOnTitleClickListener(OnClickListener listener);

	abstract public ActionMode startActionMode(ActionMode.Callback callback);

	abstract public void stopActionMode();

	abstract public void startProgress();

	abstract public void stopProgress();

	abstract public SearchView startSearchMode();

	abstract public void stopSearchMode();

	abstract public ActionMenu getActionMenu();

	abstract public void clearActions();

	abstract public ArrayList<ActionMenuItem> getActions();

	abstract public void addActions(ArrayList<ActionMenuItem> actions);

	abstract public boolean onBackPressed();

	abstract public void setShow(boolean show);
	
	abstract public boolean isShow();
	
	abstract public void setBackgroundColor(int color);
	
	abstract public void setBackgroundResource(int resId);
	
	abstract public ActionTab createdActionTab();

	abstract public ArrayList<ActionTabItem> getTabs();
	
	abstract public void clearActionTabs();

	abstract public void addTabs(ArrayList<ActionTabItem> tabs);

	abstract public  void setTitleVisibility(int visibly);

}
