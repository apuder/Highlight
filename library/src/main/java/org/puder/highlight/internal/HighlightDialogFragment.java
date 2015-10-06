package org.puder.highlight.internal;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import org.puder.highlight.R;

public class HighlightDialogFragment extends DialogFragment {

    public interface HighlightDismissedListener {
        void onHighlightDismissed();
    }


    private HighlightDismissedListener listener;

    private HighlightItem              item;


    public void setHighlightItem(HighlightItem item) {
        this.item = item;
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
        if (item == null) {
            /*
             * Although this fragment is not retained, it seems that Android
             * will re-create it during an orientation change. In this case item
             * == null. Just return the empty dialog here and dismiss the dialog
             * immediately. It will be immediately disposed once the fragment is
             * removed after the orientation change.
             */
            dismissAllowingStateLoss();
            return overlayInfo;
        }
        WindowManager wm = getActivity().getWindowManager();
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int cy = item.screenTop + (item.screenBottom - item.screenTop) / 2;
        overlayInfo.setContentView(size.y / 2 > cy ? R.layout.highlight_top
                : R.layout.highlight_bottom);
        HighlightView highlightView = (HighlightView) overlayInfo.findViewById(R.id.highlight_view);
        highlightView.setHighlightItem(item);
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
