package com.warlock31.badcontroller.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.warlock31.badcontroller.pojo.Information;
import com.warlock31.badcontroller.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by Warlock on 4/24/2016.
 */
public class WarlockAdapter extends RecyclerView.Adapter<WarlockAdapter.MyViewHolder> {


    private LayoutInflater inflater;
    Context context;

//    private ClickListener clickListener;
    List<Information> data = Collections.emptyList();

    public WarlockAdapter(Context context, List<Information> data){
        this.context = context;
        inflater = LayoutInflater.from(context);

        this.data = data;
    }


    public void delete(int position){
        data.remove(position);
        notifyItemRemoved(position);
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


//    public void setClickListener(ClickListener clickListener){
//        this.clickListener = clickListener;
//    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView item;

        public MyViewHolder(View itemView) {

            super(itemView);


            title = (TextView) itemView.findViewById(R.id.listText);
            item = (ImageView) itemView.findViewById(R.id.listIcon);

        }


    }



}
