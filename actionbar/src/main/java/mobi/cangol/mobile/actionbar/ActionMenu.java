package mobi.cangol.mobile.actionbar;

import android.view.View;

import java.util.List;

import mobi.cangol.mobile.actionbar.view.ActionMenuView.OnActionClickListener;

/**
 * @author Cangol
 */
public abstract class ActionMenu {
    /**
     * 获取所有menuItem
     *
     * @return 菜单列表
     */
    public abstract List<ActionMenuItem> getActions();

    /**
     * 设置menuItem列表
     *
     * @param actions 菜单列表
     */
    public abstract void setActions(List<ActionMenuItem> actions);

    /**
     * 添加menu
     *
     * @param id 菜单唯一Id
     * @param text 文本资源string的id，必须有效
     * @param drawable 图片资源的id，如果无，请填-1
     * @param show 是否常显示
     * @return 菜单ActionMenuItem
     */
    public abstract ActionMenuItem addMenu(int id, int text, int drawable, int show);

    /**
     * 清除菜单
     */
    public abstract void clear();

    /**
     * 获取菜单数据
     *
     * @return 菜单大小
     */
    public abstract int size();

    /**
     * 通过索引获取菜单
     *
     * @param index 菜单的索引
     * @return 菜单ActionMenuItem
     */
    public abstract ActionMenuItem getAction(int index);

    /**
     * 通过id获取menu的view
     *
     * @param id menu的资源id
     * @return menu的view
     */
    public abstract View getActionMenuItemView(int id);

    /**
     * 设置事件监听
     *
     * @param onActionClickListener 监听
     */
    public abstract void setOnActionClickListener(OnActionClickListener onActionClickListener);
}
