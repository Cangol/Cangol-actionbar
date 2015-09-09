package mobi.cangol.mobile.actionbar.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import mobi.cangol.mobile.actionbar.R;
import mobi.cangol.mobile.actionbar.ActionBarActivity;
import mobi.cangol.mobile.actionbar.ActionMenu;
import mobi.cangol.mobile.actionbar.ActionMenuItem;
import mobi.cangol.mobile.actionbar.ActionMode;
import mobi.cangol.mobile.actionbar.ActionMode.Callback;
import mobi.cangol.mobile.actionbar.ActionTab;
import mobi.cangol.mobile.actionbar.ActionTabItem;
import mobi.cangol.mobile.actionbar.OnNavigationListener;
import mobi.cangol.mobile.actionbar.internal.ActionMenuImpl;
import mobi.cangol.mobile.actionbar.internal.ActionModeImpl;
import mobi.cangol.mobile.actionbar.internal.ActionTabImpl;
import mobi.cangol.mobile.actionbar.view.ActionMenuView.OnActionClickListener;

public class ActionBarView extends RelativeLayout {
	public static final String TAG    = "ActionBar";
    private LayoutInflater mInflater;
    private View mRootView;
    private ImageView mIndicator;
    private LinearLayout mTitleLayout;
    private TextView mTitleView;
    private PopupWindow mPopuMenu;
    private ProgressView mProgressView;
    private SearchView mSearchView;
    private ActionMenu mActionMenu;
    private ActionMode  mActionMode;
    private ActionTab mActionTab;
    private ActionBarActivity mActionBarActivity;
    private boolean mIsSearchMode;
   	private DrawerArrowDrawable mDrawerArrowDrawable;
   	private boolean mIsCustomHomeAsUpIndicator;
   	private int mHomeId,mUpId;
    public ActionBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mActionBarActivity=(ActionBarActivity) context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        
        mDrawerArrowDrawable=new DrawerArrowDrawable(context.getResources(),false);
        mDrawerArrowDrawable.setStrokeColor(getResources().getColor(R.color.actionbar_indicator));
        
