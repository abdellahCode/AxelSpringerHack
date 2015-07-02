package com.abdlh.axelspringerhack;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.abdlh.axelspringerhack.UI.Fragments.DetailsFragment;
import com.abdlh.axelspringerhack.UI.Fragments.PointsOfInterestFragment;

public class MainActivity extends AppCompatActivity implements fragment_click {

    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baselayout);
        initActionBar();

        getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment, PointsOfInterestFragment.newInstance("", "")).commit();
    }

    protected void initActionBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setTitle("");
            //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //toolbar.setNavigationIcon(R.mipmap.menu_weiss);

        }
    }

    @Override
    public void onBackPressed()
    {
        // Überschreibt den Back-Button damit bei offenem Drawer nicht die App geschlossen wird, sondern der Drawer.
//        if (drawerLayout.isDrawerOpen(GravityCompat.START))
//        {
//            drawerLayout.closeDrawer(GravityCompat.START);
//        }
//        else
        {
            super.onBackPressed();
        }
    }

    @Override
    public void onPause()
    {
        super.onPause();
    }


    @Override
    public void onclick(String name) {
        getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment, DetailsFragment.newInstance(name)).addToBackStack(null).commit();

    }
}
