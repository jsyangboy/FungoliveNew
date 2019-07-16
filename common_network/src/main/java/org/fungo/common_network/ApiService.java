package org.fungo.common_network;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author yqy
 * @create 19-7-16
 * @Describe
 */
public interface ApiService {
    /**
     * 启动界面图片
     */
    @GET("start-image/{res}")
    Observable<Object> getWelcomeInfo(@Path("res") String res);

}
