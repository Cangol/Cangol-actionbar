package mobi.cangol.mobile.actionbar.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import mobi.cangol.mobile.actionbar.ActionMenu;
import mobi.cangol.mobile.actionbar.ActionMode;
import mobi.cangol.mobile.actionbar.R;
import mobi.cangol.mobile.actionbar.internal.ActionMenuImpl;
import mobi.cangol.mobile.actionbar.view.ActionMenuView.OnActionClickListener;

/**
 * @author Cangol
 */
public class ActionModeView extends LinearLayout {
    private LayoutInflater mInflater;
    private ImageView mDoneButton;
    private TextView mTitleText;
    private ActionMenu mActionMenu;
    private ActionMode mActionMode;

    public ActionModeView(Context context) {
        super(context);
        initViews(context);
    }

    public ActionModeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
    }

    public ActionModeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ActionModeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initViews(context);
    }

    public void setActionMode(ActionMode mActionMode) {
        this.mActionMode = mActionMode;
    }

    private void initViews(Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(R.layout.actionbar_action_mode, this);

        mActionMenu = new ActionMenuImpl((ActionMenuView) this.findViewById(R.id.actionbar_mode_menu));
        mDoneButton = (ImageView) this.findViewById(R.id.actionbar_mode_done);
        mTitleText = (TextView) this.findViewById(R.id.actionbar_mode_title);

        mDoneButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mActionMode.finish();
            }

        });
    }

    public void setTitle(int resId) {
        mTitleText.setText(resId);
    }

    public CharSequence getTitle() {
        return mTitleText.getText();
    }

    public void setTitle(CharSequence title) {
        mTitleText.setText(title);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        super.onTouchEvent(ev);
        return true;
    }

    public void setOnActionClickListener(OnActionClickListener onActionClickListener) {
        mActionMenu.setOnActionClickListener(onActionClickListener);
    }

    public ActionMenu getActionMenu() {
        return mActionMenu;
    }

}
