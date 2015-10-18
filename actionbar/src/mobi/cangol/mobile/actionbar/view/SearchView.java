package mobi.cangol.mobile.actionbar.view;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.speech.RecognizerIntent;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import junit.framework.Test;

import java.util.List;

import mobi.cangol.mobile.actionbar.R;

/**
* @author Cangol
*/

public class SearchView extends LinearLayout {
	private static final String	TAG = "SearchView";
	private ClearableEditText mSearchEditText;
	private ImageView mActionButton;
	private ImageView mIndicatoButton;
	private OnSearchTextListener mOnSearchTextListener;
	private OnActionClickListener mOnActionClickListener;
    private Context mContext;
	public SearchView(Context context, AttributeSet attrs) {
		super(context, attrs);
        mContext=context;
		LayoutInflater inflater = (LayoutInflater) context .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.actionbar_search_view, this, true);
        initViews();
	}
	private void initViews(){
		DrawerArrowDrawable arror=new DrawerArrowDrawable(this.getResources(),true);
		arror.setStrokeColor(getResources().getColor(R.color.actionbar_indicator));
		arror.setParameter(1);
		mIndicatoButton=(ImageView) this.findViewById(R.id.actionbar_search_indicator);
		mIndicatoButton.setImageDrawable(arror);
        mSearchEditText=(ClearableEditText) this.findViewById(R.id.actionbar_search_text);
		mActionButton=(ImageView) this.findViewById(R.id.actionbar_search_action);
        mSearchEditText.setOnEditorActionListener(new OnEditorActionListener() {

            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean result = false;
                if (TextUtils.isEmpty(mSearchEditText.getText())) {
                    mSearchEditText.startAnimation(AnimationUtils.loadAnimation(mContext,R.anim.shake));
                } else {
                    String keywords = mSearchEditText.getText().toString().trim();
                    if (mOnSearchTextListener != null)
                        result = mOnSearchTextListener.onSearchText(keywords);
                }
                return result;
            }

        });
        mActionButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mSearchEditText.getText())) {
                    mSearchEditText.startAnimation(AnimationUtils.loadAnimation(mContext,R.anim.shake));
                } else {
                    String keywords = mSearchEditText.getText().toString().trim();
                    if (mOnActionClickListener != null)
                        mOnActionClickListener.onActionClick(keywords);
                }

            }

        });
        mIndicatoButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                setVisibility(View.GONE);
            }

        });
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		super.onTouchEvent(ev);
	    return true;
	}
	public void setOnSearchTextListener(OnSearchTextListener mOnSearchTextListener) {
		this.mOnSearchTextListener = mOnSearchTextListener;
	}
	public void setActioImageResource(int resId) {
		this.mActionButton.setImageResource(resId);
	}
	public void setOnActionClickListener(OnActionClickListener mOnActionClickListener) {
		this.mOnActionClickListener = mOnActionClickListener;
	}

    public ClearableEditText geSearchEditText() {
        return mSearchEditText;
    }

    public void setSearchText(String keywords){
        mSearchEditText.setText(keywords);
	}
	public void setSearchTextHint(String hint){
        mSearchEditText.setHint(hint);
	}
	public interface OnActionClickListener {

        boolean onActionClick(String keywords);
        
    }
	
	public interface OnSearchTextListener {

		boolean onSearchText(String keywords);
        
    }

	public void clearText() {
        mSearchEditText.setText(null);
	}
}
