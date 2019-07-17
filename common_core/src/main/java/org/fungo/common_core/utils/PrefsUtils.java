/**
 * Copyright (c) 2013 NewSenceNetwork.Co.Ltd.
 * <p>
 * All rights reserved.
 */
package org.fungo.common_core.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author xutao
 */
public class PrefsUtils {

    public static String SHAREPREFERENCE_NAME = "org.fungo.fungolive";
    public static String SETTINGSPREFERENCE_NAME = "org.fungo.fungolive_preferences";
    public static SharedPreferences prefs;
    public static SharedPreferences settingPrefs;

    private static String KEY_LOGIN_REWARD_DIALOG_TIME = "KEY_LOGIN_REWARD_DIALOG_TIME";
    public static String KEY_XIAO_AI_DAN_MU_TIME = "KEY_XIAO_AI_DAN_MU_TIME";
    public static String KEY_HARD_CODEC = "KEY_HARD_CODEC";
    public static String KEY_CUSTOM_NEW_SIGN_CHANNEL = "KEY_CUSTOM_NEW_SIGN_CHANNEL";

    public static final int HARD_CODEC_INIT = 0;
    public static final int HARD_CODEC_ENABLE = 1;
    public static final int HARD_CODEC_DISABLE = 2;


    public static SharedPreferences getPrefs(Context context) {
        if (prefs == null) {
            return prefs = context.getSharedPreferences(PrefsUtils.SHAREPREFERENCE_NAME, Activity.MODE_PRIVATE);
        } else {
            return prefs;
        }
    }

    public static SharedPreferences getSettingPrefs(Context context) {
        if (settingPrefs == null) {
            return settingPrefs = context.getSharedPreferences(PrefsUtils.SETTINGSPREFERENCE_NAME, Activity.MODE_PRIVATE);
        } else {
            return settingPrefs;
        }
    }

    public static boolean isExceedTime(Context context, String key, long gap) {
        long now = System.currentTimeMillis();

        long lastTime = getPrefs(context).getLong(key, 0);

        return now - lastTime > gap;
    }


    /**
     * 这个方法用来限制弹幕显示的时间
     *
     * @param context
     */
    public static void saveXiaoAiDanmuVisiableTime(Context context) {
        getPrefs(context).edit().putLong(KEY_XIAO_AI_DAN_MU_TIME, System.currentTimeMillis()).apply();
    }

    /**
     * 用于判断小爱的弹幕是否可以显示了
     *
     * @param context
     * @param gap
     * @return
     */
    public static boolean isShowXiaoAiDanmu(Context context, long gap) {
        boolean isShow = false;
        long now = System.currentTimeMillis();

        long lastTime = getPrefs(context).getLong(KEY_XIAO_AI_DAN_MU_TIME, 0);

        if (lastTime == 0) {//说明没有设置过，可以显示
            isShow = true;
        } else {
            if (now - lastTime > gap) { //大于两小时可以显示
                isShow = true;
            }
        }
        return isShow;
    }


    /**
     * 通过匹配系统芯片信息来决定是否使用硬解码
     * 这里主要是判断了华为芯片
     *
     * @param context
     * @return
     */
    public static int isHardCodec(Context context) {
        SharedPreferences prefs = getPrefs(context);
        int codec = prefs.getInt(KEY_HARD_CODEC, HARD_CODEC_INIT);
        if (codec == HARD_CODEC_INIT) {
            boolean enableMob = false;
            Set<String> set = new HashSet<>(Arrays.asList("kirin", "hi3660", "hi3650", "hi6250"));
            for (String s : set) {
                if (!TextUtils.isEmpty(Utils.getSystemHardWare()) && Utils.getSystemHardWare().contains(s)) {
                    enableMob = true;
                    break;
                }
            }
            codec = enableMob ? HARD_CODEC_ENABLE : HARD_CODEC_DISABLE;
            prefs.edit().putInt(KEY_HARD_CODEC, codec).apply();
        }
        return codec;
    }


    /**
     * 根据芯片字符串来匹配是否需要硬件
     *
     * @param context
     * @param matchChip 芯片信息 例如 {"hardcode":["kirin","hi3660","hi3650","hi6250"]}
     * @return
     */
    public static int isHardCodec(Context context, String matchChip) {
        SharedPreferences prefs = getPrefs(context);
        int codec = prefs.getInt(KEY_HARD_CODEC, HARD_CODEC_INIT);
        boolean enableMob = false;

        final String systemHadWare = Utils.getSystemHardWare();
        if (!TextUtils.isEmpty(matchChip) && !TextUtils.isEmpty(systemHadWare)) {
            try {
                /**
                 * 根据客户端配置的来进行匹配
                 */
                JSONObject jsonObject = JSONObject.parseObject(matchChip);
                JSONArray jsonArray = jsonObject.getJSONArray("hardcode");

                for (int i = 0; i < jsonArray.size(); i++) {
                    String code = jsonArray.getString(i);

                    /**
                     * 如果相互包含对应内容，则默认开始硬解码
                     */
                    if (code.contains(systemHadWare) || systemHadWare.contains(code)) {
                        enableMob = true;
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        final int temp = enableMob ? HARD_CODEC_ENABLE : HARD_CODEC_DISABLE;
        if (codec != temp) {
            codec = temp;
            prefs.edit().putInt(KEY_HARD_CODEC, codec).apply();
        }
        return codec;
    }
}
