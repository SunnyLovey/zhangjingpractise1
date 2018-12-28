package com.example.day3retrofit.fragment;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.day3retrofit.R;
import com.example.day3retrofit.bean.MessageBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentDiscuss extends BaseFragment {
    @BindView(R.id.text_discuss_message)
    TextView textView_message;

    @Override
    protected int findViewId() {
        return R.layout.layout_discuss;
    }

    @Override
    protected void initData(View view) {
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        ButterKnife.bind(this,view);

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMessage(MessageBean messageBean){
        if(messageBean.getFlag()==1){
            Double price = (Double) messageBean.getObject();
            String s = String.valueOf(price);
            Toast.makeText(getActivity(),s,Toast.LENGTH_LONG).show();
            textView_message.setText(s);
        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
