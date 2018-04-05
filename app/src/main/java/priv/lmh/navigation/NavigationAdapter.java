package priv.lmh.navigation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by HY on 2018/3/9.
 */

public abstract class NavigationAdapter implements NavigationBuilder {
    protected Context mContext;
    protected int leftIconRes;
    protected int titleRes;
    protected int rightTextRes;

    View.OnClickListener leftIconListener;
    View.OnClickListener rightIconListener;

    public NavigationAdapter(Context context){
        this.mContext = context;
    }

    @Override
    public NavigationAdapter setLeftIcon(int iconRes) {
        this.leftIconRes = iconRes;
        return this;
    }

    @Override
    public NavigationAdapter setLeftIconListener(View.OnClickListener onClickListener) {
        this.leftIconListener = onClickListener;
        return this;
    }

    @Override
    public NavigationBuilder setTitle(int textRes) {
        this.titleRes = textRes;
        return this;
    }

    @Override
    public NavigationAdapter setRightText(int textRes) {
        this.rightTextRes = textRes;
        return this;
    }

    @Override
    public NavigationAdapter setRightTextListener(View.OnClickListener onClickListener) {
        this.rightIconListener = onClickListener;
        return this;
    }

    @Override
    public View createAndBind(ViewGroup parent) {
        View view = LayoutInflater.from(getContext()).inflate(getLayoutId(),parent,false);
        ViewGroup viewGroup = (ViewGroup) view.getParent();
        if(viewGroup != null){
            viewGroup.removeView(view);
        }
        parent.addView(view,0);
        return view;
    }

    public void setImageViewStyle(View view,int imageViewId, int resId, View.OnClickListener onClickListener){
        ImageView imageView = view.findViewById(imageViewId);
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageResource(resId);
        imageView.setOnClickListener(onClickListener);
    }

    public void setTextViewStyle(View view,int textViewId, int resId, View.OnClickListener onClickListener){
        TextView textView = view.findViewById(textViewId);
        textView.setVisibility(View.VISIBLE);
        textView.setText(resId);
        textView.setOnClickListener(onClickListener);
    }

    protected abstract int getLayoutId();

    public Context getContext(){
        return mContext;
    }
}
