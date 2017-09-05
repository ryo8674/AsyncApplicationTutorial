package com.example.peter.a0906learning.async_query_loader;

import android.net.Uri;
import android.provider.BaseColumns;

class UserColumns implements BaseColumns {
    // URIパス
    static final String PATH = "users";
    // コンテントURI
    static final Uri CONTENT_URI = Uri.parse("content://" + MyContentProvider.AUTHORITY + "/" + PATH);
    // テーブル指定コンテントタイプ
    static final String CONTENT_TYPE = "vnd.android.cursor.item/vnd.example.users";
    // レコード個別指定コンテントタイプ
    static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.dir/vnd.example.users";

    // テーブル名
    static final String TABLE = "users";
    //カラム名 ID
    static final String ID = "_id";
    // カラム 名前
    static final String NAME = "name";
    // カラム メールアドレス
    static final String EMAIL = "email";

    // コンストラクタ(呼ばれることは無い)
    private UserColumns() {}
}
