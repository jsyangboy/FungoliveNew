package org.fungo.fungolivenew;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.fungo.common_core.utils.Logger;
import org.fungo.common_core.utils.Utils;
import org.fungo.common_db.DbUtils;
import org.fungo.common_network.HttpUtils;
import org.fungo.common_network.NetWorkConstants;
import org.fungo.common_network.api.NowTvApiService;
import org.fungo.common_network.bean.EPGItem;
import org.fungo.common_network.interceptor.BasicParamsInterceptor;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.btn_go)
    Button btnGo;
    @BindView(R.id.btn_get)
    Button btnGet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

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

    public void getData() {

        try {
            NowTvApiService apiService = HttpUtils.getInstance().getNowTvApiService();
            if (apiService != null) {
                Observable<ResponseBody> observable = apiService.getEpgFormIds("50667");
                //Observable<ResponseBody> observable = apiService.getEpgFormTag("央视");
                observable.subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .retry(3)//请求失败重连次数
                        .subscribe(new DisposableObserver<ResponseBody>() {
                            @Override
                            public void onNext(ResponseBody o) {
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
        } catch (Exception e) {
            e.printStackTrace();
        }

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
                getData();
                break;
        }
    }

    public static void main(String arvs[]) {

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("version=" + Utils.getVersionNameTiny()).
                append("channel=" + Utils.getChannel());




      /*  BasicParamsInterceptor basicParamsInterceptor = new BasicParamsInterceptor.Builder()
                .addQueryParam(NetWorkConstants.REQ_PARAMS_KEY_VERSION, Utils.getVersionNameTiny())
                .addQueryParam(NetWorkConstants.REQ_PARAMS_KEY_PLATFORM, NetWorkConstants.REQ_PARAMS_VALUE_PLATFROM)
                .addQueryParam(NetWorkConstants.REQ_PARAMS_KEY_APPX, NetWorkConstants.REQ_PARAMS_VALUE_APPX)
                .addQueryParam(NetWorkConstants.REQ_PARAMS_KEY_APPPN, apk_package_name)
                .addQueryParam(NetWorkConstants.REQ_PARAMS_KEY_ENTERPRISE, enterPrise)
                .addQueryParam(NetWorkConstants.REQ_PARAMS_KEY_CHANNEL, Utils.getChannel())
                .addQueryParam(NetWorkConstants.REQ_PARAMS_KEY_MARKET, String.valueOf(Utils.getMarketInfo()))
                .addQueryParam(NetWorkConstants.REQ_PARAMS_KEY_OS_VERSION, String.valueOf(Utils.getReleaseVersionNumber()))
                .addQueryParam(NetWorkConstants.REQ_PARAMS_KEY_DEVICE_MODEL, String.valueOf(Utils.getDeviceModel()))
                .addQueryParam(NetWorkConstants.REQ_PARAMS_KEY_DEVICE_CODE, Utils.getDeviceId())
                .addQueryParam(NetWorkConstants.REQ_PARAMS_VALUE_UDID, Utils.getDeviceInfoWithoutMD5())
                .addQueryParam(NetWorkConstants.REQ_PARAMS_VALUE_ANDROID_ID, Utils.getAndroidId())
                .addQueryParam(NetWorkConstants.REQ_PARAMS_VALUE_SOURCE, appMark).build();*/
    }
}
