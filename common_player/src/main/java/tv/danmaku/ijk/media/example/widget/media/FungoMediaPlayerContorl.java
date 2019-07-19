package tv.danmaku.ijk.media.example.widget.media;

import android.widget.MediaController;

/**
 * Created by Administrator on 2018/3/26.
 * 这个自己添加的,继承原来的MediaPlayerControl增加多toggleAspecRation方法主要用于，原来是没有的，主要用于视频回放过程中缩放比例的切换
 */
public interface FungoMediaPlayerContorl extends MediaController.MediaPlayerControl {
    void toggleAspectRatio(int mode);
}
