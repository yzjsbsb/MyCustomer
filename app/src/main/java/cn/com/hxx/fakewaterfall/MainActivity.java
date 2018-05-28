package cn.com.hxx.fakewaterfall;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.List;

import cn.com.hxx.fakewaterfall.uti.httputil.HttpResult;
import cn.com.hxx.fakewaterfall.uti.httputil.ICallback;
import cn.com.hxx.fakewaterfall.uti.httputil.MyConstantUtils;
import cn.com.hxx.fakewaterfall.uti.httputil.MyHttpUtils;
import cn.com.hxx.fakewaterfall.uti.httputil.data.CommodityData;

public class MainActivity extends AppCompatActivity {

    private int page = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyConstantUtils.init();
        getData();
    }



    public void getData() {

        //加载热门作品
        MyHttpUtils.getMainWebService().getHotDesigns(page, "like_count")
                .enqueue(new ICallback<HttpResult<List<CommodityData>>>() {
                    @Override
                    public void onSuccess(HttpResult<List<CommodityData>> result) throws Exception {
                        handlerResult(result);
                    }
                });
    }

    public void handlerResult(HttpResult<List<CommodityData>> result) {

    }

}
