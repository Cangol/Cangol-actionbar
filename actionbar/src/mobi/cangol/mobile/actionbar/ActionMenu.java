package mobi.cangol.mobile.actionbar;

import java.util.ArrayList;

import mobi.cangol.mobile.actionbar.view.ActionMenuView.OnActionClickListener;
import android.view.View;

public interface ActionMenu {
	
	ArrayList<ActionMenuItem>  getActions();
	
	void add(ActionMenuItem action);
	
	void addActions(ArrayList<ActionMenuItem> actions);
	
	void clear();
	
	int size();
	
	ActionMenuItem getAction(int index);
	
	View getActionMenuItemView(int id);

	void setOnActionClickListener(OnActionClickListener onActionClickListener);	
}
