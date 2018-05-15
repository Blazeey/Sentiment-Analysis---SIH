package com.blazeey.sentimentanalysis.Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

import com.blazeey.sentimentanalysis.R;

import java.util.List;

/**
 * Created by venki on 24/3/18.
 */

public class IndiaMap {

    private List<State> influenceMap;
    private Context context;

    public IndiaMap(Context context,List<State> influenceMap) {
        this.influenceMap = influenceMap;
        this.context = context;
    }

    public Bitmap createMap(){
//        Bitmap tempBitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.maps);
        Bitmap backgroundMap = BitmapFactory.decodeResource(context.getResources(),R.drawable.names);
        for(State state: influenceMap){

            Bitmap image;
            Log.v("State",state.getName());
            if(state.getResult() == State.Result.POSITIVE) {
                image = BitmapFactory.decodeResource(context.getResources(), state.getImagePositive());
            }
            else if(state.getResult() == State.Result.NEGATIVE) {
                image = BitmapFactory.decodeResource(context.getResources(), state.getImageNegative());
            }
            else if(state.getResult() == State.Result.NEUTRAL){
                image = BitmapFactory.decodeResource(context.getResources(), state.getImageNeutral());
            }
            else{
                image = BitmapFactory.decodeResource(context.getResources(), state.getImageNone());
            }
            Log.v("Image",image.toString());
            backgroundMap = overlayImages(image,backgroundMap);
        }

        return backgroundMap;
    }

    private Bitmap overlayImages(Bitmap overlay,Bitmap backgroundMap){
        Bitmap result = Bitmap.createBitmap(backgroundMap.getWidth(),backgroundMap.getHeight(),backgroundMap.getConfig());
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(backgroundMap,0.0f,0.0f,null);
        canvas.drawBitmap(overlay,10.0f,10.0f,null);
        return result;
    }


}
