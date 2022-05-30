package markus.wieland.sporttracker.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import markus.wieland.sporttracker.positions.models.Position;
import markus.wieland.sporttracker.positions.PositionDataAccessObject;
import markus.wieland.sporttracker.sportevents.database.SportEventDataAccessObject;
import markus.wieland.sporttracker.sportevents.models.SportEvent;

@Database(entities = {SportEvent.class, Position.class}, version = 4)
public abstract class SportDatabase extends RoomDatabase {

    private static SportDatabase instance;

    public abstract SportEventDataAccessObject getSportEventDataAccessObject();

    public abstract PositionDataAccessObject getPositionDataAccessObject();

    public static synchronized SportDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), SportDatabase.class, "sport_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }


}
