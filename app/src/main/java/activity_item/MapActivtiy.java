package activity_item;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.baidu.location.LocationClient;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.ldc.easycar.R;

import java.util.List;

/**
 * Created by Mr.Liu on 2016/6/15.
 */
public class MapActivtiy extends Activity {
    private LatLng latLng;
    private BaiduMap mBaiduMap;
    private LocationClient mLocationClient;
    private List<LatLng> points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_map);

        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        latLng = new LatLng(intent.getDoubleExtra("latitude",0),intent.getDoubleExtra("longitude",0));
        MapView mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();

        //构建Marker所需要显示的icon图标
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(
                R.drawable.addr_tag_press);
        //用来做标记的选项
        OverlayOptions markOptions = new MarkerOptions()
                .position(latLng)  //设置覆盖物所显示位置
                .icon(icon)  //覆盖物的图标
                .zIndex(9)   //覆盖物的z轴坐标
                .draggable(true);  //设置覆盖物可拖动
        mBaiduMap.addOverlay(markOptions);

//        //设置地图的中心点为地图定位返回给用户的位置，并设置地图缩放级别
//        builder.target(latLng).zoom(18.0f);
        //通过动画的方式将地图的中心点设置为地图返回给用户的位置经纬度
//        mBaiduMap.animateMapStatus(
//                MapStatusUpdateFactory.newMapStatus(builder.build()));
//        mBaiduMap.setOnMarkerDragListener(new BaiduMap.OnMarkerDragListener() {
//
//            @Override
//            public void onMarkerDragStart(Marker arg0) {
//                Log.e("123", "onMarkerDragStart");
//            }
//
//            @Override
//            public void onMarkerDragEnd(Marker marker) {
//                // TODO Auto-generated method stub
//                Log.e("123", "onMarkerDragEnd");
//                LatLng ll = new LatLng(marker.getPosition().latitude,
//                        marker.getPosition().longitude);
//
//                points.add(ll);
//
//                //if(points.size() >= 3) {
//                    //构建多边形的Options对象
//                    OverlayOptions polygonOptions = new PolygonOptions()
//                            .points(points)
//                            .stroke(new Stroke(5, 0xAA00FF00))
//                            .fillColor(0xAAFFFF00);
//
//                    mBaiduMap.addOverlay(polygonOptions);
//                //}
//
//            }
//
//            @Override
//            public void onMarkerDrag(Marker arg0) {
//                // TODO Auto-generated method stub
//                Log.e("123", "onMarkerDrag");
//
//            }
//        });

    }




}
