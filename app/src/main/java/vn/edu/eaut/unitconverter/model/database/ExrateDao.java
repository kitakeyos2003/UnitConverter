package vn.edu.eaut.unitconverter.model.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ExrateDao {

    @Insert
    void insertExrates(List<ExrateEntity> exrates);

    @Query("SELECT * FROM exrates")
    List<ExrateEntity> getAllExrates();

    @Query("DELETE FROM exrates")
    void deleteAllExrates();
}
