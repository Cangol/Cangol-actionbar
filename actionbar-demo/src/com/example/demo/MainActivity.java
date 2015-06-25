package com.example.demo;

import mobi.cangol.mobile.actionbar.ActionBarActivity;
import mobi.cangol.mobile.actionbar.ActionMenu;
import mobi.cangol.mobile.actionbar.ActionMenuItem;
import mobi.cangol.mobile.actionbar.ActionMode;
import mobi.cangol.mobile.actionbar.OnNavigationListener;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

@SuppressLint("ResourceAsColor")
public class MainActivity extends ActionBarActivity implements OnClickListener{
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		this.setStatusBarTintColor(R.color.red);
//		this.setNavigationBarTintColor(R.color.blue);
		findViews();
		this.setTitle("首页");
		this.setActionbarShow(true);
		this.setActionbarOverlay(false);
		this.getCustomActionBar().setDisplayHomeAsUpEnabled(true);
		this.getCustomActionBar().setBackgroundResource(R.color.red);
		this.getCustomActionBar().setTitleGravity(Gravity.CENTER);
		this.setFullScreen(true);
		final String[] navs={"首页","游戏","壁纸","资讯"};
		this.getCustomActionBar().setListNavigationCallbacks(navs, new OnNavigationListener(){

			@Override
			public boolean onNavigationItemSelected(int itemPosition,
					long itemId) {
				showToast("Navigation "+navs[itemPosition]);
				getCustomActionBar().setTitle(navs[itemPosition]);
				return false;
			}
		});
		
		
	}
	public void findViews(){
		this.findViewById(R.id.button1).setOnClickListener(this);
		this.findViewById(R.id.button2).setOnClickListener(this);
		this.findViewById(R.id.button3).setOnClickListener(this);
		this.findViewById(R.id.button4).setOnClickListener(this);
		this.findViewById(R.id.button5).setOnClickListener(this);
		this.findViewById(R.id.button6).setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.button1:
				this.getCustomActionBar().startProgress();
				break;
			case R.id.button2:
				this.getCustomActionBar().stopProgress();
				break;
			case R.id.button3:
				this.getCustomActionBar().startSearchMode();
				break;
			case R.id.button4:
				this.getCustomActionBar().stopSearchMode();
				break;
			case R.id.button5:
				this.startCustomActionMode(new ActionMode.Callback(){

					@Override
					public void onCreateActionMode(ActionMode mode,
							ActionMenu actionMenu) {
						
					}

					@Override
					public boolean onActionItemClicked(ActionMode mode,
							ActionMenuItem menuItem) {
						return false;
					}

					@Override
					public void onDestroyActionMode(ActionMode mode) {
						
					}
					
				});
				break;
			case R.id.button6:
				showToast("Locale.ENGLISH");
				//AppUtils.changeLocale(this, Locale.ENGLISH);
				break;
		}
	}

	private void showToast(String string) {
		Toast.makeText(this, string, 0);
	}
	@Override
	public void onMenuActionCreated(ActionMenu actionMenu) {
		super.onMenuActionCreated(actionMenu);
		actionMenu.add(new ActionMenuItem(1,R.string.action_delete,-1,0));
		actionMenu.add(new ActionMenuItem(2,R.string.action_selectAll,R.drawable.ic_action_select,0));
		actionMenu.add(new ActionMenuItem(3,R.string.action_invert,R.drawable.ic_action_unselect,0));
	}
	@Override
	public boolean onMenuActionSelected(ActionMenuItem action) {
		switch(action.getId()){
			case 1:
				Toast.makeText(this, R.string.action_delete, 0).show();
				break;
			case 2:
				Toast.makeText(this, R.string.action_selectAll, 0).show();
				break;
			case 3:
				Toast.makeText(this, R.string.action_invert, 0).show();
				break;
		}
		return super.onMenuActionSelected(action);
	}
}
