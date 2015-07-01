package com.abdlh.axelspringerhack;

import android.annotation.TargetApi;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.abdlh.axelspringerhack.Model.PointOfInterest;
import com.abdlh.axelspringerhack.UI.Adapters.DetailFragmentEventListener;

import com.abdlh.axelspringerhack.UI.Fragments.DetailsFragment;
import com.abdlh.axelspringerhack.UI.Fragments.PointsOfInterestFragment;
import com.abdlh.axelspringerhack.UI.Fragments.DetailsFragment;
import com.abdlh.axelspringerhack.UI.Fragments.PointsOfInterestFragment;

public class MainActivity extends AppCompatActivity implements fragment_click {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    public static PointOfInterest detailPoi;

    public static PointOfInterest getDetailPoi()
    {
        return detailPoi;
    }

    public static void setDetailPoi(PointOfInterest detailPoi)
    {
        MainActivity.detailPoi = detailPoi;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baselayout);
        initActionBar();
        //showPoiListFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment, PointsOfInterestFragment.newInstance("", "")).commit();
        //onclick();

    }

    protected void initActionBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setTitle("");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //toolbar.setNavigationIcon(R.mipmap.menu_weiss);

        }
    }

    @Override
    protected void onPostCreate(final Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        if (actionBarDrawerToggle != null)
        {
            // Sync the toggle state after onRestoreInstanceState has occurred.
            actionBarDrawerToggle.syncState();
        }
    }

    @Override
    public void onBackPressed()
    {
        // Überschreibt den Back-Button damit bei offenem Drawer nicht die App geschlossen wird, sondern der Drawer.
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override
    public void onPause()
    {
        if (drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        super.onPause();
    }


    @Override
    public void onConfigurationChanged(final Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        if (actionBarDrawerToggle != null)
        {
            actionBarDrawerToggle.onConfigurationChanged(newConfig);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Initializes the sliding menu drawer.
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void initDrawer()
    {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawerLayout.setFocusableInTouchMode(true);

        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        TypedArray ta = obtainStyledAttributes(new int[] {R.attr.ic_action_home});
        int homeResId = ta.getResourceId(0, 0);
        ta.recycle();

        actionBarDrawerToggle = new

            ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close)

            {
                @Override
                public void onDrawerClosed(View view)
                {
                    super.onDrawerClosed(view);
                    supportInvalidateOptionsMenu();
                }

                @Override
                public void onDrawerOpened(View drawerView)
                {
                    super.onDrawerOpened(drawerView);
                    supportInvalidateOptionsMenu();
                }
            };
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
    }

    private void showPoiListFragment()
    {
        final FragmentManager mg = getSupportFragmentManager();
        Fragment fragment = mg.findFragmentByTag(PointsOfInterestFragment.class.getSimpleName());
        if (fragment == null)
        {
            final PointsOfInterestFragment poiListFragment = new PointsOfInterestFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_fragment, poiListFragment, PointsOfInterestFragment.class.getSimpleName());
            transaction.addToBackStack(PointsOfInterestFragment.class.getSimpleName());
            transaction.commit();
//            Log.d(TAG, "MeineBildFragment added to backstack");
        }
    }

    public void showDetailFragment(final PointOfInterest mPoi)
    {

        final FragmentManager mg = getSupportFragmentManager();
        Fragment fragment = mg.findFragmentByTag(DetailsFragment.class.getSimpleName());
        if (fragment == null)
        {
            final DetailsFragment detailsFragment = new DetailsFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_fragment, detailsFragment, DetailsFragment.class.getSimpleName());
            transaction.addToBackStack(DetailsFragment.class.getSimpleName());
            transaction.commit();
//            Log.d(TAG, "MeineBildFragment added to backstack");
        } else
        {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_fragment, fragment, DetailsFragment.class.getSimpleName());
            transaction.addToBackStack(DetailsFragment.class.getSimpleName());
            transaction.commit();
        }
    }



    public void handleDetails(final PointOfInterest mPoi)
    {
        setDetailPoi(mPoi);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_fragment, DetailsFragment.newInstance(this, "")).commit();
    }

    @Override
    public void onclick() {

    }
}
