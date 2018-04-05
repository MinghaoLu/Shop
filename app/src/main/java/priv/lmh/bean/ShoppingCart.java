package priv.lmh.bean;

/**
 * Created by HY on 2017/12/18.
 */

public class ShoppingCart extends HotWare {
    private int count;
    private boolean isChecked = true;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
