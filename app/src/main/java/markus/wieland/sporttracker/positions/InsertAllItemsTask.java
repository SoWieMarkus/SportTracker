package markus.wieland.sporttracker.positions;

import android.os.AsyncTask;

import markus.wieland.sporttracker.positions.models.Position;

public class InsertAllItemsTask extends AsyncTask<Position, Void, Void> {

    private final PositionDataAccessObject positionDataAccessObject;

    public InsertAllItemsTask(PositionDataAccessObject dataAccessObject) {
        this.positionDataAccessObject = dataAccessObject;
    }

    @Override
    protected Void doInBackground(Position... positions) {
        if (positions.length == 0) return null;
        positionDataAccessObject.insertAll(positions);
        return null;
    }
}
