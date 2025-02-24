package vn.edu.eaut.unitconverter.model.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface HistoryDao {

    @Query("SELECT * FROM history")
    List<HistoryEntity> getAll();

    @Insert
    void insertAll(HistoryEntity... items);

    @Delete
    void delete(HistoryEntity item);

    @Query("DELETE FROM history")
    void deleteAll();
}
