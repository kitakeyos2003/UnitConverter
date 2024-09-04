package vn.edu.eaut.unitconverter.api;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.eaut.unitconverter.model.database.ExrateDao;
import vn.edu.eaut.unitconverter.model.database.ExrateEntity;
import vn.edu.eaut.unitconverter.util.ThreadPool;

public class ExrateRepository {

    private final VietcombankApi vietcombankApi;
    private final ExrateDao exrateDao;

    public ExrateRepository(VietcombankApi vietcombankApi, ExrateDao exrateDao) {
        this.vietcombankApi = vietcombankApi;
        this.exrateDao = exrateDao;
    }

    public void fetchAndStoreExrateList(Callback<ExrateList> callback) {
        vietcombankApi.getExrateList().enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<ExrateList> call, @NonNull Response<ExrateList> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ExrateEntity> exrateList = convertToEntities(response.body());
                    ThreadPool.execute(() -> {
                        exrateDao.deleteAllExrates();
                        exrateDao.insertExrates(exrateList);
                        callback.onResponse(call, Response.success(response.body()));
                    });
                } else {
                    callback.onFailure(call, new Throwable("Failed to fetch data"));
                }
            }

            @Override
            public void onFailure(Call<ExrateList> call, Throwable t) {
                callback.onFailure(call, t);
            }
        });
    }

    public void getLocalExrates(Callback<ExrateList> callback) {
        ThreadPool.execute(() -> {
            ExrateList exrateList = convertToExrateList(exrateDao.getAllExrates());
            callback.onResponse(null, Response.success(exrateList));
        });
    }

    private List<ExrateEntity> convertToEntities(ExrateList exrateList) {
        List<ExrateEntity> list = new ArrayList<>();
        for (Exrate exrate : exrateList.getExrates()) {
            ExrateEntity exrateEntity = new ExrateEntity(0, exrate.getCurrencyCode(), exrate.getCurrencyName(), exrate.getBuy(), exrate.getTransfer(), exrate.getSell());
            list.add(exrateEntity);
        }
        return list;
    }

    private ExrateList convertToExrateList(List<ExrateEntity> list) {
        ExrateList exrateList = new ExrateList();
        for (ExrateEntity exrateEntity : list) {
            Exrate exrate = new Exrate(exrateEntity.getCurrencyCode(), exrateEntity.getCurrencyName(), exrateEntity.getBuy(), exrateEntity.getTransfer(), exrateEntity.getSell());
            exrateList.addExrate(exrate);
        }
        return exrateList;
    }
}

