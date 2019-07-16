package org.fungo.common_network.host;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;

/**
 * 服务器域名管理类,所有的域名获取都是通过该类来获取
 */
public class ServiceHostManager {

    private volatile static ServiceHostManager serviceHost;

    private boolean isDebug = false;

    private ServiceHostManager() {
    }

    public static ServiceHostManager getInstall() {
        if (serviceHost == null) {
            synchronized (ServiceHostManager.class) {
                if (serviceHost == null) {
                    serviceHost = new ServiceHostManager();
                }
            }
        }
        return serviceHost;
    }

    /**
     * @param hostJson
     */
    public void setServiceHostJsonData(String hostJson) {
        try {
            /**
             * 如果不是debug模式
             */
            if (!isDebug()) {
                host = JSON.parseObject(hostJson, Host.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试后台,使用不用的域名
     *
     * @param isDebug
     */
    public void setBeta(boolean isDebug) {
        if (isDebug) {

            this.isDebug = isDebug;

            /**
             * 广告测试域名
             */
            getHost().setAd_service("http://adtest.tvesou.com");

            /**
             * 评论测试域名
             */
            getHost().setComments_service("http://116.62.37.170:8021");

            /**
             * tv测试域名,为了方便测试,这里的域名跟正式域名一样
             */
            getHost().setNowtv_service("http://nowtv.yuntuds.net");

            /**
             * user的测试地址
             */
            getHost().setUser_service("http://usertest.tvesou.com");
        }
    }

    public boolean isDebug() {
        return isDebug;
    }

    public void setDebug(boolean debug) {
        isDebug = debug;
    }

    private Host host;

    private Host getHost() {
        if (host == null) {
            host = new Host();
        }
        return host;
    }

    public String getUser_service() {
        return getHost().getUser_service();
    }

    public String getA8_service() {
        return getHost().getA8_service();
    }

    public String getTrack_service() {
        return getHost().getTrack_service();
    }

    public String getProbe_service() {
        return getHost().getProbe_service();
    }

    public String getXproxy_service() {
        return getHost().getXproxy_service();
    }

    public String getComments_service() {
        return getHost().getComments_service();
    }

    public String getAd_service() {
        return getHost().getAd_service();
    }

    public String getNowtv_service() {
        return getHost().getNowtv_service();
    }

    public String getActivity_service() {
        return getHost().getActivity_service();
    }

    public String getBarrage_ws_service() {
        return getHost().getBarrage_ws_service();
    }

    public String getWs_service() {
        return getHost().getWs_service();
    }

    public String getCdn_service() {
        return getHost().getNowtv_pic_service();
    }

    /**
     * 获取域名,不包含http://
     *
     * @return
     */
    public String getNowtvServiceHost() {
        return getHost().getNowtv_servicehost();
    }

    /**
     * 获取域名,不包含http://
     *
     * @return
     */
    public String getProbServiceHost() {
        return getHost().getProbe_servicehost();
    }

    /**
     * 获取域名,不包含http://
     *
     * @return
     */
    public String getUserServiceHost() {
        return getHost().getUser_servicehost();
    }


    /**
     * 获取域名,不包含http://
     *
     * @return
     */
    public String getCdnServiceHost() {
        return getHost().getNowtv_pic_servicehost();
    }

    private static class Host {

        private final static String service_user = "http://user.yuntuds.net";
        private final static String service_a8 = "http://a8.yuntuds.net";
        private final static String service_track = "http://track1.yuntuds.net";
        private final static String service_probe = "http://probe.yuntuds.net";
        private final static String service_xproxy = "http://xproxy.yuntuds.net";
        private final static String service_comments = "http://comments.yuntuds.net";
        private final static String service_ad = "http://ad.yuntuds.net";
        private final static String service_nowtv = "http://nowtv.yuntuds.net";
        private final static String service_nowtv_pic = "http://tvnow-pic.yuntuds.net";
        private final static String service_activity = "http://activity.yuntuds.net";
        private final static String service_ws_barrage = "ws://barrage_ws.yuntuds.net/websocket";
        private final static String service_ws = "ws://ws.yuntuds.net/websocket";

        String user_service = service_user;
        String a8_service = service_a8;
        String track_service = service_track;
        String probe_service = service_probe;
        String xproxy_service = service_xproxy;
        String comments_service = service_comments;
        String ad_service = service_ad;
        String nowtv_service = service_nowtv;
        String activity_service = service_activity;
        String barrage_ws_service = service_ws_barrage;
        String ws_service = service_ws;
        String nowtv_pic_service = service_nowtv_pic;

        /**
         * 不包含http的域名
         */
        static String nowtv_servicehost;

        /**
         * 不包含http的域名
         */
        static String probe_servicehost;

        /**
         * 不包含http的域名
         */
        static String user_servicehost;

        /**
         * 不包含http的域名
         */
        static String nowtv_pic_servicehost;

        public String getUser_service() {
            if (TextUtils.isEmpty(user_service)) {
                user_service = service_user;
            }
            return user_service;
        }

        public void setUser_service(String user_service) {
            this.user_service = user_service;
        }

        public String getA8_service() {
            if (TextUtils.isEmpty(a8_service)) {
                a8_service = service_a8;
            }
            return a8_service;
        }

        public void setA8_service(String a8_service) {
            this.a8_service = a8_service;
        }

        public String getTrack_service() {
            if (TextUtils.isEmpty(track_service)) {
                track_service = service_track;
            }
            return track_service;
        }

        public void setTrack_service(String track_service) {
            this.track_service = track_service;
        }

        public String getProbe_service() {
            if (TextUtils.isEmpty(probe_service)) {
                probe_service = service_probe;
            }
            return probe_service;
        }

        public void setProbe_service(String probe_service) {
            this.probe_service = probe_service;
        }

        public String getXproxy_service() {
            if (TextUtils.isEmpty(xproxy_service)) {
                xproxy_service = service_xproxy;
            }
            return xproxy_service;
        }

        public void setXproxy_service(String xproxy_service) {
            this.xproxy_service = xproxy_service;
        }

        public String getComments_service() {
            if (TextUtils.isEmpty(comments_service)) {
                comments_service = service_comments;
            }
            return comments_service;
        }

        public void setComments_service(String comments_service) {
            this.comments_service = comments_service;
        }

        public String getAd_service() {
            if (TextUtils.isEmpty(ad_service)) {
                ad_service = service_ad;
            }
            return ad_service;
        }

        public void setAd_service(String ad_service) {
            this.ad_service = ad_service;
        }

        public String getNowtv_service() {
            if (TextUtils.isEmpty(nowtv_service)) {
                nowtv_service = service_nowtv;
            }
            return nowtv_service;
        }

        public void setNowtv_service(String nowtv_service) {
            this.nowtv_service = nowtv_service;
        }

        public String getActivity_service() {
            if (TextUtils.isEmpty(activity_service)) {
                activity_service = service_activity;
            }
            return activity_service;
        }

        public void setActivity_service(String activity_service) {
            this.activity_service = activity_service;
        }

        public String getBarrage_ws_service() {
            if (TextUtils.isEmpty(barrage_ws_service)) {
                barrage_ws_service = service_ws_barrage;
            }
            return barrage_ws_service;
        }

        public void setBarrage_ws_service(String barrage_ws_service) {
            this.barrage_ws_service = barrage_ws_service;
        }

        public String getWs_service() {
            if (TextUtils.isEmpty(ws_service)) {
                ws_service = service_ws;
            }
            return ws_service;
        }

        public void setWs_service(String ws_service) {
            this.ws_service = ws_service;
        }

        public String getNowtv_pic_service() {
            if (TextUtils.isEmpty(nowtv_pic_service)) {
                nowtv_pic_service = service_nowtv_pic;
            }
            return nowtv_pic_service;
        }

        public void setNowtv_pic_service(String nowtv_pic_service) {
            this.nowtv_pic_service = nowtv_pic_service;
        }

        /**
         * 获取域名,不包含http://
         *
         * @return
         */
        public String getNowtv_servicehost() {
            if (TextUtils.isEmpty(nowtv_servicehost)) {
                nowtv_servicehost = getNowtv_service();
                if (nowtv_service.startsWith("http://")) {
                    nowtv_servicehost = nowtv_service.replace("http://", "");
                }
                if (nowtv_service.startsWith("https://")) {
                    nowtv_servicehost = nowtv_service.replace("https://", "");
                }
            }
            return nowtv_servicehost;
        }

        /**
         * 获取域名,不包含http://
         *
         * @return
         */
        public String getProbe_servicehost() {
            if (TextUtils.isEmpty(probe_servicehost)) {
                probe_servicehost = getProbe_service();
                if (probe_servicehost.startsWith("http://")) {
                    probe_servicehost = probe_servicehost.replace("http://", "");
                }
                if (probe_servicehost.startsWith("https://")) {
                    probe_servicehost = probe_servicehost.replace("https://", "");
                }
            }
            return probe_servicehost;
        }

        /**
         * 获取域名,不包含http://
         *
         * @return
         */
        public String getUser_servicehost() {
            if (TextUtils.isEmpty(user_servicehost)) {
                user_servicehost = getUser_service();
                if (user_servicehost.startsWith("http://")) {
                    user_servicehost = user_servicehost.replace("http://", "");
                }
                if (user_servicehost.startsWith("https://")) {
                    user_servicehost = user_servicehost.replace("https://", "");
                }
            }
            return user_servicehost;
        }

        /**
         * 获取域名,不包含http://
         *
         * @return
         */
        public String getNowtv_pic_servicehost() {
            if (TextUtils.isEmpty(nowtv_pic_servicehost)) {
                nowtv_pic_servicehost = getNowtv_pic_service();
                if (nowtv_pic_servicehost.startsWith("http://")) {
                    nowtv_pic_servicehost = nowtv_pic_servicehost.replace("http://", "");
                }
                if (nowtv_pic_servicehost.startsWith("https://")) {
                    nowtv_pic_servicehost = nowtv_pic_servicehost.replace("https://", "");
                }
            }
            return nowtv_pic_servicehost;
        }

        @Override
        public String toString() {
            return "Host{" +
                    "user_service='" + user_service + '\'' +
                    ", a8_service='" + a8_service + '\'' +
                    ", track_service='" + track_service + '\'' +
                    ", probe_service='" + probe_service + '\'' +
                    ", xproxy_service='" + xproxy_service + '\'' +
                    ", comments_service='" + comments_service + '\'' +
                    ", ad_service='" + ad_service + '\'' +
                    ", nowtv_service='" + nowtv_service + '\'' +
                    ", activity_service='" + activity_service + '\'' +
                    ", barrage_ws_service='" + barrage_ws_service + '\'' +
                    ", ws_service='" + ws_service + '\'' +
                    '}';
        }
    }
}
