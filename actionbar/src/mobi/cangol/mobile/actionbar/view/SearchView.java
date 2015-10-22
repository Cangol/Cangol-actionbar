package mobi.cangol.mobile.actionbar.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.speech.RecognizerIntent;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import junit.framework.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import mobi.cangol.mobile.actionbar.R;

/**
 * @author Cangol
 */

public class SearchView extends LinearLayout {
    private static final String TAG = "SearchView";
    private ClearableEditText mSearchEditText;
    private ImageView mActionButton;
    private ImageView mIndicatoButton;
    private ListView mListView;
    private LinearLayout mContentView;
    private SearchAdapter mSearchAdapter;
    private OnSearchTextListener mOnSearchTextListener;
    private OnActionClickListener mOnActionClickListener;

    private Context mContext;
    private LayoutInflater mInflater;
    private Set<String> mSearchHistory = new HashSet<String>();
    private SharedPreferences mSharedPreferences;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mSharedPreferences = mContext.getSharedPreferences("search_history", Context.MODE_PRIVATE);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(R.layout.actionbar_search_view, this, true);
        initViews();
        mSearchHistory = mSharedPreferences.getStringSet("history", new HashSet<String>());
    }

    private void initViews() {
        DrawerArrowDrawable arror = new DrawerArrowDrawable(this.getResources(), true);
        arror.setStrokeColor(getResources().getColor(R.color.actionbar_indicator));
        arror.setParameter(1);
        mIndicatoButton = (ImageView) this.findViewById(R.id.actionbar_search_indicator);
        mIndicatoButton.setImageDrawable(arror);
        mSearchEditText = (ClearableEditText) this.findViewById(R.id.actionbar_search_text);
        mActionButton = (ImageView) this.findViewById(R.id.actionbar_search_action);
        mSearchEditText.setOnEditorActionListener(new OnEditorActionListener() {

            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean result = false;
                if (TextUtils.isEmpty(mSearchEditText.getText())) {
                    mSearchEditText.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.shake));
                    result = true;
                } else {
                    String keywords = mSearchEditText.getText().toString().trim();
                    if (mOnSearchTextListener != null)
                        result = mOnSearchTextListener.onSearchText(keywords);
                    saveSearchHistory(keywords);
                    hide();
                }
                return result;
            }

        });
        mSearchEditText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus)
//                    showHistoryList();

            }
        });
        mActionButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mSearchEditText.getText())) {
                    mSearchEditText.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.shake));
                } else {
                    String keywords = mSearchEditText.getText().toString().trim();
                    if (mOnActionClickListener != null)
                        mOnActionClickListener.onActionClick(keywords);
                    saveSearchHistory(keywords);
                    hide();
                }

            }

        });
        mIndicatoButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                hide();
            }

        });

        mListView = (ListView) this.findViewById(R.id.actionbar_search_list);
        mSearchAdapter = new SearchAdapter(mContext);
        mListView.setAdapter(mSearchAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String keywords = (String) parent.getItemAtPosition(position);
                if (mOnSearchTextListener != null)
                    mOnSearchTextListener.onSearchText(keywords);
                hide();
            }
        });
        mSearchAdapter.setOnClearClickListener(new SearchAdapter.OnClearClickListener() {
            @Override
            public void onClearClick(int position) {
                String item = mSearchAdapter.getItem(position);
                removeSearchHistory(item);
                mSearchAdapter.remove(position);
                if (mSearchAdapter.getList().size() > 0) {
                    mListView.setVisibility(View.VISIBLE);
                } else {
                    mListView.setVisibility(View.GONE);
                }
            }
        });

        mContentView= (LinearLayout) this.findViewById(R.id.actionbar_search_content);
    }

    private void showHistoryList() {
        List<String> list = new ArrayList<>();
        Iterator<String> iterator = mSearchHistory.iterator();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        mSearchAdapter.setList(list);
        mSearchAdapter.notifyDataSetChanged();
        if (list.size() > 0) {
            mListView.setVisibility(View.VISIBLE);
        } else {
            mListView.setVisibility(View.GONE);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void saveSearchHistory(String keywords) {
        mSearchHistory.add(keywords);
        mSharedPreferences.edit().putStringSet("history", mSearchHistory).commit();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void removeSearchHistory(String keywords) {
        mSearchHistory.remove(keywords);
        mSharedPreferences.edit().putStringSet("history", mSearchHistory).commit();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void setSearchHistory(String[] keywords) {
        mSearchHistory.addAll(Arrays.asList(keywords));
        mSharedPreferences.edit().putStringSet("history", mSearchHistory).commit();
    }

    public void clearSearchHistory() {
        mSearchHistory.clear();
        mSharedPreferences.edit().clear().commit();
    }

    public void show(){
        this.setVisibility(View.VISIBLE);
        this.clearSearchText();
        showHistoryList();
    }
    public void hide(){
        this.setVisibility(View.GONE);
        this.clearSearchText();
    }
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        super.onTouchEvent(ev);
        hide();
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

    public void setSearchText(String keywords) {
        mSearchEditText.setText(keywords);
    }

    public void setSearchTextHint(String hint) {
        mSearchEditText.setHint(hint);
    }

    public interface OnActionClickListener {

        boolean onActionClick(String keywords);

    }

    public interface OnSearchTextListener {

        boolean onSearchText(String keywords);

    }

    public void clearSearchText() {
        mSearchEditText.setText(null);
    }
}

class SearchAdapter extends BaseAdapter {
    List<String> list;
    Context context;
    LayoutInflater inflater;

    public SearchAdapter(Context context) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        list = new ArrayList<String>();
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public String getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void remove(int position) {
        list.remove(position);
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = this.inflater.inflate(R.layout.actionbar_search_list_item, parent, false);
            holder = new ViewHolder();
            holder.labelView = (TextView) convertView.findViewById(R.id.actionbar_search_item_text);
            holder.clearView = (ImageView) convertView.findViewById(R.id.actionbar_search_item_clear);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.labelView.setText(getItem(position));
        holder.clearView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClearClickListener != null)
                    onClearClickListener.onClearClick(position);
            }
        });

        return convertView;
    }

    class ViewHolder {
        TextView labelView;
        ImageView clearView;
    }

    private OnClearClickListener onClearClickListener;


    public void setOnClearClickListener(OnClearClickListener onClearClickListener) {
        this.onClearClickListener = onClearClickListener;
    }

    public interface OnClearClickListener {

        void onClearClick(int position);

    }
}
