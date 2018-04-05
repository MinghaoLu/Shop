package priv.lmh.shop;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import priv.lmh.bean.HotWare;
import priv.lmh.util.CartProvider;
import priv.lmh.widget.CNToolBar;

/**
 * Created by HY on 2018/1/4.
 */

public class WareDetailActivity extends AppCompatActivity {
    private WebView mWebView;

    private CNToolBar mToolBar;

    private CartProvider mProvider;

    private HotWare mWare;

    private WebAppInterface mAppInterface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ware_detail);

        mProvider = new CartProvider(this,ShopApplication.phoneNumber);

        initWare();

        initToolBar();

        initWebView();


    }

    private void initWare() {
        mWare = (HotWare) getIntent().getSerializableExtra("WARE");
        Toast.makeText(this, ""+mWare.getId(), Toast.LENGTH_SHORT).show();
    }

    private void initToolBar() {
        mToolBar = (CNToolBar) findViewById(R.id.toolBar_detail);

        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WareDetailActivity.this.finish();
            }
        });
    }

    private void initWebView(){
        mWebView = (WebView) findViewById(R.id.webView_detail);

        WebSettings  webSetting = mWebView.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setBlockNetworkImage(false);
        webSetting.setAppCacheEnabled(true);

        /*webSetting.setDomStorageEnabled(true);
        webSetting.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);*/

        /*webSetting.setSupportZoom(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSetting.setLoadWithOverviewMode(true);*/

        //webSetting.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        mWebView.setWebChromeClient(new WebChromeClient());

        mWebView.loadUrl("http://112.124.22.238:8081/course_api/wares/detail.html");

        mAppInterface = new WebAppInterface(this);
        mWebView.addJavascriptInterface(mAppInterface,"appInterface");
        mWebView.setWebViewClient(new MyWebViewClient());
        //mWebView.loadUrl("http://www.baidu.com");
    }

    class MyWebViewClient extends WebViewClient{
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mAppInterface.showDetail();
        }
    }

    //Android调用JS中的AJAX   JS调用Android
    class WebAppInterface{
        private Context mContext;
        public WebAppInterface(Context context){
            mContext = context;
        }

        //调用AJAX
        @JavascriptInterface
        public void showDetail(){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mWebView.loadUrl("javascript:showDetail("+mWare.getId()+")");
                }
            });
        }

        @JavascriptInterface
        public void buy(long id){
            if(ShopApplication.isLogined){
                mProvider.put(CartProvider.convertToShoppingCart(mWare));

                Toast.makeText(mContext, "已加入购物车", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(WareDetailActivity.this,"请登录",Toast.LENGTH_LONG).show();

                Intent intent = new Intent(WareDetailActivity.this,LoginActivity.class);
                startActivity(intent);
            }

        }

        @JavascriptInterface
        public void addFavorite(){

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mProvider = new CartProvider(this,ShopApplication.phoneNumber);
    }
}
