package org.fungo.feature_player_live.bean;

import java.util.List;

/**
 * Created by Veesong
 */
public class SourceItem {

    /**
     * source : ["tv]ow://http://nowtv.yuntuds.net:8000/p/get_current_carousel?tvId=100888","tvnow://http://nowtv.yuntuds.net:8000/p/get_current_carousel?tvId=100888","http://www.mgtv.com//v/0/41661/f..."]
     * source_labels : [{"tag":"普通线路","score":5,"code":"","from":"http://www.mgtv.com//v/0/41661/f...","mark":"0"},{"tag":"普通线路","score":5,"code":"","from":"http://www.mgtv.com//v/0/41661/f...","mark":"0"},{"tag":"普通线路","score":5,"code":"","from":"网络","mark":"0"}]
     * tvid : 100888
     * back_url : yuntulunbo
     * path :
     */

    private int tvid;
    private String back_url;
    private String path;
    private List<String> source;
    /**
     * tag : 普通线路
     * score : 5
     * code :
     * from : http://www.mgtv.com//v/0/41661/f...
     * mark : 0
     */

    private List<SourceLabelsBean> source_labels;

    public int getTvid() {
        return tvid;
    }

    public void setTvid(int tvid) {
        this.tvid = tvid;
    }

    public String getBack_url() {
        return back_url;
    }

    public void setBack_url(String back_url) {
        this.back_url = back_url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<String> getSource() {
        return source;
    }

    public void setSource(List<String> source) {
        this.source = source;
    }

    public List<SourceLabelsBean> getSource_labels() {
        return source_labels;
    }

    public void setSource_labels(List<SourceLabelsBean> source_labels) {
        this.source_labels = source_labels;
    }

    public static class SourceLabelsBean {
        private String tag;
        private int score;
        private String code;
        private String from;
        private int mark;
        private boolean isSupportScreenTV;

        public SourceLabelsBean() {

        }

        public SourceLabelsBean(String tag) {
            this.tag = tag;
        }

        public SourceLabelsBean(String tag, String from) {
            this.tag = tag;
            this.from = from;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public int getMark() {
            return mark;
        }

        public void setMark(int mark) {
            this.mark = mark;
        }

        public boolean getIsSupportScreenTV() {
            return isSupportScreenTV;
        }

        public void setIsSupportScreenTV(boolean supportScreenTV) {
            isSupportScreenTV = supportScreenTV;
        }
    }
}
