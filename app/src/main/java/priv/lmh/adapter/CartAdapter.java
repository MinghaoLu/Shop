package priv.lmh.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import priv.lmh.bean.ShoppingCart;
import priv.lmh.shop.R;
import priv.lmh.shop.ShopApplication;
import priv.lmh.util.CartProvider;
import priv.lmh.util.NetCartProvider;
import priv.lmh.util.Provider;
import priv.lmh.widget.NumAddSubView;

/**
 * Created by HY on 2017/12/19.
 */

public class CartAdapter extends BaseAdapter<ShoppingCart> implements BaseAdapter.OnItemClickListener,View.OnClickListener{
    private Provider mProvider;

    private CheckBox mCheckBox;
    private TextView mTextTotal;

    public CartAdapter(Context context, List<ShoppingCart> data, int layoutResId,CheckBox checkBox,TextView textView,Provider provider) {
        super(context, data, layoutResId);

        mProvider = provider;
        mTextTotal = textView;
        mCheckBox = checkBox;

        mCheckBox.setOnClickListener(this);
        setOnItemClickListener(this);
    }

    @Override
    public void bindData(BaseViewHolder holder, final ShoppingCart cart) {
        final int position = holder.getLayoutPosition();
        CheckBox checkBox = (CheckBox) holder.getView(R.id.check_box);
        checkBox.setChecked(cart.isChecked());

        SimpleDraweeView simpleDraweeView = (SimpleDraweeView) holder.getView(R.id.drawee_view);
        simpleDraweeView.setImageURI(Uri.parse(cart.getImgUrl()));

        holder.getTextView(R.id.text_name).setText(cart.getName());
        holder.getTextView(R.id.text_price).setText("￥"+cart.getPrice());

        final NumAddSubView numAddSubView = (NumAddSubView) holder.getView(R.id.num_control);
        numAddSubView.setValue(cart.getCount());

        //加减按钮点击事件
        numAddSubView.setOnButtonClickLister(new NumAddSubView.OnButtonClickListener() {
            @Override
            public void onButtonClick(View view, int value) {
                if(view.getId() == R.id.btn_add){
                    ++value;
                    numAddSubView.setValue(value);
                    mProvider.update(cart,numAddSubView.getValue());
                    cart.setCount(numAddSubView.getValue());
                    showTotalPrice();

                }else if(view.getId() == R.id.btn_sub) {
                    if(value == 1){
                        //将商品清除出商品列表（将数据清除出ProVider中）
                        deleteWare(cart,position);

                        //mOnItemClickListener.onItemClick(view,position);
                    }else {
                        --value;
                        numAddSubView.setValue(value);
                        mProvider.update(cart,numAddSubView.getValue());
                        cart.setCount(numAddSubView.getValue());
                        showTotalPrice();
                    }

                }

            }
        });

    }

    public boolean isNull(){
        return (mData == null & mData.size() == 0);
    }

    private void deleteWare(ShoppingCart cart,int position) {
        //从数据库中移除
        mProvider.delete(cart);

        notifyRecyclerView(position);

        showTotalPrice();

    }

    private void notifyRecyclerView(int position) {
        //从源数据中移除
        mData.remove(position);

        notifyDataSetChanged();
        /*notifyItemRemoved(position);

        //通知此position后面的item发生变化了，即重新执行onBindViewHolder
        notifyItemRangeChanged(position, mData.size() - position);*/
    }

    //编辑状态
    @Override
    protected void inEdittingState() {
        state = EDITTING;
        if(mData == null || mData.size() == 0){
            return;
        }
        for(ShoppingCart cart : mData){
            cart.setChecked(false);
        }
        notifyDataSetChanged();
    }

    //完成状态
    @Override
    protected void inFinishedState() {
        state = FINISHED;
        if(mData == null || mData.size() == 0){
            return;
        }
        for(ShoppingCart cart : mData){
            cart.setChecked(true);
        }
        notifyDataSetChanged();
        showTotalPrice();
    }

    @Override
    public void onItemClick(View v, int position) {
            ShoppingCart cart = mData.get(position);
            cart.setChecked(!cart.isChecked());
            notifyDataSetChanged();

            int sum = 0;
            for(ShoppingCart item : mData){
                if(item.isChecked() == true){
                    sum+=1;
                }
            }
            if(sum == mData.size()){
                mCheckBox.setChecked(true);
            }else{
                mCheckBox.setChecked(false);
            }

            showTotalPrice();
    }

    @Override
    public void onClick(View v) {
        for(ShoppingCart cart : mData){
            cart.setChecked(mCheckBox.isChecked());
        }
        notifyDataSetChanged();
        showTotalPrice();
    }

    public void showTotalPrice(){
        mTextTotal.setText(getTotalPrice()+"");
    }

    protected float getTotalPrice(){
        int sum = 0;
        if(isNull()){
            return sum;
        }
        for(ShoppingCart cart : mData){
            if(cart.isChecked())
                sum+=cart.getPrice()*cart.getCount();
        }
        return sum;
    }
}
