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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import mobi.cangol.mobile.actionbar.ActionBarActivity;
import mobi.cangol.mobile.actionbar.OnNavigationListener;

@SuppressLint("ResourceAsColor")
public class NavViewActivity extends ActionBarActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_view);
        this.getCustomActionBar().displayUpIndicator();
        this.setTitle(this.getClass().getSimpleName().replace("Activity",""));
        findViews();
    }

    public void findViews() {
        this.findViewById(R.id.button_nav_1).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                actionNav();
            }
        });
        this.findViewById(R.id.button_nav_2).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getCustomActionBar().clearListNavigation();
            }
        });
    }
    public void actionNav() {
        final String[] navs = {"首页", "游戏", "壁纸", "资讯"};
        this.getCustomActionBar().setListNavigation(navs);
        this.getCustomActionBar().setOnNavigationListener(new OnNavigationListener() {

            @Override
            public boolean onNavigationItemSelected(int itemPosition,
                                                    long itemId) {
                Toast.makeText(NavViewActivity.this, "Navigation " + navs[itemPosition], Toast.LENGTH_SHORT).show();
                getCustomActionBar().setTitle(navs[itemPosition]);
                return true;
            }
        });
    }
}
