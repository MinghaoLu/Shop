package priv.lmh.util;

import android.content.Context;
import android.os.Handler;
import android.util.SparseArray;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import priv.lmh.bean.HotWare;
import priv.lmh.bean.ShoppingCart;

/**
 * Created by HY on 2017/12/18.
 * 购物车数据存储在这里，算是中介
 */

public class CartProvider extends Provider{


    public static final String CART_JSON="cart_json";

    public CartProvider(Context context,String phoneNumeber){
        mData = new SparseArray<>();

        this.mContext = context;
        this.mPhoneNumber = phoneNumeber;
        listToSparse();
    }

    @Override
    public void setStringData() {

    }

    @Override
    protected String getStringData() {
        return PreferencesUtils.getString(mContext,mPhoneNumber+"");
    }

    //保存
    protected void commit(){
        List<ShoppingCart> carts = sparseToList();

        PreferencesUtils.putString(mContext,mPhoneNumber+"",JSONUtil.toJSON(carts));
    }

    /*public void put(ShoppingCart cart){
        ShoppingCart temp = mData.get((int)cart.getId());

        //先检查是否在购物车当中
        if(temp != null){
            temp.setCount(temp.getCount()+1);
        }else {
            temp = cart;
            temp.setCount(1);
        }
        mData.put((int)cart.getId(),temp);

        commit();
    }*/

   /* //对购物车中商品的数量进行更改
    public void update(ShoppingCart cart,int count){
        ShoppingCart temp = mData.get((int)cart.getId());

        temp.setCount(count);
        mData.put((int)temp.getId(),temp);

        commit();
    }*/

    /*public List<ShoppingCart> getAll(){
        return getData();
    }*/

    /*public void delete(ShoppingCart cart){
        mData.delete((int)cart.getId());

        commit();
    }*/

    /*protected List<ShoppingCart> sparseToList() {
        int size = mData.size();

        List<ShoppingCart> carts = new ArrayList<>(size);
        for(int i = 0;i < size;i++){
            carts.add(mData.valueAt(i));
        }

        return carts;
    }

    protected void listToSparse(){
        List<ShoppingCart> carts = getData();

        if(carts!= null && carts.size()!=0){
            for(ShoppingCart cart:carts){
                mData.put((int)cart.getId(),cart);
            }
        }
    }*/

    /*//提取购物车的String数据并转换为List
    @Override
    protected List<ShoppingCart> getData(){
        String json = getStringData();
        List<ShoppingCart> carts = null;
        if(json != null){
            carts = JSONUtil.fromJson(json,new TypeToken<List<ShoppingCart>>(){}.getType());
        }
        return carts;
    }*/

    /*public float getTotalPrice(){
        List<ShoppingCart> carts = getData();
        float sum = 0;
        for(ShoppingCart cart:carts){
            sum+=cart.getCount()*cart.getPrice();
        }
        return sum;
    }*/


    /*public static ShoppingCart convertToShoppingCart(HotWare ware){
        ShoppingCart cart = new ShoppingCart();

        cart.setId(ware.getId());
        cart.setDescription(ware.getDescription());
        cart.setImgUrl(ware.getImgUrl());
        cart.setName(ware.getName());
        cart.setPrice(ware.getPrice());

        return cart;
    }*/
}
