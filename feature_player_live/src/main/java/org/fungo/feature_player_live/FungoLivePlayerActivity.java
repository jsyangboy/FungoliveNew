package org.fungo.feature_player_live;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.alibaba.android.arouter.facade.annotation.Route;

import org.fungo.feature_player_live.bean.PlayerBundleData;

@Route(path = "/feature_player_live/FungoLivePlayerActivity")
public class FungoLivePlayerActivity extends AppCompatActivity {


    private IjkLivePlayerFragment mIjkLivePlayerFragment;

    /**
     * 播放器使用到的数据
     */
    private PlayerBundleData playerBundleData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * 隐藏标题栏
         */
        getSupportActionBar().hide();

        /**
         * 隐藏状态栏
         */
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_fungo_live);

        Bundle bundle = getIntent().getBundleExtra("");
        createIjkLivePlayer(playerBundleData);

    }

    private void createIjkLivePlayer(PlayerBundleData playerBundleData) {
        mIjkLivePlayerFragment = new IjkLivePlayerFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.video_area, mIjkLivePlayerFragment)
                .commitAllowingStateLoss();
    }


}
