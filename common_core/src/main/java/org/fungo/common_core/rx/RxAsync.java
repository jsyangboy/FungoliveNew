package org.fungo.common_core.rx;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Veesong on 2017/8/25.
 */
public class RxAsync {
    public static <T> void run(final RxAsyncCallback<T> callback) {
        Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> subscriber) throws Exception {

                T t = callback.doInBackgroud();

                subscriber.onNext(t);
                subscriber.onComplete();
            }
        })
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<T>() {

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(T t) {
                callback.onPost(t);
            }
        });
    }

    public static void runBackground(final RxAsyncBackgroudCallback call) {
        RxAsyncCallback callback = new RxAsyncCallback<Integer>() {
            @Override
            public Integer doInBackgroud() {
                call.doInBackgroud();
                return 0;
            }

            @Override
            public void onPost(Integer integer) {

            }
        };
        run(callback);
    }
}
