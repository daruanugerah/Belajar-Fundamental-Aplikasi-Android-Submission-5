package id.daruanugerah.favoritecatalogapp;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    @BindView(R.id.rv_list_fav_consumer)
    RecyclerView rvListFavConsumer;

    private CatalogListAdapter catalogListAdapter;
    private static final int CODE_CATALOG = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        rvListFavConsumer.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        catalogListAdapter = new CatalogListAdapter(getApplicationContext());
        rvListFavConsumer.setAdapter(catalogListAdapter);
        getSupportLoaderManager().initLoader(CODE_CATALOG, null, this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        switch (id) {
            case CODE_CATALOG:
                return new CursorLoader(getApplicationContext(), Utils.CONTENT_URI, null, null, null, null);
            default:
                throw new IllegalArgumentException();
        }

    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        if (loader.getId() == CODE_CATALOG) {
            try {
                catalogListAdapter.setData(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        if (loader.getId() == CODE_CATALOG) {
            catalogListAdapter.setData(null);
        }

    }
}
