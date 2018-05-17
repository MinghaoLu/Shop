package priv.lmh.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by HY on 2018/5/3.
 */

public class TableMyOrderOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = "TableMyOrderOpenHelper";
    public static final String db_name_2 = "all_order.db";
    public static final String db_name_1 = "all_orders.db";
    private static final String table_myorder = "myorder";
    private static int version = 1;

    public TableMyOrderOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*createTable*/
        /*String sql = "create table if not exists " + table_myorder +
                "(phonenumber text primary key ," +
                "goodorder text)";
        db.execSQL(sql);*/
        createTable(db);

        /*initialTable*/
        /*ContentValues values = new ContentValues();
        values.put("phonenumber", "666");
        db.insert(table_myorder, null, values);*/
        initialTable(db);
    }

    private void createTable(SQLiteDatabase db){
        String sql = "create table if not exists " + table_myorder +
                "(phonenumber text primary key ," +
                "goodorder text)";
        db.execSQL(sql);
    }

    private void initialTable(SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put("phonenumber", "666");
        db.insert(table_myorder, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
