package mobi.cangol.mobile.actionbar.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

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
    private OnIndicatorClickListener mOnIndicatorClickListener;

    private Context mContext;
    private LayoutInflater mInflater;
    private Set<String> mSearchHistory = new HashSet<String>();
    private SharedPreferences mSharedPreferences;
    private boolean mIsSearchHistory = true;
    private boolean mOnTouchOutSiteDismiss = false;

    public SearchView(Context context) {
        super(context);
        mContext = context;
        mSharedPreferences = mContext.getSharedPreferences("search_history", Context.MODE_PRIVATE);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(R.layout.actionbar_search_view, this, true);
        initViews();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mSharedPreferences = mContext.getSharedPreferences("search_history", Context.MODE_PRIVATE);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(R.layout.actionbar_search_view, this, true);
        initViews();
    }

    private void initViews() {
        DrawerArrowDrawable arrow = new DrawerArrowDrawable(this.getResources(), true);
        TypedValue typedValue = new TypedValue();
        mContext.getTheme().resolveAttribute(R.attr.actionbar_indicator, typedValue, true);
        arrow.setStrokeColor(typedValue.data);
        arrow.setParameter(1);

        mIndicatoButton = (ImageView) this.findViewById(R.id.actionbar_search_indicator);
        mIndicatoButton.setImageDrawable(arrow);
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
                    if (mOnSearchTextListener != null)
                        mOnSearchTextListener.onSearchText(keywords);
                    saveSearchHistory(keywords);
                    hide();
                }

            }

        });
        mIndicatoButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                hide();
                if (mOnIndicatorClickListener != null)
                    mOnIndicatorClickListener.onIndicatorClick();
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

        mContentView = (LinearLayout) this.findViewById(R.id.actionbar_search_content);
        mContentView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnTouchOutSiteDismiss)
                    hide();
            }
        });
    }

    public void setSearchIconShow(boolean show) {
        mActionButton.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public void setSearchDrawableShow(boolean show) {
        TypedValue typedValue = new TypedValue();
        mContext.getTheme().resolveAttribute(R.attr.actionbar_search, typedValue, true);
        Drawable imgSearch = getResources().getDrawable(typedValue.resourceId);
        imgSearch.setBounds(0, 0, imgSearch.getIntrinsicWidth(), imgSearch.getIntrinsicHeight());
        mSearchEditText.setCompoundDrawables(show ? imgSearch : null,
                mSearchEditText.getCompoundDrawables()[1],
                mSearchEditText.getCompoundDrawables()[2],
                mSearchEditText.getCompoundDrawables()[3]);
    }

    private void showHistoryList() {
        if (!mIsSearchHistory) return;
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
        if (!mIsSearchHistory) return;
        mSearchHistory.add(keywords);
        mSharedPreferences.edit().putStringSet("history", mSearchHistory).commit();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void removeSearchHistory(String keywords) {
        if (!mIsSearchHistory) return;
        mSearchHistory.remove(keywords);
        mSharedPreferences.edit().putStringSet("history", mSearchHistory).commit();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void setIsSearchHistory(String[] keywords) {
        if (!mIsSearchHistory) return;
        mSearchHistory.addAll(Arrays.asList(keywords));
        mSharedPreferences.edit().putStringSet("history", mSearchHistory).commit();
    }

    public void clearSearchHistory() {
        if (!mIsSearchHistory) return;
        mSearchHistory.clear();
        mSharedPreferences.edit().clear().commit();
    }

    public void setSearchHistoryEnable(boolean enable) {
        mIsSearchHistory = enable;
        if (mIsSearchHistory) {
            if (mSearchAdapter.getList().size() > 0) {
                mListView.setVisibility(View.VISIBLE);
            } else {
                mListView.setVisibility(View.GONE);
            }
        } else {
            mListView.setVisibility(View.GONE);
        }
    }
    public boolean searchHistoryEnable() {
        return mIsSearchHistory;
    }
    public void setmOnTouchOutSiteDismiss(boolean mOnTouchOutSiteDismiss) {
        this.mOnTouchOutSiteDismiss = mOnTouchOutSiteDismiss;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void show() {
        if (mIsSearchHistory) {
            mSearchHistory = mSharedPreferences.getStringSet("history", new HashSet<String>());
            showHistoryList();
        }
        this.setVisibility(View.VISIBLE);
        this.clearSearchText();

        geSearchEditText().requestFocus();
        //geSearchEditText().setFocusable(true);
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(geSearchEditText(), 0);
    }

    public void hide() {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(geSearchEditText().getWindowToken(), 0);
        this.setVisibility(View.GONE);
        this.clearSearchText();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        super.onTouchEvent(ev);

        return true;
    }

    public void setOnSearchTextListener(OnSearchTextListener mOnSearchTextListener) {
        this.mOnSearchTextListener = mOnSearchTextListener;
    }

    public void setOnIndicatorClickListener(OnIndicatorClickListener onIndicatorClickListener) {
        this.mOnIndicatorClickListener = onIndicatorClickListener;
    }

    public void setActioImageResource(int resId) {
        this.mActionButton.setImageResource(resId);
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

    public void clearSearchText() {
        mSearchEditText.setText(null);
    }

    public interface OnIndicatorClickListener {

        boolean onIndicatorClick();

    }

    public interface OnSearchTextListener {

        boolean onSearchText(String keywords);

    }
}

class SearchAdapter extends BaseAdapter {
    List<String> list;
    Context context;
    LayoutInflater inflater;
    private OnClearClickListener onClearClickListener;

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

    public void setOnClearClickListener(OnClearClickListener onClearClickListener) {
        this.onClearClickListener = onClearClickListener;
    }


    public interface OnClearClickListener {

        void onClearClick(int position);

    }

    class ViewHolder {
        TextView labelView;
        ImageView clearView;
    }
}
