package com.example.tstv.remindmeabout.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.tstv.remindmeabout.fragment.TaskFragment;
import com.example.tstv.remindmeabout.model.Item;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by tstv on 31.03.2017.
 */

public abstract class TaskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    List<Item> mList;
    TaskFragment mTaskFragment;

    public TaskAdapter(TaskFragment taskFragment){
        this.mTaskFragment = taskFragment;
        mList = new ArrayList<>();
    }

    public Item getItem(int position){
        return mList.get(position);
    }

    public void addItem(Item item){
        mList.add(item);
        notifyItemInserted(getItemCount() - 1);
    }

    public void addItem(int location,Item item){
        mList.add(location,item);
        notifyItemInserted(location);
    }

    public void removeItem(int location){
        if (location >= 0 && location <= getItemCount() - 1){
            mList.remove(location);
            notifyItemRemoved(location);
        }
    }

    public void removeAllItems(){
        if (getItemCount() != 0){
            mList = new ArrayList<>();
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    protected class TaskViewHolder extends RecyclerView.ViewHolder{

        protected TextView title;
        protected TextView date;

        protected CircleImageView priority;

        public TaskViewHolder(View itemView, TextView title, TextView date,CircleImageView priority) {
            super(itemView);
            this.title = title;
            this.date = date;
            this.priority = priority;

        }
    }


    public TaskFragment getTaskFragment() {
        return mTaskFragment;
    }
}
