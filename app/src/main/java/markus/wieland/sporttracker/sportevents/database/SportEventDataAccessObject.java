package markus.wieland.sporttracker.sportevents.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import markus.wieland.databases.BaseDataAccessObject;
import markus.wieland.sporttracker.sportevents.models.SportEvent;
import markus.wieland.sporttracker.sportevents.models.SportEventWithPosition;

@Dao
public interface SportEventDataAccessObject extends BaseDataAccessObject<SportEvent> {

    @Transaction
    @Query("SELECT * FROM SportEvent WHERE id = :sportEventId")
    LiveData<SportEventWithPosition> getSportEventById(long sportEventId);

    @Transaction
    @Query("SELECT * FROM SportEvent ORDER BY startTime ASC")
    LiveData<List<SportEventWithPosition>> getAllSportEvents();

    @Insert
    long insertSportEvent(SportEvent sportEvent);

}
