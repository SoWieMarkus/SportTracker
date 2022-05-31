package markus.wieland.sporttracker;

import android.widget.Button;
import android.widget.TextView;

import markus.wieland.defaultappelements.uielements.fragments.DefaultFragment;
import markus.wieland.sporttracker.sportevents.SportEventListener;

public class SportEventFragment extends DefaultFragment {

    private SportEventListener sportEventListener;

    private TextView time;
    private TextView distance;
    private TextView averageSpeed;
    private TextView averageSpeedPerKm;
    private Button start;
    private Button stop;
    private Button pause;

    public SportEventFragment(SportEventListener sportEventListener) {
        super(R.layout.fragment_sport_event);
        this.sportEventListener = sportEventListener;
    }

    @Override
    public void bindViews() {

    }

    @Override
    public void initializeViews() {

    }

    @Override
    public void execute() {

    }
}
