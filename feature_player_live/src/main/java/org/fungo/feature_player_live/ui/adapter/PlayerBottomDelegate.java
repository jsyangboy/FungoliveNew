package org.fungo.feature_player_live.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.zhy.adapter.recyclerview.base.ItemViewDelegate;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import org.fungo.common_core.AppCore;
import org.fungo.common_core.utils.DensityUtil;
import org.fungo.common_core.utils.Logger;
import org.fungo.common_network.bean.MultiItemEntity;
import org.fungo.feature_player_live.R;
import org.fungo.feature_player_live.bean.EPGItem;

/**
 * @author yqy
 * @create 19-7-19
 * @Describe 底部的推荐频道
 */
public class PlayerBottomDelegate implements ItemViewDelegate<MultiItemEntity> {

    @Override
    public int getItemViewLayoutId() {
        return R.layout.live_player_adapter_bottom;
    }

    @Override
    public boolean isForViewType(MultiItemEntity item, int position) {
        return item.getItemType() == 0;
    }

    @Override
    public void convert(ViewHolder holder, MultiItemEntity multiItemEntity, int position) {
        EPGItem epgItem = null;
        try {
            epgItem = (EPGItem) multiItemEntity;
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (epgItem == null) {

            return;
        }

        /**
         * 图片地址
         */
        final String imageUrl = epgItem.getFrameurl();
        final ImageView imageView = holder.getView(R.id.iv_icon);

        /**
         * 设置圆角
         */
        RoundedCorners roundedCorners = new RoundedCorners(DensityUtil.dip2px(AppCore.getApplication(), 4));
        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners).override(300, 300);
        Glide.with(AppCore.getApplication()).load(imageUrl).apply(options).into(imageView);
    }
}
