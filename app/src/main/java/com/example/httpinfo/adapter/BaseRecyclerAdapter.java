package com.example.httpinfo.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseRecyclerHolder> {
    protected Context context;
    protected List<T> list;
    protected LayoutInflater inflater;
    protected int itemLayoutId;
    protected RecyclerView recyclerView;
    private static final int MIN_CLICK_DELAY_TIME = 2000;
    private long lastClickTime = 0;
    private int lastPosition=-1;

    public interface RecyclerViewClickListener {
        void OnItemClickListener(RecyclerView parent, View view, int position);

        void OnLongItemClickListener(RecyclerView parent, View view, int position);
    }

    private RecyclerViewClickListener recyclerViewClickListener;

    public void setRecyclerViewClickListener(RecyclerViewClickListener recyclerViewClickListener) {
        this.recyclerViewClickListener = recyclerViewClickListener;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.recyclerView = null;
    }

    public void insertData(T item, int position) {
        list.add(position, item);
        notifyItemInserted(position);
    }

    public void insertData(T item) {
        list.add(list.size(), item);
        notifyItemInserted(list.size());
    }


    public void deleteData(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void refreshData(List<T> lists) {
        list.clear();
        list.addAll(lists);
        notifyDataSetChanged();
    }

    public void cancelData() {
        list.clear();
        notifyDataSetChanged();
    }

    public void loadMoreData(List<T> lists) {
        list.addAll(lists);
        notifyDataSetChanged();
    }

    public T getItem(int postion) {
        if (list != null) {
            return list.get(postion);
        } else {
            return null;
        }
    }

    public BaseRecyclerAdapter(Context context, List<T> list, int itemLayoutId) {
        this.context = context;
        this.list = list;
        this.inflater = LayoutInflater.from(context);
        this.itemLayoutId = itemLayoutId;
    }

    public BaseRecyclerAdapter(Context context, int itemLayoutId) {
        this.context = context;
        this.list = new ArrayList<>();
        this.inflater = LayoutInflater.from(context);
        this.itemLayoutId = itemLayoutId;
    }

    @NonNull
    @Override
    public BaseRecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(itemLayoutId, parent, false);
        return BaseRecyclerHolder.getRecyclerHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseRecyclerHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position==lastPosition){
                    long currentTime = Calendar.getInstance().getTimeInMillis();
                    if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
                        lastClickTime = currentTime;
                        if (recyclerViewClickListener != null && v != null && recyclerView != null) {
                            int position = recyclerView.getChildAdapterPosition(v);
                            recyclerViewClickListener.OnItemClickListener(recyclerView, v, position);
                        }
                    }
                }else {
                    if (recyclerViewClickListener != null && v != null && recyclerView != null) {
                        int position = recyclerView.getChildAdapterPosition(v);
                        recyclerViewClickListener.OnItemClickListener(recyclerView, v, position);
                    }
                    lastPosition=position;
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // 返回true，长按监听只执行长按的代码 返回false 还会监听其他的监听的代码

                if (recyclerViewClickListener != null && v != null && recyclerView != null) {
                    int position = recyclerView.getChildAdapterPosition(v);
                    recyclerViewClickListener.OnLongItemClickListener(recyclerView, v, position);
                }
                return true;
            }
        });
        convert(holder, list.get(position), position);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseRecyclerHolder holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            Bundle bundle = (Bundle) payloads.get(0);
            payloads(holder, list.get(position), position, bundle);
        }
    }

    public abstract void convert(BaseRecyclerHolder baseRecyclerHolder, T item, int position);

    public void payloads(BaseRecyclerHolder baseRecyclerHolder, T item, int position, Bundle bundle) {

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }
}
