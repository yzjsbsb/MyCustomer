package cn.com.hxx.fakewaterfall.myView.expandview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import cn.com.hxx.fakewaterfall.R;
import cn.com.hxx.fakewaterfall.uti.httputil.HttpResult;
import cn.com.hxx.fakewaterfall.uti.httputil.ICallback;
import cn.com.hxx.fakewaterfall.uti.httputil.MyHttpUtils;
import cn.com.hxx.fakewaterfall.uti.httputil.data.CommodityData;

/**
 * Created by apple on 2018/6/28.
 */

public class ExpandFragment extends Fragment {


    private CommodityData body;
    private MyExpannableLayout myExpannableLayout;

    public static ExpandFragment getInstatnce(){
        ExpandFragment expandFragment = new ExpandFragment();
        return expandFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.expand_fragment, container, false);
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        getData();
    }

    private void initView() {
        myExpannableLayout  = getActivity().findViewById(R.id.ll_my);

    }


    private void getData() {
        MyHttpUtils.getMainWebService().getCommodityDetail(72922).enqueue(new ICallback<HttpResult<CommodityData>>() {
            @Override
            public void onSuccess(HttpResult<CommodityData> result) throws Exception {
                body = result.getBody();
                handlerData(body);
            }
        });
    }

    private void handlerData(CommodityData body) {
        String[] crowd_info_image = body.getCrowd_info_image();
        for (String url : crowd_info_image){
            ImageView imageView = new ImageView(getContext());
            Glide.with(this).load(url).into(imageView);
            myExpannableLayout.setView(imageView);
        }
        myExpannableLayout.setOnItemClickListener(new MyExpannableLayout.ItemClickListner() {
            @Override
            public void OnItemClick(View view, int position) {
                Toast.makeText(getContext(), position+"", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

