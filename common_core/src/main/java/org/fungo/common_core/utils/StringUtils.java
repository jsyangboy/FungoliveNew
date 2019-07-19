package org.fungo.common_core.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String Utils
 *
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2011-7-22
 */
public class StringUtils {

    public static int[][] memoizedDistance = new int[100][100];

    /**
     * is null or its length is 0 or it is made by space
     * <p>
     * <pre>
     * isBlank(null) = true;
     * isBlank(&quot;&quot;) = true;
     * isBlank(&quot;  &quot;) = true;
     * isBlank(&quot;a&quot;) = false;
     * isBlank(&quot;a &quot;) = false;
     * isBlank(&quot; a&quot;) = false;
     * isBlank(&quot;a b&quot;) = false;
     * </pre>
     *
     * @param str
     * @return if string is null or its size is 0 or it is made by space, return true, else return false.
     */
    public static boolean isBlank(String str) {
        return (str == null || str.trim().length() == 0);
    }

    /**
     * is null or its length is 0
     * <p>
     * <pre>
     * isEmpty(null) = true;
     * isEmpty(&quot;&quot;) = true;
     * isEmpty(&quot;  &quot;) = false;
     * </pre>
     *
     * @param str
     * @return if string is null or its size is 0, return true, else return false.
     */
    public static boolean isEmpty(String str) {
        return (str == null || str.length() == 0);
    }

    /**
     * null string to empty string
     * <p>
     * <pre>
     * nullStrToEmpty(null) = &quot;&quot;;
     * nullStrToEmpty(&quot;&quot;) = &quot;&quot;;
     * nullStrToEmpty(&quot;aa&quot;) = &quot;aa&quot;;
     * </pre>
     *
     * @param str
     * @return
     */
    public static String nullStrToEmpty(String str) {
        return (str == null ? "" : str);
    }

    /**
     * capitalize first letter
     * <p>
     * <pre>
     * capitalizeFirstLetter(null)     =   null;
     * capitalizeFirstLetter("")       =   "";
     * capitalizeFirstLetter("2ab")    =   "2ab"
     * capitalizeFirstLetter("a")      =   "A"
     * capitalizeFirstLetter("ab")     =   "Ab"
     * capitalizeFirstLetter("Abc")    =   "Abc"
     * </pre>
     *
     * @param str
     * @return
     */
    public static String capitalizeFirstLetter(String str) {
        if (isEmpty(str)) {
            return str;
        }

        char c = str.charAt(0);
        return (!Character.isLetter(c) || Character.isUpperCase(c)) ? str
                : new StringBuilder(str.length()).append(Character.toUpperCase(c)).append(str.substring(1)).toString();
    }

