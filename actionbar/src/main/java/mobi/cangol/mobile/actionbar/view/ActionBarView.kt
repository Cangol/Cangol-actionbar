package mobi.cangol.mobile.actionbar.view

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import mobi.cangol.mobile.actionbar.*
import mobi.cangol.mobile.actionbar.ActionMode.Callback
import mobi.cangol.mobile.actionbar.internal.ActionMenuImpl
import mobi.cangol.mobile.actionbar.internal.ActionModeImpl
import mobi.cangol.mobile.actionbar.internal.ActionTabImpl


/**
 * @author Cangol
 */
class ActionBarView : RelativeLayout {
    private var mInflater: LayoutInflater? = null
    private var mLeftMenuLayout: LinearLayout? = null
    private var mIndicator: ImageView? = null
    private var mTitleLayout: LinearLayout? = null
    private var mTitleView: TextView? = null
    private var mPopupMenu: PopupWindow? = null
    private var mRefreshView: ImageView? = null
    private var mActionMenu: ActionMenu? = null
    private var mActionMode: ActionMode? = null
    private var mActionTab: ActionTab? = null
    private var mCustomLayout: FrameLayout? = null
    private var mActionBarActivity: ActionBarActivity? = null
    private var mDrawerArrowDrawable: DrawerArrowDrawable? = null
    private var mIsCustomHomeAsUpIndicator: Boolean = false
    private var mDisplayShowHomeEnabled: Boolean = false
    private var mHomeId: Int = 0
    private var mUpId: Int = 0
    private var mListNavigation: Array<String>? = null
    private var mOnNavigationListener: OnNavigationListener? = null
    private var mOnRefreshClickListener: OnClickListener? = null

    fun addActions(actions: MutableList<ActionMenuItem>) {
        mActionMenu?.setActions(actions)
    }

    fun getActions(): MutableList<ActionMenuItem> {
        return mActionMenu?.getActions()!!
    }

    fun setTabs(tabs: MutableList<ActionTabItem>) {
        if (tabs.isNotEmpty()) {
            setTitleVisibility(View.GONE)
            mActionTab?.setTabs(tabs)
        }
    }

    fun getTabs(): MutableList<ActionTabItem> {
        return mActionTab?.getTabs()!!
    }

    fun setTitleVisibility(visibility: Int) {
        mTitleLayout?.visibility = visibility
    }

