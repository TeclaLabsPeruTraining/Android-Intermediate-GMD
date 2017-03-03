package com.gmd.lessons.fcm.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gmd.lessons.fcm.R;
import com.gmd.lessons.fcm.model.MessageChat;

import java.util.List;

/**
 * Created by emedinaa on 03/03/17.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private static final String TAG = "MessageAdapter";
    private List<MessageChat> messageChats;
    private String DEFAULTCOLOR="#585858";

    public MessageAdapter(List<MessageChat> messageChats) {
        this.messageChats = messageChats;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView tviMessage;

        public ViewHolder(View v) {
            super(v);
            tviMessage = (TextView) v.findViewById(R.id.tviMessage);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_message, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MessageChat messageChat = messageChats.get(position);
        if (messageChat != null) {
            String message= (TextUtils.isEmpty(messageChat.getValue())?(""):(messageChat.getValue()));
           holder.tviMessage.setText(message);
        }
    }

    @Override
    public int getItemCount() {
        return messageChats.size();
    }

    public void addMessage(MessageChat messageChat){
        Log.v(TAG, "addMessage "+messageChat);
        this.messageChats.add(messageChat);
        //int position= messageChats.indexOf(messageChat);
        //this.notifyItemChanged(position);
        this.notifyDataSetChanged();
    }
}
