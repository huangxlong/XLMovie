package com.hxl.xlmovie;

import android.app.Activity;
import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/4 0004.
 */

public class App extends Application {

    protected static App sApp;
    public List<Activity> mActivities = new ArrayList<Activity>();
    public static final boolean IS_PUBLISH_VERSION = BuildConfig.PUBLIC;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        sApp = this;
    }

    public static App getApplication() {
        return sApp;
    }

    public static void addActivity(Activity activity) {
        if (sApp != null) {
            sApp.mActivities.add(activity);
        }
    }

}
