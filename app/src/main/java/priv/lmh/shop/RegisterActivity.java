package priv.lmh.shop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import priv.lmh.bean.UserInfo;
import priv.lmh.navigation.RegisterNavigationBuilder;

/**
 * Created by HY on 2018/3/9.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "RegisterActivity";

    private AppCompatEditText mTxtPhoneNumber;
    private AppCompatEditText mTxtPassWord;
    private AppCompatEditText mTxtUserName;
    private AppCompatButton mBtnRegister;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initToolBar();

        initViewAndSetListener();
    }

    private void initToolBar() {
        LinearLayout toolbar = (LinearLayout) findViewById(R.id.ll_toolbar);

        RegisterNavigationBuilder builder = new RegisterNavigationBuilder(this);
        builder.setLeftIcon(R.drawable.icon_back_32px)
                .setLeftIconListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        RegisterActivity.this.finish();
                    }
                })
                .setTitle(R.string.register)
                .createAndBind(toolbar);

    }

    private void initViewAndSetListener() {
        mTxtPhoneNumber = (AppCompatEditText) findViewById(R.id.txt_phoneNumber);
        mTxtPassWord = (AppCompatEditText) findViewById(R.id.txt_password);
        mTxtUserName = (AppCompatEditText) findViewById(R.id.txt_username);
        mBtnRegister = (AppCompatButton) findViewById(R.id.btn_register);

        mTxtUserName.setOnClickListener(this);
        mTxtPassWord.setOnClickListener(this);
        mTxtPhoneNumber.setOnClickListener(this);
        mBtnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_register:
                Log.d(TAG, "onClick: true");
                if(mTxtUserName.getText().toString().equals("") || mTxtPassWord.getText().toString().equals("") || mTxtUserName.getText().toString().equals("")){
                    Toast.makeText(this,"帐号或者密码或者昵称不能为空不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                verifyUser(mTxtPhoneNumber.getText().toString(),mTxtPassWord.getText().toString(),mTxtUserName.getText().toString());
                break;
        }
    }

    private void verifyUser(final String phoneNumber, final String password,final String username){
        Log.d(TAG, "verifyUser: true");
        BmobQuery<UserInfo> query = new BmobQuery<>();
        query.addWhereEqualTo("phoneNumber",phoneNumber);
        query.findObjects(new FindListener<UserInfo>() {
            @Override
            public void done(List<UserInfo> list, BmobException e) {
                if(list != null && list.size()!=0){
                    Toast.makeText(RegisterActivity.this,"帐号已存在",Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "done: exist");
                }else {
                    Toast.makeText(RegisterActivity.this,"正在注册，请稍等",Toast.LENGTH_SHORT).show();
                    //添加数据到云数据库
                    addUser(phoneNumber,password,username);

                    RegisterActivity.this.finish();
                }
            }
        });
    }

    private void addUser(String phoneNumber, String password,String username) {
        UserInfo user = new UserInfo();
        user.setPhoneNumber(phoneNumber);
        user.setPassWord(password);
        user.setUserName(username);
        user.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e==null){
                    Toast.makeText(RegisterActivity.this,"注册成功，请登录",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(RegisterActivity.this,"注册成功失败！",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
