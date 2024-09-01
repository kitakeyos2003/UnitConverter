package vn.edu.eaut.unitconverter.history;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import dagger.hilt.android.AndroidEntryPoint;
import vn.edu.eaut.unitconverter.R;
import vn.edu.eaut.unitconverter.databinding.FragmentHistoryBinding;
import vn.edu.eaut.unitconverter.model.database.HistoryItem;

@AndroidEntryPoint
public class HistoryFragment extends Fragment implements MenuProvider {

    private FragmentHistoryBinding binding;
    private HistoryAdapter adapter;
    private HistoryViewModel viewModel;

    public HistoryFragment() {
        super(R.layout.fragment_history);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(HistoryViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentHistoryBinding.bind(view);

        // Set up RecyclerView
        binding.recycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recycler.setLayoutAnimation(AnimationUtils.loadLayoutAnimation(requireContext(), R.anim.layout_animation_fall_down));

        adapter = new HistoryAdapter(viewModel::removeItem, this::shareItem);
        binding.recycler.setAdapter(adapter);

        viewModel.getItems().observe(getViewLifecycleOwner(), items -> {
            adapter.submitList(items);
            binding.recycler.setVisibility(items.isEmpty() ? View.INVISIBLE : View.VISIBLE);
        });

        MenuHost menuHost = requireActivity();
        menuHost.addMenuProvider(this, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }

    @Override
    public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.menu_history, menu);
    }

    @Override
    public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.delete) {
            viewModel.removeAll();
            return true;
        } else if (menuItem.getItemId() == R.id.filter) {
            new HistoryFilterDialog(viewModel::filter).show(getChildFragmentManager(), "dialog");
            return true;
        }
        return false;
    }

    private void shareItem(HistoryItem item) {
        String message = item.getValueFrom() + " " + item.getUnitFrom() + " = " + item.getValueTo() + " " + item.getUnitTo();
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        sendIntent.putExtra(Intent.EXTRA_TEXT, message);
        requireContext().startActivity(Intent.createChooser(sendIntent, message));
    }
}