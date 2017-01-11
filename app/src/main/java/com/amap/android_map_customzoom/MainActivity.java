package com.amap.android_map_customzoom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.CameraPosition;

/**
 * 自定义地图缩放控件的实现，可以自定义位置
 */
public class MainActivity extends AppCompatActivity implements AMap.OnCameraChangeListener {
    private AMap aMap;
    private MapView mapView;
    private ZoomView mZoomView;
    private RelativeLayout zoomContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        init();
        initZoomControllerView(20,80);//添加自定义的缩放控件
    }

    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
            aMap.getUiSettings().setZoomControlsEnabled(false);//设置默认的缩放按钮不显示
            aMap.setOnCameraChangeListener(this);
        }
        zoomContainer = (RelativeLayout)findViewById(R.id.zoomContainer);
    }

    /**
     * 初始化缩放控件，设置位置
     * @param leftMargin 控件到左边的距离
     * @param bottomMargin 控件到底边的距离
     */
    private void initZoomControllerView(int leftMargin, int bottomMargin) {
        mZoomView = new ZoomView(this, aMap);
        LayoutParams zoomParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        zoomParams.setMargins(leftMargin,0,0,bottomMargin);//设置左，上，右，下，的距离
        zoomParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, -1);//添加位置信息 -1表示相对于父控件的位置
        zoomContainer.addView(mZoomView, zoomParams);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        //地图移动结束后，判断当前缩放级别，设置控件图片
        mZoomView.setZoomBitmap(cameraPosition.zoom);
    }
}
