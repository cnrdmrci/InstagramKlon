package com.abisininstagram.instagramcc.instagramcc.Likes;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.abisininstagram.instagramcc.instagramcc.Utils.MainfeedListAdapter;
import com.abisininstagram.instagramcc.instagramcc.Utils.SectionsStatePagerAdapter;
import com.abisininstagram.instagramcc.instagramcc.models.Comment;
import com.abisininstagram.instagramcc.instagramcc.models.Liked;
import com.abisininstagram.instagramcc.instagramcc.models.Photo;
import com.abisininstagram.instagramcc.instagramcc.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import com.abisininstagram.instagramcc.instagramcc.R;
import com.abisininstagram.instagramcc.instagramcc.Utils.BottomNavigationViewHelper;
import com.abisininstagram.instagramcc.instagramcc.Likes.RequestFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Camokan on 25.10.2017.
 */

public class LikesActivity extends AppCompatActivity{
    private static final String TAG = "LikesActivity";
    private static final int ACTIVITY_NUM = 3;

    private Context mContext = LikesActivity.this;
    private ArrayList<String> LikesList = new ArrayList<>();
    private ListView mlvLikes;

    private FirebaseDatabase db;
    private FirebaseDatabase db2;
    private DatabaseReference dbRef;
    private DatabaseReference dbRef2;
    private FirebaseUser fUser;
    private ArrayAdapter<String> adapter;
    private TextView mLikesName;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_likes);
        Log.d(TAG, "onCreate: started.");

        mlvLikes = (ListView) findViewById(R.id.lvLikes);
        mLikesName = (TextView) findViewById(R.id.LikesName);

        db = FirebaseDatabase.getInstance();
        db2 = FirebaseDatabase.getInstance();
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        dbRef = db.getReference("user_photos");
        dbRef2 = db2.getReference("users");

        adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, android.R.id.text1,LikesList);
        mlvLikes.setAdapter(adapter);

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String user_id;
                LikesList.clear();
                for(DataSnapshot ds : dataSnapshot.child(fUser.getUid()).getChildren()){
                    for(DataSnapshot ds2 : ds.child("likes").getChildren()){
                        Log.d("degerlerimiz",String.valueOf(ds2.getValue()));
                        Liked mLiked = ds2.getValue(Liked.class);
                        user_id= mLiked.getUser_id();
                        if(!fUser.getUid().equals(user_id)){
                            LikesList.add(user_id);
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        dbRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String user_id;
                //LikesList.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Log.d("qwe123",String.valueOf(ds.getValue()));
                    User userim = ds.getValue(User.class);
                    if(userim.getUser_id().equals(fUser.getUid())){
                        mLikesName.setText(userim.getUsername());
                    }
                    Log.d("calisma",userim.getUser_id());
                    Log.d("boyut",String.valueOf(LikesList.size()));
                    for(int i = 0;i<LikesList.size();i++){
                        if(LikesList.get(i).equals(userim.getUser_id())){
                            userim.setUsername("Liked photo by " + userim.getUsername());
                            LikesList.set(i,userim.getUsername());
                            Log.d("okey","oldu");
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        setupBottomNavigationView();
    }



    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(mContext, this,bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
}