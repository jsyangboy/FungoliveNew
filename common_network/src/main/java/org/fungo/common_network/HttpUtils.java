package org.fungo.common_network;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author yqy
 * @create 19-7-16
 * @Describe
 */
public class HttpUtils {

    private static final int MAX_RETRY_COUNT = 3;

    private static final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) {
                    Request request = chain.request();

                    // try the request
                    Response response = null;

                    int tryCount = 0;
                    while (tryCount < MAX_RETRY_COUNT) {
                        try {

                            tryCount++;
                            // retry the request
                            response = chain.proceed(request);
                            if (response.isSuccessful()) {
                                tryCount = MAX_RETRY_COUNT;
                            }

                        } catch (NullPointerException e) {
                            if (tryCount == MAX_RETRY_COUNT) {
                                throw e;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    return response;
                }
            })
            .build();

    public Retrofit getApiServiceConfig(String url) {
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();

            return retrofit;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ApiService getApiService(String url) {
        Retrofit retrofit = getApiServiceConfig(url);
        if (retrofit != null) {
            return retrofit.create(ApiService.class);
        }
        return null;
    }
}
