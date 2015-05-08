package com.example.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mobi.cangol.mobile.actionbar.ActionBarActivity;
import mobi.cangol.mobile.actionbar.ActionMenu;
import mobi.cangol.mobile.actionbar.ActionMenuItem;
import mobi.cangol.mobile.actionbar.OnNavigationListener;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

@SuppressLint("ResourceAsColor")
public class MainActivity extends ActionBarActivity implements OnClickListener{
	private DeleteActionMode mDeleteActionMode;
	static Map<String, String> map1 = new HashMap<String, String>();  
	static Map<String, String> map2 = new HashMap<String, String>();  
	static Map<String, String> map3 = new HashMap<String, String>();
	static{
	        map1.put("name", "新闻");  
	        map2.put("name", "游戏"); 
	        map3.put("name", "壁纸"); 
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.setStatusBarTintColor(R.color.red);
		this.setNavigationBarTintColor(R.color.blue);
		findViews();
		this.setTitle("Home");
		this.setFullScreen(false);
		this.setActionbarShow(true);
		this.setActionbarOverlay(false);
		this.getCustomActionBar().setDisplayHomeAsUpEnabled(true);
		this.getCustomActionBar().setBackgroundResource(R.color.red);
		//this.setScreenBackgroundResource(R.color.green);
		
		
		List<Map<String, String>> list=new ArrayList<Map<String, String>>();
		list.add(map1);
		list.add(map2);
		list.add(map3);
		final BaseAdapter adapter=new SimpleAdapter(this,list,android.R.layout.simple_list_item_1,  
                new String[]{"name"},            //每行显示一个姓名  
                new int[]{android.R.id.text1});  
		this.getCustomActionBar().setListNavigationCallbacks(adapter, new OnNavigationListener(){

			@Override
			public boolean onNavigationItemSelected(int itemPosition,
					long itemId) {
				showToast("Navigation "+adapter.getItem(itemPosition));
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
				mDeleteActionMode=new DeleteActionMode();
				this.startCustomActionMode(mDeleteActionMode);
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
