package com.example.dell.ngoproject.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.dell.ngoproject.FundRaise;
import com.example.dell.ngoproject.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

public class FundRaiseAdapter extends RecyclerView.Adapter<FundRaiseAdapter.ViewHolder> {

    private Context context;
    private ArrayList<FundRaise> fundRaiseList;
    private AdapterListeners adapterListeners;

    public interface AdapterListeners {
        void onEventItemClick(int position, int id);
        void onBtnDonateClick(int position);
    }

    public FundRaiseAdapter(Context context, ArrayList<FundRaise> fundRaiseList, AdapterListeners adapterListeners) {
        this.context = context;
        this.fundRaiseList = fundRaiseList;
        this.adapterListeners = adapterListeners;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView ivNGOPhoto,imgFundPhoto;
        TextView tvPostTitle, tvDescription,tvTotalAmount;
        LinearLayout layoutFundRaise;
        Button btnDonate;

        public ViewHolder(View itemView) {
            super(itemView);

            ivNGOPhoto = itemView.findViewById(R.id.ivNGOPhoto);
            imgFundPhoto = itemView.findViewById(R.id.imgFundPhoto);
            tvPostTitle = itemView.findViewById(R.id.tvPostTitle);
            tvTotalAmount=itemView.findViewById(R.id.tvTotalAmount);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            layoutFundRaise = itemView.findViewById(R.id.layoutFundRaise);
            btnDonate=itemView.findViewById(R.id.btnDonate);

            layoutFundRaise.setOnClickListener(this);
            btnDonate.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                    case R.id.layoutFundRaise:
                    adapterListeners.onEventItemClick(getAdapterPosition(),
                            fundRaiseList.get(getAdapterPosition()).getId());
                    break;

                case R.id.btnDonate:
                    adapterListeners.onBtnDonateClick(getAdapterPosition());
                    break;
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.layout_item_fund_raise, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       // holder.ivNGOPhoto.setImageResource(R.drawable.ic_menu_share);
//        ImageLoader.getInstance().displayImage("https://images.theconversation.com/files/184154/original/file-20170831-9929-1hwb7p7.jpg?ixlib=rb-1.1.0&rect=3%2C59%2C2196%2C1067&q=45&auto=format&w=1356&h=668&fit=crop",
//                holder.ivNGOPhoto);
        ImageLoader.getInstance().displayImage("https://d2gg9evh47fn9z.cloudfront.net/800px_COLOURBOX20400235.jpg",
                holder.ivNGOPhoto,
                new DisplayImageOptions.Builder().cacheOnDisk(true).build());

        if (fundRaiseList.get(holder.getAdapterPosition()).getImageUrlList().size() > 0) {
            ImageLoader.getInstance().displayImage(fundRaiseList.get(holder.getAdapterPosition()).getImageUrlList().get(0),
                    holder.imgFundPhoto,
                    new DisplayImageOptions.Builder().cacheOnDisk(true).build());
        } else {
            holder.imgFundPhoto.setImageDrawable(null);
        }

        holder.tvTotalAmount.setText(String.valueOf(fundRaiseList.get(holder.getAdapterPosition()).getAmount()));
        holder.tvPostTitle.setText(fundRaiseList.get(holder.getAdapterPosition()).getName());
        holder.tvDescription.setText(fundRaiseList.get(holder.getAdapterPosition()).getDescripition());
    }

    @Override
    public int getItemCount() {
        return fundRaiseList.size();
    }
}
