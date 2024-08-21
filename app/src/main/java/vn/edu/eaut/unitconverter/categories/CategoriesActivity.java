package vn.edu.eaut.unitconverter.categories;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;

import dagger.hilt.android.AndroidEntryPoint;
import dev.chrisbanes.insetter.Insetter;
import vn.edu.eaut.unitconverter.R;
import vn.edu.eaut.unitconverter.converter.ConverterActivity;
import vn.edu.eaut.unitconverter.converter.ConverterFragment;
import vn.edu.eaut.unitconverter.converter.ConverterViewModel;
import vn.edu.eaut.unitconverter.databinding.ActivityCategoriesBinding;
import vn.edu.eaut.unitconverter.model.Categories;
import vn.edu.eaut.unitconverter.model.nbrb.HistoryDatabase;
import vn.edu.eaut.unitconverter.model.nbrb.NBRBRepository;

@AndroidEntryPoint
public class CategoriesActivity extends AppCompatActivity {
   private final CategoriesViewModel categoriesViewModel = new CategoriesViewModel();
   private final ConverterViewModel converterViewModel = new ConverterViewModel();

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      ActivityCategoriesBinding binding = ActivityCategoriesBinding.inflate(getLayoutInflater());
      setContentView(binding.getRoot());
      setSupportActionBar(binding.toolbar);

      boolean isTablet = binding.converterContainer != null;

      FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
      transaction.setReorderingAllowed(true);
      transaction.replace(R.id.categoriesContainer, new CategoriesFragment());
      if (isTablet) {
         transaction.replace(R.id.converterContainer, new ConverterFragment());
      }
      transaction.commit();

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

      ViewCompat.setOnApplyWindowInsetsListener(binding.toolbarLayout, (v, insets) -> {
         Insets systemInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars());
         v.setPadding(systemInsets.left, systemInsets.top, systemInsets.right, systemInsets.bottom);
         return insets;
      });
   }
}
