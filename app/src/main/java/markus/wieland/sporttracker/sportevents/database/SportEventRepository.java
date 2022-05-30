package markus.wieland.sporttracker.sportevents.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

import markus.wieland.databases.BaseRepository;
import markus.wieland.sporttracker.database.SportDatabase;
import markus.wieland.sporttracker.sportevents.InsertSportEventTask;
import markus.wieland.sporttracker.sportevents.database.SportEventDataAccessObject;
import markus.wieland.sporttracker.sportevents.models.SportEvent;
import markus.wieland.sporttracker.sportevents.models.SportEventWithPosition;

public class SportEventRepository extends BaseRepository<SportEvent, SportEventDataAccessObject> {

    public SportEventRepository(@NonNull Application application) {
        super(application);
    }

    @Override
    public SportEventDataAccessObject initDataAccessObject(@NonNull Application application) {
        return SportDatabase.getInstance(application).getSportEventDataAccessObject();
    }

    public LiveData<SportEventWithPosition> getSportEventById(long sportEventId){
        return getDataAccessObject().getSportEventById(sportEventId);
    }


    public LiveData<List<SportEventWithPosition>> getAllSportEvents(){
        return getDataAccessObject().getAllSportEvents();
    }

    public long insertSportEvent(SportEvent sportEvent) {
        long id;
        try {
            id = new InsertSportEventTask(getDataAccessObject()).execute(sportEvent).get();
        } catch (ExecutionException | InterruptedException e) {
            id = -1;
            e.printStackTrace();
        }
        return id;
    }
}
