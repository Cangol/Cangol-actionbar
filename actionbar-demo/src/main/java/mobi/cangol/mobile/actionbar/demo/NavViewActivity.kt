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
import mobi.cangol.mobile.actionbar.OnNavigationListener

@SuppressLint("ResourceAsColor")
class NavViewActivity : ActionBarActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav_view)
        this.customActionBar.displayUpIndicator()
        this.title = this.javaClass.simpleName.replace("Activity", "")
        findViews()
    }

    fun findViews() {
        this.findViewById<View>(R.id.button_nav_1)!!.setOnClickListener { actionNav() }
        this.findViewById<View>(R.id.button_nav_2)!!.setOnClickListener { customActionBar.clearListNavigation() }
    }

    fun actionNav() {
        val navs = arrayOf("首页", "游戏", "壁纸", "资讯")
        this.customActionBar.listNavigation = navs
        this.customActionBar.setOnNavigationListener(object : OnNavigationListener {

            override fun onNavigationItemSelected(itemPosition: Int,
                                                  itemId: Long): Boolean {
                Toast.makeText(this@NavViewActivity, "Navigation " + navs[itemPosition], Toast.LENGTH_SHORT).show()
                customActionBar.setTitle(navs[itemPosition])
                return true
            }
        })
    }
}
