package com.ravix.videocallui;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import java.util.Locale;
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
    private List<Item> participantList;
    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    private List<Item> itemList;
    private TextView calLText;
    private  Button callEnd;

    private List<String> randomStrings = Arrays.asList("Millie", "Emma", "Jaylene");
    private List<Integer> imageResources = Arrays.asList(R.drawable.user1, R.drawable.user2, R.drawable.user3);
    private Handler timerHandler;
    private Runnable timerRunnable;
    private TextView timerTextView;
    private int startTime = 0;
    private int timeInterval = 1000; // 1 second
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        participantList = new ArrayList<>();
        Item item = new Item("You", R.drawable.you);

        participantList.add(item);

        participantAdapter = new ParticipantAdapter(participantList);
        participantAdapter.notifyItemInserted(0);




        bottomBar = findViewById(R.id.bottomBar);
        mic = findViewById(R.id.mic_icon);
        video = findViewById(R.id.video_icon);
        addUser = findViewById(R.id.add_users_icon);
        camera = findViewById(R.id.camera_change_icon);
        callEnd  = findViewById(R.id.calLEnd);
        timerTextView = findViewById(R.id.timer_text);

        recyclerView = findViewById(R.id.recyclerViewCall);

        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(participantAdapter);

        timerHandler = new Handler();
        timerRunnable = new Runnable() {
            @Override
            public void run() {
                startTime++; // Increment the timer value
                updateTimerText(startTime);
                timerHandler.postDelayed(this, timeInterval); // Schedule the next update
            }
        };

        startTimer();


//        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.grid_spacing); // Adjust the dimension resource as needed
//        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, spacingInPixels, true));


        recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                int itemCount = recyclerView.getAdapter().getItemCount();
                int recyclerViewHeight = recyclerView.getHeight();

                if (itemCount == 1) {
                    itemHeight = ViewGroup.LayoutParams.MATCH_PARENT;
                } else {
                    int numRows = (itemCount + 1) / 2;
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
                     mic.setImageResource(R.drawable.mic_on);
                    isMicOff = false;
                    Toast.makeText(CallActivity.this, "Mic turned on", Toast.LENGTH_SHORT).show();
                } else {
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
                    int newImageResId = R.drawable.you;
                    participantAdapter.updateFirstItem(newImageResId);
                     video.setImageResource(R.drawable.video);
                    isSoundOff = false;
                    Toast.makeText(CallActivity.this, "Everyone can see you", Toast.LENGTH_SHORT).show();

                } else {
                    int newImageResId = R.drawable.user;
                    participantAdapter.updateFirstItem(newImageResId);
                     video.setImageResource(R.drawable.video_off);
                    isSoundOff = true;
                    Toast.makeText(CallActivity.this, "Video disabled", Toast.LENGTH_SHORT).show();

                }
            }
        });

        callEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                      isBackCamera = false;
                    Toast.makeText(CallActivity.this, "Using Front Camera", Toast.LENGTH_SHORT).show();
                } else {
                      isBackCamera = true;
                    Toast.makeText(CallActivity.this, "Using Rear Camera", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }

    private void startTimer() {
        timerHandler.postDelayed(timerRunnable, timeInterval);
    }

    private void updateTimerText(int timeInSeconds) {
        int minutes = timeInSeconds / 60;
        int seconds = timeInSeconds % 60;
        String timerText = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        timerTextView.setText(timerText);
    }

    private void openBottomSheet() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(CallActivity.this);
        View bottomSheetView = LayoutInflater.from(CallActivity.this).inflate(R.layout.bottom_sheet_layout, null);



         Button addButton = bottomSheetView.findViewById(R.id.add_user_btn);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (participantList.size() != 10){
                    participantList.add(getRandomParticipant());
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



              }

            private Item getRandomParticipant() {
                Random random = new Random();
                int randomStringIndex = random.nextInt(randomStrings.size());
                int randomImageIndex = random.nextInt(imageResources.size());

                String randomString = randomStrings.get(randomStringIndex);
                int randomImageRes = imageResources.get(randomImageIndex);

                return new Item(randomString, randomImageRes);
            }

        });

        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timerHandler.removeCallbacks(timerRunnable); // Stop the timer when the activity is destroyed

    }
}
