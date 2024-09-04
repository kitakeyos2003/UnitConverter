package vn.edu.eaut.unitconverter;

import android.content.Context;
import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import vn.edu.eaut.unitconverter.model.database.Database;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {
    @Provides
    public Database provideDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(context, Database.class, "app-db").fallbackToDestructiveMigration().build();
    }
}
