package priv.lmh.navigation;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by HY on 2018/3/9.
 */

public interface NavigationBuilder {
    public NavigationBuilder setLeftIcon(int iconRes);
    public NavigationBuilder setLeftIconListener(View.OnClickListener onClickListener);

    public NavigationBuilder setTitle(int textRes);

    public NavigationBuilder setRightText(int textRes);
    public NavigationBuilder setRightTextListener(View.OnClickListener onClickListener);

    public View createAndBind(ViewGroup viewGroup);

}
