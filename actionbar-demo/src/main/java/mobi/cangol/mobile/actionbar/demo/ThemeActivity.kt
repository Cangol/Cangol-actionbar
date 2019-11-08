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

import android.os.Bundle
import android.util.Log
import android.view.View
import mobi.cangol.mobile.actionbar.ActionBarActivity
import mobi.cangol.mobile.actionbar.ActionMenu

class ThemeActivity : ActionBarActivity() {
    public override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("MainActivity", "light==$light")
        if (light) {
            setTheme(R.style.AppTheme1)
            setStatusBarTextColor(true)
            setUseSystemBarTintLollipop(false)
        } else {
            setTheme(R.style.AppTheme2)
            setStatusBarTextColor(false)
            setUseSystemBarTintLollipop(true)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_theme)
        this.setActionbarShadow(true)
        this.customActionBar.displayUpIndicator()
        this.title = this.javaClass.simpleName.replace("Activity", "")
        findViews()
    }

    fun findViews() {
        this.findViewById<View>(R.id.button_1)!!.setOnClickListener {
            light = true
            recreate()
        }
        this.findViewById<View>(R.id.button_2)!!.setOnClickListener {
            light = false
            recreate()
        }
        val actionTab = this.customActionBar.actionTab
        actionTab.newTab(1, "推荐", 1)
        actionTab.newTab(2, "关注", 0)
    }

    override fun onMenuActionCreated(actionMenu: ActionMenu) {
        super.onMenuActionCreated(actionMenu)
        actionMenu.addMenu(1, R.string.action_delete, -1, 1)
        actionMenu.addMenu(2, R.string.action_selectAll, R.drawable.ic_action_select, 1)
        actionMenu.addMenu(3, R.string.action_invert, R.drawable.ic_action_unselect, 0)
    }

    companion object {
        private var light = true
    }
}
