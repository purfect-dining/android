package com.pud.ui.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.hololo.tutorial.library.Step;
import com.hololo.tutorial.library.TutorialActivity;
import com.pud.R;

public class IntroActivity extends TutorialActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addFragment(new Step.Builder().setTitle("This is 1")
                .setContent("This is 1")
                .setBackgroundColor(Color.parseColor("#FF0957")) // int background color
                .setDrawable(R.mipmap.ic_launcher) // int top drawable
                .setSummary("This is summary")
                .build());
        addFragment(new Step.Builder().setTitle("This is 2")
                .setContent("This is 2")
                .setBackgroundColor(Color.RED) // int background color
                .setSummary("This is summary")
                .build());

        addFragment(new Step.Builder().setTitle("This is 3")
                .setContent("This is 3")
                .setBackgroundColor(Color.GREEN) // int background color
                .setDrawable(R.mipmap.ic_launcher) // int top drawable
                .setSummary("This is summary")
                .build());
    }

    @Override
    public void currentFragmentPosition(int position) {

    }

    @Override
    public void finishTutorial() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}