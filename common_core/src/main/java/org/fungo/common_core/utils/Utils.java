package org.fungo.common_core.utils;

import android.text.TextUtils;

import org.fungo.common_core.AppCore;

/**
 * @author yqy
 * @create 19-7-16
 * @Describe
 */
public class Utils {


    private static String marketName = "";

    public static String getChannel() {
        if ("blank".equals(marketName) || TextUtils.isEmpty(marketName)) {
            marketName = ManifestMetaDataUtils.getString(AppCore.getApplication(), "UMENG_CHANNEL");
        }
        return marketName;
    }

}
