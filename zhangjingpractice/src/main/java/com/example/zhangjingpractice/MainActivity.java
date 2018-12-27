package com.example.zhangjingpractice;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.zhangjingpractice.activity.DetailActivity;
import com.example.zhangjingpractice.adapter.RecyclerViewAdapter;
import com.example.zhangjingpractice.bean.GoodsBean;
import com.example.zhangjingpractice.bean.MessageBean;
import com.example.zhangjingpractice.greendao.DaoMaster;
import com.example.zhangjingpractice.greendao.DaoSession;
import com.example.zhangjingpractice.greendao.DataBean;
import com.example.zhangjingpractice.greendao.DataBeanDao;
import com.example.zhangjingpractice.utils.OkHttpUtils;
import com.example.zhangjingpractice.utils.Path;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
@BindView(R.id.recycler_view)
RecyclerView recyclerView;
private RecyclerViewAdapter adapter;
private static final int lineCount=2;
public static final String DB_NAME="user.db";
private DaoSession mDaoSession;
private DataBeanDao dataBeanDao;
    private List<DataBean> data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //注册
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
        //得到GridLayoutManager
        GridLayoutManager manager=new GridLayoutManager(this,lineCount);
        manager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(manager);

        //创建适配器
        adapter=new RecyclerViewAdapter(this);
        recyclerView.setAdapter(adapter);

        //删除
       adapter.setOnLongClickItemListener(new RecyclerViewAdapter.onLongClickItemListener() {
           @Override
           public void getPosition(int position) {
               adapter.delMethod(position);
           }
       });

       //点击商品列表项，跳转到详情页，要求动态显示商品详情数据
        adapter.setOnItemClickListener(new RecyclerViewAdapter.onItemClickListener() {
            @Override
            public void getInfo(int position) {
                Intent intent=new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("pid",position);
                startActivity(intent);
            }
        });

        //初始化GreenDao
        initGreenDao();
        //判断有无网络
        if(!OkHttpUtils.hasNetwork(MainActivity.this)){
            Toast.makeText(MainActivity.this,"当前网络不可用", Toast.LENGTH_LONG).show();
            //查询
            List<DataBean> list = dataBeanDao.queryBuilder().list();
            adapter.setList(list);
        }else {
            //网络请求
            Map<String,String> params=new HashMap<>();
            params.put("keywords","笔记本");
            params.put("page",1+"");
            OkHttpUtils.getInstance().postRequest(Path.path_goods,params,GoodsBean.class);

        }

    }

   private void initGreenDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, DB_NAME,null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        mDaoSession = daoMaster.newSession();
       dataBeanDao = mDaoSession.getDataBeanDao();

   }
   //添加
    private void add(){
       for (int i=0;i<data.size();i++){
           String title = data.get(i).getTitle();
           String images = data.get(i).getImages();
           double price = data.get(i).getPrice();
           int pid = data.get(i).getPid();
          DataBean dataBean=new DataBean((long) i,images,pid,price,title);
          dataBeanDao.insert(dataBean);
       }

    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getEventData(MessageBean messageBean){
      if(messageBean.getFlag()==1){
          GoodsBean bean= (GoodsBean) messageBean.getObject();
          data = bean.getData();
          adapter.setList(data);
          add();
      }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销注册
        EventBus.getDefault().unregister(this);
    }

}
