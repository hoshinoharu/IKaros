package com.reharu.ikaros.haru.cortana;

import com.orange.entity.sprite.AnimatedSprite;
import com.reharu.harubase.tools.MathTool;

import java.util.Arrays;
import java.util.List;

/**
 * Created by hoshino on 2017/3/18.
 */

public class CortanaAnimResManager {

    public static final String APPEAR_0_XML = "texture/appera_0.xml";
    private static final String APPEAR_0 = "appera_0";

    public static final String NORMAL_BLINK_XML = "texture/normal_blink.xml";
    private static final String NORMAL_BLINK = "normal_blink";

    public static final String HAPPY_XML = "texture/happy.xml";
    private static final String HAPPY = "happy";

    public static final String LOVE_XML = "texture/love.xml";
    private static final String LOVE = "love";


    public static final String JUMP_XML = "texture/jump.xml";
    private static final String JUMP = "jump";

    public static final String ROTATE_XML = "texture/rotate.xml";
    private static final String ROTATE = "rotate";

    public static final String DOUBT_XML = "texture/doubt.xml";
    private static final String DOUBT = "doubt";

    public static final String BLINK_XML = "texture/blink.xml";
    private static final String BLINK = "blink";


    private List<Animation> animationList;

    private Animation appera0;
    private Animation normal_blink;
    private Animation happy;
    private Animation love;
    private Animation jump;
    private Animation rotate;
    private Animation doubt;
    private Animation blink;






    private Animation wave;



    private Animation[] happySet ;
    private Animation[] apperaSet ;
    private Animation[] normalSet ;
    private Animation[] doubtSet ;
    private Animation[] sadSet;

    private List<Animation> allAnimations ;

    private Cortana cortana ;

    public CortanaAnimResManager(Cortana cortana) {
        this.cortana = cortana ;
        appera0 = new Animation(CortanaAnimResManager.APPEAR_0);
        normal_blink = new Animation(CortanaAnimResManager.NORMAL_BLINK, 0, -50);
        happy = new Animation(CortanaAnimResManager.HAPPY, 0, -5);
        love = new Animation(CortanaAnimResManager.LOVE, 0, -2);
        jump = new Animation(CortanaAnimResManager.JUMP, 0, -5);
        rotate =  new Animation(CortanaAnimResManager.ROTATE, -10, -10);
        doubt =  new Animation(CortanaAnimResManager.DOUBT, -0, -50);
        blink =  new Animation(CortanaAnimResManager.BLINK, -5, -30);
        //初始化动作列表 方便随机抽取动作
        happySet = new Animation[]{happy, happy, happy, happy,jump,jump, jump, rotate, rotate, love};
        apperaSet = new Animation[]{appera0};
        normalSet = new Animation[]{normal_blink} ;
        doubtSet = new Animation[]{doubt} ;
        sadSet = new Animation[]{blink};
        allAnimations = Arrays.asList(
                appera0,normal_blink,happy,love,jump,rotate,doubt,blink
        ); ;
        //加载数据
        for(Animation animation : allAnimations){
            this.load(animation);
        }
    }

    private void load(Animation animation){
        animation.setAnimatedSprite(new AnimatedSprite(0, 0, animation.getName(), cortana.getVertexBufferObjectManager()));
    }

    public List<Animation> getAllAnimations() {
        return allAnimations ;
    }

    public Animation getHappyAnimation(){
        return happySet[MathTool.randomInt(happySet.length)] ;
    }

    public Animation getApperaAnimation(){
        return apperaSet[MathTool.randomInt(apperaSet.length)] ;
    }

    public Animation getNormalAnimation(){
        return normalSet[MathTool.randomInt(normalSet.length)] ;
    }

    public Animation getDoubtAnimation(){
        return doubtSet[MathTool.randomInt(doubtSet.length)] ;
    }

    public Animation getSadAnimation(){
        return randomAnimation(sadSet) ;
    }

    private Animation randomAnimation(Animation[] animations){
        return animations[MathTool.randomInt(animations.length)] ;
    }

    public Animation getWave() {
        return wave;
    }
}
