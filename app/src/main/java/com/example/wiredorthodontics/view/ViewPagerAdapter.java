/**
 * Adapter for managing the fragments displayed
 * in a ViewPager.
 * @author Drey Smith
 * @date 10.20.2023
 */
package com.example.wiredorthodontics.view;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> fragmentTitleList = new ArrayList<>();

    /**
     * Constructor for the ViewPagerAdapter.
     *
     * @param manager The FragmentManager to handle the fragments.
     */
    public ViewPagerAdapter(FragmentManager manager) {
        super(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }


    /**
     * Returns the fragment at the specified position.
     *
     * @param position The position of the fragment.
     * @return The fragment at the specified position.
     */
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    /**
     * Gets the total number of fragments managed by the adapter.
     *
     * @return The total number of fragments.
     */
    @Override
    public int getCount() {
        return fragmentList.size();
    }

    /**
     * Adds a fragment to the adapter with a given title.
     *
     * @param fragment The fragment to add.
     * @param title    The title associated with the fragment.
     */
    public void addFragment(Fragment fragment, String title) {
        fragmentList.add(fragment);
        fragmentTitleList.add(title);
    }

    /**
     * Gets the title of the fragment at a specific position.
     *
     * @param position The position of the fragment.
     * @return The title of the fragment at the specified position.
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitleList.get(position);
    }
}