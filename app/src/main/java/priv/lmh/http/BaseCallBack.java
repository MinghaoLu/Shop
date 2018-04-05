package priv.lmh.http;

import com.google.gson.internal.$Gson$Types;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by HY on 2017/12/10.
 * 策略模式,委托BaseCallBack处理回调
 */

public abstract class BaseCallBack<T> {
    public Type mType;

    static Type getSuperclassTypeParameter(Class<?> subclass)
    {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class)
        {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }

    public BaseCallBack() {
        this.mType = getSuperclassTypeParameter(getClass());
    }

    public abstract void onFailure(Request requst, IOException e);

    public abstract void onSuccess(Response response,T t);
    public abstract void onError(Response response,int code,Exception e);
}
