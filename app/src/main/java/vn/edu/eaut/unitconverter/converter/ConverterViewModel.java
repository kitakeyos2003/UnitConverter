package vn.edu.eaut.unitconverter.converter;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import vn.edu.eaut.unitconverter.model.database.HistoryDao;
import vn.edu.eaut.unitconverter.model.database.HistoryItem;
import vn.edu.eaut.unitconverter.model.ConverterCategory;
import vn.edu.eaut.unitconverter.model.converter.Converter;
import vn.edu.eaut.unitconverter.model.database.HistoryDatabase;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ConverterViewModel extends ViewModel {

    private final HistoryDao dao;
    private final ExecutorService executorService = Executors.newFixedThreadPool(2); // Adjust the pool size as needed

    private final MutableLiveData<Boolean> _drawerOpened = new MutableLiveData<>(false);

    private final MutableLiveData<Integer> _backgroundColor = new MutableLiveData<>();
    private final MutableLiveData<Integer> _title = new MutableLiveData<>();
    private final MutableLiveData<Integer> _category = new MutableLiveData<>();
    private final MutableLiveData<Converter> _converter = new MutableLiveData<>();
    private final MutableLiveData<ConvertResult> _result = new MutableLiveData<>();


    @Inject
    public ConverterViewModel(HistoryDatabase db) {
        dao = db.dao();
    }

    public LiveData<Integer> getBackgroundColor() {
        return _backgroundColor;
    }

    public LiveData<Boolean> getDrawerOpened() {
        return _drawerOpened;
    }

    public LiveData<Integer> getTitle() {
        return _title;
    }

    public LiveData<Integer> getCategory() {
        return _category;
    }

    public LiveData<Converter> getConverter() {
        return _converter;
    }

    public LiveData<ConvertResult> getResult() {
        return _result;
    }

    public void setDrawerOpened(boolean opened) {
        _drawerOpened.setValue(opened);
    }

    public void load(ConverterCategory category, Context context) {
        _title.setValue(category.getCategoryName());
        _backgroundColor.setValue(category.getColor());

        _category.setValue(category.getIndex());

        Converter converter = category.getConverter(context);

        executorService.execute(() -> {
            try {
                converter.load();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            _converter.postValue(converter);
        });
    }

    public void saveResultToHistory(ConvertData result) {
        Converter converter = _converter.getValue();
        if (converter == null || result.getResult().isEmpty())
            return;

        HistoryItem item = new HistoryItem(
                0,
                converter.get(result.getFrom()).getName(),
                converter.get(result.getTo()).getName(),
                result.getValue(),
                result.getResult(),
                _category.getValue() != null ? _category.getValue() : 0
        );

        executorService.execute(() -> dao.insertAll(item));
    }

    public void convert(ConvertData data) {
        Converter converter = _converter.getValue();
        if (converter == null) {
            _result.setValue(ConvertResult.EMPTY);
            return;
        }
        try {
            ConvertResult result = new ConvertResult(converter.convert(data.getValue(), data.getFrom(), data.getTo()));
            _result.setValue(result);
        } catch (Exception ex) {
            _result.setValue(ConvertResult.EMPTY);
        }
    }

}
