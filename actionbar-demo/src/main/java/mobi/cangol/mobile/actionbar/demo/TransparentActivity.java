package mobi.cangol.mobile.actionbar.demo;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.FitWindowsFrameLayout;
import android.support.v7.widget.FitWindowsLinearLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;

import mobi.cangol.mobile.actionbar.ActionBarActivity;

/**
 * Created by xuewu.wei on 2019/5/5.
 */
public class TransparentActivity extends ActionBarActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transparent_view);
        this.getCustomActionBar().displayUpIndicator();
        this.setTitle(this.getClass().getSimpleName().replace("Activity", ""));
        findViews();
        findViewById(android.support.v7.appcompat.R.id.action_mode_bar_stub);
    }

    public void findViews() {
        setActionbarOverlay(true);
        this.findViewById(R.id.button_transparent_0).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                setSystemUiFloatFullScreen(false);
                setActionbarShow(true);
                setStatusBarTintColor(getColor(R.color.actionbar_background));
                getCustomActionBar().setBackgroundResource(R.color.actionbar_background);
            }
        });
        this.findViewById(R.id.button_transparent_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStatusBarTintColor(getColor(R.color.translucent));
                getCustomActionBar().setBackgroundResource(R.color.translucent);
            }
        });
        this.findViewById(R.id.button_transparent_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStatusBarTintColor(getColor(R.color.transparent));
                getCustomActionBar().setBackgroundResource(R.color.transparent);
            }
        });

        this.findViewById(R.id.button_transparent_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSystemUiFloatFullScreen(true);
            }
        });
        this.findViewById(R.id.button_transparent_4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSystemUiFloatFullScreen(false);
            }
        });

        this.findViewById(R.id.button_transparent_5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setActionbarShow(true);
            }
        });
        this.findViewById(R.id.button_transparent_6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setActionbarShow(false);
            }
        });
    }
}
