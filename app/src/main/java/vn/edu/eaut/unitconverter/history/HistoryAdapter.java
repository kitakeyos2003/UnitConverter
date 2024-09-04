package vn.edu.eaut.unitconverter.history;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import vn.edu.eaut.unitconverter.databinding.HistoryItemBinding;
import vn.edu.eaut.unitconverter.model.Categories;
import vn.edu.eaut.unitconverter.model.ConverterCategory;
import vn.edu.eaut.unitconverter.model.database.HistoryEntity;

public class HistoryAdapter extends ListAdapter<HistoryEntity, HistoryAdapter.ViewHolder> {
    private final RemoveItemListener remove;
    private final ShareItemListener share;

    public HistoryAdapter(RemoveItemListener remove, ShareItemListener share) {
        super(new Differ());
        this.remove = remove;
        this.share = share;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        HistoryItemBinding binding = HistoryItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        ViewHolder holder = new ViewHolder(binding);
        holder.binding.removeItem.setOnClickListener(v -> {
            int position = holder.getBindingAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                remove.onRemove(getItem(position));
            }
        });
        holder.binding.shareItem.setOnClickListener(v -> {
            int position = holder.getBindingAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                share.onShare(getItem(position));
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HistoryEntity item = getItem(position);
        ConverterCategory category = Categories.INSTANCE.get(item.getCategory());
        holder.bind(category, item);
    }

    public interface RemoveItemListener {
        void onRemove(HistoryEntity item);
    }

    public interface ShareItemListener {
        void onShare(HistoryEntity item);
    }

    public static class Differ extends DiffUtil.ItemCallback<HistoryEntity> {
        @Override
        public boolean areItemsTheSame(HistoryEntity oldItem, HistoryEntity newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(HistoryEntity oldItem, HistoryEntity newItem) {
            return oldItem.equals(newItem);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final HistoryItemBinding binding;

        public ViewHolder(HistoryItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(ConverterCategory category, HistoryEntity item) {
            binding.categoryName.setText(category.getCategoryName());
            binding.valueFrom.setText(item.getValueFrom());
            binding.valueTo.setText(item.getValueTo());
            binding.unitFrom.setText(item.getUnitFrom());
            binding.unitTo.setText(item.getUnitTo());
            itemView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), category.getColor()));
            binding.categoryIcon.setImageDrawable(AppCompatResources.getDrawable(itemView.getContext(), category.getIcon()));
        }
    }
}