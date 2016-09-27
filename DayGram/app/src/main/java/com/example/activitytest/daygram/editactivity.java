package com.example.activitytest.daygram;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Calendar;

/**
 * Created by apple on 2016/9/21.
 */
public class editactivity extends Activity implements View.OnClickListener {
    private TextView editTitle;
    private Button button_done;
    private EditText edit_text;
    Diary tddiary;
    String[] monthS = {"JANUARY ", "FENRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.edit);
        Intent intent = getIntent();
        tddiary = (Diary) intent.getSerializableExtra("today");
        int Year = intent.getIntExtra("Year", -1);
        String month = monthS[Integer.parseInt(tddiary.getmonth()) - 1];

        String edit_title = tddiary.getdaycount() + ". /" + month + " " + tddiary.getdayNum() + " / " + String.valueOf(Year);
        editTitle = (TextView) findViewById(R.id.edit_title);
        editTitle.setText(edit_title);

        button_done = (Button) findViewById(R.id.edit_done);
        button_done.setOnClickListener(this);

        edit_text = (EditText) findViewById(R.id.edit_text);
        edit_text.setText(tddiary.getdiaryText());

        Button button_clock = (Button) findViewById(R.id.edit_clock);
        button_clock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertText(edit_text, gettime());
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_done:
                String inputText = edit_text.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("new_diary", inputText);
                setResult(RESULT_OK, intent);
                finish();
        }
    }

    private String gettime() {
        String timenow;
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        timenow = String.valueOf(hour) + ":" + String.valueOf(minute) + " ";
        return timenow;
    }

    private int getEditTextCursorIndex(EditText mEditText) {
        return mEditText.getSelectionStart();
    }

    private void insertText(EditText mEditText, String mText) {
        mEditText.getText().insert(getEditTextCursorIndex(mEditText), mText);
    }
}
