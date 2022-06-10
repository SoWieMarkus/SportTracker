package markus.wieland.sporttracker;

import lombok.Getter;
import lombok.Setter;
import markus.wieland.sporttracker.sportevents.models.SportEvent;

@Getter
@Setter
public class DataPoint {

    private String date;
    private double distance;
    private float minutesPerKm;

    public DataPoint(SportEvent event) {
        this.date = event.getDateForGraph();
        this.distance = event.getTotalDistance();
        this.minutesPerKm = event.getAverageSpeedPerKm();
    }

}
