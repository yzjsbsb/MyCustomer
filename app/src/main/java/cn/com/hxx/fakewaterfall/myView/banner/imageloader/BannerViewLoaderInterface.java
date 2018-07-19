package cn.com.hxx.fakewaterfall.myView.banner.imageloader;

import android.content.Context;
import android.view.View;

/**
 * Created by apple on 2018/7/4.
 */

public interface BannerViewLoaderInterface {

    void loadView(Context context, View view, Object object);

    View createView(Context context);
}
