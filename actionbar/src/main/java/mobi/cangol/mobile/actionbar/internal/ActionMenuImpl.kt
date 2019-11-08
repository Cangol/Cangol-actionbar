package mobi.cangol.mobile.actionbar.internal

import android.view.View
import mobi.cangol.mobile.actionbar.ActionMenu
import mobi.cangol.mobile.actionbar.ActionMenuItem
import mobi.cangol.mobile.actionbar.ActionTabItem
import mobi.cangol.mobile.actionbar.view.ActionMenuView
import mobi.cangol.mobile.actionbar.view.ActionMenuView.OnActionClickListener

/**
 * @author Cangol
 */
class ActionMenuImpl(private val mActionMenuView: ActionMenuView) : ActionMenu() {
    private var mActions: MutableList<ActionMenuItem> = mutableListOf()

    init {
        this.mActionMenuView.setActionMenu(this)
    }

    override fun setActions(actions: MutableList<ActionMenuItem>) {
        mActions=actions
        mActionMenuView.addActions(mActions)
    }

    override fun getActions(): MutableList<ActionMenuItem> {
        return mActions
    }

    override fun addMenu(id: Int, text: Int, drawable: Int, show: Int): ActionMenuItem {
        val item = ActionMenuItem(id, text, drawable, show)
        mActions.add(item)
        mActionMenuView.addAction(item)
        return item
    }

    override fun clear() {
        mActions.clear()
        mActionMenuView.removeAllActions()

    }

    override fun size(): Int {
        return mActions.size
    }

    override fun getAction(index: Int): ActionMenuItem {
        return mActions[index]
    }

    override fun setOnActionClickListener(onActionClickListener: OnActionClickListener) {
        mActionMenuView.setOnActionClickListener(onActionClickListener)

    }

    override fun getActionMenuItemView(id: Int): View {
        return mActionMenuView.findViewById(id)
    }

}
