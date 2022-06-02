package markus.wieland.sporttracker;

import android.location.Location;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import markus.wieland.defaultappelements.uielements.fragments.DefaultFragment;
import markus.wieland.sporttracker.helper.CountUpListener;
import markus.wieland.sporttracker.helper.CountUpTimer;
import markus.wieland.sporttracker.helper.TimeConverter;
import markus.wieland.sporttracker.positions.models.Position;
import markus.wieland.sporttracker.sportevents.SportEventListener;
import markus.wieland.sporttracker.sportevents.models.SportEvent;
import markus.wieland.sporttracker.sportevents.models.SportEventWithPosition;

public class SportEventFragment extends DefaultFragment implements CountUpListener {


    private boolean wasPaused;
    private final SportEventListener sportEventListener;

    private TextView time;
    private TextView distance;
    private TextView averageSpeed;
    private TextView averageSpeedPerKm;
    private Button start;
    private Button stop;
    private Button pause;

    private MapView mapView;

    private SportEventWithPosition sportEventWithPosition;
    private WorkoutState workoutState;

    private Location lastLocation;
    private CountUpTimer timer;


    public SportEventFragment(SportEventListener sportEventListener) {
        super(R.layout.fragment_sport_event);
        this.sportEventListener = sportEventListener;
        this.workoutState = WorkoutState.WAITING_TO_START;
        this.wasPaused = false;
    }

    @Override
    public void bindViews() {
        time = findViewById(R.id.fragment_sport_event_time);
        distance = findViewById(R.id.fragment_sport_event_distance);
        averageSpeed = findViewById(R.id.fragment_sport_event_average_speed);
        averageSpeedPerKm = findViewById(R.id.fragment_sport_event_average_speed_per_km);
        start = findViewById(R.id.fragment_sport_event_start);
        stop = findViewById(R.id.fragment_sport_event_stop);
        pause = findViewById(R.id.fragment_sport_event_pause);
        mapView = findViewById(R.id.fragment_sport_event_map_view);
    }

    @Override
    public void initializeViews() {
        start.setOnClickListener(v -> start());
        stop.setOnClickListener(v -> stop());
        pause.setOnClickListener(v -> pause());
    }

    @Override
    public void execute() {
        sportEventWithPosition = new SportEventWithPosition();
        sportEventWithPosition.setSportEvent(new SportEvent());
        sportEventWithPosition.setPositions(new ArrayList<>());

        timer = new CountUpTimer(this);
        toggleButtonVisibility(workoutState);
    }

    public void onLocationChanged(Location location) {
        mapView.showUserPosition(new Position(location, 0));
        if (workoutState != WorkoutState.IS_RUNNING) return;
        if (lastLocation == null) {
            lastLocation = location;
            return;
        }

        float distanceToLastLocation = lastLocation.distanceTo(location);

        if (wasPaused) {
            wasPaused = false;
            distanceToLastLocation = 0;
        }

        Position position = new Position(location, distanceToLastLocation);
        sportEventWithPosition.getPositions().add(position);

        averageSpeed.setText(TimeConverter.formatSpeed(sportEventWithPosition.getCurrentSpeed()));
        averageSpeedPerKm.setText(TimeConverter.formatSpeedPerKm(sportEventWithPosition.getCurrentSpeedPerKm()));
        distance.setText(TimeConverter.formatDistance(sportEventWithPosition.getTotalDistance()));

        mapView.showLiveTracking(sportEventWithPosition.getPositions());
        lastLocation = location;
    }

    private void start() {
        timer.start();
        workoutState = WorkoutState.IS_RUNNING;
        toggleButtonVisibility(workoutState);

        sportEventWithPosition.start();
        sportEventListener.onSportEventStart(sportEventWithPosition);
    }

    private void pause() {
        wasPaused = true;
        timer.stop();
        workoutState = WorkoutState.PAUSED;
        toggleButtonVisibility(workoutState);

        sportEventListener.onSportEventPause(sportEventWithPosition);
    }

    private void stop() {
        timer.stop();
        workoutState = WorkoutState.STOPPED;
        toggleButtonVisibility(workoutState);

        sportEventWithPosition.stop();
        sportEventListener.onSportEventStop(sportEventWithPosition);
    }

    private void toggleButtonVisibility(WorkoutState state) {
        stop.setVisibility(state == WorkoutState.IS_RUNNING ? View.VISIBLE : View.GONE);
        pause.setVisibility(state == WorkoutState.IS_RUNNING ? View.VISIBLE : View.GONE);
        start.setVisibility(state == WorkoutState.IS_RUNNING ? View.GONE : View.VISIBLE);
    }


    @Override
    public void onCountUpSecond(long currentTime) {
        time.setText(TimeConverter.convertMillisToString(currentTime));
    }
}
