package priv.lmh.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.facebook.drawee.view.SimpleDraweeView;
/*import com.squareup.okhttp.Response;*/

import java.util.List;

import okhttp3.Response;
import priv.lmh.adapter.BaseAdapter;
import priv.lmh.adapter.BaseViewHolder;
import priv.lmh.adapter.CategoryAdapter;
import priv.lmh.adapter.CategoryWareAdapter;
import priv.lmh.bean.Banner;
import priv.lmh.bean.Category;
import priv.lmh.bean.HotWare;
import priv.lmh.bean.Page;
import priv.lmh.http.OkHttpHelper;
import priv.lmh.http.SpotsCallBack;
import priv.lmh.shop.Constants;
import priv.lmh.shop.R;
import priv.lmh.shop.WareDetailActivity;

/**
 * Created by HY on 2017/8/31.
 */

public class CategoryFragment extends Fragment {
    private RecyclerView mCategoryRecyclerView;
    private BaseAdapter mCateAdapter;
    private BaseAdapter mWareAdapter;
    private List<Category> mCategories;

    private SliderLayout mSliderLayout;
    private PagerIndicator mIndicator;

    private MaterialRefreshLayout mMaterialRefreshLayout;
    private RecyclerView mGoodsRecyclerView;

    private OkHttpHelper mOkHttpHelper;

    private SimpleDraweeView mImg_wares;
    private TextView mTextName;
    private TextView mTextPrice;

    private static final int STATE_NORMAL = 0;
    private static final int STATE_REFRESH = 1;
    private static final int STATE_MORE = 2;

    //默认值
    private int state = STATE_NORMAL;

    private int currentPage = 1;
    private int totalPage = 3;
    private int pageSize = 10;
    private long categoryId = 1;

    private static final String CATEGORY_LIST = "http://112.124.22.238:8081/course_api/category/list";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category,container,false);
        mOkHttpHelper = OkHttpHelper.getInstance();

        mCategoryRecyclerView =  view.findViewById(R.id.recycleview_category);

        mSliderLayout =  view.findViewById(R.id.slider_category);
        mIndicator =  view.findViewById(R.id.indicator_category);

        mMaterialRefreshLayout = view.findViewById(R.id.refreshlayout_category);
        mGoodsRecyclerView =  view.findViewById(R.id.recycleview_goods);

        requestCategoryData();

        requestCompaignData();

        initRefreshLayout();

        requestGoods(1);

        return view;
    }

    private void requestCategoryData(){
        mOkHttpHelper.get(CATEGORY_LIST, new SpotsCallBack<List<Category>>() {
            @Override
            public void onSuccess(Response response, List<Category> categoryList) {
                Log.d("succ",categoryList.size()+"");
                mCategories = categoryList;
                showCateList();
            }

            @Override
            public void onError(Response response, int code) {
                Log.d("error",code+"");
            }
        });

    }

    private void requestCompaignData(){
        mOkHttpHelper.get(Constants.API.BANNER+"?type=1", new SpotsCallBack<List<Banner>>() {
            @Override
            public void onSuccess(Response response, List<Banner> banners) {
                Log.d("banner size",""+banners.size());
                showCampaign(banners);
            }

            @Override
            public void onError(Response response, int code) {

            }
        });
    }

    private void requestGoods(long categoryId){
        this.categoryId = categoryId;
        mOkHttpHelper.get(Constants.API.WARES_LIST+"?categoryId="+categoryId+"&curPage="+currentPage+"&pageSize="+pageSize, new SpotsCallBack<Page<HotWare>>() {
            @Override
            public void onSuccess(Response response, Page<HotWare> hotWarePage) {
                List<HotWare> wares = hotWarePage.getList();
                if(wares != null && wares.size() != 0){
                    showGoods(wares);
                }

            }

            @Override
            public void onError(Response response, int code) {

            }
        });
    }

    //展示分类列表
    private void showCateList() {
        /*mCateAdapter = new BaseAdapter<Category>(getActivity(), mCategories,R.layout.template_category_catelist) {
            @Override
            public void bindData(BaseViewHolder holder, Category category) {
                Log.d("bind","bindData");
                holder.getTextView(R.id.text_catename).setText(category.getName());
            }

        };*/
        mCateAdapter = new CategoryAdapter(getContext(),mCategories,R.layout.template_category_catelist);

        mCateAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                currentPage = 1;
                state = STATE_NORMAL;

                Category category = (Category) mCateAdapter.getItem(position);
                long categoryId = category.getId();

                requestGoods(categoryId);

                Log.d("position",""+position);
            }
        });

        mCategoryRecyclerView.setAdapter(mCateAdapter);

        mCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mCategoryRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mCategoryRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        mCategoryRecyclerView.scrollToPosition(0);
    }

    //显示滑动图
    private void showCampaign(List<Banner> banners){
        if(banners != null){
            for(Banner banner: banners){
                TextSliderView sliderView = new TextSliderView(getActivity());
                sliderView.image(banner.getImgUrl());
                sliderView.description(banner.getName());
                sliderView.setScaleType(BaseSliderView.ScaleType.Fit);

                mSliderLayout.addSlider(sliderView);
            }
        }

        mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);

        mSliderLayout.setCustomAnimation(new DescriptionAnimation());
        mSliderLayout.setPresetTransformer(SliderLayout.Transformer.Default);
        mSliderLayout.setDuration(3000);
        mSliderLayout.setCustomIndicator(mIndicator);

    }

    private void showGoods(List<HotWare> wares){
        switch (state){
            case STATE_NORMAL:
                mWareAdapter = new CategoryWareAdapter(getContext(),wares,R.layout.template_category_wares);

                mWareAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        //显示商品详细信息
                        HotWare hotWare = (HotWare) mWareAdapter.getDatas().get(position);

                        Intent intent = new Intent(getActivity(),WareDetailActivity.class);
                        intent.putExtra("WARE",hotWare);

                        startActivity(intent);
                    }
                });

                mGoodsRecyclerView.setAdapter(mWareAdapter);
                mGoodsRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
                mGoodsRecyclerView.setItemAnimator(new DefaultItemAnimator());

                break;

            case STATE_REFRESH:
                mWareAdapter.clearData();

                mWareAdapter.addData(wares);

                mGoodsRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
                mGoodsRecyclerView.scrollToPosition(0);

                mMaterialRefreshLayout.finishRefresh();
                break;

            case STATE_MORE :
                int position = mWareAdapter.getDatas().size();
                mWareAdapter.addData(position,wares);

                mGoodsRecyclerView.scrollToPosition(position);
                mMaterialRefreshLayout.finishRefreshLoadMore();
                break;
        }
    }

    private void initRefreshLayout(){
        mMaterialRefreshLayout.setLoadMore(true);
        mMaterialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                //刷新数据，重头再来
                refreshData();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                if(currentPage < totalPage){
                    //加载更多数据
                    loadMore();
                }else {
                    mMaterialRefreshLayout.finishRefreshLoadMore();
                }
            }
        });
    }

    private void refreshData(){
        currentPage = 1;

        state = STATE_REFRESH;

        requestGoods(categoryId);
    }

    private void loadMore(){
        currentPage++;

        state = STATE_MORE;

        requestGoods(categoryId);
    }

}
