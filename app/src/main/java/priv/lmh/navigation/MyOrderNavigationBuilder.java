package priv.lmh.navigation;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import priv.lmh.shop.R;

/**
 * Created by HY on 2018/4/20.
 */

public class MyOrderNavigationBuilder extends NavigationAdapter {
    public MyOrderNavigationBuilder(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.toolbar_layout;
    }

    @Override
    public View createAndBind(ViewGroup parent) {
        View view = super.createAndBind(parent);
        setImageViewStyle(view,R.id.img_back,leftIconRes,leftIconListener);
        setTextViewStyle(view,R.id.txt_register,R.string.myorders,null);
        return view;
    }
}
