package com.example.zhangjingpractice.adapter;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zhangjingpractice.R;
import com.example.zhangjingpractice.bean.GoodsBean;
import com.example.zhangjingpractice.greendao.DataBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<DataBean> list;
    private Context context;
    private ObjectAnimator animator;

    public RecyclerViewAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public void setList(List<DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.goods_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
          viewHolder.textView_title.setText(list.get(i).getTitle());
          viewHolder.textView_price.setText("￥"+list.get(i).getPrice()+"");
        final String images = list.get(i).getImages();
        final String[] split = images.split("\\|");
        viewHolder.simpleDraweeView.setImageURI(split[0]);
//长按事件
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                if(longClickItemListener!=null){
                    //删除动画
                    animator = ObjectAnimator.ofFloat(v,"alpha",1.0f,0f);
                    animator.setDuration(3000);
                    longClickItemListener.getPosition(i);
                }
                return true;
            }
        });
        //点击事件
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemClickListener!=null){
                    itemClickListener.getInfo(list.get(i).getPid());
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
            simpleDraweeView=itemView.findViewById(R.id.simpleDrawee_view);
            textView_price=itemView.findViewById(R.id.textView_price);
            textView_title=itemView.findViewById(R.id.textView_title);
        }
    }
    //长按事件
    public onLongClickItemListener longClickItemListener;
    public void setOnLongClickItemListener(onLongClickItemListener itemListener){
        this.longClickItemListener=itemListener;
    }
    public interface onLongClickItemListener{
        void getPosition(int position);
    }
    //删除的方法
    public void delMethod(final int position){
        animator.start();
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                list.remove(position);
                notifyDataSetChanged();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }
    //点击事件

    public onItemClickListener itemClickListener;
    public void setOnItemClickListener(onItemClickListener clickListener){
        this.itemClickListener=clickListener;
    }
    public interface onItemClickListener{
        void getInfo(int position);
    }
}
