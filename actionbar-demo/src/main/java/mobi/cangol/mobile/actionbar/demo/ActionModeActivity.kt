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
import mobi.cangol.mobile.actionbar.ActionBarActivity
import mobi.cangol.mobile.actionbar.ActionMenu
import mobi.cangol.mobile.actionbar.ActionMenuItem
import mobi.cangol.mobile.actionbar.ActionMode

@SuppressLint("ResourceAsColor")
class ActionModeActivity : ActionBarActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_action_mode)
        this.customActionBar.displayUpIndicator()
        this.title = this.javaClass.simpleName.replace("Activity", "")
        findViews()
    }

    fun findViews() {
        this.findViewById<View>(R.id.button_mode_1)!!.setOnClickListener {
            startCustomActionMode(object : ActionMode.Callback {

                override fun onCreateActionMode(mode: ActionMode,
                                                actionMenu: ActionMenu) {

                }

                override fun onActionItemClicked(mode: ActionMode,
                                                 menuItem: ActionMenuItem): Boolean {
                    return false
                }

                override fun onDestroyActionMode(mode: ActionMode) {

                }

            })
        }
        this.findViewById<View>(R.id.button_mode_2)!!.setOnClickListener { stopCustomActionMode() }
    }
}
