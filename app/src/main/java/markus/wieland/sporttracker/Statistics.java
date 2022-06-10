package markus.wieland.sporttracker;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import markus.wieland.sporttracker.sportevents.models.SportEventWithPosition;

@Getter
@Setter
public class Statistics {

    private List<DataPoint> dataPoints;

    public Statistics(List<SportEventWithPosition> events) {
        this.dataPoints = new ArrayList<>();

        for (SportEventWithPosition event : events) {
            this.dataPoints.add(new DataPoint(event.getSportEvent()));
        }

    }

}
