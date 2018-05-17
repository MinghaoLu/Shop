package priv.lmh.util;

import java.util.List;

/**
 * Created by HY on 2018/4/21.
 */

public interface OnUIThreadListener<T> {
    void onResult(T result);
}
