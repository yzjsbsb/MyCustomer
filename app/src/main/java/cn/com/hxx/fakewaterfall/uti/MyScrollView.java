package cn.com.hxx.fakewaterfall.uti;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;

import cn.com.hxx.fakewaterfall.R;
import cn.com.hxx.fakewaterfall.uti.httputil.EmptyUtils;
import cn.com.hxx.fakewaterfall.uti.httputil.MyUtils;
import cn.com.hxx.fakewaterfall.uti.httputil.StylesManager;
import cn.com.hxx.fakewaterfall.uti.httputil.data.CommodityData;
import cn.com.hxx.fakewaterfall.uti.httputil.data.StyleProductData;

/**
 * Created by apple on 2018/5/25.
 */

public class MyScrollView extends ScrollView implements View.OnTouchListener {


    private static RequestLoadMoreListener requestLoadMoreListener;

    /**
     * 每页要加载的图片数量
     */
    public static final int PAGE_SIZE = 10;

    /**
     * 记录当前已加载到第几页
     */
    private int page;

    /**
     * 每一列的宽度
     */
    private int columnWidth;

    /**
     * 当前第一列的高度
     */
    private int firstColumnHeight;

    /**
     * 当前第二列的高度
     */
    private int secondColumnHeight;

    /**
     * 当前第三列的高度
     */
    private int thirdColumnHeight;

    /**
     * 是否已加载过一次layout，这里onLayout中的初始化只需加载一次
     */
    private boolean loadOnce;

    /**
     * 对图片进行管理的工具类
     */
    private ImageLoader imageLoader;

    /**
     * 第一列的布局
     */
    private LinearLayout firstColumn;

    /**
     * 第二列的布局
     */
    private LinearLayout secondColumn;


    /**
     * 记录所有正在下载或等待下载的任务。
     */
//    private static Set<LoadImageTask> taskCollection;

    /**
     * MyScrollView下的直接子布局。
     */
    private static View scrollLayout;

    /**
     * MyScrollView布局的高度。
     */
    private static int scrollViewHeight;

    /**
     * 记录上垂直方向的滚动距离。
     */
    private static int lastScrollY = -1;

    /**
     * 记录所有界面上的图片，用以可以随时控制对图片的释放。
     */
    private List<View> imageViewList = new ArrayList<View>();

    /**
     * 在Handler中进行图片可见性检查的判断，以及加载更多图片的操作。
     */
    private static Handler handler = new Handler() {

        public void handleMessage(android.os.Message msg) {
            MyScrollView myScrollView = (MyScrollView) msg.obj;
            int scrollY = myScrollView.getScrollY();
            // 如果当前的滚动位置和上次相同，表示已停止滚动
            if (scrollY == lastScrollY) {
                // 当滚动的最底部，并且当前没有正在下载的任务时，开始加载下一页的图片
                if (scrollViewHeight + scrollY >= scrollLayout.getHeight()) {
                    requestLoadMoreListener.onLoadMoreRequested();
                }
                myScrollView.checkVisibility();
            } else {
                lastScrollY = scrollY;
                Message message = new Message();
                message.obj = myScrollView;
                // 5毫秒后再次对滚动位置进行判断
                handler.sendMessageDelayed(message, 5);
            }
        };

    };

