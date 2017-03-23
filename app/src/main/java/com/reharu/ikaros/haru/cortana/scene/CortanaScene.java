package com.reharu.ikaros.haru.cortana.scene;

import com.orange.content.SceneBundle;
import com.orange.engine.camera.Camera;
import com.orange.entity.primitive.Rectangle;
import com.orange.entity.scene.Scene;
import com.orange.input.touch.TouchEvent;
import com.orange.opengl.util.GLState;
import com.orange.util.color.Color;
import com.reharu.harubase.tools.HLog;
import com.reharu.ikaros.haru.cortana.Cortana;
import com.reharu.ikaros.haru.cortana.sprite.WaveSprite;

/**
 * Created by hoshino on 2017/3/17.
 */

//小娜场景
public class CortanaScene extends Scene {
    private Cortana cortana;
    private Rectangle bg;
    private WaveSprite wave ;
    private boolean isFirst;

    public interface OnCreatedListener{
        void onCreated(CortanaScene scene);
    }

    private OnCreatedListener onCreatedListener ;
    @Override
    public void onSceneCreate(SceneBundle bundle) {
        super.onSceneCreate(bundle);
        bg = new Rectangle(0, 0, this.getWidth(), this.getHeight(),
                this.getVertexBufferObjectManager());
        this.cortana = new Cortana(this);
        //设置背景颜色
        bg.setColor(Color.BLACK);
        this.attachChild(bg, 0);
        this.attachChild(cortana, 1);
        isFirst = true;
    }

    public void setOnCreatedListener(OnCreatedListener onCreatedListener) {
        this.onCreatedListener = onCreatedListener;
    }

    @Override
    protected void onApplyMatrix(GLState pGLState, Camera pCamera) {
        //场景渲染时播放小娜出现动画
        if(isFirst && !pGLState.isBlendEnabled()){
            cortana.onApperance();
            isFirst =false ;
            if(onCreatedListener != null){
                onCreatedListener.onCreated(this);
            }
        }
        super.onApplyMatrix(pGLState, pCamera);
    }

    @Override
    public boolean onTouch(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        if(pSceneTouchEvent.isActionDown() ){
            new WaveSprite(this).show(pTouchAreaLocalX, pTouchAreaLocalY);
        }
        return super.onTouch(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
    }

    @Override
    public void onAttached() {
        super.onAttached();
        HLog.e("TAG", "onAttached");

    }



    public Cortana getCortana() {
        return cortana;
    }

    @Override
    public void onSceneResume() {
        super.onSceneResume();
        HLog.e("TAG", "SceneResume");
        this.setIgnoreUpdate(false);
    }


    @Override
    public void onScenePause() {
        super.onScenePause();
        HLog.e("TAG", "ScenePause");
        this.setIgnoreUpdate(false);
    }
}
