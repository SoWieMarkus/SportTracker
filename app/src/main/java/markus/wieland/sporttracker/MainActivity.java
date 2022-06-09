package markus.wieland.sporttracker;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;
import java.util.Objects;

import markus.wieland.defaultappelements.uielements.activities.DefaultActivity;
import markus.wieland.sporttracker.positions.PositionViewModel;
import markus.wieland.sporttracker.positions.models.Position;
import markus.wieland.sporttracker.sportevents.SportEventAdapter;
import markus.wieland.sporttracker.sportevents.SportEventListener;
import markus.wieland.sporttracker.sportevents.database.SportEventViewModel;
import markus.wieland.sporttracker.sportevents.models.SportEventWithPosition;

public class MainActivity extends DefaultActivity implements Observer<List<SportEventWithPosition>>, LocationListener, NavigationBarView.OnItemSelectedListener, SportEventListener {

    private static final int LOCATION_REFRESH_TIME = 5000;
    private static final int LOCATION_REFRESH_DISTANCE = 5;
    private static final int REQUEST_LOCATION_PERMISSION = 1;

    private SportEventViewModel sportEventViewModel;
    private PositionViewModel positionViewModel;

    private ProgressFragment progressFragment;
    private SportEventFragment sportEventFragment;

    private BottomNavigationView bottomNavigationView;

    public MainActivity() {
        super(R.layout.activity_main);
    }

    @Override
    public void bindViews() {
        bottomNavigationView = findViewById(R.id.activity_main_bottom_navigation_view);
    }

    @Override
    public void initializeViews() {
        Objects.requireNonNull(getSupportActionBar()).setElevation(0);
        bottomNavigationView.setOnItemSelectedListener(this);

        sportEventViewModel = ViewModelProviders.of(this).get(SportEventViewModel.class);
        positionViewModel = ViewModelProviders.of(this).get(PositionViewModel.class);

        progressFragment = new ProgressFragment(this::getDetailsOfSportEvent);
        sportEventFragment = new SportEventFragment(this);

        selectFragment(R.id.menu_bottom_navigation_sport_event);
    }

    @Override
    public void execute() {
        sportEventViewModel.getAllSportEvents().observe(this, this);
        registerLocationManager();
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
    public void onChanged(List<SportEventWithPosition> sportEventWithPositions) {
        progressFragment.getSportEventAdapter().submitList(sportEventWithPositions);
    }

    public void getDetailsOfSportEvent(SportEventWithPosition sportEventWithPosition) {
        startActivity(new Intent(this, DetailActivity.class).putExtra(DetailActivity.KEY_SPORT_EVENT, sportEventWithPosition));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return selectFragment(item.getItemId());
    }

    private boolean selectFragment(@IdRes int itemId) {

        // can't use switch statement because of dynamic ids (wtf android?)

        if (itemId == R.id.menu_bottom_navigation_progress) {
            showFragment(progressFragment, R.string.bottom_navigation_progress);
            return true;
        }
        if (itemId == R.id.menu_bottom_navigation_sport_event) {
            showFragment(sportEventFragment, R.string.bottom_navigation_sport_event);
            return true;
        }

        return false;
    }


    @Override
    public void onSportEventStart(SportEventWithPosition sportEventWithPosition) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        bottomNavigationView.setVisibility(View.GONE);
    }

    @Override
    public void onSportEventStop(SportEventWithPosition sportEventWithPosition) {
        getWindow().clearFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        long id = sportEventViewModel.insertSportEvent(sportEventWithPosition.getSportEvent());
        for (Position position: sportEventWithPosition.getPositions()) {
            position.setSportEventId(id);
        }
        positionViewModel.insertAll(sportEventWithPosition.getPositions());
        getDetailsOfSportEvent(sportEventWithPosition);
        bottomNavigationView.setVisibility(View.VISIBLE);
        sportEventFragment = new SportEventFragment(this);
        selectFragment(R.id.menu_bottom_navigation_sport_event);
    }

    @Override
    public void onSportEventPause(SportEventWithPosition sportEventWithPosition) {
        getWindow().clearFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    private void showFragment(Fragment fragment, @StringRes int title) {
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_frame_layout, fragment).commit();
        Objects.requireNonNull(getSupportActionBar()).setTitle(title);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        sportEventFragment.onLocationChanged(location);
    }
}