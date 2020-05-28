package com.example.betteradventureams;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends AppCompatActivity {
    MapView mv;
    CityView cv;
    BarView bv;

    boolean hasCoin = false;
    boolean hasAxe = false;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cv = new CityView(this);
        bv = new BarView(this);

        loadOverView();
        loadCityView();
        loadBarView();
    }

    void loadOverView(){
        mv = findViewById(R.id.mapview);
        mv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //Log.i("CS3680", "Touch event " + event.getX() + "," + event.getY());
                float nextX, nextY;
                mv.performClick();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    //Log.i("CS3680", "Touch event " + event.getX() + "," + event.getY());
                    nextX = event.getX();
                    nextY = event.getY();
                    if(mv.won){
                        Log.i("MA", "Finishing up");
                        finish();
                        System.exit(0);
                    }
                    else if ((nextY <= 2 * mv.getHeight() / 3.0) && (nextY > mv.getHeight() / 3.0) && (nextX > mv.getWidth() / 3.0 && nextX <= 2 * mv.getWidth() / 3.0)) {
                        //Log.i("MA","Trying to interact with mv listener");
                       if(mv.canTravelToCity()){
                           setContentView(cv);
                       }else if(mv.foundTreasure()){
                           //mv.setOnTouchListener(null);
                       }else if(hasAxe){
                           mv.canChopSomeTrees();
                       }
                    }else{
                        mv.move(nextX, nextY);
                    }
                }
                return true;
            }
        });
    }
    void loadCityView(){
        //cv = findViewById(R.id.cityview);
        cv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //Log.i("CS3680", "Touch event " + event.getX() + "," + event.getY());
                float nextX, nextY;
                cv.performClick();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    //Log.i("CS3680", "Touch event " + event.getX() + "," + event.getY());
                    nextX = event.getX();
                    nextY = event.getY();
                    if ((nextY <= 2 * cv.getHeight() / 3.0) && (nextY > cv.getHeight() / 3.0) && (nextX > cv.getWidth() / 3.0 && nextX <= 2 * cv.getWidth() / 3.0)) {
                        if(cv.canTravelToOverMap()){
                            cv.resetPosition();
                            ViewGroup p = (ViewGroup) mv.getParent();
                            if(p != null){
                                p.removeAllViews();
                            }
                            Log.i("MA","Going back to MV");
                            setContentView(mv);
                            mv.invalidate();
                        }else if(cv.canTravelToBar()){
                            //Log.i("Main", "City Listener: travelling to bar");
                            ViewGroup p = (ViewGroup) bv.getParent();
                            if(p != null){
                                p.removeAllViews();
                            }
                            setContentView(bv);
                            if(hasCoin && !hasAxe){
                                bv.addBuyOption();
                            }
                        }else if(cv.canGrabCoin()){
                            hasCoin = true;
                        } else if(cv.canTouchRat()){
                            if(!mv.isSick){
                                mv.makeSick();
                            }
                        }
                    }else{
                        cv.move(nextX, nextY);
                    }
                }
                return true;
            }
        });
    }
    void loadBarView(){
        bv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float x,y;
                bv.performClick();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    x = event.getX();
                    y = event.getY();
                    if(bv.tryingToExit(x,y)){
                        //Log.i("LBV", "Trying to leave bar");
                        ViewGroup p = (ViewGroup) cv.getParent();
                        if(p != null){
                            p.removeAllViews();
                        }
                        setContentView(cv);
                    }else if(bv.tryingToBuyAxe(x,y)){
                        hasAxe = true;
                    }
                }
                return true;
            }
        });
    }
}
