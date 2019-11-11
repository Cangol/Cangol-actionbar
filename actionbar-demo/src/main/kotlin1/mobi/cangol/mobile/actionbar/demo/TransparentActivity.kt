package mobi.cangol.mobile.actionbar.demo

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import mobi.cangol.mobile.actionbar.ActionBarActivity

/**
 * Created by xuewu.wei on 2019/5/5.
 */
class TransparentActivity : ActionBarActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transparent_view)
        this.getCustomActionBar().displayUpIndicator()
        this.setActionbarShadow(true, 9f)
        this.title = this.javaClass.simpleName.replace("Activity", "")
        findViews()
        findViewById<View>(android.support.v7.appcompat.R.id.action_mode_bar_stub)
    }

    fun findViews() {
        setActionbarOverlay(true)
        this.findViewById<View>(R.id.button_transparent_0)?.setOnClickListener {
            setSystemUiFloatFullScreen(false)
            setActionbarShow(true)
            setStatusBarTintColor(ContextCompat.getColor(applicationContext, R.color.actionbar_background))
            getCustomActionBar().setBackgroundResource(R.color.actionbar_background)
        }
        this.findViewById<View>(R.id.button_transparent_1)?.setOnClickListener {
            setStatusBarTintColor(ContextCompat.getColor(applicationContext, R.color.translucent))
            getCustomActionBar().setBackgroundResource(R.color.translucent)

        }
        this.findViewById<View>(R.id.button_transparent_2)?.setOnClickListener {
            setStatusBarTintColor(ContextCompat.getColor(applicationContext, R.color.transparent))
            getCustomActionBar().setBackgroundResource(R.color.transparent)
        }

        this.findViewById<View>(R.id.button_transparent_3)?.setOnClickListener {
            setSystemUiFloatFullScreen(true)
        }
        this.findViewById<View>(R.id.button_transparent_4)?.setOnClickListener {
            setSystemUiFloatFullScreen(false)
        }

        this.findViewById<View>(R.id.button_transparent_5)?.setOnClickListener {
            setActionbarShow(true)

        }
        this.findViewById<View>(R.id.button_transparent_6)?.setOnClickListener {
            setActionbarShow(false)
        }
    }
}
