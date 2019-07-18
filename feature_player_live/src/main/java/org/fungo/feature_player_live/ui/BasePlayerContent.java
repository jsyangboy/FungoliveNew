package org.fungo.feature_player_live.ui;

import android.content.Context;
import android.view.View;

import org.fungo.common_core.AppCore;

import java.lang.ref.WeakReference;

/**
 * @author yqy
 * @create 19-7-18
 * @Describe
 */
public class BasePlayerContent implements IBasePlayerContent {

    /**
     * 播放器主页
     */
    public static final int View_Type_Main = 0;


    protected WeakReference<Context> mContextWeakReference;

    @Override
    public void init(Context context) {
        mContextWeakReference = new WeakReference<>(context);
    }

    @Override
    public View getView() {
        return null;
    }

    @Override
    public Context getContent() {
        if (mContextWeakReference != null) {
            return mContextWeakReference.get();
        }
        return AppCore.getApplication();
    }
}
