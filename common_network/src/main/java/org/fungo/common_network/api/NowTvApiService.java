package org.fungo.common_network.api;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author yqy
 * @create 19-7-17
 * @Describe
 */
public interface NowTvApiService {
    /**
     * 根据视频ids来获取视频信息(格式比如50667,60665)
     */
    @GET("epg_tvnow_android_ids.php?")
    Observable<ResponseBody> getEpgFormIds(@Query("ids") String ids);


    /**
     * 根据tag获取epg信息
     *
     * @param tag
     * @return
     */
    @GET("epg_tvnow_android_with_tag.php?")
    Observable<ResponseBody> getEpgFormTag(@Query("tag") String tag);
}
