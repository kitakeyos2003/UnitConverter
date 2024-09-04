package vn.edu.eaut.unitconverter.history;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import vn.edu.eaut.unitconverter.model.database.Database;
import vn.edu.eaut.unitconverter.model.database.HistoryDao;
import vn.edu.eaut.unitconverter.model.database.HistoryEntity;
import vn.edu.eaut.unitconverter.util.ThreadPool;

@HiltViewModel
public class HistoryViewModel extends ViewModel {

    private final HistoryDao dao;
    private final MutableLiveData<List<HistoryEntity>> itemsRaw = new MutableLiveData<>();
    private final MutableLiveData<Integer> filter = new MutableLiveData<>(0);

    private final MediatorLiveData<List<HistoryEntity>> itemsFiltered = new MediatorLiveData<>() {{
        addSource(filter, mask -> {
            List<HistoryEntity> rawItems = itemsRaw.getValue();
            setValue(rawItems == null ? List.of() : rawItems.stream()
                    .filter(item -> mask == 0 || ((1 << item.getCategory()) & mask) != 0)
                    .collect(Collectors.toList()));
        });
        addSource(itemsRaw, rawItems -> {
            Integer currentFilter = filter.getValue();
            setValue(rawItems.stream()
                    .filter(item -> currentFilter == 0 || ((1 << item.getCategory()) & currentFilter) != 0)
                    .collect(Collectors.toList()));
        });
    }};

    @Inject
    public HistoryViewModel(Database db) {
        dao = db.historyDao();
        fetch();
    }

    public LiveData<List<HistoryEntity>> getItems() {
        return itemsFiltered;
    }

    public void removeItem(HistoryEntity item) {
        ThreadPool.execute(() -> {
            dao.delete(item);
            fetch();
        });
    }

    public void removeAll() {
        ThreadPool.execute(() -> {
            dao.deleteAll();
            fetch();
        });
    }

    public void filter(int mask) {
        filter.setValue(mask);
    }

    private void fetch() {
        ThreadPool.execute(() -> {
            List<HistoryEntity> items = dao.getAll();
            itemsRaw.postValue(items);
        });
    }
}