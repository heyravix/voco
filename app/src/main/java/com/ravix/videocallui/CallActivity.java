package com.ravix.videocallui;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CallActivity extends AppCompatActivity {
    private LinearLayout bottomBar;
    private BottomSheetBehavior<View> bottomSheetBehavior;
    ImageView mic, video, callCut, addUser, camera ;
    boolean isMicOff = false;
    boolean isSoundOff = false;
    private int itemHeight; // Declare itemHeight as a class member variable

    boolean isBackCamera = false;
    private ParticipantAdapter participantAdapter;
    private List<String> participantList;
    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    private List<Item> itemList;
    private TextView calLText;
    private  Button callEnd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        participantList = new ArrayList<>();

        participantList.add("You");

        participantAdapter = new ParticipantAdapter(participantList);
        participantAdapter.notifyItemInserted(0);


        Intent intent = getIntent();
        if (intent != null) {
            calLText = findViewById(R.id.call_title);

            String text = intent.getStringExtra("username");
                 calLText.setText(text);
        }
        bottomBar = findViewById(R.id.bottomBar);
        mic = findViewById(R.id.mic_icon);
        video = findViewById(R.id.video_icon);
        callCut = findViewById(R.id.call_cut_icon);
        addUser = findViewById(R.id.add_users_icon);
        camera = findViewById(R.id.camera_change_icon);
        callEnd  = findViewById(R.id.calLEnd);

        // Set click listeners for the icons in the bottom bar
        recyclerView = findViewById(R.id.recyclerViewCall);

        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
//        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.grid_spacing); // Adjust the dimension resource as needed
//        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, spacingInPixels, true));


        // Calculate itemHeight based on recyclerViewHeight and itemCount
        recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                int itemCount = recyclerView.getAdapter().getItemCount();
                int recyclerViewHeight = recyclerView.getHeight();

                if (itemCount == 1) {
                    itemHeight = ViewGroup.LayoutParams.MATCH_PARENT;
                } else {
                    int numRows = (itemCount + 1) / 2; // Calculate the number of rows in the grid
                    itemHeight = recyclerViewHeight / numRows;
                }

                // Set itemHeight for each item in the RecyclerView
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

                if (layoutManager instanceof GridLayoutManager) {
                    GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
                    int firstVisiblePosition = gridLayoutManager.findFirstVisibleItemPosition();
                    int lastVisiblePosition = gridLayoutManager.findLastVisibleItemPosition();

                    for (int i = firstVisiblePosition; i <= lastVisiblePosition; i++) {
                        View itemView = gridLayoutManager.findViewByPosition(i);
                        if (itemView != null) {
                            ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
                            layoutParams.height = itemHeight;
                            itemView.setLayoutParams(layoutParams);
                        }
                    }
                }
            }
        });

        participantAdapter.setItemHeight(itemHeight);
        recyclerView.setAdapter(participantAdapter);












//        // Update an existing item
//        int updatedPosition = 0;
//        Item updatedItem = itemList.get(updatedPosition);
//        updatedItem.setText("Updated Item");
//        itemAdapter.notifyItemChanged(updatedPosition);
//
//        // Remove an item
//        int removedPosition = 2;
//        itemList.remove(removedPosition);
//        itemAdapter.notifyItemRemoved(removedPosition);


        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBottomSheet();
            }
        });

        mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isMicOff) {
                    // If already clicked, change the icon back to the original
                    mic.setImageResource(R.drawable.mic_on);
                    isMicOff = false;
                    Toast.makeText(CallActivity.this, "Mic turned on", Toast.LENGTH_SHORT).show();
                } else {
                    // If not clicked, change the icon to the new icon
                    mic.setImageResource(R.drawable.mute);
                    isMicOff = true;
                    Toast.makeText(CallActivity.this, "Mic turned off", Toast.LENGTH_SHORT).show();

                }
            }
        });

        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSoundOff) {
                    // If already clicked, change the icon back to the original
                    video.setImageResource(R.drawable.video);
                    isSoundOff = false;
                    Toast.makeText(CallActivity.this, "Everyone can see you", Toast.LENGTH_SHORT).show();

                } else {
                    // If not clicked, change the icon to the new icon
                    video.setImageResource(R.drawable.video_off);
                    isSoundOff = true;
                    Toast.makeText(CallActivity.this, "Video disabled", Toast.LENGTH_SHORT).show();

                }
            }
        });

        callEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle icon3 click event
                Toast.makeText(CallActivity.this, "Call Ended", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(CallActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isBackCamera) {
                    // If already clicked, change the icon back to the original
                     isBackCamera = false;
                    Toast.makeText(CallActivity.this, "Using Front Camera", Toast.LENGTH_SHORT).show();
                } else {
                    // If not clicked, change the icon to the new icon
                     isBackCamera = true;
                    Toast.makeText(CallActivity.this, "Using Rear Camera", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }

    private void openBottomSheet() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(CallActivity.this);
        View bottomSheetView = LayoutInflater.from(CallActivity.this).inflate(R.layout.bottom_sheet_layout, null);



        // Set click listener for the expanded view in the bottom sheet
        Button addButton = bottomSheetView.findViewById(R.id.add_user_btn);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (participantList.size() != 10){
                    // Add a new participant to the list
                    participantList.add(getRandomName());
                    participantAdapter.notifyItemInserted(participantList.size() - 1);

                    // Calculate the item height dynamically
                    int maxItemsPerRow = 2; // Maximum number of items to display per row
                    int numRows = (int) Math.ceil((double) participantList.size() / maxItemsPerRow);
                    int recyclerViewHeight = recyclerView.getHeight();
                    int itemHeight = recyclerViewHeight / numRows;
                    participantAdapter.setItemHeight(itemHeight);

                    participantAdapter.notifyDataSetChanged();
                }else {
                    Toast.makeText(CallActivity.this, "You can add only 10 users.", Toast.LENGTH_SHORT).show();

                }



                // Notify the adapter that the data set has changed
             }

            private String getRandomName() {
                String[] names = {
                        "John",
                        "Emma",
                        "Liam",
                        "Olivia",
                        "Noah",
                        "Ava",
                        // Add more names to the array as needed
                };

                Random random = new Random();
                int randomIndex = random.nextInt(names.length);

                return names[randomIndex];
            }
        });

        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }
}
