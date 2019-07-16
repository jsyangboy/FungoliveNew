package org.fungo.common_network.host;

public class ServiceHostApiManager {

    /**
     * 获取a8比赛列表的域名
     *
     * @return
     */
    public final static String getA8GameListApi() {
        return ServiceHostManager.getInstall().getA8_service() + "/v3/game/list";
    }

    /**
     * 获取a8比赛分数域名
     *
     * @return
     */
    public final static String getA8GameScoreApi() {
        return ServiceHostManager.getInstall().getA8_service() + "/v2/game/score";
    }

    /**
     * 获取a8体育最新赛程接口
     * 原来是https://news-xp2.api.a8sport.com/v2/hot/main后面部分机型会出现认证问题
     * 这里方便起见直接使用http来进行访问
     *
     * @return
     */
    public final static String getA8HotApi() {
        return "http://news-xp2.api.a8sport.com/v2/hot/main";
    }

    /**
     * 统计播放等待时间的上报接口host
     *
     * @return
     */
    public final static String getRecordSourceTimeApi() {
        return ServiceHostManager.getInstall().getProbe_service() + "/record_source_time";
    }

    /**
     * 用处不详,已经不使用了
     *
     * @return
     */
    public final static String getProbeC1Api() {
        return ServiceHostManager.getInstall().getProbe_service() + "/probe/c1.php?key=6_";
    }

    /**
     * 增加评论域名
     *
     * @return
     */
    public final static String getCommentAddApi() {
        return ServiceHostManager.getInstall().getComments_service() + "/comment/add";
    }

    /**
     * 短视频评论
     *
     * @return
     */
    public final static String getShortVideoCommentAddApi() {
        return getCommentAddApi();
    }

    /**
     * 小视频评论
     *
     * @return
     */
    public final static String getSmallVideoCommentAddApi() {
        return getCommentAddApi();
    }

    /**
     * 获取短视频评论列表域名
     *
     * @return
     */
    public final static String getCommentListApi() {
        return ServiceHostManager.getInstall().getComments_service() + "/comment/newPageList";
    }

    /**
     * 获取频道评论域名
     *
     * @return
     */
    public final static String getCommentV2Api() {
        return ServiceHostManager.getInstall().getComments_service() + "/comment/newPageV2";
    }

    /**
     * 获取小视频评论
     *
     * @return
     */
    public final static String getSmallVideoCommentListApi() {
        return ServiceHostManager.getInstall().getComments_service() + "/comment/newPageList";
    }

    /**
     * 获取任务链接数据
     *
     * @return
     */
    public final static String getTaskGridDatas() {
        return ServiceHostManager.getInstall().getNowtv_service() + "/v3/find_page_second_half_conf_new.php";
    }


    /**
     * 删除评论
     */
    public final static String getCommentDeleteApi() {
        return ServiceHostManager.getInstall().getComments_service() + "/comment/del";
    }


    /**
     * 举报评论
     */
    public final static String getCommentReportApi() {
        return ServiceHostManager.getInstall().getComments_service() + "/comment/report";
    }


    /**
     * 评论点赞
     */
    public final static String getCommentApproveApi() {
        return ServiceHostManager.getInstall().getComments_service() + "/comment/approve";
    }


    /**
     * 获取广告配置的域名
     * 配合新的后台，根据对应的广告位置进行对应的广告配置获取
     *
     * @return
     */
    public final static String getAdQueryApi() {
        return ServiceHostManager.getInstall().getAd_service() + "/ad/query";
    }

    /**
     * 广告统计上报的域名
     * 配置新的后台，对应广告展示，点击次数统计数据的上报
     *
     * @return
     */
    public final static String getAdReportApi() {
        return ServiceHostManager.getInstall().getAd_service() + "/ad/report";
    }

    /**
     * 分享频道的时候，需要请求服务器获取二维码图片地址
     *
     * @return
     */
    public final static String getShareQrcodeApi() {
        return ServiceHostManager.getInstall().getActivity_service() + "/share/get_share_qr";
    }

    /**
     * 扫描二维码后，需求请求服务器获取对应的信息
     *
     * @return
     */
    public final static String getShareSourceApi() {
        return ServiceHostManager.getInstall().getActivity_service() + "/share/get_source";
    }

    /**
     * 分享链接文案
     */
    public final static String getShareLinkTextApi() {
        return ServiceHostManager.getInstall().getActivity_service() + "/share/get_share_text";
    }