    /**
     * MyScrollView的构造函数。
     *
     * @param context
     * @param attrs
     */
    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        imageLoader = ImageLoader.getInstance();
    //    taskCollection = new HashSet<LoadImageTask>();
        setOnTouchListener(this);
    }

    /**
     * 进行一些关键性的初始化操作，获取MyScrollView的高度，以及得到第一列的宽度值。并在这里开始加载第一页的图片。
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed && !loadOnce) {
            scrollViewHeight = getHeight();
            scrollLayout = getChildAt(0);
            firstColumn = (LinearLayout) findViewById(R.id.first_column);
            secondColumn = (LinearLayout) findViewById(R.id.second_column);
            columnWidth = firstColumn.getWidth();
            loadOnce = true;
     //       loadMoreImages();
        }
    }

    /**
     * 监听用户的触屏事件，如果用户手指离开屏幕则开始进行滚动检测。
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            Message message = new Message();
            message.obj = this;
            handler.sendMessageDelayed(message, 5);
        }
        return false;
    }

    /**
     * 开始加载下一页的图片，每张图片都会开启一个异步线程去下载。
     */
    public void loadMoreImages(List<CommodityData> body) {
        int num = body.size();
        if (num == PAGE_SIZE) {
            Toast.makeText(getContext(), "正在加载...", Toast.LENGTH_SHORT)
                    .show();

        } else {
            Toast.makeText(getContext(), "已没有更多图片", Toast.LENGTH_SHORT)
                    .show();
        }
        for (CommodityData commodityData : body) {
//                LoadImageTask task = new LoadImageTask();
//                taskCollection.add(task);
//                task.execute(commodityData);
            findColumnToAdd(commodityData);
        }
    }

    /**
     * 遍历imageViewList中的每张图片，对图片的可见性进行检查，如果图片已经离开屏幕可见范围，则将图片替换成一张空图。
     */
    public void checkVisibility() {
        for (int i = 0; i < imageViewList.size(); i++) {
            View view = imageViewList.get(i);
            int borderTop = (Integer) view.getTag(R.string.border_top);
            int borderBottom = (Integer) view
                    .getTag(R.string.border_bottom);
            ImageView imageView = view.findViewById(R.id.iv_cover);
            if (borderBottom > getScrollY()
                    && borderTop < getScrollY() + scrollViewHeight) {
                if (EmptyUtils.isEmpty(imageView.getDrawable())){
                    String imageUrl = (String) view.getTag(R.string.image_url);
                    Glide.with(getContext()).load(imageUrl).into(imageView);
                }
            } else {
                imageView.setImageDrawable(null);
            }
        }
    }

    /**
     * 判断手机是否有SD卡。
     *
     * @return 有SD卡返回true，没有返回false。
     */
    private boolean hasSDCard() {
        return Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState());
    }

    /**
     * 异步下载图片的任务。
     *
     * @author guolin
     */
