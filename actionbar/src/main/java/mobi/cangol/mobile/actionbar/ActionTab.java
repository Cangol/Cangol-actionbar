package mobi.cangol.mobile.actionbar;

import java.util.ArrayList;

import mobi.cangol.mobile.actionbar.view.ActionTabView.OnTabSelectedListener;

/**
 * @author Cangol
 */
public abstract class ActionTab {

    /**
     * 获取所有tabItem
     */

    public abstract ArrayList<ActionTabItem> getTabs();

    /**
     * 设置tabItem列表
     */
    public abstract void setTabs(ArrayList<ActionTabItem> tabs);

    /**
     * 移除所有tabItem
     */
    public abstract void removeAllTabs();

    /**
     * 新增tabItem
     *
     * @param id       为数字，tabItem唯一标示
     * @param title    标题
     * @param selected 选择状态：1为选择，0或其他为未选中
     * @return
     */
    public abstract ActionTabItem newTab(int id, String title, int selected);

    /**
     * 选择tab
     *
     * @param id tabItem唯一标示
     */
    public abstract void setTabSelected(int id);

    /**
     * 获取选择的ID
     *
     */
    public abstract int getTabSelected();

    /**
     * 设置tab选择监听
     *
     * @param onTabSelectedListener
     */
    public abstract void setOnTabSelectedListener(OnTabSelectedListener onTabSelectedListener);
}
