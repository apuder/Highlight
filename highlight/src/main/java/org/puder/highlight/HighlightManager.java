package org.puder.highlight;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import org.puder.highlight.internal.HighlightContentViewItem;
import org.puder.highlight.internal.HighlightDialogFragment;
import org.puder.highlight.internal.HighlightItem;
import org.puder.highlight.internal.HighlightMenuItem;

import java.util.ArrayList;
import java.util.List;

public class HighlightManager implements HighlightDialogFragment.HighlightDismissedListener {

    final private static String TAG_FRAGMENT   = "HIGHLIGHT_FRAG";
    final private static String SHARE_PREF     = "HIGHLIGHT_PREFS";

    private FragmentActivity    activity;
    private SharedPreferences   prefs;
    private List<HighlightItem> items;
    private int                 numItemsToShow = 0;


    public HighlightManager(FragmentActivity activity) {
        this.activity = activity;
        prefs = activity.getSharedPreferences(SHARE_PREF, Context.MODE_PRIVATE);
        items = new ArrayList<>();
    }

    public HighlightContentViewItem addView(int id) {
        final HighlightContentViewItem item = new HighlightContentViewItem(id);
        String key = "view-" + Integer.toString(id);
        if (!isFirstTime(key)) {
            return item;
        }
        items.add(item);
        activity.findViewById(id).addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom,
                    int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (left == 0 && top == 0 && right == 0 && bottom == 0) {
                    return;
                }
                v.removeOnLayoutChangeListener(this);
                setScreenPosition(v, item);
            }
        });
        return item;
    }

    public HighlightMenuItem addMenuItem(Menu menu, int menuItemId) {
        final HighlightMenuItem item = new HighlightMenuItem(menu, menuItemId);
        String key = "menu-" + Integer.toString(menuItemId);
        if (!isFirstTime(key)) {
            return item;
        }
        items.add(item);
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
                setScreenPosition(v, item);
            }
        });
        it.setActionView(button);
        return item;
    }

    private void setScreenPosition(View v, HighlightItem item) {
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        int screenLeft = location[0];
        int screenTop = location[1];
        int screenRight = location[0] + v.getMeasuredWidth();
        int screenBottom = location[1] + v.getMeasuredHeight();
        item.setScreenPosition(screenLeft, screenTop, screenRight, screenBottom);
        numItemsToShow++;
        if (numItemsToShow == items.size()) {
            show();
        }
    }

    private boolean isFirstTime(String key) {
        if (prefs.getBoolean(key, true)) {
            Editor editor = prefs.edit();
            editor.putBoolean(key, false);
            editor.apply();
            return true;
        }
        return false;
    }

    public void reshowAllHighlights() {
        Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
    }

    private void show() {
        if (items.isEmpty()) {
            return;
        }
        HighlightItem item = items.remove(0);
        FragmentManager fm = activity.getSupportFragmentManager();
        HighlightDialogFragment fragment = (HighlightDialogFragment) fm
                .findFragmentByTag(TAG_FRAGMENT);

        android.support.v4.app.FragmentTransaction transaction = fm.beginTransaction();
        if (fragment != null) {
            transaction.remove(fragment);
        }
        fragment = new HighlightDialogFragment();
        fragment.setListener(this);
        fragment.setHighlightItem(item);
        transaction.add(fragment, TAG_FRAGMENT);
        transaction.commit();
    }

    @Override
    public void onHighlightDismissed() {
        numItemsToShow--;
        show();
    }
}
