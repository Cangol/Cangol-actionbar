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
import android.widget.EditText;
import android.widget.Toast;

import mobi.cangol.mobile.actionbar.ActionBarActivity;
import mobi.cangol.mobile.actionbar.ActionMenu;
import mobi.cangol.mobile.actionbar.ActionMenuItem;
import mobi.cangol.mobile.actionbar.ActionMode;
import mobi.cangol.mobile.actionbar.ActionTab;
import mobi.cangol.mobile.actionbar.ActionTabItem;
import mobi.cangol.mobile.actionbar.OnNavigationListener;
import mobi.cangol.mobile.actionbar.view.ActionTabView;
import mobi.cangol.mobile.actionbar.view.SearchView;

@SuppressLint("ResourceAsColor")
public class SearchViewActivity extends ActionBarActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);
        this.getCustomActionBar().displayUpIndicator();
        this.setTitle(this.getClass().getSimpleName().replace("Activity",""));
        findViews();
    }

    public void findViews() {
        this.findViewById(R.id.button_search_1).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchView searchView= startSearchMode();
                searchView.setSearchHistoryEnable(false);
                searchView.setSearchDrawableShow(true);
                searchView.setOnSearchTextListener(new SearchView.OnSearchTextListener() {
                    @Override
                    public boolean onSearchText(String keywords) {

                        return true;
                    }
                });
            }
        });
        this.findViewById(R.id.button_search_2).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                stopSearchMode();
            }
        });
    }
}
