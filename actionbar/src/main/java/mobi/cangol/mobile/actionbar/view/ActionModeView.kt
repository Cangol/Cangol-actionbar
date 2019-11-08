package mobi.cangol.mobile.actionbar.view

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import mobi.cangol.mobile.actionbar.ActionMenu
import mobi.cangol.mobile.actionbar.ActionMode
import mobi.cangol.mobile.actionbar.R
import mobi.cangol.mobile.actionbar.internal.ActionMenuImpl
import mobi.cangol.mobile.actionbar.view.ActionMenuView.OnActionClickListener


/**
 * @author Cangol
 */
class ActionModeView : LinearLayout {
    private var mTitleText: TextView? = null
    private var mActionMenu: ActionMenu? = null
    private var mActionMode: ActionMode? = null

    constructor(context: Context) : super(context) {
        initViews(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initViews(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initViews(context)
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        initViews(context)
    }

    private fun initViews(context: Context) {
        val mInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mInflater.inflate(R.layout.actionbar_action_mode, this)

        mActionMenu = ActionMenuImpl(this.findViewById(R.id.actionbar_mode_menu))
        val mDoneButton = this.findViewById(R.id.actionbar_mode_done) as ImageView
        mTitleText = this.findViewById(R.id.actionbar_mode_title)
        mDoneButton.setOnClickListener { mActionMode?.finish() }
    }

    fun getTitle(): CharSequence {
        return mTitleText?.text.toString()
    }

    fun setTitle(resId: Int) {
        mTitleText?.setText(resId)
    }

    fun setTitle(title: CharSequence) {
        mTitleText?.text = title
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        super.onTouchEvent(ev)
        return true
    }

    fun setOnActionClickListener(onActionClickListener: OnActionClickListener) {
        mActionMenu?.setOnActionClickListener(onActionClickListener)
    }

    fun setActionMode(mActionMode: ActionMode) {
        this.mActionMode = mActionMode
    }
    fun getActionMenu(): ActionMenu {
        return mActionMenu!!
    }
}
