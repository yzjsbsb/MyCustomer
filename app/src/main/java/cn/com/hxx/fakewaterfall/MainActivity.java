package cn.com.hxx.fakewaterfall;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.List;

import cn.com.hxx.fakewaterfall.uti.MyScrollView;
import cn.com.hxx.fakewaterfall.uti.httputil.HttpResult;
import cn.com.hxx.fakewaterfall.uti.httputil.ICallback;
import cn.com.hxx.fakewaterfall.uti.httputil.MyConstantUtils;
import cn.com.hxx.fakewaterfall.uti.httputil.MyHttpUtils;
import cn.com.hxx.fakewaterfall.uti.httputil.MyUtils;
import cn.com.hxx.fakewaterfall.uti.httputil.StylesManager;
import cn.com.hxx.fakewaterfall.uti.httputil.data.CommodityData;
import cn.com.hxx.fakewaterfall.uti.httputil.data.StyleData;

public class MainActivity extends AppCompatActivity {

    private int page = 1;
    private MyScrollView my_scroll_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyConstantUtils.init();
        initView();
        getData();
    }

    private void initView() {
        my_scroll_view = findViewById(R.id.my_scroll_view);

    }


    public void getData() {

        MyHttpUtils.getMainWebService().getStyles().enqueue(new ICallback<HttpResult<List<StyleData>>>() {
            @Override
            public void onSuccess(HttpResult<List<StyleData>> result) throws Exception {
                StylesManager.getInstance().saveStylesData(result.getBody());
                //加载热门作品
                getHotData();

            }
        });
    }



    public void getHotData(){
        MyHttpUtils.getMainWebService().getHotDesigns(page, "like_count")
                .enqueue(new ICallback<HttpResult<List<CommodityData>>>() {
                    @Override
                    public void onSuccess(HttpResult<List<CommodityData>> result) throws Exception {
                        handlerResult(result);
                    }
                });
    }



    public void handlerResult(HttpResult<List<CommodityData>> result) {
        my_scroll_view.loadMoreImages(result.getBody());
    }
}
