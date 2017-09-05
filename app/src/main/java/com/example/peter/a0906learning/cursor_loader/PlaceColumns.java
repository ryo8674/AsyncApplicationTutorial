package com.example.peter.a0906learning.cursor_loader;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * DB、テーブル、クエリURLの定義
 */
class PlaceColumns {
    static final String AUTHORITY = "com.example.peter.a0906learning.place";
    static final String DATABASE_NAME = "place.db";
    static final int DATABASE_VERSION = 1;
    static final String TABLE_PLACE = "place";

    static final class Place implements BaseColumns {

        static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/place");

        static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.myexamplecursorloader.place";
        static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.myexamplecursorloader.place";

        // Place Table Columns names
        static final String KEY_ID = "_id";
        static final String KEY_PLACE_ID = "place_id";
        static final String KEY_PLACE = "place";
        static final String KEY_URL = "url";

    }
}