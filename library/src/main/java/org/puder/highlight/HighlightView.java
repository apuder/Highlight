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

    private static final int ALPHA_60_PERCENT = 153;
    private final float      outerRadius      = 150;
    private final float      innerRadius      = 90;
    private final float      scaleMultiplier  = 1;

    private Paint            eraserPaint;

    private int              cx;
    private int              cy;


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

    public void setLocation(int cx, int cy) {
        this.cx = cx;
        this.cy = cy;
        invalidate();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        int[] location = new int[2];
        getLocationOnScreen(location);
        int x = cx - location[0];
        int y = cy - location[1];

        Bitmap overlay = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas bufferCanvas = new Canvas(overlay);
        bufferCanvas.drawColor(0xcc3d4353);// 0x3333B5E5);
        eraserPaint.setAlpha(ALPHA_60_PERCENT);
        bufferCanvas.drawCircle(x, y, outerRadius * scaleMultiplier, eraserPaint);
        eraserPaint.setAlpha(0);
        bufferCanvas.drawCircle(x, y, innerRadius * scaleMultiplier, eraserPaint);
        canvas.drawBitmap(overlay, 0, 0, new Paint());
        super.dispatchDraw(canvas);
    }
}
