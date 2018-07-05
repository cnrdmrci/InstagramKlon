package com.abisininstagram.instagramcc.instagramcc.Home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.abisininstagram.instagramcc.instagramcc.R;
import com.abisininstagram.instagramcc.instagramcc.models.Message;

import com.abisininstagram.instagramcc.instagramcc.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Camokan on 15.12.2017.
 */



public class Messaging extends AppCompatActivity {
    private static final String TAG = "MessagingFragment";

    private FirebaseDatabase db;
    private DatabaseReference dbRef;
    private DatabaseReference dbRef2;
    private DatabaseReference dbRef3;
    private FirebaseUser fUser;
    private ArrayList<Message> chatLists = new ArrayList<>();
    private CustomMessageAdapter customAdapter;
    private String subject;
    private ListView listView;
    private FloatingActionButton floatingActionButton;
    private EditText inputChat;
    private TextView kullanici_adi;

    static String my_k_adim ;
    static String other_user_idsi;

    public void set_my_k_adi(String my_k_adi){
        my_k_adim = my_k_adi;
        Log.d("kullanici_adi_tamam:",my_k_adi);
    }
    public void set_other_user_id(String other_user_id){
        other_user_idsi = other_user_id;
        Log.d("kullanici_adisi_tamam:",other_user_id);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);

        listView = (ListView)findViewById(R.id.chatListView);
        inputChat = (EditText)findViewById(R.id.inputChat);
        floatingActionButton = (FloatingActionButton)findViewById(R.id.fab);
        kullanici_adi = (TextView) findViewById(R.id.mesaj_kullanici_adi);

        db = FirebaseDatabase.getInstance();
        fUser = FirebaseAuth.getInstance().getCurrentUser();

        customAdapter = new CustomMessageAdapter(getApplicationContext(),chatLists,fUser);
        listView.setAdapter(customAdapter);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            subject = bundle.getString("subject");
            //dbRef = db.getReference("ChatSubjects/"+subject+"/mesaj");
            //setTitle(subject);
            Log.d("kullanici adim: ",subject);
            kullanici_adi.setText(subject);
        }
        dbRef3 = db.getReference("users");
        dbRef3.addValueEventListener(new ValueEventListener() {
            String my_kadi,other_uid;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    User usr = ds.getValue(User.class);
                    if(usr.getUser_id().equals(fUser.getUid())){
                        set_my_k_adi(usr.getUsername());
                        my_kadi=usr.getUsername();
                        Log.d("deneme12",my_kadi);
                    }
                }

                dbRef = db.getReference("messages");
                dbRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        chatLists.clear();
                        for(DataSnapshot ds : dataSnapshot.getChildren()){
                            Message msj = ds.getValue(Message.class);
                            Log.d("bakalim",String.valueOf(ds.getValue()));
                            Log.d("kullaniciA",msj.getMessageUser());
                            Log.d("kullaniciB",msj.getMessageUser2());
                            Log.d("kullanici123",msj.getMessageUserName());
                            if(msj.getMessageUserName().equals(kullanici_adi.getText())&& msj.getMessageUserName2().equals(my_kadi)){
                                chatLists.add(msj);
                            }else if(msj.getMessageUserName2().equals(kullanici_adi.getText()) && msj.getMessageUserName().equals(my_kadi)){
                                chatLists.add(msj);
                            }
                            //String temp =  String.valueOf(kullanici_adi.getText());
                            //Log.d("kullanici_adi:",temp);
                            //Log.d("VALUE",ds.getValue(Message.class).getMesajText());
                        }
                        customAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });






        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    final String k_adi =  String.valueOf(kullanici_adi.getText());
                    final String my_k_adi;
                    Log.d("kullanici_adi:",k_adi);

                    dbRef2 = db.getReference("users");

                dbRef2.addValueEventListener(new ValueEventListener() {
                    String my_kadi,other_uid;
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for(DataSnapshot ds : dataSnapshot.getChildren()){
                            User usr = ds.getValue(User.class);
                            if(usr.getUser_id().equals(fUser.getUid())){
                                set_my_k_adi(usr.getUsername());
                                my_kadi=usr.getUsername();
                                Log.d("buda tamam",usr.getUsername());
                            }
                            if(usr.getUsername().equals(k_adi)){
                                set_other_user_id(usr.getUser_id());
                                Log.d("buda tamam",usr.getUser_id());
                                other_uid = usr.getUser_id();
                            }
                            String temp =  String.valueOf(kullanici_adi.getText());
                            Log.d("kullanici_adi:",usr.getUser_id());
                            //Log.d("VALUE",ds.getValue(Message.class).getMesajText());
                        }
                        long msTime = System.currentTimeMillis();
                        Date curDateTime = new Date(msTime);
                        SimpleDateFormat formatter = new SimpleDateFormat("dd'/'MM'/'y hh:mm");
                        String dateTime = formatter.format(curDateTime);
                        Message message = new Message(inputChat.getText().toString(),fUser.getUid(),dateTime,other_uid,my_kadi,k_adi);
                        dbRef.push().setValue(message);
                        inputChat.setText("");
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });

    }

}
