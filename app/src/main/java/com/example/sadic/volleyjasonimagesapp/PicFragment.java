package com.example.sadic.volleyjasonimagesapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class PicFragment extends Fragment {

    ImageView ivUrlPic;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pic, container, false);

        ivUrlPic = v.findViewById(R.id.imageView);


        Bundle b = this.getArguments();
        String imgUrl = b.getString("urlImg");

        Picasso.get().load(imgUrl).placeholder(R.drawable.ic_launcher_foreground).into(ivUrlPic);
        return v;
    }
}
