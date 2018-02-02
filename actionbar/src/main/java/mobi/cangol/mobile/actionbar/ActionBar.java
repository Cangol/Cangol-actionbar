package mobi.cangol.mobile.actionbar;

import android.support.annotation.IdRes;
import android.view.View;
import android.view.View.OnClickListener;

import java.util.ArrayList;

/**
 * @author Cangol
 */

public abstract class ActionBar {
    /**
     * 设置自定义的home和up
     *
     * @param homeId
     * @param upId
     */
    abstract public void setCustomHomeAsUpIndicator(int homeId, int upId);

    /**
     * 设置home的显示
     *
     * @param show
     */
    abstract public void setDisplayShowHomeEnabled(boolean show);

    /**
     * 显示home指示器
     */
    abstract public void displayHomeIndicator();

    /**
     * 显示Up指示器
     */
    abstract public void displayUpIndicator();

    /**
     * 设置指示器显示的offset，只对原生指示器起作用
     *
     * @param slideOffset
     */
    abstract public void displayIndicator(float slideOffset);

    /**
     * 设置指示器显示的颜色，只对原生指示器起作用
     *
     * @param color
     */
    abstract public void setIndicatorColor(int color);

    /**
     * 设置左侧按钮
     * @param id 菜单唯一Id
     * @param text 文本资源string的id，必须有效
     * @param icon 图片资源的id，如果无，请填-1
     * @param listener 点击事件listener
     */
    abstract public void setLeftMenu(final int id, final int text, int icon, OnClickListener listener);

    /**
     * 清除左侧按钮
     */
    abstract public void clearLeftMenu();
    /**
     * back时执行
     *
     * @return
     */
    abstract protected boolean onBackPressed();

    /**
     * 返回是否显示
     *
     * @return
     */
    abstract protected boolean isShow();

    /**
     * 设置显示
     *
     * @param show
     */
    abstract protected void setShow(boolean show);

    /**
     * 设置背景颜色
     *
     * @param color
     */
    abstract public void setBackgroundColor(int color);

    /**
     * 设置背景
     *
     * @param resId
     */
    abstract public void setBackgroundResource(int resId);

    /**
     * 获取标题
     *
     * @return
     */
    abstract public CharSequence getTitle();

    /**
     * 设置标题
     *
     * @param resid
     */
    abstract public void setTitle(int resid);

    /**
     * 设置标题
     *
     * @param title
     */
    abstract public void setTitle(CharSequence title);

    /**
     * 设置标题对其方式
     *
     * @param gravity
     */
    abstract public void setTitleGravity(int gravity);

    /**
     * 获取标题对其方式
     * @return
     */
    abstract public int getTitleGravity();

    /**
     * 设置标题显示
     *
     * @param visibly
     */
    abstract public void setTitleVisibility(int visibly);

    /**
     * 获取标题显示
     */
    abstract public int getTitleVisibility();

    /**
     * 设置标题点击事件监听
     * 此事件与setOnNavigationListener相冲突，只有后设置的有效
     *
     * @param listener
     */
    abstract public void setOnTitleClickListener(OnClickListener listener);

    /**
     * 启动ActionMode
     *
     * @param callback
     * @return
     */
    abstract public ActionMode startActionMode(ActionMode.Callback callback);

    /**
     * 停止ActionMode
     */
    abstract public void stopActionMode();

    /**
     * 开启/关闭 刷新
     */
    abstract public void enableRefresh(boolean enable);


    /**
     * 开启/关闭 刷新left|right
     */
    abstract public void enableRefresh(boolean enable,int gravity);
    /**
     * 开始/停止 刷新
     */
    abstract public void refreshing(boolean refresh);

    /**
     * 刷新按钮点击事件
     * @param listener
     */
    abstract public void setOnRefreshClickListener(OnClickListener listener);

    /**
     * 设置导航菜单
     * 此事件与setOnTitleClickListener相冲r突，只有后设置的有效
     *
     * @param onNavigationListener 导航监听
     */
    abstract public void setOnNavigationListener(OnNavigationListener onNavigationListener);

    /**
     * 获取导航菜单
     */
    abstract public String[] getListNavigation();

    /**
     * 设置导航菜单
     *
     * @param navs
     */
    abstract public void setListNavigation(String[] navs);

    /**
     * 清除导航菜单
     */
    abstract public void clearListNavigation();

    /**
     * 获取actionMenu
     *
     * @return
     */
    abstract public ActionMenu getActionMenu();

    /**
     * 获取所有菜单按钮
     *
     * @return
     */
    abstract public ArrayList<ActionMenuItem> getMenus();

    /**
     * 设置菜单按钮
     *
     * @param actions
     */
    abstract protected void setMenus(ArrayList<ActionMenuItem> actions);

    /**
     * 清除所有菜单按钮
     */
    abstract public void clearActionMenus();

    /**
     * 创建ActionTab
     *
     * @return
     */
    abstract public ActionTab getActionTab();

    /**
     * 获取所有tabItem
     *
     * @return
     */
    abstract public ArrayList<ActionTabItem> getTabs();

    /**
     * 设置标题栏tabItems
     *
     * @param tabs
     */
    abstract protected void setTabs(ArrayList<ActionTabItem> tabs);

    /**
     * 清除所有tabItem
     */
    abstract public void clearActionTabs();

    /**
     * 添加自定义view
     * 背景透明，所占空间为[指示器右侧到menu左侧]
     * 此时title会被隐藏，tab会被移除
     */
    abstract public void setCustomView(View view);

    /**
     * 移除自定义view
     */
    abstract public void removeCustomView();

    /**
     * 获取view
     */
    abstract public View findViewById(@IdRes int id);
}
