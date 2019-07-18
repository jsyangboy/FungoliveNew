package org.fungo.fungolivenew;

import android.Manifest;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.fungo.common_core.base.BaseActivity;
import org.fungo.common_core.rx.RxAsync;
import org.fungo.common_core.utils.DecodeUtils;
import org.fungo.common_core.utils.Logger;
import org.fungo.common_core.utils.Utils;
import org.fungo.common_db.DbUtils;
import org.fungo.common_network.HttpUtils;
import org.fungo.common_network.NetWorkConstants;
import org.fungo.common_network.api.NowTvApiService;
import org.fungo.common_network.bean.EPGItem;
import org.fungo.common_network.interceptor.BasicParamsInterceptor;
import org.fungo.common_network.utils.RxUtils;
import org.fungo.fungolivenew.persenters.MainActivityPresenter;
import org.reactivestreams.Subscription;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class MainActivity extends BaseActivity {


    @BindView(R.id.btn_go)
    Button btnGo;
    @BindView(R.id.btn_get)
    Button btnGet;

    MainActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        presenter = ViewModelProviders.of(this).get(MainActivityPresenter.class);
        /**
         * 监听生命周期
         */
        getLifecycle().addObserver(presenter);

        long start = System.currentTimeMillis();
        String value = DbUtils.getInstance().getString("test");
        Logger.e("yqy " + (System.currentTimeMillis() - start));
        DbUtils.getInstance().putString("test", "testetsetestttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt");

        final RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    Toast.makeText(getApplicationContext(), "有权限", Toast.LENGTH_LONG).show();
                }
            }
        });


    }


    @OnClick({R.id.btn_go, R.id.btn_get})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_go:
                try {
                    ARouter.getInstance().build("/feature_player_live/FungoLivePlayerActivity").navigation();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_get:
                presenter.getData2();
                break;
        }
    }

}
