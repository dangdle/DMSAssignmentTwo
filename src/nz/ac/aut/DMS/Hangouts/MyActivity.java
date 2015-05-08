package nz.ac.aut.DMS.Hangouts;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import nz.ac.aut.DMS.Hangouts.ServerStuff.Server;
import nz.ac.aut.DMS.Hangouts.ServerStuff.SignIn;
import nz.ac.aut.DMS.Hangouts.ServerStuff.SignUp;

public class MyActivity extends Activity {
    private Server server;
    private static LocationManager lm;

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
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 1000, new MyLocationListener(this));
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
        return ((EditText)findViewById(R.id.username_input)).getText().toString();
    }

    private String getPassword(){
        return ((EditText)findViewById(R.id.passwordinput)).getText().toString();
    }

    public String getPhoneNumber() {
        return ((EditText)findViewById(R.id.phoneNumber)).getText().toString();
    }

    public void toast(CharSequence string){
            Toast toast = Toast.makeText(getApplicationContext(), string, Toast.LENGTH_SHORT);
            toast.show();
    }

}
