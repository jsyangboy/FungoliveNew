package org.fungo.feature_player_live.persenter;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.FrameLayout;

import com.alibaba.fastjson.JSON;
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
import org.fungo.feature_player_live.bean.SourceItem;
import org.fungo.feature_player_live.persenter.repository.GetEpgDataRepository;
import org.fungo.feature_player_live.persenter.repository.IjkPlayerRepository;
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


    /**
     * 视频播放相应的仓库
     */
    private final IjkPlayerRepository ijkPlayerRepository;
    private final GetEpgDataRepository getEpgDataRepository;

    public IjkPlayerRepository getIjkPlayerRepository() {
        return ijkPlayerRepository;
    }

    public GetEpgDataRepository getGetEpgDataRepository() {
        return getEpgDataRepository;
    }

    public IjkLivePlayerPresenter(@NonNull Application application) {
        super(application);
        ijkPlayerRepository = new IjkPlayerRepository(application);
        getEpgDataRepository = new GetEpgDataRepository(application);
    }


    public boolean setIjkVideoContent(FrameLayout layout_video) {
        if (ijkPlayerRepository != null) {
            return ijkPlayerRepository.setIjkVideoContent(layout_video);
        }
        return false;
    }

    public void removePlayerFromContent() {
        if (ijkPlayerRepository != null) {
            ijkPlayerRepository.removePlayerFromContent();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getEpgDataRepository.release();

    }
}
