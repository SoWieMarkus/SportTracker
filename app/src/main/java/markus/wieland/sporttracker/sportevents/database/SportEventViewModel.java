package markus.wieland.sporttracker.sportevents.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

import markus.wieland.databases.BaseViewModel;
import markus.wieland.sporttracker.sportevents.database.SportEventDataAccessObject;
import markus.wieland.sporttracker.sportevents.database.SportEventRepository;
import markus.wieland.sporttracker.sportevents.models.SportEvent;
import markus.wieland.sporttracker.sportevents.models.SportEventWithPosition;

public class SportEventViewModel extends BaseViewModel<SportEvent, SportEventDataAccessObject, SportEventRepository> {

    public SportEventViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected SportEventRepository initRepository() {
        return new SportEventRepository(getApplication());
    }

    public LiveData<SportEventWithPosition> getSportEventById(long sportEventId){
        return getRepository().getSportEventById(sportEventId);
    }
    
    public LiveData<List<SportEventWithPosition>> getAllSportEvents(){
        return getRepository().getAllSportEvents();
    }

    public long insertSportEvent(SportEvent sportEvent) {
        return getRepository().insertSportEvent(sportEvent);
    }
}
