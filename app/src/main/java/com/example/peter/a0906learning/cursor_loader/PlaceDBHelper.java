package com.example.peter.a0906learning.cursor_loader;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class PlaceDBHelper extends SQLiteOpenHelper{
    //@formatter:off
    private static final String CREATE_PLACE_TABLE_SQL = "CREATE TABLE "
            + PlaceColumns.TABLE_PLACE + "("
            + PlaceColumns.Place.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + PlaceColumns.Place.KEY_PLACE_ID + " TEXT UNIQUE NOT NULL,"
            + PlaceColumns.Place.KEY_PLACE + " TEXT,"
            + PlaceColumns.Place.KEY_URL + " TEXT"
            + ")";
    //@formatter:on
    private static final String DROP_PLACE_TABLE_SQL = "DROP TABLE IF EXISTS "
            + PlaceColumns.TABLE_PLACE;

    PlaceDBHelper(Context context) {
        super(context, PlaceColumns.DATABASE_NAME, null, PlaceColumns.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PLACE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL(DROP_PLACE_TABLE_SQL);

        // Create tables again
        onCreate(db);
    }
}
