package priv.lmh.util;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.SparseArray;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import priv.lmh.bean.ShoppingCart;
import priv.lmh.bean.UserInfo;
import priv.lmh.shop.ShopApplication;

/**
 * Created by HY on 2018/2/13.
 */

public class NetCartProvider extends Provider {
    public NetCartProvider(Context context,String phoneNumber){
        mData = new SparseArray<>();

        this.mContext = context;
        this.mPhoneNumber = phoneNumber;

        listToSparse();
    }

    //获取服务器数据
    protected String getStringData() {
        if(mPhoneNumber.equals(""))
            return null;

        return ShopApplication.cartStringData;
    }

    //提前获取数据并储存
    public void setStringData(){
        //向云数据库请求数据
        BmobQuery<UserInfo> query = new BmobQuery<>();
        query.addWhereEqualTo("phoneNumber",mPhoneNumber);
        query.findObjects(new FindListener<UserInfo>() {
            @Override
            public void done(List<UserInfo> list, BmobException e) {
                if(list!=null && list.size()!=0){
                    ShopApplication.cartStringData = list.get(0).getCart();
                }
            }
        });
    }

    @Override
    protected void commit() {
        String json;
        List<ShoppingCart> carts = sparseToList();
        assert carts != null;
        if(carts == null || carts.size()==0){
            json = "";
        }else {
            json = JSONUtil.toJSON(carts);
        }

        //提交到服务器
        UserInfo userInfo = new UserInfo();
        userInfo.setValue("cart",json);
        userInfo.update(ShopApplication.objectId,new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e == null){
                    Toast.makeText(mContext,"成功",Toast.LENGTH_LONG).show();

                    setStringData();
                }else {
                    Toast.makeText(mContext,"失败",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
