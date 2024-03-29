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

public class tmt_large2 extends AppCompatActivity {

    public static long start;
    public static long end;

    DrawingView dv;
    private Paint mPaint;
    public long[] timer = new long[24];
    public boolean mistake = false;
    public boolean hasLifted = false;
    int maxTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Arrays.fill(timer, -1);
        start = SystemClock.elapsedRealtime();
        maxTime = 300000;
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
            Toast.makeText(tmt_large2.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public void displayTime(View view) {
        int total = (int) (end - start);
        EditText edtTime = findViewById(R.id.Time);
        edtTime.setText(String.valueOf(total) + " ms");
//        edtTime.setText("Test complete, thank you!");
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
//            this.circleArray = new float[24][];
//            circleArray[0] = new float[] {408, 474};
//            circleArray[1] = new float[] {558, 805};
//            circleArray[2] = new float[] {289, 775};
//            circleArray[3] = new float[] {367, 200};
//            circleArray[4] = new float[] {455, 330};
//            circleArray[5] = new float[] {561, 562};
//            circleArray[6] = new float[] {564, 155};
//            circleArray[7] = new float[] {677, 167};
//            circleArray[8] = new float[] {672 ,632};
//            circleArray[9] = new float[] {624, 893};
//            circleArray[10] = new float[] {430, 863};
//            circleArray[11] = new float[] {242, 965};
//            circleArray[12] = new float[] {189, 486};
//            circleArray[13] = new float[] {123, 612};
//            circleArray[14] = new float[] {98, 67};
//            circleArray[15] = new float[] {214, 298};
//            circleArray[16] = new float[] {211, 139};
//            circleArray[17] = new float[] {496, 54};
//            circleArray[18] = new float[] {740, 67};
//            circleArray[19] = new float[] {702, 733};
//            circleArray[20] = new float[] {742, 985};
//            circleArray[21] = new float[] {58, 1017};
//            circleArray[22] = new float[] {43, 531};
//            circleArray[23] = new float[] {136, 805};

            this.circleArray = new float[24][];

            circleArray[0] = new float[] {610, 624};
            circleArray[1] = new float[] {829, 1064};
            circleArray[2] = new float[] {447, 1014};
            circleArray[3] = new float[] {562, 270};

            circleArray[4] = new float[] {683, 442};
            circleArray[5] = new float[] {845, 743};
            circleArray[6] = new float[] {838, 211};
            circleArray[7] = new float[] {1013, 211};

            circleArray[8] = new float[] {980, 823};
            circleArray[9] = new float[] {923, 1181};
            circleArray[10] = new float[] {648, 1126};
            circleArray[11] = new float[] {375, 1265};

            circleArray[12] = new float[] {276, 631};
            circleArray[13] = new float[] {185, 805};
            circleArray[14] = new float[] {146, 83};
            circleArray[15] = new float[] {329, 393};

            circleArray[16] = new float[] {311, 184};
            circleArray[17] = new float[] {747, 80};
            circleArray[18] = new float[] {1099, 92};
            circleArray[19] = new float[] {1077, 968};

            circleArray[20] = new float[] {1099, 1291};
            circleArray[21] = new float[] {96, 1347};
            circleArray[22] = new float[] {74, 700};
            circleArray[23] = new float[] {216, 1054};
        }



        private boolean success(float xpos, float ypos, int index, float radius) {
            if (index < 0) {
                return false;
            }
            float circleX = circleArray[index][0];
            float circleY = circleArray[index][1];
            float deltax = circleX - xpos;
            float deltay = circleY - ypos;
            float distance = (float) Math.pow((Math.pow(deltax,2) + Math.pow(deltay,2)), 0.5);
            return distance < radius;
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {

            // 1620: 1942

            super.onSizeChanged(w, h, oldw, oldh);
            Bitmap workingBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bfull);
            mBitmap = workingBitmap.copy(Bitmap.Config.ARGB_8888, true);
            mBitmap = Bitmap.createScaledBitmap(mBitmap, (int) (800*scale), (int) (800*1942/1620*scale), true);
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

            if (SystemClock.elapsedRealtime() - tmt_large2.start > maxTime) {
                tmt_large2.end = -1;
                setContentView(R.layout.activity_tmt_large2);
            }
            Log.i("xpos", String.valueOf(x));
            Log.i("ypos", String.valueOf(y));
            Log.i("index", String.valueOf(currentIndex));

            float dx = Math.abs(x - mX);
            float dy = Math.abs(y - mY);
            if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
                mPath.quadTo(mX, mY, (x + mX)/2, (y + mY)/2);
                mX = x;
                mY = y;
                circlePath.reset();
                circlePath.addCircle(mX, mY, 30, Path.Direction.CW);

                Log.i("location", "XPOS " + String.valueOf(x));
                Log.i("location", "YPOS " + String.valueOf(y));
//                Log.i("tag", String.valueOf(currentIndex));

                if (currentIndex == 0 && success(x, y, currentIndex, radius)) {
                    tmt_large2.start = SystemClock.elapsedRealtime();
                }

                if (success(x, y, currentIndex, radius)) {

                    hasLifted = false;
                    mCanvas.drawPath(mPath,  mPaint);               // retains old correct lines
                    touch_start(x, y);

                    mistake = false;
                    mPaint.setColor(Color.GREEN);

                    if (timer[currentIndex] < 0) {
                        timer[currentIndex] = SystemClock.elapsedRealtime()-tmt_large2.start;
                    }
                    if (currentIndex == circleArray.length-1) {
                        tmt_large2.end = SystemClock.elapsedRealtime();
                        setContentView(R.layout.activity_tmt_large2);
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
                        if (!mistake && currentIndex > 0) {
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