package markus.wieland.sporttracker.positions.models;

import android.location.Location;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import markus.wieland.databases.DatabaseEntity;
import markus.wieland.sporttracker.positions.PositionViewModel;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Position implements DatabaseEntity, Serializable {

    public Position(Location location, double differenceToLastLocation) {
        this.longitude = location.getLongitude();
        this.latitude = location.getLatitude();
        this.altitude = location.getAltitude();
        this.differenceToLastLocation = differenceToLastLocation;
        this.timeStamp = System.currentTimeMillis();
    }

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(index = true)
    private long sportEventId;

    private double longitude;
    private double latitude;
    private double altitude;

    private long timeStamp;

    private double differenceToLastLocation;

    @Override
    public long getUniqueId() {
        return getId();
    }
}
