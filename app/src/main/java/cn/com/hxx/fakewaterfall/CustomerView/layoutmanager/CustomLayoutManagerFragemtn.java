package cn.com.hxx.fakewaterfall.CustomerView.layoutmanager;

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

import java.util.ArrayList;
import java.util.List;

import cn.com.hxx.fakewaterfall.R;

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
        for (int i = 0 ; i < 60; i++){
            String string = "我是" + i + "项";
            stringList.add(string);
        }
        recycle_view.setLayoutManager(new CustomerLayoutManager(getContext()));
        MyAdapter myAdapter = new MyAdapter();
        recycle_view.setAdapter(myAdapter);

        new LinearLayoutManager(getContext());
    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflate = LayoutInflater.from(getContext()).inflate(R.layout.customer_manager_item, parent, false);
            MyViewHolder myViewHolder = new MyViewHolder(inflate);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            String str = stringList.get(position);
            holder.setContent(str);
        }

        @Override
        public int getItemCount() {
            return stringList.size();
        }
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_content;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_content = itemView.findViewById(R.id.tv_content);
        }

        public void setContent(String str){
            tv_content.setText(str);
        }
    }
}
