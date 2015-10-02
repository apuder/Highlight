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
        highlightManager.addView(R.id.text).setTitle(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItemCompat.setShowAsAction(
                menu.add(Menu.NONE, 0, Menu.NONE, "Add").setIcon(R.drawable.add_icon),
                MenuItemCompat.SHOW_AS_ACTION_ALWAYS);
        highlightManager.addMenuItem(menu, 0).setTitle(0).setDescriptionId(0);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menu) {
        Toast.makeText(this, "Menu", Toast.LENGTH_SHORT).show();
        return true;
    }
}