package vn.edu.eaut.unitconverter.categories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import vn.edu.eaut.unitconverter.model.ConverterCategory;

public class CategoriesViewModel extends ViewModel {
    private final MutableLiveData<Integer> _converterOpened = new MutableLiveData<>();

    public LiveData<Integer> getConverterOpened() {
        return _converterOpened;
    }

    public void openConverter(ConverterCategory category) {
        _converterOpened.setValue(category.getIndex());
    }
}
