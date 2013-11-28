package de.uniulm.mmci.assign02;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by sebastian on 22.11.13.
 */
public class GraphicView extends View {
    private Paint  paint = new Paint();
    private int cx = 0;
    private int cy = 0;
    private final int xInit = 200;
    private final int yInit = 200;
    private float r = 80.0f;


    public GraphicView(Context context) {
        super(context);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(cx,cy,r,paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_UP:
                paint.setColor(Color.TRANSPARENT);
                break;
            case MotionEvent.ACTION_DOWN:
                cx = (int) event.getX();
                cy = (int) event.getY();
                paint.setColor(Color.RED);
                break;
            case MotionEvent.ACTION_MOVE:
                cx = (int) event.getX();
                cy = (int) event.getY();
                break;
            case MotionEvent.ACTION_CANCEL:
                paint.setColor(Color.TRANSPARENT);
                break;
        }
        invalidate();
        return true;
    }
}
