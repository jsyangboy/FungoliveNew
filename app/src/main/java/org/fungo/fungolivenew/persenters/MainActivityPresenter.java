package org.fungo.fungolivenew.persenters;

import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;

import org.fungo.common_core.base.BasePresenter;
import org.fungo.common_core.utils.DecodeUtils;
import org.fungo.common_core.utils.Logger;
import org.fungo.common_network.HttpUtils;
import org.fungo.common_network.utils.RxUtils;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;

/**
 * @author yqy
 * @create 19-7-18
 * @Describe
 */
public class MainActivityPresenter extends BasePresenter {


    public MainActivityPresenter(@NonNull Application application) {
        super(application);
    }

    public void getData2() {
        Disposable disposable = HttpUtils.getInstance().getNowTvApiService()
                .getEpgFormTag("央视")
                .compose(RxUtils.<ResponseBody>io_main())
                .doOnNext(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        Logger.e("yqy accept =" + Thread.currentThread());
                    }
                }).subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        Logger.e("yqy onNext thread =" + Thread.currentThread());
                        Log.e("yqy result=", DecodeUtils.resoveEPGBase64String(responseBody.string()));

                    }
                });

        /**
         * 添加,activity退出来的时候,会自动释放
         */
        addDisposable(disposable);
    }

}
