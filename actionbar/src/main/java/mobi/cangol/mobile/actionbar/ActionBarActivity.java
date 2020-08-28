package mobi.cangol.mobile.actionbar;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.AttrRes;
import androidx.annotation.ColorInt;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import mobi.cangol.mobile.actionbar.view.SearchView;

public class ActionBarActivity extends AppCompatActivity {
    private ActionBarActivityDelegate mDelegate;
    private SystemBarTintManager mTintManager;
    private boolean useSystemBarTintLollipop = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDelegate = new ActionBarActivityDelegate(this);
        mDelegate.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            mTintManager = new SystemBarTintManager(this);
        }
        setStatusBarTintColor(getThemeAttrColor(R.attr.actionbar_background));
    }

    /**
     * 在Lollipop是否使用澄侵式系统栏(状态栏和导航栏)
     *
     * @param useSystemBarTintLollipop  Lollipop是否使用澄侵式系统栏
     */
    public void setUseSystemBarTintLollipop(boolean useSystemBarTintLollipop) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M &&
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.useSystemBarTintLollipop = useSystemBarTintLollipop;
        }
    }

    public TypedValue getAttrTypedValue(@AttrRes int attr) {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(attr, typedValue, true);
        return typedValue;
    }

    @ColorInt
    public int getThemeAttrColor(@AttrRes int colorAttr) {
        TypedArray array = this.obtainStyledAttributes(null, new int[]{colorAttr});
        try {
            return array.getColor(0, 0);
        } finally {
            array.recycle();
        }
    }

    /**
     * 设置标题
     *
     * @param title 标题
     */
    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        mDelegate.setTitle(title);
    }

    private void setRootViewFitsSystemWindows(boolean fitsSystemWindows) {
        mDelegate.getRootView().setFitsSystemWindows(fitsSystemWindows);
        if (fitsSystemWindows) {
            mDelegate.getRootView().setPadding(0, getStatusBarHeight(), 0, 0);
            ((RelativeLayout.LayoutParams) findViewById(R.id.actionbar_view).getLayoutParams()).topMargin = 0;
        } else {
            mDelegate.getRootView().setPadding(0, 0, 0, 0);
            ((RelativeLayout.LayoutParams) findViewById(R.id.actionbar_view).getLayoutParams()).topMargin = getStatusBarHeight();
        }
    }

    /**
     * 设置标题
     *
     * @param titleId 标题id
     */
    @Override
    public void setTitle(int titleId) {
        mDelegate.setTitle(titleId);
    }

    /**
     * 设置背景颜色
     *
     * @param color 颜色
     */
    public void setBackgroundColor(int color) {
        mDelegate.setBackgroundColor(color);
    }

    /**
     * 设置背景颜色
     *
     * @param resId 背景资源id
     */
    public void setBackgroundResource(int resId) {
        mDelegate.setBackgroundResource(resId);
    }


    /**
     * 设置window背景颜色
     *
     * @param resId 背景资源id
     */
    public void setWindowBackground(int resId) {
        //替换背景
        this.getWindow().setBackgroundDrawableResource(resId);
    }

    /**
     * 设置状态栏颜色
     *
     * @param color 颜色
     */
    public void setStatusBarTintColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(color);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (useSystemBarTintLollipop) {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                getWindow().setStatusBarColor(color);
            }
        } else if (!isFullScreen()) {
            mTintManager.setStatusBarTintEnabled(true);
            mTintManager.setStatusBarTintColor(color);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void setFitsSystemWindows(int layoutId) {
        if (layoutId == R.id.container_view) {
            findViewById(R.id.container_view).setFitsSystemWindows(true);
        } else {
            findViewById(R.id.container_view).setFitsSystemWindows(false);
            findViewById(layoutId).setFitsSystemWindows(true);
        }
    }

    public void setStatusBarTranslucent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#1f000000"));
            setSystemUiFloatFullScreen(true);
        }
    }

    public void setStatusBarTransparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            setSystemUiFloatFullScreen(true);
        }
    }


    public void setSystemUiFloatFullScreen(boolean enable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (enable) {
                findViewById(R.id.container_view).setFitsSystemWindows(false);
                findViewById(R.id.container_view).setPadding(0, 0, 0, 0);
                ((RelativeLayout.LayoutParams) findViewById(R.id.actionbar_view).getLayoutParams()).topMargin = getStatusBarHeight();
            } else {
                findViewById(R.id.container_view).setFitsSystemWindows(true);
                findViewById(R.id.container_view).setPadding(0, getStatusBarHeight(), 0, 0);
                ((RelativeLayout.LayoutParams) findViewById(R.id.actionbar_view).getLayoutParams()).topMargin = 0;
            }
            View decorView = this.getWindow().getDecorView();
            int option = decorView.getSystemUiVisibility();
            if (enable) {
                option |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            } else {
                option &= ~View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            }

            decorView.setSystemUiVisibility(option);

            decorView.requestApplyInsets();
        }
    }

    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = this.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = this.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 设置导航栏颜色
     *
     * @param color 颜色
     */
    public void setNavigationBarTintColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setNavigationBarColor(color);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (useSystemBarTintLollipop) {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                getWindow().setNavigationBarColor(color);
            }
        } else if (!isFullScreen()) {
            mTintManager.setNavigationBarTintEnabled(true);
            mTintManager.setNavigationBarTintColor(color);
        }
    }

    /**
     * 设置状态栏字体图标颜色
     *
     * @param black 是否黑色
     */
    public void setStatusBarTextColor(boolean black) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int systemUiVisibility = getWindow().getDecorView().getSystemUiVisibility();
            if (black) {
                systemUiVisibility |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            } else {
                systemUiVisibility &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
            getWindow().getDecorView().setSystemUiVisibility(systemUiVisibility);
            if (Build.BRAND.equals("Xiaomi") && Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                setStatusBarTextColorWithXiaomi(black);
            }
        }
    }

    private void setStatusBarTextColorWithXiaomi(boolean black) {
        Class clazz = getWindow().getClass();
        try {
            int darkModeFlag = 0;
            Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            if (black) {
                extraFlagField.invoke(getWindow(), darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
            } else {
                extraFlagField.invoke(getWindow(), 0, darkModeFlag);//清除黑色字体
            }
        } catch (Exception e) {
            Log.d("setStatusBarTextColor", e.getMessage());
        }
    }

    /**
     * 获取遮罩整个activity的mask
     *
     * @return 遮罩view
     */
    public FrameLayout getMaskView() {
        return mDelegate.getMaskView();
    }

    /**
     * 显示遮罩
     *
     * @param display 是否遮罩
     */
    public void displayMaskView(boolean display) {
        mDelegate.displayMaskView(display);
    }

    /**
     * 开启搜索模式，与stopSearchMode成对使用
     *
     * @return SearchView
     */
    public SearchView startSearchMode() {
        return mDelegate.startSearchMode();
    }

    /**
     * 停止搜索模式
     */
    public void stopSearchMode() {
        mDelegate.stopSearchMode();
    }

    /**
     * 设置全屏
     *
     * @param fullscreen 是否全屏
     */
    public void setFullScreen(boolean fullscreen) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (fullscreen) {
                this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            } else {
                this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            }
        } else {
            int newVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;

            if (fullscreen) {
                newVisibility |= View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            }
            // Set the visibility
            this.getWindow().getDecorView().setSystemUiVisibility(newVisibility);
        }
    }

    public void setFullScreenNoActionBar(boolean fullscreen) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (fullscreen) {
                setRootViewFitsSystemWindows(false);
                setActionbarShow(false);
                int option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
                this.getWindow().getDecorView().setSystemUiVisibility(option);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    WindowManager.LayoutParams lp = this.getWindow().getAttributes();
                    lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
                    this.getWindow().setAttributes(lp);
                }
                this.getWindow().getDecorView().requestApplyInsets();
            } else {
                setRootViewFitsSystemWindows(true);
                setActionbarShow(true);
                this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                this.getWindow().getDecorView().requestApplyInsets();
            }
        } else {
            int newVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;

            if (fullscreen) {
                newVisibility |= View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            }
            // Set the visibility
            this.getWindow().getDecorView().setSystemUiVisibility(newVisibility);
        }
    }

    /**
     * 返回是否全屏
     * @return 是否全屏
     */
    public boolean isFullScreen() {
        return (this.getWindow().getAttributes().flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) == WindowManager.LayoutParams.FLAG_FULLSCREEN;
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDelegate.onPostCreate(savedInstanceState);
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v != null)
            return v;
        return mDelegate.findViewById(id);
    }

    /**
     * 返回actionbar是否悬浮
     *
     * @return 是否悬浮
     */
    public boolean isActionbarOverlay() {
        return mDelegate.isActionbarOverlay();
    }

    /**
     * 设置actionbar是否悬浮
     *
     * @param mActionbarOverlay 是否悬浮
     */
    public void setActionbarOverlay(boolean mActionbarOverlay) {
        this.mDelegate.setActionbarOverlay(mActionbarOverlay);
    }

    /**
     * 返回actionbar的显示
     * @return 是否显示
     */
    public boolean isActionbarShow() {
        return mDelegate.isActionbarShow();

    }

    /**
     * 设置actionbar的显示
     *
     * @param show 是否显示
     */
    public void setActionbarShow(boolean show) {
        this.mDelegate.setActionbarOverlay(!show);
        this.mDelegate.setActionbarShow(show);

    }

    /**
     * menu菜单创建方法
     *
     * @param actionMenu 菜单
     */
    public void onMenuActionCreated(ActionMenu actionMenu) {
        //do somethings
    }

    /**
     * menu菜单选择监听方法
     *
     * @param actionMenu  菜单
     * @return 是否添加
     */
    public boolean onMenuActionSelected(ActionMenuItem actionMenu) {
        return false;
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mDelegate.onSaveInstanceState(outState);
    }

    /**
     * 获取自定义actionbar
     *
     * @return 自定义actionbar
     */
    public ActionBar getCustomActionBar() {
        return mDelegate.getCustomActionBar();
    }

    /**
     * 启动自定义actionmode
     *
     * @param callback 回调
     * @return ActionMode
     */
    public ActionMode startCustomActionMode(ActionMode.Callback callback) {
        return getCustomActionBar().startActionMode(callback);
    }

    /**
     * 停止自定义actionmode
     */
    public void stopCustomActionMode() {
        getCustomActionBar().stopActionMode();
    }

    /**
     * 导航回调
     *
     * @return 是否操作
     */
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        boolean b = mDelegate.onKeyUp(keyCode, event);
        if (b) return b;
        return super.onKeyUp(keyCode, event);
    }

    /**
     * 设置阴影
     *
     * @param shadow 是否阴影
     */
    public void setActionbarShadow(boolean shadow) {
        mDelegate.setActionbarShadow(shadow);
    }

    /**
     * 设置阴影
     *
     * @param shadow 是否阴影
     * @param elevation 阴影半径
     */
    public void setActionbarShadow(boolean shadow, float elevation) {
        mDelegate.setActionbarShadow(shadow, elevation);
    }
}
