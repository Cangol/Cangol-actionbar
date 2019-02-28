package mobi.cangol.mobile.actionbar.internal;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

import mobi.cangol.mobile.actionbar.ActionMenu;
import mobi.cangol.mobile.actionbar.ActionMenuItem;
import mobi.cangol.mobile.actionbar.view.ActionMenuView;
import mobi.cangol.mobile.actionbar.view.ActionMenuView.OnActionClickListener;

/**
 * @author Cangol
 */
public final class ActionMenuImpl extends ActionMenu {
    private List<ActionMenuItem> mActions = new ArrayList<>();
    private ActionMenuView mActionMenuView;

    public ActionMenuImpl(ActionMenuView view) {
        this.mActionMenuView = view;
        this.mActionMenuView.setActionMenu(this);
    }

    @Override
    public List<ActionMenuItem> getActions() {
        return mActions;
    }

    @Override
    public void setActions(List<ActionMenuItem> actions) {
        if (actions != null){
            mActions=actions;
        }

        mActionMenuView.addActions(mActions);

    }

    @Override
    public ActionMenuItem addMenu(int id, int text, int drawable, int show) {
        ActionMenuItem item = new ActionMenuItem(id, text, drawable, show);
        mActions.add(item);
        mActionMenuView.addAction(item);
        return item;
    }

    @Override
    public void clear() {
        mActions.clear();
        mActionMenuView.removeAllActions();

    }

    @Override
    public int size() {
        return mActions.size();
    }

    @Override
    public ActionMenuItem getAction(int index) {
        return mActions.get(index);
    }

    @Override
    public void setOnActionClickListener(
            OnActionClickListener onActionClickListener) {
        mActionMenuView.setOnActionClickListener(onActionClickListener);

    }

    @Override
    public View getActionMenuItemView(int id) {
        return mActionMenuView.findViewById(id);
    }

}
