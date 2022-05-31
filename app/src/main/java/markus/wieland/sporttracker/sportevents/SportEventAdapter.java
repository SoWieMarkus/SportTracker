package markus.wieland.sporttracker.sportevents;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import markus.wieland.defaultappelements.uielements.adapter.DefaultAdapter;
import markus.wieland.defaultappelements.uielements.adapter.DefaultViewHolder;
import markus.wieland.defaultappelements.uielements.adapter.iteractlistener.OnItemClickListener;
import markus.wieland.defaultappelements.uielements.adapter.iteractlistener.OnItemInteractListener;
import markus.wieland.sporttracker.R;
import markus.wieland.sporttracker.helper.TimeConverter;
import markus.wieland.sporttracker.sportevents.models.SportEventWithPosition;

public class SportEventAdapter extends DefaultAdapter<SportEventWithPosition, SportEventAdapter.SportEventViewHolder> {

    public SportEventAdapter(OnItemInteractListener<SportEventWithPosition> onItemInteractListener) {
        super(onItemInteractListener);
    }

    @NonNull
    @Override
    public SportEventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SportEventViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sport_event, parent, false));
    }

    @Override
    public OnItemClickListener<SportEventWithPosition> getOnItemInteractListener() {
        return (OnItemClickListener<SportEventWithPosition>)super.getOnItemInteractListener();
    }

    public class SportEventViewHolder extends DefaultViewHolder<SportEventWithPosition> {

        private TextView distance;
        private TextView duration;
        private TextView date;
        private TextView averageSpeed;
        private TextView averageSpeedPerKm;

        public SportEventViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void bindViews() {
            distance = findViewById(R.id.item_sport_event_distance);
            duration = findViewById(R.id.item_sport_event_duration);
            date = findViewById(R.id.item_sport_event_date);
            averageSpeed = findViewById(R.id.item_sport_event_average_speed);
            averageSpeedPerKm = findViewById(R.id.item_sport_event_average_speed_per_km);
        }

        @Override
        public void bindItemToViewHolder(SportEventWithPosition sportEvent) {
            distance.setText(TimeConverter.formatDistance((float) sportEvent.getSportEvent().getTotalDistance()));
            duration.setText(TimeConverter.convertMillisToString(sportEvent.getSportEvent().getDuration()));
            averageSpeed.setText(TimeConverter.formatSpeed((sportEvent.getSportEvent().getAverageSpeed())));
            averageSpeedPerKm.setText(TimeConverter.formatSpeedPerKm(sportEvent.getSportEvent().getAverageSpeedPerKm()));

            itemView.setOnClickListener(view -> getOnItemInteractListener().onClick(sportEvent));
        }
    }
}
