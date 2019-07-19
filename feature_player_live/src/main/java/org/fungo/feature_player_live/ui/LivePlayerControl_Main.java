package org.fungo.feature_player_live.ui;

import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import org.fungo.common_core.AppCore;
import org.fungo.common_core.base.RecycleViewSpaceItemDecoration;
import org.fungo.common_core.utils.DensityUtil;
import org.fungo.common_core.utils.Logger;
import org.fungo.common_network.bean.MultiItemEntity;
import org.fungo.feature_player_live.R;
import org.fungo.feature_player_live.base.BasePlayerContent;
import org.fungo.feature_player_live.bean.EPGItem;
import org.fungo.feature_player_live.ui.adapter.PlayerBottomDelegate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yqy
 * @create 19-7-18
 * @Describe 直播页面主要控制页面
 */
public class LivePlayerControl_Main extends BasePlayerContent implements View.OnClickListener {

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
        tvChangeChannel = view.findViewById(R.id.tv_change_channel);
        tvChangeLine = view.findViewById(R.id.tv_change_line);
        tvChangeBack = view.findViewById(R.id.tv_change_back);

        recycleView = view.findViewById(R.id.recycleView);

        /**
         * 设置横向
         */
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContent());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycleView.setLayoutManager(linearLayoutManager);

        /**
         * 设置适配器
         */
        adapter = new MultiItemTypeAdapter(getContent(), showData);
        adapter.addItemViewDelegate(new PlayerBottomDelegate());
        recycleView.setAdapter(adapter);
        recycleView.addItemDecoration(new RecycleViewSpaceItemDecoration(DensityUtil.dip2px(getContent(), 5), 1));

        adapter.notifyDataSetChanged();
    }


    /**
     * 添加数据
     *
     * @param showData
     */
    public void addData(List<EPGItem> showData) {
        if (showData == null) {
            Logger.e("yqy showData = null");
            return;
        }

        this.showData.clear();
        this.showData.addAll(showData);
        Logger.e("yqy addData size = " + showData.size());
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onClick(View v) {
        final int id = v.getId();
        if (id == R.id.btn_exit) {

        } else if (id == R.id.tv_change_back) {//回看

        } else if (id == R.id.tv_change_channel) {//q切换频道

        } else if (id == R.id.tv_change_line) {//切换线路

        } else if (id == R.id.btn_share) {

        } else if (id == R.id.btn_pase) {

        } else if (id == R.id.btn_last) {

        } else if (id == R.id.btn_next) {

        } else if (id == R.id.btn_lock) {

        }
    }

    class RecycleViewSpaceItemDecoration extends RecyclerView.ItemDecoration {
        int mSpace;
        int mColumn = 1;

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            final int positon = parent.getChildAdapterPosition(view);
            /**
             * 第一行
             */
            outRect.bottom = mSpace * 2;
            outRect.top = mSpace * 2;


            if (positon % mColumn == mColumn - 1) {//最后列
                outRect.right = mSpace * 2;
                outRect.left = mSpace;
            } else if (positon % mColumn == 0) {//第一列
                outRect.left = mSpace * 2;
                outRect.right = mSpace;
            } else {//其他列
                outRect.left = mSpace;
                outRect.right = mSpace;
            }
        }

        public RecycleViewSpaceItemDecoration(int space, int column) {
            this.mSpace = space;
            this.mColumn = column;
        }
    }
}
