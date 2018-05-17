package priv.lmh.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.nineoldandroids.view.ViewHelper;

/**
 * Created by HY on 2018/5/17.
 */

public class CustomButton extends AppCompatButton {
    private static final String TAG = "CustomButton";

    private float mLastX;
    private float mLastY;

    public CustomButton(Context context) {
        super(context);
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x =  event.getRawX();
        float y =  event.getRawY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN :
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "onTouchEvent: Move...");

                float deltaX = x - mLastX;
                float deltaY = y - mLastY;

                float translationX =  ViewHelper.getTranslationX(this) + deltaX;
                float translationY = ViewHelper.getTranslationY(this) +deltaY;

                ViewHelper.setTranslationX(this,translationX);
                ViewHelper.setTranslationY(this,translationY);
                break;

            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }

        mLastX = x;
        mLastY = y;
        return super.onTouchEvent(event);
    }

    private int computeScrollDistance(){
        return 0;
    }

    private void scroll(){

    }
}
