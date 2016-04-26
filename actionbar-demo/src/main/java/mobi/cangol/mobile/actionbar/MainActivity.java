package mobi.cangol.mobile.actionbar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import mobi.cangol.mobile.actionbar.ActionBarActivity;
import mobi.cangol.mobile.actionbar.ActionMenu;
import mobi.cangol.mobile.actionbar.ActionMenuItem;
import mobi.cangol.mobile.actionbar.ActionMode;
import mobi.cangol.mobile.actionbar.ActionTab;
import mobi.cangol.mobile.actionbar.ActionTabItem;
import mobi.cangol.mobile.actionbar.OnNavigationListener;
import mobi.cangol.mobile.actionbar.view.ActionTabView;
import mobi.cangol.mobile.actionbar.view.SearchView;

@SuppressLint("ResourceAsColor")
public class MainActivity extends ActionBarActivity implements OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        this.setTitle("首页");

        //this.setFullScreen(true);
        //this.setWindowBackground(Color.BLACK);
        //this.setBackgroundResource(R.color.activity_background);

        this.setStatusBarTintColor(R.color.blue);
        this.setNavigationBarTintColor(R.color.red);

        this.setActionbarShow(true);
        this.setActionbarOverlay(false);
        this.getCustomActionBar().setDisplayShowHomeEnabled(true);
        //this.getCustomActionBar().setBackgroundResource(R.color.blue);
//        this.setMaskView(R.layout.activity_mask);
//        this.displayMaskView(true);
//        this.findViewById(R.id.button_mask).setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                displayMaskView(false);
//            }
//        });

    }

    public void findViews() {
        this.findViewById(R.id.button_search_1).setOnClickListener(this);
        this.findViewById(R.id.button_search_2).setOnClickListener(this);
        this.findViewById(R.id.button_mode_1).setOnClickListener(this);
        this.findViewById(R.id.button_mode_2).setOnClickListener(this);
        this.findViewById(R.id.button_nav_1).setOnClickListener(this);
        this.findViewById(R.id.button_nav_2).setOnClickListener(this);
        this.findViewById(R.id.button_progress_1).setOnClickListener(this);
        this.findViewById(R.id.button_progress_2).setOnClickListener(this);
        this.findViewById(R.id.button_tab_1).setOnClickListener(this);
        this.findViewById(R.id.button_tab_2).setOnClickListener(this);
        this.findViewById(R.id.button_custom_1).setOnClickListener(this);
        this.findViewById(R.id.button_custom_2).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_progress_1:
                this.getCustomActionBar().startProgress();
                break;
            case R.id.button_progress_2:
                this.getCustomActionBar().stopProgress();
                break;
            case R.id.button_search_1:
                SearchView searchView= this.startSearchMode();
                //searchView.setSearchHistoryEnable(false);
                searchView.setOnActionClickListener(new SearchView.OnActionClickListener() {
                    @Override
                    public boolean onActionClick(String keywords) {

                        return true;
                    }
                });
                searchView.setOnSearchTextListener(new SearchView.OnSearchTextListener() {
                    @Override
                    public boolean onSearchText(String keywords) {

                        return true;
                    }
                });
                break;
            case R.id.button_search_2:
                this.stopSearchMode();
                break;
            case R.id.button_mode_1:
                actionMode();
                break;
            case R.id.button_mode_2:
                this.getCustomActionBar().stopActionMode();
                break;
            case R.id.button_nav_1:
                actionNav();
                break;
            case R.id.button_nav_2:
                this.getCustomActionBar().clearListNavigation();
                break;
            case R.id.button_tab_1:
                actionTab();
                break;
            case R.id.button_tab_2:
                this.getCustomActionBar().getActionTab().removeAllTabs();
                break;
            case R.id.button_custom_1:
                this.getCustomActionBar().setCustomView(new EditText(this));
                break;
            case R.id.button_custom_2:
                this.getCustomActionBar().removeCustomView();
                break;
        }
    }

    private void showToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }

    private void showToast(int id) {
        Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMenuActionCreated(ActionMenu actionMenu) {
        super.onMenuActionCreated(actionMenu);
        actionMenu.addMenu(1, R.string.action_delete, -1, 1);
        actionMenu.addMenu(2, R.string.action_selectAll, R.drawable.ic_action_select, 1);
        actionMenu.addMenu(3, R.string.action_invert, R.drawable.ic_action_unselect, 0);
    }

    @Override
    public boolean onMenuActionSelected(ActionMenuItem action) {
        switch (action.getId()) {
            case 1:
                showToast(R.string.action_delete);
                break;
            case 2:
                showToast(R.string.action_selectAll);
                break;
            case 3:
                showToast(R.string.action_invert);
                break;
        }
        return super.onMenuActionSelected(action);
    }

    public void title() {
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
                showToast("Click Title");
            }
        });
    }

    public void actionMode() {
        this.startCustomActionMode(new ActionMode.Callback() {

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
    }

    public void actionNav() {
        final String[] navs = {"首页", "游戏", "壁纸", "资讯"};
        this.getCustomActionBar().setListNavigation(navs);
        this.getCustomActionBar().setOnNavigationListener(new OnNavigationListener() {

            @Override
            public boolean onNavigationItemSelected(int itemPosition,
                                                    long itemId) {
                showToast("Navigation " + navs[itemPosition]);
                getCustomActionBar().setTitle(navs[itemPosition]);
                return true;
            }
        });
    }

    public void actionTab() {
        ActionTab actionTab = this.getCustomActionBar().getActionTab();
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
    }
}
