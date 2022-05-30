package markus.wieland.sporttracker.positions;

import android.app.Application;

import androidx.annotation.NonNull;

import java.util.List;

import markus.wieland.databases.BaseRepository;
import markus.wieland.databases.tasks.InsertItemTask;
import markus.wieland.sporttracker.database.SportDatabase;
import markus.wieland.sporttracker.positions.models.Position;

public class PositionRepository extends BaseRepository<Position, PositionDataAccessObject> {

    public PositionRepository(@NonNull Application application) {
        super(application);
    }

    @Override
    public PositionDataAccessObject initDataAccessObject(@NonNull Application application) {
        return SportDatabase.getInstance(application).getPositionDataAccessObject();
    }

    public void insertAll(List<Position> positions) {
        new InsertAllItemsTask(getDataAccessObject()).execute(positions.toArray(new Position[0]));
    }
}
