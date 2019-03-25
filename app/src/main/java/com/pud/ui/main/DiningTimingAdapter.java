package com.pud.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pud.R;
import com.pud.model.DiningTiming;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DiningTimingAdapter extends RecyclerView.Adapter<DiningTimingViewHolder> {

    private Context mContext;
    private List<DiningTiming> mList;

    public DiningTimingAdapter(Context context, List<DiningTiming> list) {
        mContext = context;
        mList = list;
    }

    @NonNull
    @Override
    public DiningTimingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.diningtiming_list_item, parent, false);
        return new DiningTimingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiningTimingViewHolder holder, int position) {
        holder.bind(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public List<DiningTiming> getList() {
        return mList;
    }

}