package com.example.comandaxpress.Util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

import com.example.comandaxpress.SQLite.FeedReaderContract;
import com.example.comandaxpress.SQLite.FeedReaderDbHelper;

import java.util.ArrayList;
import java.util.List;
public class SQLiteUtils {
    /**
     * Clase para la gestion de la BD SQLITE
     * */
    private static FeedReaderDbHelper dbHelper;

    public static void insertarIP(Context context, String ip){
        if (dbHelper == null) {
            dbHelper = new FeedReaderDbHelper(context.getApplicationContext());
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE, ip);
        long newRowId = db.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
    }

    public static String getIP(Context context) {
        if (dbHelper == null) {
            dbHelper = new FeedReaderDbHelper(context.getApplicationContext());
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] projection = {FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE};
        Cursor cursor = db.query(
                FeedReaderContract.FeedEntry.TABLE_NAME,
                projection, null, null, null, null, null
        );

        String ip = null;
        if (cursor.moveToFirst()) {
            ip = cursor.getString(
                    cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE));
        }
        cursor.close();
        return ip;
    }

    public static void modificarIP(Context context, String newIp) {
        if (dbHelper == null) {
            dbHelper = new FeedReaderDbHelper(context.getApplicationContext());
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE, newIp);
        int count = db.update(FeedReaderContract.FeedEntry.TABLE_NAME, values, null,  null);
        Log.d("Update Operation", "Rows affected: " + count);
    }

}
