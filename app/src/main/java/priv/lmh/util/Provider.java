package priv.lmh.util;

import android.content.Context;
import android.os.Handler;
import android.util.SparseArray;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import priv.lmh.bean.HotWare;
import priv.lmh.bean.ShoppingCart;

/**
 * Created by HY on 2018/2/13.
 */

public abstract class Provider{
    protected SparseArray<ShoppingCart> mData;

    protected Context mContext;

    protected String mPhoneNumber;

    abstract public void setStringData();

    protected List<ShoppingCart> getData(){
        //不同的存储方式有不同的获取数据的方法
        String json = getStringData();
        List<ShoppingCart> carts = null;
        if(json != null){
            carts = JSONUtil.fromJson(json,new TypeToken<List<ShoppingCart>>(){}.getType());
        }
        return carts;
    }
    public List<ShoppingCart> getAll(){
        return getData();
    };

    public float getTotalPrice(){
        List<ShoppingCart> carts = getData();
        float sum = 0;
        if(carts != null && carts.size() != 0){
            for(ShoppingCart cart:carts){
                sum+=cart.getCount()*cart.getPrice();
            }
        }
        return sum;
    }

    //增删查改操作都一样，不一样的是提交的方式不同
    public void put(ShoppingCart cart){
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
    };

    public void delete(ShoppingCart cart){
        mData.delete((int)cart.getId());
        commit();
    };

    public void update(ShoppingCart cart,int count){
        ShoppingCart temp = mData.get((int)cart.getId());

        temp.setCount(count);
        mData.put((int)temp.getId(),temp);

        commit();
    };

    //将sparse数组转化为List
    protected List<ShoppingCart> sparseToList() {
        int size = mData.size();

        List<ShoppingCart> carts = new ArrayList<>(size);
        for(int i = 0;i < size;i++){
            carts.add(mData.valueAt(i));
        }

        return carts;
    }

    //将List转化为Sparse数组
    protected void listToSparse() {
        List<ShoppingCart> carts = getData();

        if (carts != null && carts.size() != 0) {
            for (ShoppingCart cart : carts) {
                mData.put((int) cart.getId(), cart);
            }
        }
    }

    public static ShoppingCart convertToShoppingCart(HotWare ware){
        ShoppingCart cart = new ShoppingCart();

        cart.setId(ware.getId());
        cart.setDescription(ware.getDescription());
        cart.setImgUrl(ware.getImgUrl());
        cart.setName(ware.getName());
        cart.setPrice(ware.getPrice());

        return cart;
    }

    abstract protected String getStringData();

    abstract protected void commit();
}
