package vn.edu.eaut.unitconverter.history;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;
import java.util.function.Consumer;

import vn.edu.eaut.unitconverter.model.Categories;

public class HistoryFilterDialog extends DialogFragment {

    private final Consumer<Integer> consumer;
    private int mask = -1;

    public HistoryFilterDialog(Consumer<Integer> consumer) {
        this.consumer = consumer;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        List<String> names = Categories.mapTo(category -> getString(category.getCategoryName()));
        names.add(0, "None");

        return new MaterialAlertDialogBuilder(requireContext())
                .setItems(names.toArray(new CharSequence[0]), (dialog, which) -> mask = which - 1)
                .create();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mask < 0) {
            consumer.accept(0);
        } else {
            consumer.accept(1 << mask);
        }
    }
}