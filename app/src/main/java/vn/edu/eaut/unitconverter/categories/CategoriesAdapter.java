package vn.edu.eaut.unitconverter.categories;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import vn.edu.eaut.unitconverter.databinding.CategoryItemBinding;
import vn.edu.eaut.unitconverter.model.Categories;
import vn.edu.eaut.unitconverter.model.ConverterCategory;



public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {
    private final CategoryClickListener listener;

    public CategoriesAdapter(CategoryClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        CategoryItemBinding binding = CategoryItemBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        ViewHolder holder = new ViewHolder(binding);
        binding.getRoot().setOnClickListener(v -> {
            int position = holder.getBindingAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                listener.onCategoryClick(Categories.get(position).getId());
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(Categories.get(holder.getBindingAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return Categories.size();
    }

    public interface CategoryClickListener {
        void onCategoryClick(int index);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final CategoryItemBinding binding;

        public ViewHolder(CategoryItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(ConverterCategory category) {
            binding.categoryName.setText(category.getCategoryName());
            binding.categoryIcon.setImageResource(category.getIcon());
            binding.categoryContainer.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), category.getColor()));
        }
    }
}

