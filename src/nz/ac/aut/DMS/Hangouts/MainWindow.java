package nz.ac.aut.DMS.Hangouts;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import nz.ac.aut.DMS.Hangouts.ServerStuff.Server;
import nz.ac.aut.DMS.Hangouts.ServerStuff.ServerHangoutResponse;

import java.util.Date;

/**
 * Created by Ashellan Edmonds on 7/05/15.
 */
public class MainWindow extends Activity {

    private String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainwindow);
        username = getIntent().getExtras().getString("name");
    }
    public void listview(View view) {
        Intent intent = new Intent(this, friendsActivity.class);
        intent.putExtra("name", username);
        startActivity(intent);
    }

    public void hangouts(View view) {
        Location location = MyLocationListener.lastKnownLocation;
        if(location == null) {
            Toast.makeText(this, "Location not found", Toast.LENGTH_SHORT).show();
            return;
        }
        ServerHangoutResponse hangout = Server.Hangout(username, String.valueOf(location.getLongitude()), String.valueOf(location.getLatitude()), new Date().toString());
        if(hangout.equals(ServerHangoutResponse.SUCCESS)){

        }

    }
}
