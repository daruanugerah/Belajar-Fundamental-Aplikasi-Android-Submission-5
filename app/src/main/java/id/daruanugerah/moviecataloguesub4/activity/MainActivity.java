package id.daruanugerah.moviecataloguesub4.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.daruanugerah.moviecataloguesub4.R;
import id.daruanugerah.moviecataloguesub4.fragment.FavoriteFragment;
import id.daruanugerah.moviecataloguesub4.fragment.MovieFragment;
import id.daruanugerah.moviecataloguesub4.fragment.SearchFragment;
import id.daruanugerah.moviecataloguesub4.fragment.ShowFragment;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.nav_bottom)
    BottomNavigationView navBottom;
    private SearchView searchView;

    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //BottomNavigationView navBottom = findViewById(R.id.nav_bottom);
        navBottom.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        if (savedInstanceState == null) {
            navBottom.setSelectedItemId(R.id.nav_movie);

        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.movies);
        }

    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            //Fragment fragment;

            switch (item.getItemId()) {
                case R.id.nav_movie:
                    fragment = new MovieFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_container, fragment, fragment.getClass().getSimpleName())
                            .commit();
                    getSupportActionBar().setTitle(R.string.movies);
                    return true;

                case R.id.nav_show:
                    fragment = new ShowFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_container, fragment, fragment.getClass().getSimpleName())
                            .commit();
                    getSupportActionBar().setTitle(R.string.tvshow);
                    return true;

                case R.id.nav_favorite:
                    fragment = new FavoriteFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_container, fragment, fragment.getClass().getSimpleName())
                            .commit();
                    getSupportActionBar().setTitle(R.string.favorite);
                    return true;

            }
            return false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() > 0) {
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    Bundle bundle = new Bundle();
                    bundle.putString("query", newText);
                    SearchFragment searchFragment = new SearchFragment();
                    searchFragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.frame_container, searchFragment);
                    fragmentTransaction.commit();

                    navBottom.getMenu().setGroupCheckable(0, false, true);

                } else {
                    navBottom.getMenu().setGroupCheckable(0, true, true);
                    navBottom.getMenu().getItem(0).setChecked(true);

                    fragment = new MovieFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_container, fragment, fragment.getClass().getSimpleName())
                            .commit();

                    if (getSupportActionBar() != null) {
                        getSupportActionBar().setTitle(R.string.movies);
                    }
                }
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.language_settings) {
            Intent langIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(langIntent);
        } else if (item.getItemId() == R.id.notif_settings) {
            Intent notifIntent = new Intent(this, NotificationActivity.class);
            startActivity(notifIntent);
        }

        return super.onOptionsItemSelected(item);
    }
}
