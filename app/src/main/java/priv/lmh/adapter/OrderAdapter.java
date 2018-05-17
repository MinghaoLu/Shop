package priv.lmh.adapter;

import android.content.Context;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import priv.lmh.bean.ShoppingCart;
import priv.lmh.shop.R;

/**
 * Created by HY on 2018/4/20.
 */

public class OrderAdapter extends BaseAdapter<ShoppingCart> {

    public OrderAdapter(Context context, List<ShoppingCart> data, int layoutResId) {
        super(context, data, layoutResId);

    }

    @Override
    public void bindData(BaseViewHolder holder, ShoppingCart shoppingCart) {
        SimpleDraweeView simpleDraweeView = (SimpleDraweeView) holder.getView(R.id.img_order);
        simpleDraweeView.setImageURI(shoppingCart.getImgUrl());

        holder.getTextView(R.id.txt_name).setText(shoppingCart.getName());
        holder.getTextView(R.id.txt_price).setText("￥"+shoppingCart.getPrice());
        holder.getTextView(R.id.txt_amount).setText(shoppingCart.getCount()+"");

    }
}
