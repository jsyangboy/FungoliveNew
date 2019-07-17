package org.fungo.common_network;

import org.fungo.common_core.utils.Logger;
import org.fungo.common_network.api.A8ApiService;
import org.fungo.common_network.api.ActivityApiService;
import org.fungo.common_network.api.AdApiService;
import org.fungo.common_network.api.NowTvApiService;
import org.fungo.common_network.api.UserApiService;
import org.fungo.common_network.host.ServiceHostManager;
import org.fungo.common_network.interceptor.BasicParamsInterceptor;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author yqy
 * @create 19-7-16
 * @Describe
 */
public class HttpUtils {

    private static volatile HttpUtils httpUtils;
    private static final String TAG = "HttpUtils";

    /**
     * 单利模式
     *
     * @return
     */
    public static HttpUtils getInstance() {
        if (httpUtils != null) {
            synchronized (HttpUtils.class) {
                if (httpUtils == null) {
                    httpUtils = new HttpUtils();
                }
            }
        }
        return httpUtils;
    }

    private HttpUtils() {
    }

    private OkHttpClient okHttpClient;

    /**
     * 获取httpclick
     *
     * @return
     */
    private OkHttpClient getOkHttpClient() {
        //定制OkHttp
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient
                .Builder();
        /**
         * OkHttp进行添加拦截器
         */
        httpClientBuilder.addInterceptor(getLoggerInterceptor());
        httpClientBuilder.addInterceptor(getBasicParamsInterceptor());

        if (okHttpClient == null) {
            okHttpClient = httpClientBuilder.build();
        }
        return okHttpClient;
    }

    /**
     * long
     *
     * @return
     */
    private Interceptor getLoggerInterceptor() {
        //日志显示级别
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;
        //新建log拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Logger.e(TAG + " message=" + message);
            }
        });
        loggingInterceptor.setLevel(level);
        return loggingInterceptor;
    }

    /**
     * 公共参数
     *
     * @return
     */
    private Interceptor getBasicParamsInterceptor() {
        BasicParamsInterceptor basicParamsInterceptor = new BasicParamsInterceptor.Builder()
                .addHeaderLine("hello:hello")
                .addQueryParam("deviceId", "12345")
                .build();
        return basicParamsInterceptor;
    }

    /**
     * user接口
     *
     * @return
     */
    private static Retrofit UserRetrofit;

    private Retrofit getUserApiRetrofit() {
        if (UserRetrofit == null) {
            UserRetrofit = new Retrofit.Builder()
                    .baseUrl(ServiceHostManager.getInstall().getUser_service() + "/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getOkHttpClient())//使用自己创建的OkHttp
                    .build();
        }
        return UserRetrofit;
    }


    /**
     * user接口
     *
     * @return
     */
    private static Retrofit NowTvRetrofit;

    private Retrofit getNowTvApiRetrofit() {
        if (NowTvRetrofit == null) {
            NowTvRetrofit = new Retrofit.Builder()
                    .baseUrl(ServiceHostManager.getInstall().getNowtv_service() + "/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getOkHttpClient())//使用自己创建的OkHttp
                    .build();
        }
        return NowTvRetrofit;
    }


    /**
     * ad接口
     *
     * @return
     */
    private static Retrofit AdRetrofit;

    private Retrofit getAdApiRetrofit() {
        if (AdRetrofit == null) {
            AdRetrofit = new Retrofit.Builder()
                    .baseUrl(ServiceHostManager.getInstall().getAd_service() + "/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getOkHttpClient())//使用自己创建的OkHttp
                    .build();
        }
        return AdRetrofit;
    }

    /**
     * ad接口
     *
     * @return
     */
    private static Retrofit ActivityRetrofit;

    private Retrofit getActivityApiRetrofit() {
        if (ActivityRetrofit == null) {
            ActivityRetrofit = new Retrofit.Builder()
                    .baseUrl(ServiceHostManager.getInstall().getActivity_service() + "/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getOkHttpClient())//使用自己创建的OkHttp
                    .build();
        }
        return ActivityRetrofit;
    }

    /**
     * ad接口
     *
     * @return
     */
    private static Retrofit A8Retrofit;

    private Retrofit getA8ApiRetrofit() {
        if (A8Retrofit == null) {
            A8Retrofit = new Retrofit.Builder()
                    .baseUrl(ServiceHostManager.getInstall().getA8_service() + "/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getOkHttpClient())//使用自己创建的OkHttp
                    .build();
        }
        return A8Retrofit;
    }


    /**
     * 获取接口
     * User接口
     *
     * @return
     */

    public UserApiService getUserApiService() {
        return getUserApiRetrofit().create(UserApiService.class);
    }

    /**
     * 获取接口
     * nowTv接口
     *
     * @return
     */
    public NowTvApiService getNowTvApiService() {
        return getUserApiRetrofit().create(NowTvApiService.class);
    }


    /**
     * 获取接口
     * ad接口
     *
     * @return
     */
    public AdApiService getAdApiService() {
        return getUserApiRetrofit().create(AdApiService.class);
    }


    /**
     * 获取接口
     * activity接口
     *
     * @return
     */
    public ActivityApiService getActivityApiService() {
        return getUserApiRetrofit().create(ActivityApiService.class);
    }


    /**
     * 获取接口
     * A8接口
     *
     * @return
     */
    public A8ApiService getA8ApiService() {
        return getA8ApiRetrofit().create(A8ApiService.class);
    }


}
