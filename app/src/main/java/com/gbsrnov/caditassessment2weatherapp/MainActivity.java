package com.gbsrnov.caditassessment2weatherapp;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
    private BottomButtonClickListener bottomButtonClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HomeFragment homeFragment = new HomeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, homeFragment)
                .commit();

        bottomButtonClickListener = (BottomButtonClickListener) homeFragment;

        //button functions
        Button refreshButton = findViewById(R.id.btn_refresh);
        Button closeButton = findViewById(R.id.btn_close_app);

        refreshButton.setOnClickListener(view -> bottomButtonClickListener.onRefresh());
        closeButton.setOnClickListener(view -> bottomButtonClickListener.onCloseApp());
    }

    public interface BottomButtonClickListener {
        void onCloseApp();

        void onRefresh();
    }
}