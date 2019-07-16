package org.fungo.common_db;


import com.tencent.mmkv.MMKV;

import org.fungo.common_core.AppCore;
import org.fungo.common_core.utils.Logger;

/**
 * @author yqy
 * @create 19-7-16
 * @Describe 数据库辅助工具, 这里使用了,mmkv来进行存储,不使用数据库
 */
public class DbUtils {

    private static final String TAG = "DbUtils";

    private static volatile DbUtils dbUtils;

    private DbUtils() {
        String rootDir = MMKV.initialize(AppCore.getApplication());
        Logger.i(TAG + " rootDir=" + rootDir);
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
        if (key == null) {
            Logger.e(TAG + " putString key == " + key);
            return;
        }
        Logger.i(TAG + " putString key=" + key + " value=" + value);
        MMKV kv = getMMKV();
        if (kv == null) {
            return;
        }
        kv.encode(key, value);
    }

    public void putBoolean(String key, Boolean value) {
        if (key == null) {
            Logger.e(TAG + " putBoolean key == " + key);
            return;
        }

        Logger.i(TAG + " putBoolean key=" + key + " value=" + value);
        MMKV kv = getMMKV();
        if (kv == null) {
            return;
        }
        kv.encode(key, value);
    }

    public void putInt(String key, Integer value) {
        if (key == null) {
            Logger.e(TAG + " putInt key == " + key);
            return;
        }
        Logger.i(TAG + " putInt key=" + key + " value=" + value);
        MMKV kv = getMMKV();
        if (kv == null) {
            return;
        }
        kv.encode(key, value);
    }

    public void putLong(String key, Long value) {
        if (key == null) {
            Logger.e(TAG + " putLong key == " + key);
            return;
        }
        Logger.i(TAG + " putLong key=" + key + " value=" + value);
        MMKV kv = getMMKV();
        if (kv == null) {
            return;
        }
        kv.encode(key, value);
    }

    public String getString(String key) {
        if (key == null) {
            Logger.e(TAG + " getString key == " + key);
            return null;
        }
        MMKV kv = getMMKV();
        if (kv == null) {
            return null;
        }
        return kv.decodeString(key, null);
    }

    public boolean getBoolean(String key) {
        if (key == null) {
            Logger.e(TAG + " getBoolean key == " + key);
            return false;
        }
        MMKV kv = getMMKV();
        if (kv == null) {
            return false;
        }
        return kv.decodeBool(key, false);
    }

    public int getInt(String key) {
        if (key == null) {
            Logger.e(TAG + " getInt key == " + key);
            return -1;
        }
        MMKV kv = getMMKV();
        if (kv == null) {
            return -1;
        }
        return kv.decodeInt(key, -1);
    }

    public long getLong(String key) {
        if (key == null) {
            Logger.e(TAG + " getLong key == " + key);
            return -1;
        }
        MMKV kv = getMMKV();
        if (kv == null) {
            return -1;
        }
        return kv.decodeLong(key, -1);
    }

    private MMKV getMMKV() {
        MMKV kv = null;
        try {
            kv = MMKV.defaultMMKV();
        } catch (Exception e) {
            e.printStackTrace();
            Logger.e(TAG + " getMMKV = error");
        }
        return kv;
    }
}
