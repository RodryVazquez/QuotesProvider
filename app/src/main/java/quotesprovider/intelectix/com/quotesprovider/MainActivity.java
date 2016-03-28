package quotesprovider.intelectix.com.quotesprovider;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import quotesprovider.intelectix.com.quotesprovider.Fragments.FortuneFragment;
import quotesprovider.intelectix.com.quotesprovider.Helpers.Preferences;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Setamos el fragmento por defecto
        if(savedInstanceState == null){
            MenuItem item = navigationView.getMenu().getItem(0);
            onNavigationItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            moveTaskToBack(true);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        //Obtenemos el Id del item seleccionado
        int id = item.getItemId();
        //Dependiendo del item seleccionado
        Fragment fragment = null;
        switch (id) {

            case R.id.nav_camera:
                fragment = new FortuneFragment();
                break;
            case R.id.nav_gallery:
                Preferences preferences = new Preferences(MainActivity.this);
                preferences.removeUser();
                finish();
            case R.id.nav_slideshow:
                break;
            case R.id.nav_manage:
                break;
            case R.id.nav_share:
                break;
            case R.id.nav_send:
                break;
        }
        if (fragment != null) {
            //Seteamos la transaccion y reemplazamos el container
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment).commit();

            //Indicamos el item seleccionado y el titulo en el action bar
            item.setChecked(true);
            getSupportActionBar().setTitle(item.getTitle());

            //Cerramos el navigationView
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        } else {
            Log.e(TAG, "Error al crear el fragmento");
        }
        return false;
    }
}
