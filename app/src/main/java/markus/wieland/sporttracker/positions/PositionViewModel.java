package markus.wieland.sporttracker.positions;

import android.app.Application;

import androidx.annotation.NonNull;

import java.util.List;

import markus.wieland.databases.BaseViewModel;
import markus.wieland.sporttracker.positions.models.Position;

public class PositionViewModel extends BaseViewModel<Position, PositionDataAccessObject, PositionRepository> {

    public PositionViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected PositionRepository initRepository() {
        return new PositionRepository(getApplication());
    }

    public void insertAll(List<Position> positions) {
        getRepository().insertAll(positions);
    }
}
