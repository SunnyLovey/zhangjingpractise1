package com.example.day3retrofit.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.day3retrofit.R;
import com.example.day3retrofit.bean.GoodsBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerGridAdapter extends RecyclerView.Adapter<RecyclerGridAdapter.ViewHolder> {
    private List<GoodsBean.DataBean> list;
    private Context context;

    public RecyclerGridAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public void setList(List<GoodsBean.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_grid_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.textView_title.setText(list.get(i).getTitle());
        viewHolder.textView_price.setText("￥"+list.get(i).getPrice()+"");
        String images = list.get(i).getImages();
        String[] split = images.split("\\|");
        viewHolder.simpleDraweeView.setImageURI(split[0]);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickItemListener!=null){
                    clickItemListener.getPosition(list.get(i).getPid());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView simpleDraweeView;
        TextView textView_title,textView_price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            simpleDraweeView=itemView.findViewById(R.id.image_grid);
            textView_price=itemView.findViewById(R.id.textView_price_grid);
            textView_title=itemView.findViewById(R.id.textView_title_grid);
        }
    }
    //点击事件
    public onClickItemListener clickItemListener;
    public void setOnClickItemListener(onClickItemListener itemListener){
        this.clickItemListener=itemListener;
    }
    public interface onClickItemListener{
        void getPosition(int position);
    }
}
