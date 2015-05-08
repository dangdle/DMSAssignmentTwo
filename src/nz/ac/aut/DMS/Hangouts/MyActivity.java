package nz.ac.aut.DMS.Hangouts;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import nz.ac.aut.DMS.Hangouts.ServerStuff.Server;
import nz.ac.aut.DMS.Hangouts.ServerStuff.SignIn;
import nz.ac.aut.DMS.Hangouts.ServerStuff.SignUp;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MyActivity extends Activity {
    private Server server;
    private LocationManager lm;
    private final Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);
    private Address address;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                1, 1000,
                new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
//                        toast(String.format("Latitude: %s  Longitude %s", location.getLatitude(), location.getLongitude()));
                        try {
                            List<Address> fromLocation = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            if (Geocoder.isPresent()) {
                                address = fromLocation.get(0);
                                //        if(address != null)
//        toast(address.toString());
                            }
//                            location.distanceTo(location);
//                            float[] f = new float[1];
//                            Location.distanceBetween(location.getLatitude(), location.getLongitude(), location.getLatitude(), location.getLongitude(), f);
//                            System.out.println("Distance in meters = " + f);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                });

    }



    public void login(View view) {
        SignIn signIn = Server.signIn(getUsername(), getPassword());
        toast(signIn.getToast());
        if(signIn.equals(SignIn.SUCCESS)){
            Intent intent = new Intent(this, MainWindow.class);
            intent.putExtra("name", getUsername());
            startActivity(intent);
        }

    }

    public void signup(View view) {
        SignUp signUp = Server.signUp(getUsername(), getPassword(), getPhoneNumber());
        toast(signUp.getToast());
        if(signUp.equals(SignUp.SUCCESS)){
            Intent intent = new Intent(this, MainWindow.class);
            intent.putExtra("name", getUsername());
            startActivity(intent);
        }
    }


    private String getUsername(){
        return ((EditText)findViewById(R.id.usernameinput)).getText().toString();
    }

    private String getPassword(){
        return ((EditText)findViewById(R.id.passwordinput)).getText().toString();
    }

    public String getPhoneNumber() {
        return "";
    }

    public void toast(CharSequence string){
            Toast toast = Toast.makeText(getApplicationContext(), string, Toast.LENGTH_SHORT);
            toast.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainsettingsmenu, menu);
        return true;
    }

    public void clickedItem1(MenuItem item) {
        toast("Cliiiiick");
    }

}
