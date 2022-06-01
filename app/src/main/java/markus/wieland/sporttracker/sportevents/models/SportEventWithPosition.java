package markus.wieland.sporttracker.sportevents.models;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import markus.wieland.sporttracker.positions.models.Position;

@Getter
@Setter
public class SportEventWithPosition implements Serializable {

    @Embedded
    private SportEvent sportEvent;

    @Relation(parentColumn = "id", entityColumn = "sportEventId")
    private List<Position> positions;

    public long getDurationSinceLastPosition() {
        if (positions.isEmpty()) return 0;
        if (positions.size() == 1)
            return positions.get(0).getTimeStamp() - sportEvent.getStartTime();
        return positions.get(positions.size() - 1).getTimeStamp() -
                positions.get(positions.size() - 2).getTimeStamp();
    }

    public long getDuration() {
        if (positions.isEmpty()) return 0;
        return positions.get(positions.size() - 1).getTimeStamp() - sportEvent.getStartTime();
    }

    public float getCurrentSpeed() {
        if (positions.isEmpty()) return 0;
        return (float) ((positions.get(positions.size() - 1).getDifferenceToLastLocation() / (getDurationSinceLastPosition() / 1000f)) * 3.6);
    }

    public float getTotalDistance() {
        float distance = 0;
        for (Position position : positions) {
            distance += position.getDifferenceToLastLocation();
        }
        return distance;
    }

    public float getCurrentSpeedPerKm() {
        return (getDuration() / (1000f * 60)) / (getTotalDistance() / 1000f);
    }

    public void start(){
        sportEvent.setStartTime(System.currentTimeMillis());
    }

    public void stop() {
        float totalDistance = getTotalDistance();
        float totalDuration = getDuration();

        getSportEvent().setEndTime(System.currentTimeMillis());
        getSportEvent().setTotalDistance(getTotalDistance());
        getSportEvent().setAverageSpeed((float) (totalDistance / (totalDuration / 1000f) * 3.6));
        getSportEvent().setAverageSpeedPerKm((totalDuration / (1000f * 60)) / (totalDistance / 1000f));
    }


}
