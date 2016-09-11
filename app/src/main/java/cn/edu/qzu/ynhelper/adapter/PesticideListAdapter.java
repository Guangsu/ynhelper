package cn.edu.qzu.ynhelper.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.edu.qzu.ynhelper.PesticideDetailActivity;
import cn.edu.qzu.ynhelper.R;
import cn.edu.qzu.ynhelper.entity.Pesticide;

/**
 * Created by Jaren on 2016/7/4.
 */
public class PesticideListAdapter extends CustomAdapter {

    private Context context;
    private List<Pesticide> data;

    public PesticideListAdapter(Context context, List<Pesticide> data) {
        super(context,data);
        this.context = context;
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == NORMAL_ITEM){
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pesticide_list,null);
            return new PesticideViewHolder(itemView);
        }
        return super.onCreateViewHolder(parent,viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(isFooterItem(position)){
            super.onBindViewHolder(holder,position);
        }else {
            PesticideViewHolder viewHolder = (PesticideViewHolder) holder;
            Pesticide pesticide = data.get(position);
            viewHolder.tvName.setText(pesticide.getName());
            viewHolder.tvCompany.setText(pesticide.getCompany());
            viewHolder.tvCategory.setText(pesticide.getCategory()== null?"暂无分类":pesticide.getCategory());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PesticideDetailActivity.class);
                    intent.putExtra("id",data.get(position).getId());
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class PesticideViewHolder extends RecyclerView.ViewHolder{

        private TextView tvName,tvCompany,tvCategory;

        public PesticideViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvCompany  = (TextView) itemView.findViewById(R.id.tv_company);
            tvCategory = (TextView) itemView.findViewById(R.id.tv_category);
        }
    }
}