package markus.wieland.sporttracker;

import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;
import markus.wieland.defaultappelements.uielements.adapter.iteractlistener.OnItemClickListener;
import markus.wieland.defaultappelements.uielements.fragments.DefaultFragment;
import markus.wieland.sporttracker.sportevents.SportEventAdapter;
import markus.wieland.sporttracker.sportevents.models.SportEventWithPosition;

@Getter
public class ProgressFragment extends DefaultFragment {

    private RecyclerView recyclerView;
    private final SportEventAdapter sportEventAdapter;
    private StatisticsView statisticsView;

    public ProgressFragment(OnItemClickListener<SportEventWithPosition> onItemClickListener) {
        super(R.layout.fragment_progress);
        sportEventAdapter = new SportEventAdapter(onItemClickListener);
    }

    @Override
    public void bindViews() {
        recyclerView = findViewById(R.id.fragment_progress_recycler_view);
        statisticsView = findViewById(R.id.fragment_progress_statistics);
    }

    @Override
    public void initializeViews() {
        recyclerView.setHasFixedSize(true);

        if (getActivity() == null) return;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void execute() {
        recyclerView.setAdapter(sportEventAdapter);
        statisticsView.show(new Statistics(new ArrayList<>()));
    }
}
