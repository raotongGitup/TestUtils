package com.example.testutils.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testutils.R;
import com.example.testutils.utils.ContentEditText;

public class WeiXinActivity extends AppCompatActivity {

    private ContentEditText contentEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wei_xin);
        contentEditText = ((ContentEditText) findViewById(R.id.content_edit_text));
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contentEditText.addAtSpan("@","我是大佬？");
            }
        });
    }
}
