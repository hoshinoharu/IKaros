package com.reharu.ikaros.haru.news;

import java.util.Date;

/**
 * Created by hoshino on 2017/4/20.
 */

public class News {

    public static String WAR = "war"; //军事 ;
    public static String SPORT = "sport"; //体育 ;
    public static String TECH = "tech"; //科技 ;
    public static String EDU = "edu"; //教育 ;
    public static String ENT = "ent"; //娱乐 ;
    public static String MONEY = "money"; //财经 ;
    public static String GUPIAO = "gupiao"; //股票 ;
    public static String TRAVEL = "travel"; //旅游 ;
    public static String LADY = "lady"; //女人 ;



    private String imgurl ;
    private boolean has_content ;
    private String docurl ;
    private Integer id ;
    private Date time ;
    private String title ;
    private String channelname ;

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public boolean isHas_content() {
        return has_content;
    }

    public void setHas_content(boolean has_content) {
        this.has_content = has_content;
    }

    public String getDocurl() {
        return docurl;
    }

    public void setDocurl(String docurl) {
        this.docurl = docurl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChannelname() {
        return channelname;
    }

    public void setChannelname(String channelname) {
        this.channelname = channelname;
    }

    @Override
    public String toString() {
        return "News{" +
                "imgurl='" + imgurl + '\'' +
                ", has_content=" + has_content +
                ", docurl='" + docurl + '\'' +
                ", id=" + id +
                ", time=" + time +
                ", title='" + title + '\'' +
                ", channelname='" + channelname + '\'' +
                '}';
    }
}
