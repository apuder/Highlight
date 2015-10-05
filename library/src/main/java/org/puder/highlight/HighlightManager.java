package org.puder.highlight;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import org.puder.highlight.internal.HighlightContentViewItem;
import org.puder.highlight.internal.HighlightDialogFragment;
import org.puder.highlight.internal.HighlightItem;
import org.puder.highlight.internal.HighlightMenuItem;

import java.util.ArrayList;
import java.util.List;

public class HighlightManager implements HighlightDialogFragment.HighlightDismissedListener {

    final private static String TAG_FRAGMENT = "HIGHLIGHT";

    private FragmentActivity    activity;
    private List<HighlightItem> items;


    public HighlightManager(FragmentActivity activity) {
        this.activity = activity;
        items = new ArrayList<>();
        final View view = activity.findViewById(android.R.id.content);
        ViewTreeObserver o = view.getViewTreeObserver();
        o.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                show();
            }
        });
    }

    public HighlightContentViewItem addView(int id) {
        HighlightContentViewItem item = new HighlightContentViewItem(id);
        items.add(item);
        return item;
    }

    public HighlightMenuItem addMenuItem(Menu menu, int menuItemId) {
        HighlightMenuItem item = new HighlightMenuItem(menu, menuItemId);
        items.add(item);
        return item;
    }

    public void show() {
        if (items.isEmpty()) {
            return;
        }
        HighlightItem item = items.remove(0);
        if (item instanceof HighlightMenuItem) {
            showMenuItem((HighlightMenuItem) item);
        }
        if (item instanceof HighlightContentViewItem) {
            showContentViewItem((HighlightContentViewItem) item);
        }
    }

    private void showContentViewItem(HighlightContentViewItem item) {
        View view = activity.findViewById(item.getContentViewId());
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        showHighlight(item, location[0], location[1], location[0] + view.getMeasuredWidth(),
                location[1] + view.getMeasuredHeight());
    }

    private void showMenuItem(final HighlightMenuItem item) {
        final MenuItem it = item.getMenuItem();

        ImageView button = new ImageView(activity, null, android.R.attr.actionButtonStyle);
        button.setImageDrawable(it.getIcon());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onMenuItemSelected(0, it);
            }
        });
        button.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom,
                    int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (left == 0 && top == 0 && right == 0 && bottom == 0) {
                    return;
                }

                if (right == oldRight && left == oldLeft && bottom == oldBottom && top == oldTop) {
                    return;
                }
                int[] location = new int[2];
                v.getLocationOnScreen(location);
                showHighlight(item, location[0], location[1], location[0] + right - left,
                        location[1] + bottom - top);
            }
        });
        it.setActionView(button);
    }

    private void showHighlight(HighlightItem item, int left, int top, int right, int bottom) {
        FragmentManager fm = activity.getSupportFragmentManager();
        HighlightDialogFragment fragment = (HighlightDialogFragment) fm
                .findFragmentByTag(TAG_FRAGMENT);

        android.support.v4.app.FragmentTransaction transaction = fm.beginTransaction();
        if (fragment != null) {
            transaction.remove(fragment);
        }
        fragment = new HighlightDialogFragment();
        fragment.setListener(this);
        fragment.setHighlightItem(item, left, top, right, bottom);
        transaction.add(fragment, TAG_FRAGMENT);
        transaction.commit();
    }

    @Override
    public void onHighlightDismissed() {
        show();
    }
}
