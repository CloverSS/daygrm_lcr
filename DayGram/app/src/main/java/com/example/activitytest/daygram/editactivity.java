package com.example.activitytest.daygram;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by apple on 2016/9/21.
 */
public class editactivity  extends Activity implements View.OnClickListener{
    private TextView editTitle;
    private Button button_done;
    private EditText edit_text;
    Diary tddiary;
    String[] monthS={"JANUARY ","FENRUARY","MARCH","APRIL","MAY","JUNE","JULY","AUGUST","SEPTEMBER","OCTOBER","NOVEMBER","DECEMBER"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.edit);
        tddiary = (Diary) getIntent().getSerializableExtra("today");
        String month=monthS[Integer.parseInt( tddiary.getmonth())-1];

        String edit_title = tddiary.getdaycount() + ". /" +month + " "+tddiary.getdayNum() + " / 2016";
        editTitle = (TextView) findViewById(R.id.edit_title);
        editTitle.setText(edit_title);

        button_done = (Button) findViewById(R.id.edit_done);
        button_done.setOnClickListener(this);

        edit_text = (EditText) findViewById(R.id.edit_text);
        edit_text.setText(tddiary.getdiaryText());

    }
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.edit_done:
                    String inputText = edit_text.getText().toString();
                    //tddiary.setdiaryText(inputText);
                    //tddiary.setType(1);
                    Intent intent=new Intent();
                    /*Bundle mBundle = new Bundle();
                    mBundle.putSerializable("new_diary",tddiary);
                    intent.putExtras(mBundle);*/
                    intent.putExtra("new_diary", inputText);
                    setResult(RESULT_OK, intent);
                    finish();
            }
        }
}
