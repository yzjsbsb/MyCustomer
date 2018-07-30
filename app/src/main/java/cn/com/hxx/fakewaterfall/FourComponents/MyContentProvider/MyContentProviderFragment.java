package cn.com.hxx.fakewaterfall.FourComponents.MyContentProvider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import cn.com.hxx.fakewaterfall.R;
import cn.com.hxx.fakewaterfall.base.BaseFragment;

/**
 * Created by apple on 2018/7/30.
 */

public class MyContentProviderFragment extends BaseFragment {

    private final String TAG = "bookcontent";

    @Override
    public int getContentView() {
        return R.layout.fragment_mycontentprovider;
    }

    public static MyContentProviderFragment getInstance(){
        return new MyContentProviderFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        getView().findViewById(R.id.btn_insert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 一个URI由以下几个部分组成：
                 *Scheme：ContentProvider的Scheme被规定为“content://”
                 *Authority：用于唯一标识某个ContentProvider，外部调用者根据这个标识来指定要操作的ContentProvider,即manifest中的Authority。根据这个来确定是哪个ContentProvider
                 *Path：用来标识要操作的具体数据
                 */
                Uri uri = Uri.parse("content://com.hxx.fakewater.bookprovider/book");//book.db数据库
                ContentValues contentValues = new ContentValues();
                contentValues.put("bookName", "Effective Java");
                getContext().getContentResolver().insert(uri, contentValues);
                Cursor query = getContext().getContentResolver().query(uri, new String[]{"_id", "bookName"}, null, null, null, null);
                printInfo(query);
            }
        });

        getView().findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("content://com.hxx.fakewater.bookprovider/book");//book.db数据库
                getContext().getContentResolver().delete(uri, "_id = (select max(_id) from book)", null);
                Cursor query = getContext().getContentResolver().query(uri, new String[]{"_id", "bookName"}, null, null, null, null);
                printInfo(query);
            }
        });

        getView().findViewById(R.id.btn_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("content://com.hxx.fakewater.bookprovider/book");//book.db数据库
                ContentValues contentValues = new ContentValues();
                contentValues.put("bookName", "Android开发艺术");
                getContext().getContentResolver().update(uri, contentValues, "id = (select max(_id) from book)", null);
                Cursor query = getContext().getContentResolver().query(uri, new String[]{"_id", "bookName"}, null, null, null, null);
                printInfo(query);

            }
        });
    }

    private void printInfo(Cursor c){
        if (c != null){
            while (c.moveToNext()){
                Log.e(TAG, c.getColumnName(c.getColumnIndex("_id"))+":" + c.getInt(c.getColumnIndex("_id")) + c.getColumnName(c.getColumnIndex("bookName")) +" : " + c.getString(c.getColumnIndex("bookName")));
            }
            c.close();
        }
    }
}
