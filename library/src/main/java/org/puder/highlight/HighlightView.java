package org.puder.highlight;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.widget.Button;
import android.widget.RelativeLayout;

public class HighlightView extends RelativeLayout {

    private static final int ALPHA_60_PERCENT           = 153;
    private final float      innerRadiusScaleMultiplier = 1.2f;
    private final float      outerRadiusScaleMultiplier = 1.8f;

    private Paint            eraserPaint;

    private int              left;
    private int              top;
    private int              right;
    private int              bottom;


    public HighlightView(Context context) {
        super(context);
        PorterDuffXfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY);
        eraserPaint = new Paint();
        eraserPaint.setColor(0xFFFFFF);
        eraserPaint.setAlpha(0);
        eraserPaint.setXfermode(xfermode);
        eraserPaint.setAntiAlias(true);
        Button b = new Button(context);
        b.setText("Hello");
        addView(b);
    }

    public void setLocation(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        invalidate();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        int[] location = new int[2];
        getLocationOnScreen(location);
        int width = right - left;
        int height = bottom - top;
        int cx = left + width / 2 - location[0];
        int cy = top + height / 2 - location[1];
        float radius = width > height ? width / 2 : height / 2;
        Bitmap overlay = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas bufferCanvas = new Canvas(overlay);
        bufferCanvas.drawColor(0xcc3d4353);// 0x3333B5E5);
        eraserPaint.setAlpha(ALPHA_60_PERCENT);
        bufferCanvas.drawCircle(cx, cy, radius * outerRadiusScaleMultiplier, eraserPaint);
        eraserPaint.setAlpha(0);
        bufferCanvas.drawCircle(cx, cy, radius * innerRadiusScaleMultiplier, eraserPaint);
        canvas.drawBitmap(overlay, 0, 0, new Paint());
        super.dispatchDraw(canvas);
    }
}
