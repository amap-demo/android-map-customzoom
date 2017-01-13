package com.amap.android_map_customzoom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;

/**
 * 自定义的缩放控件
 * Created by my94493 on 2017/1/6.
 */

public class ZoomView extends LinearLayout {
    private AMap aMap;
    private ImageView zoomIn, zoomOut;
    public static final int ZOOMIN = 0;
    public static final int ZOOMOUT = 1;
    public ZoomView(Context context) {
        super(context);
    }
    public ZoomView(Context context,AttributeSet attrs){
        super(context, attrs);
    }
    public ZoomView(Context context, AMap map) {
        super(context);
        this.aMap = map;

        LayoutParams mParams = new LayoutParams(130, 130);
        zoomIn = new ImageView(context);
        zoomIn.setId(R.id.zoomin_view);
        zoomIn.setLayoutParams(mParams);
        zoomIn.setScaleType(ImageView.ScaleType.FIT_XY);
        zoomIn.setImageResource(R.drawable.zoomin_v);
        zoomIn.setClickable(true);
        zoomIn.setOnClickListener(onClickListener);

        zoomOut = new ImageView(context);
        zoomOut.setId(R.id.zoomout_view);
        zoomOut.setLayoutParams(mParams);
        zoomOut.setScaleType(ImageView.ScaleType.FIT_XY);
        zoomOut.setImageResource(R.drawable.zoomout_v);
        zoomOut.setClickable(true);
        zoomOut.setOnClickListener(onClickListener);

        this.setOrientation(VERTICAL);//HORIZONTAL水平排列，VERTICAL垂直排列
        this.addView(zoomIn);
        this.addView(zoomOut);
    }

    /**
     * 根据地图缩放级别，调整缩放按键的图片
     * @param zoomlevel
     */
    public void setZoomBitmap(float zoomlevel) {
        try {
            if (zoomlevel < aMap.getMaxZoomLevel()
                    && zoomlevel > aMap.getMinZoomLevel()) {
                zoomIn.setImageResource(R.drawable.zoomin_v);
                zoomOut.setImageResource(R.drawable.zoomout_v);
            } else if (zoomlevel == aMap.getMinZoomLevel()) {
                zoomOut.setImageResource(R.drawable.zoomout_v_disabled);
                zoomIn.setImageResource(R.drawable.zoomin_v);
            } else if (zoomlevel == aMap.getMaxZoomLevel()) {
                zoomIn.setImageResource(R.drawable.zoomin_v_disabled);
                zoomOut.setImageResource(R.drawable.zoomout_v);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    ImageView.OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            if(view.equals(zoomIn)){
                changeCamera(CameraUpdateFactory.zoomIn(), ZOOMIN);
            } else if(view.equals(zoomOut)){
                changeCamera(CameraUpdateFactory.zoomOut(), ZOOMOUT);
            }
        }
    };

    /**
     * 根据动画按钮状态，调用函数animateCamera来改变可视区域
     */
    private void changeCamera(CameraUpdate update, int zoommode) {
        if(zoommode == ZOOMIN) {
            if (aMap.getCameraPosition().zoom >= aMap.getMaxZoomLevel()){
                return;
            }
            aMap.animateCamera(update);
        } else if(zoommode == ZOOMOUT){
            if (aMap.getCameraPosition().zoom <= aMap.getMinZoomLevel()) {
                return ;
            }
            aMap.animateCamera(update);
        }
    }
}
