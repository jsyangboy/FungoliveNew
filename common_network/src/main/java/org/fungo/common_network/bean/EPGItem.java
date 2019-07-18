/**
 * Copyright (c) 2013 NewSenseNetwork.Co.Ltd.
 * <p>
 * All rights reserved.
 */
package org.fungo.common_network.bean;


public class EPGItem implements ModeItem, MultiItemEntity {

    private int tv_id;

    private String tv_name;

    private String title;

    private String epg_title;

    private long start_time;

    private long end_time;

    private int percent;

    private String frameurl;

    private String tags;

    private double rating;

    private String tv_name_py;

    private String tv_name_py_abb;

    private int frequency;

    private String iconUrl;

    private int channelType;

    //预留字段
    private int data1;

    //预留字段
    private int data2;

    /*
     * 回看节目单地址
     */
    private String string1;

    /*
     * 标记是否跳到web页
     */
    private String string2;

    // 记录刷新时间
    private long long1;

    //预留字段
    private long long2;

    // 专题子分类
    private String category;

    private boolean shouldRefresh = false;
    private int type;

    private int topPos;

    public EPGItem() {

    }

    public EPGItem(String tv_name, int tv_id, String title, String epg_title,
                   long start_time, long end_time, int percent, String frameurl,
                   String tags, double rating, String tv_name_py, String tv_name_py_abb,
                   String icon_url, int frequency, int type, String back_url, String online_url) {
        this.tv_name = tv_name;
        this.tv_id = tv_id;
        this.title = title;
        this.epg_title = epg_title;
        this.start_time = start_time;
        this.end_time = end_time;
        this.percent = percent;
        this.frameurl = frameurl;
        this.tags = tags;
        this.rating = rating;
        this.tv_name_py = tv_name_py;
        this.tv_name_py_abb = tv_name_py_abb;
        this.frequency = frequency;
        this.iconUrl = icon_url;
        this.channelType = type;
        this.string1 = back_url;
        this.string2 = online_url;
    }

    public EPGItem(String tv_name, int tv_id, String title, String epg_title,
                   long start_time, long end_time, int percent, String frameurl,
                   String tags, double rating, String tv_name_py, String tv_name_py_abb,
                   String icon_url, int frequency, int type, String back_url, String online_url, int data1, int data2) {
        this.tv_name = tv_name;
        this.tv_id = tv_id;
        this.title = title;
        this.epg_title = epg_title;
        this.start_time = start_time;
        this.end_time = end_time;
        this.percent = percent;
        this.frameurl = frameurl;
        this.tags = tags;
        this.rating = rating;
        this.tv_name_py = tv_name_py;
        this.tv_name_py_abb = tv_name_py_abb;
        this.frequency = frequency;
        this.iconUrl = icon_url;
        this.channelType = type;
        this.string1 = back_url;
        this.string2 = online_url;
        this.data1 = data1;
        this.data2 = data2;
    }

    public EPGItem(String tags) {
        this.tags = tags;
    }

    /**
     * @return the tvName
     */
    public String getTvName() {
        return tv_name;
    }

    /**
     * @param tvName the tvName to set
     */
    public void setTvName(String tvName) {
        this.tv_name = tvName;
    }

    /**
     * @return the tv_id
     */
    public int getTv_id() {
        return tv_id;
    }

