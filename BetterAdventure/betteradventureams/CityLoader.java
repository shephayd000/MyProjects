package com.example.betteradventureams;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

public class CityLoader extends AsyncTask<Bitmap[], Void, Bitmap[]> {

    private Bitmap[] bm;
    private Context context;

    CityLoader(Context c, Bitmap[] bm){
        context = c;
        this.bm = bm;
    }

    @Override
    protected Bitmap[] doInBackground(Bitmap[]... bitmaps) {
        bm[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.out);
        bm[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.curb1);
        bm[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.curb2);
        bm[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.tlcorner);
        bm[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.curbup);
        bm[5] = BitmapFactory.decodeResource(context.getResources(), R.drawable.cross1);
        bm[6] = BitmapFactory.decodeResource(context.getResources(), R.drawable.cross2);
        bm[7] = BitmapFactory.decodeResource(context.getResources(), R.drawable.cross3);
        bm[8] = BitmapFactory.decodeResource(context.getResources(), R.drawable.cross4);
        bm[9] = BitmapFactory.decodeResource(context.getResources(), R.drawable.street);
        bm[10] = BitmapFactory.decodeResource(context.getResources(), R.drawable.streetup);
        bm[11] = BitmapFactory.decodeResource(context.getResources(), R.drawable.intersection);
        bm[12] = BitmapFactory.decodeResource(context.getResources(), R.drawable.streetupright);
        bm[13] = BitmapFactory.decodeResource(context.getResources(), R.drawable.trcorner);
        bm[14] = BitmapFactory.decodeResource(context.getResources(), R.drawable.brcorner);
        bm[15] = BitmapFactory.decodeResource(context.getResources(), R.drawable.blcorner);
        bm[16] = BitmapFactory.decodeResource(context.getResources(), R.drawable.buildingtile);
        bm[17] = BitmapFactory.decodeResource(context.getResources(), R.drawable.curbdowncoin);
        bm[18] = BitmapFactory.decodeResource(context.getResources(), R.drawable.curbdownrat);
        bm[19] = BitmapFactory.decodeResource(context.getResources(), R.drawable.streetdoor);
        bm[20] = BitmapFactory.decodeResource(context.getResources(), R.drawable.curbupdoor);
        bm[21] = BitmapFactory.decodeResource(context.getResources(), R.drawable.curbupmanhole);
        bm[22] = BitmapFactory.decodeResource(context.getResources(), R.drawable.curbdown);
        bm[23] = BitmapFactory.decodeResource(context.getResources(), R.drawable.person);
        bm[24] = BitmapFactory.decodeResource(context.getResources(), R.drawable.sickperson);
        return bm;
    }
}
