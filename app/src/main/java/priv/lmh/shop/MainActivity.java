package priv.lmh.shop;

import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import priv.lmh.bean.TabItem;
import priv.lmh.fragment.CartFragment;
import priv.lmh.fragment.CategoryFragment;
import priv.lmh.fragment.HomeFragment;
import priv.lmh.fragment.HotFragment;
import priv.lmh.fragment.MineFragment;

public class MainActivity extends AppCompatActivity {
    private List<TabItem> tabItemList;

    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initTabData();
        initTabHost();
        toast = Toast.makeText(this, "再按一次退出", Toast.LENGTH_LONG);
    }

    private void initTabData(){
        tabItemList = new ArrayList<>();
        tabItemList.add(new TabItem(R.drawable.selecto_icon_home,R.string.home,HomeFragment.class));
        tabItemList.add(new TabItem(R.drawable.selector_icon_hot,R.string.hot, HotFragment.class));
        tabItemList.add(new TabItem(R.drawable.selector_icon_category,R.string.catagory, CategoryFragment.class));
        tabItemList.add(new TabItem(R.drawable.selector_icon_cart,R.string.cart, CartFragment.class));
        tabItemList.add(new TabItem(R.drawable.selector_icon_mine,R.string.mine, MineFragment.class));
    }

    private void initTabHost(){
        FragmentTabHost fragmentTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        fragmentTabHost.setup(this,getSupportFragmentManager(),android.R.id.tabcontent);
        fragmentTabHost.getTabWidget().setDividerDrawable(null);

        //可以自定义Tab样式：setIndicator(View v)
        for(TabItem tabItem : tabItemList){
            TabHost.TabSpec tabSpec = fragmentTabHost.newTabSpec(getString(tabItem.getTitle()));
            tabSpec.setIndicator(buildIndicator(tabItem));
            fragmentTabHost.addTab(tabSpec,tabItem.getCls(),null);
        }


        /*fragmentTabHost.addTab(Tabspec,Class<? extends Fragment>,Bundle,);Tab对应的Fragment，还可以传递参数
        * 可以重复不断添加Tabs所以可以创建一个类包含Tab所需的数据，面向对象的编程*/

        //监听器

    }

    //不同Tab有不同的图标和名字（可以重构到TabItem类中）
    private View buildIndicator(TabItem tabItem){
        View view = getLayoutInflater().inflate(R.layout.view_tab_indicator,null);
        ImageView imageView = view.findViewById(R.id.icon_tab);
        TextView textView = view.findViewById(R.id.tv_tab);

        imageView.setImageResource(tabItem.getImage());
        textView.setText(getString(tabItem.getTitle()));

        return view;
    }

    @Override
    public void onBackPressed() {
        showToast();

        onExit();
    }

    private void showToast() {
        if(toast.getView().getParent() == null){
            toast.show();
        }
    }

    private void onExit() {
        if(toast.getView().getParent() != null){
            this.finish();
        }
    }
}
