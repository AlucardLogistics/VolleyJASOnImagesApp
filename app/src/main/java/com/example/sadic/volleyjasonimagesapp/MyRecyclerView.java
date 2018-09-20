package com.example.sadic.volleyjasonimagesapp;

import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MyRecyclerView extends RecyclerView.Adapter<MyRecyclerView.MyViewHolder> {

    List<Album> albumList;
    Context mContext;

    public MyRecyclerView(List<Album> albumList, Context ctx) {
        this.albumList = albumList;
        this.mContext = ctx;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new MyViewHolder(v, mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final Album album = albumList.get(position);

        holder.tvAlbumId.setText("AlbumID: " + album.getAlbumId());
        holder.tvId.setText("ID: " + album.getId());
        holder.tvTitle.setText("Title: " + album.getTitle());

        Picasso.get()
                .load(album.getThumb())
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.ivThumb);

        holder.ivThumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Clicked!", Toast.LENGTH_SHORT).show();
                String urlImg = album.getImg();

                Bundle bundle = new Bundle();
                bundle.putString("urlImg", urlImg);

                PicFragment pic = new PicFragment();
                pic.setArguments(bundle);

                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.frameLayout, pic)
                        .addToBackStack(null)
                        .commit();
            }
        });

    }

    private void openPic() {
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvAlbumId, tvId, tvTitle, tvThumbPic;
        ImageView ivThumb;
        Context context;

        public MyViewHolder(View itemView, Context ctx) {
            super(itemView);
            this.context = ctx;
            tvAlbumId = itemView.findViewById(R.id.tvAlbumID);
            tvId = itemView.findViewById(R.id.tvId);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            ivThumb = itemView.findViewById(R.id.ivThumb);
            //ivThumb.setOnClickListener(this);

        }

    }
}
