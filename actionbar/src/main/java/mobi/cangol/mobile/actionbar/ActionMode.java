package mobi.cangol.mobile.actionbar;

/**
 * @author Cangol
 */
public abstract class ActionMode {
    /**
     * 设置标题
     *
     * @param title
     */
    public abstract void setTitle(CharSequence title);

    /**
     * 获取标题
     *
     * @return
     */
    public abstract CharSequence getTitle();

    /**
     * 设置标题
     *
     * @param resId
     */
    public abstract void setTitle(int resId);

    /**
     * 启动action
     *
     * @param callback
     */
    public abstract void start(Callback callback);

    /**
     * 完成结束action
     */
    public abstract void finish();

    /**
     * 获取菜单Inflater
     *
     * @return
     */
    public abstract ActionMenuInflater getMenuInflater();

    /**
     * 返回模式是运行
     *
     * @return
     */
    public abstract boolean isActionMode();

    /**
     * actionmode 回调
     */
    public interface Callback {

        void onCreateActionMode(ActionMode mode, ActionMenu actionMenu);

        boolean onActionItemClicked(ActionMode mode, ActionMenuItem menuItem);

        void onDestroyActionMode(ActionMode mode);
    }

}
