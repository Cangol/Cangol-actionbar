package mobi.cangol.mobile.actionbar.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.RadioGroup.OnCheckedChangeListener
import mobi.cangol.mobile.actionbar.ActionTab
import mobi.cangol.mobile.actionbar.ActionTabItem
import mobi.cangol.mobile.actionbar.R

/**
 * @author Cangol
 */
class ActionTabView : RadioGroup, OnCheckedChangeListener {
    private var mInflater: LayoutInflater? = null
    private var mActionTab: ActionTab? = null
    private var mOnTabSelectedListener: OnTabSelectedListener? = null

    val tabs: List<ActionTabItem>
        get() = mActionTab!!.tabs

    var tabSelected: Int
        get() = this.checkedRadioButtonId
        set(id) = this.check(id)

    constructor(context: Context) : super(context) {
        initViews(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initViews(context)
    }

    private fun initViews(context: Context) {
        this.orientation = LinearLayout.HORIZONTAL
        mInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        this.setOnCheckedChangeListener(this)
    }

    fun setActionTab(actionTab: ActionTab) {
        this.mActionTab = actionTab
    }

    fun addTabs(tabs: List<ActionTabItem>) {
        for (tabItem in tabs) {
            addTabItem(tabItem)
        }
    }

    fun addTabItem(tabItem: ActionTabItem) {
        this.addView(inflateTabItem(tabItem))
    }

    fun removeAllTabs() {
        this.removeAllViews()
    }

    private fun inflateTabItem(tabItem: ActionTabItem): View {
        val view = mInflater?.inflate(R.layout.actionbar_tab_item, this, false)

        val labelView = view?.findViewById(R.id.actionbar_tab_item) as RadioButton
        labelView.id = tabItem.id
        labelView.text = tabItem.text
        labelView.isChecked = 1 == tabItem.selected
        labelView.tag = tabItem
        return view
    }

    fun setOnTabSelectedListener(onTabSelectedListener: OnTabSelectedListener) {
        this.mOnTabSelectedListener = onTabSelectedListener
    }
    fun removeTabSelectedListener() {
        this.mOnTabSelectedListener = null
    }
    override fun onCheckedChanged(group: RadioGroup, checkedId: Int) {
        val view = this.findViewById<View>(checkedId) ?: return
        if (view.tag is ActionTabItem) {
           mOnTabSelectedListener?.onTabSelected(view.tag as ActionTabItem)
        }
    }

    interface OnTabSelectedListener {
        fun onTabSelected(tab: ActionTabItem): Boolean
    }

}
