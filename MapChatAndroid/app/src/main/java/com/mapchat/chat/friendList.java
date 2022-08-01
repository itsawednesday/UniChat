package com.mapchat.chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mapchat.R;

/**
 * TEMPORARY ACTIVITY/CLASS. WILL BE REPLACED WITH CONTACTS.
 * PURELY FOR TESTING PURPOSES.
 * For testing purposes: define the five buttons.
 */

public class friendList extends AppCompatActivity {

    //TEMPORARY ACTIVITY/CLASS. WILL BE REPLACED WITH CONTACTS.
    //PURELY FOR TESTING PURPOSES.

    //Creates a string to be loaded into the next class.
    public static final String CHANNEL = "channel";
    public static final String USERNAME = "username";
    //For testing purposes: define the five buttons.
    //TODO: Dynamically add buttons when you add a friend.
    public TextView friendOne;
    public TextView friendTwo;
    public TextView friendThree;
    public TextView friendFour;
    public TextView friendFive;
    public TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        //Link the previously defined buttons to the buttons in the xml.
        friendOne = findViewById(R.id.friendOne);
        friendTwo = findViewById(R.id.friendTwo);
        friendThree = findViewById(R.id.friendThree);
        friendFour = findViewById(R.id.friendFour);
        friendFive = findViewById(R.id.friendFive);

        //For testing purposes: set on click listeners for each button to ensure the correct names are being loaded and the correct activity.
        //TODO: Dynamically add on click listeners to each new friend added, alongside adding buttons for each friend.
        friendOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create new intent to switch from this class to the next class.
                Intent loadChat = new Intent(friendList.this, chatMain.class);

                //Establish the button within this view to be able to grab the text value from the button and convert to string.
                TextView friendOneButton = (TextView) v;
                String friendName = friendOneButton.getText().toString();

                //TODO: Retrieve channel name for each friend dynamically rather than hard coded.
                //Add the string variables to the intent and start the next class with it as a parameter.
                loadChat.putExtra(CHANNEL, friendName);

                startActivity(loadChat);
            }
        });

        friendTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loadChat = new Intent(friendList.this, chatMain.class);

                TextView friendTwoButton = (TextView) v;
                String friendName = friendTwoButton.getText().toString();

                loadChat.putExtra(CHANNEL, friendName);

                startActivity(loadChat);
            }
        });

        friendThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loadChat = new Intent(friendList.this, chatMain.class);

                TextView friendThreeButton = (TextView) v;
                String friendName = friendThreeButton.getText().toString();

                loadChat.putExtra(CHANNEL, friendName);
                startActivity(loadChat);
            }
        });

        friendFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loadChat = new Intent(friendList.this, chatMain.class);

                TextView friendFourButton = (TextView) v;
                String friendName = friendFourButton.getText().toString();

                loadChat.putExtra(CHANNEL, friendName);
                startActivity(loadChat);
            }
        });

        friendFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loadChat = new Intent(friendList.this, chatMain.class);

                TextView friendFiveButton = (TextView) v;
                String friendName = friendFiveButton.getText().toString();

                loadChat.putExtra(CHANNEL, friendName);
                startActivity(loadChat);
            }
        });
    }
}
