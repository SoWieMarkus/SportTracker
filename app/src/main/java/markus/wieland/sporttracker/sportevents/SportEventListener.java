package markus.wieland.sporttracker.sportevents;

import markus.wieland.sporttracker.sportevents.models.SportEventWithPosition;

public interface SportEventListener {

    void onSportEventStart(SportEventWithPosition sportEventWithPosition);
    void onSportEventStop(SportEventWithPosition sportEventWithPosition);
    void onSportEventPause(SportEventWithPosition sportEventWithPosition);

}
