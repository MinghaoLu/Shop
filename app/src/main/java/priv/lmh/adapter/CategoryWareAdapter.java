package priv.lmh.adapter;

import android.content.Context;
import android.net.Uri;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import priv.lmh.bean.HotWare;
import priv.lmh.shop.R;

/**
 * Created by HY on 2017/12/15.
 */

public class CategoryWareAdapter extends BaseAdapter<HotWare> {
    public CategoryWareAdapter(Context context, List<HotWare> data, int layoutResId) {
        super(context, data, layoutResId);
    }

    @Override
    public void bindData(BaseViewHolder holder, HotWare hotWare) {
        SimpleDraweeView simpleDraweeView = (SimpleDraweeView) holder.getView(R.id.img_ware);
        simpleDraweeView.setImageURI(Uri.parse(hotWare.getImgUrl()));

        holder.getTextView(R.id.text_name).setText(hotWare.getName());
        holder.getTextView(R.id.text_price).setText("￥"+ hotWare.getPrice());
    }
}
