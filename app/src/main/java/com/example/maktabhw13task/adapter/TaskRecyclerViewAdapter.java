package com.example.maktabhw13task.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.maktabhw13task.R;
import com.example.maktabhw13task.model.TaskModel;
import com.example.maktabhw13task.repository.UserRepository;

import java.text.DateFormat;
import java.util.List;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TaskRecyclerViewAdapter extends RecyclerView.Adapter<TaskRecyclerViewAdapter.TaskViewHolder> {

    public static final String TAG = "tag";
    private List<TaskModel> mTaskList;
    private UserRepository mUserRepository = UserRepository.getInstance();
    private OnMyTaskListener mOnMyTaskListener;

    public TaskRecyclerViewAdapter(List<TaskModel> taskList, OnMyTaskListener onMyTaskListener) {
        mTaskList = taskList;
        mOnMyTaskListener = onMyTaskListener;
    }

    public void setTaskList(List<TaskModel> taskList) {
        mTaskList = taskList;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false), mOnMyTaskListener);
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
    }

    @Override
    public int getItemCount() {
        return mTaskList.size();
    }

    public interface OnMyTaskListener {
        void sendTaskInfo(UUID taskId, int taskPosition);

        void startEditTaskDialog(int itemPosition, boolean admin);
    }

    class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        OnMyTaskListener onMyTaskListener;
        private TextView mTitle, mSubtitle, mFirstLetter, mByUser;
        private ImageView mDelete;

        public TaskViewHolder(@NonNull View itemView, OnMyTaskListener onMyTaskListener) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.textViewItemRowTitle);
            mSubtitle = itemView.findViewById(R.id.textViewItemRowSubtitle);
            mFirstLetter = itemView.findViewById(R.id.textViewItemRowFirstLetter);
            mByUser = itemView.findViewById(R.id.textViewItemRowDescription);
            mDelete = itemView.findViewById(R.id.imageViewDeleteItemRow);
            this.onMyTaskListener = onMyTaskListener;
            mDelete.setOnClickListener(this);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (v == mDelete)
                mOnMyTaskListener.sendTaskInfo(mTaskList.get(getAdapterPosition()).getTaskId(), getAdapterPosition());

            if (v == itemView) {

                if (mUserRepository.getCurrentUserIndex() == 0)
                    mOnMyTaskListener.startEditTaskDialog(getAdapterPosition(), true);

                else
                    mOnMyTaskListener.startEditTaskDialog(getAdapterPosition(), false);

            }


        }
    }

}
