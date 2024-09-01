package vn.edu.eaut.unitconverter.history;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import dagger.hilt.android.AndroidEntryPoint;
import dev.chrisbanes.insetter.Insetter;
import vn.edu.eaut.unitconverter.R;
import vn.edu.eaut.unitconverter.databinding.ActivityHistoryBinding;

@AndroidEntryPoint
public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityHistoryBinding binding = ActivityHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, new HistoryFragment());
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.commit();

        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        Insetter.builder().setOnApplyInsetsListener((view, insets, initialState) -> {
                    view.setPadding(
                            view.getPaddingLeft(),
                            view.getPaddingTop() + insets.getSystemWindowInsetTop(),
                            view.getPaddingRight(),
                            view.getPaddingBottom()
                    );
                    ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                    layoutParams.leftMargin += insets.getSystemWindowInsetLeft();
                    layoutParams.rightMargin += insets.getSystemWindowInsetRight();
                    view.setLayoutParams(layoutParams);
                })
                .applyToView(binding.toolbarLayout);

    }
}
