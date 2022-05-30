package markus.wieland.sporttracker;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import markus.wieland.defaultappelements.uielements.activities.DefaultActivity;
import markus.wieland.sporttracker.helper.TimeConverter;
import markus.wieland.sporttracker.positions.PositionViewModel;
import markus.wieland.sporttracker.positions.models.Position;
import markus.wieland.sporttracker.sportevents.database.SportEventViewModel;
import markus.wieland.sporttracker.sportevents.models.SportEvent;
import markus.wieland.sporttracker.sportevents.models.SportEventWithPosition;

public class RunActivity extends DefaultActivity implements LocationListener {

    // TODO vibrieren bei jeder Minute / km?

    private PositionViewModel positionViewModel;
    private SportEventViewModel sportEventViewModel;

    private static final int LOCATION_REFRESH_TIME = 5000;
    private static final int LOCATION_REFRESH_DISTANCE = 5;
    private static final int REQUEST_LOCATION_PERMISSION = 1;

    private TextView time;
    private TextView distance;
    private TextView averageSpeed;
    private TextView averageSpeedPerKm;
    private Button start;
    private Button stop;
    private Button pause;
    private Timer timer;

    private long currentTime = 0;
    private long totalDistance = 0;
    private static final long TIME_INTERVAL = 100;

    private SportEventWithPosition sportEvent;
    private WorkoutState currentState;
    private Location lastLocation;

    public RunActivity() {
        super(R.layout.activity_run);
    }

    @Override
    public void bindViews() {
        time = findViewById(R.id.activity_run_time);
        distance = findViewById(R.id.activity_run_distance);
        averageSpeed = findViewById(R.id.activity_run_average_speed);
        averageSpeedPerKm = findViewById(R.id.activity_run_average_speed_per_km);
        start = findViewById(R.id.activity_run_start);
        stop = findViewById(R.id.activity_run_stop);
        pause = findViewById(R.id.activity_run_pause);
    }

    @Override
    public void initializeViews() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        start.setOnClickListener(v -> start());
        stop.setOnClickListener(v -> stop());
        pause.setOnClickListener(v -> pause());

        positionViewModel = ViewModelProviders.of(this).get(PositionViewModel.class);
        sportEventViewModel = ViewModelProviders.of(this).get(SportEventViewModel.class);
    }

    @Override
    public void execute() {
        sportEvent = new SportEventWithPosition();
        sportEvent.setSportEvent(new SportEvent());
        sportEvent.setPositions(new ArrayList<>());
        currentState = WorkoutState.WAITING_TO_START;
        toggleButtonVisibility(currentState);
        registerLocationManager();
    }


    private void registerLocationManager() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME, LOCATION_REFRESH_DISTANCE, this);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        if (currentState != WorkoutState.IS_RUNNING) return;
        if (lastLocation == null) lastLocation = location;

        double differenceToLastLocation = lastLocation.distanceTo(location);
        totalDistance += differenceToLastLocation;
        sportEvent.getPositions().add(new Position(location, differenceToLastLocation));

        averageSpeed.setText(sportEvent.getCurrentSpeed() + "km/h");
        averageSpeedPerKm.setText(sportEvent.getCurrentSpeedPerKm() + "min/km");
        distance.setText(sportEvent.getTotalDistance()+"m");

        lastLocation = location;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode != REQUEST_LOCATION_PERMISSION) return;
        if (grantResults.length == 0) return;
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                finish();
                return;
            }
        }

        registerLocationManager();
    }

    private void startTimer(){
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                currentTime += TIME_INTERVAL;
                runOnUiThread(() -> time.setText(TimeConverter.convertMillisToString(currentTime)));
            }
        }, 0,100L);
    }

    private void stopTimer(){
        timer.cancel();
    }

    private void pause(){
        stopTimer();
        currentState = WorkoutState.PAUSED;
        toggleButtonVisibility(currentState);
    }

    private void start(){
        if (currentState == WorkoutState.WAITING_TO_START) {
            sportEvent.getSportEvent().setStartTime(System.currentTimeMillis());
        }
        currentState = WorkoutState.IS_RUNNING;
        toggleButtonVisibility(currentState);

        startTimer();
    }

    private void stop(){
        stopTimer();
        sportEvent.getSportEvent().setEndTime(System.currentTimeMillis());
        sportEvent.getSportEvent().setTotalDistance(sportEvent.getTotalDistance());
        sportEvent.getSportEvent().setAverageSpeed(totalDistance / (sportEvent.getSportEvent().getDuration()/1000f) * 3.6);
        sportEvent.getSportEvent().setAverageSpeedPerKm((sportEvent.getSportEvent().getDuration() / (1000f* 60)) / (totalDistance / 1000f));
        currentState = WorkoutState.STOPPED;

        long id = sportEventViewModel.insertSportEvent(sportEvent.getSportEvent());

        for (Position position : sportEvent.getPositions()) {
            position.setSportEventId(id);
        }

        positionViewModel.insertAll(sportEvent.getPositions());

        startActivity(new Intent(this, DetailActivity.class)
                .putExtra(DetailActivity.KEY_SPORT_EVENT, sportEvent));
        finish();
    }

    private void toggleButtonVisibility(WorkoutState state) {
        stop.setVisibility(state == WorkoutState.IS_RUNNING ? View.VISIBLE : View.GONE);
        pause.setVisibility(state == WorkoutState.IS_RUNNING ? View.VISIBLE : View.GONE);
        start.setVisibility(state == WorkoutState.IS_RUNNING ? View.GONE: View.VISIBLE);
    }



}