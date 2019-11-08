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

    override var tabs: MutableList<ActionTabItem>
        get() = mTabs
        set(tabs) {
            mTabs = tabs
            mActionTabView.addTabs(tabs)
        }

    override var tabSelected: Int
        get() = mActionTabView.tabSelected
        set(id) {
            mActionTabView.tabSelected = id
        }

    init {
        mActionTabView.setActionTab(this)
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
