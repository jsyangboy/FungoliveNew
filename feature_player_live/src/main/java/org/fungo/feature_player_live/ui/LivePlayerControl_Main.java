package org.fungo.feature_player_live.ui;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import org.fungo.feature_player_live.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yqy
 * @create 19-7-18
 * @Describe 直播页面主要控制页面
 */
public class LivePlayerControl_Main extends BasePlayerContent {

    ImageButton btnExit;
    TextView tvTitle;
    TextView tvSubTitle;
    ImageButton btnShare;
    ImageButton btnLock;
    ImageButton btnPase;
    ImageButton btnLast;
    ImageButton btnNext;
    TextView tvChangeChannel;
    TextView tvChangeLine;
    TextView tvChangeBack;
    RecyclerView recycleView;
    /**
     * 根容器
     */
    private View mRootView;

    /**
     * adapter
     */
    private MultiItemTypeAdapter adapter;

    /**
     * 显示的数据
     */
    private List<Object> showData = new ArrayList<>();

    @Override
    public View getView() {
        mRootView = LayoutInflater.from(getContent()).inflate(R.layout.live_player_layout_live_player_main, null);
        initView(mRootView);
        return mRootView;
    }

    private void initView(View view) {
        btnExit = view.findViewById(R.id.btn_exit);
        btnLock = view.findViewById(R.id.btn_lock);
        btnPase = view.findViewById(R.id.btn_pase);
        btnLast = view.findViewById(R.id.btn_last);
        btnNext = view.findViewById(R.id.btn_next);

        tvTitle = view.findViewById(R.id.tv_title);
        tvSubTitle = view.findViewById(R.id.tv_sub_title);
        btnShare = view.findViewById(R.id.btn_share);
        tvChangeChannel = view.findViewById(R.id.tv_change_line);
        tvChangeLine = view.findViewById(R.id.tv_change_line);
        tvChangeBack = view.findViewById(R.id.tv_change_back);

        recycleView = view.findViewById(R.id.recycleView);

        /**
         * 设置横向
         */
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContent());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycleView.setLayoutManager(linearLayoutManager);


        adapter = new MultiItemTypeAdapter(getContent(), showData);
        recycleView.setAdapter(adapter);
    }


    public void addData(List<Object> showData) {
        showData.clear();
        this.showData.addAll(showData);
    }

    
}
