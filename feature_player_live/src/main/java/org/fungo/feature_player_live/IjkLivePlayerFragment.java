package org.fungo.feature_player_live;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import org.fungo.common_core.base.OnItemClickListener;
import org.fungo.common_core.utils.DateUtil;
import org.fungo.common_core.utils.JSONUtils;
import org.fungo.common_core.utils.Logger;
import org.fungo.common_core.utils.WeakHandler;
import org.fungo.feature_player_live.bean.EPGItem;
import org.fungo.feature_player_live.bean.SourceItem;
import org.fungo.feature_player_live.persenter.IjkLivePlayerPresenter;
import org.fungo.feature_player_live.base.BasePlayerContent;
import org.fungo.feature_player_live.persenter.repository.GetEpgDataRepository;
import org.fungo.feature_player_live.persenter.repository.IjkPlayerRepository;
import org.fungo.feature_player_live.ui.LivePlayerControl_Main;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import tv.danmaku.ijk.media.player.IMediaPlayer;


/**
 *
 */
public class IjkLivePlayerFragment extends Fragment implements WeakHandler.IHandler {


    private IjkLivePlayerPresenter ijkLivePlayerPresenter;
    private FrameLayout layout_video, layout_content;

    /**
     * 播放器主要的控制页面
     */
    private LivePlayerControl_Main livePlayerControl_main;

    /**
     * 定时检查是否正常播放,如果不正常,要自动切换下一条线路
     */
    private WeakHandler weakHandler_time = new WeakHandler(new WeakHandler.IHandler() {
        @Override
        public void handleMsg(Message msg) {

        }
    });

    /**
     * 各种各样的界面交互
     */
    private WeakHandler weakHandler_work = new WeakHandler(this);

