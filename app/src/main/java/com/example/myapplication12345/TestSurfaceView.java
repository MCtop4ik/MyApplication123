package com.example.myapplication12345;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.content.Context;

import java.util.Random;

public class TestSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable{

    float x = -100;
    float y = -100;
    float r = 200;
    float width_dp = 0;
    float height_dp = 0;

    private Bitmap bitmap;

    public TestSurfaceView(Context context) {
        super(context);
        getHolder().addCallback(this);
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.log);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){
        height_dp = height;
        width_dp = width;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        new Thread(this).start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        x = event.getX();
        y = event.getY();
        bitmap = rotateBitmap(bitmap, 90);
        return false;
    }

    public Bitmap rotateBitmap(Bitmap original, float degrees) {
        int width = original.getWidth();
        int height = original.getHeight();

        Matrix matrix = new Matrix();
        matrix.preRotate(degrees);

        Bitmap rotatedBitmap = Bitmap.createBitmap(original, 0, 0, width, height, matrix, true);
        Canvas canvas = new Canvas(rotatedBitmap);
        canvas.drawBitmap(original, 5.0f, 0.0f, null);

        return rotatedBitmap;
    }

    @Override
    public void run(){
        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(100);

        while(true){
            Canvas canvas = getHolder().lockCanvas();
            if (canvas!=null){
                try {
                    canvas.drawColor(Color.GRAY);
                    //canvas.drawCircle(width_dp/2, height_dp/2, r, paint);

                    canvas.drawBitmap(bitmap, width_dp/2-311, height_dp/2-317-100, paint);

                } finally {
                    getHolder().unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}
