package priv.lmh.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

/**
 * Created by HY on 2017/12/14.
 */

public class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    protected SparseArray<View> mViews;
    protected BaseAdapter.OnItemClickListener mOnItemClickListener;

    public BaseViewHolder(View itemView, BaseAdapter.OnItemClickListener onItemClickListener) {
        super(itemView);
        itemView.setOnClickListener(this);
        this.mViews = new SparseArray<>();
        this.mOnItemClickListener = onItemClickListener;
    }


    public View getView(int id){
        return findView(id);
    }

    public TextView getTextView(int id){
        return findView(id);
    }

    private <T extends View> T findView(int id){
        View view =  mViews.get(id);
        if(view == null){
            view = itemView.findViewById(id);
            mViews.put(id,view);
        }
        return (T) view;
    }

    @Override
    public void onClick(View v) {
        if(mOnItemClickListener != null){
            mOnItemClickListener.onItemClick(v,getLayoutPosition());
        }else {
            Log.d("error", "no ItemClickListener");
        }

    }

}
