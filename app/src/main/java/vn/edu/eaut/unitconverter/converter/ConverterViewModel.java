package vn.edu.eaut.unitconverter.converter;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import vn.edu.eaut.unitconverter.converter.ConvertData;
import vn.edu.eaut.unitconverter.converter.Result;
import vn.edu.eaut.unitconverter.model.ConverterCategory;
import vn.edu.eaut.unitconverter.model.converter.Converter;
import vn.edu.eaut.unitconverter.model.converter.CurrencyConverter;
import vn.edu.eaut.unitconverter.model.nbrb.HistoryDatabase;
import vn.edu.eaut.unitconverter.model.nbrb.NBRBRepository;
import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.Dispatchers;

@HiltViewModel
public class ConverterViewModel extends ViewModel {
    private HistoryDatabase database;
    private NBRBRepository repository;
    private final ExecutorService executorService = Executors.newFixedThreadPool(2); // Adjust the pool size as needed

    private final MutableLiveData<Boolean> _drawerOpened = new MutableLiveData<>(false);
    public LiveData<Boolean> getDrawerOpened() {
        return _drawerOpened;
    }

    private final MutableLiveData<Integer> _backgroundColor = new MutableLiveData<>();
    public LiveData<Integer> getBackgroundColor() {
        return _backgroundColor;
    }

    private final MutableLiveData<Integer> _title = new MutableLiveData<>();
    public LiveData<Integer> getTitle() {
        return _title;
    }

    private final MutableLiveData<Integer> _category = new MutableLiveData<>();
    public LiveData<Integer> getCategory() {
        return _category;
    }

    private final MutableLiveData<Converter> _converter = new MutableLiveData<>();
    public LiveData<Converter> getConverter() {
        return _converter;
    }

    private final MutableLiveData<Result> _result = new MutableLiveData<>();
    public LiveData<Result> getResult() {
        return _result;
    }

    @Inject
    public ConverterViewModel() {
    }

    public boolean setDrawerOpened(boolean opened) {
        boolean changed = _drawerOpened.getValue() != opened;
        _drawerOpened.setValue(opened);
        return changed;
    }

    public void load(ConverterCategory category, Context context) {
        _title.setValue(category.getCategoryName());
        _backgroundColor.setValue(category.getColor());

        _category.setValue(category.getIndex());

        Converter converter = category.getConverter(context);

        if (converter instanceof CurrencyConverter) {
            ((CurrencyConverter) converter).setRepository(repository);
        }

        executorService.execute(() -> {
            try {
                converter.load();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            _converter.postValue(converter); // Use postValue to update LiveData from a background thread
        });
    }

    public void saveResultToHistory(ConvertData result) {
//        Converter converter = _converter.getValue();
//        if (converter == null) return;
//
//        HistoryItem item = new HistoryItem(
//                0,
//                converter.get(result.getFrom()).getName(),
//                converter.get(result.getTo()).getName(),
//                result.getValue(),
//                result.getResult(),
//                _category.getValue() != null ? _category.getValue() : 0
//        );
//
//        executorService.execute(() -> {
//            database.dao().insertAll(item);
//        });
    }

    public void convert(ConvertData data) {
        Converter converter = _converter.getValue();
        if (converter == null) {
            _result.setValue(Result.Empty);
            return;
        }
        try {
            Result result = new Result.Converted(converter.convert(data.getValue(), data.getFrom(), data.getTo()));
            _result.setValue(result);
        } catch (Exception ex) {
            _result.setValue(Result.Empty);
        }
    }

}
