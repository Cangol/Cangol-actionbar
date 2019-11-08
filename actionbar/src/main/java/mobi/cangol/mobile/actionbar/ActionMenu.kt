package mobi.cangol.mobile.actionbar

import android.view.View

import mobi.cangol.mobile.actionbar.view.ActionMenuView.OnActionClickListener

/**
 * @author Cangol
 */
abstract class ActionMenu {
    /**
     * 获取所有menuItem
     *
     * @return
     */
    /**
     * 设置menuItem列表
     *
     * @param actions
     */
    abstract var actions: MutableList<ActionMenuItem>

    /**
     * 添加menu
     *
     * @param id 菜单唯一Id
     * @param text 文本资源string的id，必须有效
     * @param drawable 图片资源的id，如果无，请填-1
     * @param show 是否常显示
     * @return
     */
    abstract fun addMenu(id: Int, text: Int, drawable: Int, show: Int): ActionMenuItem

    /**
     * 清除菜单
     */
    abstract fun clear()

    /**
     * 获取菜单数据
     *
     * @return
     */
    abstract fun size(): Int

    /**
     * 通过索引获取菜单
     *
     * @param index
     * @return
     */
    abstract fun getAction(index: Int): ActionMenuItem

    /**
     * 通过id获取menu的view
     *
     * @param id
     * @return
     */
    abstract fun getActionMenuItemView(id: Int): View

    /**
     * 设置事件监听
     *
     * @param onActionClickListener
     */
    abstract fun setOnActionClickListener(onActionClickListener: OnActionClickListener)
}
