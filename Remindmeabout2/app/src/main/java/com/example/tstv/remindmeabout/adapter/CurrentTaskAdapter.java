package com.example.tstv.remindmeabout.adapter;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.widget.TextView;

import com.example.tstv.remindmeabout.R;
import com.example.tstv.remindmeabout.Utils;
import com.example.tstv.remindmeabout.fragment.CurrentTaskFragment;
import com.example.tstv.remindmeabout.fragment.TaskFragment;
import com.example.tstv.remindmeabout.model.Item;
import com.example.tstv.remindmeabout.model.ModelTask;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by tstv on 31.03.2017.
 */

public class CurrentTaskAdapter extends TaskAdapter{



    private static final int TYPE_TASK = 0;
    private static final int TYPE_SEPARATOR = 1;

    public CurrentTaskAdapter(CurrentTaskFragment taskFragment) {
        super(taskFragment);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_TASK:
                View v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.model_taask,parent,false);
                TextView title = (TextView) v.findViewById(R.id.tvTaskTitle);
                TextView date = (TextView) v.findViewById(R.id.tvTaskDate);
                CircleImageView priority = (CircleImageView) v.findViewById(R.id.cvTaskPriority);
                return new TaskViewHolder(v,title,date,priority);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Item item = mList.get(position);
        if (item.isTask()){
            holder.itemView.setEnabled(true);
            final ModelTask task = (ModelTask) item;
            final TaskViewHolder taskViewHolder = (TaskViewHolder) holder;

            final View itemView = taskViewHolder.itemView;
            final Resources resources = itemView.getResources();

            taskViewHolder.title.setText(task.getTitle());
            if (task.getDate() != 0){
                taskViewHolder.date.setText(Utils.getFullDate(task.getDate()));
            }else {
                 taskViewHolder.date.setText(null);
            }

            itemView.setVisibility(View.VISIBLE);

            taskViewHolder.priority.setEnabled(true);


            taskViewHolder.title.setTextColor(resources.getColor(R.color.primary_text_default_material_light));
            taskViewHolder.date.setTextColor(resources.getColor(R.color.secondary_text_default_material_light));
            taskViewHolder.priority.setColorFilter(resources.getColor(task.getPriorityColor()));
            taskViewHolder.priority.setImageResource(R.drawable.ic_checkbox_blank_circle_white_48dp);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getTaskFragment().removeTaskDialog(taskViewHolder.getLayoutPosition());
                        }
                    }, 1000);

                    return true;
                }
            });

            taskViewHolder.priority.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    taskViewHolder.priority.setEnabled(false);
                    task.setStatus(ModelTask.STATUS_DONE);
                    getTaskFragment().activity.dbHelper.update().status(task.getTimeStamp(), ModelTask.STATUS_DONE);


                    taskViewHolder.title.setTextColor(resources.getColor(R.color.primary_text_disabled_material_light));
                    taskViewHolder.date.setTextColor(resources.getColor(R.color.secondary_text_disabled_material_light));
                    taskViewHolder.priority.setColorFilter(resources.getColor(task.getPriorityColor()));

                    ObjectAnimator flipIn = ObjectAnimator.ofFloat(taskViewHolder.priority,"rotationY", -180f, 0f);

                    flipIn.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            if (task.getStatus() == ModelTask.STATUS_DONE) {
                                taskViewHolder.priority.setImageResource(R.drawable.ic_check_circle_white_48dp);

                                ObjectAnimator translationX = ObjectAnimator.ofFloat(itemView,
                                        "translationX", 0f, itemView.getWidth());

                                ObjectAnimator translationXBack = ObjectAnimator.ofFloat(itemView,
                                        "translationX", itemView.getWidth(), 0f);

                                translationX.addListener(new Animator.AnimatorListener() {
                                    @Override
                                    public void onAnimationStart(Animator animation) {

                                    }

                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        itemView.setVisibility(View.GONE);
                                        getTaskFragment().moveTask(task);
                                        removeItem(taskViewHolder.getLayoutPosition());
                                    }

                                    @Override
                                    public void onAnimationCancel(Animator animation) {

                                    }

                                    @Override
                                    public void onAnimationRepeat(Animator animation) {

                                    }
                                });

                                AnimatorSet translationSet = new AnimatorSet();
                                translationSet.play(translationX).before(translationXBack);
                                translationSet.start();
                            }
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });

                    flipIn.start();
                }
            });

        }
    }


    @Override
    public int getItemViewType(int position) {
        if (getItem(position).isTask()){
            return TYPE_TASK;
        }else {
            return TYPE_SEPARATOR;
        }
    }

}
