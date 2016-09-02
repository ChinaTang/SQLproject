package com.di.tang.sqlproject.contentprovide;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;

import com.di.tang.sqlproject.sql.Database;

/**
 * Created by tangdi on 2016/9/1.
 */
public class SelfContentProvide extends ContentProvider {

    private static final UriMatcher mUriMatcher;
    private static final int MATCH_FIRST = 1;
    private static final int MATCH_SECOND = 2;
    public static final String AUTHORITY =
            "com.di.tang.sqlproject.contentprovide.SelfContentProvide";
    public static final Uri CONTENT_URI_FIRST =
            Uri.parse("content://" + AUTHORITY + "/frist");
    public static final Uri CONTENT_URI_SECOND =
            Uri.parse("content://" + AUTHORITY + "/second");

    static{
        mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mUriMatcher.addURI(AUTHORITY, "/frist", MATCH_FIRST);
        mUriMatcher.addURI(AUTHORITY, "/second", MATCH_SECOND);
    }
    private Database mDataBase;

    @Override
    public boolean onCreate() {
        mDataBase = new Database(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        SQLiteDatabase sqLiteDatabase = mDataBase.getWritableDatabase();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        if(mUriMatcher.match(uri) == MATCH_FIRST){
            queryBuilder.setTables(Database.TABLE1);
        }else if(mUriMatcher.match(uri) == MATCH_SECOND){
            queryBuilder.setTables(Database.TABLE1);
        }else{
            throw new IllegalArgumentException("Unknown URI " + uri);
        }
        Cursor cursor = queryBuilder.query(sqLiteDatabase, strings, s, strings1, s1, null, null, null);
     //   sqLiteDatabase.close();
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        SQLiteDatabase sqLiteDatabase = mDataBase.getWritableDatabase();
        if(mUriMatcher.match(uri) == MATCH_FIRST){
            long rowID = sqLiteDatabase.insert(Database.TABLE1, null, contentValues);
            if(rowID > 0){
                Uri uri1 = ContentUris.withAppendedId(CONTENT_URI_FIRST, rowID);
            }
        }else if(mUriMatcher.match(uri) == MATCH_SECOND){
            long rowID = sqLiteDatabase.insert(Database.TABLE2, null, contentValues);
            if(rowID > 0){
                Uri uri1 = ContentUris.withAppendedId(CONTENT_URI_SECOND, rowID);
            }
        }else{
            throw new IllegalArgumentException("Unknown URI " + uri);
        }
        sqLiteDatabase.close();
        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        SQLiteDatabase sqLiteDatabase = mDataBase.getWritableDatabase();
        int count = 0;
        if(mUriMatcher.match(uri) == MATCH_FIRST){
            count = sqLiteDatabase.delete(Database.TABLE1, s, strings);
        }else if(mUriMatcher.match(uri) == MATCH_SECOND){
            count = sqLiteDatabase.delete(Database.TABLE2, s, strings);
        }else {
            throw new IllegalArgumentException("Unknown URI " + uri);
        }
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {

        int count = 0;
        SQLiteDatabase sqLiteDatabase = mDataBase.getWritableDatabase();
        if(mUriMatcher.match(uri) == MATCH_FIRST){
            count = sqLiteDatabase.update(Database.TABLE1, contentValues, s, strings);
        }else if(mUriMatcher.match(uri) == MATCH_SECOND){
            count = sqLiteDatabase.update(Database.TABLE2, contentValues, s, strings);
        }else{
            throw new IllegalArgumentException("Unknown URI " + uri);
        }
        this.getContext().getContentResolver().notifyChange(uri, null);
        sqLiteDatabase.close();
        return count;
    }
}
