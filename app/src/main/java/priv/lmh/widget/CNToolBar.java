package priv.lmh.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.TintTypedArray;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import priv.lmh.shop.R;

/**
 * Created by HY on 2017/8/31.
 * 自定义Toolbar
 */

public class CNToolBar extends Toolbar {
    private LayoutInflater mLayoutInflater;
    private View mView;
    private EditText mSearchView;
    private TextView mTextTitle;
    private Button mBtnEdit;
    private Button mBtnCancel;

    public CNToolBar(Context context) {
        this(context,null);
    }

    public CNToolBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CNToolBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mLayoutInflater = LayoutInflater.from(context);

        initView();
        setContentInsetsRelative(10,10);

        TintTypedArray a = TintTypedArray.obtainStyledAttributes(context,attrs,R.styleable.CNToolBar,defStyleAttr,0);

        boolean isShowSearchView = a.getBoolean(R.styleable.CNToolBar_isShowSearchView,false);
        if(isShowSearchView){
            showSearchView();
            hideTitleView();
        }

        String title = a.getString(R.styleable.CNToolBar_label);
        if(title != null){
            setTitle(title);
            hideSearchView();
        }

        /*Drawable rightButtonIcon = a.getDrawable(R.styleable.CNToolBar_rightButtonIcon);
        if(rightButtonIcon != null){
            setRightButtonIcon(rightButtonIcon);
        }*/
        boolean rightButton = a.getBoolean(R.styleable.CNToolBar_rightButton,false);
        if(rightButton == true){
            mBtnEdit = findViewById(R.id.btn_edit);
            mBtnEdit.setVisibility(INVISIBLE);
        }

        a.recycle();
    }

    private void initView() {
        if(mView == null){
            mView = mLayoutInflater.inflate(R.layout.toolbar,null);
            mSearchView =  mView.findViewById(R.id.toolbar_searchview);
            mTextTitle =  mView.findViewById(R.id.toolbar_title);
            //mBtnEdit = (Button) mView.findViewById(R.id.btn_edit);
            //mBtnCancel = (Button) mView.findViewById(R.id.btn_cancel);

            //自定义View
            LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL);
            addView(mView,lp);
        }

    }

    @Override
    public void setTitle(int resId) {
        setTitle(getContext().getText(resId));
    }

    @Override
    public void setTitle(CharSequence title) {
        if(mTextTitle != null){
            mTextTitle.setText(title);
            showTitleView();
        }
    }

    /*private void setRightButtonIcon(Drawable icon){
        mImageButton.setImageDrawable(icon);
        showRightButton();
    }*/

    public  void showSearchView(){

        if(mSearchView !=null)
            mSearchView.setVisibility(VISIBLE);

    }


    public void hideSearchView(){
        if(mSearchView !=null)
            mSearchView.setVisibility(GONE);
    }

    public void showTitleView(){
        if(mTextTitle !=null)
            mTextTitle.setVisibility(VISIBLE);
    }


    public void hideTitleView() {
        if (mTextTitle != null)
            mTextTitle.setVisibility(GONE);

    }

    /*private void showRightButton(){
        if(mImageButton != null){
            mImageButton.setVisibility(VISIBLE);
        }
    }

    private  void hideRightButton(){
        if(mImageButton != null){
            mImageButton.setVisibility(GONE);
        }
    }*/

}
