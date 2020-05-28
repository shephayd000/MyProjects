package com.example.betteradventureams;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

public class BarLoad extends AsyncTask<Bitmap[], Void, Bitmap[]> {
    Context context;
    private Bitmap[] bm;

     BarLoad(Context c, Bitmap[] bm){
         context = c;
         this.bm = bm;
     }

    @Override
    protected Bitmap[] doInBackground(Bitmap[]... bitmaps) {
        bm[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.playerintavern);
        bm[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.exitarrow);
        bm[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.greetbarmessage);
        bm[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.buybutton);
        bm[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.acqtile);
        return bm;
    }
}
