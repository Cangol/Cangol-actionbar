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

@SuppressLint("ResourceAsColor")
class LeftMenuActivity : ActionBarActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_left_menu)
        this.customActionBar.displayUpIndicator()
        this.title = this.javaClass.simpleName.replace("Activity", "")
        findViews()
    }

    private fun findViews() {
        this.customActionBar.titleGravity = Gravity.CENTER
        this.findViewById<View>(R.id.button_menu_0)?.setOnClickListener {
            customActionBar.setDisplayShowHomeEnabled(true)
        }
        this.findViewById<View>(R.id.button_menu_1)?.setOnClickListener {
            customActionBar.setDisplayShowHomeEnabled(false)
        }
        this.findViewById<View>(R.id.button_menu_2)?.setOnClickListener {
            customActionBar.setLeftMenu(0x11, R.string.action_setting, R.drawable.actionbar_clear_dark, OnClickListener { })
        }
        this.findViewById<View>(R.id.button_menu_3)?.setOnClickListener {
            customActionBar.clearLeftMenu()
        }
    }

    private fun showToast(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
    }

    private fun showToast(id: Int) {
        Toast.makeText(this, id, Toast.LENGTH_SHORT).show()
    }
}
