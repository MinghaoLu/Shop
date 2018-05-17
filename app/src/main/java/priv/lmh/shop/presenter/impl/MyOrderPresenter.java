package priv.lmh.shop.presenter.impl;

import android.content.Context;

import java.util.List;

import priv.lmh.bean.ShoppingCart;
import priv.lmh.shop.model.MvpModel;
import priv.lmh.shop.model.impl.MvpMyOrderModel;
import priv.lmh.shop.presenter.MvpPresenter;
import priv.lmh.shop.view.MvpView;
import priv.lmh.util.OnUIThreadListener;
import priv.lmh.util.ShoppingUtil;

/**
 * Created by HY on 2018/4/21.
 */

public class MyOrderPresenter implements MvpPresenter<List<ShoppingCart>> {
    private MvpModel mvpModel;
    private MvpView mvpView;
    public MyOrderPresenter(Context context,MvpView view){
        mvpModel = new MvpMyOrderModel(context);
        mvpView = view;
    }

    public void getData(String phonenumber, final OnUIThreadListener onUIThreadListener){
        mvpModel.onRequest(phonenumber, new ShoppingUtil.OnSPResultListener<List<ShoppingCart>>() {
            @Override
            public void onResult(List<ShoppingCart> result) {
                onUIThreadListener.onResult(result);
                mvpView.loadData(result);
            }
        });
    }

}
