package priv.lmh.shop;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import priv.lmh.bean.ShoppingCart;
import priv.lmh.bean.UserInfo;
import priv.lmh.util.NetCartProvider;
import priv.lmh.util.Provider;
import priv.lmh.widget.CNToolBar;

/**
 * Created by HY on 2018/1/6.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private AppCompatEditText txtPhoneNumber;
    private AppCompatEditText txtPassWord;
    private TextView txtToRegister;

    private Button btnLogin;

    private CNToolBar toolbar;

    private boolean isValid = false;

    private Provider mProvider;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        initView();

        setListener();

    }

    private void initView() {
        txtPhoneNumber = (AppCompatEditText) findViewById(R.id.txt_phoneNumber);
        txtPassWord = (AppCompatEditText) findViewById(R.id.txt_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        txtToRegister = (TextView) findViewById(R.id.txt_to_register);

        toolbar = (CNToolBar) findViewById(R.id.toolbar_login);
    }

    private void setListener(){
        btnLogin.setOnClickListener(this);
        txtToRegister.setOnClickListener(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.this.finish();
            }
        });

    }

    @Override
    public void onClick(View v) {
        if(v == btnLogin){
            verifyUser(txtPhoneNumber.getText().toString(),txtPassWord.getText().toString());
        }else if(v == txtToRegister){
            startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
        }
    }

    private void verifyUser(final String phoneNumber, String passWord){
        BmobQuery<UserInfo> query = new BmobQuery<>();
        query.addWhereEqualTo("phoneNumber",phoneNumber);
        query.addWhereEqualTo("passWord",passWord);
        query.findObjects(new FindListener<UserInfo>() {
            @Override
            public void done(List<UserInfo> list, BmobException e) {
               if(list.size() > 0){
                   Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                   ShopApplication.isLogined = true;
                   ShopApplication.phoneNumber = phoneNumber;
                   ShopApplication.username = list.get(0).getUserName();
                   ShopApplication.image = list.get(0).getImage();
                   /*ShopApplication.objectId = list.get(0).getObjectId();

                   mProvider = new NetCartProvider(LoginActivity.this,phoneNumber);
                   mProvider.setStringData();*/

                   LoginActivity.this.finish();
                   return;
               }
                Toast.makeText(LoginActivity.this,"登录失败",Toast.LENGTH_LONG).show();
            }
        });
    }
}
