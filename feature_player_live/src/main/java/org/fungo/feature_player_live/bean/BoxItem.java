/**
 * Copyright (c) 2013 NewSenceNetwork.Co.Ltd.
 * <p>
 * All rights reserved.
 */
package org.fungo.feature_player_live.bean;


/**
 * @author Xutao
 * 频道
 */
public class BoxItem {

    private int id;

    private String boxName;

    private String boxShortName;

    private String boxImage;

    private int boxType;

    private int rating;

    private int added;

    private boolean hasAd;

    public final static int BOX_MORE_TYPE = 1;
    public final static int BOX_CHANNEL_TYPE = 3;
    public final static int BOX_CONTIES_TYPE = 4;
    public final static int BOX_SPORT_TYPE = 5;
    public final static int BOX_BEAUTY_TYPE = 6;
    public final static int BOX_TOP_TYPE = 7;
    public final static int BOX_FAVOURITE_TYPE = 8;
    public final static int BOX_CHESS_GAME = 10;
    public final static int BOX_GAME_TYPE = 11;//游戏
    public final static int BOX_CATEGORY_TYPE = 12;
    public final static int BOX_RECOMMEND_TYPE = 13;
    public final static int BOX_FINANCE_TYPE = 14;
    public final static int BOX_AD_TYPE = 15;
    public final static int BOX_SHORTVIDEO_TYPE = 16;
    public final static int BOX_SMOREVIDEO_TYPE = 17;

    public final static int ADDED_NO = 0;
    public final static int ADDED_YES = 1;

    public BoxItem() {

    }

    public BoxItem(int boxType, String boxShortName) {
        this.boxType = boxType;
        this.boxShortName = boxShortName;
    }


    public BoxItem(int id, String boxName, String boxShortName, String boxImage, int boxType, int rating, int added, boolean hasAd) {
        this.id = id;
        this.boxName = boxName;
        this.boxImage = boxImage;
        this.boxType = boxType;
        this.boxShortName = boxShortName;
        this.rating = rating;
        this.added = added;
        this.hasAd = hasAd;
    }

    public static BoxItem getMoreItem() {
        return new BoxItem(0, "编辑", "编辑", "more", BOX_MORE_TYPE, -1, ADDED_NO, false);
    }

    public static BoxItem getAllItem() {
        return new BoxItem(0, "全部", "全部", "all", BOX_MORE_TYPE, -1, ADDED_NO, false);
    }

    /**
     * @return the boxName
     */
    public String getBoxName() {
        return boxName;
    }

    /**
     * @param boxName the boxName to set
     */
    public void setBoxName(String boxName) {
        this.boxName = boxName;
    }

    /**
     * @return the boxImage
     */
    public String getBoxImage() {
        return boxImage;
    }

    /**
     * @param boxImage the boxImage to set
     */
    public void setBoxImage(String boxImage) {
        this.boxImage = boxImage;
    }

    /**
     * @return the boxType
     */
    public int getBoxType() {
        return boxType;
    }

    /**
     * @param boxType the boxType to set
     */
    public void setBoxType(int boxType) {
        this.boxType = boxType;
    }

    /**
     * @return the boxShortName
     */
    public String getBoxShortName() {
        return boxShortName;
    }

    /**
     * @param boxShortName the boxShortName to set
     */
    public void setBoxShortName(String boxShortName) {
        this.boxShortName = boxShortName;
    }

    /**
     * @return the rating
     */
    public int getRating() {
        return rating;
    }

    /**
     * @param rating the rating to set
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

    /**
     * @return the added
     */
    public int getAdded() {
        return added;
    }

    /**
     * @param added the added to set
     */
    public void setAdded(int added) {
        this.added = added;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    public boolean isHasAd() {
        return hasAd;
    }

    public void setHasAd(boolean hasAd) {
        this.hasAd = hasAd;
    }
}
