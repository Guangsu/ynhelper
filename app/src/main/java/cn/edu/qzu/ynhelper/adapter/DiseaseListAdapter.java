package cn.edu.qzu.ynhelper.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.edu.qzu.ynhelper.DiseaseDetailActivity;
import cn.edu.qzu.ynhelper.R;
import cn.edu.qzu.ynhelper.entity.Disease;

/**
 * Created by Jaren on 2016/7/4.
 */
public class DiseaseListAdapter extends CustomAdapter {

    private Context context;
    private List<Disease> data;

    public DiseaseListAdapter(Context context, List<Disease> data) {
        super(context, data);
        this.context = context;
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == NORMAL_ITEM){
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_disease_list,null);
            return new DiseaseViewHolder(itemView);
        }
       return super.onCreateViewHolder(parent,viewType);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if(isFooterItem(position)){
            super.onBindViewHolder(holder,position);
        } else  {
            DiseaseViewHolder viewHolder = (DiseaseViewHolder) holder;
            Disease disease = data.get(position);
            viewHolder.tvName.setText(disease.getName());
            viewHolder.tvAbs.setText(disease.getSummary().isEmpty()?"暂无简介":disease.getSummary());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DiseaseDetailActivity.class);
                    intent.putExtra("code", data.get(position).getCode());
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class DiseaseViewHolder extends RecyclerView.ViewHolder{

        private TextView tvName,tvAbs;

        public DiseaseViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvAbs  = (TextView) itemView.findViewById(R.id.tv_abs);
        }
    }
}
