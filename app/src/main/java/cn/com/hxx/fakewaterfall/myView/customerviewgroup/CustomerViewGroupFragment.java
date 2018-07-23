package cn.com.hxx.fakewaterfall.myView.customerviewgroup;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Random;

import cn.com.hxx.fakewaterfall.R;
import cn.com.hxx.fakewaterfall.uti.MyUtils;

/**
 * Created by apple on 2018/7/13.
 */

public class CustomerViewGroupFragment extends Fragment {

    CustomerViewGroup customerviewgroup;

    private String tag;

    public static CustomerViewGroupFragment getInstance(){
        CustomerViewGroupFragment customerViewGroupFragment = new CustomerViewGroupFragment();
        return customerViewGroupFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cutomerviewgroup_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        customerviewgroup = getActivity().findViewById(R.id.customerviewgroup);
        for(int i = 0; i < 500 ; i++){
            tag = "我是标签：0"+i;
            TextView textView = new TextView(getContext());
            int color = i % 2 == 0 ? Color.CYAN : Color.YELLOW ;
            Random random = new Random();
            int fontSize = random.nextInt(30) +10;
            textView.setTextSize( fontSize );
            textView.setBackgroundColor(color);
            textView.setText(tag);
            customerviewgroup.addView(textView);
        }
        int height = customerviewgroup.getHeight();
        MyUtils.t(getContext(), height+"");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
