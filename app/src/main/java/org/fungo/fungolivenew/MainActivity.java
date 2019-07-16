package org.fungo.fungolivenew;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import org.fungo.common_core.utils.Logger;
import org.fungo.common_db.DbUtils;
import org.fungo.common_network.ApiService;
import org.fungo.common_network.HttpUtils;
import org.fungo.common_network.RxUtils;
import org.reactivestreams.Subscription;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.recycleView)
    RecyclerView recycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        long start = System.currentTimeMillis();
        String value = DbUtils.getInstance().getString("test");
        Logger.e("yqy " + (System.currentTimeMillis() - start));
        DbUtils.getInstance().putString("test", "testetsetestttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt");

        getData();
    }

    public void getData() {
        HttpUtils httpUtils = new HttpUtils();
        ApiService apiService = httpUtils.getApiService("www.baidu.com");
        if (apiService != null) {
            Observable<Object> observable = apiService.getWelcomeInfo("tes");
            observable.subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .retry(3)//请求失败重连次数
                    .subscribe(new DisposableObserver<Object>() {
                        @Override
                        public void onNext(Object o) {
                            Logger.e("yqy onNext");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Logger.e("yqy onError");
                        }

                        @Override
                        public void onComplete() {
                            Logger.e("yqy onComplete");
                        }
                    });

        } else {
            Logger.e("yqy apiService == null");
        }
    }
}
