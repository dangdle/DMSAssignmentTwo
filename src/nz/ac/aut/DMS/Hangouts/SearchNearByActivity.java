package nz.ac.aut.DMS.Hangouts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import nz.ac.aut.DMS.Hangouts.ServerStuff.Server;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Ben on 8/05/15.
 */
public class SearchNearByActivity extends Activity{

    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nearby);

        username = getIntent().getExtras().getString("name");
        List<User> use = Server.getHangoutUsers();
        List<User> users = new LinkedList<>();
        for(User user : use){
            if(!user.getUsername().equals(username)){
                users.add(user);
            }
        }
        Log.d("my", username);
        String[] friendsValues = new String[users.size()];
        for (int i = 0; i <users.size(); i++) {
            friendsValues[i] = users.get(i).getUsername();
        }

        ListView viewById = (ListView) findViewById(R.id.listView2);
        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < friendsValues.length; ++i) {
            list.add(friendsValues[i]);
        }
        final StableArrayAdapter adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        viewById.setAdapter(adapter);
        viewById.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchNearByActivity.this, MessageActivity.class);
                intent.putExtra("name", username);
                intent.putExtra("Name", parent.getItemAtPosition(position).toString());
                startActivity(intent);
            }
        });
    }
}
