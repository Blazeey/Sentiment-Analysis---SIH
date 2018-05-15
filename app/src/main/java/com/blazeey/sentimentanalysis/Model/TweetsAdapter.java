package com.blazeey.sentimentanalysis.Model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blazeey.sentimentanalysis.R;

import java.util.List;

/**
 * Created by venki on 31/3/18.
 */

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.MyViewHolder> {

    Context context;
    List<String> tweetsList;

    public TweetsAdapter(Context context, List<String> tweetsList) {
        this.context = context;
        this.tweetsList = tweetsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.text_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.textView.setText(tweetsList.get(position));
    }

    @Override
    public int getItemCount() {
        return tweetsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        public MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text);
        }
    }
}
