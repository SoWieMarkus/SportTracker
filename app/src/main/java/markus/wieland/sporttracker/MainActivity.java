package markus.wieland.sporttracker;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.View;

import java.util.List;

import markus.wieland.defaultappelements.uielements.activities.DefaultActivity;
import markus.wieland.sporttracker.sportevents.SportEventAdapter;
import markus.wieland.sporttracker.sportevents.database.SportEventViewModel;
import markus.wieland.sporttracker.sportevents.models.SportEventWithPosition;

public class MainActivity extends DefaultActivity implements Observer<List<SportEventWithPosition>> {

    private SportEventViewModel sportEventViewModel;
    private RecyclerView recyclerView;
    private SportEventAdapter sportEventAdapter;

    public MainActivity() {
        super(R.layout.activity_main);
    }

    @Override
    public void bindViews() {
        recyclerView = findViewById(R.id.activity_main_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void initializeViews() {
        sportEventViewModel = ViewModelProviders.of(this).get(SportEventViewModel.class);
        sportEventViewModel.getAllSportEvents().observe(this, this);
    }

    @Override
    public void execute() {
        sportEventAdapter = new SportEventAdapter(null);
        recyclerView.setAdapter(sportEventAdapter);
        startActivity(new Intent(this, RunActivity.class));


        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RunActivity.class));
            }
        });
    }

    @Override
    public void onChanged(List<SportEventWithPosition> sportEventWithPositions) {
        sportEventWithPositions.size();
        sportEventAdapter.submitList(sportEventWithPositions);
    }
}