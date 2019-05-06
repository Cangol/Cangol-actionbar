package mobi.cangol.mobile.actionbar.demo;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by xuewu.wei on 2019/5/5.
 */
public class TransparentNativeActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transparent_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.actionbar_background_dark));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(getApplicationContext(), R.color.actionbar_background_dark)));
        this.setTitle(this.getClass().getSimpleName().replace("Activity", ""));
        findViews();
    }

    public void findViews() {
        this.findViewById(R.id.button_transparent_0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportActionBar().show();
                getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.actionbar_background_dark));
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(getApplicationContext(), R.color.actionbar_background_dark)));
                resetSystemUi();
            }
        });
        this.findViewById(R.id.button_transparent_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStatusBarTranslucent();
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(getApplicationContext(), R.color.translucent)));
            }
        });
        this.findViewById(R.id.button_transparent_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStatusBarTransparent();
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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
                getSupportActionBar().show();
            }
        });
        this.findViewById(R.id.button_transparent_6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportActionBar().hide();
            }
        });
    }

    public void setStatusBarTranslucent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getColor(R.color.translucent));
        }
    }

    public void setStatusBarTransparent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public void resetSystemUi() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            decorView.requestApplyInsets();

        }
    }

    public void setSystemUiFloatFullScreen(boolean enable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
//            int option = enable ? (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
//                    : (View.SYSTEM_UI_FLAG_VISIBLE | View.SYSTEM_UI_FLAG_LAYOUT_STABLE|View.SYSTEM_UI_FLAG_VISIBLE);

            int option = decorView.getSystemUiVisibility();
            if (enable) {
                option |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            } else {
                option &= ~View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            }

            Log.d(">>", "SystemUiVisibility " + decorView.getSystemUiVisibility()+">>"+option);
            decorView.setSystemUiVisibility(option);
            decorView.requestApplyInsets();
        }
    }

    private void setPaddingTop(int id) {
        findViewById(id).setPadding(0, getStatusBarHeight(), 0, 0);
    }

    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = this.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = this.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
