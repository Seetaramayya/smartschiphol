package com.smartschiphol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import com.smartschiphol.flightservice.Flight;
import com.smartschiphol.flightservice.FlightState;

import java.util.Calendar;


public class WelcomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_welcome);

        final Button submitButton = (Button) findViewById(R.id.welcome_submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, ShowInfoActivity.class);

                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.HOUR, 2);

                Flight fake = new Flight();
                fake.setFlightNumber("HV611");
                fake.setScheduleDateTime(cal.getTime());
                fake.setGate("D12");
                fake.setState(FlightState.SCHEDULED);

                intent.putExtra(ShowInfoActivity.EXTRA_FLIGHT, fake);

                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.welcome, menu);
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
