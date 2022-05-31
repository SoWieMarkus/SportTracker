package markus.wieland.sporttracker.sportevents.models;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import markus.wieland.databases.DatabaseEntity;

@Getter
@Setter
@Entity
public class SportEvent implements DatabaseEntity, Serializable {

    private long startTime;
    private long endTime;

    @PrimaryKey(autoGenerate = true)
    private long id;

    private double averageSpeed;
    private double maxSpeed;
    private double averageSpeedPerKm;

    private double totalDistance;

    @Ignore
    public long getDuration(){
        return this.endTime - this.startTime;
    }

    @Override
    public long getUniqueId() {
        return getId();
    }
}