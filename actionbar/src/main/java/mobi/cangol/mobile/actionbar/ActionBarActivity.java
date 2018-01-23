package mobi.cangol.mobile.actionbar;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import mobi.cangol.mobile.actionbar.view.SearchView;

/**
 * @author Cangol
 */
public class ActionBarActivity extends AppCompatActivity{
    private ActionBarActivityDelegate mDelegate;
    private SystemBarTintManager mTintManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDelegate = new ActionBarActivityDelegate(this);
        mDelegate.onCreate(savedInstanceState);
        mTintManager = new SystemBarTintManager(this);
    }

    /**
     * 设置标题
     *
     * @param title
     */
    @Override
    public void setTitle(CharSequence title) {
        mDelegate.setTitle(title);
    }

    /**
     * 设置标题
     *
     * @param titleId
     */
    @Override
    public void setTitle(int titleId) {
        mDelegate.setTitle(titleId);
    }

    /**
     * 设置背景颜色
     *
     * @param color
     */
    public void setBackgroundColor(int color) {
        mDelegate.setBackgroundColor(color);
    }

    /**
     * 设置背景颜色
     *
     * @param resId
     */
    public void setBackgroundResource(int resId) {
        mDelegate.setBackgroundResource(resId);
    }


    /**
     * 设置window背景颜色
     *
     * @param resId
     */
    public void setWindowBackground(int resId) {
        //替换背景
        this.getWindow().setBackgroundDrawableResource(resId);
    }

    /**
     * 设置状态栏颜色
     *
     * @param color
     */
    @TargetApi(19)
    public void setStatusBarTintColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(color);
        }else {
            mTintManager.setStatusBarTintEnabled(true);
            mTintManager.setStatusBarTintColor(color);
        }
    }

    /**
     * 设置导航栏颜色
     *
     * @param color
     */
    @TargetApi(19)
    public void setNavigationBarTintColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(color);
        }else {
            mTintManager.setNavigationBarTintEnabled(true);
            mTintManager.setNavigationBarTintColor(color);
        }
    }

    /**
     * 遮罩整个activity的mask
     *
     * @param id
     */
    public void setMaskView(int id) {
        mDelegate.setMaskView(id);
    }

    /**
     * 遮罩整个activity的mask
     *
     * @param view
     */
    public void setMaskView(View view) {
        mDelegate.setMaskView(view);
    }

    /**
     * 显示遮罩
     *
     * @param display
     */
    public void displayMaskView(boolean display) {
        mDelegate.displayMaskView(display);
    }

    /**
     * 开启搜索模式，与stopSearchMode成对使用
     *
     * @return
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
     * 设置为导航栏 状态栏 透明
     *
     * @param on
     */
    @TargetApi(19)
    public void setTranslucent(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    /**
     * 设置全屏
     *
     * @param fullscreen
     */
    public void setFullScreen(boolean fullscreen) {
        if (fullscreen) {
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        mTintManager.setStatusBarTintEnabled(!fullscreen);
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
     * @return
     */
    public boolean isActionbarOverlay() {
        return mDelegate.isActionbarOverlay();
    }

    /**
     * 设置actionbar是否悬浮
     *
     * @param mActionbarOverlay
     */
    public void setActionbarOverlay(boolean mActionbarOverlay) {
        this.mDelegate.setActionbarOverlay(mActionbarOverlay);
    }

    /**
     * 返回actionbar的显示
     */
    public boolean isActionbarShow() {
        return mDelegate.isActionbarShow();

    }

    /**
     * 设置actionbar的显示
     *
     * @param show
     */
    public void setActionbarShow(boolean show) {
        if (show) {
            this.mDelegate.setActionbarOverlay(false);
        } else {
            this.mDelegate.setActionbarOverlay(true);
        }
        this.mDelegate.setActionbarShow(show);

    }

    /**
     * 获取菜单的Inflater
     *
     * @return
     */
    public ActionMenuInflater getActionMenuInflater() {
        return mDelegate.getActionMenuInflater();
    }

    /**
     * menu菜单创建方法
     *
     * @param actionMenu
     */
    public void onMenuActionCreated(ActionMenu actionMenu) {

    }

    /**
     * menu菜单选择监听方法
     *
     * @param actionMenu
     * @return
     */
    public boolean onMenuActionSelected(ActionMenuItem actionMenu) {
        return false;
    }

    /**
     * 指派菜单监听事件
     *
     * @param actionMenu
     * @return
     */
    protected boolean dispatchActionSelected(ActionMenuItem actionMenu) {
        if (onMenuActionSelected(actionMenu)) {
            return true;
        } else {
            return dispatchFragmentActionSelected(actionMenu);
        }
    }

    /**
     * 指派菜单监听事件到fragment
     *
     * @param actionMenu
     * @return
     */
    protected boolean dispatchFragmentActionSelected(ActionMenuItem actionMenu) {
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
     * @return
     */
    public ActionBar getCustomActionBar() {
        return mDelegate.getCustomActionBar();
    }

    /**
     * 启动自定义actionmode
     *
     * @param callback
     * @return
     */
    public ActionMode startCustomActionMode(ActionMode.Callback callback) {
        return getCustomActionBar().startActionMode(callback);
    }
    /**
     * 停止自定义actionmode
     *
     */
    public void stopCustomActionMode() {
         getCustomActionBar().stopActionMode();
    }
    /**
     * 导航回调
     *
     * @return
     */
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
}
