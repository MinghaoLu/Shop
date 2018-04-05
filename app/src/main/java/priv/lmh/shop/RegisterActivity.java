package priv.lmh.shop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import priv.lmh.navigation.RegisterNavigationBuilder;

/**
 * Created by HY on 2018/3/9.
 */

public class RegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initToolBar();
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
}