    /**
     * 实时频道直播间
     *
     * @return
     */
    public final static String getLiveWebSocketApi() {
        return ServiceHostManager.getInstall().getBarrage_ws_service();
    }

    /**
     * 播放错误上报
     *
     * @return
     */
    public final static String getProbeErrorApi() {
        return ServiceHostManager.getInstall().getProbe_service() + "/record_source_error";
    }

    /**
     * 播放失败
     *
     * @return
     */
    public final static String getProbeDeadApi() {
        return ServiceHostManager.getInstall().getProbe_service() + "/record_source_dead";
    }

    /**
     * 上报
     *
     * @return
     */
    public final static String getProbeActiviateApi() {
        return ServiceHostManager.getInstall().getProbe_service() + "/activate";
    }

    /**
     * 回看播放信息上包
     *
     * @return
     */
    public final static String getProbeVodApi() {
        return ServiceHostManager.getInstall().getProbe_service() + "/vod_probe.php";
    }

    /**
     * @return
     */
    public final static String getProbeGetProfile1Api() {
        return ServiceHostManager.getInstall().getProbe_service() + "/probe/get_profile1.php";
    }


    /**
     * 短视频tab 获取专题信息
     *
     * @return
     */
    public final static String getShortVideoTabApi() {
        return ServiceHostManager.getInstall().getUser_service() + "/v3/vod/video_cate_list";
    }

    /**
     * 注册获取验证码
     *
     * @return
     */
    public final static String getAuthorCode() {
        return ServiceHostManager.getInstall().getUser_service() + "/register_get_valid_number";
    }

    /**
     * 校验验证码
     *
     * @return
     */
    public final static String checkAuthorCode() {
        return ServiceHostManager.getInstall().getUser_service() + "/v3/account/validate_reg_code";
    }

    /**
     * 注册
     *
     * @return
     */
    public final static String register() {
        return ServiceHostManager.getInstall().getUser_service() + "/register";
    }

    /**
     * 获取任务
     *
     * @return
     */
    public final static String loadTask() {
        return ServiceHostManager.getInstall().getUser_service() + "/v3/task/detail_list.json";
    }

    /**
     * 获取今日的云币奖励
     *
     * @return
     */
    public final static String loadTodyGain() {
        return ServiceHostManager.getInstall().getUser_service() + "/v3/action/get_sign_in_status";
    }

    /**
     * 修改用户详情
     *
     * @return
     */
    public final static String updateUserInfo() {
        return ServiceHostManager.getInstall().getUser_service() + "/v3/account/update_detail";
    }


    /**
     * 小视频收藏
     *
     * @return
     */
    public final static String setSmallVideoCollect() {
        return ServiceHostManager.getInstall().getUser_service() + "/v3/vod/collect";
    }

    /**
     * 小视频取消收藏
     *
     * @return
     */
    public final static String cancelSmallVideoCollect() {
        return ServiceHostManager.getInstall().getUser_service() + "/v3/vod/collect_cancel";
    }


    /**
     * 评论回复列表
     *
     * @return
     */
    public final static String getReplyMessageApi() {
        return ServiceHostManager.getInstall().getUser_service() + "/get_user_message";
    }

    /**
     * 私信， 请求源帮助;(在节目被屏蔽时调用)
     *
     * @return
     */
    public final static String getSourceHelpApi() {
        return ServiceHostManager.getInstall().getUser_service() + "/v3/help/get_source_help";
    }

    /**
     * 获取用户私信
     *
     * @return
     */
    public final static String getUserMessageApi() {
        return ServiceHostManager.getInstall().getUser_service() + "/get_user_message";
    }

    /**
     * 标记信息为已读
     *
     * @return
     */
    public final static String getMessageMarkReadApi() {
        return ServiceHostManager.getInstall().getUser_service() + "/message/mark_read";
    }

    /**
     * 世界杯信息获取地址
     *
     * @return
     */
    public final static String getWorldCupMessageApi() {
        return "http://svc.api.a8sport.com/v3/schedule/gameList";//
    }

    /**
     * 预约发私信请求源帮助;
     *
     * @return
     */
    public final static String getSubSourceHelpApi() {
        return ServiceHostManager.getInstall().getUser_service() + "/v3/help/sub_source_help";
    }

    /**
     * 单个视频详情
     *
     * @return
     */
    public final static String getShortVideoInfoApi() {
        return ServiceHostManager.getInstall().getUser_service() + "/v3/vod/get_video_info_login";
    }


    /**
     * 短视频收藏列表
     *
     * @return
     */
    public final static String getShortVideoFavoriteListApi() {
        return ServiceHostManager.getInstall().getUser_service() + "/v3/vod/user_collect";
    }

