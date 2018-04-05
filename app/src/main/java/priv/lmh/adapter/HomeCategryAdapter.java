package priv.lmh.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import priv.lmh.bean.HomeCampaign;
import priv.lmh.bean.HomeCategory;
import priv.lmh.shop.R;

/**
 * Created by HY on 2017/12/9.
 */

public class HomeCategryAdapter extends RecyclerView.Adapter<HomeCategryAdapter.MyViewHolder>{
    private List<HomeCampaign> mData;
    private LayoutInflater mInflater;

    private Context mContext;

    private  static int VIEW_TYPE_L = 0;
    private  static int VIEW_TYPE_R = 1;

    public HomeCategryAdapter(Context context,List<HomeCampaign> mData){
        this.mData = mData;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mInflater = LayoutInflater.from(parent.getContext());

        if(viewType == VIEW_TYPE_L){
            return new MyViewHolder(mInflater.inflate(R.layout.template_home_cardview,parent,false));
        }
        return new MyViewHolder(mInflater.inflate(R.layout.template_home_cardview2,parent,false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        HomeCampaign campaign = mData.get(position);
        holder.textTitle.setText(campaign.getTitle());
       /* holder.imageViewBig.setImageResource(category.getImgBig());
        holder.imageViewSmallTop.setImageResource(category.getImgSmallTop());
        holder.imageViewSmallBottom.setImageResource(category.getImgSmallBottom());*/
        Picasso.with(mContext).load(campaign.getCpOne().getImgUrl()).into(holder.imageViewBig);
        Picasso.with(mContext).load(campaign.getCpTwo().getImgUrl()).into(holder.imageViewSmallTop);
        Picasso.with(mContext).load(campaign.getCpThree().getImgUrl()).into(holder.imageViewSmallBottom);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(position % 2 == 0){
            return VIEW_TYPE_L;
        }
        return VIEW_TYPE_R;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textTitle;
        ImageView imageViewBig;
        ImageView imageViewSmallTop;
        ImageView imageViewSmallBottom;

        public MyViewHolder(View itemView) {
            super(itemView);

            textTitle = (TextView) itemView.findViewById(R.id.text_title);
            imageViewBig = (ImageView) itemView.findViewById(R.id.imgview_big);
            imageViewSmallTop = (ImageView) itemView.findViewById(R.id.imgview_small_top);
            imageViewSmallBottom = (ImageView) itemView.findViewById(R.id.imgview_small_bottom);
        }
    }
}
