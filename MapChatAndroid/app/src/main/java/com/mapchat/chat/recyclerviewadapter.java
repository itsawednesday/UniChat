package com.mapchat.chat;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mapchat.R;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Adapter for chatMain method
 * extends RecyclerView
 * populates the data
 */

public class recyclerviewadapter extends RecyclerView.Adapter {

    private static final int message_sent_type = 1;
    private static final int message_received_type = 2;

    private List<message> MessageList;
    private Context con;


    public recyclerviewadapter(Context context, List<message> MessageList) {

        //Populate the private variable with the messagelist provided in the chat class.
        this.MessageList = MessageList;
        con = context;
    }

    //A necessary function for recyclerview, keeps track of the message list size.
    @Override
    public int getItemCount() {
        return MessageList.size();
    }

    //
    //public int getItemViewType(int position) {
    //message message = (message) MessageList.get(position);

    //if (message.getUser().equals()){
    //    return message_sent_type;
    //} else {
    //    return message_received_type;
    //}
    //}

    //Creates an itemview for the view holder to use as a format for the username and message.
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        //if (viewType == message_sent_type) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sendmessageformat, parent, false);
        return new SendMessageHolder(view);
        //} else if (viewType == message_received_type){
        //  view = LayoutInflater.from(parent.getContext()).inflate(R.layout.receivemessageformat, parent, false);
        //  return new ReceiveMessageHolder(view);
        //}
        //return null;
    }


    //Sets the username and message in the view holder to the value of the supplied username and message and formats it.
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final message m = MessageList.get(position);
        ((SendMessageHolder) holder).bind(m);
        //switch (holder.getItemViewType()) {
        //case message_sent_type:
        //  ((SendMessageHolder) holder).bind(m);
        //  break;

        //case message_received_type:
        // ((ReceiveMessageHolder) holder).bind(m);
        //}
    }

    public String time(long time) {
        Date date = new Date(time * 1000);
        Format format = new SimpleDateFormat("HH:mm:ss");
        return format.format(date);
    }

    public String date(long time) {
        Date date = new Date(time * 1000);
        Format format = new SimpleDateFormat("MMM d");
        return format.format(date);
    }

    private class ReceiveMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText, nameText, dateText;

        ReceiveMessageHolder(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.receiveMessage);
            timeText = itemView.findViewById(R.id.receiveTimestamp);
            nameText = itemView.findViewById(R.id.receiveUsername);
        }

        void bind(message message) {
            messageText.setText(message.getMessage());
            timeText.setText(DateUtils.formatDateTime(con, message.getCreatedAt(), 0));
            //nameText.setText();
        }
    }

    private class SendMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText, nameText, dateText;

        SendMessageHolder(View itemView) {
            super(itemView);

            messageText = itemView.findViewById(R.id.sendMessage);
            timeText = itemView.findViewById(R.id.sendTimestamp);
            nameText = itemView.findViewById(R.id.sendUsername);
            dateText = itemView.findViewById(R.id.sendMessageDate);
        }

        void bind(message message) {
            messageText.setText(message.getMessage());
            timeText.setText(time(message.getCreatedAt() / 10000000));
            nameText.setText(message.getUser());
            dateText.setText(date(message.getCreatedAt() / 10000000));
        }
    }
}
