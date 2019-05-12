package com.pud.ui.main;

import android.content.Intent;
import android.os.Bundle;

import com.hololo.tutorial.library.Step;
import com.hololo.tutorial.library.TutorialActivity;
import com.pud.R;

public class IntroActivity extends TutorialActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addFragment(new Step.Builder().setTitle("Intro")
                .setContent("Currently, Purdue doesn’t have a system for students, faculty, and the general public to " +
                        "rate their experience at dining courts, or to make informed decisions about where they wish to dine " +
                        "on campus based on real-time statistics of other users’ dining experiences." +
                        "\n\n\n" +
                        "PUrfect Dining utilizes a 3-button rating system to enable users, while exiting dining courts, to rate their dining experience on a scale of 1-3: Poor, Satisfactory, or Excellent, respectively." +
                        "\n\n\n" +
                        "The home page displays real time information about which dining courts are open, and which ones are closed. The ratings that you see beside them represent the average rating of the dining court for the current meal time" +
                        "\n\n\n" +
                        "Clicking a dining court's name on the home page will lead you to the individual dining court's page, which displays information about the respective dining court. This includes the average rating and the menu for the current meal time, the comments by users on the dining courts, and most importantly, the graph representing all ratings for the dining court for the current meal time.\n" +
                        "Commenting is available for users who have created an account. Additionally, they also have the option of reporting a big/providing feedback using the feedback form that can be accessed on the settings page.\n" +
                        "Thank you for your patience. Click the button below to be redirected to the home page!")
                .setBackgroundColor(getResources().getColor(R.color.backgroundDark))
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