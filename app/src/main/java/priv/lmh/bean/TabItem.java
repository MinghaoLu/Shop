package priv.lmh.bean;

import android.support.v4.app.Fragment;

/**
 * Created by HY on 2017/8/30.
 */

public class TabItem {
    private int image;
    private int title;
    private Class<? extends Fragment> cls;

    public TabItem(int image, int title, Class<? extends Fragment> cls) {
        this.image = image;
        this.title = title;
        this.cls = cls;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public Class<? extends Fragment> getCls() {
        return cls;
    }

    public void setCls(Class<? extends Fragment> cls) {
        this.cls = cls;
    }
}
