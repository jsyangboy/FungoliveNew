package org.fungo.common_core.base;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.support.annotation.NonNull;

import org.fungo.common_core.utils.Logger;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author yqy
 * @create 19-7-18
 * @Describe
 */
public class BasePresenter extends AndroidViewModel implements LifecycleObserver {

    /**
     * 管理Disposable对象
     */
    private CompositeDisposable mCompositeDisposable;
    /**
     * Context对象
     */
    public Context mContext;

    private final static String TAG = "BasePresenter";

    public BasePresenter(@NonNull Application application) {
        super(application);
        this.mContext = application;
    }

    protected void addDisposable(Disposable disposable) {
        if (null == mCompositeDisposable) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    /**
     * app退出来的时候会清空
     */
    protected void release() {
        if (null != mCompositeDisposable) {
            mCompositeDisposable.clear();
            mCompositeDisposable = null;
        }
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        Logger.e(TAG + " onCreate");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        Logger.e(TAG + " onStart");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        Logger.e(TAG + " onResume");
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        Logger.e(TAG + " onPause");

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        Logger.e(TAG + " onStop");
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        Logger.e(TAG + " onDestroy");
        release();
    }
}
