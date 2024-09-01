package vn.edu.eaut.unitconverter.model.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {HistoryItem.class}, version = 1, exportSchema = false)
public abstract class HistoryDatabase extends RoomDatabase {
    public abstract HistoryDao dao();
}
