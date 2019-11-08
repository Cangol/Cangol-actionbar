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
import mobi.cangol.mobile.actionbar.view.SearchView

@SuppressLint("ResourceAsColor")
class SearchViewActivity : ActionBarActivity() {
    private var searchView: SearchView? = null
    internal var history = true
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_view)
        this.customActionBar.displayUpIndicator()
        this.title = this.javaClass.simpleName.replace("Activity", "")
        findViews()
    }

    fun findViews() {
        this.findViewById<View>(R.id.button_search_1)!!.setOnClickListener {
            searchView = startSearchMode()
            searchView!!.setSearchHistoryEnable(history)
            searchView!!.setOnSearchTextListener(object : SearchView.OnSearchTextListener {
                override fun onSearchText(keywords: String): Boolean {
                    Toast.makeText(applicationContext, keywords, Toast.LENGTH_SHORT).show()
                    return true
                }
            })
        }
        this.findViewById<View>(R.id.button_search_2)!!.setOnClickListener { stopSearchMode() }
        this.findViewById<View>(R.id.button_search_3)!!.setOnClickListener {
            history = !history
        }
        this.findViewById<View>(R.id.button_search_4)!!.setOnClickListener {
            if (searchView != null)
                searchView!!.clearSearchHistory()
        }


    }
}
