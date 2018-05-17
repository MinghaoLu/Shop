package priv.lmh.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import com.facebook.common.logging.LoggingDelegate;

import java.util.List;

import priv.lmh.adapter.BaseAdapter;
import priv.lmh.adapter.CartAdapter;
import priv.lmh.bean.HotWare;
import priv.lmh.bean.ShoppingCart;
import priv.lmh.shop.LoginActivity;
import priv.lmh.shop.R;
import priv.lmh.shop.ShopApplication;
import priv.lmh.shop.WareDetailActivity;
import priv.lmh.util.CartProvider;
import priv.lmh.util.Provider;
import priv.lmh.util.ShoppingUtil;
import priv.lmh.widget.CNToolBar;

/**
 * Created by HY on 2017/8/31.
 */

public class CartFragment extends Fragment implements View.OnClickListener{
    private static final String TAG = "CartFragment";

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
    private ShoppingUtil mShopUtil;

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

        mRecyclerView =  mView.findViewById(R.id.recycleview_cart);
        mCheckBox =  mView.findViewById(R.id.checkbox_all);
        mTextTotal =  mView.findViewById(R.id.text_total);
        mBtnOrder =  mView.findViewById(R.id.btn_order);
        mBtnDel =  mView.findViewById(R.id.btn_del);

        mNeedLogin = mView.findViewById(R.id.need_login);
        mBtnLogin = mView.findViewById(R.id.btn_login);

        mPriceManifest = mView.findViewById(R.id.price_manifest);

    }

    private void setListener(){
        mBtnEdit.setOnClickListener(this);
        mBtnFinish.setOnClickListener(this);
        mBtnLogin.setOnClickListener(this);

        mBtnOrder.setOnClickListener(this);
        mBtnDel.setOnClickListener(this);
    }

    private void loadCart(){
        if(ShopApplication.isLogined){
            mNeedLogin.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            mPriceManifest.setVisibility(View.VISIBLE);

            initRec();
            mShopUtil = new ShoppingUtil(getActivity(),ShopApplication.phoneNumber,mProvider);

            setTotalPrice();
        }else{
            mNeedLogin.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
            mPriceManifest.setVisibility(View.GONE);
        }
    }

    //初始化R
    private void initRec() {
        mProvider = new CartProvider(getActivity(),ShopApplication.phoneNumber);
        //mProvider.setStringData();
        final List<ShoppingCart> carts = mProvider.getAll();
        //carts = JSONUtil.fromJson(,new TypeToken<List<ShoppingCart>>(){}.getType());
        mAdapter = new CartAdapter(getActivity(), carts,R.layout.template_cart,mCheckBox,mTextTotal,mProvider);
        mAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

                if(mAdapter.state == BaseAdapter.FINISHED){
                    HotWare ware = carts.get(position);
                    Intent intent = new Intent(getActivity(), WareDetailActivity.class);
                    intent.putExtra("WARE",ware);
                    startActivity(intent);
                }else {
                    ShoppingCart cart = carts.get(position);
                    cart.setChecked(!cart.isChecked());
                    mAdapter.notifyDataSetChanged();
                }
            }
        });

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
            List<ShoppingCart> carts = mAdapter.getDatas();
            if(carts == null || carts.size() ==0){
                Toast.makeText(getActivity(),"没有可处理的商品", Toast.LENGTH_SHORT).show();
                return;
            }

            mAdapter.setState(BaseAdapter.EDITTING);
            mCheckBox.setChecked(false);

            hideEditShowFinButton();
            hideOrderShowDelButton();
        }else if(v == mBtnFinish && mAdapter != null){
            mAdapter.setState(BaseAdapter.FINISHED);
            mCheckBox.setChecked(true);

            hideFinShowEditButton();
            hideDelShowOrderButton();

            mAdapter.showTotalPrice();
        }else if(v == mBtnOrder){
            Log.d(TAG, "onClick: "+mAdapter.getItemCount());

            Log.d(TAG, "onClick: mBtnOrder");
            if(mShopUtil != null){
                mShopUtil.order(mAdapter.getDatas());

            }
            mAdapter.notifyDataSetChanged();
            mAdapter.showTotalPrice();
            Toast.makeText(getActivity(),"下单成功",Toast.LENGTH_SHORT).show();

            //算钱
;
            if(mAdapter.getItemCount() != 0 && checkState(mAdapter.getDatas())!=0){
                mBtnOrder.performClick();
            }
        }else if(v == mBtnDel){
            List<ShoppingCart> carts = mAdapter.getDatas();
            if(carts == null || carts.size() ==0){
                Toast.makeText(getActivity(),"已经没有可删除的商品了", Toast.LENGTH_SHORT).show();
                mBtnFinish.performClick();
                //mBtnFinish.callOnClick();
                return;
            }

            Log.d(TAG, "onClick: "+carts.size());
            for(int i = 0;i<mAdapter.getItemCount();i++){
                if(carts.get(i).isChecked()){
                    mProvider.delete(carts.get(i));
                    carts.remove(i);
                    mAdapter.notifyDataSetChanged();
                }
            }

            if (carts.size() == 0){
                mBtnFinish.performClick();
                return;
            }

            if(mAdapter.getItemCount() != 0 && checkState(mAdapter.getDatas())!=0){
                mBtnDel.performClick();
            }


        }
    }

    private void edit(){

    }

    private int checkState(List<ShoppingCart> carts){
        int i = 0;
        for(int j = 0;j <carts.size();j++){
            ShoppingCart cart = carts.get(j);
            if(cart.isChecked()){
                i++;
            }
        }
        return i;
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

    @Override
    public void onDestroy() {
        if(mBtnFinish.getVisibility() == View.VISIBLE){
            mBtnFinish.performClick();
            mBtnFinish.setVisibility(View.INVISIBLE);
        }

        /*shut down resource*/
        if(mShopUtil != null){
            mShopUtil.close();
        }

        mAdapter=null;
        mShopUtil = null;
        mProvider = null;
        super.onDestroy();
    }

    private void hideDelShowOrderButton(){
        mBtnDel.setVisibility(View.INVISIBLE);
        mBtnOrder.setVisibility(View.VISIBLE);
    }

    private void hideOrderShowDelButton(){
        mBtnDel.setVisibility(View.VISIBLE);
        mBtnOrder.setVisibility(View.INVISIBLE);
    }

    private void hideEditShowFinButton(){
        mBtnEdit.setVisibility(View.INVISIBLE);
        mBtnFinish.setVisibility(View.VISIBLE);
    }

    private void hideFinShowEditButton(){
        mBtnFinish.setVisibility(View.INVISIBLE);
        mBtnEdit.setVisibility(View.VISIBLE);
    }
}
