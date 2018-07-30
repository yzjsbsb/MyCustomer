package cn.com.hxx.fakewaterfall.FourComponents.MyContentProvider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import cn.com.hxx.fakewaterfall.Module.Sqlite.BookSqliteHelper;

/**
 * Created by apple on 2018/7/30.
 */

public class MyBookProvider extends ContentProvider {

    SQLiteDatabase writableDatabase;
    private Context context;
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);   //常量UriMatcher.NO_MATCH表示不匹配任何路径的返回码
    private static final String AUTHORITY = "com.hxx.fakewater.bookprovider";
    private static final int BOOK_URI_CODE = 0;
    private static final int USER_URI_CODE = 1;

    static {
        uriMatcher.addURI(AUTHORITY, BookSqliteHelper.BOOK_TABLE_BOOK, BOOK_URI_CODE);  //第三个参数为匹配到BOOK_TABLE_BOOK时match()方法的返回值
        uriMatcher.addURI(AUTHORITY, BookSqliteHelper.USER_TABLE_NAME, USER_URI_CODE);
    }

    @Override
    public boolean onCreate() {
        context = getContext();
        initProviderData();
        return false;
    }

    //初始化原始数据
    private void initProviderData() {
        //获取BookSqliteHelper中可写入的数据库,即book.db;
        writableDatabase = new BookSqliteHelper(context).getWritableDatabase();
        writableDatabase.execSQL("DELETE FROM " + BookSqliteHelper.BOOK_TABLE_BOOK);
        writableDatabase.beginTransaction();
        //通过将一个 ContentValues 对象传递至 insert() 方法将数据插入数据库
        ContentValues contentValues = new ContentValues();
        contentValues.put("bookName", "数据原理");  //类似于key-value
        //表名-空列的默认值-contentValues(key，value);
        writableDatabase.insert(BookSqliteHelper.BOOK_TABLE_BOOK, null, contentValues);
        contentValues.put("bookName", "编译原理");
        writableDatabase.insert(BookSqliteHelper.BOOK_TABLE_BOOK, null, contentValues);
        contentValues.put("bookName", "网络原理");
        writableDatabase.insert(BookSqliteHelper.BOOK_TABLE_BOOK, null, contentValues);
        //清空key-value数据
        contentValues.clear();
        contentValues.put("userName", "张三");
        contentValues.put("sex", "女");
        writableDatabase.insert(BookSqliteHelper.USER_TABLE_NAME, null, contentValues);
        contentValues.put("userName", "李四");
        contentValues.put("sex", "男");
        writableDatabase.insert(BookSqliteHelper.USER_TABLE_NAME, null, contentValues);
        contentValues.put("userName", "王五");
        contentValues.put("sex", "男");
        writableDatabase.insert(BookSqliteHelper.USER_TABLE_NAME, null, contentValues);

        //设置事务标志为成功，当结束事务时就会提交事务，否则会回滚
        writableDatabase.setTransactionSuccessful();
        writableDatabase.endTransaction();

    }

    private String getTableName(Uri uri){
        String tableName = null;
        switch (uriMatcher.match(uri)){
            case  BOOK_URI_CODE:
                    tableName = BookSqliteHelper.BOOK_TABLE_BOOK;
                break;
            case  USER_URI_CODE:
                    tableName = BookSqliteHelper.USER_TABLE_NAME;
                break;
        }
        return tableName;
    }


    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        String tableName = getTableName(uri);
        if (tableName == null){
            throw new IllegalArgumentException("Unsupported URI :" + uri);
        }
        Cursor query = writableDatabase.query(tableName, projection, selection, selectionArgs, null, null, sortOrder, null);
        return query;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        String tableName = getTableName(uri);
        if (tableName == null){
            throw new IllegalArgumentException("Unsupported URI :" + uri);
        }
        writableDatabase.insert(tableName, null, values);
        getContext().getContentResolver().notifyChange(uri, null);
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        String tableName = getTableName(uri);
        if (tableName == null){
            throw new IllegalArgumentException("Unsupported URI :" + uri);
        }
        int delete = writableDatabase.delete(tableName, selection, selectionArgs);
        if (delete > 0){
            //删除成功
            context.getContentResolver().notifyChange(uri, null);
        }
        return delete;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        String tableName = getTableName(uri);
        if (tableName == null){
            throw new IllegalArgumentException("Unsupported URI :" + uri);
        }
        int update = writableDatabase.update(tableName, values, selection, selectionArgs);
        if (update > 0){
            //修改成功
            context.getContentResolver().notifyChange(uri, null);
        }
        return update;
    }
}
