package priv.lmh.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import priv.lmh.shop.R;

/**
 * Created by HY on 2018/1/17.
 */

public class CustomView1 extends View {
    private int mWidth;
    private int mHeight;

    private float mCircleXY;//位置(圆心的位置)
    private float mRadius;//半径

    private Paint mCirclePaint;

    public CustomView1(Context context) {
        this(context,null);
    }

    public CustomView1(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomView1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CustomView1(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();

        /*TintTypedArray a = TintTypedArray.obtainStyledAttributes(context,attrs, R.styleable.CustomView1,defStyleAttr,0);

        if(attrs!=null){
            mLength = a.getInt(R.styleable.CustomView1_length,200);


        }

        a.recycle();*/
    }

    private void init(){
        mCirclePaint = new Paint();
        mCirclePaint.setColor(Color.RED);
        mCirclePaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        if(widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST){

        }else if (widthSpecMode == MeasureSpec.AT_MOST){

        }else if (heightSpecMode == MeasureSpec.AT_MOST){

        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        float width = getWidth();
        float height = getHeight();
        mRadius = Math.min(width,height)/2;

        canvas.drawCircle(width/2,height/2,mRadius,mCirclePaint);
    }
}
