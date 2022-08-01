package com.mapchat.chat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;
import com.mapchat.R;
import com.pubnub.api.PNConfiguration;
import com.pubnub.api.PubNub;
import com.pubnub.api.callbacks.PNCallback;
import com.pubnub.api.callbacks.SubscribeCallback;
import com.pubnub.api.models.consumer.PNPublishResult;
import com.pubnub.api.models.consumer.PNStatus;
import com.pubnub.api.models.consumer.objects_api.channel.PNChannelMetadataResult;
import com.pubnub.api.models.consumer.objects_api.membership.PNMembershipResult;
import com.pubnub.api.models.consumer.objects_api.uuid.PNUUIDMetadataResult;
import com.pubnub.api.models.consumer.pubsub.PNMessageResult;
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult;
import com.pubnub.api.models.consumer.pubsub.PNSignalResult;
import com.pubnub.api.models.consumer.pubsub.files.PNFileEventResult;
import com.pubnub.api.models.consumer.pubsub.message_actions.PNMessageActionResult;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Implements a chat function within the app
 * Uses pubnub functionality
 *
 */
public class chatMain extends AppCompatActivity {

    private Button sendMessageButt;
    private PubNub pubNub;
    private EditText sendMessage;
    private String username;
    private List<message> messageList;
    private RecyclerView newRecycler;
    private recyclerviewadapter rAdapter;
    private String channelName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        initPubNub();

        messageList = new ArrayList<>();

        newRecycler = findViewById(R.id.receiveMessage);
        newRecycler.setLayoutManager(new LinearLayoutManager(this));

        //getUserName();

        Random random = new Random();
        int num = random.nextInt(10000);
        username = "" + num;

        sendMessageButt = (Button) findViewById(R.id.sendMessButt);
        sendMessageButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendMessage = (EditText) findViewById(R.id.sendMessages);

                if (!sendMessage.getText().toString().isEmpty()) {
                    String message = sendMessage.getText().toString();

                    try {
                        publishMessage(message, username);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    sendMessage.setText(" ");
                }
            }
        });

    }

    public void initPubNub() {

        PNConfiguration pnConfiguration = new PNConfiguration();
        pnConfiguration.setPublishKey("pub-c-754f664d-7af8-4562-a190-8c5190edaa18");
        pnConfiguration.setSubscribeKey("sub-c-5a781e14-9203-11eb-b45a-4ab5cd6a50d7");
        pnConfiguration.setSecure(true);

        pubNub = new PubNub(pnConfiguration);

        pubNub.addListener(new SubscribeCallback() {
            @Override
            public void status(PubNub pubnub, PNStatus status) {
            }

            @Override
            public void message(PubNub pubnub, PNMessageResult message) {

                final String msg = message.getMessage().toString().replace("\"", "");

                JsonObject rawUserMeta = (JsonObject) message.getUserMetadata();
                JsonObject elementRawUser = (JsonObject) rawUserMeta.get("nameValuePairs");
                final String user = elementRawUser.get("username").toString().replace("\"", "");
                final long time = message.getTimetoken();

                System.out.println(time);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            //Updates the chat view to show the message. Needs to be changed to a recyclerview.
                            message m = new message(msg, time, user);

                            messageList.add(m);

                            rAdapter = new recyclerviewadapter(getApplicationContext(), messageList);
                            rAdapter.notifyDataSetChanged();
                            newRecycler.setAdapter(rAdapter);
                            newRecycler.scrollToPosition(messageList.size() - 1);

                        } catch (Exception e) {
                            System.out.println("Error");
                            e.printStackTrace();
                        }
                    }
                });
            }

            @Override
            public void signal(@NotNull PubNub pubnub, @NotNull PNSignalResult pnSignalResult) {
            }

            @Override
            public void uuid(@NotNull PubNub pubnub, @NotNull PNUUIDMetadataResult pnUUIDMetadataResult) {
            }

            @Override
            public void channel(@NotNull PubNub pubnub, @NotNull PNChannelMetadataResult pnChannelMetadataResult) {
            }

            @Override
            public void membership(@NotNull PubNub pubnub, @NotNull PNMembershipResult pnMembershipResult) {
            }

            @Override
            public void messageAction(@NotNull PubNub pubnub, @NotNull PNMessageActionResult pnMessageActionResult) {
            }

            @Override
            public void file(@NotNull PubNub pubnub, @NotNull PNFileEventResult pnFileEventResult) {

            }

            @Override
            public void presence(PubNub pubnub, PNPresenceEventResult presence) {
            }
        });

        //Needs to be changed. Temporary whilst testing.
        channelName = (String) getIntent().getExtras().getString(friendList.CHANNEL) + " - 1";

        pubNub.subscribe()
                .channels(Arrays.asList(channelName))
                .execute();
    }

    public void publishMessage(String message, String username) throws JSONException {
        JSONObject userDetails = new JSONObject();
        userDetails.put("username", username);

        pubNub.publish()
                .message(message)
                .channel(channelName)
                .shouldStore(true)
                .meta(userDetails)
                .async(new PNCallback<PNPublishResult>() {
                    @Override
                    public void onResponse(PNPublishResult result, PNStatus status) {
                        if (status.isError()) {
                            System.out.println("pub status code: " + status.getStatusCode());
                        }
                    }
                });
    }

    /**
     public String getUserName() {
     String name = "";

     Cursor cursor = database.getProfile();
     if (cursor.moveToFirst()){
     int col = cursor.getColumnIndex("ProfileName");
     System.out.println(cursor.getString(col));
     }
     return name;
     }
     **/
}
