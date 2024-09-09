package vn.edu.eaut.unitconverter.categories;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                converterViewModel.load(Categories.INSTANCE.get(it), this);
            } else {
                startActivity(ConverterActivity.getIntent(this, it));
            }
        });

        if (isTablet) {
            converterViewModel.load(Categories.INSTANCE.get(0), this);
        }

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
