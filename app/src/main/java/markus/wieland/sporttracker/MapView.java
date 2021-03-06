package markus.wieland.sporttracker;

import android.content.Context;
import android.location.Location;
import android.util.AttributeSet;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;

import java.util.List;

import markus.wieland.sporttracker.positions.models.Position;

public class MapView extends WebView {

    private final Gson gson;

    public MapView(@NonNull Context context) {
        this(context, null);
    }

    public MapView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MapView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public MapView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize();
        gson = new Gson();
    }

    private void initialize() {
        loadUrl("file:///android_asset/maps/map.html");
        getSettings().setJavaScriptEnabled(true);
        getSettings().setDomStorageEnabled(true);
    }

    public void showTracking(List<Position> positions) {
        loadUrl("javascript:showTracking(" + gson.toJson(positions) + ")");
    }

    public void showLiveTracking(List<Position> positions) {
        loadUrl("javascript:showLiveTracking(" + gson.toJson(positions) + ")");
    }

    public void showUserPosition(Position currentLocation) {
        loadUrl("javascript:updateUserPosition(" + gson.toJson(currentLocation) + ")");
    }
}
