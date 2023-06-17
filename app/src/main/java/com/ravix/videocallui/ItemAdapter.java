package com.ravix.videocallui;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private List<Item> itemList;
    private int itemHeight;
    private int itemSize = RecyclerView.LayoutParams.WRAP_CONTENT /2;  // Default size

    public ItemAdapter(List<Item> itemList) {
        this.itemList = itemList;
    }
    public void setItemSize(int size) {
        itemSize = size;
        notifyDataSetChanged();  // Notify the adapter that the data set has changed
    }
    public void setItemHeight(int height) {
        this.itemHeight = height;
    }
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_list, parent, false);


        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.itemImage.setImageResource(item.getImageResId());
        holder.itemText.setText(item.getText());
        holder.callbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = " Talking to " + item.getText();
                    // Create an intent to start the desired activity or perform an action
                    Intent intent = new Intent(v.getContext(), CallActivity.class);
                    intent.putExtra("username",text );
                    v.getContext().startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView itemImage, callbtn;
        public TextView itemText;

        public ItemViewHolder(View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.user_image);
            itemText = itemView.findViewById(R.id.user_name);
            callbtn = itemView.findViewById(R.id.callBtn);

        }
    }
}
