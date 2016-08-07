package mobi.cangol.mobile.actionbar.internal;

import android.app.Activity;
import android.view.View;

import mobi.cangol.mobile.actionbar.ActionMenuInflater;
import mobi.cangol.mobile.actionbar.ActionMenuItem;
import mobi.cangol.mobile.actionbar.ActionMode;
import mobi.cangol.mobile.actionbar.view.ActionMenuView.OnActionClickListener;
import mobi.cangol.mobile.actionbar.view.ActionModeView;

/**
 * @author Cangol
 */
public final class ActionModeImpl extends ActionMode {
    private ActionModeView mActionModeView;
    private ActionMode.Callback mActionModeCallback;
    private boolean mActionMode;
    private Activity mContext;

    public ActionModeImpl(Activity context, ActionModeView view) {
        mContext = context;
        mActionModeView = view;
        mActionModeView.setActionMode(this);
    }

    @Override
    public void setTitle(CharSequence title) {
        mActionModeView.setTitle(title);
    }

    @Override
    public CharSequence getTitle() {
        return mActionModeView.getTitle();
    }

    @Override
    public void setTitle(int resId) {
        mActionModeView.setTitle(resId);
    }

    @Override
    public void finish() {
        mActionModeView.getActionMenu().clear();
        mActionModeView.setVisibility(View.GONE);
        if (mActionModeCallback != null) {
            mActionModeCallback.onDestroyActionMode(this);
        }
        mActionMode = false;
    }

    @Override
    public void start(Callback callback) {
        mActionModeView.setVisibility(View.VISIBLE);
        mActionModeCallback = callback;
        if (mActionModeCallback != null) {
            mActionModeCallback.onCreateActionMode(this, mActionModeView.getActionMenu());
        }
        mActionModeView.setOnActionClickListener(new OnActionClickListener() {

            @Override
            public boolean onActionClick(ActionMenuItem action) {
                if (mActionModeCallback != null) {
                    return mActionModeCallback.onActionItemClicked(ActionModeImpl.this, action);
                }
                return false;
            }

        });

        mActionMode = true;
    }

    @Override
    public ActionMenuInflater getMenuInflater() {
        return new ActionMenuInflater(mActionModeView.getActionMenu(), mContext);
    }

    @Override
    public boolean isActionMode() {
        return mActionMode;
    }
}
