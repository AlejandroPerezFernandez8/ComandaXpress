package com.example.comandaxpress.SQLite;

import android.provider.BaseColumns;

public final class FeedReaderContract {
    private FeedReaderContract() {}
    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "Conexion";
        public static final String COLUMN_NAME_TITLE = "Api_ip";
    }
    static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                    FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedEntry.COLUMN_NAME_TITLE + " TEXT)";


    static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;
}

