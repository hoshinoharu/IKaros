package com.reharu.ikaros.haru.cortana.dto;

/**
 * Created by hoshino on 2017/3/20.
 */

public class ChartRecord {
    public static final int CORTANA = 0 ;
    public static final int MASTER = 1 ;

    //说话内容
    public CharSequence content ;

    //说话者
    public int speakcer ;

    public ChartRecord(CharSequence content, int speakcer) {
        this.content = content;
        this.speakcer = speakcer;
    }
}
