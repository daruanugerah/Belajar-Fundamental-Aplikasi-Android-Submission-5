package id.daruanugerah.moviecataloguesub4.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import id.daruanugerah.moviecataloguesub4.R;
import id.daruanugerah.moviecataloguesub4.adapter.ViewPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {

    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewPager viewPager = view.findViewById(R.id.viewpager_fav);
        setupViewPager(viewPager);

        TabLayout tabLayout = view.findViewById(R.id.tab_fav);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter vpAdapter = new ViewPagerAdapter(getChildFragmentManager(), 1);
        vpAdapter.addFragment(new MovieFavFragment(), getResources().getString(R.string.movies));
        vpAdapter.addFragment(new ShowFavFragment(), getResources().getString(R.string.tvshow));
        viewPager.setAdapter(vpAdapter);
    }
}
