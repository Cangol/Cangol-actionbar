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

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import mobi.cangol.mobile.actionbar.ActionBarActivity;

public class ThemeActivity extends ActionBarActivity{
    private static boolean light=true;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("MainActivity","light=="+light);
        if(light){
            setTheme(R.style.AppTheme1);
        }else {
            setTheme(R.style.AppTheme2);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);
        this.setActionbarShadow(true);
        this.getCustomActionBar().displayUpIndicator();
        this.setTitle(this.getClass().getSimpleName().replace("Activity",""));
        findViews();
    }

    public void findViews() {
        this.findViewById(R.id.button_1).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                light=true;
                recreate();
            }
        });
        this.findViewById(R.id.button_2).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                light=false;
                recreate();
            }
        });
    }
}
