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
public class BaseRepository {

    /**
     * 管理Disposable对象
     */
    private CompositeDisposable mCompositeDisposable;
    /**
     * Context对象
     */
    public Context mContext;

    private final static String TAG = "BasePresenter";

    public BaseRepository(@NonNull Application application) {
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
    public void release() {
        if (null != mCompositeDisposable) {
            mCompositeDisposable.clear();
            mCompositeDisposable = null;
        }
    }

}
