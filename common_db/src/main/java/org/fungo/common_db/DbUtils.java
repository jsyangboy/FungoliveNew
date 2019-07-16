package org.fungo.common_db;


import com.tencent.mmkv.MMKV;

import org.fungo.common_core.AppCore;
import org.fungo.common_core.utils.Logger;

/**
 * @author yqy
 * @create 19-7-16
 * @Describe 数据库辅助工具
 */
public class DbUtils {

    private static final String TAG = "DbUtils";

    private static volatile DbUtils dbUtils;

    private DbUtils() {
        String rootDir = MMKV.initialize(AppCore.getApplication());
        Logger.e(TAG + " rootDir=" + rootDir);
    }

    public static DbUtils getInstance() {
        if (dbUtils == null) {
            synchronized (DbUtils.class) {
                if (dbUtils == null) {
                    dbUtils = new DbUtils();
                }
            }
        }
        return dbUtils;
    }

    public void putString(String key, String value) {
        MMKV kv = MMKV.defaultMMKV();
        kv.encode(key, value);
    }

    public void putBoolean(String key, Boolean value) {
        MMKV kv = MMKV.defaultMMKV();
        kv.encode(key, value);
    }

    public void putInt(String key, Integer value) {
        MMKV kv = MMKV.defaultMMKV();
        kv.encode(key, value);
    }

    public void putLong(String key, Boolean value) {
        MMKV kv = MMKV.defaultMMKV();
        kv.encode(key, value);
    }

    public String getString(String key) {
        MMKV kv = MMKV.defaultMMKV();
        return kv.decodeString(key, "");
    }

    public boolean getBoolean(String key) {
        MMKV kv = MMKV.defaultMMKV();
        return kv.decodeBool(key, false);
    }

    public int getInt(String key) {
        MMKV kv = MMKV.defaultMMKV();
        return kv.decodeInt(key, -1);
    }

    public long getLong(String key) {
        MMKV kv = MMKV.defaultMMKV();
        return kv.decodeLong(key, -1);
    }

}
