package com.yocn.dumpanalysis.view;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yocn.dumpanalysis.util.ColorUtil;
import com.yocn.dumpanalysis.R;
import com.yocn.dumpanalysis.bean.MainBean;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private List<MainBean> list;
    private Context context;
    static class ViewHolder extends RecyclerView.ViewHolder {
        View root;
        TextView name;

        public ViewHolder(View view) {
            super(view);
            root = view;
            name = (TextView) view.findViewById(R.id.name);
        }
    }

    public MainAdapter(Context context, List<MainBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MainBean bean = list.get(position);
        holder.name.setText(bean.getName());
        holder.name.setTextColor(context.getResources().getColor(ColorUtil.textColor[position % ColorUtil.textColor.length]));
        holder.root.setBackgroundResource(ColorUtil.colors[position% ColorUtil.colors.length]);
        holder.name.setOnClickListener(v -> context.startActivity(new Intent(context, bean.getTarget())));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}