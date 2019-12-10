package mobi.cangol.mobile.actionbar;

import android.widget.TextView;

import java.util.List;

import mobi.cangol.mobile.actionbar.view.ActionTabView.OnTabSelectedListener;

/**
 * @author Cangol
 */
public abstract class ActionTab {

    /**
     * 获取所有tabItem
     * @return tabItem列表
     */
    public abstract List<ActionTabItem> getTabs();

    /**
     * 设置tabItem列表
     * @param tabs tabItem列表
     */
    public abstract void setTabs(List<ActionTabItem> tabs);

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
     * @return ActionTabItem
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
     * @return  选择的tab的ID
     */
    public abstract int getTabSelected();

    /**
     * 设置tab选择监听
     *
     * @param onTabSelectedListener 监听
     */
    public abstract void setOnTabSelectedListener(OnTabSelectedListener onTabSelectedListener);

    /**
     * 获取view
     * @param id 资源id
     * @return TextView
     */
    public abstract TextView getTabView(int id);
}
