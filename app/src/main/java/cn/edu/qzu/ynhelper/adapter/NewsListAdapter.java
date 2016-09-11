package cn.edu.qzu.ynhelper.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import cn.edu.qzu.ynhelper.R;
import cn.edu.qzu.ynhelper.entity.News;

/**
 * Created by Jaren on 2016/7/1.
 */
public class NewsListAdapter extends CustomAdapter {

    private List<News> data;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public NewsListAdapter(Context context,List<News> data) {
        super(context,data);
        this.context = context;
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == NORMAL_ITEM){
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_list,null);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener != null){
                        onItemClickListener.OnItemClick(v,(Integer)v.getTag());
                    }
                }
            });
            return new NewsViewHolder(itemView);
        }
        return super.onCreateViewHolder(parent,viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(isFooterItem(position)){
            super.onBindViewHolder(holder,position);
        }else {
            NewsViewHolder viewHolder = (NewsViewHolder) holder;
            News news = data.get(position);
            viewHolder.tvTittle.setText(news.getTitle());
            viewHolder.tvTime.setText(news.getTime());
            if (news.getImg() == null || news.getImg().isEmpty() || "null".equals(news.getImg())) {
                viewHolder.tvTittle.getLayoutParams().width = (int) context.getResources().getDimension(R.dimen.width_75_80);
                viewHolder.ivImg.setVisibility(View.INVISIBLE);
                //viewHolder.itemView.setMinimumHeight((int)context.getResources().getDimension(R.dimen.height_6_80));
            } else {
                viewHolder.tvTittle.getLayoutParams().width = (int) context.getResources().getDimension(R.dimen.width_52_80);
                viewHolder.ivImg.setVisibility(View.VISIBLE);
                Picasso.with(context).load(news.getImg()).into(viewHolder.ivImg);
            }

            viewHolder.itemView.setTag(position);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener = listener;
    }

    class NewsViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTittle,tvTime;
        private ImageView ivImg;

        public NewsViewHolder(View itemView) {
            super(itemView);
            tvTittle = (TextView) itemView.findViewById(R.id.tv_title);
            tvTime = (TextView) itemView.findViewById(R.id.tv_time);
            ivImg = (ImageView) itemView.findViewById(R.id.iv_img);
        }
    }

    public interface OnItemClickListener{
        void OnItemClick(View v,int position);
    }
}
