package com.example.user.storybook;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

/**
 * Created by USER on 02.05.2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    List<Data> data;
    public RecyclerAdapter(Context context,List<Data> data){
        super();
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView cardImage;
        TextView cardDescription;
        TextView cardLocation;
        TextView likeCount;
        public ViewHolder(final View itemView){
            super(itemView);
            cardImage = (ImageView)itemView.findViewById(R.id.cardPhoto);
            cardDescription= (TextView)itemView.findViewById(R.id.cardStory);
            cardLocation = (TextView)itemView.findViewById(R.id.linkMap);
            likeCount= (TextView)itemView.findViewById(R.id.countLike);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
       Data superHero =  data.get(position);
        ViewHolder myholder = (ViewHolder)holder;
        myholder.cardDescription.setText(superHero.getDescription());
        myholder.likeCount.setText(superHero.getLikes());
        String str = superHero.getImage().replace("\\","/");
        Picasso.with(context).load(new File(str)).into(myholder.cardImage);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
