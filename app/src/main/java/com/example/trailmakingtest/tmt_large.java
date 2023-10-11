package com.example.trailmakingtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.*;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.*;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;

public class tmt_large extends AppCompatActivity {

    public static long start;
    public static long end;

    DrawingView dv;
    private Paint mPaint;
    public long[] timer = new long[] {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
    public boolean mistake = false;
    public boolean hasLifted = false;

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
        mPaint.setStrokeWidth(1);
    }

    protected void sendEmail(int total) {
        Log.i("Send email", "");

        String[] TO = {"ackertmt@gmail.com"};
        String[] CC = {"kx33@duke.edu"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");


        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        String FIRSTNAME = MainActivity.firstName;
        String LASTNAME = MainActivity.lastName;
        int TEST = MainActivity.whichTest;
        String TIME = String.valueOf(total) + " ms";
        String COMBINED =  FIRSTNAME + " " + LASTNAME + "\n" + "Test number: " + String.valueOf(TEST) + "\n" + TIME;
        emailIntent.putExtra(Intent.EXTRA_TEXT, COMBINED);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Sent email...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(tmt_large.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public void displayTime(View view) {
        int total = (int) (end - start);
        EditText edtTime = findViewById(R.id.Time);
        edtTime.setText(String.valueOf(total) + " ms");
//        edtTime.setText("Thank You!");
        Log.i("time_array", Arrays.toString(timer));
//        sendEmail(total);
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

        float scale = 1.5F;

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
            this.radius = 30*scale;
            this.circleArray = new float[25][];
            circleArray[0] = new float[] {491, 775};
            circleArray[1] = new float[] {379, 894};
            circleArray[2] = new float[] {569, 900};
            circleArray[3] = new float[] {591, 410};
            circleArray[4] = new float[] {367, 528};
            circleArray[5] = new float[] {475, 638};
            circleArray[6] = new float[] {331, 706};
            circleArray[7] = new float[] {150, 883};
            circleArray[8] = new float[] {187 ,978};
            circleArray[9] = new float[] {280, 883};
            circleArray[10] = new float[] {364, 1007};
            circleArray[11] = new float[] {57, 1030};
            circleArray[12] = new float[] {110, 570};
            circleArray[13] = new float[] {45, 670};
            circleArray[14] = new float[] {40, 128};
            circleArray[15] = new float[] {122, 311};
            circleArray[16] = new float[] {267, 60};
            circleArray[17] = new float[] {314, 316};
            circleArray[18] = new float[] {505, 220};
            circleArray[19] = new float[] {369, 207};
            circleArray[20] = new float[] {413, 51};
            circleArray[21] = new float[] {727, 159};
            circleArray[22] = new float[] {757, 978};
            circleArray[23] = new float[] {666, 556};
            circleArray[24] = new float[] {628, 1014};
        }

        private boolean success(float xpos, float ypos, int index, float radius) {
            if (index < 0) {
                return false;
            }
            float circleX = circleArray[index][0]*scale;
            float circleY = circleArray[index][1]*scale;
            float deltax = circleX - xpos;
            float deltay = circleY - ypos;
            float distance = (float) Math.pow((Math.pow(deltax,2) + Math.pow(deltay,2)), 0.5);
            return distance < radius;
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            Bitmap workingBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.tmt_large);
            mBitmap = workingBitmap.copy(Bitmap.Config.ARGB_8888, true);
            mBitmap = Bitmap.createScaledBitmap(mBitmap, (int) (800*scale), (int) (1080*scale), true);
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
//            Log.i("xpos", String.valueOf(x));
//            Log.i("ypos", String.valueOf(y));

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

                if (currentIndex == 0 && success(x, y, currentIndex, radius)) {
                    tmt_large.start = SystemClock.elapsedRealtime();
                }

                if (success(x, y, currentIndex, radius)) {

                    hasLifted = false;
                    mCanvas.drawPath(mPath,  mPaint);               // retains old correct lines
                    touch_start(x, y);

                    mistake = false;
                    mPaint.setColor(Color.GREEN);

                    if (timer[currentIndex] < 0) {
                        timer[currentIndex] = SystemClock.elapsedRealtime()-tmt_large.start;
                    }
                    if (currentIndex == circleArray.length-1) {
                        tmt_large.end = SystemClock.elapsedRealtime();
                        setContentView(R.layout.activity_tmt_large);
                    }
                    else {
                        currentIndex++;
                    }
                }

                for (int i = 0; i < circleArray.length; i++) {
                    if (i == currentIndex - 1) {
                        continue;
                    }
                    if (success(x, y, i, radius)) {
                        mPaint.setColor(Color.RED);
                        if (!mistake && !hasLifted && currentIndex > 0) {
                            currentIndex--;
                            mistake = true;
                        }
                        break;
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
                    if (mPaint.getColor() == Color.GREEN && currentIndex != 0 && !hasLifted) {
                        currentIndex--;
                        hasLifted = true;
                    }
                    touch_up();
                    invalidate();
                    break;
            }
            return true;
        }
    }
}