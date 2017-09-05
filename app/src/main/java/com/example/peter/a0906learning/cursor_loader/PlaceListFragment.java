package com.example.peter.a0906learning.cursor_loader;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.peter.a0906learning.R;

import java.util.ArrayList;
import java.util.List;


public class PlaceListFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor> { // ←これを実装する必要がある

    private SimpleCursorAdapter mAdapter;
    List<Place> mPlaces;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.list, container, false);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Loaderの初期化
        getLoaderManager().initLoader(0, null, this);

        // ListViewにセットしたいテーブルのフィールドを配列にセット
        String[] from = new String[]{
                PlaceColumns.Place.KEY_PLACE,
                PlaceColumns.Place.KEY_URL
        };
        // ListViewの各ビューを配列にセット
        int[] to = new int[]{
                R.id.place,
                R.id.url
        };
        // アダプターに一行のレイアウトをセット
        mAdapter = new SimpleCursorAdapter(getActivity(), R.layout.list_item, null,
                from, to,
                SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        // アダプターをListViewにセット
        ListView listView = getView().findViewById(R.id.listview);
        listView.setAdapter(mAdapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // Loaderの廃棄
        getLoaderManager().destroyLoader(0);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
        // CursorLoader生成（検索条件の指定）
        return new CursorLoader(this.getActivity(),
                PlaceColumns.Place.CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        Cursor old = mAdapter.swapCursor(cursor);
        if (old != null) {
            old.close();
        }
        if (cursor.getCount() == 0) {
            mPlaces = null;
            return;
        }
        mPlaces = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Place place = new Place();
                place.setID(cursor.getInt(0));
                place.setPlaceID(cursor.getString(1));
                place.setPlace(cursor.getString(2));
                place.setUrl(cursor.getString(3));
                // Adding to list
                mPlaces.add(place);
            } while (cursor.moveToNext());
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Cursor old = mAdapter.swapCursor(null);
        if (old != null) {
            old.close();
        }
    }
}