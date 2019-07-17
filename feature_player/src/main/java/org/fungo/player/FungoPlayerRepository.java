package org.fungo.player;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewGroup;


import org.fungo.common_core.utils.Logger;

import tv.danmaku.ijk.media.example.widget.media.IjkVideoView;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

import static tv.danmaku.ijk.media.example.widget.media.IjkVideoView.RENDER_SURFACE_VIEW;

/**
 * @author yqy
 * @create 2018/9/17
 * @Describe 视频播放器仓库
 */
public class FungoPlayerRepository {


    private final static String TAG = "IjkPlayerRepository";
    /**
     * 视频播放控件ijk
     */
    private static IjkVideoView ijkVideoView;

    /**
     * 装载ijkVideoView的ViewGroup,外部传递进来
     */
    private ViewGroup mVideoContent;

    /**
     * Context对象
     */
    private Context mContext;

    /**
     * 当前播放的url
     */
    private String mCurrentUrl;

    /**
     * 视频播放结束的liveData
     */
    private final MutableLiveData<VideoInfo> mVideoCompletionLiveData;

    /**
     * 视频播放info的liveData
     */
    private final MutableLiveData<VideoInfo> mVideoInfoLiveData;

    /**
     * 视频准备好的liveData
     */
    private final MutableLiveData<VideoInfo> mVideoPreparedLiveData;

    /**
     * 视频播放失败的liveData
     */
    private final MutableLiveData<VideoInfo> mVideoErrorLiveData;

    /**
     * 视频播放进度liveData
     */
    private final MutableLiveData<Bundle> mVideoProgressLiveData;

    /**
     * 视频缓冲到真正播放的时间liveData
     */
    private final MutableLiveData<Integer> mVideoBufferTimeLiveData;

    /**
     * 是否已经停止播放
     */
    private boolean mIsStop = false;

    /**
     * 视频最开始的偏移量
     */
    private int mSegOffsetTime = 0;

    /**
     * 标志是否已经播放完毕
     */
    private boolean mIsPlayComplete = false;

    /**
     * 标志视频是否已经开始渲染（即播放）
     */
    private boolean mIsPlayRender = false;

    /**
     * 标志是否正在缓存中
     */
    private boolean mIsBuffering = false;

    /**
     * 记录缓冲的时间，即mIsBuffering开始到能播放的时间
     */
    private int mBufferingTime = 0;

    /**
     * 视频进度的handle
     */
    private final Handler videoProgressHandler = new Handler();

    /**
     * 检查视频缓冲的时间
     */
    private final Handler videoBufferTimeHandler = new Handler();


    /**
     * 是否正在seek中
     */
    private boolean mIsSeeking = false;

    /**
     * 是否是暂停状态
     */
    private boolean mIsPause = false;


    /**
     * 传递进度跟缓存的进度
     */
    final Bundle bundle = new Bundle();

    /**
     * 判断视频是否已经准备
     */
    private boolean mIsPrePared = false;

    /**
     * 视频播放完毕后返回的对象
     */
    private final VideoInfo mVideoCompletionInfo = new VideoInfo(1);

    /**
     * 视频消息，视频的一些状态
     */
    private final VideoInfo mVideoInfo = new VideoInfo(1);

    /**
     * 视频准备播放返回的对象
     */
    private final VideoInfo mVideoPreparedInfo = new VideoInfo(1);

    /**
     * 视频播放错误返回的对象
     */
    private final VideoInfo mVideoErrorInfo = new VideoInfo(1);

    public FungoPlayerRepository(Context context) {
        mContext = context;
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");

        mVideoCompletionLiveData = new MutableLiveData();
        mVideoInfoLiveData = new MutableLiveData();
        mVideoPreparedLiveData = new MutableLiveData();
        mVideoErrorLiveData = new MutableLiveData();
        mVideoProgressLiveData = new MutableLiveData();
        mVideoBufferTimeLiveData = new MutableLiveData<>();
    }


