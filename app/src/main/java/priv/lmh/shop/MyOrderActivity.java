package priv.lmh.shop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

import priv.lmh.adapter.BaseAdapter;
import priv.lmh.adapter.OrderAdapter;
import priv.lmh.bean.HotWare;
import priv.lmh.bean.ShoppingCart;
import priv.lmh.navigation.MyOrderNavigationBuilder;
import priv.lmh.shop.presenter.MvpPresenter;
import priv.lmh.shop.presenter.impl.MyOrderPresenter;
import priv.lmh.shop.view.MvpView;
import priv.lmh.util.OnUIThreadListener;
import priv.lmh.util.ShoppingUtil;

/**
 * Created by HY on 2018/4/14.
 */

public class MyOrderActivity extends AppCompatActivity implements MvpView<List<ShoppingCart>>{
    private static final String TAG = "MyOrderActivity";
    private ShoppingUtil mUtil;
    private RecyclerView mRecyclerView;
    private BaseAdapter mAdapter;

    private MvpPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myorder);

        //mUtil = new ShoppingUtil(this,ShopApplication.phoneNumber);
        mPresenter = new MyOrderPresenter(this,this);

        initView();

        loadOrder();
    }

    private void initView() {
        LinearLayout view = (LinearLayout) findViewById(R.id.ll_myorder);
        MyOrderNavigationBuilder builder = new MyOrderNavigationBuilder(this);
        builder.setLeftIcon(R.drawable.icon_back_32px)
                .setLeftIconListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyOrderActivity.this.finish();
                    }
                }).setTitle(R.string.myorders)
                .createAndBind(view);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycleview_myorder);

    }

    private void loadOrder() {
        /*List<ShoppingCart> orders = mUtil.getOrder();
        mAdapter = new OrderAdapter(this,orders,R.layout.template_myorder_wares);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());*/

        mPresenter.getData(ShopApplication.phoneNumber, new OnUIThreadListener<List<ShoppingCart>>() {
            @Override
            public void onResult(List<ShoppingCart> result) {
                Toast.makeText(MyOrderActivity.this,""+result.size(),Toast.LENGTH_SHORT).show();
                loadData(result);
            }

        });
    }

    @Override
    public void loadData(final List<ShoppingCart> result) {

        mAdapter = new OrderAdapter(MyOrderActivity.this,result,R.layout.template_myorder_wares);
        mAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(MyOrderActivity.this,WareDetailActivity.class);
                HotWare ware = result.get(position);
                intent.putExtra("WARE",ware);
                startActivity(intent);
            }
        });

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MyOrderActivity.this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        Log.d(TAG, "loadData: succeed!");
    }

}
