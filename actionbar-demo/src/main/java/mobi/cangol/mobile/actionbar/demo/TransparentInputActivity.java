package mobi.cangol.mobile.actionbar.demo;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;

import mobi.cangol.mobile.actionbar.ActionBarActivity;

/**
 * Created by xuewu.wei on 2019/5/5.
 */
public class TransparentInputActivity extends ActionBarActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transparent_input);
        AndroidBug5497Workaround.assistActivity(this);
        this.setTitle(this.getClass().getSimpleName().replace("Activity", ""));
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        findViews();
    }

    public void findViews() {
        this.setActionbarShow(false);
        this.setStatusBarTintColor(Color.TRANSPARENT);
        this.setSystemUiFloatFullScreen(true);

        WebView webView= (WebView) this.findViewById(R.id.webView);
        webView.loadUrl("file:///android_asset/index.html");
    }

}
