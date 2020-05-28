package com.example.betteradventureams;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class BarView extends View implements View.OnTouchListener {

    final int TAVERN_INDEX = 0;
    final int EXIT_INDEX = 1;
    final int GREET_INDEX = 2;
    final int BUY_INDEX = 3;
    final int ACQ_INDEX = 4;

    Bitmap[] images;
    final int NUM_OF_IMAGES = 5;
    BarLoad bl;
    Paint paint;
    boolean loaded = false;
    boolean addBuyButton = false;
    boolean axeAcquired = false;

    Rect background;
    Rect exit;
    Rect dialogue;
    Rect buy;


    public BarView(Context context) {
        super(context);
        setup();
    }
    public BarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean performClick(){
        super.performClick();
        return true;
    }
    void setup(){
        images = new Bitmap[NUM_OF_IMAGES];
        bl = new BarLoad(this.getContext(), images);
        bl.execute();
        paint = new Paint(Color.RED);
        invalidate();
    }

    public void onDraw(Canvas canvas){
        if(bl != null && bl.getStatus() == AsyncTask.Status.FINISHED){
            super.onDraw(canvas);
            int h = getHeight();
            int w = getWidth();

            if(!loaded) {
                loaded = true;
                background = new Rect(0, 0, w, h);
                exit = new Rect(0, 4 * h / 5, w / 3, h);
                dialogue = new Rect(w / 4, 0, 3 * w / 4, h / 3);
                buy = new Rect(2 * w / 3, 4 * h / 5, w, h);
            }

            canvas.drawBitmap(images[TAVERN_INDEX], null, background, paint);
            canvas.drawBitmap(images[EXIT_INDEX], null, exit, paint);
            canvas.drawBitmap(images[GREET_INDEX], null, dialogue, paint);
            if(addBuyButton){
                canvas.drawBitmap(images[BUY_INDEX], null, buy, paint);
            }else if(axeAcquired){
                canvas.drawBitmap(images[ACQ_INDEX], null, buy, paint);
            }
        }
    }

    public boolean tryingToExit(float x, float y){
        return (x >= 0 && x <= getWidth() / 3.0 && y <= getHeight() && y >= 4 * getHeight() / 5.0);
    }
    public boolean tryingToBuyAxe(float x, float y){
        if(x >= 2 * getWidth() / 3.0 && y >= 4 * getHeight() / 5.0){
            addBuyButton = false;
            axeAcquired = true;
            invalidate();
            return true;
        }else{
            return false;
        }
    }
    public void addBuyOption(){
        addBuyButton = true;
        invalidate();
    }
}
