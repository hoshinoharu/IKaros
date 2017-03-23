package com.reharu.harubase.tools;

import java.io.InputStream;
import java.util.Scanner;

/**
 * Created by hoshino on 2017/3/19.
 */

public class StreamTool {

    public static String getString(InputStream inputStream){
        StringBuffer buffer = new StringBuffer() ;
        Scanner scanner = new Scanner(inputStream, "UTF-8") ;
        while(scanner.hasNextLine()){
            buffer.append(scanner.nextLine());
        }
        return buffer.toString() ;
    }
}
