package priv.lmh.adapter;

import android.content.Context;

import java.util.List;

import priv.lmh.bean.Category;
import priv.lmh.shop.R;

/**
 * Created by HY on 2017/12/15.
 */

public class CategoryAdapter extends BaseAdapter<Category> {

    public CategoryAdapter(Context context, List<Category> data, int layoutResId) {
        super(context, data, layoutResId);
    }

    @Override
    public void bindData(BaseViewHolder holder, Category category) {
        holder.getTextView(R.id.text_catename).setText(category.getName());
    }
}
