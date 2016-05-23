package com.gibbr_madlibs;

import android.app.Instrumentation;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GibbrReadActivity extends AppCompatActivity {

    private List<String> mList;
    private String mResultText;
    private String mStoryText;
    private TextView mWordsCount;
    private TextView mPlaceHolder;
    private EditText mPlaceHolderText;
    private Button mOkButton;
    private int MAX = 0;
    private int mCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gibbr_fill_story_dialog);
        mWordsCount = (TextView)findViewById(R.id.words_count);
        mPlaceHolder = (TextView)findViewById(R.id.place_holder);
        mPlaceHolderText = (EditText) findViewById(R.id.place_holder_text);
        mOkButton = (Button)findViewById(R.id.ok);
        mOkButton.setOnClickListener(mButtonClick);
        mList = new ArrayList<String>();
        readFromFile();
    }

    private void readFromFile(){
        try {
            final InputStream mInputStream = getAssets().open("gibbr_fill");
            if(mInputStream != null) {
                final BufferedReader mBufferedReader = new BufferedReader(new InputStreamReader(mInputStream));
                StringBuilder mStringBuilder = new StringBuilder();
                String mActText = mBufferedReader.readLine();
                if (!mActText.isEmpty()) {
                    mStringBuilder.append(mActText);
                }
                mInputStream.close();
                mResultText = mStringBuilder.toString();
                findPlaceHolder(mResultText);
                if(!mList.isEmpty()) {
                    findAndReplaceHolder(mList.size(), mCounter);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void findPlaceHolder(String mStory){
        final Matcher m = Pattern.compile("\\<(.*?)\\>").matcher(mStory);
        while (m.find()) {
            mList.add(m.group());
        }
        MAX = mList.size();
    }

    private void findAndReplaceHolder(int size, int pos){
        if(size != pos) {
            String mType =  mList.get(pos).replaceAll("[\\<\\>]","");
            mWordsCount.setText( mList.size()  + " " + getResources().getString(R.string.left_words));
            mPlaceHolder.setText(getResources().getString(R.string.type) + " " + mType);
            mPlaceHolderText.setHint(mType);
        } else {
            mWordsCount.setText( mList.size() + " " + getResources().getString(R.string.left_words));
            GibbrFillStoryActivity.mFillStory = mStoryText;
            Intent mIntent = new Intent(getApplicationContext(), GibbrFillStoryActivity.class);
            startActivity(mIntent);
        }

    }

    View.OnClickListener mButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final String  mPlaceHolderChar = mPlaceHolderText.getText().toString();
            if(!mPlaceHolderChar.isEmpty()) {
                mPlaceHolderText.setText("");
                if(mList.size() == MAX) {
                    mStoryText = mResultText.replaceFirst(mList.get(mCounter), "<b>" + mPlaceHolderChar + "</b> ");
                } else {
                    mStoryText = mStoryText.replaceFirst(mList.get(mCounter), "<b>" + mPlaceHolderChar + "</b> ");
                }
                mList.remove(mCounter);
                findAndReplaceHolder(mList.size(), mCounter);
            } else {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_text), Toast.LENGTH_SHORT).show();
            }
        }
    };
}
