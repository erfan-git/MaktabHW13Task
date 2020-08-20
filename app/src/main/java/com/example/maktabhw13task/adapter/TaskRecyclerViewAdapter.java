package com.example.maktabhw13task.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.maktabhw13task.R;
import com.example.maktabhw13task.controller.fragments.dialogs.EditTaskDialog;
import com.example.maktabhw13task.controller.fragments.dialogs.ViewTaskDialog;
import com.example.maktabhw13task.model.TaskModel;
import com.example.maktabhw13task.repository.TaskRepository;
import com.example.maktabhw13task.repository.UserRepository;

import java.text.DateFormat;
import java.util.List;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class TaskRecyclerViewAdapter extends RecyclerView.Adapter<TaskRecyclerViewAdapter.TaskViewHolder> {

    public static final String TAG = "tag";
    private List<TaskModel> mTaskList ;
    private Context mContext;
    private UserRepository mUserRepository = UserRepository.getInstance();
    private OnDeleteTaskListener mOnDeleteTaskListener;

    public TaskRecyclerViewAdapter(Context context, List<TaskModel> taskList, OnDeleteTaskListener onDeleteTaskListener) {
        mContext = context;
        mTaskList = taskList;
        mOnDeleteTaskListener = onDeleteTaskListener;
    }

    public void setTaskList(List<TaskModel> taskList) {
        mTaskList = taskList;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false),mOnDeleteTaskListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, final int position) {
        holder.mTitle.setText(mTaskList.get(position).getTitle());
        holder.mSubtitle.setText(DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT).format(mTaskList.get(position).getDate()));
        holder.mFirstLetter.setText(String.valueOf(mTaskList.get(position).getTitle().toUpperCase().charAt(0)));

        if (mUserRepository.getCurrentUserIndex() == 0) {
            holder.mByUser.setText("(" + mTaskList.get(position).getUser(mTaskList.get(position).getUserId()).getUsername() + ")");
            holder.mDelete.setVisibility(View.VISIBLE);
        }

        onItemClickListeners(holder, position);
    }

    private void onItemClickListeners(final TaskViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mUserRepository.getCurrentUserIndex() == 0) {
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


    class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitle, mSubtitle, mFirstLetter, mByUser;
        private ImageView mDelete;
        OnDeleteTaskListener onDeleteTaskListener;

        public TaskViewHolder(@NonNull View itemView, OnDeleteTaskListener onDeleteTaskListener) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.textViewItemRowTitle);
            mSubtitle = itemView.findViewById(R.id.textViewItemRowSubtitle);
            mFirstLetter = itemView.findViewById(R.id.textViewItemRowFirstLetter);
            mByUser = itemView.findViewById(R.id.textViewItemRowDescription);
            mDelete = itemView.findViewById(R.id.imageViewDeleteItemRow);
            this.onDeleteTaskListener = onDeleteTaskListener;
            mDelete.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            mOnDeleteTaskListener.sendTaskInfo(mTaskList.get(getAdapterPosition()).getTaskId(), getAdapterPosition() );
        }
    }

    public interface OnDeleteTaskListener{
        void sendTaskInfo(UUID taskId, int taskPosition);
    }

}
