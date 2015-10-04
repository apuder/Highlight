package org.puder.highlight;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

public class HighlightView extends RelativeLayout {

    private static final int ALPHA_60_PERCENT           = 153;
    private final float      innerRadiusScaleMultiplier = 1.2f;
    private final float      outerRadiusScaleMultiplier = 1.8f;

    private Paint            eraserPaint;

    private HighlightItem    item;
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
        addButton(context);
    }

    public void setHighlightItem(HighlightItem item, int left, int top, int right, int bottom) {
        this.item = item;
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        invalidate();
    }

    private void addButton(Context context) {
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        int margin = (int) context.getResources().getDimension(R.dimen.default_button_margin);
        params.setMargins(margin, margin, margin, margin);
        Button b = new Button(context);
        // noinspection ResourceType
        b.setId(1);
        b.setLayoutParams(params);
        b.setText(R.string.default_button_label);
        addView(b);
    }

    @Override
    public void setOnClickListener(OnClickListener listener) {
        // Delegate the click listener to the button
        // noinspection ResourceType
        findViewById(1).setOnClickListener(listener);
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
