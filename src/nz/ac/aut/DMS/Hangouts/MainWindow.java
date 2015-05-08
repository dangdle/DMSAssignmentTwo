package nz.ac.aut.DMS.Hangouts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import nz.ac.aut.DMS.Hangouts.ServerStuff.Server;

import java.util.List;

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
        List<User> hangoutUsers = Server.getHangoutUsers();
    }
}
