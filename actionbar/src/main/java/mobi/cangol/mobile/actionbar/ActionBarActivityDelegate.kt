package mobi.cangol.mobile.actionbar

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.FrameLayout
import android.widget.RelativeLayout
import mobi.cangol.mobile.actionbar.internal.ActionBarImpl
import mobi.cangol.mobile.actionbar.view.ActionBarView
import mobi.cangol.mobile.actionbar.view.SearchView
import java.util.*


/**
 * @author Cangol
 */
class ActionBarActivityDelegate(private val mActivity: ActionBarActivity) {
    private var mContainerView: ViewGroup? = null
    private var mActionBar: ActionBar? = null
    private var mContentView: FrameLayout? = null
    private var mMaskView: FrameLayout? = null
    private var mSearchView: SearchView? = null
    private var mActionbarOverlay: Boolean = false

    fun onCreate() {
        mContainerView = LayoutInflater.from(mActivity).inflate(R.layout.actionbar_activity_main, null) as ViewGroup
        mContentView = mContainerView?.findViewById(R.id.actionbar_content_view)
        mMaskView = mContainerView?.findViewById(R.id.actionbar_mask_view)
        mActionBar = ActionBarImpl(mContainerView?.findViewById(R.id.actionbar_view) as ActionBarView)
    }

    fun isActionbarOverlay(): Boolean {
        return mActionbarOverlay
    }

    fun setActionbarOverlay(mActionbarOverlay: Boolean) {
        this.mActionbarOverlay = mActionbarOverlay
        if (mActionbarOverlay) {
            mContentView?.setPadding(0, 0, 0, 0)
        } else {
            mContentView?.setPadding(0, mActivity.resources.getDimensionPixelSize(R.dimen.actionbar_height), 0, 0)
        }
    }

    fun getCustomActionBar(): ActionBar {
        return mActionBar!!
    }

    fun setBackgroundColor(color: Int) {
        mContainerView?.setBackgroundColor(color)
    }

    fun setBackgroundResource(resId: Int) {
        mContainerView?.setBackgroundResource(resId)
    }

    fun onPostCreate(savedInstanceState: Bundle?) {
        attachToActivity(mActivity, mContainerView)
        if (savedInstanceState != null) {
            mActionBar?.setTitle(savedInstanceState.getCharSequence("ActionBar.title", ""))

            mActionBar?.clearListNavigation()
            mActionBar?.setListNavigation(savedInstanceState.getStringArray("ActionBar.navs"))

            mActionBar?.clearActionMenus()
            mActionBar?.addMenus(savedInstanceState.getParcelableArrayList<ActionMenuItem>("ActionBar.menus"))

            mActionBar?.clearActionTabs()
            mActionBar?.setTabs(savedInstanceState.getParcelableArrayList<ActionTabItem>("ActionBar.tabs"))
            mActionBar?.getActionTab()?.setTabSelected(savedInstanceState.getInt("ActionBar.tabs.selected"))

        } else {
            mActionBar?.getActionMenu()?.let { mActivity.onMenuActionCreated(it) }
        }

        if (mActionBar?.getTabs()?.isNullOrEmpty() == false) {
            mActionBar?.setTitleVisibility(View.GONE)
        } else {
            mActionBar?.setTitleVisibility(View.VISIBLE)
        }
    }

    private fun attachToActivity(activity: Activity, layout: View?) {
        // get the window background
        val a = activity.theme.obtainStyledAttributes(intArrayOf(android.R.attr.windowBackground))
        val background = a.getResourceId(0, 0)
        a.recycle()

        val decor = activity.window.decorView as ViewGroup
        val decorChild = decor.getChildAt(0) as ViewGroup
        if (decorChild.background != null) {
            mContainerView?.background=decorChild.background
            decorChild.background=null
        } else {
            if (mContainerView?.background == null)
                mContainerView?.setBackgroundResource(background)
        }
        decor.removeView(decorChild)
        decorChild.fitsSystemWindows = false
        decor.addView(layout, 0)
        setContent(decorChild)
    }

    private fun setContent(view: View) {
        setContentView(view)
    }

    fun setContentView(v: View) {
        mContentView?.removeAllViews()
        mContentView?.addView(v)
    }

    fun setContentView(v: View, params: LayoutParams) {
        mContentView?.removeAllViews()
        mContentView?.addView(v, params)
    }

    fun <T : View> findViewById(id: Int): T? {
        return mContainerView?.findViewById(id)
    }

    fun onKeyUp(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.isTracking && !event.isCanceled) {
            if (mSearchView != null) {
                stopSearchMode()
                return true
            } else {
                return mActionBar?.onBackPressed() ?: false
            }
        }
        return false
    }

    fun onSaveInstanceState(outState: Bundle) {
        outState.putCharSequence("ActionBar.title", mActionBar?.getTitle())
        outState.putStringArray("ActionBar.navs", mActionBar?.getListNavigation())
        outState.putParcelableArrayList("ActionBar.menus", mActionBar?.getMenus() as ArrayList<out Parcelable>)
        outState.putParcelableArrayList("ActionBar.tabs", mActionBar?.getTabs() as ArrayList<out Parcelable>)
        outState.putInt("ActionBar.tabs.selected", mActionBar?.getActionTab()?.getTabSelected()
                ?: 0)
    }

    fun setTitle(resId: Int) {
        mActionBar?.setTitle(resId)
    }

    fun setTitle(title: CharSequence) {
        mActionBar?.setTitle(title)
    }

    fun getMaskView(): FrameLayout {
        return mMaskView!!
    }

    fun displayMaskView(show: Boolean) {
        mMaskView?.visibility = if (show) View.VISIBLE else View.GONE
    }

    fun startSearchMode(): SearchView? {
        val layoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT)
        mSearchView = SearchView(this.mActivity)
        val aIndex = mContainerView?.indexOfChild(mContainerView?.findViewById(R.id.actionbar_view))
                ?: 0
        mContainerView?.addView(mSearchView, aIndex + 1, layoutParams)
        mSearchView?.show()
        return mSearchView
    }

    fun stopSearchMode() {
        if (mSearchView != null) {
            mContainerView?.removeView(mSearchView)
            mSearchView?.hide()
            mSearchView = null
        }
    }

    fun isActionbarShow(): Boolean {
        return mActionBar?.isShow()!!
    }

    fun setActionbarShow(show: Boolean) {
        mActionBar?.setShow(show)
    }

    fun setActionbarShadow(shadow: Boolean) {
        if (shadow) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mContainerView?.findViewById<View>(R.id.actionbar_view)?.elevation = 1.5f * mActivity.resources.displayMetrics.density
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mContainerView?.findViewById<View>(R.id.actionbar_view)?.elevation = 0f
            }
        }
    }

    fun setActionbarShadow(shadow: Boolean, elevation: Float) {
        if (shadow) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mContainerView?.findViewById<View>(R.id.actionbar_view)?.elevation = elevation * mActivity.resources.displayMetrics.density
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mContainerView?.findViewById<View>(R.id.actionbar_view)?.elevation = elevation
            }
        }
    }

}
