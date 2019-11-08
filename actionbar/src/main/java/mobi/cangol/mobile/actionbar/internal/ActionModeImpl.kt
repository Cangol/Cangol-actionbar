package mobi.cangol.mobile.actionbar.internal

import android.view.View
import mobi.cangol.mobile.actionbar.ActionMenuItem
import mobi.cangol.mobile.actionbar.ActionMode
import mobi.cangol.mobile.actionbar.view.ActionMenuView.OnActionClickListener
import mobi.cangol.mobile.actionbar.view.ActionModeView


/**
 * @author Cangol
 */
class ActionModeImpl(private val mActionModeView: ActionModeView) : ActionMode() {
    private var mActionModeCallback: Callback? = null
    private var isActionMode: Boolean = false
    init {
        mActionModeView.setActionMode(this)
    }

    override fun isActionMode(): Boolean {
       return isActionMode
    }

    override fun setTitle(title: CharSequence) {
        mActionModeView.setTitle(title)
    }
    override fun setTitle(resId: Int) {
        mActionModeView.setTitle(resId)
    }

    override fun finish() {
        mActionModeView.getActionMenu()?.clear()
        mActionModeView.visibility = View.GONE
        mActionModeCallback?.onDestroyActionMode(this)
        isActionMode = false
    }

    override fun start(callback: Callback) {
        mActionModeView.visibility = View.VISIBLE
        mActionModeCallback = callback
        mActionModeCallback?.onCreateActionMode(this, mActionModeView.getActionMenu()!!)
        mActionModeView.setOnActionClickListener(object : OnActionClickListener {

            override fun onActionClick(action: ActionMenuItem): Boolean {
                return mActionModeCallback?.onActionItemClicked(this@ActionModeImpl, action)?:false
            }

        })
        isActionMode = true
    }
}