    fun getTitleVisibility(): Int {
        return mTitleLayout?.visibility!!
    }

    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context)
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView(context)
    }

    fun initView(context: Context) {
        mActionBarActivity = context as ActionBarActivity
        mInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        mDrawerArrowDrawable = DrawerArrowDrawable(context.getResources(), false)

        val typedValue = TypedValue()
        context.getTheme().resolveAttribute(R.attr.actionbar_indicator, typedValue, true)
        mDrawerArrowDrawable?.setStrokeColor(typedValue.data)


        mInflater?.inflate(R.layout.actionbar_layout, this, true)
        mLeftMenuLayout = this.findViewById(R.id.actionbar_left_menus)
        mIndicator = this.findViewById(R.id.actionbar_main_indicator)
        mTitleLayout = this.findViewById(R.id.actionbar_main_title_layout)
        mTitleView = this.findViewById(R.id.actionbar_main_title)
        mCustomLayout = this.findViewById(R.id.actionbar_main_custom_layout)
        mActionTab = ActionTabImpl(this.findViewById(R.id.actionbar_main_tabview))
        mActionMenu = ActionMenuImpl(this.findViewById(R.id.actionbar_main_menu))
        mActionMode = ActionModeImpl(this.findViewById(R.id.actionbar_main_mode))
        setTitle(context.getApplicationInfo().name)
        initListeners()
    }

    private fun initListeners() {
        if (!mIsCustomHomeAsUpIndicator)
            mDrawerArrowDrawable?.setParameter(0f)
        mIndicator?.setImageDrawable(mDrawerArrowDrawable)
        mIndicator?.setOnClickListener {
            mActionBarActivity?.onSupportNavigateUp()
        }
        mActionMenu?.setOnActionClickListener(object : ActionMenuView.OnActionClickListener {

            override fun onActionClick(action: ActionMenuItem): Boolean {
                return mActionBarActivity?.onMenuActionSelected(action) ?: false
            }

        })
    }

    fun getListNavigation(): Array<String>? {
        return mListNavigation
    }

    fun setListNavigation(listNavigation: Array<String>?) {
        this.mListNavigation = listNavigation
    }

    fun clearListNavigation() {
        mTitleView?.setCompoundDrawables(null, null, null, null)
        mTitleView?.setOnClickListener(null)
        this.mOnNavigationListener = null
    }

    fun setOnNavigationListener(onNavigationListener: OnNavigationListener) {
        this.mOnNavigationListener = onNavigationListener
        var inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val adapter = object : BaseAdapter() {

            override fun getCount(): Int {
                return mListNavigation!!.size
            }

            override fun getItem(position: Int): String {
                return mListNavigation!![position]
            }

            override fun getItemId(position: Int): Long {
                return position.toLong()
            }

            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
                var itemView = convertView
                if (itemView == null) {
                    itemView = inflater.inflate(R.layout.actionbar_navigation_list_item, parent, false)
                }
                val labelView = itemView?.findViewById<TextView>(R.id.actionbar_navigation_item_text)
                labelView?.text = mListNavigation!![position]
                return itemView
            }

        }
        initNavigationPopupMenu(mActionBarActivity!!, adapter, mOnNavigationListener)
    }

    private fun initNavigationPopupMenu(context: Context, adapter: BaseAdapter, onNavigationListener: OnNavigationListener?) {
        val popuLayout = mInflater?.inflate(R.layout.actionbar_navigation_list, null)
        val listView = popuLayout?.findViewById<View>(R.id.actionbar_popup_navigation_list) as ListView
        listView.adapter = adapter
        val width = (200 * context.resources.displayMetrics.density).toInt()
        mPopupMenu = PopupWindow(popuLayout, width, RelativeLayout.LayoutParams.WRAP_CONTENT, true)
        mPopupMenu?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        mPopupMenu?.setOnDismissListener {
            val typedValue = TypedValue()
            context.theme.resolveAttribute(R.attr.actionbar_dropdown, typedValue, true)
            val imgV = ContextCompat.getDrawable(context,typedValue.resourceId)
            imgV?.setBounds(0, 0, imgV.intrinsicWidth, imgV.intrinsicHeight)
            mTitleView?.setCompoundDrawables(null, null, imgV, null)
        }
        listView.onItemClickListener = OnItemClickListener { _, _, position, id ->
            onNavigationListener?.onNavigationItemSelected(position, id)
            mPopupMenu?.dismiss()
        }
        val typedValue = TypedValue()
        context.theme.resolveAttribute(R.attr.actionbar_dropdown, typedValue, true)
        val imgV = ContextCompat.getDrawable(context,typedValue.resourceId)
        imgV?.setBounds(0, 0, imgV.intrinsicWidth, imgV.intrinsicHeight)
        mTitleView?.setCompoundDrawables(null, null, imgV, null)
        mTitleView?.setOnClickListener {
            if (!mPopupMenu!!.isShowing) {
                val value = TypedValue()
                context.theme.resolveAttribute(R.attr.actionbar_dropup, value, true)
                val img = ContextCompat.getDrawable(context,value.resourceId)
                img?.setBounds(0, 0, img.intrinsicWidth, img.intrinsicHeight)
                mTitleView?.setCompoundDrawables(null, null, img, null)

                val xoff = -(width - mTitleView!!.width) / 2
                if (mTitleView!!.gravity == Gravity.CENTER) {
                    mPopupMenu?.showAsDropDown(mTitleView, xoff, 0)
                } else {
                    mPopupMenu?.showAsDropDown(mTitleView, 0, 0)
                }
            }
        }
    }

    fun setCustomHomeAsUpIndicator(homeId: Int, upId: Int) {
        mIsCustomHomeAsUpIndicator = true
        mHomeId = homeId
        mUpId = upId
        mIndicator?.setImageResource(homeId)
    }

    fun resetCustomHomeAsUpIndicator() {
        mIsCustomHomeAsUpIndicator = false
        mHomeId = 0
        mUpId = 0
        mDrawerArrowDrawable?.setParameter(0f)
        mIndicator?.setImageDrawable(mDrawerArrowDrawable)
    }

    fun setDisplayShowHomeEnabled(show: Boolean) {
        mDisplayShowHomeEnabled = show
        mIndicator?.visibility = if (show) View.VISIBLE else View.GONE
    }

    fun displayHomeIndicator() {
        if (!mIsCustomHomeAsUpIndicator) {
            mDrawerArrowDrawable?.setParameter(0f)
            mIndicator?.setImageDrawable(mDrawerArrowDrawable)
        } else {
            mIndicator?.setImageResource(mHomeId)
        }
        mIndicator?.visibility = if (mDisplayShowHomeEnabled) View.VISIBLE else View.GONE
    }

    fun hideHomeAsUpIndicator() {
        mIndicator?.visibility = View.GONE
    }

    fun displayUpIndicator() {
        if (!mIsCustomHomeAsUpIndicator) {
            mDrawerArrowDrawable?.setParameter(1f)
            mIndicator?.setImageDrawable(mDrawerArrowDrawable)
        } else {
            mIndicator?.setImageResource(mUpId)
        }
        mIndicator?.visibility = View.VISIBLE
    }

    fun displayIndicator(slideOffset: Float) {
        if (!mIsCustomHomeAsUpIndicator) {
            if (slideOffset >= .995) {
                mDrawerArrowDrawable?.setFlip(true)
            } else if (slideOffset <= .005) {
                mDrawerArrowDrawable?.setFlip(false)
            }
            mDrawerArrowDrawable?.setParameter(slideOffset)
            mIndicator?.setImageDrawable(mDrawerArrowDrawable)
        } else {
            if (slideOffset >= .995) {
                mIndicator?.setImageResource(mUpId)
            } else if (slideOffset <= .005) {
                mIndicator?.setImageResource(mHomeId)
            }
        }
        mIndicator?.visibility = View.VISIBLE
    }

    fun setIndicatorColor(color: Int) {
        if (!mIsCustomHomeAsUpIndicator)
            mDrawerArrowDrawable?.setStrokeColor(color)
    }

    fun setLeftMenu(id: Int, text: Int, drawable: Int, listener: OnClickListener) {
        mLeftMenuLayout?.visibility = View.VISIBLE
        mLeftMenuLayout?.removeAllViews()
        val layoutParams = mLeftMenuLayout?.layoutParams as LayoutParams
        if (mIndicator?.visibility == View.VISIBLE) {
            layoutParams.addRule(ALIGN_PARENT_LEFT, 0)
            layoutParams.addRule(RIGHT_OF, R.id.actionbar_main_indicator)
        } else if (mRefreshView != null && mRefreshView?.id == R.id.actionbar_main_refresh_left && mRefreshView?.visibility == View.VISIBLE) {
            layoutParams.addRule(ALIGN_PARENT_LEFT, 0)
            layoutParams.addRule(RIGHT_OF, R.id.actionbar_main_refresh_left)
        } else {
            layoutParams.addRule(ALIGN_PARENT_LEFT, 1)
        }

        if (drawable != -1) {
            val view = mInflater?.inflate(R.layout.actionbar_item_icon, null, false)

            val labelView = view?.findViewById<View>(R.id.actionbar_item) as ImageView
            labelView.setImageResource(drawable)
            view.id = id
            view.tag = context.getString(text)
            labelView.setOnClickListener(listener)
            labelView.setOnLongClickListener {
                if (text != -1) {
                    var toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.LEFT or Gravity.TOP, view.left, view.bottom)
                    toast.show()
                    true
                } else {
                    false
                }
            }
            mLeftMenuLayout?.addView(view, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
        } else {
            val view = mInflater?.inflate(R.layout.actionbar_item_text, null, false)

            val labelView = view?.findViewById<View>(R.id.actionbar_item) as TextView
            labelView.setText(text)
            view.id = id
            view.tag = context.getString(text)
            labelView.setOnClickListener(listener)
            labelView.setOnLongClickListener {
                if (text != -1) {
                    var toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.LEFT or Gravity.TOP, view.left, view.bottom)
                    toast.show()
                    true
                } else {
                    false
                }
            }
            mLeftMenuLayout?.addView(view, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
        }


    }

    fun clearLeftMenu() {
        mLeftMenuLayout?.getChildAt(0)?.setOnClickListener(null)
        mLeftMenuLayout?.removeAllViews()
        mLeftMenuLayout?.visibility = View.GONE
    }

    fun getTitle(): CharSequence {
        return mTitleView?.text!!
    }

    fun setTitle(resId: Int) {
        mTitleView?.setText(resId)
    }

    fun setTitle(title: CharSequence?) {
        mTitleView?.text = title
    }

    fun setTitleGravity(gravity: Int) {
        mTitleLayout?.gravity = gravity
        mTitleView?.gravity = gravity
    }

    fun getTitleGravity(): Int {
        return mTitleView?.visibility!!
    }

    fun setOnTitleClickListener(listener: OnClickListener) {
        mTitleView?.setOnClickListener(listener)
    }

    fun startActionMode(callback: Callback): ActionMode {
        if (!mActionMode?.isActionMode()!!) {
            mActionMode?.start(callback)
        }
        return mActionMode!!
    }

    fun stopActionMode() {
        if (mActionMode?.isActionMode()!!) {
            mActionMode?.finish()
        }
    }

    fun getActionMenu(): ActionMenu {
        return mActionMenu!!
    }

    fun clearActions() {
        mActionMenu?.clear()
    }

    fun getActionTab(): ActionTab {
        return mActionTab!!
    }

    fun clearActionTabs() {
        mActionTab?.removeAllTabs()
        setTitleVisibility(View.VISIBLE)
    }

    fun onBackPressed(): Boolean {
        if (mActionMode?.isActionMode()!!) {
            stopActionMode()
            return true
        }

        return false
    }

    fun setCustomView(view: View) {
        mCustomLayout?.removeAllViews()
        mCustomLayout?.addView(view)
        mTitleLayout?.visibility = View.GONE
        mActionTab?.removeAllTabs()
    }

    fun removeCustomView() {
        mCustomLayout?.removeAllViews()
        mTitleLayout?.visibility = View.VISIBLE
    }

    fun enableRefresh(enable: Boolean, gravity: Int = Gravity.RIGHT) {
        if (mRefreshView != null) {
            mRefreshView?.clearAnimation()
            mRefreshView?.visibility = View.GONE
            mRefreshView?.setOnClickListener(null)
            if (mRefreshView?.id == R.id.actionbar_main_refresh_left) {
                val view = findViewById<View>(R.id.actionbar_left_menus)
                val layoutParams = view.layoutParams as LayoutParams
                if (mIndicator?.visibility == View.VISIBLE) {
                    layoutParams.addRule(RIGHT_OF, R.id.actionbar_main_indicator)
                } else {
                    layoutParams.addRule(ALIGN_PARENT_LEFT, 1)
                }
            } else {
                val view = findViewById<View>(R.id.actionbar_main_menu)
                val layoutParams = view.layoutParams as LayoutParams
                layoutParams.addRule(ALIGN_PARENT_RIGHT, 1)
            }
            mRefreshView = null
        }
        mRefreshView = this.findViewById(if (gravity == Gravity.LEFT) R.id.actionbar_main_refresh_left else R.id.actionbar_main_refresh_right)
        val view = findViewById<View>(if (gravity == Gravity.LEFT) R.id.actionbar_left_menus else R.id.actionbar_main_menu)
        val layoutParams = view.layoutParams as LayoutParams
        if (enable) {
            if (gravity == Gravity.LEFT) {
                layoutParams.addRule(ALIGN_PARENT_LEFT, 0)
                layoutParams.addRule(RIGHT_OF, R.id.actionbar_main_refresh_left)
            } else {
                layoutParams.addRule(ALIGN_PARENT_RIGHT, 0)
            }
            mRefreshView?.visibility = View.VISIBLE
            mRefreshView?.setOnClickListener(mOnRefreshClickListener)
        } else {
            if (gravity == Gravity.LEFT) {
                if (mIndicator?.visibility == View.VISIBLE) {
                    layoutParams.addRule(RIGHT_OF, R.id.actionbar_main_indicator)
                } else {
                    layoutParams.addRule(ALIGN_PARENT_LEFT, 1)
                }
            } else {
                layoutParams.addRule(ALIGN_PARENT_RIGHT, 1)
            }

            mRefreshView?.clearAnimation()
            mRefreshView?.visibility = View.GONE
            mRefreshView?.setOnClickListener(null)
            mOnRefreshClickListener = null
            mRefreshView = null
        }

    }

    fun setOnRefreshClickListener(listener: OnClickListener) {
        mOnRefreshClickListener = listener
        mRefreshView?.setOnClickListener(mOnRefreshClickListener)
    }

    fun refreshing(refresh: Boolean) {
        if (mRefreshView?.visibility == View.VISIBLE) {
            val anim = RotateAnimation(0.0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
            anim.interpolator = LinearInterpolator()
            anim.repeatCount = Animation.INFINITE
            anim.duration = resources.getInteger(android.R.integer.config_mediumAnimTime).toLong()
            if (refresh) {
                mRefreshView?.startAnimation(anim)
            } else {
                mRefreshView?.clearAnimation()
            }
        }
    }
}