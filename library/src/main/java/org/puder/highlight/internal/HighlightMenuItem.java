package org.puder.highlight.internal;

import android.view.Menu;
import android.view.MenuItem;

public class HighlightMenuItem extends HighlightItem {
    private Menu menu;
    private int  menuItemId;


    public HighlightMenuItem(Menu menu, int menuItemId) {
        this.menu = menu;
        this.menuItemId = menuItemId;
    }

    public MenuItem getMenuItem() {
        return menu.findItem(menuItemId);
    }
}
