package vn.edu.eaut.unitconverter.categories;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import dagger.hilt.android.AndroidEntryPoint;
import vn.edu.eaut.unitconverter.R;
import vn.edu.eaut.unitconverter.categories.CategoriesAdapter;
import vn.edu.eaut.unitconverter.databinding.FragmentCategoriesBinding;
import vn.edu.eaut.unitconverter.model.Categories;

@AndroidEntryPoint
public class CategoriesFragment extends Fragment {
    private CategoriesViewModel viewModel;
    private FragmentCategoriesBinding binding;

    public CategoriesFragment() {
        super(R.layout.fragment_categories);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(CategoriesViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding = FragmentCategoriesBinding.bind(view);
        binding.recycler.setLayoutManager(
                new GridLayoutManager(requireContext(), getResources().getInteger(R.integer.column_count))
        );
        binding.recycler.setHasFixedSize(true);
        binding.recycler.setAdapter(new CategoriesAdapter(index -> viewModel.openConverter(Categories.INSTANCE.get(index))));

        binding.recycler.setOnApplyWindowInsetsListener((v, insets) -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    v.setPadding(
                            v.getPaddingLeft(),
                            v.getPaddingTop(),
                            v.getPaddingRight(),
                            insets.getInsets(WindowInsets.Type.navigationBars()).bottom
                    );
                }
            }
            return insets.consumeSystemWindowInsets();
        });
    }
}
