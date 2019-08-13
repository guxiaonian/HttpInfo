package com.example.httpinfo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class BaseRecyclerHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> viewSparseArray;
    private Context context;

    private BaseRecyclerHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
        viewSparseArray = new SparseArray<>(8);
    }

    /**
     * 拿到实体类
     *
     * @param itemView
     * @param context
     * @return
     */
    public static BaseRecyclerHolder getRecyclerHolder(View itemView, Context context) {
        return new BaseRecyclerHolder(itemView, context);
    }

    /**
     * 拿到所有的控件
     *
     * @return
     */
    public SparseArray<View> getViews() {
        return this.viewSparseArray;
    }

    /**
     * 拿到view的id来获取控件
     *
     * @param viewId
     * @param <T>
     * @return
     */
    public  <T extends View> T getView(int viewId) {
        View view = viewSparseArray.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            viewSparseArray.put(viewId, view);
        }
        return (T) view;
    }

    public BaseRecyclerHolder setText(int viewId, String text) {
        TextView textView = getView(viewId);
        if (text == null) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setVisibility(View.VISIBLE);
            textView.setText(text);
        }
        return this;
    }

    public BaseRecyclerHolder setVisibility(int viewId, int visible) {
        View view = getView(viewId);
        view.setVisibility(visible);
        return this;
    }


    public BaseRecyclerHolder setImageViewByResource(int viewId, int drawableId) {
        ImageView imageView = getView(viewId);
        imageView.setImageResource(drawableId);
        return this;
    }
}
