package com.hxl.xlmovie.base;

import android.Manifest;
import android.app.Activity;
import android.app.Application;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.hxl.xlmovie.BuildConfig;
import com.hxl.xlmovie.entity.Address;
import com.hxl.xlmovie.util.LogUtils;
import com.hxl.xlmovie.util.PermissionUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/9/4 0004.
 */

public class App extends Application {

    protected static App sApp;
    public List<Activity> mActivities = new ArrayList<Activity>();
    public static final boolean IS_PUBLISH_VERSION = BuildConfig.PUBLIC;
    private LocationClient mLocationClient = null;
    private BDLocationListener myListener = new MyLocationListener();
    private LocationListener locationListener;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        sApp = this;
        initLocation();
    }

    /**
     * 初始化定位
     */
    private void initLocation() {
        //声明LocationClient类
        mLocationClient = new LocationClient(this);
        //注册监听函数
        mLocationClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认gcj02，设置返回的定位结果坐标系
        option.setCoorType("bd09ll");
        int span = 0;
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setScanSpan(span);
        //可选，设置是否需要地址信息，默认不需要
        option.setIsNeedAddress(true);
        //可选，默认false,设置是否使用gps
        option.setOpenGps(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setLocationNotify(false);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationDescribe(false);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIsNeedLocationPoiList(false);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.setIgnoreKillProcess(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集
        option.SetIgnoreCacheException(false);
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        option.setEnableSimulateGps(false);
        mLocationClient.setLocOption(option);
    }

    public void startLocation(SimpleActivity activity, LocationListener listener) {
        final String[] permissions2 = new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION};
        if (!PermissionUtil.checkPermission(activity, permissions2)) {
            PermissionUtil.getPermission(activity, permissions2, SimpleActivity.PERMISSION_CODE);
        } else {
            initLocation();
            locationListener = listener;
            mLocationClient.start();
        }
    }


    private class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            LogUtils.d("BaiduLocationApiDem", "" + location.toString());
            if (location.getLocType() == BDLocation.TypeCriteriaException) {
                LogUtils.d("无法获取地理位置信息");
//                hideLoadingDialog();
                if (locationListener != null) {
                    locationListener.onLocationResult(null);
                }
                return;
            }
            Address.LocationInfo locationInfo = new Address.LocationInfo();
            locationInfo.city = location.getCity();
            locationInfo.desc = location.getAddrStr();
            locationInfo.lat = location.getLatitude();
            locationInfo.lng = location.getLongitude();
            locationInfo.province = location.getProvince();
            if (location.getLocType() == BDLocation.TypeGpsLocation) {
                // GPS定位结果
                LogUtils.d("gps定位成功 ：" + location.getAddrStr());
                //locationSuccess();
                //  layoutLocationFail.setVisibility(View.GONE);
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
                // 网络定位结果
                LogUtils.d("网络定位成功");
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {
                LogUtils.d("离线定位成功，离线定位结果也是有效的");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                LogUtils.d("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                LogUtils.d("网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                LogUtils.d("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            } else {
                LogUtils.d("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
//            hideLoadingDialog();
            if (locationListener != null) {
                locationListener.onLocationResult(locationInfo);
            }
        }

    }


    public static App getApplication() {
        return sApp;
    }

    public static void addActivity(Activity activity) {
        if (sApp != null) {
            sApp.mActivities.add(activity);
        }
    }

    public static void removeActivity(Activity act) {
        if (sApp.mActivities != null) {
            sApp.mActivities.remove(act);
        }
    }

    public interface LocationListener {
        void onLocationResult(Address.LocationInfo location);
    }

}
