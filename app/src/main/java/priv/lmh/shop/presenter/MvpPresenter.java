package priv.lmh.shop.presenter;

import java.util.List;

import priv.lmh.bean.ShoppingCart;
import priv.lmh.shop.model.MvpModel;
import priv.lmh.util.OnUIThreadListener;

/**
 * Created by HY on 2018/4/19.
 */

public interface MvpPresenter<T> {
    void getData(String phonenumber, final OnUIThreadListener<T> onUIThreadListener);
}
