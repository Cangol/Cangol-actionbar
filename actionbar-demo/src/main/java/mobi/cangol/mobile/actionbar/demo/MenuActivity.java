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
import mobi.cangol.mobile.actionbar.ActionMenu;
import mobi.cangol.mobile.actionbar.ActionMenuItem;

@SuppressLint("ResourceAsColor")
public class MenuActivity extends ActionBarActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_view);
        this.getCustomActionBar().displayUpIndicator();
        this.setTitle(this.getClass().getSimpleName().replace("Activity",""));
        findViews();
    }

    public void findViews() {
        this.findViewById(R.id.button_menu_1).setOnClickListener(new OnClickListener() {
            boolean fullScreen;
            @Override
            public void onClick(View v) {
                if(!fullScreen){
                    setFullScreen(true);
                    fullScreen=true;
                } else{
                    setFullScreen(false);
                    fullScreen=false;
                }

            }
        });
        this.findViewById(R.id.button_menu_2).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                    setActionbarShow(!isActionbarShow());
            }
        });

        this.findViewById(R.id.button_menu_3).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setActionbarOverlay(!isActionbarOverlay());
            }
        });

        this.findViewById(R.id.button_menu_4).setOnClickListener(new OnClickListener() {
            boolean displayHome=true;
            @Override
            public void onClick(View v) {
                if(!displayHome){
                    getCustomActionBar().setDisplayShowHomeEnabled(true);
                    displayHome=true;
                }else{
                    getCustomActionBar().setDisplayShowHomeEnabled(false);
                    displayHome=false;
                }
            }
        });
        //this.setMaskView();
        this.findViewById(R.id.button_menu_5).setOnClickListener(new OnClickListener() {
            boolean maskView;
            @Override
            public void onClick(View v) {
                if(!maskView){
                    displayMaskView(true);
                    maskView=true;
                }else{
                    displayMaskView(false);
                    maskView=false;
                }
            }
        });
        this.findViewById(R.id.button_menu_6).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setWindowBackground(R.color.red);
            }
        });
        this.findViewById(R.id.button_menu_7).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setBackgroundResource(R.color.activity_background);
            }
        });
        this.findViewById(R.id.button_menu_8).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setStatusBarTintColor(getResources().getColor(R.color.blue));
            }
        });
        this.findViewById(R.id.button_menu_9).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setNavigationBarTintColor(getResources().getColor(R.color.red));
            }
        });
        this.findViewById(R.id.button_menu_10).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getCustomActionBar().setBackgroundResource(R.color.blue);
            }
        });
    }
    @Override
    public void onMenuActionCreated(ActionMenu actionMenu) {
        super.onMenuActionCreated(actionMenu);
        actionMenu.addMenu(1, R.string.action_delete, -1, 1);
        actionMenu.addMenu(2, R.string.action_selectAll, R.drawable.ic_action_select, 1);
        actionMenu.addMenu(3, R.string.action_invert, R.drawable.ic_action_unselect, 0);
    }

    @Override
    public boolean onMenuActionSelected(ActionMenuItem action) {
        switch (action.getId()) {
            case 1:
                showToast(R.string.action_delete);
                break;
            case 2:
                showToast(R.string.action_selectAll);
                break;
            case 3:
                showToast(R.string.action_invert);
                break;
        }
        return super.onMenuActionSelected(action);
    }

    private void showToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }

    private void showToast(int id) {
        Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
    }
}
