package org.fungo.feature_player_live.persenter;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.fungo.common_core.base.BasePresenter;
import org.fungo.common_core.utils.DecodeUtils;
import org.fungo.common_core.utils.JSONUtils;
import org.fungo.common_core.utils.Logger;
import org.fungo.common_core.utils.Utils;
import org.fungo.common_network.HttpUtils;
import org.fungo.common_network.utils.RxUtils;
import org.fungo.feature_player_live.bean.EPGItem;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * @author yqy
 * @create 19-7-18
 * @Describe
 */
public class IjkLivePlayerPresenter extends BasePresenter {

    private final MutableLiveData<List<EPGItem>> mutableLiveData_egps;

    public IjkLivePlayerPresenter(@NonNull Application application) {
        super(application);
        mutableLiveData_egps = new MutableLiveData<>();
    }

    public MutableLiveData<List<EPGItem>> getMutableLiveData_egps() {
        return mutableLiveData_egps;
    }

    /**
     * 获取epg的数据
     */
    public void getEpgData() {
        Disposable disposable = HttpUtils.getInstance().getNowTvApiService()
                .getEpgFormTag("央视")
                .subscribeOn(Schedulers.io())
                .compose(RxUtils.<ResponseBody>io_main())
                .doOnNext(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        Logger.e("yqy accept =" + Thread.currentThread());
                    }
                }).subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        final List<EPGItem> itemToInsert = new ArrayList<EPGItem>();
                        final String resolveContent = DecodeUtils.resoveEPGBase64String(responseBody.string());
                        try {
                            JSONArray arrays = new JSONArray(resolveContent);

                            long time = new Date().getTime();
                            for (int i = 0; i < arrays.length(); i++) {
                                JSONObject obj = null;
                                try {
                                    obj = (JSONObject) arrays.get(i);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                EPGItem item = resolveEachEPGJson(obj);
                                // 添加刷新时间，使用long1字段
                                if (item == null) {
                                    continue;
                                }
                                item.setLong1(time);
                                itemToInsert.add(item);
                            }
                            Logger.e("yqy resolveContent=" + resolveContent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        mutableLiveData_egps.postValue(itemToInsert);
                    }
                });

        /**
         * 添加,activity退出来的时候,会自动释放
         */
        addDisposable(disposable);
    }


    /*
     * 解析单个json字串打包成itemBean函数，此处字段解析还待添加一些异常处理，使程序
     * 更加健壮
     */
    public static EPGItem resolveEachEPGJson(JSONObject obj) {
        if (obj == null) {
            return null;
        }

        String tv_name = JSONUtils.getString(obj, "tv_name", "");
        int tv_id = JSONUtils.getInt(obj, "tv_id", -1);
        String title = JSONUtils.getString(obj, "title", "");
        String epg_title = JSONUtils.getString(obj, "epg_title", "");
        long start_time = JSONUtils.getLong(obj, "start_time", 0);
        long end_time = JSONUtils.getLong(obj, "end_time", 0);
        int percent = JSONUtils.getInt(obj, "percent", 0);
        String frameurl = JSONUtils.getString(obj, "frameurl", "");
        double rating = JSONUtils.getDouble(obj, "rating", 10);
        String tv_name_py = JSONUtils.getString(obj, "tv_name_py", "");
        String tv_name_py_abb = JSONUtils.getString(obj, "tv_name_py_abb", "");
        String icon_url = JSONUtils.getString(obj, "icon_url2", "");
        String back_html = JSONUtils.getString(obj, "back_html", "");
        String icon_and_back_url = icon_url + "###" + back_html;
        int frequency = JSONUtils.getInt(obj, "frequency", 180);
        JSONArray tagArray = JSONUtils.getJSONArray(obj, "tags", null);
        String tagString = "";
        if (tagArray != null)
            tagString = tagArray.toString();
        String back_url = JSONUtils.getString(obj, "back_url", "");
        String online_url = JSONUtils.getString(obj, "web_url", "");
        int flag1 = JSONUtils.getInt(obj, "flag1", -1);

        int play_time = JSONUtils.getInt(obj, "num1", -1);  //add play times with key "num1"

        EPGItem item = new EPGItem(tv_name, tv_id, title, epg_title, start_time, end_time, percent,
                frameurl, tagString, rating, tv_name_py, tv_name_py_abb, icon_and_back_url, frequency, 0, back_url, online_url, play_time, flag1);
        return item;
    }

}
