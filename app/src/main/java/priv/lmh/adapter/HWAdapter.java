package priv.lmh.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import priv.lmh.bean.HotWare;
import priv.lmh.bean.ShoppingCart;
import priv.lmh.shop.LoginActivity;
import priv.lmh.shop.R;
import priv.lmh.shop.ShopApplication;
import priv.lmh.util.CartProvider;
import priv.lmh.util.NetCartProvider;
import priv.lmh.util.Provider;

/**
 * Created by HY on 2017/12/19.
 */

public class HWAdapter extends BaseAdapter<HotWare> {
    private static final String TAG = "HWAdapter";

    private Provider mProvider;

    public HWAdapter(Context context, List<HotWare> data, int layoutResId) {
        super(context, data, layoutResId);
    }

    @Override
    public void bindData(BaseViewHolder holder, final HotWare hotWare) {
        SimpleDraweeView simpleDraweeView = (SimpleDraweeView) holder.getView(R.id.img_hot);
        simpleDraweeView.setImageURI(Uri.parse(hotWare.getImgUrl()));

        holder.getTextView(R.id.tv_name).setText(hotWare.getName());
        holder.getTextView(R.id.tv_price).setText("￥"+hotWare.getPrice());

        Button btn_buy = (Button) holder.getView(R.id.btn_buy);
        btn_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //如果还没有登录，跳转到登录界面
                if(!ShopApplication.isLogined ||ShopApplication.phoneNumber.equals("")){
                    //跳转到登录界面
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    mContext.startActivity(intent);
                }else {
                    //否则加入到购物车当中
                    //加入到Provider中
                    //先将HotWare转换为ShoppingCart
                    if(mProvider == null){
                        mProvider = new CartProvider(mContext,ShopApplication.phoneNumber);
                    }

                    mProvider.put(convertData(hotWare));
                    Toast.makeText(mContext, "商品已加入购物车", Toast.LENGTH_SHORT).show();
                    Log.d(TAG,convertData(hotWare).getName());
                }

            }
        });
    }

    private ShoppingCart convertData(HotWare ware){
        ShoppingCart cart = new ShoppingCart();

        cart.setId(ware.getId());
        cart.setDescription(ware.getDescription());
        cart.setImgUrl(ware.getImgUrl());
        cart.setName(ware.getName());
        cart.setPrice(ware.getPrice());

        return cart;
    }
}
