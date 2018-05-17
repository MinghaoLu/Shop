package priv.lmh.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ViewUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
/*import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;*/

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;
import priv.lmh.adapter.HomeCategryAdapter;
import priv.lmh.bean.Banner;
import priv.lmh.bean.HomeCampaign;
import priv.lmh.bean.HomeCategory;
import priv.lmh.http.BaseCallBack;
import priv.lmh.http.OkHttpHelper;
import priv.lmh.http.SpotsCallBack;
import priv.lmh.shop.R;

/**
 * Created by HY on 2017/8/31.
 */

public class HomeFragment extends Fragment {
    private View mView;

    private SliderLayout mSlider;
    private PagerIndicator mIndicator;

    private RecyclerView mRecycleView;
    private HomeCategryAdapter mAdapter;

    private List<Banner> mBanner;

    private OkHttpHelper mOkHttpHelper;
    private static String URL_SLIDER = "http://112.124.22.238:8081/course_api/banner/query?type=1";
    private static String URL_CAMPAIGN_HOME = "http://112.124.22.238:8081/course_api/campaign/recommend";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home,container,false);
        mOkHttpHelper = OkHttpHelper.getInstance();

        initBanner();

        initRecycleView();
        return mView;
    }

    private void initBanner(){
        mOkHttpHelper.get(URL_SLIDER, new SpotsCallBack<List<Banner>>() {
            @Override
            public void onSuccess(Response response, List<Banner> banners) {
                mBanner = banners;
                Log.d("2","Success"+mBanner.size());

                initSlider();
            }

            @Override
            public void onError(Response response, int code) {
                System.out.print("ERROR");
                Log.i("3","ERROR");
            }
        });
    }

    private void initSlider(){
        mSlider =  mView.findViewById(R.id.slider);
        mIndicator =  mView.findViewById(R.id.indicator);

        /*List<String> imageUrls = new ArrayList<>();
        final List<String> description = new ArrayList<>();

        imageUrls.add("http://m.360buyimg.com/mobilecms/s300x98_jfs/t2416/102/20949846/13425/a3027ebc/55e6d1b9Ne6fd6d8f.jpg");
        imageUrls.add("http://m.360buyimg.com/mobilecms/s300x98_jfs/t1507/64/486775407/55927/d72d78cb/558d2fbaNb3c2f349.jpg");
        imageUrls.add("http://m.360buyimg.com/mobilecms/s300x98_jfs/t1363/77/1381395719/60705/ce91ad5c/55dd271aN49efd216.jpg");

        description.add("新品推荐");
        description.add("时尚男装");
        description.add("家电秒杀");*/

        for(final Banner banner : mBanner){
            TextSliderView textSliderView = new TextSliderView(this.getActivity());
            textSliderView.image(banner.getImgUrl());
            textSliderView.description(banner.getName());
            mSlider.addSlider(textSliderView);

            textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(BaseSliderView slider) {
                    Log.d("onClick",mBanner.indexOf(banner)+"");
                    //Toast.makeText(HomeFragment.this.getActivity(),0,Toast.LENGTH_LONG).show();
                }
            });
        }



        mSlider.setCustomAnimation(new DescriptionAnimation());
        mSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSlider.setPresetTransformer(SliderLayout.Transformer.RotateUp);
        mSlider.setDuration(2000);
        mSlider.setCustomIndicator(mIndicator);

       /* for(int i = 0; i < imageUrls.size();i++){
            final int a = i;
            TextSliderView textSliderView = new TextSliderView(this.getActivity());
            textSliderView.image(imageUrls.get(i));
            textSliderView.description(description.get(i));
            mSlider.addSlider(textSliderView);*/

            //可以设置监听，点击每张图片对应不同的Intent
           /*

        }*/

        //自定义配置



    }

    private void initRecycleView() {
        mRecycleView =  mView.findViewById(R.id.recycleview);

        /*List<HomeCategory> data = new ArrayList<>();

        HomeCategory  category = new HomeCategory("超值购",R.drawable.img_big_0,R.drawable.img_0_small1,R.drawable.img_0_small2);
        data.add(category);

        category = new HomeCategory("金融街 包赚翻",R.drawable.img_big_1,R.drawable.img_1_small1,R.drawable.img_1_small2);
        data.add(category);

        category = new HomeCategory("品牌街",R.drawable.img_big_2,R.drawable.img_2_small1,R.drawable.img_2_small2);
        data.add(category);

        category = new HomeCategory("有利可图",R.drawable.img_big_3,R.drawable.img_3_small1,R.drawable.imag_3_small2);
        data.add(category);

        category = new HomeCategory("热门活动",R.drawable.img_big_4,R.drawable.img_4_small1,R.drawable.img_4_small2);
        data.add(category);

        mAdapter = new HomeCategryAdapter(data);*/
        mOkHttpHelper.get(URL_CAMPAIGN_HOME, new BaseCallBack<List<HomeCampaign>>() {
            @Override
            public void onFailure(IOException e) {
                Log.d("1","Failed");
            }

            @Override
            public void onSuccess(Response response, List<HomeCampaign> homeCampaigns) {
                Log.d("2",homeCampaigns.size()+"");
                initData(homeCampaigns);
            }

            @Override
            public void onError(Response response, int code) {
                Log.d("3","ERROR");
            }
        });



    }

    private void initData(List<HomeCampaign> homeCampaigns){
        mAdapter = new HomeCategryAdapter(getActivity(),homeCampaigns);

        mRecycleView.setAdapter(mAdapter);

        mRecycleView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