    /**
     * 获取播放进度的Runnable,一秒重复获取一次
     */
    private final Runnable getProgressRunnable = new Runnable() {
        @Override
        public void run() {
            if (mIsStop || mIsPlayComplete) {
                Logger.e(TAG + " getProgressRunnable mIsStop =" + mIsStop + " mIsPlayComplete=" + mIsPlayComplete);
                return;
            }

            /**
             * 如果正在缓冲中
             */
            if (mVideoBufferTimeLiveData != null && (mIsBuffering || !mIsPlayRender) && !mIsPause) {
                if (!isPlayerPlaying()) {
                    mBufferingTime++;
                    mVideoBufferTimeLiveData.postValue(mBufferingTime);
                }
            }

            if (mVideoProgressLiveData != null && ijkVideoView != null) {
                /**
                 * 获取当前的进度
                 */
                int position = ijkVideoView.getCurrentPosition();//毫秒

                /**
                 * 所以视频的总进度是减去偏移量
                 */
                int duration = ijkVideoView.getDuration();//毫秒
                if (duration < 0) {
                    duration = 0;
                }

                /**
                 * 当前真正的进度是当前进度减去偏移量
                 */
                int actualPos = position;
                if (actualPos < 0) {
                    actualPos = 0;
                } else if (actualPos > duration) {
                    actualPos = duration;
                }

                bundle.putInt("position", actualPos);
                final int percentage = ijkVideoView.getBufferPercentage();
                bundle.putInt("percentage", percentage);
                mVideoProgressLiveData.setValue(bundle);

                /**
                 * 有一些短视频是监听不到onComplete事件的，并且最后一针返回的
                 * position 往往会大于 duration 这里需要手动的处理一下
                 */
                if (duration > 0 && !mIsBuffering && !mIsSeeking && actualPos >= duration) {
                    if (!mIsPlayComplete) {
                        mVideoCompletionLiveData.setValue(mVideoCompletionInfo);
                    }
                    return;
                }
                //Logger.e(TAG + " duration=" + duration + " position=" + position + " actualPos=" + actualPos + " percentage=" + percentage);
                videoProgressHandler.postDelayed(getProgressRunnable, 1000);
            }
        }
    };

    /**
     * 从开始执行播放到成功播放这断时间的检查,当视频已经开始渲染后就停止回调
     * 一秒回调一次
     */
    final Runnable getBufferTimeRunnable = new Runnable() {
        @Override
        public void run() {
            if (mVideoBufferTimeLiveData == null || mIsPlayRender || mIsPause || mIsStop || mIsPlayComplete) {
                mBufferingTime = 0;
                return;
            }

            if (isPlayerPlaying()) {
                return;
            }
            mBufferingTime++;
            mVideoBufferTimeLiveData.postValue(mBufferingTime);
            videoBufferTimeHandler.postDelayed(getBufferTimeRunnable, 1000);
        }
    };

    /**
     * 设置ijkVideoView的容器
     *
     * @param content
     * @return
     */
    public boolean setIjkVideoContent(ViewGroup content) {
        if (mContext == null || content == null) {
            Logger.e("setIjkVideoContent context == null || content == null");
            return false;
        }
        /**
         * 存储之前要确保ijkVideoView已经被移除
         */
        if (mVideoContent != null) {
            mVideoContent.removeAllViews();
        }
        mVideoContent = content;

        /**
         * 要确保已经关闭正在播放的视频
         */
        if (ijkVideoView != null) {
            if (ijkVideoView.isPlaying()) {
                ijkVideoView.stopPlayback();
                ijkVideoView.release(true);
                ijkVideoView.stopBackgroundPlay();
            }

            if (ijkVideoView.getParent() != null) {
                Logger.e("ijkVideoView is have Parent");
                return false;
            }
        }

        if (ijkVideoView == null) {
            ijkVideoView = new IjkVideoView(mContext);
            ijkVideoView.setOnCompletionListener(onCompletionListener);
            ijkVideoView.setOnErrorListener(onErrorListener);
            ijkVideoView.setOnInfoListener(onInfoListener);
            ijkVideoView.setOnPreparedListener(onPreparedListener);
        }
        /**
         * 添加到布局中
         */
        mVideoContent.addView(ijkVideoView);
        return true;
    }


