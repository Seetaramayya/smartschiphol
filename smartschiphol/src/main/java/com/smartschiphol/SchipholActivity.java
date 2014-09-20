package com.smartschiphol;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.smartschiphol.flightservice.Flight;
import com.smartschiphol.flightservice.FlightService;
import com.smartschiphol.flightservice.FlightServiceImpl;

import java.io.IOException;
import java.text.SimpleDateFormat;


public class SchipholActivity extends Activity {
    private static final String TAG = "SchipholActivity";

    private final FlightService flightService = new FlightServiceImpl();

    private EditText flightNumberEditText;
    private TextView flightInfoTextView;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_schiphol);

        flightNumberEditText = (EditText) findViewById(R.id.flightNumber);
        flightInfoTextView = (TextView) findViewById(R.id.flightInfo);
        submitButton = (Button) findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String flightNumber = flightNumberEditText.getText().toString();
                Log.d(TAG, "Flight number is: " + flightNumber);
                flightInfoTextView.setText("Getting flight information...");
                if (flightNumber != null && !"".equals(flightNumber.trim())) {
                    new LoadFlightData().execute(flightNumber);
                } else {
                    flightInfoTextView.setText("");
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.schiphol, menu);
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

    class LoadFlightData extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                Flight flight = flightService.getDepartingFlight(params[0]);
                if (flight != null) {
                    String dateTime = new SimpleDateFormat("d MMM yyyy HH:mm").format(flight.getScheduleDateTime());
                    return "Your flight is scheduled at " + dateTime + " at gate " + flight.getGate() +
                            ".\n\nPlease make sure to be at the gate at least 30 minutes in advance.";
                }
            } catch (IOException e) {
                Log.e(TAG, "An error occurred in the webservice call", e);
            }
            return "Flight information is not available.";
        }

        @Override
        protected void onPostExecute(String s) {
            flightInfoTextView.setText(s);
        }
    }
}
