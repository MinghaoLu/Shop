package priv.lmh.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import priv.lmh.shop.LoginActivity;
import priv.lmh.shop.R;
import priv.lmh.shop.ShopApplication;

/**
 * Created by HY on 2017/8/31.
 */

public class MineFragment extends Fragment implements View.OnClickListener{
    private Button mBtnLog;
    private CircleImageView mImgHead;
    private TextView mTxtUserName;

    private LinearLayout mAlreadyLog;
    private TextView mTxtOrders;
    private Button mBtnExit;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine,container,false);

        initView(view);

        setListener();

        showUser();

        return view;
    }

    private void initView(View view) {
        mBtnLog = (Button) view.findViewById(R.id.btn_login);
        mImgHead = view.findViewById(R.id.img_head);
        mTxtUserName = view.findViewById(R.id.txt_username);

        mAlreadyLog = view.findViewById(R.id.already_log);
        mTxtOrders = view.findViewById(R.id.txt_myorders);
        mBtnExit = view.findViewById(R.id.btn_exit);
    }

    private void setListener() {
        mBtnLog.setOnClickListener(this);
        mImgHead.setOnClickListener(this);

        mTxtOrders.setOnClickListener(this);
        mBtnExit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == mImgHead && !ShopApplication.isLogined){
            Intent intent = new Intent(getActivity(), LoginActivity.class);

            startActivityForResult(intent,0);
        }else if(v == mTxtOrders){

        }else if(v == mBtnExit){
            //登录信息置空
            ShopApplication.isLogined = false;
            ShopApplication.phoneNumber = "";
            ShopApplication.username = "";
            //ShopApplication.objectId = "";
            //ShopApplication.cartStringData="";

            //退出登陆后按钮的显示与隐藏
            mImgHead.setImageResource(R.drawable.default_head);
            mTxtUserName.setText("点击登录");
            mAlreadyLog.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("TAG","onActivityResult");
        showUser();
    }

    private void showUser() {
        if(ShopApplication.isLogined){
            if(ShopApplication.username != "" && ShopApplication.username != null){
                mTxtUserName.setText(ShopApplication.username);
            }else {
                mTxtUserName.setText("");
            }

            if(!ShopApplication.image.equals("")){
                Picasso.with(getActivity()).load(Uri.parse(ShopApplication.image)).into(mImgHead);
            }
            mAlreadyLog.setVisibility(View.VISIBLE);
        }
    }
}
