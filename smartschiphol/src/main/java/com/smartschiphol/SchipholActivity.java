package com.smartschiphol;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.smartschiphol.flightservice.FlightService;
import com.smartschiphol.flightservice.FlightServiceImpl;

import javax.xml.transform.Result;
import java.io.IOException;


public class SchipholActivity extends Activity {
    private static final String TAG = "SchipholActivity";
    private final FlightService flightService = new FlightServiceImpl();
    private EditText pnrNumberEditText;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schiphol);

        pnrNumberEditText = (EditText) findViewById(R.id.pnr_number);
        textView = (TextView) findViewById(R.id.text);
        final Button loadButton = (Button) findViewById(R.id.submit);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pnrNumber = pnrNumberEditText.getText().toString();
                Log.d(TAG, "PNR Number is " + pnrNumber);
                if (pnrNumber != null && !"".equals(pnrNumber.trim())) {
                    new LoadFlightData().execute(pnrNumber);
                } else {
                    textView.setText("Enter PNR NUMBER");
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
                return flightService.getDepartingFlight(params[0]).toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            textView.setText(s);
        }
    }
}
