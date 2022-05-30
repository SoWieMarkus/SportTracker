package markus.wieland.sporttracker;

import android.webkit.WebView;
import android.webkit.WebViewClient;

import markus.wieland.defaultappelements.uielements.activities.DefaultActivity;
import markus.wieland.sporttracker.sportevents.models.SportEventWithPosition;

public class DetailActivity extends DefaultActivity {

    public static final String KEY_SPORT_EVENT = "markus.wieland.sporttracker.KEY_SPORT_EVENT";

    private MapView mapView;

    public DetailActivity() {
        super(R.layout.activity_detail);
    }


    @Override
    public void bindViews() {
        mapView = findViewById(R.id.activity_detail_map);
    }

    @Override
    public void initializeViews() {

    }

    @Override
    public void execute() {
        SportEventWithPosition sportEventWithPosition = (SportEventWithPosition) getIntent().getSerializableExtra(KEY_SPORT_EVENT);
        mapView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                mapView.showTracking(sportEventWithPosition.getPositions());
            }
        });
    }
}