package org.fungo.common_core.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import org.fungo.common_core.AppCore;

import java.util.Random;

/**
 * @author yqy
 * @create 19-7-16
 * @Describe
 */
public class Utils {


    /**
     * 获取渠道
     */
    private static String marketName = "";

    public static String getChannel() {
        if ("blank".equals(marketName) || TextUtils.isEmpty(marketName)) {
            marketName = ManifestMetaDataUtils.getString(AppCore.getApplication(), "UMENG_CHANNEL");
        }
        return marketName;
    }

    /**
     * 获取版本号
     */
    private static String versionName = "";

    public static String getVersionNameTiny() {
        if (versionName == null) {
            versionName = getPackInfo(AppCore.getApplication()).versionName;
        }
        try {
            return versionName.substring(0, 5);
        } catch (Exception e) {
            return versionName;
        }
    }


    public static PackageInfo getPackInfo(Context context) {
        return getPackInfo(context, context.getPackageName());
    }

    public static PackageInfo getPackInfo(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(packageName, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return packInfo;
    }


    private static int marketMaskCache;

    public static int getMarketInfo() {
        if (marketMaskCache == 0) {
            int marketMask = 1;
            String marketName = getChannel();
            if (marketName.equals("360")) {
                marketMask = 2;
            } else if (marketName.equals("baidu")) {
                marketMask = 4;
            } else if (marketName.equals("wandoujia")) {
                marketMask = 8;
            } else if (marketName.equals("xiaomi")) {
                marketMask = 16;
            } else if (marketName.equals("tencent")) {
                marketMask = 32;
            } else if (marketName.equals("anzhuo")) {
                marketMask = 64;
            } else if (marketName.equals("googleplay")) {
                marketMask = 128;
            } else if (marketName.equals("anzhi")) {
                marketMask = 256;
            } else if (marketName.equals("lenovo")) {
                marketMask = 512;
            } else if (marketName.equals("yingyonghui")) {
                marketMask = 1024;
            } else if (marketName.equals("youmengupdate")) {
                marketMask = 2048;
            } else if (marketName.equals("test")) {
                marketMask = 4096;
            } else if (marketName.equals("huawei")) {
                marketMask = 65536;
            } else if (marketName.equals("oppo")) {
                marketMask = 131072;
            } else if (marketName.equals("vivo")) {
                marketMask = 262144;
            }
            marketMaskCache = marketMask;
        }

        return marketMaskCache;
    }

    /**
     * Platform Version
     *
     * @return
     */
    public static String getReleaseVersionNumber() {
        return Build.VERSION.RELEASE;
    }


    /**
     * 机型
     *
     * @return
     */
    public static String getDeviceModel() {
        return Build.MODEL;
    }


    /**
     * 获取设备码
     *
     * @param context
     * @return
     */
    private static String deviceId;

    public static String getDeviceId() {
        try {
            if ("none".equals(deviceId) || TextUtils.isEmpty(deviceId)) {
                TelephonyManager tm = (TelephonyManager) AppCore.getApplication().getSystemService(Context.TELEPHONY_SERVICE);
                deviceId = tm.getDeviceId();
                if (deviceId == null) {
                    deviceId = "none";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deviceId;
    }


    /**
     * 获取原始的设备码
     *
     * @param context
     * @return
     */
    private static String udid;

    public static String getDeviceInfoWithoutMD5() {
        Context context = AppCore.getApplicationContext();
        if (TextUtils.isEmpty(udid)) {
            try {
                android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                String device_id = null;
                if (checkPermission(context, Manifest.permission.READ_PHONE_STATE)) {
                    device_id = tm.getDeviceId();
                }

                if (TextUtils.isEmpty(device_id)) {
                    try {
                        device_id = android.provider.Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                String mac = wifi.getConnectionInfo().getMacAddress();
                StringBuilder builder = new StringBuilder();
                builder.append(mac).append(":");
                if (TextUtils.isEmpty(device_id)) {
                    device_id = mac;
                }
                if (TextUtils.isEmpty(device_id)) {
                    device_id = android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
                }
                builder.append(device_id);
                udid = builder.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return udid;
    }

    @SuppressLint("NewApi")
    public static boolean checkPermission(Context context, String permission) {
        try {
            boolean result = false;
            if (Build.VERSION.SDK_INT >= 23) {
                if (context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) {
                    result = true;
                }
            } else {
                PackageManager pm = context.getPackageManager();
                if (pm.checkPermission(permission, context.getPackageName()) == PackageManager.PERMISSION_GRANTED) {
                    result = true;
                }
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    private static String androidId = "";

    public static String getAndroidId() {
        try {
            Context context = AppCore.getApplicationContext();
            if (TextUtils.isEmpty(androidId)) {

                /**
                 * 通过内部存储来获取设备andoridid
                 */
                androidId = PrefsUtils.getPrefs(context).getString("androidid", "");

                Logger.e("test androidId=" + androidId);

                if (TextUtils.isEmpty(androidId)) {

                    /**
                     * 如果获取不到，在通过文件夹获取设备id
                     */
                    androidId = FileUtil.readFile(FileUtil.getRootFolder() + "/appid.txt");
                    if (TextUtils.isEmpty(androidId)) {

                        /**
                         * 如果还是获取不到通过系统获取设备id
                         */
                        androidId = Settings.System.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

                        /**
                         * 如果还是获取不到，随机
                         */
                        if (TextUtils.isEmpty(androidId)) {
                            Random random = new Random();
                            androidId = Integer.toHexString(random.nextInt())
                                    + Integer.toHexString(random.nextInt())
                                    + Integer.toHexString(random.nextInt());
                        }

                        /**
                         * 存储到内部存储
                         */
                        PrefsUtils.getPrefs(context).edit().putString("androidid", androidId).apply();

                        /**
                         * 将设备id存储到sd卡的内部存储中
                         */
                        FileUtil.writeTxtToFile(androidId, FileUtil.getRootFolder(), "/appid.txt");
                    }
                }
            }
        } catch (Exception e) {
            Logger.e(e);

            /**
             * 如果还是获取不到，随机
             */
            if (TextUtils.isEmpty(androidId)) {
                Random random = new Random();
                androidId = Integer.toHexString(random.nextInt())
                        + Integer.toHexString(random.nextInt())
                        + Integer.toHexString(random.nextInt());
                /**
                 * 存储到内部存储
                 */
                PrefsUtils.getPrefs(AppCore.getApplicationContext()).edit().putString("androidid", androidId).apply();

                /**
                 * 将设备id存储到sd卡的内部存储中
                 */
                FileUtil.writeTxtToFile(androidId, FileUtil.getRootFolder(), "/appid.txt");
            }
        }

        Logger.e("test androidId A =" + androidId);
        return androidId;

    }


    public static String getSystemHardWare() {
        return Build.HARDWARE;
    }
}
