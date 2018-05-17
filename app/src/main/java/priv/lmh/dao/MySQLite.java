package priv.lmh.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.Settings;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import priv.lmh.bean.ShoppingCart;
import priv.lmh.util.JSONUtil;

/**
 * Created by HY on 2018/4/7.
 * Database all_order TABLE1:myorder(phonenumber->String,order->String).
 */

public class MySQLite {
    public static final String TAG = "MySQLite";

    public static final String db_name_2 = "all_order.db";
    public static final String db_name_1 = "all_orders.db";
    private static final String table_myorder = "myorder";
    private static int version = 1;

    private static SQLiteOpenHelper sqLiteOpenHelper;
    private static SQLiteDatabase sqLiteDatabase;

    public MySQLite(Context context) {
        sqLiteOpenHelper = new SQLiteOpenHelper(context, db_name_1, null, version) {
            @Override
            public void onCreate(SQLiteDatabase db) {
                String sql = "create table if not exists " + table_myorder +
                        "(phonenumber text primary key ," +
                        "goodorder text)";
                db.execSQL(sql);

                ContentValues values = new ContentValues();
                values.put("phonenumber", "666");
                db.insert(table_myorder, null, values);
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            }
        };

        sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
    }

    /**
     * @param phonenumber Users'phonenumber
     * @param newOrder       Users' order;
     */
    public void addOrder(String phonenumber,List<ShoppingCart> newOrder) {
        /*String sql = "select goodorder from " + table_myorder + " where phonenumber=?";
        //先取得原订单信息再合并，再放进数据库
        String order = "";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, new String[]{phonenumber});
        if (cursor.moveToFirst()) {
            Log.d(TAG, "addOrder: true");
            order = cursor.getString(cursor.getColumnIndex("goodorder"));
            Log.d(TAG, "addOrder: " + order);
        } else {
            //如果查询不到就创建新的列
            Log.d(TAG, "addOrder: false");
            ContentValues contentValues = new ContentValues();
            contentValues.put("phonenumber",phonenumber);
            contentValues.put("goodorder","");
            sqLiteDatabase.insert(table_myorder,null,contentValues);
        }*/

        //old order
        List<ShoppingCart> order = getOrder(phonenumber);

        //更新数据，旧的数据+新的数据
        if(order == null){
            order = newOrder;
        }else {
            order.addAll(newOrder);
        }

        updateSQLDB(phonenumber, order);

    }

    //更新数据库
    private void updateSQLDB(String phonenumber,List<ShoppingCart> order){
        ContentValues contentValues = new ContentValues();
        contentValues.put("goodorder",JSONUtil.toJSON(order));
        String whereClause = "phonenumber=?";
        String[] whereArgs = {phonenumber};
        sqLiteDatabase.update(table_myorder,contentValues,whereClause,whereArgs);
    }

    //获得订单
    public List<ShoppingCart> getOrder(String phonenumber){

        //先取得原订单信息再合并，再放进数据库
        String order = "";

        String sql = "select goodorder from " + table_myorder + " where phonenumber=?";
        order = queryOrder(phonenumber,sql);
        /*Cursor cursor = sqLiteDatabase.rawQuery(sql, new String[]{phonenumber});
        if (cursor.moveToFirst()) {
            Log.d(TAG, "addOrder: true");
            order = cursor.getString(cursor.getColumnIndex("goodorder"));
            Log.d(TAG, "addOrder: " + order);
        } else {
            //如果查询不到就创建新的列
            createNewRow(phonenumber);
        }
        cursor.close();*/

        if(order == null)
            return null;
        if(order == ""){
            return new ArrayList<ShoppingCart>();
        }

        return JSONUtil.fromJson(order,new TypeToken<List<ShoppingCart>>(){}.getType());
    }

    //查询订单
    private String queryOrder(String phonenumber,String sql){
        Cursor cursor = sqLiteDatabase.rawQuery(sql, new String[]{phonenumber});
        if (cursor.moveToFirst()) {
            Log.d(TAG, "addOrder: true");
            return cursor.getString(cursor.getColumnIndex("goodorder"));
        } else {
            //如果查询不到就创建新的列
            createNewRow(phonenumber);
        }
        cursor.close();
        return null;
    }

    //创建新的行
    public void createNewRow(String phonenumber){
        Log.d(TAG, "createNewRow: new row");
        ContentValues contentValues = new ContentValues();
        contentValues.put("phonenumber",phonenumber);
        contentValues.put("goodorder","");
        sqLiteDatabase.insert(table_myorder,null,contentValues);
    }

    public void close() {
        sqLiteOpenHelper.close();
    }

}
