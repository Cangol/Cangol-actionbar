package mobi.cangol.mobile.actionbar.demo

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import mobi.cangol.mobile.actionbar.ActionBarActivity
import java.util.*

@SuppressLint("ResourceAsColor")
class MainActivity : ActionBarActivity() {
    private var mListView: ListView? = null
    private val activities = ArrayList<Class<out Activity>>()
    public override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("MainActivity", "light==" + light)
        if (light) {
            setTheme(R.style.AppTheme1)
            //setStatusBarTextColor(true);
        } else {
            setTheme(R.style.AppTheme2)
            //setStatusBarTextColor(false);
            //setUseSystemBarTintLollipop(true);
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "首页"
        this.setStatusBarTintColor(resources.getColor(R.color.red))
        this.setWindowBackground(R.drawable.ic_bg)
        this.customActionBar.setDisplayShowHomeEnabled(true)
        this.setActionbarShadow(true, 8f)
        this.customActionBar.setBackgroundResource(R.color.red)

        activities.add(SearchViewActivity::class.java)
        activities.add(ActionModeActivity::class.java)
        activities.add(RefreshActivity::class.java)
        activities.add(TabViewActivity::class.java)
        activities.add(NavViewActivity::class.java)
        activities.add(MenuActivity::class.java)
        activities.add(CustomViewActivity::class.java)
        activities.add(LeftMenuActivity::class.java)
        activities.add(TitleActivity::class.java)
        activities.add(ThemeActivity::class.java)
        activities.add(TransparentActivity::class.java)
        activities.add(TransparentNativeActivity::class.java)
        mListView = this.findViewById<View>(R.id.listView) as ListView?
        mListView!!.adapter = object : BaseAdapter() {
            override fun getCount(): Int {
                return activities.size
            }

            override fun getItem(position: Int): Class<*> {
                return activities.get(position)
            }

            override fun getItemId(position: Int): Long {
                return position.toLong()
            }

            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                var convertView = convertView
                var holder: ViewHolder? = null
                val item = getItem(position)
                if (null != convertView) {
                    holder = convertView.tag as ViewHolder
                } else {
                    convertView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_view, parent, false)
                    holder = ViewHolder()
                    holder.text = convertView!!.findViewById<View>(R.id.textView) as TextView
                    convertView.tag = holder
                }
                holder.text!!.text = item.simpleName.replace("Activity", "")
                return convertView
            }

            inner class ViewHolder {
                var text: TextView? = null
            }
        }
        mListView!!.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val clazz = parent.getItemAtPosition(position) as Class<*>
                startActivity(Intent(this@MainActivity, clazz))
            }
        }

    }

    companion object {
        private val light = true
    }
}
