package mobi.cangol.mobile.actionbar;

import android.support.annotation.IdRes;
import android.view.View;
import android.view.View.OnClickListener;

import java.util.List;

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
    public abstract void setCustomHomeAsUpIndicator(int homeId, int upId);

    /**
     */
    public abstract void resetCustomHomeAsUpIndicator();

    /**
     * 设置home的显示
     *
     * @param show
     */
    public abstract void setDisplayShowHomeEnabled(boolean show);


    public abstract void hideHomeAsUpIndicator();
    /**
     * 显示home指示器
     */
    public abstract void displayHomeIndicator();

    /**
     * 显示Up指示器
     */
    public abstract void displayUpIndicator();

    /**
     * 设置指示器显示的offset，只对原生指示器起作用
     *
     * @param slideOffset
     */
    public abstract void displayIndicator(float slideOffset);

    /**
     * 设置指示器显示的颜色，只对原生指示器起作用
     *
     * @param color
     */
    public abstract void setIndicatorColor(int color);

    /**
     * 设置左侧按钮
     *
     * @param id       菜单唯一Id
     * @param text     文本资源string的id，必须有效
     * @param icon     图片资源的id，如果无，请填-1
     * @param listener 点击事件listener
     */
    public abstract void setLeftMenu(final int id, final int text, int icon, OnClickListener listener);

    /**
     * 清除左侧按钮
     */
    public abstract void clearLeftMenu();

    /**
     * back时执行
     *
     * @return
     */
    protected abstract boolean onBackPressed();

    /**
     * 返回是否显示
     *
     * @return
     */
    protected abstract boolean isShow();

    /**
     * 设置显示
     *
     * @param show
     */
    protected abstract void setShow(boolean show);

    /**
     * 设置背景颜色
     *
     * @param color
     */
    public abstract void setBackgroundColor(int color);

    /**
     * 设置背景
     *
     * @param resId
     */
    public abstract void setBackgroundResource(int resId);

    /**
     * 获取标题
     *
     * @return
     */
    public abstract CharSequence getTitle();

    /**
     * 设置标题
     *
     * @param resid
     */
    public abstract void setTitle(int resid);

    /**
     * 设置标题
     *
     * @param title
     */
    public abstract void setTitle(CharSequence title);

    /**
     * 设置标题对其方式
     *
     * @param gravity
     */
    public abstract void setTitleGravity(int gravity);
    /**
     * 设置标题Color
     *
     * @param color
     */
    public abstract void setTitleColor(int color);
    /**
     * 设置标题size
     *
     * @param size
     */
    public abstract void setTitleSize(float size);
    /**
     * 获取标题对其方式
     *
     * @return
     */
    public abstract int getTitleGravity();

    /**
     * 设置标题显示
     *
     * @param visibly
     */
    public abstract void setTitleVisibility(int visibly);

    /**
     * 获取标题显示
     */
    public abstract int getTitleVisibility();

    /**
     * 设置标题点击事件监听
     * 此事件与setOnNavigationListener相冲突，只有后设置的有效
     *
     * @param listener
     */
    public abstract void setOnTitleClickListener(OnClickListener listener);

    /**
     * 启动ActionMode
     *
     * @param callback
     * @return
     */
    public abstract ActionMode startActionMode(ActionMode.Callback callback);

    /**
     * 停止ActionMode
     */
    public abstract void stopActionMode();

    /**
     * 开启/关闭 刷新
     */
    public abstract void enableRefresh(boolean enable);


    /**
     * 开启/关闭 刷新left|right
     */
    public abstract void enableRefresh(boolean enable, int gravity);

    /**
     * 开始/停止 刷新
     */
    public abstract void refreshing(boolean refresh);

    /**
     * 刷新按钮点击事件
     *
     * @param listener
     */
    public abstract void setOnRefreshClickListener(OnClickListener listener);

    /**
     * 设置导航菜单
     * 此事件与setOnTitleClickListener相冲r突，只有后设置的有效
     *
     * @param onNavigationListener 导航监听
     */
    public abstract void setOnNavigationListener(OnNavigationListener onNavigationListener);

    /**
     * 获取导航菜单
     */
    public abstract String[] getListNavigation();

    /**
     * 设置导航菜单
     *
     * @param navs
     */
    public abstract void setListNavigation(String[] navs);

    /**
     * 清除导航菜单
     */
    public abstract void clearListNavigation();

    /**
     * 获取actionMenu
     *
     * @return
     */
    public abstract ActionMenu getActionMenu();

    /**
     * 获取所有菜单按钮
     *
     * @return
     */
    public abstract List<ActionMenuItem> getMenus();

    /**
     * 设置菜单按钮
     *
     * @param actions
     */
    protected abstract void setMenus(List<ActionMenuItem> actions);

    /**
     * 清除所有菜单按钮
     */
    public abstract void clearActionMenus();

    /**
     * 创建ActionTab
     *
     * @return
     */
    public abstract ActionTab getActionTab();

    /**
     * 获取所有tabItem
     *
     * @return
     */
    public abstract List<ActionTabItem> getTabs();

    /**
     * 设置标题栏tabItems
     *
     * @param tabs
     */
    protected abstract void setTabs(List<ActionTabItem> tabs);

    /**
     * 清除所有tabItem
     */
    public abstract void clearActionTabs();

    /**
     * 添加自定义view
     * 背景透明，所占空间为[指示器右侧到menu左侧]
     * 此时title会被隐藏，tab会被移除
     */
    public abstract void setCustomView(View view);

    /**
     * 移除自定义view
     */
    public abstract void removeCustomView();

    /**
     * 获取view
     */
    public abstract <T extends View> T findViewById(@IdRes int id);
}
