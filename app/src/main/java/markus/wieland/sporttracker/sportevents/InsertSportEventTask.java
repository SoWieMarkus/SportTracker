package markus.wieland.sporttracker.sportevents;

import android.os.AsyncTask;

import markus.wieland.sporttracker.sportevents.database.SportEventDataAccessObject;
import markus.wieland.sporttracker.sportevents.models.SportEvent;

public class InsertSportEventTask extends AsyncTask<SportEvent, Void, Long> {

    private final SportEventDataAccessObject sportEventDataAccessObject;

    public InsertSportEventTask(SportEventDataAccessObject dataAccessObject) {
        this.sportEventDataAccessObject = dataAccessObject;
    }

    @Override
    protected Long doInBackground(SportEvent... sportEvents) {
        if (sportEvents.length != 1) return null;
        return sportEventDataAccessObject.insertSportEvent(sportEvents[0]);
    }
}
