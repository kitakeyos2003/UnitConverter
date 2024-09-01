package vn.edu.eaut.unitconverter.history;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import vn.edu.eaut.unitconverter.model.database.HistoryDao;
import vn.edu.eaut.unitconverter.model.database.HistoryDatabase;
import vn.edu.eaut.unitconverter.model.database.HistoryItem;

@HiltViewModel
public class HistoryViewModel extends ViewModel {

    ExecutorService executorService = Executors.newFixedThreadPool(4);

    private final HistoryDao dao;
    private final MutableLiveData<List<HistoryItem>> itemsRaw = new MutableLiveData<>();
    private final MutableLiveData<Integer> filter = new MutableLiveData<>(0);

    private final MediatorLiveData<List<HistoryItem>> itemsFiltered = new MediatorLiveData<List<HistoryItem>>() {{
        addSource(filter, mask -> {
            List<HistoryItem> rawItems = itemsRaw.getValue();
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
    public HistoryViewModel(HistoryDatabase db) {
        dao = db.dao();
        fetch();
    }

    public LiveData<List<HistoryItem>> getItems() {
        return itemsFiltered;
    }

    public void removeItem(HistoryItem item) {
        executorService.execute(() -> {
            dao.delete(item);
            fetch();
        });
    }

    public void removeAll() {
        executorService.execute(() -> {
            dao.deleteAll();
            fetch();
        });
    }

    public void filter(int mask) {
        filter.setValue(mask);
    }

    private void fetch() {
        executorService.execute(() -> {
            List<HistoryItem> items = dao.getAll();
            itemsRaw.postValue(items);
        });
    }
}