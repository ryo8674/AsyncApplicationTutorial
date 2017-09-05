package com.example.peter.a0906learning.async_query_loader;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class UserDBHelper extends SQLiteOpenHelper {
    // DB名とバージョン
    private static final String DB_NAME = "database.db";
    private static final int DB_VERSION = 1;
    private static final String CREATE_TABLE_SQL = "create table " + UserColumns.TABLE
            + " (_id integer primary key, "
            + UserColumns.NAME + " text not null, "
            + UserColumns.EMAIL + " text)";

    // コンストラクタ
    UserDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // データベースのテーブルを作成する
        sqLiteDatabase.execSQL(CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        // DBに変更があったときに呼ばれる
    }
}
