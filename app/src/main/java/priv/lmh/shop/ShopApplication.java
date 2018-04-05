package priv.lmh.shop;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

import cn.bmob.v3.Bmob;

/**
 * Created by HY on 2017/12/12.
 */

public class ShopApplication extends Application {
    public static boolean isLogined = false;
    public static String phoneNumber = "";
    public static String objectId = "";
    public static  String cartStringData="";
    public static String username = "";
    public static String image = "";

    @Override
    public void onCreate() {
        super.onCreate();

        Fresco.initialize(this);
        Bmob.initialize(this,"3e17e73437fa1b07f9a19da9566994d4");
    }
}
