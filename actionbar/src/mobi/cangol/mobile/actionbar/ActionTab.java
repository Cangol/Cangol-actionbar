package mobi.cangol.mobile.actionbar;

import java.util.ArrayList;

import mobi.cangol.mobile.actionbar.view.ActionTabView.OnTabSelectedListener;
/**
 * @author Cangol
 */
public interface ActionTab {

    /**
     * 获取所有tabItem
     */

	ArrayList<ActionTabItem> getTabs();

    /**
     * 添加tabItem列表
     */
	void addTabs(ArrayList<ActionTabItem> tabs);

    /**
     * 移除所有tabItem
     */
	void removeAllTabs();

    /**
     * 新增tabItem
     * @param id 为数字，tabItem唯一标示
     * @param title 标题
     * @param selected 选择状态：1为选择，0或其他为未选中
     * @return
     */
	ActionTabItem newTab(int id,String title,int selected);

    /**
     * 选择tab
     * @param id tabItem唯一标示
     */
	void setTabSelected(int id);

    /**
     * 设置tab选择监听
     * @param onTabSelectedListener
     */
	void setOnTabSelectedListener(OnTabSelectedListener onTabSelectedListener);
}
