package org.puder.highlight.demo;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

import org.puder.highlight.HighlightManager;

public class MainActivity extends AppCompatActivity {

    final static private int MENU_OPTION_ADD = 1;
    private HighlightManager highlightManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
         * Create an instance of the HighlightManager. It is important to do
         * this _after_ the call to Activity.setContentView().
         */
        highlightManager = new HighlightManager(this);

        /*
         * Calling HighlightManager.reshowAllHighlights() will make sure that
         * highlights are shown with each launch of the app. Otherwise the
         * highlights will only be shown the first time. This is useful for
         * debugging purposes but should not be used for a production release.
         */
        highlightManager.reshowAllHighlights();

        /*
         * In the following three different highlights are defined via
         * HighlightManager.addView(). The id refers to the android:id parameter
         * of the respective view to be highlighted.
         */
        highlightManager.addView(R.id.text1).setTitle(R.string.highlight1_title)
                .setDescriptionId(R.string.highlight1_descr);
        highlightManager.addView(R.id.button).setTitle(R.string.highlight2_title)
                .setDescriptionId(R.string.highlight2_descr);
        highlightManager.addView(R.id.text2).setTitle(R.string.highlight3_title)
                .setDescriptionId(R.string.highlight3_descr);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItemCompat.setShowAsAction(menu.add(Menu.NONE, MENU_OPTION_ADD, Menu.NONE, "Add")
                .setIcon(R.mipmap.add_icon), MenuItemCompat.SHOW_AS_ACTION_ALWAYS);

        /*
         * It is also possible to highlight ActionBar menu items. This is done
         * via HighlightManager.addMenu() _after_ the menu item has been added.
         */
        highlightManager.addMenuItem(menu, MENU_OPTION_ADD).setTitle(R.string.highlight4_title)
                .setDescriptionId(R.string.highlight4_descr);
        return true;
    }
}
