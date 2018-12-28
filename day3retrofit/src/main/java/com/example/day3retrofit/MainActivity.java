package com.example.day3retrofit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.day3retrofit.activity.DetailActivity;
import com.example.day3retrofit.adapter.RecyclerGridAdapter;
import com.example.day3retrofit.adapter.RecyclerListAdapter;
import com.example.day3retrofit.bean.GoodsBean;
import com.example.day3retrofit.presenter.IPresenterImpl;
import com.example.day3retrofit.utils.Path;
import com.example.day3retrofit.view.IView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements IView{
@BindView(R.id.image_search)
ImageView imageView_search;
@BindView(R.id.image_change)
ImageView imageView_change;
@BindView(R.id.recycler_list)
RecyclerView recyclerView_list;
@BindView(R.id.edit_name)
EditText editText_name;
    @BindView(R.id.recycler_grid)
    RecyclerView recyclerView_grid;


private IPresenterImpl iPresenter;
private RecyclerListAdapter listAdapter;
private final int lineCounts=2;
private RecyclerGridAdapter gridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //实例化IPresenterImpl
        iPresenter=new IPresenterImpl(this);

        //得到LinearLayoutManager管理者
        LinearLayoutManager manager=new LinearLayoutManager(this);
        manager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView_list.setLayoutManager(manager);
        //创建适配器
        listAdapter=new RecyclerListAdapter(this);
        recyclerView_list.setAdapter(listAdapter);

        //跳转
        listAdapter.setOnClickItemListener(new RecyclerListAdapter.onClickItemListener() {
            @Override
            public void getPosition(int position) {
                Intent intent=new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("pid",position);
                startActivity(intent);
            }
        });
       //得到GridLayoutManager管理者
        GridLayoutManager manager1=new GridLayoutManager(this,lineCounts);
        manager1.setOrientation(OrientationHelper.VERTICAL);
        recyclerView_grid.setLayoutManager(manager1);
        //创建适配器
        gridAdapter=new RecyclerGridAdapter(this);
        recyclerView_grid.setAdapter(gridAdapter);

        gridAdapter.setOnClickItemListener(new RecyclerGridAdapter.onClickItemListener() {
            @Override
            public void getPosition(int position) {
                Intent intent=new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("pid",position);
                startActivity(intent);
            }
        });



    }
   @OnClick({R.id.image_search,R.id.image_change})
    public void viewClick(View view){
        switch (view.getId()){
            case R.id.image_search:
                String name = editText_name.getText().toString();
                Map<String,String> params=new HashMap<>();
                params.put("keywords",name);
                params.put("page",1+"");
                iPresenter.startRequest(Path.path_goods,params, GoodsBean.class);
                break;
            case R.id.image_change:
                if(recyclerView_list.getVisibility()==View.VISIBLE){
                    recyclerView_list.setVisibility(View.INVISIBLE);
                    recyclerView_grid.setVisibility(View.VISIBLE);
                }else {
                    recyclerView_list.setVisibility(View.VISIBLE);
                    recyclerView_grid.setVisibility(View.INVISIBLE);
                }
                break;
        }
    }

    @Override
    public void requestSuccess(Object data) {
          GoodsBean bean= (GoodsBean) data;
        List<GoodsBean.DataBean> data1 = bean.getData();
        listAdapter.setList(data1);
        gridAdapter.setList(data1);
    }

    @Override
    public void requestFail(String e) {
        Toast.makeText(MainActivity.this,e,Toast.LENGTH_LONG).show();
    }
}
