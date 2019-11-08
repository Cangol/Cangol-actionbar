package mobi.cangol.mobile.actionbar.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v7.widget.AppCompatEditText
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View.OnFocusChangeListener
import android.view.View.OnTouchListener
import mobi.cangol.mobile.actionbar.R


/**
 * @author Cangol
 */
class ClearableEditText : AppCompatEditText {

    private var imgX: Drawable? = null
    private var textWatcher: TextWatcher? = null

    constructor(context: Context) : super(context) {
        initViews()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        initViews()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initViews()
    }

    private fun initViews() {
        val typedValue = TypedValue()
        context.theme.resolveAttribute(R.attr.actionbar_clear, typedValue, true)
        imgX = resources.getDrawable(typedValue.resourceId)

        // Set bounds of our X button
        imgX?.setBounds(0, 0, imgX?.intrinsicWidth?:0, imgX?.intrinsicHeight?:0)

        // There may be initial text in the field, so we may need to display the button
        manageClearButton()

        this.setOnTouchListener(OnTouchListener { v, event ->
            val et = this@ClearableEditText

            // Is there an X showing?
            if (et.compoundDrawables[2] == null) return@OnTouchListener false
            // Only do this for up touches
            if (event.action != MotionEvent.ACTION_UP) return@OnTouchListener false
            // Is touch on our clear button?
            if (event.x > et.width - et.paddingRight - (imgX?.intrinsicWidth?:0)) {
                et.setText("")
                removeClearButton()
            }
            false
        })
        textWatcher = object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                //do nothings
            }

            override fun afterTextChanged(arg0: Editable) {
                manageClearButton()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                //do nothings
            }
        }
        this.addTextChangedListener(textWatcher)
        this.onFocusChangeListener = OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                removeClearButton()
            } else {
                manageClearButton()
            }
        }
    }

    private fun manageClearButton() {
        if (TextUtils.isEmpty(text)) {
            removeClearButton()
        } else if (this.hasFocus()) {
            addClearButton()
        }

    }

    private fun addClearButton() {
        this.setCompoundDrawables(this.compoundDrawables[0],
                this.compoundDrawables[1],
                if (error == null) imgX else this.compoundDrawables[2],
                this.compoundDrawables[3])
    }

    private fun removeClearButton() {
        this.setCompoundDrawables(this.compoundDrawables[0],
                this.compoundDrawables[1],
                if (error == null) null else this.compoundDrawables[2],
                this.compoundDrawables[3])
    }

    override fun onDetachedFromWindow() {
        this.removeTextChangedListener(textWatcher)
        super.onDetachedFromWindow()
    }
}