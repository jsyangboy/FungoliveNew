/**
 * Copyright (c) 2013 NewSenceNetwork.Co.Ltd. 
 * 
 * All rights reserved.
 */
package org.fungo.common_network.bean;


/**
 * @author Xutao
 *
 */
public interface ModeItem extends MultiItemEntity{
    
    int getType();
    void setType(int type);
    String getTvName();
    String getStream_url();
    String getTitle();
    int getTv_id();
    String getSecond_url();
    int getChannelType();
    String getEpg_title();
    long getStart_time();
    long getEnd_time();
    String getPath();
    String getProvinceName();
    String getOnline_url();
    void setFrameurl(String str);

}
