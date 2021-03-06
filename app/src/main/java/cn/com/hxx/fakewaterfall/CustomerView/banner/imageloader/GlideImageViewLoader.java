package cn.com.hxx.fakewaterfall.CustomerView.banner.imageloader;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by apple on 2018/7/4.
 */

public class GlideImageViewLoader implements BannerViewLoaderInterface {
    @Override
    public void loadView(Context context, View view, Object object) {
        Glide.with(context)
                .load((String)object)
                .into((ImageView)view);
    }

    @Override
    public View createView(Context context) {
        return new ImageView(context);
    }
}