    /**
     * encoded in utf-8
     * <p>
     * <pre>
     * utf8Encode(null)        =   null
     * utf8Encode("")          =   "";
     * utf8Encode("aa")        =   "aa";
     * utf8Encode("啊啊啊啊")   = "%E5%95%8A%E5%95%8A%E5%95%8A%E5%95%8A";
     * </pre>
     *
     * @param str
     * @return
     * @throws UnsupportedEncodingException if an error occurs
     */
    public static String utf8Encode(String str) {
        if (!isEmpty(str) && str.getBytes().length != str.length()) {
            try {
                return URLEncoder.encode(str, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("UnsupportedEncodingException occurred. ", e);
            }
        }
        return str;
    }

    /**
     * encoded in utf-8, if exception, return defultReturn
     *
     * @param str
     * @param defultReturn
     * @return
     */
    public static String utf8Encode(String str, String defultReturn) {
        if (!isEmpty(str) && str.getBytes().length != str.length()) {
            try {
                return URLEncoder.encode(str, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                return defultReturn;
            }
        }
        return str;
    }

    /**
     * get innerHtml from href
     * <p>
     * <pre>
     * getHrefInnerHtml(null)                                  = ""
     * getHrefInnerHtml("")                                    = ""
     * getHrefInnerHtml("mp3")                                 = "mp3";
     * getHrefInnerHtml("&lt;a innerHtml&lt;/a&gt;")                    = "&lt;a innerHtml&lt;/a&gt;";
     * getHrefInnerHtml("&lt;a&gt;innerHtml&lt;/a&gt;")                    = "innerHtml";
     * getHrefInnerHtml("&lt;a&lt;a&gt;innerHtml&lt;/a&gt;")                    = "innerHtml";
     * getHrefInnerHtml("&lt;a href="baidu.com"&gt;innerHtml&lt;/a&gt;")               = "innerHtml";
     * getHrefInnerHtml("&lt;a href="baidu.com" title="baidu"&gt;innerHtml&lt;/a&gt;") = "innerHtml";
     * getHrefInnerHtml("   &lt;a&gt;innerHtml&lt;/a&gt;  ")                           = "innerHtml";
     * getHrefInnerHtml("&lt;a&gt;innerHtml&lt;/a&gt;&lt;/a&gt;")                      = "innerHtml";
     * getHrefInnerHtml("jack&lt;a&gt;innerHtml&lt;/a&gt;&lt;/a&gt;")                  = "innerHtml";
     * getHrefInnerHtml("&lt;a&gt;innerHtml1&lt;/a&gt;&lt;a&gt;innerHtml2&lt;/a&gt;")        = "innerHtml2";
     * </pre>
     *
     * @param href
     * @return <ul>
     * <li>if href is null, return ""</li>
     * <li>if not match regx, return source</li>
     * <li>return the last string that match regx</li>
     * </ul>
     */
    public static String getHrefInnerHtml(String href) {
        if (isEmpty(href)) {
            return "";
        }

        String hrefReg = ".*<[\\s]*a[\\s]*.*>(.+?)<[\\s]*/a[\\s]*>.*";
        Pattern hrefPattern = Pattern.compile(hrefReg, Pattern.CASE_INSENSITIVE);
        Matcher hrefMatcher = hrefPattern.matcher(href);
        if (hrefMatcher.matches()) {
            return hrefMatcher.group(1);
        }
        return href;
    }

    /**
     * process special char in html
     * <p>
     * <pre>
     * htmlEscapeCharsToString(null) = null;
     * htmlEscapeCharsToString("") = "";
     * htmlEscapeCharsToString("mp3") = "mp3";
     * htmlEscapeCharsToString("mp3&lt;") = "mp3<";
     * htmlEscapeCharsToString("mp3&gt;") = "mp3\>";
     * htmlEscapeCharsToString("mp3&amp;mp4") = "mp3&mp4";
     * htmlEscapeCharsToString("mp3&quot;mp4") = "mp3\"mp4";
     * htmlEscapeCharsToString("mp3&lt;&gt;&amp;&quot;mp4") = "mp3\<\>&\"mp4";
     * </pre>
     *
     * @param source
     * @return
     */
    public static String htmlEscapeCharsToString(String source) {
        return StringUtils.isEmpty(source) ? source : source.replaceAll("&lt;", "<").replaceAll("&gt;", ">")
                .replaceAll("&amp;", "&").replaceAll("&quot;", "\"");
    }

    /**
     * transform half width char to full width char
     * <p>
     * <pre>
     * fullWidthToHalfWidth(null) = null;
     * fullWidthToHalfWidth("") = "";
     * fullWidthToHalfWidth(new String(new char[] {12288})) = " ";
     * fullWidthToHalfWidth("！＂＃＄％＆) = "!\"#$%&";
     * </pre>
     *
     * @param s
     * @return
     */
    public static String fullWidthToHalfWidth(String s) {
        if (isEmpty(s)) {
            return s;
        }

        char[] source = s.toCharArray();
        for (int i = 0; i < source.length; i++) {
            if (source[i] == 12288) {
                source[i] = ' ';
                // } else if (source[i] == 12290) {
                // source[i] = '.';
            } else if (source[i] >= 65281 && source[i] <= 65374) {
                source[i] = (char) (source[i] - 65248);
            } else {
                source[i] = source[i];
            }
        }
        return new String(source);
    }

    /**
     * transform full width char to half width char
     * <p>
     * <pre>
     * halfWidthToFullWidth(null) = null;
     * halfWidthToFullWidth("") = "";
     * halfWidthToFullWidth(" ") = new String(new char[] {12288});
     * halfWidthToFullWidth("!\"#$%&) = "！＂＃＄％＆";
     * </pre>
     *
     * @param s
     * @return
     */
    public static String halfWidthToFullWidth(String s) {
        if (isEmpty(s)) {
            return s;
        }

        char[] source = s.toCharArray();
        for (int i = 0; i < source.length; i++) {
            if (source[i] == ' ') {
                source[i] = (char) 12288;
                // } else if (source[i] == '.') {
                // source[i] = (char)12290;
            } else if (source[i] >= 33 && source[i] <= 126) {
                source[i] = (char) (source[i] + 65248);
            } else {
                source[i] = source[i];
            }
        }
        return new String(source);
    }

    public static Integer parseInt(String s) {
        if (isBlank(s)) return 0;
        else {
            s = s.trim();
            try {
                return Integer.parseInt(s);
            } catch (Exception e) {
                return 0;
            }
        }
    }

    public static Float parseFloat(String s) {
        if (isBlank(s)) return 0f;
        else {
            s = s.trim();
            try {
                return Float.parseFloat(s);
            } catch (Exception e) {
                return 0f;
            }
        }
    }

    public static boolean parseBoolean(String s) {
        if (isBlank(s)) return false;
        else {
            s = s.trim();
            try {
                return Boolean.parseBoolean(s);
            } catch (Exception e) {
                return false;
            }
        }
    }

    public static Long parseLong(String s) {
        if (isBlank(s)) return 0l;
        else {
            s = s.trim();
            try {
                return Long.parseLong(s);
            } catch (Exception e) {
                return 0l;
            }
        }
    }

    /**
     * 根据秒速获取时间格式
     */
    public static String gennerTime(int totalSeconds) {
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    /**
     * 判断两个字符串的相似度
     *
     * @param mContext
     * @param tvid
     * @param content
     * @return
     */
    public static boolean isDuplicate(Context mContext, String tvid, String content) {
        SharedPreferences prefs = PrefsUtils.getPrefs(mContext);
        String lastContent = prefs.getString("live_" + tvid, "");

        clearDistance();
        int distance = calculateStringDistance(content, 0, content.length() - 1, lastContent, 0, lastContent.length() - 1);

        boolean isDuplicate = false;
        // 长度小于10的不做过滤（防止误伤，如比赛连续的发“加油”，是不应该判重的）
        if (content.length() <= 10) {
            isDuplicate = false;
        } else if (content.length() > 10 && distance < 3) {
            isDuplicate = true;
        } else if (content.length() > 25 && distance < 5) {
            isDuplicate = true;
        } else if (content.length() > 40 && distance < 8) {
            isDuplicate = true;
        }
        prefs.edit().putString("live_" + tvid, content).apply();

        return isDuplicate;
    }


    public static void clearDistance() {
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                memoizedDistance[i][j] = -1;
            }
        }
    }


    /**
     * 计算两个字符串的相似程度
     *
     * @param strA
     * @param pABegin
     * @param pAEnd
     * @param strB
     * @param pBBegin
     * @param pBEnd
     * @return
     */
    public static int calculateStringDistance(String strA, int pABegin, int pAEnd, String strB, int pBBegin, int pBEnd) {

        if (memoizedDistance[pABegin][pBBegin] >= 0) {
            return memoizedDistance[pABegin][pBBegin];
        }

        if (pABegin > pAEnd) {
            if (pBBegin > pBEnd) {
                memoizedDistance[pABegin][pBBegin] = 0;
                return 0;
            } else {
                int temp = pBEnd - pBBegin + 1;
                memoizedDistance[pABegin][pBBegin] = temp;
                return temp;
            }
        }

        if (pBBegin > pBEnd) {
            if (pABegin > pAEnd) {
                memoizedDistance[pABegin][pBBegin] = 0;
                return 0;
            } else {
                int temp = pAEnd - pABegin + 1;
                memoizedDistance[pABegin][pBBegin] = temp;
                return temp;
            }
        }


        if (strA.charAt(pABegin) == strB.charAt(pBBegin)) {
            int temp = calculateStringDistance(strA, pABegin + 1, pAEnd, strB, pBBegin + 1, pBEnd);
            memoizedDistance[pABegin][pBBegin] = temp;
            return temp;
        } else {
            int t1 = calculateStringDistance(strA, pABegin, pAEnd, strB, pBBegin + 1, pBEnd);
            int t2 = calculateStringDistance(strA, pABegin + 1, pAEnd, strB, pBBegin, pBEnd);
            int t3 = calculateStringDistance(strA, pABegin + 1, pAEnd, strB, pBBegin + 1, pBEnd);
            int temp = minValue(t1, t2, t3) + 1;
            memoizedDistance[pABegin][pBBegin] = temp;
            return temp;
        }

    }


    public static int minValue(int t1, int t2, int t3) {
        int min = t1;
        if (t1 > t2) {
            min = t2;
        }
        if (min > t3) {
            min = t3;
        }
        return min;
    }
}
