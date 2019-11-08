package mobi.cangol.mobile.actionbar.demo

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.WindowManager

/**
 * Created by xuewu.wei on 2019/5/5.
 */
class TransparentNativeActivity : AppCompatActivity() {

    private val statusBarHeight: Int
        get() {
            var result = 0
            val resourceId = this.resources.getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) {
                result = this.resources.getDimensionPixelSize(resourceId)
            }
            return result
        }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transparent_view)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.actionbar_background_dark)
        supportActionBar!!.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(applicationContext, R.color.actionbar_background_dark)))
        this.title = this.javaClass.simpleName.replace("Activity", "")
        findViews()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun findViews() {
        this.findViewById<View>(R.id.button_transparent_0).setOnClickListener {
            supportActionBar!!.show()
            window.statusBarColor = ContextCompat.getColor(applicationContext, R.color.actionbar_background_dark)
            supportActionBar!!.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(applicationContext, R.color.actionbar_background_dark)))
            resetSystemUi()
        }
        this.findViewById<View>(R.id.button_transparent_1).setOnClickListener {
            setStatusBarTranslucent()
            supportActionBar!!.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(applicationContext, R.color.translucent)))
        }
        this.findViewById<View>(R.id.button_transparent_2).setOnClickListener {
            setStatusBarTransparent()
            supportActionBar!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        this.findViewById<View>(R.id.button_transparent_3).setOnClickListener { setSystemUiFloatFullScreen(true) }
        this.findViewById<View>(R.id.button_transparent_4).setOnClickListener { setSystemUiFloatFullScreen(false) }

        this.findViewById<View>(R.id.button_transparent_5).setOnClickListener { supportActionBar!!.show() }
        this.findViewById<View>(R.id.button_transparent_6).setOnClickListener { supportActionBar!!.hide() }
    }

    private fun setStatusBarTranslucent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor =  ContextCompat.getColor(applicationContext,R.color.translucent)
        }
    }

    private fun setStatusBarTransparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    private fun resetSystemUi() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

            val decorView = window.decorView
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            decorView.requestApplyInsets()

        }
    }

    private fun setSystemUiFloatFullScreen(enable: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val decorView = window.decorView
            //            int option = enable ? (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            //                    : (View.SYSTEM_UI_FLAG_VISIBLE | View.SYSTEM_UI_FLAG_LAYOUT_STABLE|View.SYSTEM_UI_FLAG_VISIBLE);

            var option = decorView.systemUiVisibility
            if (enable) {
                option = option or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            } else {
                option = option and View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN.inv()
            }

            Log.d(">>", "SystemUiVisibility " + decorView.systemUiVisibility + ">>" + option)
            decorView.systemUiVisibility = option
            decorView.requestApplyInsets()
        }
    }

    private fun setPaddingTop(id: Int) {
        findViewById<View>(id).setPadding(0, statusBarHeight, 0, 0)
    }
}
