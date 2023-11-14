/**
 * A fragment displaying the home screen with
 * animation and a start button.
 * @author Drey Smith
 * @date 10.25.2023
 */
package com.example.wiredorthodontics.view;

import android.os.Bundle;
import com.airbnb.lottie.LottieAnimationView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.airbnb.lottie.LottieDrawable;
import com.example.wiredorthodontics.R;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Inflates the layout for the home fragment and initializes its elements.
     *
     * @param inflater           The LayoutInflater object to inflate views.
     * @param container          The parent view for the fragment UI.
     * @param savedInstanceState  The previous state of the fragment.
     * @return                   The inflated View.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // set up the lottie animation
        LottieAnimationView lottieAnimationView = view.findViewById(R.id.lottieAnimationView);
        lottieAnimationView.setRepeatCount(LottieDrawable.INFINITE);
        lottieAnimationView.setRepeatMode(LottieDrawable.RESTART);
        lottieAnimationView.setSpeed(0.5f);
        lottieAnimationView.playAnimation();

        // set up a click listener for the button
        Button btnStart = view.findViewById(R.id.btnStart);
        btnStart.setOnClickListener(v -> {
            // go to next tab on-click (the calendar-fragment)
            MainActivity activity = (MainActivity) requireActivity();
            activity.switchToNextTab();
        });
        return view;
    }
}