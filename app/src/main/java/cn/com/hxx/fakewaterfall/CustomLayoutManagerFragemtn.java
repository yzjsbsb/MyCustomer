package cn.com.hxx.fakewaterfall;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import cn.com.hxx.fakewaterfall.myView.layoutmanager.CustomerLayoutManager;

/**
 * Created by apple on 2018/7/16.
 */

public class CustomLayoutManagerFragemtn extends Fragment {

    RecyclerView recycle_view;
    private FragmentActivity activity;
    private List<String> stringList = new ArrayList<>();

    public static CustomLayoutManagerFragemtn getInstance(){
        CustomLayoutManagerFragemtn customLayoutManagerFragemtn = new CustomLayoutManagerFragemtn();
        return customLayoutManagerFragemtn;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.customerlayoutmanager_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();

    }

    private void initView() {
        activity = getActivity();
        recycle_view = activity.findViewById(R.id.recycle_view);
        for (int i = 0 ; i < 30; i++){
            String string = "我是" + i + "项";
            stringList.add(string);
        }
        recycle_view.setLayoutManager(new CustomerLayoutManager(getContext()));
        MyAdapter myAdapter = new MyAdapter();
        myAdapter.addData(stringList);
        recycle_view.setAdapter(myAdapter);

        new LinearLayoutManager(getContext());
    }


    public class MyAdapter extends BaseQuickAdapter<String, BaseViewHolder>{

        public MyAdapter() {
            super(R.layout.customer_manager_item);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.tv_content, item);
        }
    }
}
