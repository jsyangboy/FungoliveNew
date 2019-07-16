package org.fungo.common_core.rx;

/**
 * Created by Veesong on 2017/8/25.
 */
public interface RxAsyncCallback<T> {
    T doInBackgroud();
    void onPost(T t);
}
