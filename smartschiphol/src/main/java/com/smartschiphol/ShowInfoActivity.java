package com.smartschiphol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.GridView;
import android.widget.TextView;
import com.smartschiphol.flightservice.Flight;
import com.smartschiphol.flightservice.FlightState;

import java.text.SimpleDateFormat;
import java.util.Date;


public class ShowInfoActivity extends Activity {

    public static final String EXTRA_FLIGHT = "com.smartschiphol.ShowInfoActivity.FLIGHT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_show_info);

        Intent intent = getIntent();
        Flight flight = (Flight) intent.getSerializableExtra(EXTRA_FLIGHT);
        if (flight != null) {
            ((TextView) findViewById(R.id.flightNumberTextView)).setText(flight.getFlightNumber());
            ((TextView) findViewById(R.id.gateTextView)).setText(flight.getGate());
            ((TextView) findViewById(R.id.statusTextView)).setText(formatStatus(flight.getState()));
            ((TextView) findViewById(R.id.boardingTimeTextView)).setText(formatBoardingTime(flight.getScheduleDateTime()));
        }

        GridView gridView = (GridView) findViewById(R.id.promotionGridView);
        gridView.setAdapter(new ImageAdapter(this));
    }

    private String formatStatus(FlightState status) {
        switch (status) {
            case SCHEDULED:
            case INITIATED:
                return "Scheduled, on time";
            case CANCELLED:
                return "Cancelled";
            case BOARDING:
                return "Boarding";
            case GATE_CLOSED:
                return "Gate closed";
            case TAXIING:
                return "Ready for take-off";
            case AIRBORNE:
                return "Departed";
            default:
                return "Unknown";
        }
    }

    private String formatBoardingTime(Date boardingTime) {
        return new SimpleDateFormat("HH:mm").format(boardingTime);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.show_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
