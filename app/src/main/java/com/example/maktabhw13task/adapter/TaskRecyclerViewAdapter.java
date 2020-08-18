package com.example.maktabhw13task.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.maktabhw13task.R;
import com.example.maktabhw13task.controller.fragments.dialogs.EditTaskDialog;
import com.example.maktabhw13task.controller.fragments.dialogs.ViewTaskDialog;
import com.example.maktabhw13task.model.TaskModel;

import java.text.DateFormat;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class TaskRecyclerViewAdapter extends RecyclerView.Adapter<TaskRecyclerViewAdapter.TaskViewHolder> {

    private List<TaskModel> mTaskList;
    private Context mContext;
    private int mCurrentUser;

    public TaskRecyclerViewAdapter(List<TaskModel> taskList, Context context, int currentUser) {
        mTaskList = taskList;
        mContext = context;
        mCurrentUser = currentUser;
    }


    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.mTitle.setText(mTaskList.get(position).getTitle());
        holder.mSubtitle.setText(DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT).format(mTaskList.get(position).getDate()));
        holder.mFirstLetter.setText(String.valueOf(mTaskList.get(position).getTitle().toUpperCase().charAt(0)));

        if (mCurrentUser == 0)
            holder.mByUser.setText("(" + mTaskList.get(position).getUser(mTaskList.get(position).getUserId()).getUsername() + ")");

        onItemClickListeners(holder, position);
    }

    private void onItemClickListeners(final TaskViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mCurrentUser == 0) {
                    EditTaskDialog.newInstance(mTaskList.get(position)).show(((AppCompatActivity) mContext).getSupportFragmentManager(), "TaskEditDialog");
                } else
                    ViewTaskDialog.newInstance(mTaskList.get(position)).show(((AppCompatActivity) mContext).getSupportFragmentManager(), "ViewTaskDialog");
            }
        });
    }


    @Override
    public int getItemCount() {
        return mTaskList.size();
    }


    class TaskViewHolder extends RecyclerView.ViewHolder {
        private TextView mTitle, mSubtitle, mFirstLetter, mByUser;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.textViewTaskItemTitle);
            mSubtitle = itemView.findViewById(R.id.textViewTaskItemSubtitle);
            mFirstLetter = itemView.findViewById(R.id.textViewTaskItemFirstLetter);
            mByUser = itemView.findViewById(R.id.textViewTaskByUser);

        }
    }


}
