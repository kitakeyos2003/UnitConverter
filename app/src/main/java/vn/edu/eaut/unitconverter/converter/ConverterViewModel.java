package vn.edu.eaut.unitconverter.converter;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.eaut.unitconverter.api.ApiClient;
import vn.edu.eaut.unitconverter.api.ExrateList;
import vn.edu.eaut.unitconverter.api.VietcombankApi;
import vn.edu.eaut.unitconverter.model.converter.CurrencyConverter;
import vn.edu.eaut.unitconverter.model.database.ExrateDao;
import vn.edu.eaut.unitconverter.api.ExrateRepository;
import vn.edu.eaut.unitconverter.model.database.HistoryDao;
import vn.edu.eaut.unitconverter.model.database.HistoryEntity;
import vn.edu.eaut.unitconverter.model.ConverterCategory;
import vn.edu.eaut.unitconverter.model.converter.Converter;
import vn.edu.eaut.unitconverter.model.database.Database;

import dagger.hilt.android.lifecycle.HiltViewModel;
import vn.edu.eaut.unitconverter.util.NetworkUtil;
import vn.edu.eaut.unitconverter.util.ThreadPool;

@HiltViewModel
public class ConverterViewModel extends ViewModel {

    private final HistoryDao historyDao;
    private final ExrateDao exrateDao;

    private final MutableLiveData<Boolean> _drawerOpened = new MutableLiveData<>(false);

    private final MutableLiveData<Integer> _backgroundColor = new MutableLiveData<>();
    private final MutableLiveData<Integer> _title = new MutableLiveData<>();
    private final MutableLiveData<Integer> _category = new MutableLiveData<>();
    private final MutableLiveData<Converter> _converter = new MutableLiveData<>();
    private final MutableLiveData<ConvertResult> _result = new MutableLiveData<>();


    @Inject
    public ConverterViewModel(Database db) {
        this.historyDao = db.historyDao();
        this.exrateDao = db.exrateDao();
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
        loadConverter(converter);
    }

    private void loadConverter(Converter converter) {
        ThreadPool.execute(() -> {
            if (converter instanceof CurrencyConverter c) {
                loadFromApi(new Callback<>() {
                    @Override
                    public void onResponse(Call<ExrateList> call, Response<ExrateList> response) {
                        c.setData(response.body());
                        try {
                            converter.load();
                            _converter.postValue(converter);
                        } catch (Exception e) {
                            Log.e("ConvertViewModel.load()", e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<ExrateList> call, Throwable throwable) {
                        Log.e("ConvertViewModel.load()", throwable.getMessage());
                    }
                });
            } else {
                try {
                    converter.load();
                    _converter.postValue(converter);
                } catch (Exception e) {
                    Log.e("ConvertViewModel.load()", e.getMessage());
                }
            }
        });
    }



    private void loadFromApi(Callback<ExrateList> callback) {
        VietcombankApi apiService = ApiClient.getClient().create(VietcombankApi.class);
        ExrateRepository exrateRepository = new ExrateRepository(apiService, exrateDao);
        if (NetworkUtil.isInternetAvailable()) {
            exrateRepository.fetchAndStoreExrateList(new Callback<>() {
                @Override
                public void onResponse(Call<ExrateList> call, Response<ExrateList> response) {
                    callback.onResponse(call, response);
                }

                @Override
                public void onFailure(Call<ExrateList> call, Throwable t) {
                    callback.onFailure(call, t);
                }
            });
        } else {
            exrateRepository.getLocalExrates(new Callback<>() {
                @Override
                public void onResponse(Call<ExrateList> call, Response<ExrateList> response) {
                    callback.onResponse(call, response);
                }

                @Override
                public void onFailure(Call<ExrateList> call, Throwable t) {
                    callback.onFailure(call, t);
                }
            });
        }
    }

    public void saveResultToHistory(ConvertData result) {
        Converter converter = _converter.getValue();
        if (converter == null || result.getResult().isEmpty())
            return;

        HistoryEntity item = new HistoryEntity(
                0,
                converter.get(result.getFrom()).getName(),
                converter.get(result.getTo()).getName(),
                result.getValue(),
                result.getResult(),
                _category.getValue() != null ? _category.getValue() : 0
        );

        ThreadPool.execute(() -> historyDao.insertAll(item));
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
