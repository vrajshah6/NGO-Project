package com.example.dell.ngoproject.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.dell.ngoproject.Event;
import com.example.dell.ngoproject.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Event> eventList;
    private AdapterListeners adapterListeners;

    public interface AdapterListeners {
        void onEventItemClick(int position, int id);
    }

    public EventsAdapter(Context context, ArrayList<Event> eventList, AdapterListeners adapterListeners) {
        this.context = context;
        this.eventList = eventList;
        this.adapterListeners = adapterListeners;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView ivNGOPhoto,imgEventPhoto;
        TextView tvEventTitle, tvDescription,tvVenue,tvNGOName;
        LinearLayout layoutRootView;


        public ViewHolder(View itemView) {
            super(itemView);

            ivNGOPhoto = itemView.findViewById(R.id.ivNGOPhoto);
            imgEventPhoto = itemView.findViewById(R.id.imgEventPhoto);
            tvEventTitle = itemView.findViewById(R.id.tvEventTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            layoutRootView = itemView.findViewById(R.id.layoutEvents);
            tvVenue=itemView.findViewById(R.id.tvVenue);
            tvNGOName=itemView.findViewById(R.id.tvNGOName);


            ivNGOPhoto.setOnClickListener(this);
            tvEventTitle.setOnClickListener(this);
            layoutRootView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.layoutEvents:
                    adapterListeners.onEventItemClick(getAdapterPosition(),
                            (int) eventList.get(getAdapterPosition()).getId());
                    //adapterListeners.onEventItemClick(getAdapterPosition(),
                            //eventList.get(getAdapterPosition()).getId());
                    break;
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.layout_item_events, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //holder.ivNGOPhoto.setImageResource(R.drawable.ic_menu_share);
        ImageLoader.getInstance().displayImage("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQQ4aTlYl9SmQ8ImjV2LgNx1bnVkKo3_9zvQfg5UIL_UIb3St0U2A",
                holder.ivNGOPhoto,
                new DisplayImageOptions.Builder().cacheOnDisk(true).build());
//        ImageLoader.getInstance().displayImage(eventList.get(holder.getAdapterPosition()).getImageUrl(),
//                holder.imgEventPhoto,
//                new DisplayImageOptions.Builder().cacheOnDisk(true).build());

        if (eventList.get(holder.getAdapterPosition()).getImageUrlList().size() > 0) {
            ImageLoader.getInstance().displayImage(eventList.get(holder.getAdapterPosition()).getImageUrlList().get(0),
                    holder.imgEventPhoto,
                    new DisplayImageOptions.Builder().cacheOnDisk(true).build());
        } else {
            holder.imgEventPhoto.setImageDrawable(null);
        }

        holder.tvEventTitle.setText(eventList.get(holder.getAdapterPosition()).getTitle());
        holder.tvVenue.setText(eventList.get(holder.getAdapterPosition()).getVenue());
        holder.tvDescription.setText(eventList.get(holder.getAdapterPosition()).getDescription());





        //holder.tvDescription;

//        this.id = id;
//        this.name = name;
//        this.title = title;
//        this.date = date;
//        this.venue = venue;
//        this.description = description;
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }
}
