package mobi.cangol.mobile.actionbar.view;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import mobi.cangol.mobile.actionbar.ActionBarActivity;
import mobi.cangol.mobile.actionbar.ActionMenu;
import mobi.cangol.mobile.actionbar.ActionMenuItem;
import mobi.cangol.mobile.actionbar.ActionMode;
import mobi.cangol.mobile.actionbar.ActionMode.Callback;
import mobi.cangol.mobile.actionbar.ActionTab;
import mobi.cangol.mobile.actionbar.ActionTabItem;
import mobi.cangol.mobile.actionbar.OnNavigationListener;
import mobi.cangol.mobile.actionbar.R;
import mobi.cangol.mobile.actionbar.internal.ActionMenuImpl;
import mobi.cangol.mobile.actionbar.internal.ActionModeImpl;
import mobi.cangol.mobile.actionbar.internal.ActionTabImpl;

/**
 * @author Cangol
 */
public class ActionBarView extends RelativeLayout {
    public static final String TAG = "ActionBar";
    private LayoutInflater mInflater;
    private View mRootView;
    private LinearLayout mLeftMenu;
    private ImageView mIndicator;
    private LinearLayout mTitleLayout;
    private TextView mTitleView;
    private PopupWindow mPopupMenu;
    private ImageView mRefreshView;
    private ActionMenu mActionMenu;
    private ActionMode mActionMode;
    private ActionTab mActionTab;
    private FrameLayout mCustomLayout;
    private ActionBarActivity mActionBarActivity;
    private DrawerArrowDrawable mDrawerArrowDrawable;
    private boolean mIsCustomHomeAsUpIndicator;
    private boolean mDisplayShowHomeEnabled;
    private int mHomeId, mUpId;
    private String[] mListNavigation;

    public ActionBarView(Context context) {
        super(context);
        initView(context);
    }

    public ActionBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ActionBarView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    public ActionBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public void initView(Context context) {
        mActionBarActivity = (ActionBarActivity) context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mDrawerArrowDrawable = new DrawerArrowDrawable(context.getResources(), false);

        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.actionbar_indicator, typedValue, true);
        mDrawerArrowDrawable.setStrokeColor(typedValue.data);

