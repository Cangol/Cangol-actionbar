package mobi.cangol.mobile.actionbar.internal

import android.widget.TextView
import mobi.cangol.mobile.actionbar.ActionTab
import mobi.cangol.mobile.actionbar.ActionTabItem
import mobi.cangol.mobile.actionbar.view.ActionTabView
import mobi.cangol.mobile.actionbar.view.ActionTabView.OnTabSelectedListener

/**
 * @author Cangol
 */
class ActionTabImpl(private val mActionTabView: ActionTabView) : ActionTab() {
    private var mTabs: MutableList<ActionTabItem> = mutableListOf()
    init {
        mActionTabView.setActionTab(this)
    }

    override fun getTabSelected(): Int {
        return mActionTabView.getSelectTabId()
    }

    override fun setTabSelected(id: Int){
        mActionTabView.selectTab(id)
    }

    override fun setTabs(tabs: MutableList<ActionTabItem>) {
        if(tabs.isNotEmpty()){
            mTabs = tabs
            mActionTabView.addTabs(tabs)
        }
    }

    override fun getTabs(): MutableList<ActionTabItem> {
        return mTabs
    }

    override fun removeAllTabs() {
        mTabs.clear()
        mActionTabView.removeTabSelectedListener()
        mActionTabView.removeAllTabs()
    }

    override fun setOnTabSelectedListener(onTabSelectedListener: OnTabSelectedListener) {
        mActionTabView.setOnTabSelectedListener(onTabSelectedListener)
    }

    override fun getTabView(id: Int): TextView {
        return mActionTabView.findViewById(id)
    }

    override fun newTab(id: Int, title: String, selected: Int): ActionTabItem {
        val item = ActionTabItem(id, title, selected)
        mTabs.add(item)
        mActionTabView.addTabItem(item)
        return item
    }


}
