package mobi.cangol.mobile.actionbar.view

import android.annotation.TargetApi
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.*
import mobi.cangol.mobile.actionbar.R
import java.util.*
import android.widget.TextView
import android.widget.BaseAdapter

/*
 * @author Cangol
 */
class SearchView : LinearLayout {
    private var mSearchEditText: ClearableEditText? = null
    private var mActionButton: ImageView? = null
    private var mListView: ListView? = null
    private var mSearchAdapter: SearchAdapter? = null
    private var mOnSearchTextListener: OnSearchTextListener? = null
    private var mOnIndicatorClickListener: OnIndicatorClickListener? = null

    private var mInflater: LayoutInflater? = null
    private var mSearchHistory: MutableSet<String>? = HashSet()
    private var mSharedPreferences: SharedPreferences? = null
    private var mIsSearchHistory = true
    private var mOnTouchOutSiteDismiss = false

    constructor(context: Context) : super(context) {
        mSharedPreferences = context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE)
        mInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mInflater?.inflate(R.layout.actionbar_search_view, this, true)
        initViews()
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        mSharedPreferences = context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE)
        mInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mInflater?.inflate(R.layout.actionbar_search_view, this, true)
        initViews()
    }

    private fun initViews() {
        val arrow = DrawerArrowDrawable(this.resources, true)
        val typedValue = TypedValue()
        context.theme.resolveAttribute(R.attr.actionbar_indicator, typedValue, true)
        arrow.setStrokeColor(typedValue.data)
        arrow.setParameter(1f)

        val indicatorButton = this.findViewById(R.id.actionbar_search_indicator) as ImageView
        indicatorButton.setImageDrawable(arrow)
        mSearchEditText = this.findViewById(R.id.actionbar_search_text)
        mActionButton = this.findViewById(R.id.actionbar_search_action)
        mSearchEditText?.setOnEditorActionListener { v, actionId, event ->
            var result = false
            val keywords = mSearchEditText?.text.toString()
            if (TextUtils.isEmpty(keywords)) {
                mSearchEditText?.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake))
                result = true
            } else {
                result = mOnSearchTextListener?.onSearchText(keywords)?:false
                saveSearchHistory(keywords)
                hide()
            }
            result
        }
        mSearchEditText?.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
            //do nothings
        }
        mActionButton?.setOnClickListener {
            val keywords = mSearchEditText?.text.toString()
            if (TextUtils.isEmpty(keywords)) {
                mSearchEditText?.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake))
            } else {
                mOnSearchTextListener?.onSearchText(keywords)
                saveSearchHistory(keywords)
                hide()
            }
        }
        indicatorButton.setOnClickListener {
            hide()
            mOnIndicatorClickListener?.onIndicatorClick()
        }

        mListView = this.findViewById<View>(R.id.actionbar_search_list) as ListView
        mSearchAdapter = SearchAdapter(context)
        mListView?.adapter = mSearchAdapter
        mListView?.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val keywords = parent.getItemAtPosition(position) as String
            mOnSearchTextListener?.onSearchText(keywords)
            hide()
        }
        mSearchAdapter?.setOnClearClickListener(object : SearchAdapter.OnClearClickListener {
            override fun onClearClick(position: Int) {
                val item = mSearchAdapter?.getItem(position).toString()
                removeSearchHistory(item)
                mSearchAdapter?.remove(position)
                if (mSearchAdapter?.getList()?.isNullOrEmpty()==false) {
                    mListView?.visibility = View.VISIBLE
                } else {
                    mListView?.visibility = View.GONE
                }
            }
        })

        findViewById<View>(R.id.actionbar_search_content).setOnClickListener {
            if (mOnTouchOutSiteDismiss)
                hide()
        }
    }

    fun setSearchIconShow(show: Boolean) {
        mActionButton?.visibility = if (show) View.VISIBLE else View.GONE
    }

    fun setSearchDrawableShow(show: Boolean) {
        val typedValue = TypedValue()
        context.theme.resolveAttribute(R.attr.actionbar_search, typedValue, true)
        val imgSearch = resources.getDrawable(typedValue.resourceId)
        imgSearch.setBounds(0, 0, imgSearch.intrinsicWidth, imgSearch.intrinsicHeight)
        mSearchEditText?.setCompoundDrawables(if (show) imgSearch else null,
                mSearchEditText?.compoundDrawables?.get(1),
                mSearchEditText?.compoundDrawables?.get(2),
                mSearchEditText?.compoundDrawables?.get(3))
    }

    private fun showHistoryList() {
        if (!mIsSearchHistory) return
        val list = mutableListOf<String>()
        val iterator = mSearchHistory?.iterator()
        while (iterator?.hasNext() == true) {
            list.add(iterator?.next())
        }
        mSearchAdapter?.setList(list)
        mSearchAdapter?.notifyDataSetChanged()
        if (list.isNotEmpty()) {
            mListView?.visibility = View.VISIBLE
        } else {
            mListView?.visibility = View.GONE
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private fun saveSearchHistory(keywords: String) {
        if (!mIsSearchHistory) return
        mSearchHistory?.add(keywords)
        mSharedPreferences?.edit()?.putStringSet(KEY_HISTORY, mSearchHistory)?.apply()
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private fun removeSearchHistory(keywords: String) {
        if (!mIsSearchHistory) return
        mSearchHistory?.remove(keywords)
        mSharedPreferences?.edit()?.putStringSet(KEY_HISTORY, mSearchHistory)?.apply()
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    fun setIsSearchHistory(keywords: Array<String>) {
        if (!mIsSearchHistory) return
        mSearchHistory?.addAll(listOf(*keywords))
        mSharedPreferences?.edit()?.putStringSet(KEY_HISTORY, mSearchHistory)?.apply()
    }

    fun clearSearchHistory() {
        if (!mIsSearchHistory) return
        mSearchHistory?.clear()
        mSharedPreferences?.edit()?.clear()?.apply()
    }

    fun setSearchHistoryEnable(enable: Boolean) {
        mIsSearchHistory = enable
        if (mIsSearchHistory) {
            if (mSearchAdapter?.getList()?.isNullOrEmpty() == false) {
                mListView?.visibility = View.VISIBLE
            } else {
                mListView?.visibility = View.GONE
            }
        } else {
            mListView?.visibility = View.GONE
        }
    }

    fun searchHistoryEnable(): Boolean {
        return mIsSearchHistory
    }

    fun setmOnTouchOutSiteDismiss(mOnTouchOutSiteDismiss: Boolean) {
        this.mOnTouchOutSiteDismiss = mOnTouchOutSiteDismiss
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    fun show() {
        if (mIsSearchHistory) {
            mSearchHistory = mSharedPreferences?.getStringSet(KEY_HISTORY, HashSet())
            showHistoryList()
        }
        this.visibility = View.VISIBLE
        this.clearSearchText()

        getSearchEditText()?.requestFocus()
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(getSearchEditText(), 0)
    }

    fun hide() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(getSearchEditText()?.windowToken, 0)
        this.visibility = View.GONE
        this.clearSearchText()
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        super.onTouchEvent(ev)

        return true
    }

    fun setOnSearchTextListener(mOnSearchTextListener: OnSearchTextListener) {
        this.mOnSearchTextListener = mOnSearchTextListener
    }

    fun setOnIndicatorClickListener(onIndicatorClickListener: OnIndicatorClickListener) {
        this.mOnIndicatorClickListener = onIndicatorClickListener
    }

    fun setActioImageResource(resId: Int) {
        this.mActionButton?.setImageResource(resId)
    }


    fun getSearchEditText(): ClearableEditText? {
        return mSearchEditText
    }

    fun setSearchText(keywords: String) {
        mSearchEditText?.setText(keywords)
    }

    fun setSearchTextHint(hint: String) {
        mSearchEditText?.hint = hint
    }

    fun clearSearchText() {
        mSearchEditText?.text = null
    }

    interface OnIndicatorClickListener {

        fun onIndicatorClick(): Boolean

    }

    interface OnSearchTextListener {

        fun onSearchText(keywords: String): Boolean

    }

    companion object {
        private val SHARED_NAME = "search_history"
        private val KEY_HISTORY = "history"
    }
}

internal class SearchAdapter(context: Context) : BaseAdapter() {
    private var list: MutableList<String>? = null
    private val inflater: LayoutInflater
    private var onClearClickListener: OnClearClickListener? = null

    init {
        inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        list = ArrayList()
    }

    fun getList(): List<String>? {
        return list
    }

    fun setList(list: MutableList<String>) {
        this.list = list
    }

    override fun getCount(): Int {
        return list!!.size
    }

    override fun getItem(position: Int): String {
        return list!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun remove(position: Int) {
        list!!.removeAt(position)
        this.notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        if (convertView == null) {
            convertView = this.inflater.inflate(R.layout.actionbar_search_list_item, parent, false)
        }
        var labelView = convertView?.findViewById(R.id.actionbar_search_item_text) as TextView
        var clearView = convertView?.findViewById(R.id.actionbar_search_item_clear) as ImageView
        labelView?.text = getItem(position)
        clearView?.setOnClickListener {
                onClearClickListener?.onClearClick(position)
        }
        return convertView
    }

    fun setOnClearClickListener(onClearClickListener: OnClearClickListener) {
        this.onClearClickListener = onClearClickListener
    }

    interface OnClearClickListener {
        fun onClearClick(position: Int)
    }
}
