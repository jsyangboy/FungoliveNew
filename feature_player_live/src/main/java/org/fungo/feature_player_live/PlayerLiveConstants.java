package org.fungo.feature_player_live;

import android.util.SparseArray;

import org.fungo.feature_player_live.bean.BoxItem;
import org.fungo.feature_player_live.bean.TypeItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yqy
 * @create 19-7-17
 * @Describe
 */
public class PlayerLiveConstants {

    public static SparseArray<BoxItem> allChannelTypes = new SparseArray<BoxItem>();
    private static String typeChess;

    static {
        allChannelTypes.put(1, new BoxItem(1, "热播频道", "热播", "favourite", BoxItem.BOX_TOP_TYPE, -1, BoxItem.ADDED_YES, false));
        allChannelTypes.put(2, new BoxItem(2, "央视频道", "央视", "yangshi", BoxItem.BOX_CHANNEL_TYPE, 2, BoxItem.ADDED_YES, false));
        allChannelTypes.put(3, new BoxItem(3, "卫视频道", "卫视", "weishi", BoxItem.BOX_CHANNEL_TYPE, 3, BoxItem.ADDED_YES, false));
        allChannelTypes.put(4, new BoxItem(4, "体育频道", "体育", "sport", BoxItem.BOX_SPORT_TYPE, 6, BoxItem.ADDED_YES, false));
        allChannelTypes.put(5, new BoxItem(5, "影视频道", "影视", "movie", BoxItem.BOX_CATEGORY_TYPE, 5, BoxItem.ADDED_YES, false));
        allChannelTypes.put(7, new BoxItem(7, "地方频道", "地方", "conties", BoxItem.BOX_CONTIES_TYPE, 4, BoxItem.ADDED_YES, false));
        allChannelTypes.put(9, new BoxItem(9, "美女频道", "美女", "beauty", BoxItem.BOX_BEAUTY_TYPE, 8, BoxItem.ADDED_YES, false));
        allChannelTypes.put(10, new BoxItem(10, "生活频道", "生活", "life", BoxItem.BOX_CHANNEL_TYPE, 10, BoxItem.ADDED_NO, false));
        allChannelTypes.put(11, new BoxItem(11, "都市频道", "都市", "city", BoxItem.BOX_CHANNEL_TYPE, 11, BoxItem.ADDED_NO, false));
        allChannelTypes.put(12, new BoxItem(12, "纪录频道", "纪录", "record", BoxItem.BOX_CHANNEL_TYPE, 8, BoxItem.ADDED_YES, false));
        allChannelTypes.put(13, new BoxItem(13, "少儿频道", "少儿", "child", BoxItem.BOX_CHANNEL_TYPE, 13, BoxItem.ADDED_NO, false));
        allChannelTypes.put(14, new BoxItem(14, "教育频道", "教育", "edu", BoxItem.BOX_CHANNEL_TYPE, 14, BoxItem.ADDED_NO, false));
        allChannelTypes.put(15, new BoxItem(15, "理财频道", "理财", "caijing", BoxItem.BOX_FINANCE_TYPE, 8, BoxItem.ADDED_YES, false));
        allChannelTypes.put(16, new BoxItem(16, "游戏频道", "游戏", "game", BoxItem.BOX_GAME_TYPE, 10, BoxItem.ADDED_YES, false));
        allChannelTypes.put(17, new BoxItem(17, "短视频", "短视频", "shortvideo", BoxItem.BOX_SHORTVIDEO_TYPE, 1, BoxItem.ADDED_YES, false));
        allChannelTypes.put(18, new BoxItem(18, "收藏频道", "收藏", "collect", BoxItem.BOX_FAVOURITE_TYPE, 17, BoxItem.ADDED_NO, false));
        allChannelTypes.put(20, new BoxItem(20, "高清频道", "高清", "hd", BoxItem.BOX_CHANNEL_TYPE, 9, BoxItem.ADDED_YES, false));
        allChannelTypes.put(21, new BoxItem(21, typeChess, "棋牌", "chess", BoxItem.BOX_CHESS_GAME, 7, BoxItem.ADDED_YES, false));
    }

    /**
     * 频道对应的index
     */
    public static int[] channelNormal = {1, 5, 2, 24, 3, 4, 7, 17, 20, 12, 15, 13, 16, 11, 10, 14};
    private static List<TypeItem> mChannelCategory = new ArrayList<>();

    /**
     * 获取频道数据
     */
    public static void getChannelCategory() {
        if (mChannelCategory.size() == 0) {
            for (int id : channelNormal) {
                BoxItem boxItem = allChannelTypes.get(id);
                if (boxItem != null) {
                    //不能包含短视频
                    if (boxItem.getId() != 23) {
                        mChannelCategory.add(new TypeItem(boxItem.getBoxName(), boxItem.getBoxShortName()));
                    }
                }
            }
            mChannelCategory.add(new TypeItem("收藏频道", "收藏"));
        }
    }
}
