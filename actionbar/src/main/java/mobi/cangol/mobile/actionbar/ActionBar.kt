package mobi.cangol.mobile.actionbar

import android.support.annotation.IdRes
import android.view.View
import android.view.View.OnClickListener

/**
 * @author Cangol
 */

abstract class ActionBar {

    /**
     * 返回是否显示
     *
     * @return
     */
    abstract fun isShow(): Boolean

    /**
     * 设置显示
     *
     * @param show
     */
    abstract fun setShow(show: Boolean)

    /**
     * 获取标题
     *
     * @return
     */
    abstract fun getTitle(): CharSequence

    /**
     * 设置标题
     *
     * @param title
     */
    abstract fun setTitle(title: CharSequence)

    /**
     * 设置标题
     *
     * @param resId
     */
    abstract fun setTitle(resId: Int)

    /**
     * 获取标题对其方式
     *
     * @return
     */
    abstract fun getTitleGravity(): Int

    /**
     * 设置标题对其方式
     *
     * @param gravity
     */
    abstract fun setTitleGravity(gravity: Int)


    /**
     * 获取标题显示
     */
    abstract fun getTitleVisibility(): Int

    /**
     * 设置标题显示
     *
     * @param visibility
     */
    abstract fun setTitleVisibility(visibility: Int)

    /**
     * 获取导航菜单
     */
    abstract fun getListNavigation(): Array<String>?

    /**
     * 设置导航菜单
     *
     * @param navs
     */
    abstract fun setListNavigation(navs: Array<String>?)

    /**
     * 获取actionMenu
     *
     * @return
     */
    abstract fun getActionMenu(): ActionMenu

    /**
     * 获取所有菜单按钮
     *
     * @return
     */
    abstract fun getMenus(): MutableList<ActionMenuItem>

    /**
     * 设置菜单按钮
     *
     * @param actions
     */
    abstract fun addMenus(actions: MutableList<ActionMenuItem>)

    /**
     * 获取ActionTab
     */
    abstract fun getActionTab(): ActionTab

    /**
     * 设置标题栏tabItems
     */
    abstract fun setTabs(tabs: MutableList<ActionTabItem>)

    /**
     * 获取所有tabItem
     */
    abstract fun getTabs():MutableList<ActionTabItem>

    /**
     * 设置自定义的home和up
     *
     * @param homeId
     * @param upId
     */
    abstract fun setCustomHomeAsUpIndicator(homeId: Int, upId: Int)

    /**
     */
    abstract fun resetCustomHomeAsUpIndicator()

    /**
     * 设置home的显示
     *
     * @param show
     */
    abstract fun setDisplayShowHomeEnabled(show: Boolean)


    abstract fun hideHomeAsUpIndicator()
    /**
     * 显示home指示器
     */
    abstract fun displayHomeIndicator()

    /**
     * 显示Up指示器
     */
    abstract fun displayUpIndicator()

    /**
     * 设置指示器显示的offset，只对原生指示器起作用
     *
     * @param slideOffset
     */
    abstract fun displayIndicator(slideOffset: Float)

    /**
     * 设置指示器显示的颜色，只对原生指示器起作用
     *
     * @param color
     */
    abstract fun setIndicatorColor(color: Int)

    /**
     * 设置左侧按钮
     *
     * @param id       菜单唯一Id
     * @param text     文本资源string的id，必须有效
     * @param icon     图片资源的id，如果无，请填-1
     * @param listener 点击事件listener
     */
    abstract fun setLeftMenu(id: Int, text: Int, icon: Int, listener: OnClickListener)

    /**
     * 清除左侧按钮
     */
    abstract fun clearLeftMenu()

    /**
     * back时执行
     *
     * @return
     */
    abstract fun onBackPressed(): Boolean

    /**
     * 设置背景颜色
     *
     * @param color
     */
    abstract fun setBackgroundColor(color: Int)

    /**
     * 设置背景
     *
     * @param resId
     */
    abstract fun setBackgroundResource(resId: Int)

    /**
     * 设置标题点击事件监听
     * 此事件与setOnNavigationListener相冲突，只有后设置的有效
     *
     * @param listener
     */
    abstract fun setOnTitleClickListener(listener: OnClickListener)

    /**
     * 启动ActionMode
     *
     * @param callback
     * @return
     */
    abstract fun startActionMode(callback: ActionMode.Callback): ActionMode

    /**
     * 停止ActionMode
     */
    abstract fun stopActionMode()

    /**
     * 开启/关闭 刷新
     */
    abstract fun enableRefresh(enable: Boolean)


    /**
     * 开启/关闭 刷新left|right
     */
    abstract fun enableRefresh(enable: Boolean, gravity: Int)

    /**
     * 开始/停止 刷新
     */
    abstract fun refreshing(refresh: Boolean)

    /**
     * 刷新按钮点击事件
     *
     * @param listener
     */
    abstract fun setOnRefreshClickListener(listener: OnClickListener)

    /**
     * 设置导航菜单
     * 此事件与setOnTitleClickListener相冲r突，只有后设置的有效
     *
     * @param onNavigationListener 导航监听
     */
    abstract fun setOnNavigationListener(onNavigationListener: OnNavigationListener)

    /**
     * 清除导航菜单
     */
    abstract fun clearListNavigation()

    /**
     * 清除所有菜单按钮
     */
    abstract fun clearActionMenus()

    /**
     * 清除所有tabItem
     */
    abstract fun clearActionTabs()

    /**
     * 添加自定义view
     * 背景透明，所占空间为"指示器右侧到menu左侧"
     * 此时title会被隐藏，tab会被移除
     */
    abstract fun setCustomView(view: View)

    /**
     * 移除自定义view
     */
    abstract fun removeCustomView()

    /**
     * 获取view
     */
    abstract fun <T : View> findViewById(@IdRes id: Int): T?


}
