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
import android.widget.Toast;

import java.util.ArrayList;

import id.daruanugerah.moviecataloguesub4.R;
import id.daruanugerah.moviecataloguesub4.adapter.CatalogAdapter;
import id.daruanugerah.moviecataloguesub4.model.Catalog;
import id.daruanugerah.moviecataloguesub4.viewmodel.MainViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {
    private CatalogAdapter catalogAdapter;
    private ProgressBar progressBar;
    private MainViewModel mainViewModel;

    private Observer<ArrayList<Catalog>> listCatalogSearch = new Observer<ArrayList<Catalog>>() {
        @Override
        public void onChanged(ArrayList<Catalog> catalogs) {
            if (catalogs != null && catalogs.size() >0) {
                catalogAdapter.setcData(catalogs);
            } else {
                Toast.makeText(getContext(), R.string.not_found, Toast.LENGTH_SHORT).show();
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

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        catalogAdapter = new CatalogAdapter(getActivity());
        RecyclerView rvCatalog = view.findViewById(R.id.rv_catalog_search);
        rvCatalog.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        rvCatalog.setAdapter(catalogAdapter);

        progressBar = view.findViewById(R.id.catalog_progressBar);

        String query = getArguments().getString("query");

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.searchCatalog(query);
        mainViewModel.getCatalogs().observe(this, listCatalogSearch);

        showLoading(true);

    }
}
