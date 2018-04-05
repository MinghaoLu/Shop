package priv.lmh.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import priv.lmh.adapter.BaseAdapter;
import priv.lmh.adapter.CartAdapter;
import priv.lmh.bean.ShoppingCart;
import priv.lmh.shop.LoginActivity;
import priv.lmh.shop.R;
import priv.lmh.shop.ShopApplication;
import priv.lmh.util.CartProvider;
import priv.lmh.util.JSONUtil;
import priv.lmh.util.NetCartProvider;
import priv.lmh.util.Provider;
import priv.lmh.widget.CNToolBar;

/**
 * Created by HY on 2017/8/31.
 */

public class CartFragment extends Fragment implements View.OnClickListener{
    private View mView;
    private CheckBox mCheckBox;
    private TextView mTextTotal;


    private Button mBtnEdit;
    private Button mBtnFinish;
    private Button mBtnDel;
    private Button mBtnOrder;

    private LinearLayout mNeedLogin;
    private Button mBtnLogin;

    //private CNToolBar toolBar;

    private RecyclerView mRecyclerView;

    private RelativeLayout mPriceManifest;

    private BaseAdapter mAdapter;

    private Provider mProvider;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("TAG","onCreateView");
        if(mView == null){
            mView = inflater.inflate(R.layout.fragment_cart,null);
            Log.d("TAG","CreateView");
            initView();
            setListener();
        }

        ViewGroup parent = (ViewGroup) mView.getParent();
        if(parent != null){
                parent.removeView(mView);
        }
        //loadCart();

        return mView;
    }

    private void initView(){
        CNToolBar cnToolBar = mView.findViewById(R.id.cntoolbar);
        mBtnEdit = cnToolBar.findViewById(R.id.btn_edit);
        mBtnFinish = cnToolBar.findViewById(R.id.btn_finish);

        mRecyclerView = (RecyclerView) mView.findViewById(R.id.recycleview_cart);
        mCheckBox = (CheckBox) mView.findViewById(R.id.checkbox_all);
        mTextTotal = (TextView) mView.findViewById(R.id.text_total);
        mBtnOrder = (Button) mView.findViewById(R.id.btn_order);
        mBtnDel = (Button) mView.findViewById(R.id.btn_del);

        mNeedLogin = mView.findViewById(R.id.need_login);
        mBtnLogin = mView.findViewById(R.id.btn_login);

        mPriceManifest = mView.findViewById(R.id.price_manifest);
    }

    private void setListener(){
        mBtnEdit.setOnClickListener(this);
        mBtnFinish.setOnClickListener(this);
        mBtnLogin.setOnClickListener(this);
    }

    private void loadCart(){
        if(ShopApplication.isLogined){
            mNeedLogin.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            mPriceManifest.setVisibility(View.VISIBLE);

            initRec();

            setTotalPrice();
        }else{
            mNeedLogin.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
            mPriceManifest.setVisibility(View.GONE);
        }
    }

    private void initRec() {
        mProvider = new CartProvider(getActivity(),ShopApplication.phoneNumber);
        //mProvider.setStringData();
        List<ShoppingCart> carts = mProvider.getAll();
        //carts = JSONUtil.fromJson(,new TypeToken<List<ShoppingCart>>(){}.getType());
        mAdapter = new CartAdapter(getActivity(), carts,R.layout.template_cart,mCheckBox,mTextTotal,mProvider);

        mRecyclerView.setAdapter(mAdapter);

        //加载数据后显示编辑按钮
        mBtnEdit.setVisibility(View.VISIBLE);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        /*mAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                mAdapter.notifyItemRemoved(position);
                mAdapter.notifyDataSetChanged();
                Log.d("count",mRecyclerView.getChildCount()+"");
            }
        });*/
    }

    private void setTotalPrice(){
        mTextTotal.setText("￥"+mProvider.getTotalPrice());
    }

    @Override
    public void onClick(View v) {
        if(v == mBtnLogin){
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }else if(v == mBtnEdit && mAdapter != null){
            mAdapter.setState(BaseAdapter.EDITTING);
            mCheckBox.setChecked(false);
            mBtnEdit.setVisibility(View.GONE);
            mBtnFinish.setVisibility(View.VISIBLE);

            mBtnOrder.setVisibility(View.INVISIBLE);
            mBtnDel.setVisibility(View.VISIBLE);
        }else if(v == mBtnFinish && mAdapter != null){
            mAdapter.setState(BaseAdapter.FINISHED);
            mCheckBox.setChecked(true);
            mBtnEdit.setVisibility(View.VISIBLE);
            mBtnFinish.setVisibility(View.GONE);

            mBtnOrder.setVisibility(View.VISIBLE);
            mBtnDel.setVisibility(View.INVISIBLE);
        }
    }

    private void edit(){

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("TAG","onAttach");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("TAG","onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("TAG","onResume");
        loadCart();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("TAG","onDetach");
    }
}
