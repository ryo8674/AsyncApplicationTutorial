package com.example.peter.a0906learning.cursor_loader;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

@SuppressWarnings("ConstantConditions")
public class PlaceProvider extends ContentProvider {

    private static final int PLACE = 1;
    private static final int PLACE_ID = 2;

    private static final UriMatcher URI_MATCHER;

    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URI_MATCHER.addURI(PlaceColumns.AUTHORITY, "place", PLACE);
        URI_MATCHER.addURI(PlaceColumns.AUTHORITY, "place/#", PLACE_ID);
    }

    private PlaceDBHelper mDBHelper;

    @Override
    public boolean onCreate() {
        mDBHelper = new PlaceDBHelper(getContext());
        return true;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues initialValues) {
        if (URI_MATCHER.match(uri) != PLACE) {
            throw new IllegalArgumentException("Unknown URL *** " + uri);
        }

        ContentValues values;
        if (initialValues != null) {
            values = new ContentValues(initialValues);
        } else {
            values = new ContentValues();
        }

        SQLiteDatabase db = mDBHelper.getWritableDatabase();

        // 追加、又はplace_idが重複の場合は更新
        long rowID = db.replace(PlaceColumns.TABLE_PLACE, "NULL", values);

        if (rowID > 0) {
            Uri newUri = ContentUris.withAppendedId(
                    PlaceColumns.Place.CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(newUri, null);
            return newUri;
        }
        throw new SQLException("Failed to insert row into " + uri);

    }

    @Override
    public int delete(@NonNull Uri uri, String whereClause, String[] whereArgs) {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        int count;

        switch (URI_MATCHER.match(uri)) {
            case PLACE:
                count = db.delete(PlaceColumns.TABLE_PLACE, " " +
                        PlaceColumns.Place.KEY_ID + " like '%'", null);
                break;
            default:
                throw new IllegalArgumentException("Unknown URL " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String where, String[] whereArgs) {

        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        int count;
        switch (URI_MATCHER.match(uri)) {
            case PLACE:
                count = db.update(PlaceColumns.TABLE_PLACE, values, where, whereArgs);
                break;

            case PLACE_ID:
                String id = uri.getPathSegments().get(1);
                count = db.update(PlaceColumns.TABLE_PLACE, values,
                        PlaceColumns.Place.KEY_PLACE_ID + "="
                                + id
                                + (!TextUtils.isEmpty(where) ? " AND (" + where
                                + ')' : ""), whereArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown URL " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(PlaceColumns.TABLE_PLACE);

        Log.d("query", uri.toString());
        switch (URI_MATCHER.match(uri)) {
            case PLACE:
                break;

            case PLACE_ID:
                qb.appendWhere(PlaceColumns.Place.KEY_PLACE_ID + "="
                        + uri.getPathSegments().get(1));
                break;
            default:
                throw new IllegalArgumentException("Unknown URL " + uri);
        }
        String orderBy;
        if (TextUtils.isEmpty(sortOrder)) {
            orderBy = PlaceColumns.Place.KEY_ID + " DESC"; // 新しい順
        } else {
            orderBy = sortOrder;
        }

        SQLiteDatabase db = mDBHelper.getReadableDatabase();
        Cursor cursor = qb.query(db, null, null, null, null, null, orderBy);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        //@formatter:off
        switch (URI_MATCHER.match(uri)) {
            case PLACE:
                return PlaceColumns.Place.CONTENT_TYPE; // expect the Cursor to contain 0..x items
            case PLACE_ID:
                return PlaceColumns.Place.CONTENT_ITEM_TYPE; // expect the Cursor to contain 1 item
            default:
                throw new IllegalArgumentException("Unknown URL " + uri);
        }
        //@formatter:on
    }
}