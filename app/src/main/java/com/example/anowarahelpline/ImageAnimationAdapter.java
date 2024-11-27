package com.example.anowarahelpline;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.L;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImageAnimationAdapter extends RecyclerView.Adapter<ImageAnimationAdapter.ViewHolder> {

    Context context;
    int[] images = {};

    public ImageAnimationAdapter(Context context, int[] images) {
        this.context = context;
        this.images = images;
    }

    @NonNull
    @Override
    public ImageAnimationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.animation_image,parent,false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAnimationAdapter.ViewHolder holder, int position) {

        Log.d("imagesssss", "onBindViewHolder: "+images[position]);

        Picasso.get()
                .load(images[position])
                .into(holder.imageView);



    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.animationImageId);
        }
    }
}
