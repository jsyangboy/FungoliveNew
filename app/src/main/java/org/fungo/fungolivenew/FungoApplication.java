package org.fungo.fungolivenew;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import org.fungo.common_core.AppCore;
import org.fungo.common_core.utils.Utils;

/**
 * @author yqy
 * @create 19-7-15
 * @Describe
 */
public class FungoApplication extends MultiDexApplication {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        /**
         * 初始化AppCode
         */
        AppCore.init(this, BuildConfig.DEBUG || Utils.getChannel().equals("test"));


    }
}
