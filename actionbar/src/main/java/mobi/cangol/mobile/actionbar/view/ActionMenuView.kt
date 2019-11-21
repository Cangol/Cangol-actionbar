package mobi.cangol.mobile.actionbar.view

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.View.OnLongClickListener
import android.widget.*
import mobi.cangol.mobile.actionbar.ActionMenu
import mobi.cangol.mobile.actionbar.ActionMenuItem
import mobi.cangol.mobile.actionbar.R

/**
 * @author Cangol
 */
class ActionMenuView : LinearLayout, OnClickListener, OnLongClickListener {

    private var mInflater: LayoutInflater? = null
    private var mActionsView: LinearLayout? = null
    private var mPopupActionsView: LinearLayout? = null
    private var mMoreButton: ImageView? = null
    private var mPopuMenu: PopupWindow? = null
    private var mActionMenu: ActionMenu? = null
    private var mOnActionClickListener: OnActionClickListener? = null
    private var mShowActions = 0

    constructor(context: Context) : super(context) {
        initViews(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initViews(context)
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        initViews(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initViews(context)
    }

    fun setActionMenu(mActionMenu: ActionMenu) {
        this.mActionMenu = mActionMenu
    }

    private fun initViews(context: Context) {
        mInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mInflater?.inflate(R.layout.actionbar_menu_view, this, true)
        mActionsView = this.findViewById(R.id.actionbar_menu_actions)
        mMoreButton = this.findViewById(R.id.actionbar_menu_more)
        initPopupMenu(context)
    }

    private fun initPopupMenu(context: Context) {
        val popuLayout = mInflater?.inflate(R.layout.actionbar_popup_actions, null)
        mPopupActionsView = popuLayout?.findViewById(R.id.actionbar_popup_actions)
        val width = (180 * context.resources.displayMetrics.density).toInt()
        mPopuMenu = PopupWindow(popuLayout, width, LayoutParams.WRAP_CONTENT, true)
        mPopuMenu?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        mMoreButton?.setOnClickListener { mPopuMenu?.showAsDropDown(mMoreButton) }
    }

    override fun onClick(view: View) {
        val tag = view.tag
        if (tag is ActionMenuItem) {
            if (mPopuMenu?.isShowing==true) {
                mPopuMenu?.dismiss()
            }
            mOnActionClickListener?.onActionClick(tag)
        }
    }

    override fun onLongClick(view: View): Boolean {
        val tag = view.tag
        if (tag is ActionMenuItem) {

            var toast: Toast? = null
            if (tag.text != -1)
                toast = Toast.makeText(this.context.applicationContext, tag.text, Toast.LENGTH_SHORT)

            val left = (if (mActionMenu!!.size() - mShowActions > 0) mMoreButton!!.width else 0) + view.width * mShowActions - view.left

            if (toast != null) {
                toast.setGravity(Gravity.RIGHT or Gravity.TOP, left, this.bottom)
                toast.show()
            }
        }
        return true
    }

    fun addActions(actions: List<ActionMenuItem>) {
        for (menuItem in actions) {
            addAction(menuItem)
        }
    }

    /**
     * Adds a new [ActionMenuItem].
     *
     * @param action the action to add
     */
    fun addAction(action: ActionMenuItem) {
        if (action.isShow) {
            if (mShowActions < 1) {
                mActionsView?.addView(if (action.isIcon) inflateActionIcon(action) else inflateActionText(action))
                mMoreButton?.visibility = View.GONE
                mShowActions++
            } else if (mShowActions < 2 && mActionMenu?.size() == 2) {
                mActionsView?.addView(if (action.isIcon) inflateActionIcon(action) else inflateActionText(action))
                mMoreButton?.visibility = View.GONE
                mShowActions++
            } else {
                if (mShowActions == 2) {
                    mActionsView?.removeViewAt(1)
                    mPopupActionsView?.addView(mActionMenu?.getAction(2)?.let { inflateMenuAction(it) })
                    mMoreButton?.visibility = View.VISIBLE
                }
                mPopupActionsView?.addView(inflateMenuAction(action))
                mMoreButton?.visibility = View.VISIBLE
            }
        } else {
            if (mShowActions == 2) {
                mActionsView?.removeViewAt(1)
                mPopupActionsView?.addView(mActionMenu?.getAction(2)?.let { inflateMenuAction(it) })
                mMoreButton?.visibility = View.VISIBLE
            }
            mPopupActionsView?.addView(inflateMenuAction(action))
            mMoreButton?.visibility = View.VISIBLE

        }
    }

    /**
     * Removes all action views from this action bar
     */
    fun removeAllActions() {
        mShowActions = 0
        mActionsView?.removeAllViews()
        mPopupActionsView?.removeAllViews()
        mMoreButton?.visibility = View.GONE
    }


    private fun inflateActionIcon(action: ActionMenuItem): View {
        val view = mInflater?.inflate(R.layout.actionbar_item_icon, mActionsView, false)

        val labelView = view?.findViewById<View>(R.id.actionbar_item) as ImageView
        labelView.setImageResource(action.drawable)
        view.id = action.id
        view.tag = action
        view.setOnClickListener(this)
        view.setOnLongClickListener(this)
        return view
    }

    private fun inflateActionText(action: ActionMenuItem): View {
        val view = mInflater?.inflate(R.layout.actionbar_item_text, mActionsView, false)

        val labelView = view?.findViewById(R.id.actionbar_item) as TextView
        labelView.setText(action.text)

        view.id = action.id
        view.tag = action
        view.setOnClickListener(this)
        view.setOnLongClickListener(this)
        return view
    }

    private fun inflateMenuAction(action: ActionMenuItem): View {
        val view = mInflater?.inflate(R.layout.actionbar_popup_item, mPopupActionsView, false)
        val labelView = view?.findViewById(R.id.actionbar_popup_item_text) as TextView
        /**
         * if(action.getDrawable()>0){
         * Drawable img=getResources().getDrawable(action.getDrawable());
         * img.setBounds(0, 0, img.getIntrinsicWidth(), img.getIntrinsicHeight());
         * labelView.setCompoundDrawables(img, null, null, null);
         * } */
        labelView.setText(action.text)

        view.id = action.id
        view.tag = action
        view.setOnClickListener(this)
        view.setOnLongClickListener(this)
        return view
    }


    fun setOnActionClickListener(mOnActionClickListener: OnActionClickListener) {
        this.mOnActionClickListener = mOnActionClickListener
    }

    interface OnActionClickListener {

        fun onActionClick(action: ActionMenuItem): Boolean
    }
}
