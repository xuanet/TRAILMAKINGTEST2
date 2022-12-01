package com.example.trailmakingtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.*;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;

//import static com.example.trailmakingtest.display.colorList;
//import static com.example.trailmakingtest.display.current_brush;
//import static com.example.trailmakingtest.display.pathList;

public class tmtnovember181 extends AppCompatActivity {

//    private long start;
//    private long end;
//    private long startTime;
//    private long endTime;

//    public static Path path = new Path();
//    public static Paint paint_brush = new Paint();
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_tmt);
//    }
//
//
//    public void startStopwatch(View view) {
//        start = SystemClock.elapsedRealtime();
//    }
//
//    public void stopStopwatch(View view) {
//        end = SystemClock.elapsedRealtime();
//        int total = (int) (end - start);
//
//        EditText edtTime = findViewById(R.id.Time);
//
//        edtTime.setText(String.valueOf(total) + " ms");
//    }
//}

    DrawingView dv;
    private Paint mPaint;
    public long startTime;
    public long endTime;
    private Button button;

    private long start;
    private long end;

    public void onStartClick(View view) {
        startTime = SystemClock.elapsedRealtime();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tmt);
        dv = new DrawingView(this);
//        dv = new ConnectDotsView(this);
        setContentView(dv);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(12);

    }

    public void startStopwatch(View view) {
        start = SystemClock.elapsedRealtime();
    }

    public void stopStopwatch(View view) {
        end = SystemClock.elapsedRealtime();
        int total = (int) (end - start);

        EditText edtTime = findViewById(R.id.Time);

        edtTime.setText(String.valueOf(total) + " ms");
    }


    public class DrawingView extends View {

        public int width;
        public  int height;
        private Bitmap mBitmap;
        private Canvas  mCanvas;
        private Path    mPath;
        private Paint   mBitmapPaint;
        Context context;
        private Paint circlePaint;
        private Path circlePath;

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
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);

            mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
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
                Log.i("tag", "XPOS " + String.valueOf(x));
                Log.i("tag", "YPOS " + String.valueOf(y));
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