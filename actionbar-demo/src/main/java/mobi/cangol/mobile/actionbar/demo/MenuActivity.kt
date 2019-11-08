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
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast

import mobi.cangol.mobile.actionbar.ActionBarActivity
import mobi.cangol.mobile.actionbar.ActionMenu
import mobi.cangol.mobile.actionbar.ActionMenuItem

@SuppressLint("ResourceAsColor")
class MenuActivity : ActionBarActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_view)
        this.customActionBar.displayUpIndicator()
        this.title = this.javaClass.simpleName.replace("Activity", "")
        findViews()
    }

    fun findViews() {
        this.findViewById<View>(R.id.button_menu_1)!!.setOnClickListener(object : OnClickListener {
            var fullScreen: Boolean = false
            override fun onClick(v: View) {
                if (!fullScreen) {
                    isFullScreen = true
                    fullScreen = true
                } else {
                    isFullScreen = false
                    fullScreen = false
                }

            }
        })
        this.findViewById<View>(R.id.button_menu_2)!!.setOnClickListener { isActionbarShow = !isActionbarShow }

        this.findViewById<View>(R.id.button_menu_3)!!.setOnClickListener { isActionbarOverlay = !isActionbarOverlay }

        this.findViewById<View>(R.id.button_menu_4)!!.setOnClickListener(object : OnClickListener {
            var displayHome = true
            override fun onClick(v: View) {
                if (!displayHome) {
                    customActionBar.setDisplayShowHomeEnabled(true)
                    displayHome = true
                } else {
                    customActionBar.setDisplayShowHomeEnabled(false)
                    displayHome = false
                }
            }
        })
        //this.setMaskView();
        this.findViewById<View>(R.id.button_menu_5)!!.setOnClickListener(object : OnClickListener {
            var maskView: Boolean = false
            override fun onClick(v: View) {
                if (!maskView) {
                    displayMaskView(true)
                    maskView = true
                } else {
                    displayMaskView(false)
                    maskView = false
                }
            }
        })
        this.findViewById<View>(R.id.button_menu_6)!!.setOnClickListener { setWindowBackground(R.color.red) }
        this.findViewById<View>(R.id.button_menu_7)!!.setOnClickListener { setBackgroundResource(R.color.activity_background) }
        this.findViewById<View>(R.id.button_menu_8)!!.setOnClickListener { setStatusBarTintColor(resources.getColor(R.color.blue)) }
        this.findViewById<View>(R.id.button_menu_9)!!.setOnClickListener { setNavigationBarTintColor(resources.getColor(R.color.red)) }
        this.findViewById<View>(R.id.button_menu_10)!!.setOnClickListener { customActionBar.setBackgroundResource(R.color.blue) }

        this.findViewById<View>(R.id.button_menu_11)!!.setOnClickListener {
            customActionBar.setLeftMenu(0x11, R.string.action_setting, -1, OnClickListener {
                Log.e(">>", "click me")
                showToast("click me")
            })
        }
    }

    override fun onMenuActionCreated(actionMenu: ActionMenu) {
        super.onMenuActionCreated(actionMenu)
        actionMenu.addMenu(1, R.string.action_delete, -1, 1)
        actionMenu.addMenu(2, R.string.action_selectAll, R.drawable.ic_action_select, 1)
        actionMenu.addMenu(3, R.string.action_invert, R.drawable.ic_action_unselect, 0)
    }

    override fun onMenuActionSelected(action: ActionMenuItem): Boolean {
        when (action.id) {
            1 -> showToast(R.string.action_delete)
            2 -> showToast(R.string.action_selectAll)
            3 -> showToast(R.string.action_invert)
        }
        return super.onMenuActionSelected(action)
    }

    private fun showToast(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }

    private fun showToast(id: Int) {
        Toast.makeText(this, id, Toast.LENGTH_SHORT).show()
    }
}