        mInflater.inflate(R.layout.actionbar_layout, this,true);
        mRootView = this.findViewById(R.id.actionbar_main_layout);
        mLeftMenu= (LinearLayout) this.findViewById(R.id.actionbar_left_menu);
        mIndicator = (ImageView) this.findViewById(R.id.actionbar_main_indicator);
        mTitleLayout = (LinearLayout) this.findViewById(R.id.actionbar_main_title_layout);
        mTitleView = (TextView) this.findViewById(R.id.actionbar_main_title);
        mRefreshView = (ImageView) this.findViewById(R.id.actionbar_main_refresh);
        mCustomLayout = (FrameLayout) this.findViewById(R.id.actionbar_main_custom_layout);
        mActionTab = new ActionTabImpl((ActionTabView) this.findViewById(R.id.actionbar_main_tabview));
        mActionMenu = new ActionMenuImpl((ActionMenuView) this.findViewById(R.id.actionbar_main_menu));
        mActionMode = new ActionModeImpl(mActionBarActivity, (ActionModeView) this.findViewById(R.id.actionbar_main_mode));
        setTitle(context.getApplicationInfo().name);
        initListeners();
    }

    public void setBackgroundColor(int color) {
        mRootView.setBackgroundColor(color);
    }

    public void setBackgroundResource(int resId) {
        mRootView.setBackgroundResource(resId);
    }

    private void initListeners() {
        if (!mIsCustomHomeAsUpIndicator)
            mDrawerArrowDrawable.setParameter(0);
        mIndicator.setImageDrawable(mDrawerArrowDrawable);
        mIndicator.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mActionBarActivity != null)
                    mActionBarActivity.onSupportNavigateUp();
            }

        });
        mActionMenu.setOnActionClickListener(new ActionMenuView.OnActionClickListener() {

            @Override
            public boolean onActionClick(ActionMenuItem action) {
                if (mActionBarActivity != null) {
                    return mActionBarActivity.onMenuActionSelected(action);
                }
                return false;
            }

        });
    }

    public String[] getListNavigation() {
        return mListNavigation;
    }

    public void setListNavigation(final String[] listNavigation) {
        this.mListNavigation = listNavigation;
    }

    public void clearListNavigation() {
        mTitleView.setCompoundDrawables(null, null, null, null);
        mTitleView.setOnClickListener(null);
    }

    public void setOnNavigationListener(final OnNavigationListener onNavigationListener) {
        BaseAdapter adapter = new BaseAdapter() {

            @Override
            public int getCount() {
                return mListNavigation.length;
            }

            @Override
            public String getItem(int position) {
                return mListNavigation[position];
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = mInflater.inflate(R.layout.actionbar_navigation_list_item, parent, false);
                TextView labelView = (TextView) view.findViewById(R.id.actionbar_navigation_item_text);
                labelView.setText(mListNavigation[position]);
                return view;
            }

        };
        initNavigationPopupMenu(mActionBarActivity, adapter, onNavigationListener);
    }

    private void initNavigationPopupMenu(final Context context, BaseAdapter adapter, final OnNavigationListener onNavigationListener) {
        final View popuLayout = mInflater.inflate(R.layout.actionbar_navigation_list, null);
        ListView listView = (ListView) popuLayout.findViewById(R.id.actionbar_popup_navigation_list);
        listView.setAdapter(adapter);
        final int width = (int) (200 * context.getResources().getDisplayMetrics().density);
        mPopupMenu = new PopupWindow(popuLayout, width, LayoutParams.WRAP_CONTENT, true);
        mPopupMenu.setBackgroundDrawable(new BitmapDrawable());
        mPopupMenu.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                TypedValue typedValue = new TypedValue();
                context.getTheme().resolveAttribute(R.attr.actionbar_dropdown, typedValue, true);
                Drawable imgV = getResources().getDrawable(typedValue.resourceId);
                imgV.setBounds(0, 0, imgV.getIntrinsicWidth(), imgV.getIntrinsicHeight());
                mTitleView.setCompoundDrawables(null, null, imgV, null);
            }

        });
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (onNavigationListener != null)
                    onNavigationListener.onNavigationItemSelected(position, id);
                mPopupMenu.dismiss();
            }

        });
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.actionbar_dropdown, typedValue, true);
        Drawable imgV = getResources().getDrawable(typedValue.resourceId);
        imgV.setBounds(0, 0, imgV.getIntrinsicWidth(), imgV.getIntrinsicHeight());
        mTitleView.setCompoundDrawables(null, null, imgV, null);
        mTitleView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!mPopupMenu.isShowing()) {
                    TypedValue typedValue = new TypedValue();
                    context.getTheme().resolveAttribute(R.attr.actionbar_dropup, typedValue, true);
                    Drawable imgV = getResources().getDrawable(typedValue.resourceId);
                    imgV.setBounds(0, 0, imgV.getIntrinsicWidth(), imgV.getIntrinsicHeight());
                    mTitleView.setCompoundDrawables(null, null, imgV, null);

                    int xoff = -(width - mTitleView.getWidth()) / 2;
                    if (mTitleView.getGravity() == Gravity.CENTER) {
                        mPopupMenu.showAsDropDown(mTitleView, xoff, 0);
                    } else {
                        mPopupMenu.showAsDropDown(mTitleView, 0, 0);
                    }
                }
            }

        });
    }

    public void setCustomHomeAsUpIndicator(int homeId, int upId) {
        mIsCustomHomeAsUpIndicator = true;
        mHomeId = homeId;
        mUpId = upId;
        mIndicator.setImageResource(homeId);
    }

    public void setDisplayShowHomeEnabled(boolean show) {
        mDisplayShowHomeEnabled = show;
        mIndicator.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public void displayHomeIndicator() {
        if (!mIsCustomHomeAsUpIndicator) {
            mDrawerArrowDrawable.setParameter(0);
            mIndicator.setImageDrawable(mDrawerArrowDrawable);
        } else {
            mIndicator.setImageResource(mHomeId);
        }
        mIndicator.setVisibility(mDisplayShowHomeEnabled ? View.VISIBLE : View.GONE);
    }

    public void displayUpIndicator() {
        if (!mIsCustomHomeAsUpIndicator) {
            mDrawerArrowDrawable.setParameter(1);
            mIndicator.setImageDrawable(mDrawerArrowDrawable);
        } else {
            mIndicator.setImageResource(mUpId);
        }
        mIndicator.setVisibility(View.VISIBLE);
    }

    public void displayIndicator(float slideOffset) {
        if (!mIsCustomHomeAsUpIndicator) {
            if (slideOffset >= .995) {
                mDrawerArrowDrawable.setFlip(true);
            } else if (slideOffset <= .005) {
                mDrawerArrowDrawable.setFlip(false);
            }
            mDrawerArrowDrawable.setParameter(slideOffset);
            mIndicator.setImageDrawable(mDrawerArrowDrawable);
        } else {
            if (slideOffset >= .995) {
                mIndicator.setImageResource(mUpId);
            } else if (slideOffset <= .005) {
                mIndicator.setImageResource(mHomeId);
            }
        }
        mIndicator.setVisibility(View.VISIBLE);
    }

    public void setIndicatorColor(int color) {
        if (!mIsCustomHomeAsUpIndicator)
            mDrawerArrowDrawable.setStrokeColor(color);
    }

    public void setLeftMenu(final int id,final int text,int drawable,OnClickListener listener) {
        mLeftMenu.setVisibility(View.VISIBLE);
        mLeftMenu.removeAllViews();
        if(drawable!=-1){
            final View view = mInflater.inflate(R.layout.actionbar_item_icon, null, false);

            ImageView labelView =(ImageView) view.findViewById(R.id.actionbar_item);
            labelView.setImageResource(drawable);
            view.setId(id);
            view.setTag(getContext().getString(text));
            labelView.setOnClickListener(listener);
            labelView.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast toast = null;
                    if (text != -1){
                        toast = Toast.makeText(getContext(), text, Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.LEFT | Gravity.TOP, view.getLeft(), view.getBottom());
                        toast.show();
                        return true;
                    }else{
                        return false;
                    }
                }
            });
            mLeftMenu.addView(view,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }else{
            final View view = mInflater.inflate(R.layout.actionbar_item_text, null, false);

            TextView labelView = (TextView) view.findViewById(R.id.actionbar_item);
            labelView.setText(text);
            view.setId(id);
            view.setTag(getContext().getString(text));
            labelView.setOnClickListener(listener);
            labelView.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast toast = null;
                    if (text != -1){
                        toast = Toast.makeText(getContext(), text, Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.LEFT | Gravity.TOP, view.getLeft(),view.getBottom());
                        toast.show();
                        return true;
                    }else{
                        return false;
                    }
                }
            });
            mLeftMenu.addView(view,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }


    }
    public void clearLeftMenu() {
        mLeftMenu.removeAllViews();
        mLeftMenu.setVisibility(View.GONE);
    }
    public CharSequence getTitle() {
        return mTitleView.getText();
    }

    public void setTitle(int resId) {
        mTitleView.setText(resId);
    }

    public void setTitle(CharSequence title) {
        mTitleView.setText(title);
    }

    public void setTitleGravity(int gravity) {
        mTitleLayout.setGravity(gravity);
        mTitleView.setGravity(gravity);
    }

    public int getTitleGravity() {
        return mTitleView.getVisibility();
    }

    public void setOnTitleClickListener(OnClickListener listener) {
        mTitleView.setOnClickListener(listener);
    }

    public ActionMode startActionMode(Callback callback) {
        if (!mActionMode.isActionMode()) {
            mActionMode.start(callback);
        }
        return mActionMode;
    }

    public void stopActionMode() {
        if (mActionMode.isActionMode()) {
            mActionMode.finish();
        }
    }

    public ActionMenu getActionMenu() {
        return mActionMenu;
    }

    public void clearActions() {
        mActionMenu.clear();
    }

    public ArrayList<ActionMenuItem> getActions() {
        return mActionMenu.getActions();
    }

    public void setActions(ArrayList<ActionMenuItem> actions) {
        mActionMenu.setActions(actions);
    }

    public ActionTab getActionTab() {
        return mActionTab;
    }

    public void clearActionTabs() {
        mActionTab.removeAllTabs();
        setTitleVisibility(View.VISIBLE);
    }

    public ArrayList<ActionTabItem> getTabs() {
        return mActionTab.getTabs();
    }

    public void setTabs(ArrayList<ActionTabItem> tabs) {
        setTitleVisibility(View.GONE);
        mActionTab.setTabs(tabs);
    }

    public boolean onBackPressed() {
        if (mActionMode.isActionMode()) {
            stopActionMode();
            return true;
        }

        return false;
    }

    public void setTitleVisibility(int visibility) {
        mTitleLayout.setVisibility(visibility);
    }

    public int getTitleVisibility() {
        return mTitleLayout.getVisibility();
    }

    public void setCustomView(View view) {
        mCustomLayout.removeAllViews();
        mCustomLayout.addView(view);
        mTitleLayout.setVisibility(View.GONE);
        mActionTab.removeAllTabs();
    }

    public void removeCustomView() {
        mCustomLayout.removeAllViews();
        mTitleLayout.setVisibility(View.VISIBLE);
    }

    public void enableRefresh(boolean enable) {
        View view=findViewById(R.id.actionbar_main_menu);
        RelativeLayout.LayoutParams layoutParams= (LayoutParams) view.getLayoutParams();
        if (enable) {
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,0);
            mRefreshView.setVisibility(View.VISIBLE);
        } else {
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,1);
            mRefreshView.setVisibility(View.GONE);
        }
    }

    public void setOnRefreshClickListener(OnClickListener listener) {
        mRefreshView.setOnClickListener(listener);
    }

    public void refreshing(boolean refresh) {
        if(mRefreshView.getVisibility()==VISIBLE){
            RotateAnimation anim = new RotateAnimation(0.0f, 360f,Animation.RELATIVE_TO_SELF, 0.5f,Animation.RELATIVE_TO_SELF, 0.5f);
            anim.setInterpolator(new LinearInterpolator());
            anim.setRepeatCount(Animation.INFINITE);
            anim.setDuration(250);
            if (refresh) {
                mRefreshView.startAnimation(anim);
            } else {
                mRefreshView.clearAnimation();
            }
        }
    }
}