    /**
     * 短视频收藏删除
     */
    public final static String getShortVideoFavoriteDeleteApi() {
        return ServiceHostManager.getInstall().getUser_service() + "/v3/vod/collect_delete";
    }


    /**
     * 影视特色中间板块推荐
     */
    public final static String getMoveSpecialRecomendListApi() {
        return ServiceHostManager.getInstall().getUser_service() + "/v3/carousel/get_recommend_content";
    }

    /**
     * 影视特色短视频板块推荐
     */
    public final static String getMovieShortVideoListApi() {
        return ServiceHostManager.getInstall().getUser_service() + "/v3/carousel/get_film_video_list";
    }

    /**
     * 影视特色导航栏
     */
    public final static String getMoveSpecialBannerApi() {
        return ServiceHostManager.getInstall().getUser_service() + "/v3/carousel/get_banner";
    }

    /**
     * 影视特色节目单
     */
    public final static String getMoveSpecialProgramApi() {
        return ServiceHostManager.getInstall().getUser_service() + "/v3/vod/short_video_film_features";
    }

    /**
     * 短视频影视推荐
     *
     * @return
     */
    public final static String getShortVideoFileRecommend() {
        return ServiceHostManager.getInstall().getUser_service() + "/v3/vod/short_video_film_recommend";
    }


    /**
     * websocket 发送弹幕
     */
    public final static String getSendDanmuApi() {
        return "http://barrage_ws.yuntuds.net/comment/sendBarrage";
    }

    /**
     * 获取历史弹幕
     */
    public final static String getDanmuApi() {
        return "http://comments.yuntuds.net/comment/barrage/list";
    }

    /**
     * 查看当前tvid是否为轮播
     */
    public final static String getIsLunboApi() {
        return ServiceHostManager.getInstall().getUser_service() + "/judgeChannelId";
    }

    /**
     * 获取官方评论和弹幕的接口
     */
    public final static String getRobotDanmuCommentApi() {
        return ServiceHostManager.getInstall().getUser_service() + "/v3/ad_comment";
    }

    /**
     * 云币商城
     *
     * @return
     */
    public final static String getYunBiShopApi() {
        return ServiceHostManager.getInstall().getUser_service() + "/v3/store/index?stype=1&_u=";
    }

    /**
     * 自定义列表
     *
     * @return
     */
    public final static String getDiyListApi() {
        return ServiceHostManager.getInstall().getUser_service() + "/v3/custom/tvListV2";
    }

    /**
     * 上传自定义数据到服务器(第二版，旧接口升级)
     *
     * @return
     */
    public final static String uploadDiyListApi() {
        return ServiceHostManager.getInstall().getUser_service() + "/v3/custom/uploadTvV2";
    }

    /**
     * 删除自定义数据
     *
     * @return
     */
    public final static String delDiyListApi() {
        return ServiceHostManager.getInstall().getUser_service() + "/v3/custom/delV2";
    }

    /**
     * 更新自定义数据
     *
     * @return
     */
    public final static String updateDiyListApi() {
        return ServiceHostManager.getInstall().getUser_service() + "/v3/custom/updateTvV2";
    }

    /**
     * 置顶自定义数据
     *
     * @return
     */
    public final static String onTopDiyListApi() {
        return ServiceHostManager.getInstall().getUser_service() + "/v3/custom/topTvV2";
    }

    /**
     * 广点通游戏中心跳转地址
     *
     * @return
     */
    public final static String getGDTGamaJumpUrl() {
        return "http://infotrack.gdt.qq.com/game_center";
    }

    /**
     * 获取视频集锦的tablist
     *
     * @return
     */
    public final static String getSportHighLightTabList() {
        return "http://game.api.a8sport.com/v3/news/video/tabList";
    }

    /**
     * 获取视频集锦对应tab下面的视频
     *
     * @return
     */
    public final static String getSportHighLightVideo() {
        return "http://game.api.a8sport.com/v3/news/video/listByType";
    }

    /**
     * 获取赛程里面热门列表
     *
     * @return
     */
    public final static String getSportMatchHotList() {
        return "https://game.api.a8sport.com/v3/game/hot";
    }


    /**
     * 视频推荐
     *
     * @return
     */
    public final static String getSportRecommendVideoList() {
        return "https://game.api.a8sport.com/v3/news/recommend/plus";
    }