    private final IMediaPlayer.OnCompletionListener onCompletionListener = new IMediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(IMediaPlayer iMediaPlayer) {
            mIsPlayComplete = true;
            if (mVideoCompletionLiveData != null) {
                mVideoCompletionLiveData.setValue(mVideoCompletionInfo);
            }

        }
    };

    private final IMediaPlayer.OnErrorListener onErrorListener = new IMediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(IMediaPlayer iMediaPlayer, int what, int i1) {
            mIsPlayComplete = true;
            if (mVideoErrorLiveData != null) {
                mVideoErrorInfo.setiMediaPlayer(iMediaPlayer);
                mVideoErrorInfo.setValue1(what);
                mVideoErrorInfo.setValue2(i1);
                mVideoErrorLiveData.setValue(mVideoErrorInfo);
            }
            return true;
        }
    };

    private final IMediaPlayer.OnInfoListener onInfoListener = new IMediaPlayer.OnInfoListener() {
        @Override
        public boolean onInfo(IMediaPlayer iMediaPlayer, int what, int i1) {
            switch (what) {
                case IMediaPlayer.MEDIA_INFO_BUFFERING_START:
                    if (ijkVideoView.isPlaying()) {
                        ijkVideoView.pause();
                    }
                    mIsBuffering = true;
                    mBufferingTime = 0;
                    break;
                case IMediaPlayer.MEDIA_INFO_AUDIO_RENDERING_START:
                    /**
                     * 视频开始渲染后，开始获取每秒获取时间进度
                     */
                    videoProgressHandler.removeCallbacks(getProgressRunnable);
                    videoProgressHandler.postDelayed(getProgressRunnable, 1000);
                    break;
                case IMediaPlayer.MEDIA_INFO_BUFFERING_END: {
                    mIsSeeking = false;
                    mIsBuffering = false;
                    /**
                     * 缓冲完毕后如果还没有播放则进行播放，如果是暂停状态下，就不要开始播放了
                     * 比如暂停的时候进行进度条拖动
                     */
                    if (!mIsPause && ijkVideoView != null) {
                        ijkVideoView.start();
                        mIsPause = false;
                    }
                }
                break;
                case IMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
                    mIsPlayRender = true;
                    mIsBuffering = false;
                    break;
            }
            if (mVideoInfoLiveData != null) {
                mVideoInfo.setiMediaPlayer(iMediaPlayer);
                mVideoInfo.setValue1(what);
                mVideoInfo.setValue2(i1);
                mVideoInfoLiveData.setValue(mVideoInfo);
            }
            return false;
        }
    };

    private final IMediaPlayer.OnPreparedListener onPreparedListener = new IMediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(IMediaPlayer iMediaPlayer) {
            if (mVideoPreparedLiveData != null) {
                mIsPrePared = true;
                mVideoPreparedInfo.setiMediaPlayer(iMediaPlayer);
                mVideoPreparedLiveData.setValue(mVideoPreparedInfo);

                /**
                 * 比如exo的播放器,播放的时候不回调开始渲染的消息,监听到这个接口后有可能就已经开始播放了
                 */
                if (isPlayerPlaying()) {
                    /**
                     * 视频开始渲染后，开始获取每秒获取时间进度
                     */
                    videoProgressHandler.removeCallbacks(getProgressRunnable);
                    videoProgressHandler.postDelayed(getProgressRunnable, 1000);
                    return;
                }
            }
        }
    };

    /**
     * 设置播放地址
     *
     * @param url
     */
    public boolean setPlayerUrl(String url) {
        if (url == null || url.length() == 0) {
            Logger.e(TAG + " setPlayerUrl url == null");
            return false;
        }
        this.mCurrentUrl = url;
        mIsStop = false;
        mIsPlayComplete = false;
        mIsPlayRender = false;
        if (ijkVideoView != null) {
            Logger.e(TAG + " mCurrentUrl = " + mCurrentUrl);
            // TODO: 18-10-19  setVideoPath这个函数有可能会造成卡顿
            ijkVideoView.setVideoPath(mCurrentUrl);
            ijkVideoView.setRender(RENDER_SURFACE_VIEW);
            ijkVideoView.seekTo(mSegOffsetTime);
            videoProgressHandler.removeCallbacks(getProgressRunnable);
            videoBufferTimeHandler.removeCallbacks(getBufferTimeRunnable);
            mBufferingTime = 0;
            return true;
        } else {
            /**
             * 说明在创建的时候没有创建成功，在次尝试
             */
            setIjkVideoContent(mVideoContent);
            Logger.e(TAG + " ijkVideoView == null");
        }
        return false;
    }

    /**
     * 开始播放
     */
    public boolean startPlayer() {
        if (ijkVideoView != null) {
            Logger.e(TAG + " startPlayer");
            /**
             * 有可能是经过了stopPlayer在startPlayer
             * 这里要重新设置一下播放路径
             */
            if (mIsStop || mIsPlayComplete) {
                if (mCurrentUrl == null || mCurrentUrl.length() == 0) {
                    Logger.e(TAG + " mCurrentUrl == null");
                    return false;
                }

                ijkVideoView.setVideoPath(mCurrentUrl);
                ijkVideoView.setRender(RENDER_SURFACE_VIEW);
                Logger.e(TAG + " set setRender and set setVideoPath");
                ijkVideoView.seekTo(mSegOffsetTime);
                mIsPlayRender = false;
            }
            ijkVideoView.start();
            mIsPlayComplete = false;
            mIsStop = false;
            mIsPause = false;
            mIsPrePared = false;
            mBufferingTime = 0;
            /**
             * 开始播放后就开始计时
             */
            videoBufferTimeHandler.removeCallbacks(getBufferTimeRunnable);
            videoBufferTimeHandler.postDelayed(getBufferTimeRunnable, 1000);
            return true;
        }
        return false;
    }

    /**
     * 开始重播
     */
    public boolean startRePlay() {
        if (ijkVideoView != null) {
            if (mCurrentUrl == null || mCurrentUrl.length() == 0) {
                Logger.e(TAG + " mCurrentUrl == null");
                return false;
            }
            ijkVideoView.setVideoPath(mCurrentUrl);
            ijkVideoView.setRender(RENDER_SURFACE_VIEW);
            ijkVideoView.seekTo(0);
            ijkVideoView.start();
            mIsPlayComplete = false;
            mIsStop = false;
            mIsSeeking = false;
            mIsPause = false;
            mIsPrePared = false;
            Logger.e(TAG + " startRePlay");
            return true;
        }
        return false;
    }

    /**
     * 暂停播放
     */
    public void pausePlayer() {
        if (ijkVideoView != null) {
            if (ijkVideoView.canPause()) {
                ijkVideoView.pause();
                mIsPause = true;
                Logger.e(TAG + " pausePlayer");
            }
        }
    }

    /**
     * 标志是否正在播放中
     *
     * @return
     */
    public boolean isPlayerPlaying() {
        if (ijkVideoView != null) {
            return ijkVideoView.isPlaying();
        }
        return false;
    }

    /**
     * 停止播放
     */
    public void stopPlayer() {
        if (ijkVideoView != null) {
            /**
             * 释放
             */
            ijkVideoView.stopPlayback();
            ijkVideoView.stopBackgroundPlay();
            ijkVideoView.release(true);
            mIsStop = true;
            mIsSeeking = false;
            videoProgressHandler.removeCallbacks(getProgressRunnable);
            videoBufferTimeHandler.removeCallbacks(getBufferTimeRunnable);

            Logger.e(TAG + " stopPlayer mIsStop");
        }
    }

    /**
     * 停止不释放，可以快速的切换源
     */
    public void stopPlayerNoRelease() {
        if (ijkVideoView != null) {
            /**
             * 释放
             */
            ijkVideoView.stopPlayback();
            ijkVideoView.stopBackgroundPlay();
            mIsStop = true;
            mIsSeeking = false;
            videoProgressHandler.removeCallbacks(getProgressRunnable);
            videoBufferTimeHandler.removeCallbacks(getBufferTimeRunnable);
            Logger.e(TAG + " stopPlayer stopPlayerNoRelease");
        }
    }

    /**
     * 设置音量状态
     *
     * @param
     */
    public boolean setVolumeStatus(float left, float right) {
        if (ijkVideoView != null) {
            ijkVideoView.setVolume(left, right);
            return true;
        }
        return false;
    }

    /**
     * 获取播放总时长
     *
     * @return 毫秒
     */
    public long getDuration() {
        if (ijkVideoView != null) {
            /**
             * 所以视频的总进度是减去偏移量
             */
            int duration = ijkVideoView.getDuration();
            if (duration < 0) {
                duration = 0;
            }

            /**
             * 这里要做一下判断，如果进度为0，但是mSegOffsetTime不为0说明是seek
             */
            if (duration == 0 && mSegOffsetTime > 0) {
                duration = mSegOffsetTime;
            }
            return duration;
        }
        return 0;
    }

    /**
     * 获取当前播放进度
     * 毫秒
     *
     * @return
     */
    public long getCurrentPosition() {
        if (ijkVideoView != null) {
            return ijkVideoView.getCurrentPosition();
        }
        return 0;
    }

    /**
     * 获取缓存的百分比
     *
     * @return
     */
    public int getBufferPercentage() {
        if (ijkVideoView != null) {
            return ijkVideoView.getBufferPercentage();
        }
        return 0;
    }

    /**
     * 移除布局
     */
    public void removePlayerFromContent() {
        /**
         * 移除布局中
         */
        if (mVideoContent != null) {
            mVideoContent.removeAllViews();
        }
    }

    /**
     * 快进
     *
     * @param progress
     */
    public void seekTo(int progress) {
        if (ijkVideoView != null && progress >= 0) {
            ijkVideoView.seekTo(progress);
            mIsSeeking = true;
        }
    }

    /**
     * 销毁播放
     */
    public void destroyPlayer() {
        IjkMediaPlayer.native_profileEnd();
        removePlayerFromContent();
        ijkVideoView = null;
        mCurrentUrl = null;
        mSegOffsetTime = 0;
        Logger.e(TAG + " destroyPlayer");
    }

    public MutableLiveData<VideoInfo> getVideoCompletionLiveData() {
        return mVideoCompletionLiveData;
    }

    public MutableLiveData<VideoInfo> getVideoInfoLiveData() {
        return mVideoInfoLiveData;
    }

    public MutableLiveData<VideoInfo> getVideoPreparedLiveData() {
        return mVideoPreparedLiveData;
    }

    public MutableLiveData<VideoInfo> getVideoErrorLiveData() {
        return mVideoErrorLiveData;
    }

    public MutableLiveData<Bundle> getVideoProgressLiveData() {
        return mVideoProgressLiveData;
    }

    public MutableLiveData<Integer> getmVideoBufferTimeLiveData() {
        return mVideoBufferTimeLiveData;
    }

    public int getSegOffsetTime() {
        return mSegOffsetTime;
    }

    /**
     * 设置视频开始播放的偏移量
     *
     * @param mSegOffsetTime
     */
    public void setSegOffsetTime(int mSegOffsetTime) {
        this.mSegOffsetTime = mSegOffsetTime;
    }

    public String getCurrentUrl() {
        return mCurrentUrl;
    }

    public boolean isPlayComplete() {
        return mIsPlayComplete;
    }

    /**
     * 切换屏幕比例
     *
     * @return -1代表切换失败
     */
    public int toggleAspectRatio() {
        if (ijkVideoView != null) {
            if (isPlayerPlaying()) {
                return ijkVideoView.toggleAspectRatio();
            }
        }
        return -1;
    }


    /**
     * mutableLiveData回调出去的对象
     */
    public static class VideoInfo {
        IMediaPlayer iMediaPlayer;
        int value1;
        int value2;
        int value3;
        int value4;

        public VideoInfo(IMediaPlayer iMediaPlayer, int value1, int value2) {
            this.iMediaPlayer = iMediaPlayer;
            this.value1 = value1;
            this.value2 = value2;
        }

        public VideoInfo(int value1) {
            this.value1 = value1;
        }

        public IMediaPlayer getiMediaPlayer() {
            return iMediaPlayer;
        }

        public void setiMediaPlayer(IMediaPlayer iMediaPlayer) {
            this.iMediaPlayer = iMediaPlayer;
        }

        public int getValue1() {
            return value1;
        }

        public void setValue1(int value1) {
            this.value1 = value1;
        }

        public int getValue2() {
            return value2;
        }

        public void setValue2(int value2) {
            this.value2 = value2;
        }

        public int getValue3() {
            return value3;
        }

        public void setValue3(int value3) {
            this.value3 = value3;
        }

        public int getValue4() {
            return value4;
        }

        public void setValue4(int value4) {
            this.value4 = value4;
        }
    }
}
