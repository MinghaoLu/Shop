package priv.lmh.http;

import android.util.Log;

/*import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;*/

import java.io.IOException;

import okhttp3.Request;

/**
 * Created by HY on 2017/12/11.
 */

public abstract class SpotsCallBack<T> extends BaseCallBack<T> {
    @Override
    public void onFailure(IOException e) {
        Log.i("1","Failed");
    }


}
