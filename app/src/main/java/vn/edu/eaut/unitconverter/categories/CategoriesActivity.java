package vn.edu.eaut.unitconverter.categories;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.WindowInsets;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import dagger.hilt.android.AndroidEntryPoint;
import dev.chrisbanes.insetter.Insetter;
import vn.edu.eaut.unitconverter.R;
import vn.edu.eaut.unitconverter.converter.ConverterActivity;
import vn.edu.eaut.unitconverter.converter.ConverterFragment;
import vn.edu.eaut.unitconverter.converter.ConverterViewModel;
import vn.edu.eaut.unitconverter.databinding.ActivityCategoriesBinding;
import vn.edu.eaut.unitconverter.model.Categories;

@AndroidEntryPoint
public class CategoriesActivity extends AppCompatActivity {

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences("menu_prefs", MODE_PRIVATE);
        ActivityCategoriesBinding binding = ActivityCategoriesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);


        boolean isTablet = binding.converterContainer != null;

        CategoriesViewModel categoriesViewModel = new ViewModelProvider(this).get(CategoriesViewModel.class);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setReorderingAllowed(true);
        transaction.replace(R.id.categoriesContainer, new CategoriesFragment());
        if (isTablet) {
            transaction.replace(R.id.converterContainer, new ConverterFragment());
        }
        transaction.commit();
        ConverterViewModel converterViewModel = new ViewModelProvider(this).get(ConverterViewModel.class);
        categoriesViewModel.getConverterOpened().observe(this, it -> {
            if (isTablet) {
                converterViewModel.load(Categories.get(it), this);
            } else {
                startActivity(ConverterActivity.getIntent(this, it));
            }
        });

        if (isTablet) {
            converterViewModel.load(Categories.get(0), this);
        }

        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        Insetter.builder()
                .padding(WindowInsetsCompat.Type.statusBars(), WindowInsets.Side.TOP, true)
                .applyToView(binding.toolbarLayout);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.isCheckable()) {
            boolean newState = !item.isChecked();
            item.setChecked(newState);

            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(item.getTitle().toString(), newState);
            editor.apply();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Categories.forEach(converter -> {
            MenuItem item = menu.add(getString(converter.getCategoryName()));
            item.setCheckable(true);
            boolean isChecked = preferences.getBoolean(item.getTitle().toString(), true);
            item.setChecked(isChecked);
        });
        return super.onCreateOptionsMenu(menu);
    }
}
