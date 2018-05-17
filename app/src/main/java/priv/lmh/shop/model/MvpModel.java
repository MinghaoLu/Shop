package priv.lmh.shop.model;

import priv.lmh.util.ShoppingUtil;

/**
 * Created by HY on 2018/4/19.
 */

public interface MvpModel<T> {
    void onRequest(String phonenumber, ShoppingUtil.OnSPResultListener<T> onSPResultListener);
}
