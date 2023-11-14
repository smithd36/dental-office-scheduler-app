package com.example.wiredorthodontics.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.content.res.ColorStateList;
import android.os.Bundle;

import com.example.wiredorthodontics.R;
import com.example.wiredorthodontics.controller.AppointmentController;
import com.example.wiredorthodontics.model.AppointmentDAO;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private AppointmentController controller;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the controller instance
        controller = new AppointmentController(this, new AppointmentDAO(this));

        // set up viewpager and tabs
        viewPager = findViewById(R.id.viewPager);
        setupViewPager(viewPager);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        setupTabLayout(tabLayout);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        HomeFragment homeFragment = new HomeFragment();
        CalendarFragment calendarFragment = new CalendarFragment();
        PersonalInfoFragment personalInfoFragment = new PersonalInfoFragment();

        // Set the controller for fragments
        calendarFragment.setController(controller);
        personalInfoFragment.setController(controller);

        adapter.addFragment(homeFragment, "Getting Started");
        adapter.addFragment(calendarFragment, "Pick a Date");
        adapter.addFragment(personalInfoFragment, "Your Info");

        viewPager.setAdapter(adapter);
    }

    private void setupTabLayout(TabLayout tabLayout) {
        // code for setting up tab colors
        tabLayout.setTabTextColors(ColorStateList.valueOf(getResources().getColor(R.color.unselected_tab_color)));
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.selected_tab_color));

        // set the tabs to take up the full width
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }

    public void showAlert(String message) {
        // Alert
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ALERT")
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }

    public void switchToNextTab() {
        int currentTab = viewPager.getCurrentItem();
        int nextTab = currentTab + 1;
        if (nextTab < viewPager.getAdapter().getCount()) {
            viewPager.setCurrentItem(nextTab);
        }
    }

    public void switchToFirstTab() {
        viewPager.setCurrentItem(0);
    }
}