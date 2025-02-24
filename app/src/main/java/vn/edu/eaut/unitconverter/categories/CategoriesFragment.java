package vn.edu.eaut.unitconverter.categories;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import dagger.hilt.android.AndroidEntryPoint;
import vn.edu.eaut.unitconverter.R;
import vn.edu.eaut.unitconverter.databinding.FragmentCategoriesBinding;
import vn.edu.eaut.unitconverter.model.Categories;

@AndroidEntryPoint
public class CategoriesFragment extends Fragment {
    private CategoriesViewModel viewModel;

    public CategoriesFragment() {
        super(R.layout.fragment_categories);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(CategoriesViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentCategoriesBinding binding = FragmentCategoriesBinding.bind(view);
        binding.recycler.setLayoutManager(new GridLayoutManager(requireContext(), getResources().getInteger(R.integer.column_count)));
        binding.recycler.setHasFixedSize(true);
        binding.recycler.setAdapter(new CategoriesAdapter(index -> viewModel.openConverter(Categories.get(index))));
    }
}
