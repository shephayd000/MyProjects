package com.example.betteradventureams;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

public class LoadingBuddy extends AsyncTask<Bitmap[], Void, Bitmap[]> {
    private Context context;
    private Bitmap[] bm;
    LoadingBuddy(Context c, Bitmap[] bm){
        context = c;
        this.bm = bm;
    }
    @Override
    protected Bitmap[] doInBackground(Bitmap[]... bitmaps) {
        bm[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.mountain);
        bm[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.out);
        bm[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.person);
        bm[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.plain);
        bm[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.btreasure);
        bm[5] = BitmapFactory.decodeResource(context.getResources(), R.drawable.water);
        bm[6] = BitmapFactory.decodeResource(context.getResources(), R.drawable.forest);
        bm[7] = BitmapFactory.decodeResource(context.getResources(), R.drawable.city);
        bm[8] = BitmapFactory.decodeResource(context.getResources(), R.drawable.cutdown);
        bm[9] = BitmapFactory.decodeResource(context.getResources(), R.drawable.logs);
        bm[10] = BitmapFactory.decodeResource(context.getResources(), R.drawable.sand);
        bm[11] = BitmapFactory.decodeResource(context.getResources(), R.drawable.cactidesert);
        bm[12] = BitmapFactory.decodeResource(context.getResources(), R.drawable.sickperson);
        bm[13] = BitmapFactory.decodeResource(context.getResources(), R.drawable.win);
        return bm;
    }
}
