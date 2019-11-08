package mobi.cangol.mobile.actionbar

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.AttrRes
import android.support.annotation.ColorInt
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.util.TypedValue
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.RelativeLayout
import mobi.cangol.mobile.actionbar.view.SearchView


open class ActionBarActivity : AppCompatActivity() {
    private var mDelegate: ActionBarActivityDelegate? = null
    private var mTintManager: SystemBarTintManager? = null
    private var useSystemBarTintLollipop = false
    private val statusBarHeight: Int
        get() {
            var result = 0
            val resourceId = this.resources.getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) {
                result = this.resources.getDimensionPixelSize(resourceId)
            }
            return result
        }




    /**
     * 设置全屏
     *
     * @param fullscreen
     */
    fun setFullScreen(fullscreen: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (fullscreen) {
                this.window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
            } else {
                this.window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
            }
        } else {
            var newVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)

            if (fullscreen) {
                newVisibility = newVisibility or (View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
            }
            // Set the visibility
            this.window.decorView.systemUiVisibility = newVisibility
        }
    }

    /**
     * 是否全屏
     */

    fun isFullScreen(): Boolean {
        return this.window.attributes.flags and WindowManager.LayoutParams.FLAG_FULLSCREEN == WindowManager.LayoutParams.FLAG_FULLSCREEN
    }

    /**
     * 返回actionbar是否悬浮
     *
     * @return
     */
    fun isActionbarOverlay(): Boolean {
        return mDelegate?.isActionbarOverlay()?:false
    }

    /**
     * 设置actionbar是否悬浮
     *
     * @param mActionbarOverlay
     */
    fun setActionbarOverlay(mActionbarOverlay: Boolean) {
        this.mDelegate?.setActionbarOverlay(mActionbarOverlay)
    }

    /**
     * 返回actionbar的显示
     */
    fun isActionbarShow(): Boolean {
        return mDelegate?.isActionbarShow()!!

    }

    /**
     * 设置actionbar的显示
     *
     * @param show
     */
    fun setActionbarShow(show: Boolean) {
        this.mDelegate?.setActionbarOverlay(!show)
        this.mDelegate?.setActionbarShow(show)

    }

    /**
     * 获取自定义actionbar
     *
     * @return
     */
    fun getCustomActionBar(): ActionBar {
        return mDelegate?.getCustomActionBar()!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDelegate = ActionBarActivityDelegate(this)
        mDelegate?.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            mTintManager = SystemBarTintManager(this)
        }
        setStatusBarTintColor(getThemeAttrColor(R.attr.actionbar_background))
    }

    /**
     * 在Lollipop是否使用澄侵式系统栏(状态栏和导航栏)
     *
     * @param useSystemBarTintLollipop
     */
    fun setUseSystemBarTintLollipop(useSystemBarTintLollipop: Boolean) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.useSystemBarTintLollipop = useSystemBarTintLollipop
        }
    }

    fun getAttrTypedValue(@AttrRes attr: Int): TypedValue {
        val typedValue = TypedValue()
        theme.resolveAttribute(attr, typedValue, true)
        return typedValue
    }

    @ColorInt
    fun getThemeAttrColor(@AttrRes colorAttr: Int): Int {
        val array = this.obtainStyledAttributes(null, intArrayOf(colorAttr))
        try {
            return array.getColor(0, 0)
        } finally {
            array.recycle()
        }
    }

    /**
     * 设置标题
     *
     * @param title
     */
    override fun setTitle(title: CharSequence) {
        mDelegate?.setTitle(title)
    }

    /**
     * 设置标题
     *
     * @param titleId
     */
    override fun setTitle(titleId: Int) {
        mDelegate?.setTitle(titleId)
    }

    /**
     * 设置背景颜色
     *
     * @param color
     */
    fun setBackgroundColor(color: Int) {
        mDelegate?.setBackgroundColor(color)
    }

    /**
     * 设置背景颜色
     *
     * @param resId
     */
    fun setBackgroundResource(resId: Int) {
        mDelegate?.setBackgroundResource(resId)
    }


    /**
     * 设置window背景颜色
     *
     * @param resId
     */
    fun setWindowBackground(resId: Int) {
        //替换背景
        this.window.setBackgroundDrawableResource(resId)
    }

    /**
     * 设置状态栏颜色
     *
     * @param color
     */
    fun setStatusBarTintColor(color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = color
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (useSystemBarTintLollipop) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.statusBarColor = color
            }
        } else if (!isFullScreen()) {
            mTintManager?.isStatusBarTintEnabled = true
            mTintManager?.setStatusBarTintColor(color)
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    fun setFitsSystemWindows(layoutId: Int) {
        if (layoutId == R.id.container_view) {
            findViewById<View>(R.id.container_view)?.fitsSystemWindows = true
        } else {
            findViewById<View>(R.id.container_view)?.fitsSystemWindows = false
            findViewById<View>(layoutId)?.fitsSystemWindows = true
        }
    }

    fun setStatusBarTranslucent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = Color.parseColor("#1f000000")
            setSystemUiFloatFullScreen(true)
        }
    }

    fun setStatusBarTransparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = Color.TRANSPARENT
            setSystemUiFloatFullScreen(true)
        }
    }


    fun setSystemUiFloatFullScreen(enable: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (enable) {
                findViewById<View>(R.id.container_view)?.fitsSystemWindows = false
                findViewById<View>(R.id.container_view)?.setPadding(0, 0, 0, 0)
                (findViewById<View>(R.id.actionbar_view)?.layoutParams as RelativeLayout.LayoutParams).topMargin = statusBarHeight
            } else {
                findViewById<View>(R.id.container_view)?.fitsSystemWindows = true
                findViewById<View>(R.id.container_view)?.setPadding(0, statusBarHeight, 0, 0)
                (findViewById<View>(R.id.actionbar_view)?.layoutParams as RelativeLayout.LayoutParams).topMargin = 0
            }
            val decorView = this.window.decorView
            var option = decorView.systemUiVisibility
            if (enable) {
                option = option or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            } else {
                option = option and View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN.inv()
            }

            decorView.systemUiVisibility = option

            decorView.requestApplyInsets()
        }
    }

    /**
     * 设置导航栏颜色
     *
     * @param color
     */
    fun setNavigationBarTintColor(color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.navigationBarColor = color
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (useSystemBarTintLollipop) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.navigationBarColor = color
            }
        } else if (!isFullScreen()) {
            mTintManager?.setNavigationBarTintEnabled(true)
            mTintManager?.setNavigationBarTintColor(color)
        }
    }

    /**
     * 设置状态栏字体图标颜色
     *
     * @param black
     */
    fun setStatusBarTextColor(black: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var systemUiVisibility = window.decorView.systemUiVisibility
            if (black) {
                systemUiVisibility = systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                systemUiVisibility = systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
            }
            window.decorView.systemUiVisibility = systemUiVisibility
            if (Build.BRAND == "Xiaomi" && Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                setStatusBarTextColorWithXiaomi(black)
            }
        }
    }

    private fun setStatusBarTextColorWithXiaomi(black: Boolean) {
        try {
            var darkModeFlag :Int= 0
            val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
            val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
            darkModeFlag = field.getInt(layoutParams)
            val extraFlagField = window.javaClass.getMethod("setExtraFlags", Int::class.javaPrimitiveType, Int::class.javaPrimitiveType)
            if (black) {
                extraFlagField.invoke(window, darkModeFlag, darkModeFlag)//状态栏透明且黑色字体
            } else {
                extraFlagField.invoke(window, 0, darkModeFlag)//清除黑色字体
            }
        } catch (e: Exception) {
            Log.d("setStatusBarTextColor", e.message)
        }

    }
    /**
     * 获取遮罩整个activity的mask
     *
     * @return
     */
    fun getMaskView(): FrameLayout {
        return mDelegate?.getMaskView()!!
    }
    /**
     * 显示遮罩
     *
     * @param display
     */
    fun displayMaskView(display: Boolean) {
        mDelegate?.displayMaskView(display)
    }

    /**
     * 开启搜索模式，与stopSearchMode成对使用
     *
     * @return
     */
    fun startSearchMode(): SearchView? {
        return mDelegate?.startSearchMode()
    }

    /**
     * 停止搜索模式
     */
    fun stopSearchMode() {
        mDelegate?.stopSearchMode()
    }

    /**
     * 设置为导航栏 状态栏 透明
     *
     * @param on
     */
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun setTranslucent(on: Boolean) {
        val win = window
        val winParams = win.attributes
        val bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.attributes = winParams
    }

    public override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        mDelegate?.onPostCreate(savedInstanceState)
    }

    override fun <T : View> findViewById(id: Int): T? {
        return super.findViewById(id) ?: mDelegate?.findViewById(id)
    }

    /**
     * menu菜单创建方法
     *
     * @param actionMenu
     */
    open fun onMenuActionCreated(actionMenu: ActionMenu) {
        //do somethings
    }

    /**
     * menu菜单选择监听方法
     *
     * @param actionMenu
     * @return
     */
    open fun onMenuActionSelected(actionMenu: ActionMenuItem): Boolean {
        return false
    }

    /**
     * 指派菜单监听事件
     *
     * @param actionMenu
     * @return
     */
    protected fun dispatchActionSelected(actionMenu: ActionMenuItem): Boolean {
        return if (onMenuActionSelected(actionMenu)) {
            true
        } else {
            dispatchFragmentActionSelected(actionMenu)
        }
    }

    /**
     * 指派菜单监听事件到fragment
     *
     * @param actionMenu
     * @return
     */
    private fun dispatchFragmentActionSelected(actionMenu: ActionMenuItem): Boolean {
        return false
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mDelegate?.onSaveInstanceState(outState)
    }

    /**
     * 启动自定义actionmode
     *
     * @param callback
     * @return
     */
    fun startCustomActionMode(callback: ActionMode.Callback): ActionMode {
        return getCustomActionBar()?.startActionMode(callback)
    }

    /**
     * 停止自定义actionmode
     */
    fun stopCustomActionMode() {
        getCustomActionBar()?.stopActionMode()
    }

    /**
     * 导航回调
     *
     * @return
     */
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent): Boolean {
        return if(mDelegate?.onKeyUp(keyCode, event) == true){
            true
        }else{
            super.onKeyUp(keyCode, event)
        }
    }

    /**
     * 设置阴影
     *
     * @param shadow
     */
    fun setActionbarShadow(shadow: Boolean) {
        mDelegate?.setActionbarShadow(shadow)
    }

    /**
     * 设置阴影
     *
     * @param shadow
     * @param elevation
     */
    fun setActionbarShadow(shadow: Boolean, elevation: Float) {
        mDelegate?.setActionbarShadow(shadow, elevation)
    }
}
