package mobi.cangol.mobile.actionbar;

/**
 * @author Cangol
 */
public abstract class ActionMode {
    /**
     * 设置标题
     * @param title
     */
	public abstract void setTitle(CharSequence title);

    /**
     * 设置标题
     * @param resId
     */
	public abstract void setTitle(int resId);

    /**
     * 获取标题
     * @return
     */
	public abstract CharSequence getTitle();

    /**
     * 启动action
     * @param callback
     */
	public abstract void start(Callback callback);

    /**
     * 完成结束action
     */
	public abstract void finish();

    /**
     * 获取菜单Inflater
     * @return
     */
    public abstract ActionMenuInflater getMenuInflater();

    /**
     * actionmode 回调
     */
    public interface Callback {
    	
        public void onCreateActionMode(ActionMode mode,ActionMenu actionMenu);

        public boolean onActionItemClicked(ActionMode mode, ActionMenuItem menuItem);

        public void onDestroyActionMode(ActionMode mode);
    }

    /**
     * 返回模式是运行
     * @return
     */
	public abstract boolean isActionMode();

}
