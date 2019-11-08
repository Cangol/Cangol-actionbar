package mobi.cangol.mobile.actionbar

import android.widget.TextView

import mobi.cangol.mobile.actionbar.view.ActionTabView.OnTabSelectedListener

/**
 * @author Cangol
 */
abstract class ActionTab {

    /**
     * 设置tabItem列表
     */
    abstract fun setTabs(tabs: MutableList<ActionTabItem>)

    /**
     * 获取所有tabItem
     */
    abstract fun getTabs(): MutableList<ActionTabItem>

    /**
     * 选择tab
     *
     * @param id tabItem唯一标示
     */
    abstract fun setTabSelected(id: Int)

    /**
     * 获取所选择tab
     *
     */
    abstract fun getTabSelected(): Int

    /**
     * 移除所有tabItem
     */
    abstract fun removeAllTabs()

    /**
     * 新增tabItem
     *
     * @param id       为数字，tabItem唯一标示
     * @param title    标题
     * @param selected 选择状态：1为选择，0或其他为未选中
     * @return
     */
    abstract fun newTab(id: Int, title: String, selected: Int): ActionTabItem

    /**
     * 设置tab选择监听
     *
     * @param onTabSelectedListener
     */
    abstract fun setOnTabSelectedListener(onTabSelectedListener: OnTabSelectedListener)

    /**
     * 获取view
     */
    abstract fun getTabView(id: Int): TextView
}
