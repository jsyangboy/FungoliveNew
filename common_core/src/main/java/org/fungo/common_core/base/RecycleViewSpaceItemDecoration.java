package org.fungo.common_core.base;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author yqy
 * @create 19-6-26
 * @Describe 可以让recycleView的item均匀的分布
 */
public class RecycleViewSpaceItemDecoration extends RecyclerView.ItemDecoration {
    int mSpace;
    int mColumn = 1;

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        final int positon = parent.getChildAdapterPosition(view);
        /**
         * 第一行
         */
        if (positon < mColumn) {
            outRect.top = mSpace * 2;
        } else {
            outRect.top = mSpace;
        }
        outRect.bottom = mSpace;

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
