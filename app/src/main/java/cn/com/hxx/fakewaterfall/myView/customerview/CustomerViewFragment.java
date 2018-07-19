package cn.com.hxx.fakewaterfall.myView.customerview;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.com.hxx.fakewaterfall.R;
import cn.com.hxx.fakewaterfall.myView.customerpopwin.CustomerPopWindow;
import cn.com.hxx.fakewaterfall.uti.httputil.MyUtils;

/**
 * Created by apple on 2018/6/28.
 */

public class CustomerViewFragment extends Fragment {

    private RelativeLayout rl_parent;
    private RelativeLayout rl_child;
    private Button button1;
    private ImageView iv_image;
    private ButtonCircleProgressBar circle_bar;
    private Handler handler = new Handler();
    private int progress = 0;
    private Button btn_pop1;
    private Button btn_pop2;
    private PopupWindow popupWindow1;
    private PopupWindow popupWindow2;

    private boolean isShowPop2 = false;
    private List<String> stringList = new ArrayList<>();

    private int pop1State;
    CustomerPopWindow customerPopWindow;

    public static CustomerViewFragment getInstatnce(){
        CustomerViewFragment customerViewFragment = new CustomerViewFragment();
        return customerViewFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.customer_fragment, container, false);
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rl_parent = getActivity().findViewById(R.id.rl_parent);
        rl_child = getActivity().findViewById(R.id.rl_child);
        button1 = getActivity().findViewById(R.id.btn1);
        iv_image = getActivity().findViewById(R.id.iv_image);
        circle_bar = getActivity().findViewById(R.id.circle_bar);
        circle_bar = getActivity().findViewById(R.id.circle_bar);
        btn_pop1 = getActivity().findViewById(R.id.btn_pop1);
        btn_pop2 = getActivity().findViewById(R.id.btn_pop2);
        circle_bar.setMax(100);

        initPop();

        initView();

    }

    private void initPop() {
        View popWin1 = LayoutInflater.from(getContext()).inflate(R.layout.pop_window_layout1, null);
        View popWin2 = LayoutInflater.from(getContext()).inflate(R.layout.pop_window_layout2, null);
        popupWindow1 = new PopupWindow(popWin1, 200, 200);
//        popupWindow1.setBackgroundDrawable(null);
//        popupWindow1.setOutsideTouchable(true);
//        popupWindow1.setFocusable(true);    //若想点击外部也消失，此3方法必须加入

        popupWindow2 = new PopupWindow(popWin2, ViewGroup.LayoutParams.MATCH_PARENT, 400);
        popupWindow2.setAnimationStyle(R.style.anim_menu_bottombar);
        RecyclerView recycle_view = popWin2.findViewById(R.id.recycle_view);
        for (int i = 0 ; i < 60; i++){
            String string = "我是" + i + "项";
            stringList.add(string);
        }
        recycle_view.setLayoutManager(new LinearLayoutManager(getContext()));
        MyAdapter myAdapter = new MyAdapter();
        recycle_view.setAdapter(myAdapter);

        customerPopWindow = new CustomerPopWindow.Builder(getContext())
                .setSize(200, 200)
                .setContentView(R.layout.pop_window_layout1)
                .create();
    }

    private void initView() {
        rl_child.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
           //     MyUtils.t(getContext(),"rl_child: touch");
                MyUtils.f("rl_child: touch:" + event.getAction());

                return false;
            }
        });
        rl_parent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //      MyUtils.t(getContext(),"rl_parent: touch");
                MyUtils.f("rl_parent: touch:"+ event.getAction());
                return false;
            }
        });
        button1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //       MyUtils.t(getContext(),"button1: touch");
                MyUtils.f("button1: touch:"+ event.getAction());
                return false;
            }
        });
        iv_image.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //       MyUtils.t(getContext(),"button2: touch");
                MyUtils.f("iv_image: touch:"+ event.getAction());

                return false;
            }
        });


        rl_child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //        MyUtils.t(getContext(),"rl_child: click");
                MyUtils.f("rl_child: click");
            }
        });
        rl_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //        MyUtils.t(getContext(),"rl_parent: click");
                MyUtils.f("rl_parent: click");
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //       MyUtils.t(getContext(),"button1: click");
                MyUtils.f("button1: click");
            }
        });
        iv_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //        MyUtils.t(getContext(),"button2: click");
                MyUtils.f("iv_image: click");
            }
        });

        circle_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtonCircleProgressBar.Status status = circle_bar.getStatus();
                if (status == ButtonCircleProgressBar.Status.End){
                    circle_bar.setStatus(ButtonCircleProgressBar.Status.Starting);
                    handler.post(runnable);
                }else {
                    circle_bar.setStatus(ButtonCircleProgressBar.Status.End);
                }
            }
        });

        btn_pop1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (customerPopWindow == null)return;
                switch (pop1State % 6){
                    case 0 :
                        customerPopWindow.showAsDropDown(btn_pop1, 0 ,0);
                        break;
                    case 1:
                        customerPopWindow.dismiss();
                        break;
                    case 2:
                        customerPopWindow.showAsDropDown(btn_pop1, 0, -btn_pop1.getHeight()*3);
                        break;
                    case 3:
                        customerPopWindow.dismiss();
                        break;
                    case 4:
                        customerPopWindow.showAtLocation(rl_child, Gravity.BOTTOM, 0 ,0);
                        break;
                    case 5:
                        customerPopWindow.dismiss();
                        break;
                }
                pop1State++;

            }
        });
        btn_pop2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow2 == null)return;
                if (isShowPop2){
                    popupWindow2.dismiss();
                }else {
                    popupWindow2.showAtLocation(rl_parent,Gravity.BOTTOM, 0, 0);
                }
                isShowPop2 = !isShowPop2;
            }
        });
    }

    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (progress == 100){
                progress = 0;
                handler.removeCallbacks(runnable);
                circle_bar.setStatus(ButtonCircleProgressBar.Status.End);
            }
            progress++;
            circle_bar.setProgress(progress);
            handler.postDelayed(runnable, 50);
        }
    };


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
