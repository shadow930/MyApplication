package com.example.myapplication;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

public class PagerSnapHelperAdapter extends RecyclerView.Adapter<PagerSnapHelperAdapter.ViewHolder> {

    // 数据集
    private int[] mDataList;


    public PagerSnapHelperAdapter(int[] dataset) {
        super();
        this.mDataList = dataset;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Log.e("xiaxl: ", "---onCreateViewHolder---");
        // 创建一个View，简单起见直接使用系统提供的布局，就是一个TextView
        View view = View.inflate(viewGroup.getContext(), R.layout.recycler_item, null);
        // 创建一个ViewHolder
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Log.e("xiaxl: ", "---onBindViewHolder---");

        // 绑定数据到ViewHolder上
        viewHolder.itemView.setTag(position);
        //
        viewHolder.imageView.setImageResource(mDataList[position]);
    }

    @Override
    public int getItemCount() {
        return mDataList.length;
    }

    /**
     *
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);
        }
    }
}