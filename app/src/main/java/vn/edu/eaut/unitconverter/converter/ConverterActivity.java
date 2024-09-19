package vn.edu.eaut.unitconverter.converter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.core.view.WindowCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import dagger.hilt.android.AndroidEntryPoint;
import vn.edu.eaut.unitconverter.R;
import vn.edu.eaut.unitconverter.databinding.ActivityConverterBinding;
import vn.edu.eaut.unitconverter.model.Categories;
import vn.edu.eaut.unitconverter.model.ConverterCategory;

@AndroidEntryPoint
public class ConverterActivity extends AppCompatActivity {
    private ConverterViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityConverterBinding binding = ActivityConverterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        viewModel = new ViewModelProvider(this).get(ConverterViewModel.class);

        setupNavigation(binding);

        OnBackPressedDispatcher dispatcher = getOnBackPressedDispatcher();
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                viewModel.setDrawerOpened(false);
            }
        };
        dispatcher.addCallback(this, callback);

        viewModel.getDrawerOpened().observe(this, opened -> {
            callback.setEnabled(opened);
            if (opened) {
                binding.navigationDrawer.open();
            } else {
                binding.navigationDrawer.close();
            }
        });

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setReorderingAllowed(true);
        transaction.replace(R.id.container, ConverterFragment.class, getIntent().getExtras());
        transaction.commit();

        viewModel.getCategory().observe(this, category -> {
            binding.navigationView.getMenu().getItem(category).setChecked(true);
        });

        int category = getIntent().getIntExtra(CONVERTER_ID_EXTRA, 0);
        viewModel.load(Categories.INSTANCE.get(category), this);
    }

    private void setupNavigation(ActivityConverterBinding binding) {
        for (int i = 0; i < Categories.INSTANCE.size(); i++) {
            ConverterCategory unit = Categories.INSTANCE.get(i);
            binding.navigationView.getMenu()
                    .add(Menu.NONE, Menu.NONE, i, unit.getCategoryName())
                    .setCheckable(true)
                    .setIcon(unit.getIcon());
        }

        binding.navigationView.setNavigationItemSelectedListener(menuItem -> {
            viewModel.load(Categories.INSTANCE.get(menuItem.getOrder()), this);
            viewModel.setDrawerOpened(false);
            return true;
        });
    }
// Comment 1
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        if (viewModel.getCategory().getValue() != null) {
            outState.putInt(CONVERTER_ID_EXTRA, viewModel.getCategory().getValue());
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int category = savedInstanceState.getInt(CONVERTER_ID_EXTRA, -1);
        if (category != -1) {
            viewModel.load(Categories.INSTANCE.get(category), this);
        }
    }

    public static Intent getIntent(Context context, int category) {
        return new Intent(context, ConverterActivity.class).putExtra(CONVERTER_ID_EXTRA, category);
    }

    private static final String CONVERTER_ID_EXTRA = "converter_id";
}
