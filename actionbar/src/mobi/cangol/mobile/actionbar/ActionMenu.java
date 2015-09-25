package mobi.cangol.mobile.actionbar;

import android.view.View;

import java.util.ArrayList;

import mobi.cangol.mobile.actionbar.view.ActionMenuView.OnActionClickListener;
/**
 * @author Cangol
 */
public interface ActionMenu {
    /**
     * 获取所有menuItem
     * @return
     */
	ArrayList<ActionMenuItem>  getActions();

    /**
     * 添加menuItem
     * @param action
     */
	void add(ActionMenuItem action);

    /**
     * 添加menuItem列表
     * @param actions
     */
	void addActions(ArrayList<ActionMenuItem> actions);

    /**
     * 清除菜单
     */
	void clear();

    /**
     * 获取菜单数据
     * @return
     */
	int size();

    /**
     * 通过索引获取菜单
     * @param index
     * @return
     */
	ActionMenuItem getAction(int index);

    /**
     * 通过id获取menu的view
     * @param id
     * @return
     */
	View getActionMenuItemView(int id);

    /**
     * 设置事件监听
     * @param onActionClickListener
     */
	void setOnActionClickListener(OnActionClickListener onActionClickListener);	
}
