package markus.wieland.sporttracker;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;

public class StatisticsView extends WebView {

    private final Gson gson;

    public StatisticsView(@NonNull Context context) {
        this(context, null);
    }

    public StatisticsView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatisticsView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public StatisticsView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        gson = new Gson();
        initialize();
    }

    private void initialize(){
        loadUrl("file:///android_asset/stats/statistics.html");
        getSettings().setJavaScriptEnabled(true);
        getSettings().setDomStorageEnabled(true);
    }

    public void show(Statistics statistics) {
        loadUrl("javascript:showStatistics(" + gson.toJson(statistics) + ")");

    }


}
