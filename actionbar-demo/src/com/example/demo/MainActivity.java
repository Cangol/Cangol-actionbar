package com.example.demo;

import mobi.cangol.mobile.actionbar.ActionBarActivity;
import mobi.cangol.mobile.actionbar.ActionMenu;
import mobi.cangol.mobile.actionbar.ActionMenuItem;
import mobi.cangol.mobile.actionbar.ActionMode;
import mobi.cangol.mobile.actionbar.ActionTab;
import mobi.cangol.mobile.actionbar.ActionTabItem;
import mobi.cangol.mobile.actionbar.OnNavigationListener;
import mobi.cangol.mobile.actionbar.view.ActionTabView;

import android.annotation.SuppressLint;
import android.graphics.Color;
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
        findViews();
        this.setTitle("首页");
        this.setFullScreen(true);
        this.setWindowBackground(Color.BLACK);
		this.setBackgroundResource(R.color.red);
		this.setStatusBarTintColor(Color.BLUE);
		this.setNavigationBarTintColor(Color.BLUE);
		this.setActionbarShow(true);
		this.setActionbarOverlay(false);
		this.getCustomActionBar().setDisplayShowHomeEnabled(true);
		this.getCustomActionBar().setBackgroundResource(R.color.blue);
		this.getCustomActionBar().setTitleGravity(Gravity.CENTER);
		final String[] navs={"首页","游戏","壁纸","资讯"};
		this.getCustomActionBar().setListNavigationCallbacks(navs, new OnNavigationListener() {

            @Override
            public boolean onNavigationItemSelected(int itemPosition,
                                                    long itemId) {
                showToast("Navigation " + navs[itemPosition]);
                getCustomActionBar().setTitle(navs[itemPosition]);
                return true;
            }
        });
        this.getCustomActionBar().clearListNavigation();

        ActionTab actionTab=this.getCustomActionBar().createdActionTab();
        actionTab.newTab(1, "推荐", 1);
        actionTab.newTab(2, "关注", 0);
        actionTab.setOnTabSelectedListener(new ActionTabView.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(ActionTabItem tab) {
                switch (tab.getId()) {
                    case 1:
                        Toast.makeText(MainActivity.this, tab.getText(), Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(MainActivity.this, tab.getText(), Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });

        //设置标题
        this.getCustomActionBar().setTitle("Home");
        //设置标题居中对其
        this.getCustomActionBar().setTitleGravity(Gravity.CENTER);
        //这是标题显示
        this.getCustomActionBar().setTitleVisibility(View.VISIBLE);
        //设置标题点击事件
        this.getCustomActionBar().setOnTitleClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Click Title", Toast.LENGTH_SHORT).show();
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
		Toast.makeText(this, string, 0).show();
	}
	@Override
	public void onMenuActionCreated(ActionMenu actionMenu) {
        super.onMenuActionCreated(actionMenu);
		//actionMenu.add(new ActionMenuItem(1,R.string.action_delete,-1,1));
		//actionMenu.add(new ActionMenuItem(2,R.string.action_selectAll,R.drawable.ic_action_select,1));
		//actionMenu.add(new ActionMenuItem(3,R.string.action_invert,R.drawable.ic_action_unselect,0));
	}
	@Override
	public boolean onMenuActionSelected(ActionMenuItem action) {
		switch(action.getId()){
			case 1:
				Toast.makeText(this, R.string.action_delete, Toast.LENGTH_SHORT).show();
				break;
			case 2:
				Toast.makeText(this, R.string.action_selectAll, Toast.LENGTH_SHORT).show();
				break;
			case 3:
				Toast.makeText(this, R.string.action_invert, Toast.LENGTH_SHORT).show();
				break;
		}
		return super.onMenuActionSelected(action);
	}
}
