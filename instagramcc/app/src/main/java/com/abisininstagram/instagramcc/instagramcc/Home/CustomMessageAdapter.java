package com.abisininstagram.instagramcc.instagramcc.Home;

/**
 * Created by Camokan on 15.12.2017.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.abisininstagram.instagramcc.instagramcc.R;
import com.abisininstagram.instagramcc.instagramcc.models.Message;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class CustomMessageAdapter extends ArrayAdapter<Message> {

    private FirebaseUser firebaseUser;

    public CustomMessageAdapter(Context context, ArrayList<Message> chatList,FirebaseUser firebaseUser) {
        super(context, 0, chatList);
        this.firebaseUser = firebaseUser;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Message message = getItem(position);
        if (firebaseUser.getUid().equals(message.getMessageUser())){

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.message_layout_right_item, parent, false);

            TextView txtUser = (TextView) convertView.findViewById(R.id.txtUserRight);
            TextView txtMessage = (TextView) convertView.findViewById(R.id.txtMessageRight);
            TextView txtTime = (TextView) convertView.findViewById(R.id.txtTimeRight);

            txtUser.setText(message.getMessageUserName());
            txtMessage.setText(message.getMessageText());
            txtTime.setText(String.valueOf(message.getMessageTime()));

        }else{

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.message_layout_left_item, parent, false);

            TextView txtUser = (TextView) convertView.findViewById(R.id.txtUserLeft);
            TextView txtMessage = (TextView) convertView.findViewById(R.id.txtMessageLeft);
            TextView txtTime = (TextView) convertView.findViewById(R.id.txtTimeLeft);

            txtUser.setText(message.getMessageUserName());
            txtMessage.setText(message.getMessageText());
            txtTime.setText(String.valueOf(message.getMessageTime()));

        }

        return convertView;
    }
}
