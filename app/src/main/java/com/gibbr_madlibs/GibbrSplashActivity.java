package com.gibbr_madlibs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GibbrSplashActivity extends AppCompatActivity {

    private Button mStartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gibbr_splash);
        mStartButton = (Button) findViewById(R.id.start_button);
        mStartButton.setOnClickListener(mStartButtonClickListener);
    }

    View.OnClickListener mStartButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent mIntent = new Intent(getApplicationContext(), GibbrReadActivity.class);
            startActivity(mIntent);
        }
    };
}
