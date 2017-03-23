package com.reharu.ikaros.haru.cortana;

import com.orange.entity.sprite.AnimatedSprite;

/**
 * Created by hoshino on 2017/3/18.
 */

public class Animation {

    //动画名称
    private String name ;

    //相对原本位置的偏移
    //X轴偏移量
    private float offsetX ;

    //Y轴偏移量
    private float offsetY ;


    private AnimatedSprite animatedSprite ;

    public Animation(String name) {
        this.name = name;
    }

    public Animation(String name, float offsetX, float offsetY) {
        this.name = name;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }


    public Animation setAnimatedSprite(AnimatedSprite animatedSprite) {
        this.animatedSprite = animatedSprite;
        return this ;
    }

    public AnimatedSprite getAnimatedSprite() {
        return animatedSprite;
    }

    public String getName() {
        return name;
    }

    public float getOffsetX() {
        return offsetX;
    }

    public float getOffsetY() {
        return offsetY;
    }

}
