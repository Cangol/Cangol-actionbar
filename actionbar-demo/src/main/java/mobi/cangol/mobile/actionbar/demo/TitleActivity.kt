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
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import mobi.cangol.mobile.actionbar.ActionBarActivity

@SuppressLint("ResourceAsColor")
class TitleActivity : ActionBarActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_title_view)
        this.customActionBar.displayUpIndicator()
        this.title = this.javaClass.simpleName.replace("Activity", "")
        findViews()
    }

    fun findViews() {
        this.findViewById<View>(R.id.button_title_1)?.setOnClickListener {
            customActionBar.titleGravity = Gravity.CENTER
        }
        this.findViewById<View>(R.id.button_title_2)?.setOnClickListener {
            customActionBar.titleGravity = Gravity.LEFT or Gravity.CENTER_VERTICAL
        }
        this.findViewById<View>(R.id.button_title_2)?.setOnClickListener {
            if (customActionBar.titleVisibility == View.VISIBLE) {
                customActionBar.titleVisibility = View.VISIBLE
            } else {
                customActionBar.titleVisibility = View.INVISIBLE
            }
        }
        this.findViewById<View>(R.id.button_shadwow_1)?.setOnClickListener {
            setActionbarShadow(true)
            setShadow(findViewById(R.id.image), true)
        }
        this.findViewById<View>(R.id.button_shadwow_2)?.setOnClickListener {
            setActionbarShadow(false)
            setShadow(findViewById(R.id.image), false)

        }
        //设置标题点击事件
        this.customActionBar.setOnTitleClickListener(OnClickListener {
            Toast.makeText(applicationContext, "Click Title", Toast.LENGTH_SHORT).show()
        })
    }


    private fun setShadow(view: View?, shadow: Boolean) {
        if (shadow) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                view?.elevation = 4 * resources.displayMetrics.density
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                view?.elevation = 0f
            }
        }
    }
}
