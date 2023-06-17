package com.ravix.videocallui;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    private SharedPrefManager sharedPrefManager;
    private TextView title;
    Button startMeeting;
    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    private List<Item> itemList;
    CardView createMeet, joinMeet;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPrefManager = new SharedPrefManager(this);


        startMeeting = findViewById(R.id.startMeetingBtn);
        title = findViewById(R.id.welcome_text);
        createMeet = findViewById(R.id.createMeetCard);
        joinMeet = findViewById(R.id.joinMeetCart);
       String titleText = "Hi, " + sharedPrefManager.getUsername();
       title.setText(titleText);

         recyclerView = findViewById(R.id.recyclerView);
         LinearLayoutManager layoutManager = new LinearLayoutManager(this);

         recyclerView.setLayoutManager(layoutManager ); // 2 columns

         itemList = new ArrayList<>();
         itemAdapter = new ItemAdapter(itemList);
         recyclerView.setAdapter(itemAdapter);


         // Add your items to the itemList
         for (int i = 0; i < 7; i++) {
             Item item = new Item("User " + (i + 1), R.drawable.user4);
             itemList.add(item);
             itemAdapter.notifyItemInserted(itemList.size() - 1);

         }

       startMeeting.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(MainActivity.this, CallActivity.class);
                intent.putExtra("username", "Meeting id: 265-658");
                startActivity(intent);
           }
       });

       joinMeet.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(MainActivity.this, CallActivity.class);
               intent.putExtra("username", "Meeting id: 265-658");

               startActivity(intent);
           }
       });

         createMeet.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(MainActivity.this, CallActivity.class);
                 intent.putExtra("username", "Meeting id: 265-658");
                 startActivity(intent);
             }
         });

    }

    @Override
    public void onBackPressed() {
        // Check if the current activity is the main activity
             // If it is the main activity, finish the activity (close the app)
            finish();

    }

}
