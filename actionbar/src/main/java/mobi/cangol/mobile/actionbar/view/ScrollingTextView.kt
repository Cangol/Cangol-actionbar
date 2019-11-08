package mobi.cangol.mobile.actionbar.view

import android.content.Context
import android.graphics.Rect
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet

/**
 * @author marco
 * Workaround to be able to scroll text inside a TextView without it required
 * to be focused. For some strange reason there isn't an easy way to do this
 * natively.
 *
 *
 * Original code written by Evan Cummings:
 * http://androidbears.stellarpc.net/?p=185
 */
class ScrollingTextView : AppCompatTextView {


    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {}

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        if (focused) {
            super.onFocusChanged(focused, direction, previouslyFocusedRect)
        }
    }

    override fun onWindowFocusChanged(focused: Boolean) {
        if (focused) {
            super.onWindowFocusChanged(focused)
        }
    }

    override fun isFocused(): Boolean {
        return true
    }
}
