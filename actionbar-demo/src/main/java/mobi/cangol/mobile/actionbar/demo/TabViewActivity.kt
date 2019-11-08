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

package mobi.cangol.mobile.actionbar.demo

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import mobi.cangol.mobile.actionbar.ActionBarActivity
import mobi.cangol.mobile.actionbar.ActionTabItem
import mobi.cangol.mobile.actionbar.view.ActionTabView

@SuppressLint("ResourceAsColor")
class TabViewActivity : ActionBarActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab_view)
        this.setActionbarShadow(true, 3.0f)
        this.customActionBar.displayUpIndicator()
        this.title = this.javaClass.simpleName.replace("Activity", "")
        findViews()
    }

    fun findViews() {
        this.findViewById<View>(R.id.button_tab_1)!!.setOnClickListener {
            title = ""
            initActionTab()
            customActionBar.actionTab.getTabView(2).text = "tab_1"
        }
        this.findViewById<View>(R.id.button_tab_2)!!.setOnClickListener {
            customActionBar.actionTab.removeAllTabs()
            title = this@TabViewActivity.javaClass.simpleName.replace("Activity", "")
        }
        customActionBar.actionTab.setOnTabSelectedListener(object : ActionTabView.OnTabSelectedListener {
            override fun onTabSelected(tab: ActionTabItem): Boolean {
                when (tab.id) {
                    1 -> Toast.makeText(this@TabViewActivity, tab.text, Toast.LENGTH_SHORT).show()
                    2 -> Toast.makeText(this@TabViewActivity, tab.text, Toast.LENGTH_SHORT).show()
                }
                return false
            }
        })
    }

    fun initActionTab() {
        val actionTab = this.customActionBar.actionTab
        actionTab.newTab(1, "推荐", 1)
        actionTab.newTab(2, "关注", 0)
    }
}
