/*
 *
 *  Copyright (c) 2013 Cangol
 *   <p/>
 *   Licensed under the Apache License, Version 2.0 (the "License")
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *  <p/>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p/>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package mobi.cangol.mobile.actionbar.demo;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import mobi.cangol.mobile.actionbar.ActionBarActivity;

@SuppressLint("ResourceAsColor")
public class TitleActivity extends ActionBarActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_view);
        this.getCustomActionBar().displayUpIndicator();
        this.setTitle(this.getClass().getSimpleName().replace("Activity",""));
        findViews();
    }

    public void findViews() {
        this.findViewById(R.id.button_title_1).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getCustomActionBar().setTitleGravity(Gravity.CENTER);
            }
        });
        this.findViewById(R.id.button_title_2).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getCustomActionBar().setTitleGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
            }
        });
        this.findViewById(R.id.button_title_2).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getCustomActionBar().getTitleVisibility()==View.VISIBLE){
                    getCustomActionBar().setTitleVisibility(View.VISIBLE);
                }else{
                    getCustomActionBar().setTitleVisibility(View.INVISIBLE);
                }
            }
        });
        this.findViewById(R.id.button_shadwow_1).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setActionbarShadow(true);
               setShadow(findViewById(R.id.image),true);
            }
        });
        this.findViewById(R.id.button_shadwow_2).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setActionbarShadow(false);
                setShadow(findViewById(R.id.image),false);
            }
        });
        //设置标题点击事件
        this.getCustomActionBar().setOnTitleClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Click Title", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void setShadow(View view,boolean shadow) {
        if(shadow){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                view.setElevation(4*getResources().getDisplayMetrics().density);
            }
        }else{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                view.setElevation(0);
            }
        }
    }
}
