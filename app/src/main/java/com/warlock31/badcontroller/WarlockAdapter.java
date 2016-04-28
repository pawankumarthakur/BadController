package com.warlock31.badcontroller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Warlock on 4/24/2016.
 */
public class WarlockAdapter extends RecyclerView.Adapter<WarlockAdapter.MyViewHolder> {


    private LayoutInflater inflater;
    List<Information> data = Collections.emptyList();

    public WarlockAdapter(Context context){
        inflater = LayoutInflater.from(context);

        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.custom_row,parent,false);

        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder  holder, int position) {

        Information current = data.get(position);
        holder.title.setText(current.title);
        holder.item.setImageResource(current.iconId);


    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        ImageView item;

        public MyViewHolder(View itemView) {

            super(itemView);
            title = (TextView) itemView.findViewById(R.id.listText);
            item = (ImageView) itemView.findViewById(R.id.listIcon);
        }
    }
}