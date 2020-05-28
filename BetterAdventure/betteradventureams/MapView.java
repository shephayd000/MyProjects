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

final class OverMapIndexes{
    final int MOUNTAIN_INDEX = 0;
    final int OUT_INDEX = 1;
    final int PERSON_INDEX = 2;
    final int PLAIN_INDEX = 3;
    final int TREASURE_INDEX = 4;
    final int WATER_INDEX = 5;
    final int FOREST_INDEX = 6;
    final int CITY_INDEX = 7;
    final int CHOPPED_INDEX = 8;
    final int LOG_INDEX = 9;
    final int SAND_INDEX = 10;
    final int CACTI_INDEX = 11;
    final int SICK_PERSON_INDEX = 12;
    final int WIN_INDEX = 13;
    final int NUM_OF_IMAGES = 14;
}

public class MapView extends View implements View.OnTouchListener{
    int[][] overMap = {{5,5,5,5,5,5,6,6,6,6,6},
                        {5,3,3,3,5,5,6,6,6,6,6},
                        {5,3,3,3,3,3,3,3,3,3,7},
                        {5,3,3,3,5,5,6,6,3,6,6},
                        {5,5,5,5,5,5,6,6,3,6,6},
                        {0,0,5,0,0,0,6,6,3,6,6},
                        {5,5,5,5,5,0,6,6,3,6,6},
                        {5,10,10,10,5,0,6,11,10,11,6},
                        {5,10,4,10,5,0,6,10,5,10,6},
                        {5,10,10,10,5,0,6,11,10,11,6},
                        {5,5,5,5,5,0,6,6,6,6,6}};

    final int MAP_VIEW = 7;                 //5 x 5 Map view
    final int PLAYER_POS = MAP_VIEW / 2;    //Where the plaer is relative to the Map view size
    OverMapIndexes omi;                     //Where to keep the map tile indexes
    LoadingBuddy loader;                    //Helps load images
    boolean loaded = false;                 //OnDraw loading helper
    Rect[][] rMap;                          //Tile holder
    Rect player;                            //Player tile
    Rect fin;
    Bitmap[] images;                        //Tile res
    int logs = 12;
    boolean isSick = false;
    boolean won = false;
    Paint paint;                            //Generic Rect helper

    int currRow = 2;
    int currCol = 2;
    int totalRows = overMap.length;
    int totalCols = overMap[0].length;

    public MapView(Context context) {
        super(context);
        setup();
    }
    public MapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) { return true; }

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
                fin = new Rect(getWidth() / 6, getHeight() / 3, 5 * getWidth() / 6, 3 * getHeight() / 4);
            }

            for (int i = 0; i < MAP_VIEW; i++) {
                for (int j = 0; j < MAP_VIEW; j++) {
                    int irow = currRow + (i - sight);
                    int icol = currCol + (j - sight);
                    int imgIndex = omi.OUT_INDEX;

                    if (irow >= 0 && irow < totalRows && icol >= 0 && icol < totalCols) { imgIndex = overMap[irow][icol]; }

                    canvas.drawBitmap(images[imgIndex], null, rMap[i][j], paint);
                }
            }

            if(isSick) {
                canvas.drawBitmap(images[omi.SICK_PERSON_INDEX], null, player, paint);
            }else{
                canvas.drawBitmap(images[omi.PERSON_INDEX], null, player, paint);
            }
            if(won){
                canvas.drawBitmap(images[omi.WIN_INDEX], null, fin, paint);
            }
        }else{
            Log.i("MV", "Loader really sucks");
            invalidate();
        }
    }

    private void setup(){
        omi = new OverMapIndexes();
        images = new Bitmap[omi.NUM_OF_IMAGES];
        loader = new LoadingBuddy(this.getContext(), images);

        loader.execute();
        rMap = new Rect[totalRows][totalCols];
        paint = new Paint(Color.RED);
        invalidate();
    }

    public void move(float x, float y){
            if(y <= getHeight() / 3.0 && currRow > 0){
                //Log.i("MV", "Move: moving up");
                moveUp(); }
            else if(y <= 2*getHeight()/ 3.0){
                if(x <= getWidth()/2.0 && currCol > 0){                   moveLeft(); }
                else if(x > getWidth()/2.0 &&currCol < totalCols - 1){    moveRight(); }
                else{ Log.i("Oh no", "You can't go that way!"); }
            }else if(y <= getHeight() && currRow < totalRows - 1){      moveDown(); }
            else{ Log.i("Oh no", "You can't go that way!"); }

            invalidate();
    }

    public void moveUp(){
        if(overMap[currRow - 1][currCol] != omi.MOUNTAIN_INDEX) {
            if (overMap[currRow - 1][currCol] != omi.WATER_INDEX) {
                currRow--;
            } else if (logs > 0) {
                logs--;
                overMap[currRow - 1][currCol] = omi.LOG_INDEX;
                currRow--;
            }
        }
    }
    public void moveDown(){
        if(overMap[currRow + 1][currCol] != omi.MOUNTAIN_INDEX) {
            if (overMap[currRow + 1][currCol] != omi.WATER_INDEX) {
                currRow++;
            } else if (logs > 0) {
                logs--;
                overMap[currRow + 1][currCol] = omi.LOG_INDEX;
                currRow++;
            }
        }
    }
    public void moveLeft(){
        if(overMap[currRow][currCol - 1] != omi.MOUNTAIN_INDEX) {
            if (overMap[currRow][currCol - 1] != omi.WATER_INDEX) {
                currCol--;
            } else if (logs > 0) {
                logs--;
                overMap[currRow][currCol - 1] = omi.LOG_INDEX;
                currCol--;
            }
        }
    }
    public void moveRight(){
        if(overMap[currRow][currCol + 1] != omi.MOUNTAIN_INDEX) {
            if (overMap[currRow][currCol + 1] != omi.WATER_INDEX) {
                currCol++;
            } else if (logs > 0) {
                logs--;
                overMap[currRow][currCol + 1] = omi.LOG_INDEX;
                currCol++;
            }
        }
    }

    public boolean canTravelToCity(){ return (overMap[currRow][currCol] == omi.CITY_INDEX); }
    public void canChopSomeTrees(){
        if(overMap[currRow][currCol] == omi.FOREST_INDEX){
            overMap[currRow][currCol] = omi.CHOPPED_INDEX;
            logs++;
            invalidate();
        }
    }
    public void makeSick(){
        Log.i("MV", "makeSick called");
        isSick = true;
    }
    public boolean foundTreasure(){
        if(overMap[currRow][currCol] == omi.TREASURE_INDEX){
            Log.i("MV","Treasure found");
            won = true;
            invalidate();
            return true;
        }else{
            return false;
        }
    }


}
