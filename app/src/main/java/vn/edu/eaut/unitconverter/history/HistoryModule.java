package vn.edu.eaut.unitconverter.history;

import android.content.Context;
import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import vn.edu.eaut.unitconverter.model.database.HistoryDatabase;

@Module
@InstallIn(SingletonComponent.class)
public class HistoryModule {
    @Provides
    public HistoryDatabase provideDatabase(
            @ApplicationContext Context context
    ) {
        return Room.databaseBuilder(context, HistoryDatabase.class, "history-db").build();
    }
}
