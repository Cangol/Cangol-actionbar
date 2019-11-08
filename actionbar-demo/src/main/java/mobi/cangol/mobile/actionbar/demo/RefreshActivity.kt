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
import android.view.Gravity
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast

import mobi.cangol.mobile.actionbar.ActionBarActivity
import mobi.cangol.mobile.actionbar.ActionMenu
import mobi.cangol.mobile.actionbar.ActionMenuItem

@SuppressLint("ResourceAsColor")
class RefreshActivity : ActionBarActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_refresh_view)
        this.customActionBar.displayUpIndicator()
        this.title = this.javaClass.simpleName.replace("Activity", "")
        findViews()
        customActionBar.setLeftMenu(0x11, R.string.action_setting, R.drawable.actionbar_clear_dark, OnClickListener {

        })
    }

    override fun onMenuActionCreated(actionMenu: ActionMenu) {
        super.onMenuActionCreated(actionMenu)
        actionMenu.addMenu(1, R.string.action_delete, -1, 1)
        //actionMenu.addMenu(2, R.string.action_selectAll, R.drawable.ic_action_select, 1);
        //actionMenu.addMenu(3, R.string.action_invert, R.drawable.ic_action_unselect, 0);
    }

    override fun onMenuActionSelected(action: ActionMenuItem): Boolean {
        when (action.id) {
            1 -> showToast(R.string.action_delete)
            2 -> showToast(R.string.action_selectAll)
            3 -> showToast(R.string.action_invert)
        }
        return super.onMenuActionSelected(action)
    }

    fun findViews() {
        this.findViewById<View>(R.id.button_refresh_00)?.setOnClickListener {
            customActionBar.enableRefresh(true, Gravity.LEFT)
        }
        this.findViewById<View>(R.id.button_refresh_01)?.setOnClickListener {
            customActionBar.enableRefresh(false, Gravity.LEFT)
        }
        this.findViewById<View>(R.id.button_refresh_10)?.setOnClickListener {
            customActionBar.enableRefresh(true, Gravity.RIGHT)
        }
        this.findViewById<View>(R.id.button_refresh_11)?.setOnClickListener {
            customActionBar.enableRefresh(false, Gravity.RIGHT)
        }
        this.findViewById<View>(R.id.button_refresh_2)?.setOnClickListener {
            customActionBar.refreshing(true)
        }
        this.findViewById<View>(R.id.button_refresh_3)?.setOnClickListener {
            customActionBar.refreshing(false)
        }
        customActionBar.setOnRefreshClickListener(OnClickListener { showToast("refreshing") })
    }

    private fun showToast(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }

    private fun showToast(id: Int) {
        Toast.makeText(this, id, Toast.LENGTH_SHORT).show()
    }
}
