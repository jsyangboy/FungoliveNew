package org.fungo.feature_player_live.base;

import android.content.Context;
import android.view.View;

/**
 * @author yqy
 * @create 19-7-18
 * @Describe
 */
public interface IBasePlayerContent {
    void init(Context context);

    View getView();

    Context getContent();
}
