package edu.grinnell.appdev.pradhanp.grinstagram;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

/**
 * Created by prabir on 5/4/15.
 */
public class AddLinkActivity extends Activity {

    ImageView mAddButton;
    EditText mName;
    EditText mLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity);

        mAddButton = (ImageView) findViewById(R.id.addSend_IV);
        mName = (EditText) findViewById(R.id.name_editText);
        mLink = (EditText) findViewById(R.id.link_editText);

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mLink.getText().toString().equals("")) {
                    ParseObject object = new ParseObject(ParseConstants.CLASS_IMG_LINK);
                    object.put(ParseConstants.NAME, mName.getText().toString());
                    object.put(ParseConstants.LINK, mLink.getText().toString());
                    object.put(ParseConstants.LIKE, false);
                    object.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Toast.makeText(AddLinkActivity.this, "Link Shared!", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                // error
                                AlertDialog.Builder builder = new AlertDialog.Builder(AddLinkActivity.this);
                                builder.setTitle("Oh, no!")
                                        .setMessage(e.getMessage())
                                        .setPositiveButton(android.R.string.ok, null);
                                AlertDialog dialog = builder.create();
                                dialog.show();
                            }
                        }
                    });
                }
            }
        });

    }

}
