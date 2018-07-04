package cn.com.hxx.fakewaterfall;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import cn.com.hxx.fakewaterfall.myView.ButtonCircleProgressBar;
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
        circle_bar.setMax(100);
        initView();

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
}
