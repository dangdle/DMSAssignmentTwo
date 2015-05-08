package nz.ac.aut.DMS.Hangouts;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Ben on 8/05/15.
 */
public class SearchNearByActivity extends Activity{

//    private GoogleApiClient mGoogleApiClient;
//
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nearby);
//        mGoogleApiClient = new GoogleApiClient
//                .Builder(this)
//                .addApi(Places.GEO_DATA_API)
//                .addApi(Places.PLACE_DETECTION_API)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .build();
    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        mGoogleApiClient.connect();
//    }
//
//    @Override
//    protected void onStop() {
//        mGoogleApiClient.disconnect();
//        super.onStop();
//    }
}
