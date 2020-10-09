package id.daruanugerah.moviecataloguesub4.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> favFragmentList = new ArrayList<>();
    private final List<String> favFragmentTitleList = new ArrayList<>();

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }


    @Override
    public Fragment getItem(int position) {
        return favFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return favFragmentList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        favFragmentList.add(fragment);
        favFragmentTitleList.add(title);

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return favFragmentTitleList.get(position);
    }
}
