package org.puder.highlight.demo;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.puder.highlight.HighlightManager;

public class MainActivity extends AppCompatActivity {

    private HighlightManager highlightManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        highlightManager = new HighlightManager(this);
        highlightManager.reshowAllHighlights();
        highlightManager.addView(R.id.text).setTitle(R.string.highlight1_title)
                .setDescriptionId(R.string.highlight1_descr);
        highlightManager.addView(R.id.button).setTitle(R.string.highlight2_title)
                .setDescriptionId(R.string.highlight2_descr);
        highlightManager.addView(R.id.text2).setTitle(R.string.highlight2_title)
                .setDescriptionId(R.string.highlight2_descr);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItemCompat.setShowAsAction(
                menu.add(Menu.NONE, 0, Menu.NONE, "Add").setIcon(R.drawable.add_icon),
                MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
        highlightManager.addMenuItem(menu, 0).setTitle(R.string.highlight3_title)
                .setDescriptionId(R.string.highlight3_descr);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menu) {
        Toast.makeText(this, "Menu", Toast.LENGTH_SHORT).show();
        return true;
    }
}