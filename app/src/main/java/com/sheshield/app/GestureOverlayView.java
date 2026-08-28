package com.sheshield.app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class GestureOverlayView extends View {

    public interface OnPatternMatchedListener {
        void onPatternMatched();
    }

    private OnPatternMatchedListener listener;
    private final Path path = new Path();
    private final Paint paint = new Paint();
    private final List<Point> strokePoints = new ArrayList<>();
    private float startX, startY;

    public GestureOverlayView(Context context) {
        super(context);
        init();
    }

    public GestureOverlayView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GestureOverlayView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint.setColor(Color.argb(80, 233, 30, 99));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(12f);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setAntiAlias(true);
    }

    public void setOnPatternMatchedListener(OnPatternMatchedListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = x;
                startY = y;
                path.reset();
                strokePoints.clear();
                path.moveTo(x, y);
                strokePoints.add(new Point(x, y));
                return false;

            case MotionEvent.ACTION_MOVE:
                float dx = Math.abs(x - startX);
                float dy = Math.abs(y - startY);
                if (dx > 20 || dy > 20) {
                    if (getParent() != null) {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                    path.lineTo(x, y);
                    strokePoints.add(new Point(x, y));
                    invalidate();
                    return true;
                }
                break;

            case MotionEvent.ACTION_UP:
                if (isZGesture(strokePoints) && listener != null) {
                    listener.onPatternMatched();
                }
                path.reset();
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }

    private boolean isZGesture(List<Point> points) {
        if (points.size() < 12) return false;

        Point start = points.get(0);
        Point end = points.get(points.size() - 1);

        float minX = Float.MAX_VALUE, maxX = Float.MIN_VALUE;
        float minY = Float.MAX_VALUE, maxY = Float.MIN_VALUE;

        for (Point p : points) {
            if (p.x < minX) minX = p.x;
            if (p.x > maxX) maxX = p.x;
            if (p.y < minY) minY = p.y;
            if (p.y > maxY) maxY = p.y;
        }

        float width = maxX - minX;
        float height = maxY - minY;

        if (width < 120 || height < 120) return false;

        boolean startsTopLeft = (start.x < minX + width * 0.45) && (start.y < minY + height * 0.45);
        boolean endsBottomRight = (end.x > maxX - width * 0.45) && (end.y > maxY - height * 0.45);

        return startsTopLeft && endsBottomRight;
    }

    private static class Point {
        float x, y;
        Point(float x, float y) { this.x = x; this.y = y; }
    }
}