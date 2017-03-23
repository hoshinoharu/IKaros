package com.reharu.ikaros.haru.cortana.dto;

import com.reharu.ikaros.haru.cortana.behavior.Feeling;

/**
 * Created by hoshino on 2017/3/20.
 */

public class SpeackContent {
    public Feeling feeling ;
    public String content ;

    public SpeackContent(Feeling feeling, String content) {
        this.feeling = feeling;
        this.content = content;
    }
}
