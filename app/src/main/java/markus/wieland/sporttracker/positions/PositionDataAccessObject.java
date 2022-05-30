package markus.wieland.sporttracker.positions;

import androidx.room.Dao;
import androidx.room.Insert;

import java.util.List;

import markus.wieland.databases.BaseDataAccessObject;
import markus.wieland.sporttracker.positions.models.Position;

@Dao
public interface PositionDataAccessObject extends BaseDataAccessObject<Position> {

    @Insert
    void insertAll(Position... positions);

}
