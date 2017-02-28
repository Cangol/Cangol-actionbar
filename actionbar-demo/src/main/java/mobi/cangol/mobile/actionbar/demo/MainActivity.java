package mobi.cangol.mobile.actionbar.demo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mobi.cangol.mobile.actionbar.ActionBarActivity;
import mobi.cangol.mobile.actionbar.ActionMenu;
import mobi.cangol.mobile.actionbar.ActionMenuItem;
import mobi.cangol.mobile.actionbar.ActionMode;
import mobi.cangol.mobile.actionbar.ActionTab;
import mobi.cangol.mobile.actionbar.ActionTabItem;
import mobi.cangol.mobile.actionbar.OnNavigationListener;
import mobi.cangol.mobile.actionbar.view.ActionTabView;
import mobi.cangol.mobile.actionbar.view.SearchView;

@SuppressLint("ResourceAsColor")
public class MainActivity extends ActionBarActivity{
    private ListView mListView;
    private List<Class<? extends ActionBarActivity >> activities=new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("首页");
        this.getCustomActionBar().setDisplayShowHomeEnabled(true);
        activities.add(SearchViewActivity.class);
        activities.add(ActionModeActivity.class);
        activities.add(ProgressActivity.class);
        activities.add(TabViewActivity.class);
        activities.add(NavViewActivity.class);
        activities.add(MenuActivity.class);
        activities.add(CustomViewActivity.class);
        mListView= (ListView) this.findViewById(R.id.listView);
        mListView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return activities.size();
            }

            @Override
            public Class getItem(int position) {
                return activities.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder holder = null;
                final Class item = getItem(position);
                if (null != convertView) {
                    holder = (ViewHolder) convertView.getTag();
                } else {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_view, parent, false);
                    holder = new ViewHolder();
                    holder.text = (TextView) convertView.findViewById(R.id.textView);
                    convertView.setTag(holder);
                }
                holder.text.setText(item.getSimpleName().replace("Activity",""));
                return convertView;
            }
            class ViewHolder {
                TextView text;
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Class clazz= (Class) parent.getItemAtPosition(position);
                startActivity(new Intent(MainActivity.this,clazz));
            }
        });
    }
}
