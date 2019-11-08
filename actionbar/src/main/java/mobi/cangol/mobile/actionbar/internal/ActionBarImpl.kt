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

    override fun isShow(): Boolean {
        return mActionBarView.visibility == View.VISIBLE
    }

    override fun setShow(show: Boolean) {
        mActionBarView.visibility = if (show) View.VISIBLE else View.GONE

    }
    override fun setTitleGravity(gravity: Int) {
        mActionBarView.setTitleGravity(gravity)
    }

    override fun getTitleGravity(): Int {
        return mActionBarView.getTitleGravity()
    }

    override fun setTitleVisibility(visibility: Int) {
        mActionBarView.setTitleVisibility(visibility)
    }

    override fun getTitleVisibility(): Int {
        return mActionBarView.getTitleVisibility()
    }

    override fun getListNavigation(): Array<String>? {
        return mActionBarView.getListNavigation()
    }

    override fun setListNavigation(navs: Array<String>?) {
        mActionBarView.setListNavigation(navs!!)
    }

    override fun getTitle():CharSequence {
        return mActionBarView.getTitle()
    }
    override fun getActionTab(): ActionTab {
        return mActionBarView.getActionTab()!!
    }

    override fun getActionMenu(): ActionMenu {
        return mActionBarView.getActionMenu()!!
    }

    override fun addMenus(actions: MutableList<ActionMenuItem>) {
        mActionBarView.addActions(actions)
    }

    override fun getMenus(): MutableList<ActionMenuItem> {
        return mActionBarView.getActions()
    }

    override fun setTabs(tabs: MutableList<ActionTabItem>) {
        mActionBarView.setTabs(tabs)
    }

    override fun getTabs(): MutableList<ActionTabItem> {
        return mActionBarView?.getTabs()!!
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
        mActionBarView.setTitleVisibility(View.VISIBLE)
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