package priv.lmh.util;

import android.content.Context;
import android.util.Log;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import priv.lmh.bean.ShoppingCart;
import priv.lmh.dao.MySQLite;

/**
 * Created by HY on 2018/4/14.
 */

public class ShoppingUtil {
    private static final String TAG = "ShoppingUtil";

    private String phonenumber;
    private Provider mProvider;

    private MySQLite mySQLite;

    public ShoppingUtil(Context context, String phonenumber){
        this(context,phonenumber,null);
    }

    public ShoppingUtil(Context context, String phonenumber, Provider provider){
        this.phonenumber = phonenumber;
        mySQLite = new MySQLite(context);
        mProvider = provider;
    }


    public void order(List<ShoppingCart> cartOrder){
        if(cartOrder == null || cartOrder.size() == 0){
            return;
        }

        List<ShoppingCart> newOrder = Collections.synchronizedList(new ArrayList<ShoppingCart>());
        int j =0;
        //加入到数据库中
        for(int i = 0;i<cartOrder.size();i++) {
            ShoppingCart cart = cartOrder.get(i);
            if(!cart.isChecked()){
                j++;
                Log.d(TAG, "order: unchecked");
            }
            if(cart.isChecked()){
                Log.d(TAG, "order()" + "i:"+cart.getId());
                cartOrder.remove(i);
                newOrder.add(cart);
                mProvider.delete(cart);
            }
        }
        Log.d(TAG, "order: unchecked:" + j);
        Log.d(TAG, "order: " + newOrder.size());
        if(newOrder.size() > 0){
            mySQLite.addOrder(phonenumber,newOrder);
        }
        Log.d(TAG, "order: successful!");
    }

    public  List<ShoppingCart> getOrder(){
        return mySQLite.getOrder(phonenumber);
    }

    public void close(){
        if(mySQLite != null){
            mySQLite.close();
        }
    }

    public interface OnSPResultListener<T>{
        void onResult(T result);
    }
}