    /**
     * 获取兑奖信息公告
     *
     * @return
     */
    public final static String getTaskRedeemList() {
        return ServiceHostManager.getInstall().getUser_service() + "/duiba/get_current_exchange_msg";
    }

    /**
     * 节目单操作
     *
     * @param isOrder
     * @return
     */
    public final static String orderOption(boolean isOrder) {
        if (isOrder) {
            return ServiceHostManager.getInstall().getUser_service() + "/v3/appointment";
        } else {
            return ServiceHostManager.getInstall().getUser_service() + "/v3/appointment/cancel";
        }
    }

    /**
     * 获取小视频推荐
     *
     * @return
     */
    public final static String getSmallRecomendApi() {
        return ServiceHostManager.getInstall().getUser_service() + "/v3/vod/small_recommend";
    }

    /**
     * 获取小视频-用户
     *
     * @return
     */
    public final static String getSmallUserVideoApi() {
        return ServiceHostManager.getInstall().getUser_service() + "/v3/vod/user_small_videos";
    }

    /**
     * 获取作者视频列表
     *
     * @return
     */
    public final static String getAuthorVideoApi() {
        return ServiceHostManager.getInstall().getUser_service() + "/v3/vod/author_video";
    }

    /**
     * 获取小视频点赞
     *
     * @return
     */
    public final static String getSmallVideoApproveApi() {
        return ServiceHostManager.getInstall().getUser_service() + "/v3/vod/small_video_approve";
    }

    /**
     * 小视频取消点赞
     *
     * @return
     */
    public final static String getSmallVideoCancleApproveApi() {
        return ServiceHostManager.getInstall().getUser_service() + "/v3/vod/small_video_cancel_approve";
    }

    /**
     * 小视频分享成功上报
     *
     * @return
     */
    public final static String getSmallVideoShareSuccApi() {
        return ServiceHostManager.getInstall().getUser_service() + "/v3/vod/small_video_share_success";
    }

    /**
     * 分享小视频成功后会得云币
     * @return
     */
    public final static String getSmallVideoShareGainApi(){
        return ServiceHostManager.getInstall().getUser_service() + "/v3/action/share_video";
    }

    /**
     * 小视频分享成功上报
     *
     * @return
     */
    public final static String getSmallVideoShareInfoApi() {
        return ServiceHostManager.getInstall().getActivity_service() + "/share/small_video_share_info";
    }

    /**
     * 推荐关注
     *
     * @return
     */
    public final static String getFocusRecom() {
        return ServiceHostManager.getInstall().getUser_service() + "/v3/vod/recommend_attention";
    }


    /**
     * 获取单个小视频的信息
     *
     * @return
     */
    public final static String getSmallVideoByVIdApi() {
        return ServiceHostManager.getInstall().getUser_service() + "/v3/vod/get_small_video_info";
    }

    /**
     * 获取关注的人
     *
     * @return
     */
    public final static String getFocusPeopleList() {
        return ServiceHostManager.getInstall().getUser_service() + "/v3/vod/user_attention";
    }

    /**
     * 获取我的关注的作者
     *
     * @return
     */
    public final static String getMyFocusPeopleList() {
        return ServiceHostManager.getInstall().getUser_service() + "/v3/vod/user_attention_center";
    }

    /**
     * 获取关注的人的推荐视频
     *
     * @return
     */
    public final static String getFocusPeopleVideoRecList() {
        return ServiceHostManager.getInstall().getUser_service() + "/v3/vod/user_attention_video";
    }

    /**
     * 关注作者和取消关注
     *
     * @return
     */
    public final static String getAttention() {
        return ServiceHostManager.getInstall().getUser_service() + "/v3/vod/attention";
    }

    /**
     * 关注作者和取消关注
     *
     * @return
     */
    public final static String cancelAttention() {
        return ServiceHostManager.getInstall().getUser_service() + "/v3/vod/attention_cancel";
    }

    /**
     * 获取单个小视频的信息
     * 获取作者的页面信息
     *
     * @return
     */
    public final static String getAuthorInfo() {
        return ServiceHostManager.getInstall().getUser_service() + "/v3/vod/author_info";
    }

    /**
     * 视频观看次数上报
     *
     * @return
     */
    public final static String getVideoWatchSucc() {
        return ServiceHostManager.getInstall().getUser_service() + "/v3/vod/video_watch_success";
    }



    /**
     * 更多影视特色
     */
    public final static String getMoveSpecialRecomendMoreListApi() {
        return ServiceHostManager.getInstall().getUser_service() + "/v3/carousel/get_recommend_content_more";
    }
}


