package id.daruanugerah.moviecataloguesub4.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;

import id.daruanugerah.moviecataloguesub4.R;
import id.daruanugerah.moviecataloguesub4.adapter.CatalogAdapter;
import id.daruanugerah.moviecataloguesub4.model.Catalog;
import id.daruanugerah.moviecataloguesub4.viewmodel.MainViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowFragment extends Fragment {

    private CatalogAdapter catalogAdapter;
    private ProgressBar progressBar;
    private MainViewModel mainViewModel;

    private Observer<ArrayList<Catalog>> listCatalog = new Observer<ArrayList<Catalog>>() {
        @Override
        public void onChanged(ArrayList<Catalog> catalogs) {
            if (catalogs != null) {
                catalogAdapter.setcData(catalogs);
            }
            showLoading(false);
        }
    };

    private void showLoading(boolean b) {
        if (b) {
            progressBar.setVisibility(View.VISIBLE);
        }
        else {
            progressBar.setVisibility(View.GONE);
        }
    }

    public ShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        catalogAdapter = new CatalogAdapter(getActivity());
        RecyclerView rvCatalog = view.findViewById(R.id.rv_catalog);
        rvCatalog.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        rvCatalog.setAdapter(catalogAdapter);

        progressBar = view.findViewById(R.id.catalog_progressBar);

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.setCatalogs("tv");
        mainViewModel.getCatalogs().observe(this, listCatalog);

        showLoading(true);
    }
}