        mInflater.inflate(R.layout.actionbar_layout, this);
        mRootView=this.findViewById(R.id.actionbar_main_layout);
        mIndicator = (ImageView) this.findViewById(R.id.actionbar_main_indicator);
        mTitleLayout= (LinearLayout) this.findViewById(R.id.actionbar_main_title_layout);
        mTitleView = (TextView) this.findViewById(R.id.actionbar_main_title);
        mProgressView= (ProgressView) this.findViewById(R.id.actionbar_main_progress);
        mSearchView = (SearchView) this.findViewById(R.id.actionbar_main_search);
        mActionTab=new ActionTabImpl((ActionTabView) this.findViewById(R.id.actionbar_main_tabview));
        mActionMenu=new ActionMenuImpl((ActionMenuView) this.findViewById(R.id.actionbar_main_menu));
        mActionMode=new ActionModeImpl(mActionBarActivity,(ActionModeView) this.findViewById(R.id.actionbar_main_mode));
        setTitle(context.getApplicationInfo().name);
        initListeners();
    }
    public void setBackgroundColor(int color){
    	mRootView.setBackgroundColor(color);
	}
	
	public void setBackgroundResource(int resId){
		mRootView.setBackgroundResource(resId);
	}
    private void initListeners(){
    	if(!mIsCustomHomeAsUpIndicator)
    	mDrawerArrowDrawable.setParameter(0);
    	mIndicator.setImageDrawable(mDrawerArrowDrawable);
    	mIndicator.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(mActionBarActivity!=null)
				mActionBarActivity.onSupportNavigateUp();
			}
    		
    	});
    	mActionMenu.setOnActionClickListener(new OnActionClickListener(){

			@Override
			public boolean onActionClick(ActionMenuItem action) {
				if(mActionBarActivity!=null){
					return mActionBarActivity.onMenuActionSelected(action);
				}
				return false;
			}
    		
    	});
    }
    public void setListNavigationCallbacks(final String[] navs, OnNavigationListener onNavigationListener){
    	BaseAdapter adapter=new BaseAdapter(){

			@Override
			public int getCount() {
				return navs.length;
			}

			@Override
			public String getItem(int position) {
				return navs[position];
			}

			@Override
			public long getItemId(int position) {
				return position;
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View view = mInflater.inflate(R.layout.actionbar_navigation_list_item, parent, false);
		        TextView labelView =(TextView) view.findViewById(R.id.actionbar_navigation_item_text);
		        labelView.setText(navs[position]);
				return view;
			}
    		
    	};
    	initNavigationPopuMenu(mActionBarActivity,adapter,onNavigationListener);
    }
    public void clearListNavigation(){
    	mTitleView.setCompoundDrawables(null, null, null, null);
    	mTitleView.setOnClickListener(null);
    }
	private void initNavigationPopuMenu(Context context,BaseAdapter adapter, final OnNavigationListener onNavigationListener){
    	final View popuLayout=mInflater.inflate(R.layout.actionbar_navigation_list, null);
    	ListView listView=(ListView) popuLayout.findViewById(R.id.actionbar_popup_navigation_list);
    	listView.setAdapter(adapter);
    	final int width=(int) (200*context.getResources().getDisplayMetrics().density);
    	mPopuMenu=new PopupWindow(popuLayout, width, LayoutParams.WRAP_CONTENT, true);
    	mPopuMenu.setBackgroundDrawable(new BitmapDrawable()); 
    	mPopuMenu.setOnDismissListener(new OnDismissListener(){

			@Override
			public void onDismiss() {
				Drawable imgV = getResources().getDrawable(R.drawable.actionbar_dropdown);
		    	imgV.setBounds(0, 0, imgV.getIntrinsicWidth(), imgV.getIntrinsicHeight());      
		    	mTitleView.setCompoundDrawables(null, null, imgV, null);
			}
    		
    	});
    	listView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if(onNavigationListener!=null)
					onNavigationListener.onNavigationItemSelected(position, id);
				mPopuMenu.dismiss();
			}
    		
    	});
    	Drawable imgV = getResources().getDrawable(R.drawable.actionbar_dropdown);
    	imgV.setBounds(0, 0, imgV.getIntrinsicWidth(), imgV.getIntrinsicHeight());      
    	mTitleView.setCompoundDrawables(null, null, imgV, null);
    	mTitleView.setOnClickListener(new OnClickListener(){

 			@Override
 			public void onClick(View v) {
 				if(!mPopuMenu.isShowing()){
 					Drawable imgV = getResources().getDrawable(R.drawable.actionbar_dropup);
 			    	imgV.setBounds(0, 0, imgV.getIntrinsicWidth(), imgV.getIntrinsicHeight());      
 			    	mTitleView.setCompoundDrawables(null, null, imgV, null);
 			    	
 					int xoff=-(width-mTitleView.getWidth())/2;
 					if(mTitleView.getGravity()==Gravity.CENTER){
 						mPopuMenu.showAsDropDown(mTitleView, xoff, 0);
 					}else{
 						mPopuMenu.showAsDropDown(mTitleView, 0, 0);
 					}
 				}
 			}
     		
     	});
	}
	public void setCustomHomeAsUpIndicator(int homeId,int upId){
		mIsCustomHomeAsUpIndicator=true;
		mHomeId=homeId;
		mUpId=upId;
		mIndicator.setImageResource(homeId);
	}
    public void setDisplayHomeAsUpEnabled(boolean show) {
    	mIndicator.setVisibility(show? View.VISIBLE : View.INVISIBLE);
    }
    public void displayHomeIndicator() {
    	if(!mIsCustomHomeAsUpIndicator){
    		mDrawerArrowDrawable.setParameter(0);
        	mIndicator.setImageDrawable(mDrawerArrowDrawable);
    	}else{
    		mIndicator.setImageResource(mHomeId);
    	}
	}
	public void displayUpIndicator() {
		if(!mIsCustomHomeAsUpIndicator){
			mDrawerArrowDrawable.setParameter(1);
			mIndicator.setImageDrawable(mDrawerArrowDrawable);
		}else{
    		mIndicator.setImageResource(mUpId);
    	}
	}
	public void displayIndicator(float slideOffset) {
		if(!mIsCustomHomeAsUpIndicator){
			if(slideOffset >= .995) {
				mDrawerArrowDrawable.setFlip(true);
	        }else if (slideOffset <= .005) {
	        	mDrawerArrowDrawable.setFlip(false);
	        }
			mDrawerArrowDrawable.setParameter(slideOffset);
			mIndicator.setImageDrawable(mDrawerArrowDrawable);
		}else{
			if(slideOffset >= .995) {
				mIndicator.setImageResource(mUpId);
	        }else if (slideOffset <= .005) {
	        	mDrawerArrowDrawable.setFlip(false);
	        	mIndicator.setImageResource(mHomeId);
	        }
		}
	}
	public void setIndicatorColor(int color) {
		if(!mIsCustomHomeAsUpIndicator)
			mDrawerArrowDrawable.setStrokeColor(color);
	}
	public String getTitle(){
    	return (String) mTitleView.getText()	;
    }
    public void setTitle(CharSequence title) {
        mTitleView.setText(title);
    }

    public void setTitle(int resid) {
        mTitleView.setText(resid);
    }
    public void setTitleGravity(int gravity) {
    	mTitleLayout.setGravity(gravity);
    	mTitleView.setGravity(gravity);
    }
    public void setOnTitleClickListener(OnClickListener listener) {
        mTitleView.setOnClickListener(listener);
    }
    
    public ActionMode startActionMode(Callback callback){
    	if(!mActionMode.isActionMode()){
    		mActionMode.start(callback);
    	}
    	return mActionMode;
    }
    public void stopActionMode(){
    	if(mActionMode.isActionMode()){
    		mActionMode.finish();
    	}
    }
    
    public void startProgress(){
    	mProgressView.startProgress();
    }
    
    public void stopProgress(){
    	mProgressView.stopProgress();
    }
    
    public SearchView startSearchMode(){
    	mIsSearchMode=true;
    	mSearchView.setVisibility(View.VISIBLE);
    	return mSearchView;
    }
    
    public void stopSearchMode(){
    	mIsSearchMode=false;
    	mSearchView.clearText();
    	mSearchView.setVisibility(View.GONE);
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

	public void addActions(ArrayList<ActionMenuItem> actions) {
		mActionMenu.addActions(actions);
	}
	public ActionTab getActionTab() {
		return mActionTab;
	}
	public void clearActionTabs() {
		mActionTab.removeAllTabs();
	}

	public void addTabs(ArrayList<ActionTabItem> tabs) {
		mActionTab.addTabs(tabs);
	}
	public ArrayList<ActionTabItem> getTabs() {
		return mActionTab.getTabs();
	}
	public boolean onBackPressed(){
		if(mProgressView.isProgress())
			mProgressView.stopProgress();
		
		if(mIsSearchMode){
			stopSearchMode();
			return true;
		}
		if(mActionMode.isActionMode()){
			stopActionMode();
			return true;
		}
		
		return false;
	}
	public void setTitleVisibility(int visibility) {
		mTitleLayout.setVisibility(visibility);
	}
	
}