package com.ravix.videocallui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;


public class ParticipantAdapter extends RecyclerView.Adapter<ParticipantAdapter.ParticipantViewHolder> {
    private List<Item> participantList;
    private int itemSize; // Default size
    private int itemHeight;

    public ParticipantAdapter(List<Item> participantList) {
        this.participantList = participantList;
    }

    public void setItemHeight(int height) {
        this.itemHeight = height;
    }

    @NonNull
    @Override
    public ParticipantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ParticipantViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ParticipantViewHolder holder, int position) {
        Item participant = participantList.get(position);
        holder.bind(participant, itemHeight, position);

        holder.removeUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                removeItem(holder.getAdapterPosition());
                Toast.makeText(v.getContext(), participant.getText()+ " removed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return participantList.size();
    }

    public static class ParticipantViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;
        private ConstraintLayout itemLayout;
        private ImageView removeUser;

        public ParticipantViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_image); // Replace with the actual ID of the image view in your item layout
            textView = itemView.findViewById(R.id.item_text);
            itemLayout = itemView.findViewById(R.id.item_layout);
            removeUser = itemView.findViewById(R.id.remove_user);
        }

        public void bind(Item participant, int itemHeight, int position) {
            imageView.setImageResource(participant.getImageResId());
            textView.setText(participant.getText());

            // Adjust item height dynamically
            ViewGroup.LayoutParams layoutParams = itemLayout.getLayoutParams();
            layoutParams.height = itemHeight;
            itemLayout.setLayoutParams(layoutParams);
            // Hide the "remove user" image for the first item
            if (position == 0) {
                removeUser.setVisibility(View.GONE);
            } else {
                removeUser.setVisibility(View.VISIBLE);
            }



        }


    }

    public void updateFirstItem(int newImageResId) {
        if (!participantList.isEmpty()) {
            Item firstItem = participantList.get(0);
            firstItem.setImageResId(newImageResId);
            notifyItemChanged(0);
        }
    }

    public void removeItem(int position) {
        if (position >= 0 && position < participantList.size()) {
            participantList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, participantList.size());
        }
    }
}


//
//public class ParticipantAdapter extends RecyclerView.Adapter<ParticipantAdapter.ParticipantViewHolder> {
//    private List<String> participantList;
//    private int itemSize ;  // Default size
//    private int itemHeight;
//
//
//    public ParticipantAdapter(List<String> participantList) {
//        this.participantList = participantList;
//    }
//    public void setItemHeight(int height) {
//        this.itemHeight = height;
//    }
//    @NonNull
//    @Override
//    public ParticipantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
////        itemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, itemHeight));
//
//        return new ParticipantViewHolder(itemView);
//    }
//
//
//    @Override
//    public void onBindViewHolder(@NonNull ParticipantViewHolder holder, int position) {
//        String participant = participantList.get(position);
//        holder.bind(participant,  itemHeight);
//
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                holder.removeUser.setVisibility(View.VISIBLE);
//             }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return participantList.size();
//    }
//
//    public static class ParticipantViewHolder extends RecyclerView.ViewHolder {
//        private TextView textView;
//        private ConstraintLayout itemLayout;
//        private ImageView removeUser;
//
//
//        public ParticipantViewHolder(View itemView) {
//            super(itemView);
//            textView = itemView.findViewById(R.id.item_text);
//            itemLayout = itemView.findViewById(R.id.item_layout);
//            removeUser  = itemView.findViewById(R.id.remove_user);
//
//
//
//        }
//
//        public void bind(String participant, int itemHeight) {
//            textView.setText(participant);
//
//            // Adjust item height dynamically
//            ViewGroup.LayoutParams layoutParams = itemLayout.getLayoutParams();
//            layoutParams.height = itemHeight;
//            itemLayout.setLayoutParams(layoutParams);
//        }
//    }
//
//}