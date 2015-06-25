package mobi.cangol.mobile.actionbar.view;

import java.util.List;

import mobi.cangol.mobile.R;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
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

/**
* @Description: 
* @version $Revision: 1.0 $ 
* @author xuewu.wei
* @date: 2014-6-5
*/

public class SearchView extends LinearLayout {
	private static final String	TAG = "SearchView";
	public final static int VOICE_REQUEST_CODE=701;
	private ClearableEditText mSearchText;
	private ImageView mVoiceButton;
	private ImageView mIndicatoButton;
	private OnSearchTextListener mOnSearchTextListener;
	private OnVoiceClickListener mOnVoiceClickListener;
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
        	mVoiceButton.setVisibility(View.VISIBLE);
        }else{
        	mVoiceButton.setVisibility(View.GONE);
        }
	}
	private void initViews(){
		DrawerArrowDrawable arror=new DrawerArrowDrawable(this.getResources(),true);
		arror.setStrokeColor(Color.LTGRAY);
		arror.setParameter(1);
		mIndicatoButton=(ImageView) this.findViewById(R.id.actionbar_search_indicator);
		mIndicatoButton.setImageDrawable(arror);
		mSearchText=(ClearableEditText) this.findViewById(R.id.actionbar_search_text);
		mVoiceButton=(ImageView) this.findViewById(R.id.actionbar_search_voice);
		mSearchText.setOnEditorActionListener(new OnEditorActionListener() {
			
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				String keywords = mSearchText.getText().toString().trim();
				boolean result=false;
				if(mOnSearchTextListener!=null)
					result=mOnSearchTextListener.onSearchText(keywords);
				return result;
			}
			
		});
		mVoiceButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(mVoiceEnable&&mOnVoiceClickListener!=null)
					mOnVoiceClickListener.onVoiceClick();
			}
		
		});
		mIndicatoButton.setOnClickListener(new OnClickListener(){

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
	
	public void setVoiceSearchEnable(boolean mVoiceEnable) {
		this.mVoiceEnable = mVoiceEnable;
		if(mVoiceEnable){
        	mVoiceButton.setVisibility(View.VISIBLE);
        }else{
        	mVoiceButton.setVisibility(View.GONE);
        }
	}
	public void setOnSearchTextListener(OnSearchTextListener mOnSearchTextListener) {
		this.mOnSearchTextListener = mOnSearchTextListener;
	}
	public void setOnVoiceClickListener(OnVoiceClickListener mOnVoiceClickListener) {
		this.mOnVoiceClickListener = mOnVoiceClickListener;
	}
	public void setSearchText(String keywords){
		mSearchText.setText(keywords);
	}
	public void setSearchTextHint(String hint){
		mSearchText.setHint(hint);
	}
	public interface OnVoiceClickListener {

        boolean onVoiceClick();
        
    }
	
	public interface OnSearchTextListener {

		boolean onSearchText(String keywords);
        
    }

	public void clearText() {
		mSearchText.setText(null);
	}
}
