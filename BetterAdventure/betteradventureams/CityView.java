package com.example.betteradventureams;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
final class CityMapIndexes{
    final int OOB_INDEX = 0;
    final int CURB1_INDEX = 1;
    final int CURB2_INDEX = 2;
    final int TLCORNER_INDEX = 3;
    final int CURBUP_INDEX = 4;
    final int CROSS1_INDEX = 5;
    final int CROSS2_INDEX = 6;
    final int CROSS3_INDEX = 7;
    final int CROSS4_INDEX = 8;
    final int STREET_INDEX = 9;
    final int STREETUP_INDEX = 10;
    final int INTERSECTION_INDEX = 11;
    final int STREETUPRIGHT_INDEX = 12;
    final int TRCORNER_INDEX = 13;
    final int BRCORNER_INDEX = 14;
    final int BLCORNER_INDEX = 15;
    final int BUILDING_INDEX = 16;
    final int CURBDOWNCOIN_INDEX = 17;
    final int CURBDOWNRAT_INDEX = 18;
    final int STREETDOOR_INDEX = 19;
    final int CURBUPDOOR_INDEX = 20;
    final int CURBUPMANHOLE_INDEX = 21;
    final int CURBDOWN_INDEX = 22;
    final int PERSON_INDEX = 23;
    final int SICK_PERSON_INDEX = 24;
}
public class CityView extends View implements View.OnTouchListener {

    int[][] cityMap = {	{16,16,21,10,20,16,16},
                            {16,16,4,10,12,16,16},
                            {1,2,3,6,13,2,19},
                            {9,9,5,11,7,9,9},
                            {17,22,15,8,14,22,18}};

    int currRow = 3;
    int currCol = 0;
    int totalRows = cityMap.length;
    int totalCols = cityMap[0].length;

    final int MAP_VIEW = 5;                 //5 x 5 Map view
    final int PLAYER_POS = MAP_VIEW / 2;    //Where the plaer is relative to the Map view size
    CityMapIndexes cmi;
    CityLoader loader;                    //Helps load images
    boolean loaded = false;                 //OnDraw loading helper
    Rect[][] rMap;                          //Tile holder
    Rect player;                            //Player tile
    Bitmap[] images;                        //Tile res
    boolean isSick = false;
    Paint paint;                            //Generic Rect helper
    final int NUM_OF_IMAGES = 25;

    public CityView(Context context) {
        super(context);
        //Log.i("CityView", "Your city has been built");
        setup();
    }
    public CityView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //Log.i("CityView", "Your city has been built");
        setup();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return true;
    }

    @Override
    public boolean performClick(){
        super.performClick();
        return true;
    }
    public void onDraw(Canvas canvas){
        if(loader != null && loader.getStatus() == AsyncTask.Status.FINISHED) {
            //Log.i("bl", "Passed if in MV onDraw");
            super.onDraw(canvas);
            int h = getHeight();
            int w = getWidth();
            int sight = MAP_VIEW / 2;

            if (!loaded) {
                loaded = true;
                for (int i = 0; i < MAP_VIEW; i++) {
                    for (int j = 0; j < MAP_VIEW; j++) {
                        int left = j * getWidth() / MAP_VIEW;
                        int top = i * getHeight() / MAP_VIEW;
                        int right = (j + 1) * getWidth() / MAP_VIEW;
                        int bottom = (i + 1) * getHeight() / MAP_VIEW;
                        rMap[i][j] = new Rect(left, top, right, bottom);
                    }
                }
                player = new Rect(w / MAP_VIEW * PLAYER_POS, h / MAP_VIEW * PLAYER_POS,
                        w / MAP_VIEW * (PLAYER_POS + 1), h / MAP_VIEW * (PLAYER_POS + 1));
            }

            for (int i = 0; i < MAP_VIEW; i++) {
                for (int j = 0; j < MAP_VIEW; j++) {
                    int irow = currRow + (i - sight);
                    int icol = currCol + (j - sight);
                    int imgIndex = cmi.OOB_INDEX;

                    if (irow >= 0 && irow < totalRows && icol >= 0 && icol < totalCols) { imgIndex = cityMap[irow][icol]; }

                    canvas.drawBitmap(images[imgIndex], null, rMap[i][j], paint);
                }
            }
            if(isSick){
                canvas.drawBitmap(images[cmi.SICK_PERSON_INDEX], null, player, paint);
            }else{
                canvas.drawBitmap(images[cmi.PERSON_INDEX], null, player, paint);
            }
        }else{
            invalidate();
        }
    }

    private void setup(){
        images = new Bitmap[NUM_OF_IMAGES];
        cmi = new CityMapIndexes();
        loader = new CityLoader(this.getContext(), images);
        loader.execute();
        rMap = new Rect[totalRows][totalCols];
        paint = new Paint(Color.RED);
        invalidate();
    }

    public void move(float x, float y){
        if(y <= getHeight() / 3.0 && currRow > 0){                    moveUp(); }
        else if(y <= 2*getHeight()/ 3.0){
            if(x <= getWidth()/2.0 && currCol > 0){                   moveLeft(); }
            else if(x > getWidth()/2.0 &&currCol < totalCols - 1){    moveRight(); }
            else{ Log.i("Oh no", "You can't go that way!"); }
        }else if(y <= getHeight() && currRow < totalRows - 1){      moveDown(); }
        else{ Log.i("Oh no", "You can't go that way!"); }

        invalidate();
    }
    public void moveUp(){
        if(cityMap[currRow - 1][currCol] != cmi.BUILDING_INDEX) {
            currRow--;
        }
    }
    public void moveDown(){
        if(cityMap[currRow + 1][currCol] != cmi.BUILDING_INDEX) {
            currRow++;
        }
    }
    public void moveLeft(){
        if(cityMap[currRow][currCol - 1] != cmi.BUILDING_INDEX) {
            currCol--;
        }
    }
    public void moveRight(){
        if(cityMap[currRow][currCol + 1] != cmi.BUILDING_INDEX) {
            currCol++;
        }
    }

    public boolean canTravelToOverMap(){ return (cityMap[currRow][currCol] == cmi.CURBUPMANHOLE_INDEX); }
    public boolean canTravelToBar(){ return (cityMap[currRow][currCol] == cmi.CURBUPDOOR_INDEX); }
    public boolean canGrabCoin(){
        if(cityMap[currRow][currCol] == cmi.CURBDOWNCOIN_INDEX){
            cityMap[currRow][currCol] = 22;
            invalidate();
            return true;
        }else{
            return false;
        }
    }
    public boolean canTouchRat(){
        if(cityMap[currRow][currCol] == cmi.CURBDOWNRAT_INDEX){
            isSick = true;
            invalidate();
            return true;
        }else{
            return false;
        }
    }
    public void resetPosition(){
        currRow = 2;
        currCol = 0;
    }

}
