package mobi.cangol.mobile.actionbar;

import android.app.Activity;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import mobi.cangol.mobile.actionbar.internal.ActionBarImpl;
import mobi.cangol.mobile.actionbar.view.ActionBarView;
import mobi.cangol.mobile.actionbar.view.FitSystemWindowRelativeLayout;
import mobi.cangol.mobile.actionbar.view.SearchView;

/**
 * @author Cangol
 */
public class ActionBarActivityDelegate {

    private ActionBarActivity mActivity;
    private ViewGroup mContainerView;
    private FitSystemWindowRelativeLayout mRootView;
    private ActionBar mActionBar;
    private FrameLayout mContentView;
    private FrameLayout mMaskView;
    private SearchView mSearchView;
    private boolean mActionbarOverlay = false;

    public ActionBarActivityDelegate(ActionBarActivity activity) {
        mActivity = activity;
    }

    protected void onCreate(Bundle savedInstanceState) {
        mContainerView = (ViewGroup) LayoutInflater.from(mActivity).inflate(R.layout.actionbar_activity_main, null);
        mRootView = mContainerView.findViewById(R.id.container_view);
        mContentView = (FrameLayout) mContainerView.findViewById(R.id.actionbar_content_view);
        mMaskView = (FrameLayout) mContainerView.findViewById(R.id.actionbar_mask_view);
        mActionBar = new ActionBarImpl((ActionBarView) mContainerView.findViewById(R.id.actionbar_view));
    }

    public FitSystemWindowRelativeLayout getRootView() {
        return mRootView;
    }

    public boolean isActionbarOverlay() {
        return mActionbarOverlay;
    }

    public void setActionbarOverlay(boolean mActionbarOverlay) {
        this.mActionbarOverlay = mActionbarOverlay;
        if (mActionbarOverlay) {
            mContentView.setPadding(0, 0, 0, 0);
        } else {
            mContentView.setPadding(0, (mActivity.getResources().getDimensionPixelSize(R.dimen.actionbar_height)), 0, 0);
        }
    }

    public ActionBar getCustomActionBar() {
        return mActionBar;
    }

    public void setBackgroundColor(int color) {
        mContainerView.setBackgroundColor(color);
    }

    public void setBackgroundResource(int resId) {
        mContainerView.setBackgroundResource(resId);
    }

    protected void onPostCreate(Bundle savedInstanceState) {
        attachToActivity(mActivity, mContainerView);
        if (savedInstanceState != null) {
            CharSequence title = savedInstanceState.getCharSequence("ActionBar.title");
            mActionBar.setTitle(title);

            String[] navs = savedInstanceState.getStringArray("ActionBar.navs");
            mActionBar.clearListNavigation();
            mActionBar.setListNavigation(navs);

            ArrayList<ActionMenuItem> menus = savedInstanceState.getParcelableArrayList("ActionBar.menus");
            mActionBar.clearActionMenus();
            mActionBar.setMenus(menus);

            ArrayList<ActionTabItem> tabs = savedInstanceState.getParcelableArrayList("ActionBar.tabs");
            mActionBar.clearActionTabs();
            mActionBar.setTabs(tabs);
            mActionBar.getActionTab().setTabSelected(savedInstanceState.getInt("ActionBar.tabs.selected"));

        } else {
            mActivity.onMenuActionCreated(mActionBar.getActionMenu());
        }

        if (!mActionBar.getTabs().isEmpty()) {
            mActionBar.setTitleVisibility(View.GONE);
        } else {
            mActionBar.setTitleVisibility(View.VISIBLE);
        }
    }

    private void attachToActivity(Activity activity, View layout) {
        // get the window background
        TypedArray a = activity.getTheme().obtainStyledAttributes(new int[]{android.R.attr.windowBackground});
        int background = a.getResourceId(0, 0);
        a.recycle();

        ViewGroup decor = (ViewGroup) activity.getWindow().getDecorView();
        ViewGroup decorChild = (ViewGroup) decor.getChildAt(0);
        if (decorChild.getBackground() != null) {
            mContainerView.setBackgroundDrawable(decorChild.getBackground());
            decorChild.setBackgroundDrawable(null);
        } else {
            if (mContainerView.getBackground() == null)
                mContainerView.setBackgroundResource(background);
        }
        decor.removeView(decorChild);
        decorChild.setFitsSystemWindows(false);
        decor.addView(layout, 0);
        setContent(decorChild);
    }

    public void setContent(View view) {
        setContentView(view);
    }

    public void setContentView(View v) {
        mContentView.removeAllViews();
        mContentView.addView(v);
    }

    public void setContentView(View v, LayoutParams params) {
        mContentView.removeAllViews();
        mContentView.addView(v, params);
    }

    public View findViewById(int id) {
        View v;
        if (mContainerView != null) {
            v = mContainerView.findViewById(id);
            if (v != null)
                return v;
        }
        return null;
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.isTracking() && !event.isCanceled()) {
            if (mSearchView != null) {
                stopSearchMode();
                return true;
            } else {
                return mActionBar.onBackPressed();
            }
        }
        return false;
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putCharSequence("ActionBar.title", mActionBar.getTitle());
        outState.putStringArray("ActionBar.navs", mActionBar.getListNavigation());
        outState.putParcelableArrayList("ActionBar.menus", (ArrayList<? extends Parcelable>) mActionBar.getMenus());
        outState.putParcelableArrayList("ActionBar.tabs", (ArrayList<? extends Parcelable>) mActionBar.getTabs());
        outState.putInt("ActionBar.tabs.selected", mActionBar.getActionTab().getTabSelected());
    }

    public void setTitle(int titleId) {
        mActionBar.setTitle(titleId);
    }

    public void setTitle(CharSequence title) {
        mActionBar.setTitle(title);
    }

    public FrameLayout getMaskView() {
        return mMaskView;
    }

    public void displayMaskView(boolean show) {
        mMaskView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public SearchView startSearchMode() {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        mSearchView = new SearchView(this.mActivity);
        int aIndex = mContainerView.indexOfChild(mContainerView.findViewById(R.id.actionbar_view));
        mContainerView.addView(mSearchView, aIndex + 1, layoutParams);
        mSearchView.show();
        return mSearchView;
    }

    public void stopSearchMode() {
        if (mSearchView != null) {
            mContainerView.removeView(mSearchView);
            mSearchView.hide();
            mSearchView = null;
        }
    }

    public boolean isActionbarShow() {
        return mActionBar.isShow();
    }

    public void setActionbarShow(boolean show) {
        mActionBar.setShow(show);
    }

    public void setActionbarShadow(boolean shadow) {
        if (shadow) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mContainerView.findViewById(R.id.actionbar_view).setElevation(1.5f * mActivity.getResources().getDisplayMetrics().density);
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mContainerView.findViewById(R.id.actionbar_view).setElevation(0);
            }
        }
    }

    public void setActionbarShadow(boolean shadow, float elevation) {
        if (shadow) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mContainerView.findViewById(R.id.actionbar_view).setElevation(elevation * mActivity.getResources().getDisplayMetrics().density);
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mContainerView.findViewById(R.id.actionbar_view).setElevation(elevation);
            }
        }
    }

}
