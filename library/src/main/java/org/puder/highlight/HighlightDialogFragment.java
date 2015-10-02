package org.puder.highlight;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.WindowManager;

public class HighlightDialogFragment extends DialogFragment {

    public interface HighlistDismissedListener {
        void onHighlightDismissed();
    }


    private HighlistDismissedListener listener;

    private int                       cx;
    private int                       cy;


    public void setLocation(int cx, int cy) {
        this.cx = cx;
        this.cy = cy;
        if (highlightView != null) {
            highlightView.setLocation(cx, cy);
        }
    }

    public void setListener(HighlistDismissedListener listener) {
        this.listener = listener;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        highlightView = null;
    }


    private HighlightView highlightView;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog overlayInfo = new Dialog(getActivity(), android.R.style.Theme_Black_NoTitleBar);
        // Making dialog content transparent.
        overlayInfo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // Removing window dim normally visible when dialog are shown.
        overlayInfo.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        // If user taps anywhere on the screen, dialog will be cancelled.
        overlayInfo.setCancelable(true);
        // Setting the content using prepared XML layout file.
        highlightView = new HighlightView(getActivity());
        highlightView.setLocation(cx, cy);
        overlayInfo.setContentView(highlightView);
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
        setRetainInstance(true);
        setHasOptionsMenu(false);
    }
}
