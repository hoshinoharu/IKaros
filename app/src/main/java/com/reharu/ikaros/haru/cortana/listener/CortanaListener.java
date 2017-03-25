package com.reharu.ikaros.haru.cortana.listener;

import com.reharu.ikaros.haru.cortana.Cortana;
import com.reharu.ikaros.haru.cortana.behavior.Feeling;

/**
 * Created by hoshino on 2017/3/20.
 */
//Cortana监听
public interface CortanaListener{
     class Adapter implements CortanaListener{

        @Override
        public void onApperance(Cortana cortana) {

        }

        @Override
        public void onSpeack(Cortana cortana, String content) {

        }

        @Override
        public void onFeelingChanged(Cortana cortana, Feeling feeling, boolean changed) {

        }

         @Override
         public void onTouch(Cortana cortana) {

         }

         @Override
         public boolean onInterceptGetMessage(String message) {
            return  false ;
         }

     }

    void onApperance(Cortana cortana) ;

    void onSpeack(Cortana cortana, String content) ;

    void onFeelingChanged(Cortana cortana, Feeling feeling, boolean changed);

    void onTouch(Cortana cortana) ;

    boolean onInterceptGetMessage(String message) ;
}
