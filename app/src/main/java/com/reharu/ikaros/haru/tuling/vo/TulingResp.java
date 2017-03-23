package com.reharu.ikaros.haru.tuling.vo;

import java.net.URL;
import java.util.List;

/**
 * Created by hoshino on 2017/3/20.
 */

public class TulingResp {
    public static final int KEY_ERROR = 40001 ;
    public static final int EMPTY_INFO = 40002 ;
    public static final int ENOUGH_TIMES = 40004 ;
    public static final int FORMAT_EXCEPTION = 40007 ;
    public Integer code ;
    public String text ;
    public List<NewsAndCKBook> list ;
    public URL url ;
}
