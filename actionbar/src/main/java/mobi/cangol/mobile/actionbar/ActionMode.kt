package mobi.cangol.mobile.actionbar

/**
 * @author Cangol
 */
abstract class ActionMode {

    /**
     * 设置标题
     *
     * @param title
     */
    abstract var title: CharSequence

    /**
     * 返回模式是运行
     *
     * @return
     */
    abstract val isActionMode: Boolean

    /**
     * 设置标题
     *
     * @param resId
     */
    abstract fun setTitle(resId: Int)

    /**
     * 启动action
     *
     * @param callback
     */
    abstract fun start(callback: Callback)

    /**
     * 完成结束action
     */
    abstract fun finish()

    /**
     * action mode 回调
     */
    interface Callback {

        fun onCreateActionMode(mode: ActionMode, actionMenu: ActionMenu)

        fun onActionItemClicked(mode: ActionMode, menuItem: ActionMenuItem): Boolean

        fun onDestroyActionMode(mode: ActionMode)
    }

}
