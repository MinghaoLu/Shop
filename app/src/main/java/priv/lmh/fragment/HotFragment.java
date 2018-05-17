package priv.lmh.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.facebook.drawee.view.SimpleDraweeView;
/*import com.squareup.okhttp.Response;*/


import java.io.Serializable;
import java.util.List;

import okhttp3.Response;
import priv.lmh.adapter.BaseAdapter;
import priv.lmh.adapter.BaseViewHolder;
import priv.lmh.adapter.HWAdapter;
import priv.lmh.adapter.HotWareAdapter;
import priv.lmh.bean.HotWare;
import priv.lmh.bean.Page;
import priv.lmh.http.OkHttpHelper;
import priv.lmh.http.SpotsCallBack;
import priv.lmh.shop.R;
import priv.lmh.shop.WareDetailActivity;

/**
 * Created by HY on 2017/8/31.
 */

public class HotFragment extends Fragment {
    private View mView;
    private MaterialRefreshLayout mMaterialRefreshLayout;
    private RecyclerView mRecyclerView;
    private BaseAdapter mAdapter;

    private OkHttpHelper mOkHttpHelper;

    private List<HotWare> hotWares;

    private static final String WARES_HOT = "http://112.124.22.238:8081/course_api/wares/hot";

    private static final int STATE_NORMAL = 0;
    private static final int STATE_REFRESH = 1;
    private static final int STATE_MORE = 2;

    private int state = STATE_NORMAL;

    private int currentPage = 1;
    private int totalPage = 3;
    private int pageSize = 10;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_hot,container,false);
        mMaterialRefreshLayout =  mView.findViewById(R.id.refreshlayout_hot);
        mRecyclerView =  mView.findViewById(R.id.recycleview_hot);

        mOkHttpHelper = OkHttpHelper.getInstance();

        if(savedInstanceState != null){
            currentPage = savedInstanceState.getInt("curPage",1);
            totalPage = savedInstanceState.getInt("total",3);
            state = savedInstanceState.getInt("state",STATE_NORMAL);

            mAdapter = (BaseAdapter) savedInstanceState.getSerializable("adapter");
        }

        initRefreshLayout();

        if(mAdapter == null){
            getData();
        }else {
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        }

        return mView;
    }

    private void initRefreshLayout() {
        mMaterialRefreshLayout.setLoadMore(true);
        mMaterialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            //下拉刷新
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                refreshData();
            }

            //上拉加载
            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout){
                if(currentPage <= totalPage){
                    loadMoreData();
                }else{
                    materialRefreshLayout.finishRefreshLoadMore();
                }
                //loadMoreData();
            }
        });
    }

    private void refreshData() {
        currentPage = 1;

        state = STATE_REFRESH;
        getData();
    }

    private void loadMoreData() {
        currentPage+=1;

        state = STATE_MORE;
        getData();
    }


    private void getData() {
        mOkHttpHelper.get(WARES_HOT+"?curPage="+currentPage+"&pageSize="+pageSize, new SpotsCallBack<Page<HotWare>>() {
            @Override
            public void onSuccess(Response response, Page<HotWare> hotWarePage) {
                hotWares = hotWarePage.getList();
                currentPage = hotWarePage.getCurrentPage();

                Log.d("hotWares",hotWares.size()+"");
                showData();
            }

            @Override
            public void onError(Response response, int code) {
                Log.d("error","onError" + code);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("curPage",currentPage);
        outState.putInt("totalPage",totalPage);
        outState.putInt("state",state);

        //outState.putSerializable("adapter", (Serializable) mAdapter);
    }

    private void showData() {
        switch (state){
            case STATE_NORMAL :
               //mAdapter = new HotWareAdapter(hotWares);
                if(mAdapter == null){
                    mAdapter = new HWAdapter(getActivity(),hotWares,R.layout.template_hot_wares);
                    /*mAdapter = new BaseAdapter<HotWare>(getContext(),hotWares,R.layout.template_hot_wares) {
                        @Override
                        public void bindData(BaseViewHolder holder, HotWare hotWare) {
                            SimpleDraweeView simpleDraweeView = (SimpleDraweeView) holder.getView(R.id.img_hot);
                            simpleDraweeView.setImageURI(Uri.parse(hotWare.getImgUrl()));

                            holder.getTextView(R.id.tv_name).setText(hotWare.getName());
                            holder.getTextView(R.id.tv_price).setText("￥"+hotWare.getPrice());
                        }

                    };*/
                }

                mAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        if(v.getId() != R.id.btn_buy){
                            HotWare hotWare = (HotWare) mAdapter.getDatas().get(position);

                            Intent intent = new Intent(getActivity(),WareDetailActivity.class);
                            intent.putExtra("WARE",hotWare);

                            startActivity(intent);
                        }
                    }
                });

                mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
                break;

            case STATE_REFRESH:
                mAdapter.clearData();
                mAdapter.addData(hotWares);

                //mRecyclerView.setAdapter(mAdapter);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
                mRecyclerView.scrollToPosition(0);

                mMaterialRefreshLayout.finishRefresh();
                break;
            case STATE_MORE:
                mAdapter.addData(mAdapter.getDatas().size(),hotWares);

                //mRecyclerView.setAdapter(mAdapter);
                //mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
                mRecyclerView.scrollToPosition(mAdapter.getDatas().size());
                mMaterialRefreshLayout.finishRefreshLoadMore();
                break;
            default:break;
        }



    }


}
