package com.example.day3retrofit.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.day3retrofit.R;
import com.example.day3retrofit.activity.DetailActivity;
import com.example.day3retrofit.bean.DetailBean;
import com.example.day3retrofit.bean.MessageBean;
import com.example.day3retrofit.presenter.IPresenterImpl;
import com.example.day3retrofit.utils.Path;
import com.example.day3retrofit.view.IView;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FragementGoods extends BaseFragment implements IView{
    @BindView(R.id.image_goods)
    SimpleDraweeView simpleDraweeView;
    @BindView(R.id.text_goods_price)
    TextView textView_price;
    @BindView(R.id.text_goods_title)
    TextView textView_title;
    @BindView(R.id.send_price)
    Button button_send_price;
    @BindView(R.id.send_title)
    Button button_send_title;

    private IPresenterImpl iPresenter;
    private DetailBean.DataBean data1;

    @Override
    protected int findViewId() {
        return R.layout.layout_goods;
    }

    @Override
    protected void initData(View view) {
        ButterKnife.bind(this,view);
    //实例化IPresenterImpl
        iPresenter=new IPresenterImpl(this);
        //得到pid
        int positionData = ((DetailActivity) getActivity()).getPositionData();
        Map<String,String> params=new HashMap<>();
        params.put("pid",positionData+"");
        iPresenter.startRequest(Path.path_detail,params, DetailBean.class);
    }
    @OnClick({R.id.send_price,R.id.send_title})
    public void viewClick(View view){
        switch (view.getId()){
            case R.id.send_price:
                Toast.makeText(getActivity(),"111",Toast.LENGTH_LONG).show();
                EventBus.getDefault().post(new MessageBean(1,data1.getPrice()));
                break;
            case R.id.send_title:

                EventBus.getDefault().post(new MessageBean(2,data1.getTitle()));
                break;
        }
    }



    @Override
    public void requestSuccess(Object data) {
        DetailBean bean= (DetailBean) data;
        data1 = bean.getData();
        String images = data1.getImages();
        String[] split = images.split("\\|");
        simpleDraweeView.setImageURI(split[0]);
        textView_title.setText(data1.getTitle());
        textView_price.setText("￥"+ data1.getPrice()+"");
    }

    @Override
    public void requestFail(String e) {

    }


}
