package com.di.tang.sqlproject.sql;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.util.Log;

/**
 * Created by tangdi on 2016/9/1.
 */
public class Database extends SQLiteOpenHelper {
    private static final String TAG = "Database";
    public static final String DTAT_BASE_NAME = "FromSQLproject.db";
    public static final int DATA_VERSION = 1;
    public static final String TABLE1 = "table1";
    public static final String TABLE2 = "table2";
    public static final String SQL_CREATE_TABLE1 = "CREATE TABLE" +  " " + TABLE1 + "("
            + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "table_name" +" VARCHAR(50) default 'first',"
            + "name" + " VARCHAR(50),"
            + "detail" + " TEXT"
            + ");";

    public static final String SQL_CREATE_TABLE2 = "CREATE TABLE "+ TABLE2 +" ("
            + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "table_name" +" VARCHAR(50) default 'second',"
            + "name" + " VARCHAR(50),"
            + "detail" + " TEXT"
            + ");" ;

    public Database(Context context){
        super(context, DTAT_BASE_NAME, null, DATA_VERSION);
    }

    public Database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public Database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version,
                    DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE1);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE2);
        Log.d(TAG, "onCreate: " + "Create SUCCESS");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS first");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS second");
        onCreate(sqLiteDatabase);
    }
}
