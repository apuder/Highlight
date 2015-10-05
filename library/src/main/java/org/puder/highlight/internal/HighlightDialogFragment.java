package org.puder.highlight.internal;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.WindowManager;

import org.puder.highlight.R;

public class HighlightDialogFragment extends DialogFragment {

    public interface HighlightDismissedListener {
        void onHighlightDismissed();
    }


    private HighlightDismissedListener listener;

    private HighlightItem              item;
    private int                        left;
    private int                        top;
    private int                        right;
    private int                        bottom;


    public void setHighlightItem(HighlightItem item, int left, int top, int right, int bottom) {
        this.item = item;
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    public void setListener(HighlightDismissedListener listener) {
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog overlayInfo = new Dialog(getActivity(), android.R.style.Theme_Black_NoTitleBar);
        // Making dialog content transparent.
        overlayInfo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // Removing window dim normally visible when dialog are shown.
        overlayInfo.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        overlayInfo.setContentView(R.layout.highlight);
        HighlightView highlightView = (HighlightView) overlayInfo.findViewById(R.id.highlight_item);
        highlightView.setHighlightItem(item, left, top, right, bottom);
        highlightView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissAllowingStateLoss();
                if (listener != null) {
                    listener.onHighlightDismissed();
                }
            }
        });
        return overlayInfo;
    }

    @Override
    public void onDestroyView() {
        /*
         * https://code.google.com/p/android/issues/detail?id=17423
         */
        if (getDialog() != null && getRetainInstance()) {
            getDialog().setDismissMessage(null);
        }
        super.onDestroyView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setCancelable(false);
        setHasOptionsMenu(false);
    }
}
