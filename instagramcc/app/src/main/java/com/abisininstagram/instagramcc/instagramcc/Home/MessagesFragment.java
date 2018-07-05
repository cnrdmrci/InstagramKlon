package com.abisininstagram.instagramcc.instagramcc.Home;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.util.Log;
import android.widget.TextView;

import com.abisininstagram.instagramcc.instagramcc.Search.SearchActivity;
import com.abisininstagram.instagramcc.instagramcc.models.Message;
import com.abisininstagram.instagramcc.instagramcc.models.User;
import com.abisininstagram.instagramcc.instagramcc.models.UserAccountSettings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import com.abisininstagram.instagramcc.instagramcc.R;

/**
 * Created by Camokan on 29.10.2017.
 */

public class MessagesFragment extends Fragment {
    private static final String TAG = "MessagesFragment";

    private ListView listView;
    private FirebaseAuth fAuth;
    private ArrayList<String> subjectLists = new ArrayList<>();
    private FirebaseDatabase db;
    private DatabaseReference dbRef;
    private ArrayAdapter<String> adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messages, container, false);



        fAuth = FirebaseAuth.getInstance();

       listView = (ListView)view.findViewById(R.id.listViewSubjects);

        db = FirebaseDatabase.getInstance();
        dbRef = db.getReference("messages");

        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1,subjectLists);
        listView.setAdapter(adapter);

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                subjectLists.clear();
                int sayac=0;
                int hata =0;
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Message msj = ds.getValue(Message.class);
                    if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(msj.getMessageUser())){
                        for(int i=0;i<subjectLists.size();i++){
                            if(subjectLists.get(i).equals(msj.getMessageUserName2())){
                                hata = 1;
                                Log.d("yazdironce",String.valueOf(hata));
                                Log.d("liste sayisi2: ",subjectLists.get(i));
                                Log.d("liste sayisi2: ",msj.getMessageUserName2());
                            }
                        }
                        Log.d("yazdir",String.valueOf(hata));
                        if(hata != 1) {
                            subjectLists.add(String.valueOf(msj.getMessageUserName2()));
                        }
                        hata = 0;
                    }else if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(msj.getMessageUser2())){
                        for(int i=0;i<subjectLists.size();i++){
                            Log.d("sifirinci",subjectLists.get(0));
                            if(subjectLists.get(i).equals(msj.getMessageUserName())){
                                hata = 1;
                            }
                        }
                        Log.d("yazdir2",String.valueOf(hata));
                        if(hata != 1) {
                            subjectLists.add(String.valueOf(msj.getMessageUserName()));
                        }
                        hata = 0;
                    }
                    Log.d("LOGVALUE2",FirebaseAuth.getInstance().getCurrentUser().getUid());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                //Toast.makeText(getApplicationContext(),""+databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),Messaging.class);
                intent.putExtra("subject",subjectLists.get(position));
                startActivity(intent);
            }
        });


        return view;


    }

}