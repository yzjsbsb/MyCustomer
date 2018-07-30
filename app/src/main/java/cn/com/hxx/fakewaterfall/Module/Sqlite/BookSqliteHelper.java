package cn.com.hxx.fakewaterfall.Module.Sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by apple on 2018/7/30.
 */

public class BookSqliteHelper extends SQLiteOpenHelper {

    //数据库名
    private static final String DATA_BASE_NAME = "book.db";

    //数据库版本号
    private final static int DATA_BASE_VERSION = 1;

    //表名-书
    public final static String BOOK_TABLE_BOOK = "book";

    //表名-用户
    public final static String USER_TABLE_NAME = "user";

    //创建表-书(两列：主键_id自增长，书名)
    private final String CREATE_BOOK_TABLE = "create table " + BOOK_TABLE_BOOK + " (_id integer primary key autoincrement, bookName text)";

    //创建表-用户(散列:主键_id自增长、用户名、性别)
    private final String CREATE_USER_TABLE = "create table " + USER_TABLE_NAME + " (_id integer primary key autoincrement, userName text, sex text)";

    public BookSqliteHelper(Context context){
        super(context, DATA_BASE_NAME, null, DATA_BASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建两张表
        db.execSQL(CREATE_BOOK_TABLE);
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
