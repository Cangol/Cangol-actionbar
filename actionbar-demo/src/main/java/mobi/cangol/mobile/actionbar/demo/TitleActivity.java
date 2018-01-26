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
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import mobi.cangol.mobile.actionbar.ActionBarActivity;
import mobi.cangol.mobile.actionbar.ActionTab;
import mobi.cangol.mobile.actionbar.ActionTabItem;
import mobi.cangol.mobile.actionbar.view.ActionTabView;

@SuppressLint("ResourceAsColor")
public class TitleActivity extends ActionBarActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_view);
        this.getCustomActionBar().displayUpIndicator();
        setStatusBarTintColor(getResources().getColor(R.color.actionbar_background));
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
                if(getCustomActionBar().getTitleVisibility()==View.VISIBLE){
                    getCustomActionBar().setTitleVisibility(View.VISIBLE);
                }else{
                    getCustomActionBar().setTitleVisibility(View.INVISIBLE);
                }
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

}
