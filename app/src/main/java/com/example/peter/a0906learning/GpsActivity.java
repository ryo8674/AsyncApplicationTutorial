package com.example.peter.a0906learning;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class GpsActivity extends AppCompatActivity implements LocationListener {

    // 緯度・経度表示用のTextView
    private TextView mTextViewNetworkLat, mTextViewNetworkLng, mTextViewGpsLat, mTextViewGpsLng;
    private ProgressBar mSearchProgressBar;

    private Button mButtonSearchNetwork, mButtonSearchGps;

    private Context mContext;
    private LocationManager mLocationManager;

    // どちらのプロバイダを実行中か判別できるようにする
    private int mExecSearchProvider;
    private static final int EXEC_PROVIDER_NETOWORK = 1;
    private static final int EXEC_PROVIDER_GPS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);

        mTextViewNetworkLat = (TextView) findViewById(R.id.textViewNetworkLat);
        mTextViewNetworkLng = (TextView) findViewById(R.id.textViewNetworkLng);
        mTextViewGpsLat = (TextView) findViewById(R.id.textViewGpsLat);
        mTextViewGpsLng = (TextView) findViewById(R.id.textViewGpsLng);
        mSearchProgressBar = (ProgressBar) findViewById(R.id.progressBar_Search);
        mButtonSearchNetwork = (Button) findViewById(R.id.button_search_location_network);
        mButtonSearchGps = (Button) findViewById(R.id.button_search_location_gps);
        mContext = this;
        mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

        // プログレスバーは初回非表示とする
        mSearchProgressBar.setVisibility(View.INVISIBLE);

        mButtonSearchNetwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ネットワークサービスよる検索が有効かチェック
                if (mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                    mLocationManager.removeUpdates(GpsActivity.this);
                    mButtonSearchNetwork.setClickable(false);
                    mButtonSearchGps.setClickable(false);
                    mSearchProgressBar.setVisibility(View.VISIBLE);
                    mExecSearchProvider = EXEC_PROVIDER_NETOWORK;
                    // 検索を実行
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplication(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, GpsActivity.this);
                } else {
                    // 無効
                    Toast.makeText(mContext, "ネットワークサービスが無効のため検索する事が出来ません", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mButtonSearchGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // GPSよる検索が有効かチェック
                if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    mLocationManager.removeUpdates(GpsActivity.this);
                    mButtonSearchNetwork.setClickable(false);
                    mButtonSearchGps.setClickable(false);
                    mSearchProgressBar.setVisibility(View.VISIBLE);
                    mExecSearchProvider = EXEC_PROVIDER_GPS;
                    // 検索を実行
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplication(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, GpsActivity.this);
                } else {
                    // 無効
                    Toast.makeText(mContext, "GPSが無効のため検索する事が出来ません", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        // バッググラウンドに移動した場合は検索を終了させる
        mLocationManager.removeUpdates(this);
        mButtonSearchNetwork.setClickable(true);
        mButtonSearchGps.setClickable(true);
        mSearchProgressBar.setVisibility(View.INVISIBLE);
    }

    // 位置が特定できると呼び出されます
    @Override
    public void onLocationChanged(Location location) {
        mLocationManager.removeUpdates(this);
        // テンプレートに表示
        if (mExecSearchProvider == EXEC_PROVIDER_GPS) {
            mTextViewGpsLat.setText(String.valueOf(location.getLatitude()));
            mTextViewGpsLng.setText(String.valueOf(location.getLongitude()));
        } else {
            mTextViewNetworkLat.setText(String.valueOf(location.getLatitude()));
            mTextViewNetworkLng.setText(String.valueOf(location.getLongitude()));
        }
        // ボタンを有効に戻す
        mButtonSearchNetwork.setClickable(true);
        mButtonSearchGps.setClickable(true);
        // プログレスを非表示とする
        mSearchProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
    }
}
