package priv.lmh.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import priv.lmh.bean.ShoppingCart;
import priv.lmh.shop.LoginActivity;
import priv.lmh.shop.MyOrderActivity;
import priv.lmh.shop.R;
import priv.lmh.shop.ShopApplication;
import priv.lmh.util.JSONUtil;
import priv.lmh.util.ShoppingUtil;

/**
 * Created by HY on 2017/8/31.
 */

public class MineFragment extends Fragment implements View.OnClickListener{
    private static final String TAG = "MineFragment";

    private Button mBtnLog;
    private CircleImageView mImgHead;
    private TextView mTxtUserName;

    private LinearLayout mAlreadyLog;
    private TextView mTxtOrders;
    private Button mBtnExit;

    private ShoppingUtil mShopUtil;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine,container,false);

        initView(view);

        mShopUtil = new ShoppingUtil(getContext(), ShopApplication.phoneNumber);

        setListener();

        showUser();

        return view;
    }

    private void initView(View view) {
        mBtnLog =  view.findViewById(R.id.btn_login);
        mImgHead = view.findViewById(R.id.img_head);
        mTxtUserName = view.findViewById(R.id.txt_username);

        mAlreadyLog = view.findViewById(R.id.already_log);
        mTxtOrders = view.findViewById(R.id.txt_myorders);
        mBtnExit = view.findViewById(R.id.btn_exit);
    }

    private void setListener() {
        mBtnLog.setOnClickListener(this);
        mImgHead.setOnClickListener(this);

        mTxtOrders.setOnClickListener(this);
        mBtnExit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == mImgHead && !ShopApplication.isLogined){
            Intent intent = new Intent(getActivity(), LoginActivity.class);

            startActivityForResult(intent,0);
        }else if(v == mTxtOrders){
            //获取我的订单
            List<ShoppingCart> result = mShopUtil.getOrder();
            if(result != null && result.size() != 0){
                Log.d(TAG, "onClick: "+ JSONUtil.toJSON(result));
            }

            Intent intent = new Intent(getActivity(), MyOrderActivity.class);
            startActivity(intent);
            //List<ShoppingCart> carts = JSONUtil.fromJson(result,new TypeToken<List<ShoppingCart>>() {}.getType());
            /*if(carts!=null)
                Log.d(TAG, "onClick: size"+carts.size());*/
        }else if(v == mBtnExit){
            //登录信息置空
            ShopApplication.isLogined = false;
            ShopApplication.phoneNumber = "";
            ShopApplication.username = "";
            //ShopApplication.objectId = "";
            //ShopApplication.cartStringData="";

            //退出登陆后按钮的显示与隐藏
            mImgHead.setImageResource(R.drawable.default_head);
            mTxtUserName.setText("点击登录");
            mAlreadyLog.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("TAG","onActivityResult");
        showUser();
    }

    private void showUser() {
        if(ShopApplication.isLogined){
            if(ShopApplication.username != "" && ShopApplication.username != null){
                mTxtUserName.setText(ShopApplication.username);
            }else {
                mTxtUserName.setText("");
            }

            if(!ShopApplication.image.equals("")){
                Picasso.with(getActivity()).load(Uri.parse(ShopApplication.image)).into(mImgHead);
            }
            mAlreadyLog.setVisibility(View.VISIBLE);
        }
    }

    private static String delNull(String str){
        String newStr = str;
        if(str.startsWith("null")){
            newStr = str.substring(4);
        }
        return newStr;
    }

    @Override
    public void onDestroy() {
        mShopUtil.close();
        mShopUtil = null;
        super.onDestroy();
    }
}
