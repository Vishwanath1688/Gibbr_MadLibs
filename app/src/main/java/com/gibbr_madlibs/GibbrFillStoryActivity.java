package com.gibbr_madlibs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class GibbrFillStoryActivity extends AppCompatActivity {

    public static String mFillStory;
    private TextView mFillStoryText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gibbr_fill_story);
        mFillStoryText = (TextView)findViewById(R.id.fill_story_text);
        if(!mFillStory.isEmpty()) {
            mFillStoryText.setText(Html.fromHtml(mFillStory));
        }
    }

    @Override
    public void onBackPressed() {
        Intent mIntent = new Intent(getApplicationContext(), GibbrReadActivity.class);
        mIntent.setFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mIntent);
    }
}