//    class LoadImageTask extends AsyncTask<CommodityData, Void, String> {
//
//        /**
//         * 图片的URL地址
//         */
//        private String mImageUrl;
//
//        /**
//         * 可重复使用的ImageView
//         */
//        private ImageView mImageView;
//
//        public LoadImageTask() {
//        }
//
//        /**
//         * 将可重复使用的ImageView传入
//         *
//         * @param imageView
//         */
//        public LoadImageTask(ImageView imageView) {
//            mImageView = imageView;
//        }
//
//        @Override
//        protected String doInBackground(CommodityData params) {
//            mImageUrl = params.getImage();
//            Bitmap imageBitmap = imageLoader
//                    .getBitmapFromMemoryCache(mImageUrl);
//            if (imageBitmap == null) {
//                imageBitmap = loadImage(mImageUrl);
//            }
//            return mImageUrl;
//        }
//
//        @Override
//        protected void onPostExecute(String imageUrl) {
////            bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.test1);
////            if (bitmap != null) {
////                double ratio = bitmap.getWidth() / (columnWidth * 1.0);
////                int scaledHeight = (int) (bitmap.getHeight() / ratio);
////                addImage(bitmap, columnWidth, scaledHeight);
////            }
////            taskCollection.remove(this);
//
//
//        }
//
//        /**
//         * 根据传入的URL，对图片进行加载。如果这张图片已经存在于SD卡中，则直接从SD卡里读取，否则就从网络上下载。
//         *
//         * @param imageUrl
//         *            图片的URL地址
//         * @return 加载到内存的图片。
//         */
//        private Bitmap loadImage(String imageUrl) {
//            File imageFile = new File(getImagePath(imageUrl));
//            if (!imageFile.exists()) {
//                downloadImage(imageUrl);
//            }
//            if (imageUrl != null) {
//                Bitmap bitmap = ImageLoader.decodeSampledBitmapFromResource(
//                        imageFile.getPath(), columnWidth);
//                if (bitmap != null) {
//                    imageLoader.addBitmapToMemoryCache(imageUrl, bitmap);
//                    return bitmap;
//                }
//            }
//            return null;
//        }
//
//        /**
//         * 向ImageView中添加一张图片
//         *
//         * @param bitmap
//         *            待添加的图片
//         * @param imageWidth
//         *            图片的宽度
//         * @param imageHeight
//         *            图片的高度
//         */
////        private void addImage(Bitmap bitmap, int imageWidth, int imageHeight) {
////            imageHeight = imageHeight+ new Random().nextInt(73);
////            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
////                    imageWidth, imageHeight);
////            if (mImageView != null) {
////                mImageView.setImageBitmap(bitmap);
////            } else {
////                ImageView imageView = new ImageView(getContext());
////                imageView.setLayoutParams(params);
////                imageView.setImageBitmap(bitmap);
////                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
////                imageView.setPadding(5, 5, 5, 5);
////                imageView.setTag(R.string.image_url, mImageUrl);
////                findColumnToAdd(imageView, imageHeight);
////                imageViewList.add(imageView);
////            }
////        }
//
//
//
//
//        /**
//         * 找到此时应该添加图片的一列。原则就是对三列的高度进行判断，当前高度最小的一列就是应该添加的一列。
//         *
//         * @return 应该添加图片的一列
//         */
//
//
//        /**
//         * 将图片下载到SD卡缓存起来。
//         *
//         * @param imageUrl
//         *            图片的URL地址。
//         */
//        private void downloadImage(String imageUrl) {
//            if (Environment.getExternalStorageState().equals(
//                    Environment.MEDIA_MOUNTED)) {
//                Log.d("TAG", "monted sdcard");
//            } else {
//                Log.d("TAG", "has no sdcard");
//            }
//            HttpURLConnection con = null;
//            FileOutputStream fos = null;
//            BufferedOutputStream bos = null;
//            BufferedInputStream bis = null;
//            File imageFile = null;
//            try {
//                URL url = new URL(imageUrl);
//                con = (HttpURLConnection) url.openConnection();
//                con.setConnectTimeout(5 * 1000);
//                con.setReadTimeout(15 * 1000);
//                con.setDoInput(true);
//                con.setDoOutput(true);
//                bis = new BufferedInputStream(con.getInputStream());
//                imageFile = new File(getImagePath(imageUrl));
////                fos = new FileOutputStream(imageFile);
////                bos = new BufferedOutputStream(fos);
//                byte[] b = new byte[1024];
//                int length;
//                while ((length = bis.read(b)) != -1) {
////                    bos.write(b, 0, length);
////                    bos.flush();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                try {
//                    if (bis != null) {
//                        bis.close();
//                    }
//                    if (bos != null) {
//                     //   bos.close();
//                    }
//                    if (con != null) {
//                        con.disconnect();
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (imageFile != null) {
//                Bitmap bitmap = ImageLoader.decodeSampledBitmapFromResource(
//                        imageFile.getPath(), columnWidth);
//                if (bitmap != null) {
//                    imageLoader.addBitmapToMemoryCache(imageUrl, bitmap);
//                }
//            }
////            final String  imageUrlF = imageUrl;
////            ((Activity)getContext()).runOnUiThread(new Runnable() {
////                @Override
////                public void run() {
////                    Glide.with(getContext()).load(imageUrlF).asBitmap().into(new SimpleTarget<Bitmap>() {
////                        @Override
////                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
////                            imageLoader.addBitmapToMemoryCache(imageUrlF, resource);
////                        }
////                    }); //方法中设置asBitmap可以设置回调类型
////                }
////            });
//
//
//        }
//
//        /**
//         * 获取图片的本地存储路径。
//         *
//         * @param imageUrl
//         *            图片的URL地址。
//         * @return 图片的本地存储路径。
//         */
//        private String getImagePath(String imageUrl) {
//            int lastSlashIndex = imageUrl.lastIndexOf("/");
//            String imageName = imageUrl.substring(lastSlashIndex + 1);
//            String imageDir = Environment.getExternalStorageDirectory()
//                    .getPath() + "/PhotoWallFalls/";
//            File file = new File(imageDir);
//            if (!file.exists()) {
//                file.mkdirs();
//            }
//            String imagePath = imageDir + imageName;
//            return imagePath;
//        }
//    }


    private LinearLayout findColumnToAdd(final CommodityData commodityData) {

        firstColumn.measure(0,0);
        secondColumn.measure(0,0);


        if (firstColumn.getMeasuredHeight() <= secondColumn.getMeasuredHeight()) {
            View bigView = ((LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.big_item, firstColumn, false);

            bigView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), commodityData.getDesigner().getName(), Toast.LENGTH_SHORT).show();                }
            });

            ImageView imageView2 = bigView.findViewById(R.id.iv_designer_avatar);
            Glide.with(getContext()).load(commodityData.getDesigner().getAvatar()).into(imageView2);
            TextView textView = bigView.findViewById(R.id.tv_designer);
            textView.setText(commodityData.getDesigner().getName());
            TextView tv_slogan = bigView.findViewById(R.id.tv_slogan);
            tv_slogan.setText(commodityData.getDesigner().getSlogan());


            RelativeLayout ll_cover_bg = bigView.findViewById(R.id.ll_cover_bg);
            RelativeLayout ll_cover_bg1 = bigView.findViewById(R.id.ll_cover_bg1);
            ImageView iv_cover = bigView.findViewById(R.id.iv_cover);
            StyleProductData styleProductDataById = StylesManager.getInstance().getStyleProductDataById(commodityData.getStyle_color_id());
            MyUtils.drawTshitPic(ll_cover_bg, ll_cover_bg1, iv_cover, styleProductDataById, commodityData.getImage(), commodityData.getImage_to_show_in_list(), getContext());

            firstColumn.addView(bigView);

            bigView.setTag(R.string.border_top, firstColumn.getMeasuredHeight());
            imageViewList.add(bigView);
            bigView.setTag(R.string.border_bottom, firstColumn.getMeasuredHeight()+402);
            bigView.setTag(R.string.image_url, commodityData.getImage());

            return firstColumn;
        } else {

            View smallView = ((LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.small_item, firstColumn, false);


            smallView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), commodityData.getDesigner().getName(), Toast.LENGTH_SHORT).show();                }
            });


            ImageView imageView2 = smallView.findViewById(R.id.iv_designer_avatar);
            Glide.with(getContext()).load(commodityData.getDesigner().getAvatar()).into(imageView2);
            TextView textView = smallView.findViewById(R.id.tv_designer);
            textView.setText(commodityData.getDesigner().getName());
            TextView tv_slogan = smallView.findViewById(R.id.tv_slogan);
            tv_slogan.setText(commodityData.getDesigner().getSlogan());


            RelativeLayout ll_cover_bg = smallView.findViewById(R.id.ll_cover_bg);
            RelativeLayout ll_cover_bg1 = smallView.findViewById(R.id.ll_cover_bg1);
            ImageView iv_cover = smallView.findViewById(R.id.iv_cover);
            StyleProductData styleProductDataById = StylesManager.getInstance().getStyleProductDataById(commodityData.getStyle_color_id());
            MyUtils.drawTshitPic(ll_cover_bg, ll_cover_bg1, iv_cover, styleProductDataById, commodityData.getImage(), commodityData.getImage_to_show_in_list(), getContext());


            secondColumn.addView(smallView);
            smallView.setTag(R.string.border_top, secondColumn.getMeasuredHeight());
            smallView.setTag(R.string.border_bottom, secondColumn.getMeasuredHeight()+402);
            smallView.setTag(R.string.image_url, commodityData.getImage());
            imageViewList.add(smallView);
            return secondColumn;
        }
    }

    public interface RequestLoadMoreListener {

        void onLoadMoreRequested();

    }


    public void setOnLoadMoreListener(RequestLoadMoreListener requestLoadMoreListener) {
        this.requestLoadMoreListener = requestLoadMoreListener;
    }


    public interface itemClickListener{
        void onItemClick();
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
}
