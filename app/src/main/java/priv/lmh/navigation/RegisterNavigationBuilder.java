package priv.lmh.navigation;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import priv.lmh.shop.R;

/**
 * Created by HY on 2018/3/9.
 */

public class RegisterNavigationBuilder extends NavigationAdapter {

    public RegisterNavigationBuilder(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.toolbar_register_layout;
    }

    @Override
    public View createAndBind(ViewGroup parent) {
        View view = super.createAndBind(parent);
        setImageViewStyle(view,R.id.img_back,R.drawable.icon_back_32px,leftIconListener);
        setTextViewStyle(view,R.id.txt_register,R.string.register,null);
        return view;
    }


}
