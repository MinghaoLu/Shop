package priv.lmh.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by HY on 2017/12/14.
 */

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder>{
    protected List<T> mData;

    protected Context mContext;
    protected View mView;

    protected LayoutInflater mLayoutInflater;
    protected int mLayoutResId;

    protected OnItemClickListener mOnItemClickListener = null;

    protected int state = FINISHED;

    public static final int FINISHED = 0;
    public static final int EDITTING = 1;

    public BaseAdapter(Context context,List<T> data,int layoutResId) {
        this.mContext = context;
        this.mData = data;
        this.mLayoutResId = layoutResId;

        mLayoutInflater = LayoutInflater.from(context);


    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mView = mLayoutInflater.inflate(mLayoutResId,parent,false);
        return new BaseViewHolder(mView,mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        T t = mData.get(position);
        bindData(holder,t);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        if(mData == null){
            return 0;
        }
        return mData.size();
    }

    public abstract void bindData(BaseViewHolder holder,T t);

    public List<T> getDatas(){
        return mData;
    }

    public T getItem(int position){
        return mData.get(position);
    }

    public void clearData(){
        mData.clear();
        notifyItemRangeChanged(0,mData.size());
    }

    public interface OnItemClickListener{
        void onItemClick(View v,int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }


    public void addData(List<T> data){
        addData(0,data);
    }

    public void addData(int position,List<T> data){
        if(data != null){
            mData.addAll(data);
            notifyItemRangeChanged(position,mData.size()-position);
        }
    }

    protected void inEdittingState(){

    }

    protected void inFinishedState(){

    }

    public void setState(int state){
        if(state == EDITTING){
            inEdittingState();
        }else {
            inFinishedState();
        }
    }
}
