package id.daruanugerah.moviecataloguesub4.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import id.daruanugerah.moviecataloguesub4.R;
import id.daruanugerah.moviecataloguesub4.adapter.CatalogFavAdapter;
import id.daruanugerah.moviecataloguesub4.database.CatalogDAO;
import id.daruanugerah.moviecataloguesub4.database.CatalogDB;
import id.daruanugerah.moviecataloguesub4.model.Catalog;
import id.daruanugerah.moviecataloguesub4.viewmodel.MainViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowFavFragment extends Fragment {
    private CatalogFavAdapter catalogFavAdapter;

    private Observer<ArrayList<Catalog>> listCatalogFav = new Observer<ArrayList<Catalog>>() {
        @Override
        public void onChanged(@Nullable ArrayList<Catalog> catalogs) {
            if (catalogs != null) {
                catalogFavAdapter.setCfData(catalogs);
            }

        }
    };

    public ShowFavFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_fav, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        catalogFavAdapter = new CatalogFavAdapter(getActivity());
        RecyclerView rvCatalogFav = view.findViewById(R.id.rv_catalog_fav);
        rvCatalogFav.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        rvCatalogFav.setAdapter(catalogFavAdapter);

        ArrayList<Catalog> favData = (ArrayList<Catalog>) showFavCatalog();
        MainViewModel mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.setFavCatalog(favData);
        mainViewModel.getCatalogs().observe(this, listCatalogFav);

    }

    private List<Catalog> showFavCatalog() {
        CatalogDB catalogDB = Room.databaseBuilder(getActivity(), CatalogDB.class, "db_catalog")
                .allowMainThreadQueries()
                .build();
        CatalogDAO catalogDAO = catalogDB.getCatalogDAO();

        return catalogDAO.getTypeByCatalogType(2);
    }
}
