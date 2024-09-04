package vn.edu.eaut.unitconverter.model.database;

import androidx.room.RoomDatabase;

@androidx.room.Database(entities = {HistoryEntity.class, ExrateEntity.class}, version = 4, exportSchema = false)
public abstract class Database extends RoomDatabase {
    public abstract HistoryDao historyDao();
    public abstract ExrateDao exrateDao();
}
