package priv.lmh.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.zip.Inflater;

import priv.lmh.shop.R;

/**
 * Created by HY on 2017/11/16.
 */

public class NumAddSubView extends LinearLayout implements View.OnClickListener{
    private LayoutInflater mInflater;

    private Button mBtnAdd;
    private TextView mTextNum;
    private Button mBtnSub;

    private int mValue;
    private int minValue;
    private int maxValue;

    private OnButtonClickListener mOnButtonClickListener;

    public NumAddSubView(Context context) {
        this(context,null);
    }

    public NumAddSubView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public NumAddSubView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mInflater = LayoutInflater.from(context);

        initView();

        if(attrs != null){
            TintTypedArray a = TintTypedArray.obtainStyledAttributes(context,attrs, R.styleable.NumAddSubView,defStyleAttr,0);

            //获取属性值并设置
            minValue = a.getInt(R.styleable.NumAddSubView_minValue,1);
            maxValue = a.getInt(R.styleable.NumAddSubView_maxValue,99);
            int val = a.getInt(R.styleable.NumAddSubView_value,0);
            setValue(val);

            a.recycle();

            //setOnButtonClickLister(new Listener());
        }

    }

    //初始化布局
    private void initView(){
        View view = mInflater.inflate(R.layout.widget_number_add_sup,this,true);

        //具体控件
        mBtnAdd = (Button) view.findViewById(R.id.btn_add);
        mTextNum = (TextView) view.findViewById(R.id.txt_num);
        mBtnSub = (Button) view.findViewById(R.id.btn_sub);

        mBtnAdd.setOnClickListener(this);
        mBtnSub.setOnClickListener(this);

    }

    public void setOnButtonClickLister(OnButtonClickListener mOnButtonClickListener){
        this.mOnButtonClickListener = mOnButtonClickListener;
    }

    public int getValue() {
        return mValue;
    }

    public void setValue(int value) {
        if(value >=minValue && value <= maxValue){
            mTextNum.setText(value + "");
            this.mValue = value;
        }else {
            //什么也不做
        }

    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    @Override
    public void onClick(View v) {
        if(mOnButtonClickListener != null){
            mOnButtonClickListener.onButtonClick(v,mValue);
        }
    }

    //实现接口来实现具体行为
    /*private class Listener implements OnButtonClickListener{
        @Override
        public void onButtonClick(View view, int value) {
            if(view.getId() == R.id.btn_add){
                ++value;
                setValue(value);
            }else if(view.getId() == R.id.btn_sub) {
                --value;
                setValue(value);
            }

        }
    }*/

    //利用接口来实现按钮的具体行为,策略模式
    public interface OnButtonClickListener{
        void onButtonClick(View view,int value);
    }
}
