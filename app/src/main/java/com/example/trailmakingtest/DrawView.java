package com.example.trailmakingtest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

public class DrawView extends View {

    private Paint bufferPaint;
    private Bitmap buffer;
    private Canvas board;
    private Path currPath;

    public DrawView(Context context) {
        super(context);
        bufferPaint = new Paint(Paint.DITHER_FLAG);
    }

    public void init() {
        buffer = Bitmap.createBitmap(getWidth(), getHeight(),
                Bitmap.Config.ARGB_8888);
        board = new Canvas(buffer);
        invalidate();
    }

    public void drawBitmap(Bitmap bitmap) {
        board.drawBitmap(bitmap, 0, 0, bufferPaint);
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (buffer != null) {
            canvas.drawBitmap(buffer, 0, 0, bufferPaint);
        }
    }

//    public void drawImage(Bitmap image) {
//        drawCanvas.drawBitmap(image, 0, 0, canvasPaint);
//        invalidate();
//    }
}