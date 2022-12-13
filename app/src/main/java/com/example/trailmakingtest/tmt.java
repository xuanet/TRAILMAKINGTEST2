package com.example.trailmakingtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.*;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.*;
import android.widget.EditText;
import java.lang.Math;

public class tmt extends AppCompatActivity {

    public static long start;
    public static long end;

    DrawingView dv;
    private Paint mPaint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dv = new DrawingView(this);
        setContentView(dv);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(6);
    }

    public void displayTime(View view) {
        int total = (int) (end - start);
        EditText edtTime = findViewById(R.id.Time);
        edtTime.setText(String.valueOf(total) + " ms");
    }

    public class DrawingView extends View {

        private Bitmap mBitmap;
        private Canvas  mCanvas;
        private Path    mPath;
        private Paint   mBitmapPaint;
        Context context;
        private Paint circlePaint;
        private Path circlePath;

        private float radius;
        private float[][] circleArray;
        private int currentIndex;

        public DrawingView(Context c) {
            super(c);
            context=c;
            mPath = new Path();
            mBitmapPaint = new Paint(Paint.DITHER_FLAG);
            circlePaint = new Paint();
            circlePath = new Path();
            circlePaint.setAntiAlias(true);
            circlePaint.setColor(Color.BLUE);
            circlePaint.setStyle(Paint.Style.STROKE);
            circlePaint.setStrokeJoin(Paint.Join.MITER);
            circlePaint.setStrokeWidth(4f);

            // Specific to this test
            currentIndex = 0;
            this.radius = 60;
            this.circleArray = new float[8][];
            circleArray[0] = new float[] {521, 266};
            circleArray[1] = new float[] {86, 340};
            circleArray[2] = new float[] {265, 78};
            circleArray[3] = new float[] {730, 328};
            circleArray[4] = new float[] {578, 528};
            circleArray[5] = new float[] {196, 654};
            circleArray[6] = new float[] {712, 782};
            circleArray[7] = new float[] {66, 904};
        }

        private boolean success(float xpos, float ypos, int index, float radius) {
            float circleX = circleArray[index][0];
            float circleY = circleArray[index][1];
            float deltax = circleX - xpos;
            float deltay = circleY - ypos;
            float distance = (float) Math.pow((Math.pow(deltax,2) + Math.pow(deltay,2)), 0.5);
            return distance < radius;
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            Bitmap workingBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.tmt_sample_small);
            mBitmap = workingBitmap.copy(Bitmap.Config.ARGB_8888, true);
            mBitmap = Bitmap.createScaledBitmap(mBitmap, 800, 1080, true);
            mCanvas = new Canvas(mBitmap);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawBitmap( mBitmap, 0, 0, mBitmapPaint);
            canvas.drawPath( mPath,  mPaint);
            canvas.drawPath( circlePath,  circlePaint);
        }

        private float mX, mY;
        private static final float TOUCH_TOLERANCE = 4;

        private void touch_start(float x, float y) {
            mPath.reset();
            mPath.moveTo(x, y);
            mX = x;
            mY = y;
        }

        private void touch_move(float x, float y) {
            float dx = Math.abs(x - mX);
            float dy = Math.abs(y - mY);
            if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
                mPath.quadTo(mX, mY, (x + mX)/2, (y + mY)/2);
                mX = x;
                mY = y;
                circlePath.reset();
                circlePath.addCircle(mX, mY, 30, Path.Direction.CW);

//                Log.i("location", "XPOS " + String.valueOf(x));
//                Log.i("location", "YPOS " + String.valueOf(y));
//                Log.i("tag", String.valueOf(currentIndex));

                if (currentIndex == 0 && success(x, y, currentIndex, radius)) tmt.start = SystemClock.elapsedRealtime();

                if (success(x, y, currentIndex, radius)) {
                    if (mPaint.getColor() == -16711936 && currentIndex!=0) {
                        mPaint.setColor(Color.RED);
                    }
                    else mPaint.setColor(Color.GREEN);

                    if (currentIndex == circleArray.length-1) {
                        tmt.end = SystemClock.elapsedRealtime();
                        setContentView(R.layout.activity_tmt);
                    }

                    if (currentIndex!=circleArray.length-1) {
                        currentIndex++;
                    }
                }
            }
        }

        private void touch_up() {
            mPath.lineTo(mX, mY);
            circlePath.reset();
            // commit the path to our offscreen
            mCanvas.drawPath(mPath,  mPaint);
            // kill this so we don't double draw
            mPath.reset();
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float x = event.getX();
            float y = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    touch_start(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_MOVE:
                    touch_move(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    touch_up();
                    invalidate();
                    break;
            }
            return true;
        }
    }
}