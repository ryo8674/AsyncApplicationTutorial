package com.example.peter.a0906learning.async_query_loader;

import android.app.ListActivity;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.TextView;

public class AsyncQueryHandlerActivity extends ListActivity {

    private static final String[] PROJECTION = new String[]{UserColumns.ID, UserColumns.NAME, UserColumns.EMAIL};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CustomAsyncQueryHandler asyncQueryHandler = new CustomAsyncQueryHandler(getContentResolver());

        //delete
        asyncQueryHandler.startDelete(0, null, UserColumns.CONTENT_URI, null, null);

        // テーブルにデータ投入.
        ContentValues values = new ContentValues();
        for (int i = 1; i <= 10; i++) {
            values.clear();
            values.put(UserColumns.NAME, "name" + i);
            values.put(UserColumns.EMAIL, "email" + i);
            getContentResolver().insert(UserColumns.CONTENT_URI, values);
//            asyncQueryHandler.startInsert(0, null, UserColumns.CONTENT_URI, values);
        }

//        // テーブルのデータを全件検索. 表示.
//        Cursor c = getContentResolver().query(UserColumns.CONTENT_URI, null, null, null, null);
//        //noinspection deprecation
//        startManagingCursor(c);
//        assert c != null;
//        while (c.moveToNext()) {
//            for (int i = 0; i < c.getColumnCount(); i++) {
//                Log.d(getClass().getSimpleName(), c.getColumnName(i) + " : " + c.getString(i));
//            }
//        }
        //select *
        asyncQueryHandler.startQuery(0, null, UserColumns.CONTENT_URI, PROJECTION, null, null, null);

    }

    private class CustomAsyncQueryHandler extends AsyncQueryHandler {

        CustomAsyncQueryHandler(ContentResolver cr) {
            super(cr);
        }

        @SuppressWarnings("deprecation")
        @Override
        protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
            super.onQueryComplete(token, cookie, cursor);
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(AsyncQueryHandlerActivity.this, android.R.layout.simple_list_item_2, cursor, PROJECTION, new int[]{android.R.id.text1, android.R.id.text2});

            adapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {

                public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                    ((TextView) view).setText(cursor.getString(columnIndex));
                    ((TextView) view).setText(cursor.getString(columnIndex));

                    return true;
                }
            });
            setListAdapter(adapter);

        }
    }
}
