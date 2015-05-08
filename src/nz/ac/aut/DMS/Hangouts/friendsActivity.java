package nz.ac.aut.DMS.Hangouts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import nz.ac.aut.DMS.Hangouts.ServerStuff.Server;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ashellan Edmonds on 7/05/15.
 */
public class friendsActivity extends Activity {
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friendlist);

        username = getIntent().getExtras().getString("name");
        List<User> friendsList = Server.getFriendsList(username);
        String[] friendsValues = new String[friendsList.size()];
        for (int i = 0; i <friendsList.size(); i++) {
            friendsValues[i] = friendsList.get(i).getUsername();
        }

        ListView viewById = (ListView) findViewById(R.id.listview);
        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < friendsValues.length; ++i) {
            list.add(friendsValues[i]);
        }
        ArrayAdapter<String> string = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        final StableArrayAdapter adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        viewById.setAdapter(adapter);
        viewById.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(parent.getItemAtPosition(position));
                Intent intent = new Intent(friendsActivity.this, MessageActivity.class);
                intent.putExtra("name", username);
                intent.putExtra("Name", parent.getItemAtPosition(position).toString());
                startActivity(intent);
            }
        });
    }

}
