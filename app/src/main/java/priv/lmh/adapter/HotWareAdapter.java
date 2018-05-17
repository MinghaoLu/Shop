package priv.lmh.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.net.URI;
import java.util.List;

import priv.lmh.bean.HotWare;
import priv.lmh.shop.R;

/**
 * Created by HY on 2017/12/12.
 */

public class HotWareAdapter extends  RecyclerView.Adapter<HotWareAdapter.MyViewHolder>{
    private LayoutInflater mInflater;
    private View mView;

    private List<HotWare> mData;

    public HotWareAdapter(List<HotWare> mData) {
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mInflater = LayoutInflater.from(parent.getContext());
        mView = mInflater.inflate(R.layout.template_hot_wares,parent,false);
        return new MyViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        HotWare hotWare = getData(position);
        Log.d("uri",hotWare.getImgUrl());
        holder.simpleDraweeView.setImageURI(Uri.parse(hotWare.getImgUrl()));
        holder.text_name.setText(hotWare.getName());
        holder.text_price.setText("￥"+ hotWare.getPrice());
    }

    @Override
    public int getItemCount() {
        if(mData != null && mData.size()>0){
            return mData.size();
        }
        return 0;
    }


    public  List<HotWare> getDatas(){
        return mData;

    }

    private HotWare getData(int position){
        return mData.get(position);
    }

    public void clearData(){
        mData.clear();
        notifyItemRangeChanged(0,mData.size());
    }

    public void addData(List<HotWare> data){
        addData(0,data);
    }

    public void addData(int position,List<HotWare> data){
        if(data != null && data.size()>0){
            mData.addAll(data);
            notifyItemRangeChanged(position,mData.size());
        }
    }

    public class MyViewHolder extends ViewHolder {
        public SimpleDraweeView simpleDraweeView;
        public TextView text_name;
        public TextView text_price;
        public Button btn_buy;

        public MyViewHolder(View itemView) {
            super(itemView);

            simpleDraweeView = mView.findViewById(R.id.img_hot);
            text_name =  mView.findViewById(R.id.tv_name);
            text_price = mView.findViewById(R.id.tv_price);
            btn_buy = mView.findViewById(R.id.btn_buy);
        }
    }
}
