package mobi.cangol.mobile.actionbar.view;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.speech.RecognizerIntent;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import java.util.List;

import mobi.cangol.mobile.actionbar.R;

/**
* @author Cangol
*/

public class SearchView extends LinearLayout {
	private static final String	TAG = "SearchView";
	private ClearableEditText mSearchText;
	private ImageView mActionButton;
	private ImageView mIndicatoButton;
	private OnSearchTextListener mOnSearchTextListener;
	private OnActionClickListener mOnActionClickListener;
	private boolean mVoiceEnable=false;
	public SearchView(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater inflater = (LayoutInflater) context .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.actionbar_search_view, this, true);
        initViews();
        initVoiceBtn();
	}
	private void initVoiceBtn(){
		PackageManager pm =this.getContext().getPackageManager();
        List<ResolveInfo> list = pm.queryIntentActivities(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if(list.size()!=0){
        	mVoiceEnable=true;
        }else{
        	mVoiceEnable=false;
        }
	}
	private void initViews(){
		DrawerArrowDrawable arror=new DrawerArrowDrawable(this.getResources(),true);
		arror.setStrokeColor(getResources().getColor(R.color.actionbar_indicator));
		arror.setParameter(1);
		mIndicatoButton=(ImageView) this.findViewById(R.id.actionbar_search_indicator);
		mIndicatoButton.setImageDrawable(arror);
		mSearchText=(ClearableEditText) this.findViewById(R.id.actionbar_search_text);
		mActionButton=(ImageView) this.findViewById(R.id.actionbar_search_action);
		mSearchText.setOnEditorActionListener(new OnEditorActionListener() {
			
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				String keywords = mSearchText.getText().toString().trim();
				boolean result=false;
				if(mOnSearchTextListener!=null)
					result=mOnSearchTextListener.onSearchText(keywords);
				return result;
			}
			
		});
		mActionButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(mVoiceEnable&&mOnActionClickListener!=null)
					mOnActionClickListener.onActionClick();
			}
		
		});
		mIndicatoButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				setVisibility(View.GONE);
			}
		
		});
		setActionButtonEnable(mVoiceEnable);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		super.onTouchEvent(ev);
	    return true;
	}
	
	public void setActionButtonEnable(boolean mVoiceEnable) {
		this.mVoiceEnable = mVoiceEnable;
		if(mVoiceEnable){
			mActionButton.setVisibility(View.VISIBLE);
        }else{
        	mActionButton.setVisibility(View.INVISIBLE);
        }
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
	public void setSearchText(String keywords){
		mSearchText.setText(keywords);
	}
	public void setSearchTextHint(String hint){
		mSearchText.setHint(hint);
	}
	public interface OnActionClickListener {

        boolean onActionClick();
        
    }
	
	public interface OnSearchTextListener {

		boolean onSearchText(String keywords);
        
    }

	public void clearText() {
		mSearchText.setText(null);
	}
}
