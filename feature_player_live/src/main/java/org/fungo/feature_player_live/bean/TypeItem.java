package org.fungo.feature_player_live.bean;


/*
 * PlayerActivity中用来记录频道分类
 */
public class TypeItem {
    private String name;
    private String tag;
    
    public TypeItem(String name, String tag) {
        this.name = name;
        this.tag = tag;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the tag
     */
    public String getTag() {
        return tag;
    }

    /**
     * @param tag the tag to set
     */
    public void setTag(String tag) {
        this.tag = tag;
    }
}
