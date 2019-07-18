package org.fungo.common_core.utils;

import android.util.Base64;

/**
 * @author yqy
 * @create 19-7-18
 * @Describe 字符串解码工具
 */
public class DecodeUtils {

    /**
     * 源数据的解码
     *
     * @param content
     * @return
     */
    public static String resoveSourceBase64String(String content) {
        String contentcut = null;
        String contentbefore = null;
        String contentafter = null;
        StringBuffer buffer = new StringBuffer();
        byte[] result = null;
        String resolveContent = null;
        try {
            contentcut = content.substring(2);
            contentbefore = contentcut.substring(0, 20);
            contentafter = contentcut.substring(21);
            buffer.append(contentbefore);
            buffer.append(contentafter);
            result = Base64.decode(buffer.toString(), Base64.DEFAULT);
            resolveContent = new String(result);
        } catch (Exception e) {
            return null;
        }

        return resolveContent;
    }

    /**
     * epg的数据解码
     *
     * @param content
     * @return
     */
    public static String resoveEPGBase64String(String content) {
        byte[] result;
        String resolveContent = null;
        try {
            StringBuffer buffer = new StringBuffer(content);
            buffer.delete(0, 2);
            buffer.deleteCharAt(20);
            result = Base64.decode(buffer.toString(), Base64.DEFAULT);
            resolveContent = new String(result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resolveContent;
    }

}
