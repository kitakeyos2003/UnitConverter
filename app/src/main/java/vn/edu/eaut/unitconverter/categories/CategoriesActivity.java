package vn.edu.eaut.unitconverter.categories;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import dagger.hilt.android.AndroidEntryPoint;
import vn.edu.eaut.unitconverter.R;
import vn.edu.eaut.unitconverter.converter.ConverterActivity;
import vn.edu.eaut.unitconverter.converter.ConverterFragment;
import vn.edu.eaut.unitconverter.converter.ConverterViewModel;
import vn.edu.eaut.unitconverter.databinding.ActivityCategoriesBinding;
import vn.edu.eaut.unitconverter.model.Categories;

@AndroidEntryPoint
public class CategoriesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCategoriesBinding binding = ActivityCategoriesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        CategoriesViewModel categoriesViewModel = new ViewModelProvider(this).get(CategoriesViewModel.class);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setReorderingAllowed(true);
        transaction.replace(R.id.categoriesContainer, new CategoriesFragment());
        transaction.commit();

        categoriesViewModel.getConverterOpened().observe(this, it -> startActivity(ConverterActivity.getIntent(this, it)));


        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        ViewCompat.setOnApplyWindowInsetsListener(binding.toolbarLayout, (v, insets) -> {
            Insets systemInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemInsets.left, systemInsets.top, systemInsets.right, systemInsets.bottom);
            return insets;
        });
    }
}
