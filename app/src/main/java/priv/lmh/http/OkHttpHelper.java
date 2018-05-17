package priv.lmh.http;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
/*import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;*/

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by HY on 2017/12/10.
 */

public class OkHttpHelper {
    public static final String TAG="OkHttpHelper";
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    private static OkHttpClient mHttpClient;

    private Gson mGson;

    private Handler mHandler;

    private OkHttpHelper(){
        mHttpClient = new OkHttpClient();

        mGson = new Gson();

        mHandler = new Handler(Looper.getMainLooper());
    }

    public static OkHttpHelper getInstance(){
        return new OkHttpHelper();
    }

    public void post(String url, String params,BaseCallBack baseCallBack){
        Request request = buildRequest(url,params,HttpMethodType.POST);
        doRequest(request,baseCallBack);
    }

    public void get(String url,BaseCallBack baseCallBack){
        Request request = buildRequest(url,null,HttpMethodType.GET);
        doRequest(request,baseCallBack);
    }

    public void doRequest(final Request request, final BaseCallBack baseCallBack){
        mHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                baseCallBack.onFailure(e);
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if(response.isSuccessful()){
                    String resultStr = response.body().string();
                    if(baseCallBack.mType == String.class){
                        //callBackOnSuccess(baseCallBack,response,resultStr);
                    }else {
                        try{
                            final Object object = mGson.fromJson(resultStr,baseCallBack.mType);
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    baseCallBack.onSuccess(response,object);
                                }
                            });
                        }catch (JsonParseException e){
                            Log.d("json",e.getMessage());
                            baseCallBack.onError(response,response.code());
                        }

                    }
                }else {
                    baseCallBack.onError(response,response.code());
                }
            }
        });
    }



    public Request buildRequest(String url,String params,HttpMethodType httpMethodType){
        Request.Builder builder = new Request.Builder();
        builder.url(url);

        if(httpMethodType == HttpMethodType.GET){
            builder.get();
        }else if(httpMethodType == HttpMethodType.POST){
            RequestBody requestBody = buildFormData(params);
            builder.post(requestBody);
        }
        return builder.build();
    }

    private RequestBody buildFormData(String params){
        return RequestBody.create(JSON,params);
    }

    enum HttpMethodType{
        POST,
        GET
    }

    private void callBackOnSuccess(final BaseCallBack baseCallBack, final Response response, final Object object){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                baseCallBack.onSuccess(response,object);
            }
        });
    }
}
