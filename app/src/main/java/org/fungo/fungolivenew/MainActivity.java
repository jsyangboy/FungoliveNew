package org.fungo.fungolivenew;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.alibaba.android.arouter.launcher.ARouter;

import org.fungo.common_core.utils.Logger;
import org.fungo.common_db.DbUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.btn_go)
    Button btnGo;

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

        /*AdApiService apiService = HttpUtils.getInstance().getUserApiService();
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
        }*/
    }

    @OnClick(R.id.btn_go)
    public void onViewClicked() {
        try {
            ARouter.getInstance().build("/feature_player_live/FungoLiveActivity").navigation();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
