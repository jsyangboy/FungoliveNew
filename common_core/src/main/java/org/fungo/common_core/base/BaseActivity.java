package org.fungo.common_core.base;

import android.support.v7.app.AppCompatActivity;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author yqy
 * @create 19-7-18
 * @Describe
 */
public class BaseActivity extends AppCompatActivity {

    private CompositeDisposable mCompositeDisposable;

    protected void addDisposable(Disposable disposable) {
        if (null == mCompositeDisposable) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    @Override
    protected void onDestroy() {
        if (null != mCompositeDisposable) {
            mCompositeDisposable.clear();
        }
        super.onDestroy();
    }
}