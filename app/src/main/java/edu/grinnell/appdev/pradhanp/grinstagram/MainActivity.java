package edu.grinnell.appdev.pradhanp.grinstagram;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;


public class MainActivity extends Activity {

    ImageView mAddButton;
    protected List<ParseObject> mLinks;
    protected RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAddButton = (ImageView)findViewById(R.id.add);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddLinkActivity.class);
                startActivity(intent);
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.cardList);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);



    }

    @Override
    protected void onResume() {
        super.onResume();

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(ParseConstants.CLASS_IMG_LINK);
        query.addDescendingOrder(ParseConstants.CREATED_AT);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    // We found new Messages
                    mLinks = list;

                    if (mRecyclerView.getAdapter() == null) {
                        LinkAdapter adapter = new LinkAdapter(mRecyclerView.getContext(), mLinks);

                        mRecyclerView.setAdapter(adapter);
                    }
                    else {
                        // refill adapter
                        ((LinkAdapter)mRecyclerView.getAdapter()).refill(mLinks);
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