    /**
     * 改变view的命令
     */
    private static final int Msg_Change_View = 0;
    private static final int Msg_ = 1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.live_player_fragment_ijk_live, container, false);


        /**
         * 初始化viewModel
         */
        ijkLivePlayerPresenter = ViewModelProviders.of(getActivity()).get(IjkLivePlayerPresenter.class);
        /**
         * 监听生命周期
         */
        getLifecycle().addObserver(ijkLivePlayerPresenter);


        /**
         * 初始化view
         */
        initView(view);

        getData();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /**
         * 设置播放的容器
         */
        ijkLivePlayerPresenter.setIjkVideoContent(layout_video);


        /**
         * 监听回调
         */
        addObserver();
    }


    /**
     * 获取数据
     */
    private void getData() {
        ijkLivePlayerPresenter.getGetEpgDataRepository().getEpgData();
    }

    /**
     * 监听回调
     */
    private void addObserver() {
        /**
         * 获取epg信息
         */
        final GetEpgDataRepository getEpgDataRepository = ijkLivePlayerPresenter.getGetEpgDataRepository();
        getEpgDataRepository.getMutableLiveData_egps().observe(this, egpLoadObserver);

        /**
         * 通过单个epg的tvId来获取真正的播放地址
         */
        getEpgDataRepository.getMutableLiveData_egps_source().observe(this, egpSourceGetbserver);


        /**
         * 视频设置对应的监听者
         */
        final IjkPlayerRepository ijkPlayerRepository = ijkLivePlayerPresenter.getIjkPlayerRepository();
        ijkPlayerRepository.getVideoInfoLiveData().observe(this, videoStatusObserver);
        ijkPlayerRepository.getVideoErrorLiveData().observe(this, videoErrorObserver);
        ijkPlayerRepository.getVideoCompletionLiveData().observe(this, videoCompletionObserver);
        ijkPlayerRepository.getVideoPreparedLiveData().observe(this, videoPreparedObserver);
        ijkPlayerRepository.getVideoProgressLiveData().observe(this, videoProgressObserver);
        ijkPlayerRepository.getmVideoBufferTimeLiveData().observe(this, videoBufferTimeObserver);

    }

    private void initView(View view) {
        layout_video = view.findViewById(R.id.layout_video);
        layout_content = view.findViewById(R.id.layout_content);

        /**
         * 显示主页
         */
        switchContetView(BasePlayerContent.View_Type_Main);
    }

    /**
     * 获取播放主页
     *
     * @return
     */
    private LivePlayerControl_Main getPlayerControlMainView() {
        if (livePlayerControl_main == null) {
            livePlayerControl_main = new LivePlayerControl_Main();
            livePlayerControl_main.init(getActivity());

            /**
             * 底部的item选择事件
             */
            livePlayerControl_main.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClickListener(int position, Object object) {
                    if (object != null) {
                        EPGItem epgItem = null;
                        try {
                            epgItem = (EPGItem) object;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        /**
                         * epg
                         */
                        if (epgItem != null) {
                            Logger.e("yqy tvId=" + epgItem.getTv_id());
                            ijkLivePlayerPresenter.getGetEpgDataRepository().getEpgSourceById(String.valueOf(epgItem.getTv_id()));
                        } else {

                        }
                    }
                }
            });


        }
        return livePlayerControl_main;
    }


    @Override
    public void handleMsg(Message msg) {
        final int type = msg.what;
        switch (type) {
            case Msg_Change_View:
                try {
                    final View view = (View) msg.getData().get("view");
                    //changeContentView(view);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }


    private void switchContetView(int viewType) {
        switch (viewType) {
            case BasePlayerContent.View_Type_Main:
                changeContentView(getPlayerControlMainView().getView(), null);
                break;
        }
    }


    /**
     * 切换view
     *
     * @param view
     */
    private void changeContentView(View view, FrameLayout.LayoutParams layoutParams) {
        try {
            if (view == null) {
                Logger.e("yqy changeContentView view is null");
                return;
            }
            if (layout_content != null) {
                final int count = layout_content.getChildCount();
                if (count != 0) {
                    layout_content.removeAllViews();
                }
                if (layoutParams != null) {
                    layout_content.addView(view, layoutParams);
                } else {
                    layout_content.addView(view);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public final Observer<List<EPGItem>> egpLoadObserver = new Observer<List<EPGItem>>() {
        @Override
        public void onChanged(@Nullable List<EPGItem> epgItems) {
            getPlayerControlMainView().addData(epgItems);
        }
    };

    /**
     * 获取epg真正的播放地址
     */
    public final Observer<SourceItem> egpSourceGetbserver = new Observer<SourceItem>() {
        @Override
        public void onChanged(@Nullable SourceItem sourceItem) {
            /**
             * 这里开始走源解析
             */
            
        }
    };


    private String beforePath = "";
    /**
     * 视频状态的监听
     */
    public final Observer<IjkPlayerRepository.VideoInfo> videoStatusObserver = new Observer<IjkPlayerRepository.VideoInfo>() {
        @Override
        public void onChanged(@Nullable IjkPlayerRepository.VideoInfo info) {
            if (info == null) {
                Logger.e("test videoInfoObserver info == null");
                return;
            }
            int integer = info.getValue1();
            Logger.e("test videoInfoObserver what =" + integer);

        }
    };

    /**
     * 视频播放错误的监听
     */
    public final Observer<IjkPlayerRepository.VideoInfo> videoErrorObserver = new Observer<IjkPlayerRepository.VideoInfo>() {
        @Override
        public void onChanged(@Nullable IjkPlayerRepository.VideoInfo videoInfo) {

        }
    };

    /**
     * 视频播放结束的监听
     */
    public final Observer<IjkPlayerRepository.VideoInfo> videoCompletionObserver = new Observer<IjkPlayerRepository.VideoInfo>() {
        @Override
        public void onChanged(@Nullable IjkPlayerRepository.VideoInfo videoInfo) {
            Logger.e("test videoCompletionObserver");
        }
    };

    /**
     * 视频准备播放的监听
     */
    public final Observer<IjkPlayerRepository.VideoInfo> videoPreparedObserver = new Observer<IjkPlayerRepository.VideoInfo>() {
        @Override
        public void onChanged(@Nullable IjkPlayerRepository.VideoInfo videoInfo) {
            Logger.e("test videoPreparedObserver");

        }
    };

    /**
     * 这个是视频buffer的时间，之前从新开始buffer就会会掉，并且一秒回调一次，当能播放后
     */
    public final Observer<Integer> videoBufferTimeObserver = new Observer<Integer>() {
        @Override
        public void onChanged(@Nullable Integer integer) {
            Logger.e("test videoBufferTimeObserver integer" + integer);

        }
    };


    /**
     * 视频播放进度监听
     */
    public final Observer<Bundle> videoProgressObserver = new Observer<Bundle>() {
        @Override
        public void onChanged(@Nullable Bundle bundle) {
            //Logger.e("test videoProgressObserver " + integer);
            final int position = bundle.getInt("position");
            final String currentTime = DateUtil.generateTime(position);

        }
    };


    /**
     * 源解析后得到的源字符串地址
     */
    public final Observer<List<String>> videoSourcesObserver = new Observer<List<String>>() {
        @Override
        public void onChanged(@Nullable List<String> strings) {
        }
    };
}
