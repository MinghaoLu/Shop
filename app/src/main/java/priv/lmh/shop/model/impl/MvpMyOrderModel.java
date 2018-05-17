package priv.lmh.shop.model.impl;

import android.content.Context;

import java.util.List;

import priv.lmh.bean.ShoppingCart;
import priv.lmh.shop.ShopApplication;
import priv.lmh.shop.model.MvpModel;
import priv.lmh.util.ShoppingUtil;

/**
 * Created by HY on 2018/4/21.
 */

public class MvpMyOrderModel implements MvpModel<List<ShoppingCart>>{
    private ShoppingUtil mUtil;

    public MvpMyOrderModel(Context context){
        mUtil = new ShoppingUtil(context, ShopApplication.phoneNumber);
    }

    @Override
    public void onRequest(String phonenumber, ShoppingUtil.OnSPResultListener onSPResultListener) {
        //请求数据
        List<ShoppingCart> carts = mUtil.getOrder();
        //成功后回调函数
        onSPResultListener.onResult(carts);
    }
}