    /**
     * @param tv_id the tv_id to set
     */
    public void setTv_id(int tv_id) {
        this.tv_id = tv_id;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the epg_title
     */
    public String getEpg_title() {
        return epg_title;
    }

    /**
     * @param epg_title the epg_title to set
     */
    public void setEpg_title(String epg_title) {
        this.epg_title = epg_title;
    }

    /**
     * @return the start_time
     */
    public long getStart_time() {
        return start_time;
    }

    /**
     * @param start_time the start_time to set
     */
    public void setStart_time(long start_time) {
        this.start_time = start_time;
    }

    /**
     * @return the end_time
     */
    public long getEnd_time() {
        return end_time;
    }

    /**
     * @param end_time the end_time to set
     */
    public void setEnd_time(long end_time) {
        this.end_time = end_time;
    }

    /**
     * @return the percent
     */
    public int getPercent() {
        return percent;
    }

    /**
     * @param percent the percent to set
     */
    public void setPercent(int percent) {
        this.percent = percent;
    }


    /**
     * @return the frameurl
     */
    public String getFrameurl() {
        return frameurl;
    }

    /**
     * @param frameurl the frameurl to set
     */
    public void setFrameurl(String frameurl) {
        this.frameurl = frameurl;
    }


    /**
     * @return the shouldRefresh
     */
    public boolean shouldRefresh() {
        return shouldRefresh;
    }

    /**
     * @param shouldRefresh the shouldRefresh to set
     */
    public void setShouldRefresh(boolean shouldRefresh) {
        this.shouldRefresh = shouldRefresh;
    }

    @Override
    public boolean equals(Object o) {
        String title2 = ((EPGItem) o).getTitle();
        int percent2 = ((EPGItem) o).getPercent();
        if (title.equals(title2) && percent == percent2) {
            return true;
        } else {
            return false;
        }
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    @Override
    public int getType() {
        return type;

    }

    @Override
    public void setType(int type) {
        this.type = type;
    }

    public String getTv_name_py() {
        return tv_name_py;
    }

    public void setTv_name_py(String tv_name_py) {
        this.tv_name_py = tv_name_py;
    }

    public String getTv_name_py_abb() {
        return tv_name_py_abb;
    }

    public void setTv_name_py_abb(String tv_name_py_abb) {
        this.tv_name_py_abb = tv_name_py_abb;
    }


    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String icon_url) {
        this.iconUrl = icon_url;
    }

    @Override
    public String getProvinceName() {
        return "";
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    @Override
    public String getStream_url() {
        return null;
    }

    @Override
    public String getSecond_url() {
        return null;
    }

    @Override
    public int getChannelType() {
        return 1;
    }

    @Override
    public String getPath() {
        return "";
    }

    public String getBackUrl() {
        return string1;
    }

    public void setBackUrl(String back_url) {
        this.string1 = back_url;
    }

    public String getOnline_url() {
        return string2;
    }

    public void setOnline_url(String online_url) {
        this.string2 = online_url;
    }

    public int getData1() {
        return data1;
    }

    public void setData1(int data1) {
        this.data1 = data1;
    }

    public int getData2() {
        return data2;
    }

    public void setData2(int data2) {
        this.data2 = data2;
    }

    public long getLong1() {
        return long1;
    }

    public void setLong1(long long1) {
        this.long1 = long1;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int itemType;

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    @Override
    public int hashCode() {
        return tv_name.hashCode();
    }

    public int getTopPos() {
        return topPos;
    }

    public void setTopPos(int topPos) {
        this.topPos = topPos;
    }

    @Override
    public String toString() {
        return "EPGItem{" +
                "tv_id=" + tv_id +
                ", tv_name='" + tv_name + '\'' +
                ", title='" + title + '\'' +
                ", epg_title='" + epg_title + '\'' +
                ", start_time=" + start_time +
                ", end_time=" + end_time +
                ", percent=" + percent +
                ", frameurl='" + frameurl + '\'' +
                ", tags='" + tags + '\'' +
                ", rating=" + rating +
                ", tv_name_py='" + tv_name_py + '\'' +
                ", tv_name_py_abb='" + tv_name_py_abb + '\'' +
                ", frequency=" + frequency +
                ", iconUrl='" + iconUrl + '\'' +
                ", channelType=" + channelType +
                ", data1=" + data1 +
                ", data2=" + data2 +
                ", string1='" + string1 + '\'' +
                ", string2='" + string2 + '\'' +
                ", long1=" + long1 +
                ", long2=" + long2 +
                ", category='" + category + '\'' +
                ", shouldRefresh=" + shouldRefresh +
                ", type=" + type +
                ", topPos=" + topPos +
                ", itemType=" + itemType +
                '}';
    }
}
