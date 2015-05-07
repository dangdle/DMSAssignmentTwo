package nz.ac.aut.DMS.Hangouts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Ashellan Edmonds on 7/05/15.
 */
public class MainWindow extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainwindow);

    }
    public void listview(View view) {
        startActivity(new Intent(this, friendsActivity.class));

    }
}
