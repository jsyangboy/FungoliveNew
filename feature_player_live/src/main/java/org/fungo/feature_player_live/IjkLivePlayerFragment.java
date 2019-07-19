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
import org.fungo.common_core.utils.Logger;
import org.fungo.common_core.utils.WeakHandler;
import org.fungo.feature_player_live.bean.EPGItem;
import org.fungo.feature_player_live.bean.SourceItem;
import org.fungo.feature_player_live.persenter.IjkLivePlayerPresenter;
import org.fungo.feature_player_live.base.BasePlayerContent;
import org.fungo.feature_player_live.ui.LivePlayerControl_Main;

import java.util.List;


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
         * 监听回调
         */
        addObserver();
        /**
         * 初始化view
         */
        initView(view);

        getData();
        return view;
    }

    /**
     * 获取数据
     */
    private void getData() {
        ijkLivePlayerPresenter.getEpgData();
    }

    /**
     * 监听回调
     */
    private void addObserver() {
        /**
         * 获取epg信息
         */
        ijkLivePlayerPresenter.getMutableLiveData_egps().observe(this, new Observer<List<EPGItem>>() {
            @Override
            public void onChanged(@Nullable List<EPGItem> epgItems) {
                getPlayerControlMainView().addData(epgItems);
            }
        });

        /**
         * 通过单个epg的tvId来获取真正的播放地址
         */
        ijkLivePlayerPresenter.getMutableLiveData_egps_source().observe(this, new Observer<SourceItem>() {
            @Override
            public void onChanged(@Nullable SourceItem sourceItem) {

            }
        });


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
                            ijkLivePlayerPresenter.getEpgSourceById(String.valueOf(epgItem.getTv_id()));
                        } else {

                        }
                    }
                }
            });


        }
        return livePlayerControl_main;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
}
