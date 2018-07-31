package cn.com.hxx.fakewaterfall.FourComponents.MyContentProvider;

import android.Manifest;
import android.app.Application;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import cn.com.hxx.fakewaterfall.MyApplication;
import cn.com.hxx.fakewaterfall.R;
import cn.com.hxx.fakewaterfall.base.BaseFragment;

/**
 * Created by apple on 2018/7/30.
 */

public class MyContentProviderFragment extends BaseFragment {

    private final String TAG = "bookcontent";
    private final int CODE_FOR_READ_CONTACT = 0x01;

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
                getContext().getContentResolver().insert(uri, contentValues);       //会调用BookProvier的insert方法
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
                getContext().getContentResolver().update(uri, contentValues, "_id = (select max(_id) from book)", null);
                Cursor query = getContext().getContentResolver().query(uri, new String[]{"_id", "bookName"}, null, null, null, null);
                printInfo(query);

            }
        });

        getView().findViewById(R.id.btn_query).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("content://com.hxx.fakewater.bookprovider/book");//book.db数据库
                Cursor query = getContext().getContentResolver().query(uri, new String[]{"_id", "bookName"}, null, null, null, null);
                printInfo(query);
            }
        });

        //访问通讯录
        getView().findViewById(R.id.btn_contact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int read_contatcts_permission = ContextCompat.checkSelfPermission(getActivity().getApplication(), Manifest.permission.READ_CONTACTS);
                if (read_contatcts_permission == PackageManager.PERMISSION_GRANTED){
                    //拥有权限
                    getContactInfo();
                }else {
                    //没有权限，向用户申请权限
                    ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_CONTACTS}, CODE_FOR_READ_CONTACT);
                }
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

    private void getContactInfo(){
        ContentResolver contentResolver = getContext().getContentResolver();
        Cursor query = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (query != null){
            while (query.moveToNext()){
                String contactId = query.getString(query.getColumnIndex(ContactsContract.Contacts._ID));
                String contactName = query.getString(query.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                Log.e(TAG, "Id:" + contactId + "  Name:" + contactName);
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //通过requestCode判断是否是同一个请求
        if (requestCode == CODE_FOR_READ_CONTACT){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //用户同意授权
                getContactInfo();
            }else {

            }
        }
    }
}
