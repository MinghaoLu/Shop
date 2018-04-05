package priv.lmh.bean;

import java.io.Serializable;

/**
 * Created by HY on 2017/12/9.
 */

public class BaseBean implements Serializable {
    protected long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
