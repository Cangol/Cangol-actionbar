package mobi.cangol.mobile.actionbar.internal

import android.support.annotation.IdRes
import android.view.View
import android.view.View.OnClickListener
import mobi.cangol.mobile.actionbar.*
import mobi.cangol.mobile.actionbar.ActionMode.Callback
import mobi.cangol.mobile.actionbar.view.ActionBarView

/**
 * @author Cangol
 */
class ActionBarImpl(private val mActionBarView: ActionBarView) : ActionBar() {

    override var isShow: Boolean
        get() = mActionBarView.visibility == View.VISIBLE
        set(show) {
            mActionBarView.visibility = if (show) View.VISIBLE else View.GONE
        }

    override val title: CharSequence
        get() = mActionBarView.title

    override var titleGravity: Int
        get() = mActionBarView.titleGravity
        set(gravity) {
            mActionBarView.titleGravity = gravity
        }

    override var titleVisibility: Int
        get() = mActionBarView.titleVisibility
        set(visibility) {
            mActionBarView.titleVisibility = visibility
        }

    override var listNavigation: Array<String>?
        get() = mActionBarView.listNavigation
        set(navs) {
            mActionBarView.listNavigation = navs
        }


    override val actionMenu: ActionMenu
        get() = mActionBarView.actionMenu!!

    override var menus: MutableList<ActionMenuItem>
        get() = mActionBarView.actions
        set(actions) {
            mActionBarView.actions = actions
        }

    override val actionTab: ActionTab
        get() = mActionBarView.actionTab!!

    override var tabs: MutableList<ActionTabItem>
        get() = mActionBarView.tabs
        set(tabs) {
            mActionBarView.tabs = tabs
        }

    override fun setCustomHomeAsUpIndicator(homeId: Int, upId: Int) {
        mActionBarView.setCustomHomeAsUpIndicator(homeId, upId)
    }

    override fun resetCustomHomeAsUpIndicator() {
        mActionBarView.resetCustomHomeAsUpIndicator()
    }

    override fun setDisplayShowHomeEnabled(show: Boolean) {
        mActionBarView.setDisplayShowHomeEnabled(show)
    }

    override fun hideHomeAsUpIndicator() {
        mActionBarView.hideHomeAsUpIndicator()
    }

    override fun displayIndicator(slideOffset: Float) {
        mActionBarView.displayIndicator(slideOffset)
    }

    override fun displayHomeIndicator() {
        mActionBarView.displayHomeIndicator()
    }

    override fun displayUpIndicator() {
        mActionBarView.displayUpIndicator()
    }

    override fun setIndicatorColor(color: Int) {
        mActionBarView.setIndicatorColor(color)
    }

    override fun setLeftMenu(id: Int, text: Int, icon: Int, listener: OnClickListener) {
        mActionBarView.setLeftMenu(id, text, icon, listener)
    }

    override fun clearLeftMenu() {
        mActionBarView.clearLeftMenu()
    }

    override fun onBackPressed(): Boolean {
        return mActionBarView.onBackPressed()
    }

    override fun setBackgroundColor(color: Int) {
        mActionBarView.setBackgroundColor(color)
    }

    override fun setBackgroundResource(resId: Int) {
        mActionBarView.setBackgroundResource(resId)
    }

    override fun setTitle(resId: Int) {
        mActionBarView.setTitle(resId)
    }

    override fun setTitle(title: CharSequence) {
        mActionBarView.setTitle(title)
    }

    override fun setOnTitleClickListener(listener: OnClickListener) {
        mActionBarView.setOnTitleClickListener(listener)
    }

    override fun startActionMode(callback: Callback): ActionMode {
        return mActionBarView.startActionMode(callback)
    }


    override fun stopActionMode() {
        mActionBarView.stopActionMode()

    }

    override fun enableRefresh(enable: Boolean) {
        mActionBarView.enableRefresh(enable)
    }

    override fun enableRefresh(enable: Boolean, gravity: Int) {
        mActionBarView.enableRefresh(enable, gravity)
    }

    override fun refreshing(refresh: Boolean) {
        mActionBarView.refreshing(refresh)
    }

    override fun setOnRefreshClickListener(listener: OnClickListener) {
        mActionBarView.setOnRefreshClickListener(listener)
    }

    override fun setOnNavigationListener(onNavigationListener: OnNavigationListener) {
        mActionBarView.setOnNavigationListener(onNavigationListener)
    }

    override fun clearListNavigation() {
        mActionBarView.clearListNavigation()
    }

    override fun clearActionMenus() {
        mActionBarView.clearActions()
    }

    override fun clearActionTabs() {
        mActionBarView.titleVisibility = View.VISIBLE
        mActionBarView.clearActionTabs()
    }

    override fun setCustomView(view: View) {
        mActionBarView.setCustomView(view)
    }

    override fun removeCustomView() {
        mActionBarView.removeCustomView()
    }

    override fun <T : View> findViewById(@IdRes id: Int): T {
        return mActionBarView.findViewById(id)
    }
}